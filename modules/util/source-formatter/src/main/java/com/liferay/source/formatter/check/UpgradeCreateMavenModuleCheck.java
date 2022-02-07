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

import aQute.libg.tuple.Pair;

import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;

import java.io.IOException;

import java.nio.file.Path;

import java.util.List;
import java.util.Optional;

/**
 * @author Simon Jiang
 */
public class UpgradeCreateMavenModuleCheck extends UpgradeCreateModuleCheck {

	@Override
	public List<Pair<String, String>> computePossibleUpgrades(
			Path repoPath, LugbotConfig lugbotConfig)
		throws IOException {

		Optional<Path> originPathOptional = MavenFunctions.getOriginPath(
			repoPath, lugbotConfig);

		Path sourcePath = originPathOptional.orElse(repoPath);

		List<String> pluginNames = lugbotConfig.tasks.plugins;

		return MavenFunctions.findPlugins(sourcePath, pluginNames);
	}

	@Override
	public List<Pair<String, String>> findPlugins(
			Path originPath, List<String> pluginNames)
		throws IOException {

		return MavenFunctions.findPlugins(originPath, pluginNames);
	}

	@Override
	public boolean isValidModulePath(Path path) {
		return MavenFunctions.isValidMavenPath(path);
	}

}