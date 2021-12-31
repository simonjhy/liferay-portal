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

package com.liferay.source.formatter.checks;

import com.google.common.base.Strings;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;
import com.liferay.source.formatter.upgrade.util.WorkspaceFunctions;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.StringReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author Seiphon Wang
 */
public class AnalysisReportCheck extends BaseFileCheck {

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		String baseDir = (String)properties.get("base.dir");

		if (Strings.isNullOrEmpty(baseDir)) {
			baseDir = getBaseDirName();
		}

		_generateAnalysisReport(baseDir);

		return content;
	}

	private void _analysisFiles(
		List<String> fileTypes, String includes, String path) {

		for (String fileType : fileTypes) {
			List<String> files = SourceFormatterUtil.filterFileNames(
				_allFileNames, new String[0],
				new String[] {includes + fileType},
				new SourceFormatterExcludes(), false);

			if (!files.isEmpty()) {
				SourceFormatterUtil.printError(
					path,
					StringBundler.concat(
						"\tFound (", String.valueOf(files.size()), ") ",
						fileType, " files."));

				if (isAttributeValue(_SHOW_FILE_DETAILS_ATTRIBUTE_KEY, path)) {
					for (String file : files) {
						SourceFormatterUtil.printError(
							path, "\t\tFound " + file);
					}
				}
			}
		}
	}

	private void _analysisModules(Path path) {
		File pluginFile = path.toFile();

		SourceFormatterUtil.printError(
			path.toString(), "Project Name: [" + pluginFile.getName() + "]");

		List<String> fileTypes = getAttributeValues(
			_FILE_TYPES__KEY, path.toString());

		File parentFile = pluginFile.getParentFile();

		_analysisFiles(
			fileTypes,
			StringBundler.concat(
				"**/", parentFile.getName(), "/", pluginFile.getName(), "/**"),
			path.toString());
	}

	private void _generateAnalysisReport(String baseDirName) throws Exception {
		Path dirPath = Paths.get(baseDirName);

		try {
			if (WorkspaceFunctions.isValidPluginsSDKPath(dirPath)) {
				List<Path> possiblePluginPaths =
					WorkspaceFunctions.getPossiblePluginPath(dirPath);

				SourceFormatterUtil.printError(
					baseDirName,
					"Found (" + possiblePluginPaths.size() +
						") plugins can be upgrade.");

				Stream<Path> pluginStream = possiblePluginPaths.stream();

				pluginStream.forEach(
					pluginPath -> _analysisModules(pluginPath));
			}
			else if (WorkspaceFunctions.isWorkspacePath(dirPath)) {
				List<Path> possibleFolderPaths =
					WorkspaceFunctions.getPossibleFolders(dirPath);

				Stream<Path> possibleFoldersStream =
					possibleFolderPaths.stream();

				possibleFoldersStream.filter(
					path -> Objects.nonNull(path)
				).filter(
					path -> Files.exists(path)
				).map(
					path -> path.toFile()
				).map(
					file -> file.listFiles(
						new FileFilter() {

							@Override
							public boolean accept(File file) {
								return file.isDirectory();
							}

						})
				).filter(
					files -> files.length > 0
				).flatMap(
					Stream::of
				).map(
					file -> file.toPath()
				).forEach(
					path -> _analysisModules(path)
				);
			}
			else {
				List<Path> possibleMavenPlugins =
					MavenFunctions.getPossibleMavenPluginPath(dirPath);

				if (!possibleMavenPlugins.isEmpty()) {
					SourceFormatterUtil.printError(
						baseDirName,
						"\tFound (" + possibleMavenPlugins.size() +
							") maven plugins can be upgrade.");

					Stream<Path> mavenPluginsStream =
						possibleMavenPlugins.stream();

					mavenPluginsStream.forEach(
						pluginPath -> _analysisModules(pluginPath));
				}
				else {
					_analysisModules(dirPath);
				}
			}
		}
		catch (Exception exception) {
		}
	}

	private static final String _FILE_TYPES__KEY = "fileTypes";

	private static final String _SHOW_FILE_DETAILS_ATTRIBUTE_KEY =
		"showDetails";

	private List<String> _allFileNames;

}