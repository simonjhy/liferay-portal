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

import aQute.bnd.stream.MapStream;

import aQute.lib.exceptions.ConsumerWithException;
import aQute.lib.exceptions.Exceptions;
import aQute.lib.exceptions.PredicateWithException;

import aQute.libg.tuple.Pair;

import com.liferay.blade.cli.util.StringUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.poshi.core.util.ListUtil;
import com.liferay.source.formatter.upgrade.LugbotConfig;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.MessageFormat;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.merge.ModelMerger;

import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * @author Raymond Augé
 * @author Seiphon Wang
 * @author Simon Jiang
 */
public class MavenFunctions {

	public static String calculateFromModel(Model model) {
		String packaging = model.getPackaging();

		if (packaging.equals("pom")) {
			return null;
		}

		Optional<Xpp3Dom> configurationOptional =
			getLiferayMavenPluginConfiguration(model);

		Optional<Xpp3Dom> apiBaseDirOptional = configurationOptional.map(
			c -> c.getChild("apiBaseDir"));
		Optional<Xpp3Dom> webappBaseDirOptional = configurationOptional.map(
			c -> c.getChild("webappBaseDir"));

		if (apiBaseDirOptional.isPresent() ||
			webappBaseDirOptional.isPresent()) {

			return PluginsUtils.SERVICE_BUILDER_PORTLET;
		}

		if (packaging.equals("jar")) {
			return PluginsUtils.API;
		}

		Optional<String> pluginTypeStringOptional = configurationOptional.map(
			c -> c.getChild("pluginType")
		).map(
			Xpp3Dom::getValue
		);

		List<Dependency> dependencies = model.getDependencies();

		Stream<Dependency> streamDependencies = dependencies.stream();

		if (pluginTypeStringOptional.isPresent() &&
			Objects.equals("portlet", pluginTypeStringOptional.get()) &&
			streamDependencies.filter(
				d -> Objects.equals("org.springframework", d.getGroupId())
			).anyMatch(
				d -> Objects.equals("spring-webmvc-portlet", d.getArtifactId())
			)) {

			return PluginsUtils.SPRING_MVC_PORTLET;
		}

		return pluginTypeStringOptional.orElse(PluginsUtils.PORTLET);
	}

	public static List<String> computePossibleUpgrades(
		Path repoPath, LugbotConfig lugbotConfig, String possibleUpgrade) {

		Optional<Path> workspacePathOptional = Optional.of(
			lugbotConfig
		).map(
			config -> {
				if (config.tasks.workspacePath != null) {
					Path resolvedWorkspacePath = repoPath.resolve(
						config.tasks.workspacePath);

					return resolvedWorkspacePath.normalize();
				}

				return repoPath;
			}
		).filter(
			WorkspaceFunctions::isWorkspacePath
		);

		if (!workspacePathOptional.isPresent()) {
			SourceFormatterUtil.printError(
				null,
				MessageFormat.format(
					"No possible upgrades for {0} because no workspace is " +
						"present",
					possibleUpgrade));

			return Collections.emptyList();
		}

		Optional<Path> originPathOptional = getOriginPath(
			repoPath, lugbotConfig);

		Path sourcePath = originPathOptional.orElse(repoPath);

		List<Pair<String, Path>> pluginPoms = findPluginPoms(
			sourcePath, lugbotConfig.tasks.plugins);

		if (!pluginPoms.isEmpty()) {
			return Collections.singletonList(possibleUpgrade);
		}

		SourceFormatterUtil.printError(
			null,
			MessageFormat.format(
				"No possible upgrades for {0} because no maven modules were " +
					"found at {}",
				possibleUpgrade));

		return Collections.emptyList();
	}

