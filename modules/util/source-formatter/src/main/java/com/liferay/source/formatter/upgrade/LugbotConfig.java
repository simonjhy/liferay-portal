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

package com.liferay.source.formatter.upgrade;

import java.util.List;
import java.util.Map;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class LugbotConfig {

	@Override
	public boolean equals(Object object) {
		if ((object instanceof LugbotConfig) == false) {
			return false;
		}

		LugbotConfig targetLugbotConfig = (LugbotConfig)object;

		if (tasks.equals(targetLugbotConfig.tasks) &&
			_isEqualIgnoreCase(url, targetLugbotConfig.url) &&
			_isEqualIgnoreCase(
				commiterIdentity, targetLugbotConfig.commiterIdentity)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 31;

		hash =
			(31 * hash) +
				((commiterIdentity != null) ? commiterIdentity.hashCode() : 0);
		hash = (31 * hash) + ((tasks != null) ? tasks.hashCode() : 0);
		hash = (31 * hash) + ((url != null) ? url.hashCode() : 0);

		return hash;
	}

	public String commiterIdentity;
	public Tasks tasks;
	public String url;

	public static class Analysis {

		@Override
		public boolean equals(Object object) {
			if ((object instanceof Analysis) == false) {
				return false;
			}

			Analysis targetAnalysis = (Analysis)object;

			if (showDetail == targetAnalysis.showDetail) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return Boolean.hashCode(showDetail);
		}

		public boolean showDetail;

	}

	public static class Category {

		public Category() {
		}

		public Category(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object object) {
			if ((object instanceof Category) == false) {
				return false;
			}

			Category targetCategory = (Category)object;

			if (_isEqualIgnoreCase(name, targetCategory.name)) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			if (name != null) {
				return name.hashCode();
			}

			return 0;
		}

		public String name;

	}

	public static class Check {

		public Check() {
		}

		public Check(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object object) {
			if ((object instanceof Check) == false) {
				return false;
			}

			Check targetCheck = (Check)object;

			if (_isEqualIgnoreCase(name, targetCheck.name)) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			if (name != null) {
				return name.hashCode();
			}

			return 0;
		}

		public String name;

	}

	public static class CodeQuality {

		@Override
		public boolean equals(Object object) {
			if ((object instanceof CodeQuality) == false) {
				return false;
			}

			CodeQuality targetCodeQuality = (CodeQuality)object;

			if (_isEqualIgnoreCase(categories, targetCodeQuality.categories) &&
				_isEqualIgnoreCase(checks, targetCodeQuality.checks)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = 31;

			hash =
				(31 * hash) +
					((categories != null) ? categories.hashCode() : 0);
			hash = (31 * hash) + ((checks != null) ? checks.hashCode() : 0);

			return hash;
		}

		public List<Category> categories;
		public List<Check> checks;

	}

	public static class CodeUpgrade {

		@Override
		public boolean equals(Object object) {
			if ((object instanceof CodeUpgrade) == false) {
				return false;
			}

			CodeUpgrade targetCodeUpgrade = (CodeUpgrade)object;

			if ((enableAll == targetCodeUpgrade.enableAll) &&
				_isEqualIgnoreCase(categories, targetCodeUpgrade.categories) &&
				_isEqualIgnoreCase(upgrades, targetCodeUpgrade.upgrades)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = 31;

			hash =
				(31 * hash) +
					((categories != null) ? categories.hashCode() : 0);
			hash = (31 * hash) + Boolean.hashCode(enableAll);
			hash = (31 * hash) + ((upgrades != null) ? upgrades.hashCode() : 0);

			return hash;
		}

		public List<Category> categories;
		public boolean enableAll;
		public List<Upgrade> upgrades;

	}

	public static class Tasks {

		@Override
		public boolean equals(Object object) {
			if ((object instanceof Tasks) == false) {
				return false;
			}

			Tasks targetTasks = (Tasks)object;

			if (_isEqualIgnoreCase(mode, targetTasks.mode) &&
				_isEqualIgnoreCase(
					upgradeVersion, targetTasks.upgradeVersion) &&
				_isEqualIgnoreCase(
					currentVersion, targetTasks.currentVersion) &&
				_isEqualIgnoreCase(analysis, targetTasks.analysis) &&
				_isEqualIgnoreCase(quality, targetTasks.quality) &&
				_isEqualIgnoreCase(upgrade, targetTasks.upgrade) &&
				_isEqualIgnoreCase(
					pluginsSDKPath, targetTasks.pluginsSDKPath) &&
				_isEqualIgnoreCase(workspacePath, targetTasks.workspacePath) &&
				plugins.equals(targetTasks.plugins)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = 31;

			hash = (31 * hash) + ((mode != null) ? mode.hashCode() : 0);
			hash = (31 * hash) + ((quality != null) ? quality.hashCode() : 0);
			hash = (31 * hash) + ((upgrade != null) ? upgrade.hashCode() : 0);
			hash = (31 * hash) + ((analysis != null) ? analysis.hashCode() : 0);
			hash =
				(31 * hash) +
					((upgradeVersion != null) ? upgradeVersion.hashCode() : 0);
			hash =
				(31 * hash) +
					((currentVersion != null) ? currentVersion.hashCode() : 0);
			hash =
				(31 * hash) +
					((pluginsSDKPath != null) ? pluginsSDKPath.hashCode() : 0);
			hash =
				(31 * hash) +
					((workspacePath != null) ? workspacePath.hashCode() : 0);
			hash = (31 * hash) + ((plugins != null) ? plugins.hashCode() : 0);

			return hash;
		}

		public Analysis analysis;
		public String currentVersion;
		public String mode;
		public List<String> plugins;
		public String pluginsSDKPath;
		public CodeQuality quality;
		public CodeUpgrade upgrade;
		public String upgradeVersion;
		public String workspacePath;

	}

	public static class Upgrade {

		public Upgrade() {
		}

		public Upgrade(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object object) {
			if ((object instanceof Upgrade) == false) {
				return false;
			}

			Upgrade targetUpgrade = (Upgrade)object;

			if (_isEqualIgnoreCase(description, targetUpgrade.description) &&
				_isEqualIgnoreCase(params, targetUpgrade.params) &&
				_isEqualIgnoreCase(name, targetUpgrade.name)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = 31;

			hash =
				(31 * hash) +
					((description != null) ? description.hashCode() : 0);
			hash = (31 * hash) + ((name != null) ? name.hashCode() : 0);
			hash = (31 * hash) + ((params != null) ? params.hashCode() : 0);

			return hash;
		}

		public String description;
		public String name;
		public Map<String, List<String>> params;

	}

	private static boolean _isEqualIgnoreCase(Object original, Object target) {
		if (original != null) {
			return original.equals(target);
		}

		if (target == null) {
			return true;
		}

		return false;
	}

	private static boolean _isEqualIgnoreCase(String original, String target) {
		if (original != null) {
			return original.equalsIgnoreCase(target);
		}

		if (target == null) {
			return true;
		}

		return false;
	}

}