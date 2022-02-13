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
import com.liferay.source.formatter.upgrade.UniqueDependency;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;
import com.liferay.source.formatter.upgrade.util.PluginsUtils;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

/**
 * @author Simon Jiang
 */
public class UpgradeConvertMavenModuleCheck extends UpgradeConvertModuleCheck {

	protected static boolean isUnknown(GAV dep) {
		if ((dep.getGroupId() == null) || (dep.getArtifactId() == null) ||
			(dep.getVersion() == null)) {

			return true;
		}

		return false;
	}

	@Override
	protected List<Pair<String, String>> computePossibleUpgrades(
			Path repoPath, LugbotConfig lugbotConfig)
		throws IOException {

		Optional<Path> originPathOptional = MavenFunctions.getOriginPath(
			repoPath, lugbotConfig);

		Path sourcePath = originPathOptional.orElse(repoPath);

		List<String> pluginNames = lugbotConfig.tasks.plugins;

		return MavenFunctions.findPlugins(sourcePath, pluginNames);
	}

	@Override
	protected Optional<Path> converPluginProject(
			Path workspacePath, Path originalPath, Path pluginPath,
			String pluginName, Path modulePath, String type,
			String upgradeVersion)
		throws Exception {

		Model model = MavenFunctions.readPom(pluginPath);

		Set<Dependency> dependencies = model.getDependencies(
		).stream(
		).map(
			UniqueDependency::new
		).collect(
			Collectors.toCollection(HashSet<Dependency>::new)
		);

		Map<String, GAV> migratedDependencies = getMigratedDependencies(
			upgradeVersion);

		if (!dependencies.isEmpty()) {
			for (Iterator<Dependency> iterator = dependencies.iterator();
				 iterator.hasNext();) {

				Dependency dep = iterator.next();

				String artifactId = dep.getArtifactId();

				GAV gav = migratedDependencies.get(artifactId + ".jar");

				if (gav != null) {
					if (gav.isRemove()) {
						iterator.remove();
					}
					else if (gav.isUnknown()) {
						if (MavenFunctions.isUnknown(dep)) {
							//Dependency {} was considered unknown", dep);
							iterator.remove();
						}
						else {
							//Dependency {} was considered unknown for GAV {});
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

		List<GAV> convertPortalDependencyJarToGavs =
			convertPortalDependencyJarProperty(
				originalPath, pluginPath, upgradeVersion);

		Stream<GAV> gavStream = convertPortalDependencyJarToGavs.stream();

		gavStream.forEach(
			dep -> {
				GAV gav = migratedDependencies.get(
					dep.getArtifactId() + ".jar");
				Dependency dependency = new Dependency();

				if (gav != null) {
					dependency.setArtifactId(gav.getArtifactId());
					dependency.setGroupId(gav.getGroupId());
					dependency.setVersion(gav.getVersion());
					dependency.setSystemPath(gav.getJarName());

					dependencies.add(new UniqueDependency(dependency));
				}

				dependencies.add(new UniqueDependency(dependency));
			});

		Set<GradleDependency> convertedGradleDependencies = dependencies.stream(
		).map(
			dep -> {
				if (MavenFunctions.isUnknown(dep) &&
					_contains(
						portalClasspathDependenciesMap.keySet(),
						dep.getSystemPath())) {

					return new GradleDependency(
						portalClasspathDependenciesMap.get(
							dep.getSystemPath()));
				}

				return new GradleDependency(
					MavenFunctions.toGradleDependency(dep));
			}
		).collect(
			Collectors.toCollection(HashSet<GradleDependency>::new)
		);

		convertWebInfLibNames(
			workspacePath, pluginPath, convertedGradleDependencies,
			upgradeVersion, false);

		if (Objects.equals(PluginsUtils.SERVICE_BUILDER_PORTLET, type)) {
			String moduleParentName = getServiceBuilderParentName(pluginName);

			StringBuilder sb = new StringBuilder(
				"compileOnly project(\":modules:");

			sb.append(moduleParentName);
			sb.append(":");
			sb.append(moduleParentName + "-api");
			sb.append("\")");

			convertedGradleDependencies.add(
				new GradleDependency(sb.toString()));
		}

		Path buildGradlePath = modulePath.resolve("build.gradle");

		String existingContent = new String(
			Files.readAllBytes(buildGradlePath));

		StringBuilder dependenciesBlock = new StringBuilder();

		convertedGradleDependencies.forEach(
			dep -> dependenciesBlock.append(
				"\t" + dep.toString() + System.lineSeparator()));

		dependenciesBlock.append(System.lineSeparator());
		dependenciesBlock.append("}");

		Matcher matcher = dependenciesBlockPattern.matcher(existingContent);

		if (matcher.find()) {
			String newContent = matcher.group(1) + dependenciesBlock.toString();

			Files.write(buildGradlePath, newContent.getBytes());
		}

		return Optional.of(modulePath);
	}

	@Override
	protected List<Pair<String, String>> findPlugins(
			Path originPath, List<String> pluginNames)
		throws IOException {

		return MavenFunctions.findPlugins(originPath, pluginNames);
	}

	protected String getWebInf() {
		return _WEB_INF_PATH;
	}

	@Override
	protected boolean isValidModulePath(Path path) {
		return MavenFunctions.isValidMavenPath(path);
	}

	private boolean _contains(Collection<?> collections, Object object) {
		if ((collections == null) || (object == null)) {
			return false;
		}

		return collections.contains(object);
	}

	private static final String _WEB_INF_PATH = "src/main/webapp/WEB-INF";

}