	public static List<Pair<String, Path>> findPluginPoms(
		Path originPath, List<String> pluginNames) {

		return Optional.ofNullable(
			pluginNames
		).orElseGet(
			Collections::emptyList
		).stream(
		).flatMap(
			n -> {
				MapStream<String, Path> mapStreams = MapStream.of(
					n, originPath.resolve(n));

				return mapStreams.filterValue(
					PredicateWithException.asPredicate(Files::exists)
				).mapValue(
					p -> p.resolve(
						"pom.xml"
					).normalize()
				).filterValue(
					PredicateWithException.asPredicate(Files::exists)
				).filterValue(
					Objects::nonNull
				).mapToObj(
					Pair::new
				);
			}
		).collect(
			Collectors.toList()
		);
	}

	public static List<Pair<String, String>> findPlugins(
		Path originPath, List<String> pluginNames) {

		List<Pair<String, Path>> foundPluginPoms = findPluginPoms(
			originPath, pluginNames);

		Stream<Pair<String, Path>> streamFoundPluginPoms =
			foundPluginPoms.stream();

		return streamFoundPluginPoms.map(
			p -> {
				String moduleType = mapModuleToType(p.getSecond());

				if (moduleType != null) {
					return new Pair<>(p.getFirst(), moduleType);
				}

				return null;
			}
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	public static Optional<Xpp3Dom> getLiferayMavenPluginConfiguration(
		Model model) {

		Build build = model.getBuild();

		if (build == null) {
			return Optional.empty();
		}

		List<Plugin> plugins = build.getPlugins();

		if (ListUtil.isEmpty(plugins)) {
			return Optional.empty();
		}

		return plugins.stream(
		).filter(
			p ->
				Objects.equals("liferay-maven-plugin", p.getArtifactId()) &&
				Objects.equals("com.liferay.maven.plugins", p.getGroupId())
		).map(
			Plugin::getConfiguration
		).map(
			Xpp3Dom.class::cast
		).findFirst();
	}

	public static Optional<Path> getOriginPath(
		Path repoPath, LugbotConfig lugbotConfig) {

		return Optional.ofNullable(
			lugbotConfig.tasks
		).map(
			tasks -> tasks.pluginsSDKPath
		).map(
			repoPath::resolve
		).map(
			Path::normalize
		);
	}

	public static List<Path> getPossibleMavenPluginPath(
			Path mavenPluginParentPath)
		throws IOException {

		File mavenPluginParentDir = mavenPluginParentPath.toFile();

		File[] mavenPlugins = mavenPluginParentDir.listFiles(
			new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (dir.isDirectory()) {
						return true;
					}

					return false;
				}

			});

		return Stream.of(
			mavenPlugins
		).map(
			dir -> dir.toPath()
		).filter(
			dir -> Files.exists(dir.resolve("pom.xml"))
		).collect(
			Collectors.toList()
		);
	}

	public static boolean isValidMavenPath(Path path) {
		if (Files.exists(path)) {
			Path pomXml = path.resolve("pom.xml");

			if (Files.exists(pomXml)) {
				return true;
			}
		}

		return false;
	}

	public static String mapModuleToType(Path path) {
		return Optional.of(
			readPom(path)
		).map(
			model -> calculateFromModel(model)
		).orElse(
			null
		);
	}

	public static Model readPom(Path pathOfPom) {
		if (Files.isDirectory(pathOfPom)) {
			Path resolvedPomPath = pathOfPom.resolve("pom.xml");

			pathOfPom = resolvedPomPath.normalize();
		}

		return _loadModel(pathOfPom);
	}

	private static Map<String, String> _copyAsMap(Properties properties) {
		Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();

		Stream<Map.Entry<Object, Object>> streamEntrySet = entrySet.stream();

		return streamEntrySet.map(
			e -> new AbstractMap.SimpleEntry<>(
				String.valueOf(e.getKey()), String.valueOf(e.getValue()))
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);
	}

	private static Model _loadModel(Path pathOfPom) {
		File pomFile = pathOfPom.toFile();

		try (Reader reader = new FileReader(pomFile)) {
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();

			Model model = xpp3Reader.read(reader);

			model.setPomFile(pomFile);

			return _resolveModel(model, pathOfPom);
		}
		catch (Exception exception) {
			throw Exceptions.duck(exception);
		}
	}

