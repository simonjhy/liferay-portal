/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.check;

import aQute.libg.tuple.Pair;

import com.liferay.source.formatter.upgrade.GAV;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.FileFunctions;
import com.liferay.source.formatter.upgrade.util.GitFunctions;
import com.liferay.source.formatter.upgrade.util.GradleFunctions;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;
import com.liferay.source.formatter.upgrade.util.PluginsUtils;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.LoadProperties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author Simon Jiang
 */
public abstract class UpgradeConvertModuleCheck extends UpgradeAbstractCheck {

	protected abstract List<Pair<String, String>> computePossibleUpgrades(
			Path repoPath, LugbotConfig lugbotConfig)
		throws IOException;

	protected boolean contains(Collection<?> collections, Object object) {
		if ((collections == null) || (object == null)) {
			return false;
		}

		return collections.contains(object);
	}

	protected abstract Optional<Path> converPluginProject(
			Path workspacePath, Path reportPath, Path pluginPath,
			String pluginFileName, Path modulePath, String type,
			String upgradeVersion)
		throws Exception;

	protected List<GAV> convertPortalDependencyJarProperty(
			Path pluginsSdkPath, Path pluginPath, String liferayVersion)
		throws Exception {

		List<GAV> convertedDependencies = new ArrayList<>();

		Path liferayPluginPackagePath = pluginPath.resolve(
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties");

		if (Files.exists(liferayPluginPackagePath)) {
			try (InputStream fileInputStream = Files.newInputStream(
					liferayPluginPackagePath)) {

				Properties liferayPluginPackageProperties = loadProperties(
					fileInputStream);

				String portalJarsValue =
					liferayPluginPackageProperties.getProperty(
						"portal-dependency-jars");

				List<String> portalDependencyJars = new ArrayList<>(
					Arrays.asList(_PORTLET_PLUGIN_API_DEPENDENCIES));

				if (Objects.nonNull(portalJarsValue)) {
					Collections.addAll(
						portalDependencyJars, portalJarsValue.split(","));
				}

				List<String> missingDependencyJars = new ArrayList<>();

				try (InputStream inputStream =
						UpgradeConvertModuleCheck.class.getResourceAsStream(
							"/dependencies/upgrade/portal-dependency-jars-62." +
								"properties")) {

					Properties properties = loadProperties(inputStream);

					Map<String, GAV> migratedDependencies =
						getMigratedDependencies(liferayVersion);

					for (String portalDependencyJar : portalDependencyJars) {
						GAV gav = migratedDependencies.get(portalDependencyJar);

						if (gav == null) {
							String newDependency = properties.getProperty(
								portalDependencyJar);

							if ((newDependency == null) ||
								newDependency.isEmpty()) {

								missingDependencyJars.add(portalDependencyJar);

								continue;
							}

							String[] coordinates = newDependency.split(":");

							if (coordinates.length != 3) {
								missingDependencyJars.add(portalDependencyJar);

								continue;
							}

							gav = new GAV(
								coordinates[0], coordinates[1], coordinates[2]);
						}

						if (!gav.isRemove()) {
							convertedDependencies.add(gav);
						}
					}
				}

				if (!missingDependencyJars.isEmpty()) {
					LoadProperties loadProperties = new LoadProperties();

					Project project = new Project();

					project.setProperty("sdk.dir", pluginsSdkPath.toString());

					loadProperties.setProject(project);

					Path buildProperitesPath = pluginsSdkPath.resolve(
						"build.properties");

					if (Files.exists(buildProperitesPath)) {
						loadProperties.setSrcFile(buildProperitesPath.toFile());

						loadProperties.execute();
					}

					Optional<Path> portalDirOptional = Optional.ofNullable(
						project.getProperty(
							"app.server." +
								project.getProperty("app.server.type") +
									".portal.dir")
					).map(
						Paths::get
					);

					if (portalDirOptional.filter(
							Files::exists
						).isPresent()) {

						missingDependencyJars.stream(
						).map(
							jarName -> portalDirOptional.get(
							).resolve(
								"WEB-INF"
							).resolve(
								"lib"
							).resolve(
								jarName
							).toFile()
						).filter(
							File::exists
						).map(
							portalJar -> getGAVFromJarPath(portalJar.toPath())
						).forEach(
							gav -> {
								if (gav.isUnknown()) {
									//add log
								}

								convertedDependencies.add(gav);
							}
						);
					}
					else {
						missingDependencyJars.stream(
						).map(
							jarName -> new GAV(jarName)
						).forEach(
							gav -> {
								if (gav.isUnknown()) {
									//add log
								}

								convertedDependencies.add(gav);
							}
						);
					}
				}
			}
		}

		return convertedDependencies;
	}

	protected void convertWebInfLibNames(
			Path workspaceLibsPath, Path pluginPath,
			Set<GradleDependency> convertDependencies, String liferayVersion,
			boolean report)
		throws Exception {

		Path webInfLibPath = pluginPath.resolve(getWebInf() + "/lib");

		if (!Files.exists(webInfLibPath)) {
			return;
		}

		Map<String, GAV> migratedDependencies = getMigratedDependencies(
			liferayVersion);

		Set<String> jarNames = migratedDependencies.keySet();

		String serviceJarName =
			String.valueOf(pluginPath.getFileName()) + "-service.jar";

		try (Stream<Path> webInfLibPaths = Files.list(webInfLibPath)) {
			webInfLibPaths.filter(
				filePath -> {
					String fileName = String.valueOf(filePath.getFileName());

					if (!fileName.endsWith(".jar") ||
						Objects.equals(serviceJarName, fileName)) {

						return false;
					}

					return true;
				}
			).forEach(
				libPath -> {
					Path libFileName = libPath.getFileName();

					try {
						GAV gav = migratedDependencies.get(
							libFileName.toString());

						if (gav == null) {
							gav = getGAVFromJarPath(libPath);
						}

						if (gav.isRemove()) {
							FileFunctions.deleteQuietly(libPath.toFile());
						}
						else if (gav.isUnknown()) {
							if (!jarNames.contains(libFileName.toString())) {
								String noExtensionName =
									FileFunctions.removeExtension(
										libFileName.toString());

								boolean foundDependency =
									convertDependencies.stream(
									).filter(
										dependency -> Optional.ofNullable(
											dependency.getReference()
										).map(
											s -> s.contains(noExtensionName)
										).orElse(
											false
										)
									).findAny(
									).isPresent();

								if (!foundDependency) {
									StringBuilder sb = new StringBuilder(
										"compile rootProject.files(\"libs/");

									sb.append(libFileName.toString());
									sb.append("\")");

									convertDependencies.add(
										new GradleDependency(sb.toString()));
								}
							}

							if (!report) {
								FileFunctions.copyFile(
									workspaceLibsPath,
									workspaceLibsPath.resolve(
										libFileName.toString()));
							}
						}
						else {
							convertDependencies.add(
								new GradleDependency(
									gav.toCompileDependency()));
						}
					}
					catch (Exception exception) {
						//						logError(_logger, e);
					}
				}
			);
		}
	}

	@Override
	protected void doUpgrade(
			Path repoPath, LugbotConfig lugbotConfig, Path workspacePath)
		throws Exception {

		try {
			List<Pair<String, String>> pluginTypes = computePossibleUpgrades(
				repoPath, lugbotConfig);

			if (pluginTypes.isEmpty()) {
				SourceFormatterUtil.printError(
					null,
					MessageFormat.format(
						"Expected {0} can not find plugins to convert",
						repoPath));

				return;
			}

			Optional<Path> originPathOptional = MavenFunctions.getOriginPath(
				repoPath, lugbotConfig);

			Path sourcePath = originPathOptional.orElse(repoPath);

			pluginTypes.stream(
			).map(
				pair -> {
					Pair<String, List<Path>> dto = null;

					String plugin = pair.getFirst();

					Path originalPluginPath = sourcePath.resolve(plugin);

					Path pluginPath = originalPluginPath.normalize();

					String type = pair.getSecond();

					String pluginFileName = String.valueOf(
						pluginPath.getFileName());

					Optional<Path> modulesPathOptional =
						GradleFunctions.getWorkspacePathByType(
							workspacePath, type);

					Optional<Path> modulePathOptional = modulesPathOptional.map(
						path -> {
							if (Objects.equals(
									type,
									PluginsUtils.SERVICE_BUILDER_PORTLET)) {

								return path.resolve(
									getServiceBuilderParentName(
										pluginFileName));
							}

							return path;
						}
					).map(
						path -> path.resolve(pluginFileName)
					).filter(
						Files::exists
					);

					if (!modulePathOptional.isPresent()) {
						return dto;
					}

					Optional<Path> convertedBuildPathOptional =
						Optional.empty();

					try {
						convertedBuildPathOptional = converPluginProject(
							workspacePath, sourcePath, pluginPath,
							pluginFileName, modulePathOptional.get(), type,
							lugbotConfig.tasks.upgradeVersion);
					}
					catch (Exception exception) {
						SourceFormatterUtil.printError(
							null,
							MessageFormat.format(
								"Failed to convert {0} project dependency {1}",
								pluginPath, exception.getMessage()));
					}

					if (convertedBuildPathOptional.isPresent()) {
						if (lugbotConfig.tasks.saveCommit) {
							try {
								dto = _commitBuildChanges(
									convertedBuildPathOptional.get(), repoPath,
									lugbotConfig);
							}
							catch (Exception exception) {
							}
						}
						else {
							dto = new Pair<>(
								plugin,
								Collections.singletonList(
									convertedBuildPathOptional.get()));
						}
					}

					return dto;
				}
			).filter(
				Objects::nonNull
			).collect(
				Collectors.toMap(Pair::getFirst, Pair::getSecond)
			);
		}
		catch (IOException ioException) {
		}
	}

	protected abstract List<Pair<String, String>> findPlugins(
			Path originPath, List<String> pluginNames)
		throws IOException;

	protected String getAttr(Node item, String attrName) {
		if (item != null) {
			NamedNodeMap attrs = item.getAttributes();

			if (attrs != null) {
				Node attr = attrs.getNamedItem(attrName);

				if (attr != null) {
					return attr.getNodeValue();
				}
			}
		}

		return null;
	}

	protected GAV getGAVFromJarPath(Path dependencyJarPath) {
		try (JarFile jarFile = new JarFile(dependencyJarPath.toFile())) {
			Enumeration<JarEntry> jarEntriesEnumeration = jarFile.entries();

			while (jarEntriesEnumeration.hasMoreElements()) {
				JarEntry jarEntry = jarEntriesEnumeration.nextElement();

				String name = jarEntry.getName();

				if (name.startsWith("META-INF/maven") &&
					name.endsWith("pom.properties")) {

					Properties properties = loadProperties(
						jarFile.getInputStream(jarEntry));

					return new GAV(
						properties.get("groupId"), properties.get("artifactId"),
						properties.get("version"));
				}
			}
		}
		catch (IOException ioException) {
		}

		Path dependencyJarName = dependencyJarPath.getFileName();

		return new GAV(dependencyJarName.toString());
	}

	protected Map<String, GAV> getMigratedDependencies(String liferayVersion) {
		if (Objects.equals("7.1", liferayVersion)) {
			return _migratedDependencies71;
		}
		else if (Objects.equals("7.2", liferayVersion)) {
			return _migratedDependencies72;
		}
		else if (Objects.equals("7.3", liferayVersion)) {
			return _migratedDependencies73;
		}
		else if (Objects.equals("7.4", liferayVersion)) {
			//TODO need to add 7.4 support
		}

		return Collections.emptyMap();
	}

	protected String getServiceBuilderParentName(
		String serviceBuilderPortletName) {

		String serviceBuilderParentName = serviceBuilderPortletName;

		if (serviceBuilderParentName.endsWith("-portlet")) {
			serviceBuilderParentName = serviceBuilderParentName.replaceAll(
				"-portlet$", "");
		}

		return serviceBuilderParentName;
	}

	protected abstract String getWebInf();

	@Override
	protected boolean isNeedWorkspace() {
		return true;
	}

	protected abstract boolean isValidModulePath(Path path);

	protected Properties loadProperties(InputStream inputStream)
		throws IOException {

		Properties properties = new Properties();

		properties.load(inputStream);

		inputStream.close();

		return properties;
	}

	protected static final Pattern dependenciesBlockPattern = Pattern.compile(
		"(.*^dependencies \\{.*)\\}", Pattern.MULTILINE | Pattern.DOTALL);
	protected static final Map<String, String> portalClasspathDependenciesMap =
		new HashMap<>();

	private Pair<String, List<Path>> _commitBuildChanges(
			Path warPath, Path repoPath, LugbotConfig lugbotConfig)
		throws GitAPIException, IOException {

		Path warFileName = warPath.getFileName();

		String message =
			"Convert project " + warFileName + " into Liferay Workspace.";

		Path addPath = repoPath.relativize(warPath);

		Optional<RevCommit> commitOptional = GitFunctions.commitChanges(
			repoPath, message, Collections.singletonList(addPath.toString()),
			lugbotConfig);

		if (!commitOptional.isPresent()) {
			return null;
		}

		RevCommit revCommit = commitOptional.get();

		ObjectId objectId = revCommit.toObjectId();

		return new Pair<>(
			objectId.getName(), Collections.singletonList(warPath));
	}

	private final void _loadMigratedDependencies(
		String resource, Map<String, GAV> migratedDependencies) {

		try (InputStream inputStream =
				UpgradeConvertModuleCheck.class.getResourceAsStream(resource)) {

			Properties properties = new Properties();

			properties.load(inputStream);

			Set<Map.Entry<Object, Object>> entries = properties.entrySet();

			entries.forEach(
				entry -> {
					String key = (String)entry.getKey();
					String value = (String)entry.getValue();

					GAV gav = null;

					if (Objects.equals("__remove__", value)) {
						gav = new GAV(key);

						gav.setRemove(true);
					}
					else {
						String[] coords = value.split(":");

						gav = new GAV(coords[0], coords[1], coords[2]);
					}

					migratedDependencies.put(key, gav);
				});
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private static final String[] _PORTLET_PLUGIN_API_DEPENDENCIES = {
		"commons-logging.jar", "log4j.jar", "util-bridges.jar", "util-java.jar",
		"util-taglib.jar"
	};

	private static final Map<String, GAV> _migratedDependencies71 =
		new HashMap<>();
	private static final Map<String, GAV> _migratedDependencies72 =
		new HashMap<>();
	private static final Map<String, GAV> _migratedDependencies73 =
		new HashMap<>();

	{
		_loadMigratedDependencies(
			"/dependencies/upgrade/migrated-dependencies-7.1.properties",
			_migratedDependencies71);
		_loadMigratedDependencies(
			"/dependencies/upgrade/migrated-dependencies-7.2.properties",
			_migratedDependencies72);
		_loadMigratedDependencies(
			"/dependencies/upgrade/migrated-dependencies-7.3.properties",
			_migratedDependencies73);
	}

}