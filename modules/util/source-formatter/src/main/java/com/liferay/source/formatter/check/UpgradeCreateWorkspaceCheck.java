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
import com.liferay.source.formatter.upgrade.BladeCLIException;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.GradleFunctions;
import com.liferay.source.formatter.upgrade.util.WorkspaceFunctions;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.nio.file.Files;
import java.nio.file.Path;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Simon Jiang
 */
public class UpgradeCreateWorkspaceCheck extends UpgradeAbstractCheck {

	@Override
	protected void doUpgrade(
			Path repoPath, LugbotConfig lugbotConfig, Path workspacePath)
		throws Exception {

		Files.createDirectories(workspacePath);

		String upgradeVersion = Optional.ofNullable(
			lugbotConfig.tasks.upgradeVersion
		).orElseThrow(
			Exception::new
		);

		Optional<String> productKeyOptional =
			WorkspaceFunctions.getLatestProductForUpgradeVersion(
				workspacePath, upgradeVersion);

		String version = productKeyOptional.orElse(upgradeVersion);

		List<String> args = new ArrayList<>();

		Collections.addAll(
			args, "init", "--base", workspacePath.toString(), "-f", "-v",
			version);

		String createWorkspaceCommand = StringUtil.merge(args, " ");

		try {
			BladeCLI.executeWithLatestBlade(createWorkspaceCommand);
		}
		catch (BladeCLIException bladeCLIException) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"Error {0} running 'create' Liferay workspace",
					bladeCLIException.getMessage()));

			throw new Exception(bladeCLIException.getMessage());
		}

		Path modulesPath = workspacePath.resolve(
			workspacePath.resolve(
				GradleFunctions.getWorkspaceProperty(
					workspacePath, "liferay.workspace.modules.dir",
					"modules")));

		if (Files.exists(modulesPath)) {
			Files.createFile(modulesPath.resolve(".touch"));
		}

		Path themesPath = workspacePath.resolve(
			workspacePath.resolve(
				GradleFunctions.getWorkspaceProperty(
					workspacePath, "liferay.workspace.themes.dir", "themes")));

		if (Files.exists(themesPath)) {
			Files.createFile(themesPath.resolve(".touch"));
		}
	}

	@Override
	protected boolean isNeedWorkspace() {
		return false;
	}

}