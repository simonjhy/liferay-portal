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

package com.liferay.portal.tools.bundle.support.internal.util;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.GradleException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.portal.tools.bundle.support.ProductInfo;
import com.liferay.portal.tools.bundle.support.commands.DownloadCommand;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BundleSupportUtil {

	public static String getDeployDirName(String fileName) {
		if (fileName.endsWith(".jar")) {
			return "osgi/modules/";
		}

		if (fileName.endsWith(".war")) {
			return "osgi/war/";
		}

		return "deploy/";
	}

	private static final String _CDN_PRODUCT_INFO_URL =
			"https://releases-cdn.liferay.com/tools/workspace/.product_info.json";
	
	private final Map<String, ProductInfo> _productInfos = new HashMap<>();
	
	private Map<String, ProductInfo> _getProductInfos(JsonReader jsonReader) {
		Gson gson = new Gson();

		TypeToken<Map<String, ProductInfo>> typeToken =
			new TypeToken<Map<String, ProductInfo>>() {
			};

		return gson.fromJson(jsonReader, typeToken.getType());
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
	
	public static ProductInfo getProductInfo(String product) {
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
						return null;
					}
				}
			});
	}

	public static Integer setSystemProperty(String key, Integer value) {
		String valueString = null;

		if (value != null) {
			valueString = value.toString();
		}

		valueString = setSystemProperty(key, valueString);

		if ((valueString == null) || valueString.isEmpty()) {
			return null;
		}

		return Integer.valueOf(valueString);
	}

	public static String setSystemProperty(String key, String value) {
		String oldValue = System.getProperty(key);

		if (value == null) {
			Properties properties = System.getProperties();

			properties.remove(key);
		}
		else {
			System.setProperty(key, value);
		}

		return oldValue;
	}

	private static final String _DEFAULT_WORKSPACE_CACHE_DIR_NAME =
		".liferay/workspace";

	private static final String _PRODUCT_INFO_URL =
		"https://releases.liferay.com/tools/workspace/.product_info.json";

	private static Map<String, Object> _productInfoMap = Collections.emptyMap();
	private static final File _workspaceCacheDir = new File(
		System.getProperty("user.home"), _DEFAULT_WORKSPACE_CACHE_DIR_NAME);

}