	private static void _mergeBoms(Model model) {
		DependencyManagement dependencyManagement =
			model.getDependencyManagement();

		if (dependencyManagement != null) {
			ArrayList<Dependency> dependencies = new ArrayList<>(
				dependencyManagement.getDependencies());

			Stream<Dependency> streamDependencies = dependencies.stream();

			streamDependencies.forEach(
				ConsumerWithException.asConsumer(
					dep -> {
						if (Objects.equals("pom", dep.getType()) &&
							Objects.equals("import", dep.getScope())) {

							Path mavenUserHomePath = Paths.get(
								System.getProperty("user.home"));

							Path m2HomePath = mavenUserHomePath.resolve(".m2");

							Path m2RepositoryPath = m2HomePath.resolve(
								"repository");

							Path groupPath = m2RepositoryPath.resolve(
								Paths.get(
									StringUtil.replace(
										dep.getGroupId(), '.',
										File.separatorChar)));

							Path artifactPath = groupPath.resolve(
								dep.getArtifactId());

							Path versionPath = artifactPath.resolve(
								dep.getVersion());

							Path artifactPomPath = versionPath.resolve(
								String.format(
									"%s-%s.pom", dep.getArtifactId(),
									dep.getVersion()));

							Path pathToBom = artifactPomPath.normalize();

							if (!Files.exists(pathToBom)) {
								SourceFormatterUtil.printError(
									null,
									MessageFormat.format(
										"Bom defined in {0} " +
											"dependencyManagement was not" +
												" found: {1}",
										model, dep));

								return;
							}

							dependencyManagement.removeDependency(dep);

							Model bomModel = _loadModel(pathToBom);

							ModelMerger modelMerger = new ModelMerger();

							modelMerger.merge(model, bomModel, false, null);

							_resolveProperties(model);
							_mergeBoms(model);
						}
					}));
		}
	}

	private static Model _resolveModel(Model model, Path pathOfThisPom) {
		Parent parent = model.getParent();

		if (parent == null) {
			_resolveProperties(model);
			_mergeBoms(model);

			return model;
		}

		Path pathOfParentPom = Optional.ofNullable(
			parent.getRelativePath()
		).filter(
			Objects::nonNull
		).filter(
			string -> StringUtil.isNullOrEmpty(string)
		).map(
			rp -> {
				Path parentPomPath = pathOfThisPom.getParent();

				Path rpPath = parentPomPath.resolve(rp);

				return rpPath.normalize();
			}
		).filter(
			Files::exists
		).orElseGet(
			() -> {
				Path mavenUserHomePath = Paths.get(
					System.getProperty("user.home"));

				Path m2HomePath = mavenUserHomePath.resolve(".m2");

				Path m2RepositoryPath = m2HomePath.resolve("repository");

				Path groupPath = m2RepositoryPath.resolve(
					Paths.get(
						StringUtil.replace(
							parent.getGroupId(), '.', File.separatorChar)));

				Path artifactPath = groupPath.resolve(parent.getArtifactId());

				Path versionPath = artifactPath.resolve(parent.getVersion());

				Path artifactPomPath = versionPath.resolve(
					String.format(
						"%s-%s.pom", parent.getArtifactId(),
						parent.getVersion()));

				return artifactPomPath.normalize();
			}
		);

		if (Files.isDirectory(pathOfParentPom)) {
			Path pathOfParentPomPath = pathOfParentPom.resolve("pom.xml");

			pathOfParentPom = pathOfParentPomPath.normalize();
		}

		if (Files.exists(pathOfParentPom)) {
			Model parentModel = _loadModel(pathOfParentPom);

			ModelMerger modelMerger = new ModelMerger();

			modelMerger.merge(model, parentModel, false, null);
		}

		_resolveProperties(model);
		_mergeBoms(model);

		return model;
	}

