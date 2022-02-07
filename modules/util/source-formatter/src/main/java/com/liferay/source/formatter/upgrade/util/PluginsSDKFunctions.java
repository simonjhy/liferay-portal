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

import aQute.libg.tuple.Pair;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class PluginsSDKFunctions {

	public static List<Pair<String, String>> findPlugins(
			Path pluginsSDKPath, List<String> plugins)
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

		List<Pair<String, String>> possiblePluginPaths = new ArrayList<>();

		Predicate<? super Path> matchesPlugins = path -> plugins.contains(
			String.valueOf(path.getFileName()));

		Predicate<Path> combinedFilter = containsDocroot.and(matchesPlugins);

		searchPlugins(
			pluginsSDKPath, hooksPath, combinedFilter, PluginsUtils.HOOK,
			possiblePluginPaths);
		searchPlugins(
			pluginsSDKPath, layouttplPath, combinedFilter,
			PluginsUtils.LAYOUTTPL, possiblePluginPaths);

		List<Path> serviceBuilderPaths = new ArrayList<>();

		try (Stream<Path> porletPaths = Files.list(portletsPath)) {
			porletPaths.filter(
				containsServiceBuilder
			).collect(
				Collectors.toList()
			).forEach(
				serviceBuilderPaths::add
			);
		}

		searchPlugins(
			pluginsSDKPath, portletsPath,
			combinedFilter.and(path -> !serviceBuilderPaths.contains(path)),
			PluginsUtils.PORTLET, possiblePluginPaths);

		searchPlugins(
			pluginsSDKPath, portletsPath,
			containsServiceBuilder.and(matchesPlugins),
			PluginsUtils.SERVICE_BUILDER_PORTLET, possiblePluginPaths);
		searchPlugins(
			pluginsSDKPath, themesPath, combinedFilter, PluginsUtils.THEME,
			possiblePluginPaths);
		searchPlugins(
			pluginsSDKPath, websPath, combinedFilter, PluginsUtils.WEB,
			possiblePluginPaths);

		return possiblePluginPaths;
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

	public static boolean isValidSDKLocation(String locaction) {
		boolean retval = false;

		try {
			File sdkDir = new File(locaction);

			File buildProperties = new File(sdkDir, "build.properties");
			File portletsBuildXml = new File(sdkDir, "portlets/build.xml");
			File hooksBuildXml = new File(sdkDir, "hooks/build.xml");

			retval =
				buildProperties.exists() && portletsBuildXml.exists() &&
				hooksBuildXml.exists();
		}
		catch (Exception exception) {
		}

		return retval;
	}

	public static void searchPlugins(
			Path pluginsSDKPath, Path searchPath, Predicate<Path> pathFilter,
			String type, List<Pair<String, String>> possiblePluginPaths)
		throws IOException {

		try (Stream<Path> searchPaths = Files.list(searchPath)) {
			searchPaths.filter(
				pathFilter
			).map(
				path -> pluginsSDKPath.relativize(path)
			).map(
				Path::toString
			).map(
				path -> new Pair<>(path, type)
			).forEach(
				possiblePluginPaths::add
			);
		}
	}

}