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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
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

	private void _analysisModules(Path path) {
		File pluginFile = path.toFile();

		List<String> javaFiles = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0],
			new String[] {"**/" + pluginFile.getName() + "/**.java"},
			new SourceFormatterExcludes(), false);

		List<String> jspFiles = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0],
			new String[] {"**/" + pluginFile.getName() + "/**.jsp"},
			new SourceFormatterExcludes(), false);

		List<String> propertiesFiles = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0],
			new String[] {"**/" + pluginFile.getName() + "/**.properties"},
			new SourceFormatterExcludes(), false);

		List<String> xmlFiles = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0],
			new String[] {"**/" + pluginFile.getName() + "/**.xml"},
			new SourceFormatterExcludes(), false);

		SourceFormatterUtil.printError(
			path.toString(), "Project Name: [" + pluginFile.getName() + "]");

		SourceFormatterUtil.printError(
			path.toString(), "\tFound (" + javaFiles.size() + ") Java Files.");
		SourceFormatterUtil.printError(
			path.toString(), "\tFound (" + jspFiles.size() + ") JSP Files.");
		SourceFormatterUtil.printError(
			path.toString(),
			"\tFound (" + propertiesFiles.size() + ") Properties Files.");

		if (!propertiesFiles.isEmpty()) {
			Stream<String> propertiesStream = propertiesFiles.stream();

			long portalPropertiesCount = propertiesStream.map(
				p -> new File(p)
			).map(
				f -> f.getName()
			).filter(
				n -> n.startsWith("portal")
			).count();

			if (portalPropertiesCount > 0) {
				SourceFormatterUtil.printError(
					path.toString(),
					"\t\t-Include (" + portalPropertiesCount +
						") Portal Properties Files.");
			}

			propertiesStream = propertiesFiles.stream();

			long languagePropertiesCount = propertiesStream.map(
				p -> new File(p)
			).map(
				f -> f.getName()
			).filter(
				n -> n.startsWith("Language")
			).count();

			if (languagePropertiesCount > 0) {
				SourceFormatterUtil.printError(
					path.toString(),
					"\t\t-Include (" + languagePropertiesCount +
						") Language Properties Files.");
			}
		}

		SourceFormatterUtil.printError(
			path.toString(), "\tFound (" + xmlFiles.size() + ") XML Files.");

		if (!xmlFiles.isEmpty()) {
			Stream<String> xmlFilesStream = xmlFiles.stream();

			long serviceXmlCount = xmlFilesStream.map(
				p -> new File(p)
			).map(
				f -> f.getName()
			).filter(
				n -> n.equals("service.xml")
			).count();

			if (serviceXmlCount > 0) {
				SourceFormatterUtil.printError(
					path.toString(),
					"\t\t-Include (" + serviceXmlCount +
						") Service XML Files.");
			}
		}
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
				Path modulesPath = dirPath.resolve("modules");
				Path themesPath = dirPath.resolve("themes");

				List<Path> modulesList = new ArrayList<>();
				List<Path> themesList = new ArrayList<>();

				if (Objects.nonNull(modulesPath) && Files.exists(modulesPath)) {
					File modulesDir = modulesPath.toFile();

					modulesList = Stream.of(
						modulesDir.listFiles(
							new FileFilter() {

								@Override
								public boolean accept(File file) {
									return file.isDirectory();
								}

							})
					).map(
						file -> file.toPath()
					).collect(
						Collectors.toList()
					);
				}

				if (Objects.nonNull(themesPath) && Files.exists(themesPath)) {
					File themeDir = themesPath.toFile();

					themesList = Stream.of(
						themeDir.listFiles(
							new FileFilter() {

								@Override
								public boolean accept(File file) {
									return file.isDirectory();
								}

							})
					).map(
						file -> file.toPath()
					).collect(
						Collectors.toList()
					);
				}

				List<Path> projectPaths = new ArrayList<>();

				projectPaths.addAll(modulesList);
				projectPaths.addAll(themesList);

				SourceFormatterUtil.printError(
					baseDirName,
					"Found (" + projectPaths.size() +
						") modules in Liferay Workspace can be upgrade.");

				Stream<Path> projectPathsStream = projectPaths.stream();

				projectPathsStream.forEach(path -> _analysisModules(path));
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

	private List<String> _allFileNames;

}