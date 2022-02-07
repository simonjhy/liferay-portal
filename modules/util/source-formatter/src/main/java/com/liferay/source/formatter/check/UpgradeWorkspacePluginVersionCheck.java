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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.upgrade.BladeCLI;
import com.liferay.source.formatter.upgrade.GradleBuildScript;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.FileFunctions;
import com.liferay.source.formatter.upgrade.util.GradleFunctions;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.osgi.framework.Version;

/**
 * @author Simon Jiang
 */
public class UpgradeWorkspacePluginVersionCheck extends UpgradeAbstractCheck {

	@Override
	protected void doUpgrade(
			Path repoPath, LugbotConfig lugbotConfig, Path workspacePath)
		throws Exception {

		try {
			Optional<GradleDependency> latestWorkspacePluginDependencyOptional =
				_getLatestWorkspacePluginDependency(lugbotConfig);

			if (!latestWorkspacePluginDependencyOptional.isPresent()) {
				MessageFormat.format(
					"Error finding latest workspace plugin version for target" +
						" workspace {0}",
					lugbotConfig.tasks.upgradeVersion);

				return;
			}

			Optional<GradleDependency> repoWorkspacePluginDependencyOptional =
				GradleFunctions.getWorkspacePluginDependency(workspacePath);

			if (!repoWorkspacePluginDependencyOptional.isPresent()) {
				MessageFormat.format(
					"Error finding needed workspace plugin version in a " +
						"generated workspace {0}",
					workspacePath);

				return;
			}

			GradleDependency latestWorkspacePluginDependency =
				latestWorkspacePluginDependencyOptional.get();

			Optional<List<Path>> upgradedWorkspacePluinPathsOptional =
				repoWorkspacePluginDependencyOptional.map(
					gradleDependency -> {
						Version version = Version.emptyVersion;

						Version latestVersion = Version.parseVersion(
							latestWorkspacePluginDependency.getVersion());

						Version workspaceVersion = Version.parseVersion(
							gradleDependency.getVersion());

						if (latestVersion.compareTo(workspaceVersion) > 0) {
							version = latestVersion;
						}

						return version;
					}
				).filter(
					version -> !Objects.equals(version, Version.emptyVersion)
				).map(
					workspaceVersion -> _branchUpdateWorkspacePlugin(
						workspacePath, workspaceVersion)
				);

			if (!upgradedWorkspacePluinPathsOptional.isPresent()) {
				SourceFormatterUtil.printError(
					null,
					MessageFormat.format(
						"Not find workspace plugin version need to upgrade in" +
							" a generated workspace {0}",
						workspacePath));
			}
		}
		catch (Exception exception) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"Failed to execute upgrade workspace plugin check for {0}",
					workspacePath));
		}
	}

	@Override
	protected boolean isNeedWorkspace() {
		return true;
	}

	private List<Path> _branchUpdateWorkspacePlugin(
		Path workspacePath, Version newVersion) {

		List<Path> modifiedPaths = new ArrayList<>();

		try {
			Path settingsGradlePath = workspacePath.resolve("settings.gradle");

			GradleBuildScript settingsGradleBuildScript = new GradleBuildScript(
				settingsGradlePath);

			Optional<GradleDependency> workspacePluginDependencyOptional =
				GradleFunctions.getWorkspacePluginDependency(workspacePath);

			workspacePluginDependencyOptional.ifPresent(
				gradleDependency -> {
					try {
						settingsGradleBuildScript.modifyDependencyVersion(
							gradleDependency,
							new GradleDependency(
								"classpath", "com.liferay",
								"com.liferay.gradle.plugins.workspace",
								newVersion.toString(), -1, -1));

						modifiedPaths.add(settingsGradlePath.toAbsolutePath());
					}
					catch (IOException ioException) {
					}
				});
		}
		catch (IOException ioException) {
		}

		return modifiedPaths;
	}

	private Optional<GradleDependency> _getLatestWorkspacePluginDependency(
		LugbotConfig lugbotConfig) {

		Path tempPath = null;

		try {
			tempPath = Files.createTempDirectory("blade-ws");

			Path wsPath = tempPath.resolve("ws");

			List<String> args = new ArrayList<>();

			Collections.addAll(
				args, "init", "-v", lugbotConfig.tasks.upgradeVersion, "ws");

			String initTempWorkspaceCommand = StringUtil.merge(args, " ");

			BladeCLI.executeWithLatestBlade(initTempWorkspaceCommand);

			return GradleFunctions.getWorkspacePluginDependency(wsPath);
		}
		catch (Exception exception) {
			SourceFormatterUtil.printError(
				null,
				"Error finding workspace plugin version in a new blade " +
					"generated workspace");
		}
		finally {
			if (tempPath != null) {
				try {
					FileFunctions.deleteDir(tempPath);
				}
				catch (IOException ioException) {
					SourceFormatterUtil.printError(
						null,
						MessageFormat.format(
							"Error finding workspace plugin version in a new " +
								"blade generated workspace {0}",
							tempPath));
				}
			}
		}

		return Optional.empty();
	}

}