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

import aQute.lib.exceptions.ConsumerWithException;

import aQute.libg.tuple.Pair;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.upgrade.BladeCLI;
import com.liferay.source.formatter.upgrade.BladeCLIException;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.upgrade.util.GradleFunctions;
import com.liferay.source.formatter.upgrade.util.MavenFunctions;
import com.liferay.source.formatter.upgrade.util.PluginsUtils;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Simon Jiang
 */
public abstract class UpgradeCreateModuleCheck extends UpgradeAbstractCheck {

	@Override
	protected void doUpgrade(
			Path repoPath, LugbotConfig lugbotConfig, Path workspacePath)
		throws Exception {

		List<String> pluginNames = lugbotConfig.tasks.plugins;

		try {
			List<Pair<String, String>> pluginTypes = computePossibleUpgrades(
				repoPath, lugbotConfig);

			if (pluginTypes.isEmpty()) {
				return;
			}

			Optional<Path> originPathOptional = MavenFunctions.getOriginPath(
				repoPath, lugbotConfig);

			Path sourcePath = originPathOptional.orElse(repoPath);

			pluginTypes.stream(
			).map(
				pair -> {
					String plugin = pair.getFirst();

					Path originalPluginPath = sourcePath.resolve(plugin);

					Path pluginPath = originalPluginPath.normalize();

					if (!Files.exists(pluginPath)) {
						SourceFormatterUtil.printError(
							null,
							MessageFormat.format(
								"Expected {0} to exist", pluginPath));

						return new Pair<>(plugin, Optional.empty());
					}

					String type = pair.getSecond();

					Optional<Path> newModulePathOptional = Optional.empty();

					try {
						newModulePathOptional = _createModuleProjectSkeleton(
							workspacePath, pluginPath, type, lugbotConfig);
					}
					catch (Throwable throwable) {
						SourceFormatterUtil.printError(
							null,
							MessageFormat.format(
								"Error creating war project skeleton {0}",
								throwable.getMessage()));
					}

					return new Pair<>(plugin, newModulePathOptional);
				}
			).filter(
				Objects::nonNull
			).collect(
				Collectors.toMap(Pair::getFirst, Pair::getSecond)
			);
		}
		catch (IOException ioException) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"Failed to create moudle {0} for {1}", pluginNames,
					repoPath));
		}
	}

	protected String getServiceBuilderParentName(
		Path pluginPath, String serviceBuilderPortletName) {

		String serviceBuilderParentName = serviceBuilderPortletName;

		if (serviceBuilderParentName.endsWith("-portlet")) {
			serviceBuilderParentName = serviceBuilderParentName.replaceAll(
				"-portlet$", "");
		}

		return serviceBuilderParentName;
	}

	private Optional<Path> _createModuleProjectSkeleton(
			Path workspacePath, Path pluginPath, String type,
			LugbotConfig lugbotConfig)
		throws Exception {

		Optional<String> createTypeOptional = Optional.empty();

		String pluginName = String.valueOf(pluginPath.getFileName());

		Optional<String> moduleNameOptional = Optional.empty();
		List<String> args = new ArrayList<>();
		Optional<Path> targetPathOptional = Optional.empty();

		Collections.addAll(
			args, "create", "-v", lugbotConfig.tasks.upgradeVersion);

		if (StringUtil.equalsIgnoreCase(type, PluginsUtils.API)) {
			createTypeOptional = Optional.of("api");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.API);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.HOOK)) {
			createTypeOptional = Optional.of("war-hook");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.HOOK);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.LAYOUTTPL)) {
			createTypeOptional = Optional.of("layout-template");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.LAYOUTTPL);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.PORTLET)) {
			createTypeOptional = Optional.of("war-mvc-portlet");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.PORTLET);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else if (StringUtil.equalsIgnoreCase(
					type, PluginsUtils.SERVICE_BUILDER_PORTLET)) {

			createTypeOptional = Optional.of("service-builder");
			moduleNameOptional = Optional.of(
				getServiceBuilderParentName(pluginPath, pluginName));

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.SERVICE_BUILDER_PORTLET);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else if (StringUtil.equalsIgnoreCase(
					type, PluginsUtils.SPRING_MVC_PORTLET)) {

			createTypeOptional = Optional.of("spring-mvc-portlet");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.SPRING_MVC_PORTLET);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
			args.add("--framework");
			args.add("springportletmvc");
			args.add("--view-type");
			args.add("jsp");
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.THEME)) {
			createTypeOptional = Optional.of("theme");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.THEME);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else if (StringUtil.equalsIgnoreCase(type, PluginsUtils.WEB)) {
			createTypeOptional = Optional.of("war-hook");
			moduleNameOptional = Optional.of(pluginName);

			targetPathOptional = GradleFunctions.getWorkspacePathByType(
				workspacePath, PluginsUtils.WEB);
			args.add("-d");
			args.add(
				targetPathOptional.orElseThrow(
					Exception::new
				).toAbsolutePath(
				).toString());
			args.add("-t");
			args.add(createTypeOptional.get());
		}
		else {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"Unsupported type when creating new module project" +
						" skeleton {0} for {1}",
					type, pluginPath));
		}

		String moduleName = moduleNameOptional.get();

		Optional<Path> newModulePathOptional = targetPathOptional.map(
			p -> p.resolve(moduleName)
		).filter(
			Files::exists
		);

		if (newModulePathOptional.isPresent()) {
			SourceFormatterUtil.printError(
				null, "Skipping creating new module since path already exists");

			return Optional.empty();
		}

		SourceFormatterUtil.printError(
			null,
			MessageFormat.format(
				"Creating new module project skeleton for {0} to {1}",
				pluginPath, targetPathOptional.orElseThrow(Exception::new)));

		args.add(moduleName);

		targetPathOptional.filter(
			path -> Files.notExists(path)
		).ifPresent(
			ConsumerWithException.asConsumer(Files::createDirectories)
		);

		String createModuleCommand = StringUtil.merge(args, " ");

		try {
			BladeCLI.executeWithLatestBlade(createModuleCommand);
		}
		catch (BladeCLIException bladeCLIException) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"Error {0} running 'create'",
					bladeCLIException.getMessage()));
		}

		SourceFormatterUtil.printError(
			null,
			MessageFormat.format(
				"Executing command: create moudle {0}",
				args.stream(
				).collect(
					Collectors.joining(" ")
				)));

		newModulePathOptional = targetPathOptional.map(
			p -> p.resolve(moduleName)
		).filter(
			Files::exists
		);

		if (newModulePathOptional.isPresent() &&
			Objects.equals(createTypeOptional.get(), "service-builder")) {

			final String[] sbArgs = {
				"create", "-v", lugbotConfig.tasks.upgradeVersion, "-t",
				"war-mvc-portlet", "-d",
				newModulePathOptional.map(
					Path::toString
				).get(),
				pluginName
			};

			try {
				String createServiceBuilderCommand = StringUtil.merge(
					sbArgs, " ");

				BladeCLI.executeWithLatestBlade(createServiceBuilderCommand);
			}
			catch (BladeCLIException bladeCLIException) {
				SourceFormatterUtil.printError(
					null,
					MessageFormat.format(
						"Error {0} running 'create'",
						bladeCLIException.getMessage()));
			}

			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"Executing command: {0}",
					Arrays.stream(
						sbArgs
					).collect(
						Collectors.joining(" ")
					)));
		}

		if (newModulePathOptional.isPresent()) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"New skeleton module created {0}",
					newModulePathOptional.get()));
		}

		return newModulePathOptional;
	}

}