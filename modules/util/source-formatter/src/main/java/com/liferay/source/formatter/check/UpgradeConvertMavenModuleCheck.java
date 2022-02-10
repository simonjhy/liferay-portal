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

import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import com.liferay.source.formatter.upgrade.GAV;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.UniqueDependency;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;

import aQute.libg.tuple.Pair;

/**
 * @author Simon Jiang
 */

public class UpgradeConvertMavenModuleCheck extends UpgradeConvertModuleCheck {

	@Override
	protected List<Pair<String, String>> computePossibleUpgrades(Path repoPath, LugbotConfig lugbotConfig)
			throws IOException {
		Optional<Path> originPathOptional = MavenFunctions.getOriginPath(
				repoPath, lugbotConfig);

		Path sourcePath = originPathOptional.orElse(repoPath);

		List<String> pluginNames = lugbotConfig.tasks.plugins;

		return MavenFunctions.findPlugins(sourcePath, pluginNames);
	}

	@Override
	protected List<Pair<String, String>> findPlugins(Path originPath, List<String> pluginNames) throws IOException {
		return MavenFunctions.findPlugins(originPath, pluginNames);
	}

	@Override
	protected boolean isValidModulePath(Path path) {
		return MavenFunctions.isValidMavenPath(path);
	}
	
	protected String getWebInf() {
		return _WEB_INF_PATH;
	}
	
	private static String _WEB_INF_PATH = "src/main/webapp/WEB-INF";

	protected static boolean isUnknown(GAV dep) {
		if ((dep.getGroupId() == null) || (dep.getArtifactId() == null) || (dep.getVersion() == null)) {
			return true;
		}

		return false;
	}
	
	@Override
	protected Optional<Path> converPluginProject(Path workspacePath, Path originalPath, Path pluginPath, Path modulePath,
			String type, String upgradeVersion) throws Exception {
		Model model = MavenFunctions.readPom(pluginPath);

		Set<Dependency> dependencies = model.getDependencies(
		).stream(
		).map(
			UniqueDependency::new
		).collect(
			toCollection(HashSet<Dependency>::new)
		);

		Map<String, GAV> migratedDependencies = getMigratedDependecies(upgradeVersion);

		if (!dependencies.isEmpty()) {
			for (Iterator<Dependency> iterator = dependencies.iterator(); iterator.hasNext();) {
				Dependency dep = iterator.next();

				String artifactId = dep.getArtifactId();

				GAV gav = migratedDependencies.get(artifactId + ".jar");

				if (gav != null) {
					if (gav.isRemove()) {
						iterator.remove();
					}
					else if (gav.isUnknown()) {
						if (MavenFunctions.isUnknown(dep)) {
//							_logger.warn("Dependency {} was considered unknown", dep);
							iterator.remove();
						}
						else {
//							_logger.warn("Dependency {} was considered unknown for GAV {}", dep, gav);
						}
					}
					else {
						dep.setArtifactId(gav.getArtifactId());
						dep.setGroupId(gav.getGroupId());
						dep.setVersion(gav.getVersion());
					}
				}
			}
		}

		convertPortalDependencyJarProperty(
			originalPath, pluginPath, upgradeVersion
		).forEach(
			dep -> {
				GAV gav = migratedDependencies.get(dep.getArtifactId() + ".jar");

				if (gav != null) {
					dep.setArtifactId(gav.getArtifactId());
					dep.setGroupId(gav.getGroupId());
					dep.setVersion(gav.getVersion());

				}

				dependencies.add(new UniqueDependency(dep));
			}
		);

		Set<GradleDependency> convertedGradleDependencies = dependencies.stream(
		).map(
			dep -> {
				if (MavenFunctions.isUnknown(dep) &&
					_contains(_portalClasspathDependenciesMap.keySet(), dep.getSystemPath())) {

					return new GradleDependency(_portalClasspathDependenciesMap.get(dep.getSystemPath()));
				}

				return new GradleDependency(MavenFunctions.toGradleDependency(dep));
			}
		).collect(
			toCollection(HashSet<GradleDependency>::new)
		);

		_convertWebInfLibNames(workspacePath, pluginPath, convertedGradleDependencies, upgradeVersion, false);

		if (Objects.equals(PluginsConstants.SERVICE_BUILDER_PORTLET, type)) {
			String moduleParentName = _getServiceBuilderParentName(pluginPath);

			StringBuilder sb = new StringBuilder("compileOnly project(\":modules:");

			sb.append(moduleParentName);
			sb.append(":");
			sb.append(moduleParentName + "-api");
			sb.append("\")");

			convertedGradleDependencies.add(new GradleDependency(sb.toString()));
		}

		Path buildGradlePath = modulePath.resolve("build.gradle");

		String existingContent = new String(Files.readAllBytes(buildGradlePath));

		StringBuilder dependenciesBlock = new StringBuilder();

		convertedGradleDependencies = new TreeSet<>(convertedGradleDependencies);

		convertedGradleDependencies.forEach(
			dep -> dependenciesBlock.append("\t" + dep.toString() + System.lineSeparator()));

		if (!existingContent.contains(dependenciesBlock)) {
			Matcher matcher = _dependenciesBlockPattern.matcher(existingContent);

			if (matcher.find()) {
				String newContent = matcher.group(1) + dependenciesBlock.toString() + matcher.group(2);

				Files.write(buildGradlePath, newContent.getBytes());
			}
			else if (existingContent.isBlank()) {
				String newContent = "dependencies {" + System.lineSeparator() + dependenciesBlock.toString() + "}";

				Files.write(buildGradlePath, newContent.getBytes());
			}
			else {
				String newContent =
					existingContent + System.lineSeparator() + System.lineSeparator() + "dependencies {" +
						System.lineSeparator() + dependenciesBlock.toString() + "}";

				Files.write(buildGradlePath, newContent.getBytes());
			}

			return Optional.of(modulePath);
		}

		return Optional.empty();
	}
}