	private static void _resolveProperties(Model model) {
		Properties properties = model.getProperties();

		properties.put("basedir", String.valueOf(model.getProjectDirectory()));

		_safePut(properties, "pom.version", model.getVersion());
		_safePut(properties, "project.artifactId", model.getArtifactId());
		_safePut(
			properties, "project.basedir", properties.getProperty("basedir"));
		_safePut(properties, "project.groupId", model.getGroupId());
		_safePut(properties, "project.version", model.getVersion());

		Optional.ofNullable(
			model.getParent()
		).ifPresent(
			parent -> properties.put(
				"project.parent.version", parent.getVersion())
		);

		Map<String, String> before = _copyAsMap(properties);

		for (Enumeration<Object> keysEnumeration = properties.keys();
			 keysEnumeration.hasMoreElements();) {

			String key = (String)keysEnumeration.nextElement();

			String value = properties.getProperty(key);

			properties.put(key, _resolveProperties(value, key, before));
		}

		Map<String, String> after = _copyAsMap(properties);

		Optional.ofNullable(
			model.getDependencyManagement()
		).ifPresent(
			dm -> dm.getDependencies(
			).forEach(
				dep -> {
					Optional.ofNullable(
						dep.getArtifactId()
					).map(
						a -> _resolveProperties(a, "it.artifactId", after)
					).ifPresent(
						dep::setArtifactId
					);

					Optional.ofNullable(
						dep.getGroupId()
					).map(
						g -> _resolveProperties(g, "it.groupId", after)
					).ifPresent(
						dep::setGroupId
					);

					Optional.ofNullable(
						dep.getScope()
					).map(
						s -> _resolveProperties(s, "it.scope", after)
					).ifPresent(
						dep::setScope
					);

					Optional.ofNullable(
						dep.getVersion()
					).map(
						v -> _resolveProperties(v, "it.version", after)
					).ifPresent(
						dep::setVersion
					);

					Optional.ofNullable(
						dep.getVersion()
					).ifPresent(
						v -> properties.setProperty(
							StringBundler.concat(
								dep.getGroupId(), ":", dep.getArtifactId(),
								".version"),
							v)
					);
				}
			)
		);

		List<Dependency> dependencies = model.getDependencies();

		Stream<Dependency> dependenciesStream = dependencies.stream();

		dependenciesStream.forEach(
			dep -> {
				Optional.ofNullable(
					dep.getArtifactId()
				).map(
					a -> _resolveProperties(a, "it.artifactId", after)
				).ifPresent(
					dep::setArtifactId
				);

				Optional.ofNullable(
					dep.getGroupId()
				).map(
					g -> _resolveProperties(g, "it.groupId", after)
				).ifPresent(
					dep::setGroupId
				);

				Optional.ofNullable(
					dep.getScope()
				).map(
					s -> _resolveProperties(s, "it.scope", after)
				).ifPresent(
					dep::setScope
				);

				String version = Optional.ofNullable(
					dep.getVersion()
				).map(
					v -> _resolveProperties(v, "it.version", after)
				).orElseGet(
					() -> Optional.ofNullable(
						properties.getProperty(
							StringBundler.concat(
								dep.getGroupId(), ":", dep.getArtifactId(),
								".version"))
					).orElse(
						null
					)
				);

				dep.setVersion(version);
			});
	}

	private static String _resolveProperties(
		String value, String key, Map<String, String> original) {

		return _unescape(_substVars(value, key, null, original));
	}

	private static void _safePut(
		Properties properties, String key, Object value) {

		if (value != null) {
			properties.put(key, value);
		}
	}

