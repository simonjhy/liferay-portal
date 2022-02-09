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
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.WorkspaceFunctions;
import com.liferay.source.formatter.upgrade.util.YamlFunctions;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.MessageFormat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Simon Jiang
 */
public abstract class UpgradeAbstractCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!StringUtil.endsWith(fileName, "lugbot.yaml")) {
			return content;
		}

		String checkId = getAttributeValue(_CHECK_ID, absolutePath);

		LugbotConfig lugbotConfig = YamlFunctions.load(content);

		List<String> upgradeTaskNames = getConfiguredUpgradeTasks(lugbotConfig);

		if (!upgradeTaskNames.contains(checkId)) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"{0} upgrade check is not configured in lugbot.yaml",
					checkId));

			return content;
		}

		String baseDirValue = getBaseDirName();

		Path repoPath = Paths.get(baseDirValue);

		Optional<Path> workspacePathOptional = getWorkspacePathOptional(
			lugbotConfig, repoPath);

		if (isNeedWorkspace()) {
			if (!workspacePathOptional.isPresent()) {
				SourceFormatterUtil.printError(
					repoPath.toString(),
					"is not valid lifeay workspace project");

				return content;
			}

			doUpgrade(repoPath, lugbotConfig, workspacePathOptional.get());
		}
		else {
			if (workspacePathOptional.isPresent()) {
				SourceFormatterUtil.printError(
					null,
					MessageFormat.format(
						"{0} should be empty", workspacePathOptional.get()));

				return content;
			}

			Path workspacePath = repoPath.resolve(
				lugbotConfig.tasks.workspacePath);

			doUpgrade(repoPath, lugbotConfig, workspacePath.normalize());
		}

		return content;
	}

	protected abstract void doUpgrade(
			Path repoPath, LugbotConfig lugbotConfig, Path workspacePath)
		throws Exception;

	protected List<String> getConfiguredUpgradeTasks(
		LugbotConfig lugbotConfig) {

		List<LugbotConfig.Upgrade> upgrades =
			lugbotConfig.tasks.upgrade.upgrades;

		Stream<LugbotConfig.Upgrade> upgradesStream = upgrades.stream();

		return upgradesStream.map(
			upgrade -> upgrade.name
		).collect(
			Collectors.toList()
		);
	}

	protected Optional<Path> getWorkspacePathOptional(
		LugbotConfig lugbotConfig, Path repoPath) {

		return Optional.of(
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
	}

	protected abstract boolean isNeedWorkspace();

	private static final String _CHECK_ID = "checkId";

}