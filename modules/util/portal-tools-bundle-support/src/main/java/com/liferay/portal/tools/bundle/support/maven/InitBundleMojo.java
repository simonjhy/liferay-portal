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

package com.liferay.portal.tools.bundle.support.maven;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import com.liferay.portal.tools.bundle.support.commands.DownloadCommand;
import com.liferay.portal.tools.bundle.support.commands.InitBundleCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;
import com.liferay.workspace.bundle.url.codec.BundleURLCodec;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Proxy;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Mojo(inheritByDefault = false, name = "init")
public class InitBundleMojo extends AbstractLiferayMojo {

	@Override
	public void execute() throws MojoExecutionException {
		
		DependencyManagement dependencyManagement = project.getDependencyManagement();
		
		if (project.hasParent()) {
			return;
		}

		if ((environment == null) || environment.isEmpty()) {
			environment = BundleSupportConstants.DEFAULT_ENVIRONMENT;
		}

		if (url == null) {
			url = BundleSupportConstants.DEFAULT_BUNDLE_URL_OBJECT;
		}

		Proxy proxy = MavenUtil.getProxy(_mavenSession);

		String proxyProtocol = url.getProtocol();
		String proxyHost = null;
		Integer proxyPort = null;
		String proxyUser = null;
		String proxyPassword = null;
		String nonProxyHosts = null;

		if (proxy != null) {
			proxyHost = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyHost", proxy.getHost());
			proxyPort = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyPort", proxy.getPort());
			proxyUser = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyUser", proxy.getUsername());
			proxyPassword = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyPassword", proxy.getPassword());
			nonProxyHosts = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".nonProxyHosts", proxy.getNonProxyHosts());
		}