	private static String _substVars(
			String val, String currentKey, Map<String, String> cycleMap,
			Map<String, String> configProps)
		throws IllegalArgumentException {

		if (cycleMap == null) {
			cycleMap = new HashMap<>();
		}

		// Put the current key in the cycle map.

		cycleMap.put(currentKey, currentKey);

		// Assume we have a value that is something like:
		// "leading ${foo.${bar}} middle ${baz} trailing"

		// Find the first ending '}' variable delimiter, which
		// will correspond to the first deepest nested variable
		// placeholder.

		int startDelim;
		int stopDelim = -1;

		do {
			stopDelim = val.indexOf(_DELIM_STOP, stopDelim + 1);

			while ((stopDelim > 0) &&
				   (val.charAt(stopDelim - 1) == _ESCAPE_CHAR)) {

				stopDelim = val.indexOf(_DELIM_STOP, stopDelim + 1);
			}

			// Find the matching starting "${" variable delimiter
			// by looping until we find a start delimiter that is
			// greater than the stop delimiter we have found.

			startDelim = val.indexOf(_DELIM_START);

			while (stopDelim >= 0) {
				int idx = val.indexOf(
					_DELIM_START, startDelim + _DELIM_START.length());

				if ((idx < 0) || (idx > stopDelim)) {
					break;
				}
				else if (idx < stopDelim) {
					startDelim = idx;
				}
			}
		}
		while ((startDelim >= 0) && (stopDelim >= 0) &&
			   (stopDelim < (startDelim + _DELIM_START.length())));

		// If we do not have a start or stop delimiter, then just
		// return the existing value.

		if ((startDelim < 0) || (stopDelim < 0)) {
			cycleMap.remove(currentKey);

			return val;
		}

		// At this point, we have found a variable placeholder so
		// we must perform a variable substitution on it.
		// Using the start and stop delimiter indices, extract
		// the first, deepest nested variable placeholder.

		String variable = val.substring(
			startDelim + _DELIM_START.length(), stopDelim);

		String org = variable;

		// Strip expansion modifiers

		int idx1 = variable.lastIndexOf(":-");
		int idx2 = variable.lastIndexOf(":+");

		int idx3 = (idx1 >= 0) && (idx2 >= 0) ? Math.min(idx1, idx2) :
			((idx1 >= 0) ? idx1 : idx2);

		String op = null;

		if ((idx3 >= 0) && (idx3 < variable.length())) {
			op = variable.substring(idx3);
			variable = variable.substring(0, idx3);
		}

		// Verify that this is not a recursive variable reference.

		if (cycleMap.get(variable) != null) {
			throw new IllegalArgumentException(
				"recursive variable reference: " + variable);
		}

		String substValue = null;

		// Get the value of the deepest nested variable placeholder.
		// Try to configuration properties first.

		if (configProps != null) {
			substValue = configProps.get(variable);
		}

		if (op != null) {
			if (op.startsWith(":-")) {
				if ((substValue == null) || (substValue.length() == 0)) {
					substValue = op.substring(":-".length());
				}
			}
			else if (op.startsWith(":+")) {
				if ((substValue != null) && (substValue.length() != 0)) {
					substValue = op.substring(":+".length());
				}
			}
			else {
				throw new IllegalArgumentException(
					"Bad substitution: ${" + org + "}");
			}
		}

		if (substValue == null) {

			// alters the original token to avoid infinite recursion
			// altered tokens are reverted in substVarsPreserveUnresolved()

			substValue = StringBundler.concat(_MARKER, "{", variable, "}");
		}

		// Remove the found variable from the cycle map, since
		// it may appear more than once in the value and we don't
		// want such situations to appear as a recursive reference.

		cycleMap.remove(variable);

		// Append the leading characters, the substituted value of
		// the variable, and the trailing characters to get the new
		// value.

		val =
			val.substring(0, startDelim) + substValue +
				val.substring(stopDelim + _DELIM_STOP.length());

		// Now perform substitution again, since there could still
		// be substitutions to make.

		val = _substVars(val, currentKey, cycleMap, configProps);

		cycleMap.remove(currentKey);

		return val;
	}

	private static String _unescape(String val) {
		val = val.replaceAll("\\" + _MARKER, "\\$");

		int escape = val.indexOf(_ESCAPE_CHAR);

		while ((escape >= 0) && (escape < (val.length() - 1))) {
			char c = val.charAt(escape + 1);

			if ((c == '{') || (c == '}') || (c == _ESCAPE_CHAR)) {
				val = val.substring(0, escape) + val.substring(escape + 1);
			}

			escape = val.indexOf(_ESCAPE_CHAR, escape + 1);
		}

		return val;
	}

	private static final String _DELIM_START = "${";

	private static final String _DELIM_STOP = "}";

	private static final char _ESCAPE_CHAR = '\\';

	private static final String _MARKER = "$__";

}