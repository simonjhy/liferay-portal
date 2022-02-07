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

package com.liferay.source.formatter.upgrade.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.IOException;
import java.io.StringReader;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Seiphon Wang
 * @author Simon Jiang
 */
public class WorkspaceFunctions {

	public static String getGradleProperty(
			Path projectPath, String key, String defaultValue)
		throws Exception {

		Path gradleProperties = projectPath.resolve("gradle.properties");

		String retVal = null;

		if (Files.exists(gradleProperties)) {
			Properties properties = new Properties();

			properties.load(
				new StringReader(
					FileUtil.read(gradleProperties.toFile(), false)));

			retVal = properties.getProperty(key, defaultValue);
		}

		return retVal;
	}

	public static List<Path> getPossibleFolders(Path path) throws Exception {
		Path[] possibleFolders = {
			path.resolve(getWorkspaceModulesDir(path)),
			path.resolve(getWorkspaceThemesDir(path)),
			path.resolve(getWorkspaceWarsDir(path)),
			path.resolve(getWorkspaceExtDir(path))
		};

		return ListUtil.fromArray(possibleFolders);
	}

	public static List<Path> getPossiblePluginPath(Path pluginsSDKPath)
		throws IOException {

		Path hooksPath = pluginsSDKPath.resolve("hooks");
		Path layouttplPath = pluginsSDKPath.resolve("layouttpl");
		Path portletsPath = pluginsSDKPath.resolve("portlets");
		Path themesPath = pluginsSDKPath.resolve("themes");
		Path websPath = pluginsSDKPath.resolve("webs");

		Predicate<Path> containsDocroot = path -> {
			if (Files.isDirectory(path)) {
				Path docroot = path.resolve("docroot");

				if (Files.exists(docroot)) {
					return true;
				}
			}

			return false;
		};

		Predicate<Path> containsServiceBuilder = path -> {
			if (Files.isDirectory(path)) {
				Path docroot = path.resolve("docroot");

				if (Files.isDirectory(docroot) &&
					Files.exists(path.resolve("docroot/WEB-INF/service.xml"))) {

					return true;
				}
			}

			return false;
		};

		List<Path> possiblePluginPaths = new ArrayList<>();

		try (Stream<Path> hookPaths = Files.list(hooksPath)) {
			hookPaths.filter(
				containsDocroot
			).collect(
				Collectors.toList()
			).forEach(
				possiblePluginPaths::add
			);
		}

		try (Stream<Path> layouttplPaths = Files.list(layouttplPath)) {
			layouttplPaths.filter(
				containsDocroot
			).collect(
				Collectors.toList()
			).forEach(
				possiblePluginPaths::add
			);
		}

		List<Path> serviceBuilderPaths = new ArrayList<>();

		try (Stream<Path> portletPaths = Files.list(portletsPath)) {
			portletPaths.filter(
				containsServiceBuilder
			).collect(
				Collectors.toList()
			).forEach(
				serviceBuilderPaths::add
			);
		}

		try (Stream<Path> portletPaths = Files.list(portletsPath)) {
			portletPaths.filter(
				containsDocroot
			).filter(
				path -> !serviceBuilderPaths.contains(path)
			).collect(
				Collectors.toList()
			).forEach(
				possiblePluginPaths::add
			);
		}

		serviceBuilderPaths.forEach(possiblePluginPaths::add);

		try (Stream<Path> themePaths = Files.list(themesPath)) {
			themePaths.filter(
				containsDocroot
			).collect(
				Collectors.toList()
			).forEach(
				possiblePluginPaths::add
			);
		}

		try (Stream<Path> webPaths = Files.list(websPath)) {
			webPaths.filter(
				containsDocroot
			).collect(
				Collectors.toList()
			).forEach(
				possiblePluginPaths::add
			);
		}

		return possiblePluginPaths;
	}

	public static String getWorkspaceExtDir(Path path) throws Exception {
		if (Files.exists(path)) {
			return getGradleProperty(path, "liferay.workspace.ext.dir", "ext");
		}

		return "ext";
	}

	public static String getWorkspaceModulesDir(Path path) throws Exception {
		if (Files.exists(path)) {
			return getGradleProperty(
				path, "liferay.workspace.modules.dir", "modules");
		}

		return "modules";
	}

	public static String getWorkspaceThemesDir(Path path) throws Exception {
		if (Files.exists(path)) {
			return getGradleProperty(
				path, "liferay.workspace.themes.dir", "themes");
		}

		return "themes";
	}

	public static String getWorkspaceWarsDir(Path path) throws Exception {
		if (Files.exists(path)) {
			return getGradleProperty(
				path, "liferay.workspace.wars.dir", "wars");
		}

		return "wars";
	}

	public static boolean isValidPluginsSDKPath(Path path) {
		return _checkPath(
			path,
			p -> {
				if (Files.exists(path)) {
					Path buildProperties = path.resolve("build.properties");
					Path portletsBuildXml = path.resolve("portlets/build.xml");
					Path hooksBuildXml = path.resolve("hooks/build.xml");
					Path themesBuildXml = path.resolve("themes/build.xml");

					if (Files.exists(buildProperties) &&
						Files.exists(portletsBuildXml) &&
						Files.exists(hooksBuildXml) &&
						Files.exists(themesBuildXml)) {

						return true;
					}
				}

				return false;
			});
	}

	public static boolean isWorkspacePath(Path path) {
		return _checkPath(
			path,
			p -> {
				Path settingsGradlePath = p.resolve("settings.gradle");

				if (!Files.exists(settingsGradlePath)) {
					return false;
				}

				String settingsGradle = null;

				try {
					settingsGradle = new String(
						Files.readAllBytes(settingsGradlePath));
				}
				catch (IOException ioException) {
				}

				Matcher matcher = _workspacePluginPattern.matcher(
					settingsGradle);

				return matcher.matches();
			});
	}

	private static boolean _checkPath(Path path, Predicate<Path> predicate) {
		return predicate.test(path);
	}

	private static final Pattern _workspacePluginPattern = Pattern.compile(
		".*apply\\s*plugin\\s*:\\s*[\'\"]com\\.liferay\\.workspace[\'\"]\\s*$",
		Pattern.MULTILINE | Pattern.DOTALL);

}