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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Seiphon Wang
 */
public class MavenFunctions {

	public static List<Path> getPossibleMavenPluginPath(
			Path mavenPluginParentPath)
		throws IOException {

		File mavenPluginParentDir = mavenPluginParentPath.toFile();

		File[] mavenPlugins = mavenPluginParentDir.listFiles(
			new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (dir.isDirectory()) {
						return true;
					}

					return false;
				}

			});

		return Stream.of(
			mavenPlugins
		).map(
			dir -> dir.toPath()
		).filter(
			dir -> Files.exists(dir.resolve("pom.xml"))
		).collect(
			Collectors.toList()
		);
	}

	public static boolean isValidMavenPath(Path path) {
		if (Files.exists(path)) {
			Path pomXml = path.resolve("pom.xml");

			if (Files.exists(pomXml)) {
				return true;
			}
		}

		return false;
	}

}