		try {
			InitBundleCommand initBundleCommand = new InitBundleCommand();

			initBundleCommand.setCacheDir(cacheDir);
			initBundleCommand.setConfigsDir(
				new File(project.getBasedir(), configs));
			initBundleCommand.setEnvironment(environment);
			initBundleCommand.setLiferayHomeDir(getLiferayHomeDir());
			initBundleCommand.setPassword(password);
			initBundleCommand.setStripComponents(stripComponents);
			initBundleCommand.setToken(token);
			initBundleCommand.setTokenFile(tokenFile);

			if (Objects.isNull(url)) {
				url = _getBundleUrl(product);
			}

			initBundleCommand.setUrl(url);
			initBundleCommand.setUserName(userName);

			initBundleCommand.execute();
		}
		catch (Exception exception) {
			throw new MojoExecutionException(
				"Unable to initialize bundle", exception);
		}
		finally {
			if (proxy != null) {
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyHost", proxyHost);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyPort", proxyPort);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyUser", proxyUser);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyPassword", proxyPassword);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".nonProxyHosts", nonProxyHosts);
			}
		}
	}

	public class ProductInfo {

		public String getAppServerTomcatVersion() {
			return _appServerTomcatVersion;
		}

		public String getBundleChecksumMD5() {
			return _bundleChecksumMD5;
		}

		public String getBundleUrl() {
			return _bundleUrl;
		}

		public String getLiferayDockerImage() {
			return _liferayDockerImage;
		}

		public String getLiferayProductVersion() {
			return _liferayProductVersion;
		}

		public String getReleaseDate() {
			return _releaseDate;
		}

		public String getTargetPlatformVersion() {
			return _targetPlatformVersion;
		}

		@SerializedName("appServerTomcatVersion")
		private String _appServerTomcatVersion;

		@SerializedName("bundleChecksumMD5")
		private String _bundleChecksumMD5;

		@SerializedName("bundleUrl")
		private String _bundleUrl;

		@SerializedName("liferayDockerImage")
		private String _liferayDockerImage;

		@SerializedName("liferayProductVersion")
		private String _liferayProductVersion;

		@SerializedName("releaseDate")
		private String _releaseDate;

		@SerializedName("targetPlatformVersion")
		private String _targetPlatformVersion;

	}

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_BUNDLE_CACHE_DIR_NAME
	)
	protected File cacheDir;

	@Parameter(defaultValue = BundleSupportConstants.DEFAULT_CONFIGS_DIR_NAME)
	protected String configs;

	@Parameter(defaultValue = "${liferay.workspace.environment}")
	protected String environment;

	@Parameter
	protected String password;

	@Parameter(defaultValue = "${liferay.workspace.product}")
	protected String product;

	@Parameter(
		defaultValue = "" + BundleSupportConstants.DEFAULT_STRIP_COMPONENTS
	)
	protected int stripComponents;

	@Parameter
	protected boolean token;

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_TOKEN_FILE_NAME
	)
	protected File tokenFile;

	@Parameter(defaultValue = "${liferay.workspace.bundle.url}")
	protected URL url;

	@Parameter
	protected String userName;

	private URL _getBundleUrl(String product) throws Exception {
		return Optional.ofNullable(
			_getProductInfo(product)
		).map(
			productInfo -> {
				try {
					return new URL(
						BundleURLCodec.decode(
							productInfo.getBundleUrl(),
							productInfo.getReleaseDate()));
				}
				catch (Exception exception) {
					return null;
				}
			}
		).orElse(
			null
		);
	}

	private ProductInfo _getProductInfo(Path downloadPath, String product)
		throws Exception {

		try (JsonReader jsonReader = new JsonReader(
				Files.newBufferedReader(downloadPath))) {

			Map<String, ProductInfo> productInfos = _getProductInfos(
				jsonReader);

			return productInfos.get(product);
		}
	}

	private ProductInfo _getProductInfo(String product) {
		if (product == null) {
			return null;
		}

		return _productInfos.computeIfAbsent(
			product,
			key -> {
				DownloadCommand downloadCommand = new DownloadCommand();

				downloadCommand.setCacheDir(_workspaceCacheDir);
				downloadCommand.setConnectionTimeout(5 * 1000);
				downloadCommand.setPassword(null);
				downloadCommand.setQuiet(true);
				downloadCommand.setToken(false);
				downloadCommand.setUserName(null);

				try {
					downloadCommand.setUrl(new URL(_PRODUCT_INFO_URL));

					downloadCommand.execute();

					return _getProductInfo(
						downloadCommand.getDownloadPath(), product);
				}
				catch (Exception exception1) {
					try {
						downloadCommand.setUrl(new URL(_CDN_PRODUCT_INFO_URL));

						downloadCommand.execute();

						return _getProductInfo(
							downloadCommand.getDownloadPath(), product);
					}
					catch (Exception exception2) {
						try (InputStream inputStream =
								InitBundleMojo.class.getResourceAsStream(
									"/.product_info.json");
							JsonReader jsonReader = new JsonReader(
								new InputStreamReader(inputStream))) {

							Map<String, ProductInfo> productInfos =
								_getProductInfos(jsonReader);

							return productInfos.get(product);
						}
						catch (Exception exception3) {
							return null;
						}
					}
				}
			});
	}

	private Map<String, ProductInfo> _getProductInfos(JsonReader jsonReader) {
		Gson gson = new Gson();

		TypeToken<Map<String, ProductInfo>> typeToken =
			new TypeToken<Map<String, ProductInfo>>() {
			};

		return gson.fromJson(jsonReader, typeToken.getType());
	}

	private static final String _CDN_PRODUCT_INFO_URL =
		"https://releases-cdn.liferay.com/tools/workspace/.product_info.json";

	private static final String _DEFAULT_WORKSPACE_CACHE_DIR_NAME =
		".liferay/workspace";

	private static final String _PRODUCT_INFO_URL =
		"https://releases.liferay.com/tools/workspace/.product_info.json";

	private static final Map<String, ProductInfo> _productInfos =
		new HashMap<>();
	private static final File _workspaceCacheDir = new File(
		System.getProperty("user.home"), _DEFAULT_WORKSPACE_CACHE_DIR_NAME);

	@Parameter(property = "session", readonly = true)
	private MavenSession _mavenSession;

}