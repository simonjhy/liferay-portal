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

import aQute.lib.exceptions.Exceptions;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.upgrade.GradleBuildScript;
import com.liferay.source.formatter.upgrade.GradleDependency;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

/**
 * @author Raymond Augé
 * @author Simon Jiang
 */
public class GradleFunctions {

	public static Optional<GradleDependency> getWorkspacePluginDependency(Path workspacePath)
		throws IOException, MultipleCompilationErrorsException {

		GradleBuildScript settingsGradle = new GradleBuildScript(workspacePath.resolve("settings.gradle"));

		List<GradleDependency> buildScriptDependencies = settingsGradle.getBuildScriptDependencies();

		return buildScriptDependencies.stream(
		).filter(
			dep -> Objects.equals(dep.getGroup(), "com.liferay")
		).filter(
			dep -> Objects.equals(dep.getName(), "com.liferay.gradle.plugins.workspace")
		).findFirst();
	}
	
	public static Optional<Path> getWorkspacePathByType(
		Path workspacePath, String type) {

		if (StringUtil.equalsIgnoreCase(type, PluginsUtils.API)) {
			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.modules.dir",
						"modules")));
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.HOOK)) {
			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.wars.dir",
						"modules")));
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.LAYOUTTPL)) {
			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.wars.dir",
						"modules")));
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.PORTLET)) {
			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.wars.dir",
						"modules")));
		}
		else if (StringUtil.equalsIgnoreCase(
					type, PluginsUtils.SERVICE_BUILDER_PORTLET)) {

			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.modules.dir",
						"modules")));
		}
		else if (StringUtil.equalsIgnoreCase(
					type, PluginsUtils.SPRING_MVC_PORTLET)) {

			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.wars.dir",
						"modules")));
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.THEME)) {
			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.themes.dir",
						"themes")));
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.WEB)) {
			return Optional.of(
				workspacePath.resolve(
					getWorkspaceProperty(
						workspacePath, "liferay.workspace.wars.dir",
						"modules")));
		}
		else {
			return Optional.empty();
		}
	}

	public static Properties getWorkspaceProperties(Path workspacePath) {
		ConcurrentMap<Path, Properties> asMap = _gradlePropertiesCache.asMap();

		return asMap.computeIfAbsent(
			workspacePath,
			key -> {
				try (ProjectConnection projectConnection =
						GradleConnector.newConnector(
						).forProjectDirectory(
							workspacePath.toFile()
						).connect()) {

					ByteArrayOutputStream byteArrayOutputStream =
						new ByteArrayOutputStream();

					projectConnection.newBuild(
					).addArguments(
						"-q"
					).forTasks(
						"properties"
					).setStandardOutput(
						byteArrayOutputStream
					).run();

					Properties properties = new Properties();

					properties.load(
						new StringReader(
							byteArrayOutputStream.toString("UTF-8")));

					return properties;
				}
				catch (Exception exception) {
					Path gradlePropertiesPath = workspacePath.resolve(
						"gradle.properties");

					if (Files.exists(gradlePropertiesPath)) {
						try (InputStream inputStream = Files.newInputStream(
								gradlePropertiesPath)) {

							Properties p = new Properties();

							p.load(inputStream);

							return p;
						}
						catch (IOException ioException) {
							throw Exceptions.duck(ioException);
						}
					}
					else {
						throw Exceptions.duck(exception);
					}
				}
			});
	}

	public static String getWorkspaceProperty(
		Path workspacePath, String key, String defaultValue) {

		Properties workspaceProperties = getWorkspaceProperties(workspacePath);

		String propertyValue = workspaceProperties.getProperty(
			key, defaultValue);

		if (Objects.nonNull(propertyValue)) {
			String[] propertyValueArrays = propertyValue.split("\\s*,\\s*", 2);

			return propertyValueArrays[0];
		}

		return null;
	}

	private static final Cache<Path, Properties> _gradlePropertiesCache =
		CacheBuilder.newBuilder(
		).weakValues(
		).weakKeys(
		).build();

}