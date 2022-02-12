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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.liferay.source.formatter.upgrade.GAV;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;
import com.liferay.source.formatter.upgrade.util.PluginsSDKFunctions;
import com.liferay.source.formatter.upgrade.util.PluginsUtils;
import com.liferay.source.formatter.upgrade.util.WorkspaceFunctions;

import aQute.libg.tuple.Pair;

/**
 * @author Simon Jiang
 */

public class UpgradeConvertPluginModuleCheck extends UpgradeConvertModuleCheck {

	@Override
	public List<Pair<String, String>> computePossibleUpgrades(
			Path repoPath, LugbotConfig lugbotConfig)
		throws IOException {

		Optional<Path> originPathOptional = Optional.ofNullable(
			lugbotConfig.tasks
		).map(
			tasks -> tasks.pluginsSDKPath
		).map(
			repoPath::resolve
		).filter(
			path -> MavenFunctions.isValidMavenPath(path)
		);

		if (originPathOptional.isPresent()) {
			List<String> pluginNames = lugbotConfig.tasks.plugins;

			return PluginsSDKFunctions.findPlugins(
				originPathOptional.get(), pluginNames);
		}

		return Collections.emptyList();
	}

	@Override
	protected List<Pair<String, String>> findPlugins(Path originPath, List<String> pluginNames) throws IOException {
		return PluginsSDKFunctions.findPlugins(originPath, pluginNames);
	}

	@Override
	protected boolean isValidModulePath(Path path) {
		return WorkspaceFunctions.isValidPluginsSDKPath(path);
	}

	protected Optional<Path> converPluginProject(Path workspacePath, Path reportPath, Path pluginPath, String pluginName, Path modulePath, String type,
			String upgradeVersion)
		throws Exception {
		List<GAV> convertedGavs = new CopyOnWriteArrayList<>();

		Path ivyPath = pluginPath.resolve("ivy.xml");

		if (Files.exists(ivyPath)) {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(ivyPath.toFile());

			Element documentElement = doc.getDocumentElement();

			documentElement.normalize();

			NodeList depElements = documentElement.getElementsByTagName("dependency");

			if ((depElements != null) && (depElements.getLength() > 0)) {
				Map<String, GAV> migratedDependencies = getMigratedDependecies(upgradeVersion);

				Set<String> migratedKeys = migratedDependencies.keySet();

				for (int i = 0; i < depElements.getLength(); i++) {
					Node depElement = depElements.item(i);

					String name = getAttr(depElement, "name");
					String org = getAttr(depElement, "org");
					String rev = getAttr(depElement, "rev");

					boolean removedGav = false;

					if ((name != null) &&
						migratedKeys.stream(
						).filter(
							key -> name.equals(key.replaceAll("\\.jar$", ""))
						).map(
							key -> migratedDependencies.get(key)
						).filter(
							GAV::isRemove
						).findFirst(
						).isPresent()) {

						removedGav = true;
					}

					if ((name != null) && (org != null) && (rev != null) && !removedGav) {
						GAV gav = new GAV(org, name, rev);

						convertedGavs.add(gav);
					}
				}
			}
		}

		convertedGavs.addAll(convertPortalDependencyJarProperty(reportPath, pluginPath, upgradeVersion));

		Set<GradleDependency> convertedGradleDependencies = convertedGavs.stream(
		).map(
			gav -> {
				if (gav.isUnknown() && contains(_portalClasspathDependenciesMap.keySet(), gav.getJarName())) {
					return new GradleDependency(_portalClasspathDependenciesMap.get(gav.getJarName()));
				}

				return new GradleDependency(gav.toCompileDependency());
			}
		).collect(
			Collectors.toSet()
		);

		convertWebInfLibNames(
			workspacePath.resolve("libs"), pluginPath, convertedGradleDependencies, upgradeVersion, false);

		if (Objects.equals(PluginsUtils.SERVICE_BUILDER_PORTLET, type)) {
			String moduleParentName = getServiceBuilderParentName(String.valueOf(pluginPath.getFileName()));

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

		convertedGradleDependencies.forEach(
			dep -> dependenciesBlock.append("\t" + dep.toString() + System.lineSeparator()));

		dependenciesBlock.append(System.lineSeparator());
		dependenciesBlock.append("}");

		Matcher matcher = dependenciesBlockPattern.matcher(existingContent);

		if (matcher.find()) {
			String newContent = matcher.group(1) + dependenciesBlock.toString();

			Files.write(buildGradlePath, newContent.getBytes());
		}

		return Optional.of(modulePath);
	}
	
	
	protected String getWebInf() {
		return _WEB_INF_PATH;
	}
	
	private static String _WEB_INF_PATH = "docroot/WEB-INF/";
	
}
