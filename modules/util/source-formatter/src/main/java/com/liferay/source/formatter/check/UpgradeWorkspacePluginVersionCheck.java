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
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.osgi.framework.Version;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.upgrade.BladeCLI;
import com.liferay.source.formatter.upgrade.GradleBuildScript;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.FileFunctions;
import com.liferay.source.formatter.upgrade.util.GradleFunctions;
import com.liferay.source.formatter.upgrade.util.WorkspaceFunctions;
import com.liferay.source.formatter.upgrade.util.YamlFunctions;
import com.liferay.source.formatter.util.SourceFormatterUtil;

/**
 * @author Simon Jiang
 */
public class UpgradeWorkspacePluginVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {
		if (!StringUtil.endsWith(fileName, "lugbot.yaml")) {
			return content;
		}

		LugbotConfig lugbotConfig = YamlFunctions.load(content);

		String baseDirValue = getBaseDirName();

		Path repoPath = Paths.get(baseDirValue);

		Optional<Path> workspacePathOptional = Optional.of(
			lugbotConfig
		).map(
			config -> {
				if (Objects.nonNull(config.tasks.workspacePath)) {
					Path originalWorkspacePath = repoPath.resolve(
						config.tasks.workspacePath);

					return originalWorkspacePath.normalize();
				}

				return repoPath;
			}
		).filter(
			WorkspaceFunctions::isWorkspacePath
		);

		if (!workspacePathOptional.isPresent()) {
			SourceFormatterUtil.printError(
				repoPath.toString(), "is not valid lifeay workspace project");
		}
		
		try {
			Optional<GradleDependency> latestWorkspacePluginDependencyOptional = _getLatestWorkspacePluginDependency(lugbotConfig);
			
			if (!latestWorkspacePluginDependencyOptional.isPresent()) {
				MessageFormat.format(
					"Error finding latest workspace plugin version for target workspace {0}",
					lugbotConfig.tasks.upgradeVersion);
				
				return content;
			}

			GradleDependency latestWorkspacePluginDependency = latestWorkspacePluginDependencyOptional.get();
			
			Optional<GradleDependency> repoWorkspacePluginDependency = GradleFunctions.getWorkspacePluginDependency(
				workspacePathOptional.get());
			
			if (!repoWorkspacePluginDependency.isPresent()) {
				MessageFormat.format(
					"Error finding needed workspace plugin version in a generated workspace {0}",
					workspacePathOptional.get());
				
				return content;
			}

			 Optional<List<Path>> upgradedWorkspacePluinPaths = repoWorkspacePluginDependency.map(
				gradleDependency -> {
					Version version = Version.emptyVersion;

					Version latestVersion = Version.parseVersion(latestWorkspacePluginDependency.getVersion());

					Version workspaceVersion = Version.parseVersion(gradleDependency.getVersion());

					if (latestVersion.compareTo(workspaceVersion) > 0) {
						version = latestVersion;
					}

					return version;
				}
			).filter(
				version -> !Objects.equals(version, Version.emptyVersion)
			).map(
				workspaceVersion -> {
					return _branchUpdateWorkspacePlugin(workspacePathOptional.get(), workspaceVersion);
				}
			);

			if (!upgradedWorkspacePluinPaths.isPresent()) {
				SourceFormatterUtil.printError(
					null,
					MessageFormat.format(
						"Error finding older workspace plugin version to upgrade in a new blade generated workspace {0}",
						workspacePathOptional.get()));				
			}
			
		}
		catch (Exception exception) {
			SourceFormatterUtil.printError(
					null,
					MessageFormat.format(
						"Failed to execute upgrade workspace plugin check for {0}",
						workspacePathOptional.get()));	
		}

		return content;
	}
	
	private List<Path> _branchUpdateWorkspacePlugin(Path workspacePath, Version newVersion) {
		List<Path> modifiedPaths = new ArrayList<>();

		try {
			Path settingsGradlePath = workspacePath.resolve("settings.gradle");

			GradleBuildScript settingsGradleBuildScript = new GradleBuildScript(settingsGradlePath);

			Optional<GradleDependency> workspacePluginDependency = GradleFunctions.getWorkspacePluginDependency(workspacePath);

			workspacePluginDependency.ifPresent(
				gradleDependency -> {
					try {
						settingsGradleBuildScript.modifyDependencyVersion(
							gradleDependency,
							new GradleDependency(
								"classpath", "com.liferay", "com.liferay.gradle.plugins.workspace",
								newVersion.toString(), -1, -1));

						modifiedPaths.add(settingsGradlePath.toAbsolutePath());
					}
					catch (IOException e) {
					}
				});
		}
		catch (IOException e) {
		}

		return modifiedPaths;
	}
		
	private Optional<GradleDependency> _getLatestWorkspacePluginDependency(LugbotConfig lugbotConfig) {
		Path tempPath = null;

		try {
			tempPath = Files.createTempDirectory("blade-ws");

			Path wsPath = tempPath.resolve("ws");

			final List<String> args = new ArrayList<>();

			Collections.addAll(args, "init", "-v", lugbotConfig.tasks.upgradeVersion, "ws");

			String initTempWorkspaceCommand = StringUtil.merge(args, " ");
			
			BladeCLI.executeWithLatestBlade(initTempWorkspaceCommand);
			
			return GradleFunctions.getWorkspacePluginDependency(wsPath);
		}
		catch (Exception exception) {
			SourceFormatterUtil.printError(
				null,"Error finding workspace plugin version in a new blade generated workspace");
		}
		finally {
			if (tempPath != null) {
				try {
					FileFunctions.deleteDir(tempPath);
				}
				catch (IOException e) {
					SourceFormatterUtil.printError(
						null,
						MessageFormat.format(
							"Error finding workspace plugin version in a new blade generated workspace {0}",
							tempPath));
				}
			}
		}

		return Optional.empty();
	}

}