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

package com.liferay.project.templates.workspace.internal;

import com.liferay.portal.tools.bundle.support.commands.DownloadCommand;
import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.FileUtil;

import groovy.json.JsonSlurper;

import java.io.File;

import java.math.BigInteger;

import java.net.URL;

import java.nio.file.Path;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Objects;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Simon Jiang
 */
public class WorkspaceProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "workspace";
	}

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		if (projectTemplatesArgs.isMaven()) {
			_addBundleUrlProperties(
				new File(
					projectTemplatesArgs.getDestinationDir(),
					projectTemplatesArgs.getName()));
		}
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {
	}

	public class ProductInfo {

		public ProductInfo(Map<String, String> productMap) {
			_appServerTomcatVersion = _safeGet(
				productMap, "appServerTomcatVersion", "");
			_bundleUrl = _safeGet(productMap, "bundleUrl", "");
			_liferayDockerImage = _safeGet(
				productMap, "liferayDockerImage", "");
			_liferayProductVersion = _safeGet(
				productMap, "liferayProductVersion", "");
			_releaseDate = _safeGet(productMap, "releaseDate", "");
			_targetPlatformVersion = _safeGet(
				productMap, "targetPlatformVersion", "");
			_promoted = Boolean.parseBoolean(
				_safeGet(productMap, "promoted", "false"));
		}

		public String getAppServerTomcatVersion() {
			return _appServerTomcatVersion;
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

		public boolean isPromoted() {
			return _promoted;
		}

		private String _safeGet(
			Map<String, String> map, String key, String defVal) {

			if (map != null) {
				return map.get(key);
			}

			return defVal;
		}

		private final String _appServerTomcatVersion;
		private final String _bundleUrl;
		private final String _liferayDockerImage;
		private final String _liferayProductVersion;
		private Boolean _promoted = false;
		private final String _releaseDate;
		private final String _targetPlatformVersion;

	}

	private void _addBundleUrlProperties(File baseDir) {
		try {
			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			documentBuilderFactory.setFeature(
				XMLConstants.FEATURE_SECURE_PROCESSING, true);
			documentBuilderFactory.setFeature(
				"http://apache.org/xml/features/disallow-doctype-decl", true);
			documentBuilderFactory.setFeature(
				"http://xml.org/sax/features/external-general-entities", false);
			documentBuilderFactory.setFeature(
				"http://xml.org/sax/features/external-parameter-entities",
				false);

			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);

			File pomFile = _getpomXMLFile(baseDir);

			if (Objects.isNull(pomFile)) {
				return;
			}

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(pomFile);

			Element documentElement = document.getDocumentElement();

			documentElement.normalize();

			NodeList propertiesNodeList = document.getElementsByTagName(
				"properties");

			Node propertiesNode = propertiesNodeList.item(0);

			if (propertiesNode.getNodeType() == Node.ELEMENT_NODE) {
				NodeList nodeList = propertiesNode.getChildNodes();

				String liferayBomVersion = null;

				for (int nodeInt = 0; nodeInt < nodeList.getLength();
					 nodeInt++) {

					Node sNode = nodeList.item(nodeInt);

					if ((sNode.getNodeType() == Node.ELEMENT_NODE) &&
						Objects.equals(
							sNode.getNodeName(), "liferay.bom.version")) {

						liferayBomVersion = sNode.getTextContent();

						break;
					}
				}

				if (Objects.nonNull(liferayBomVersion)) {
					String bundleURL = _getBundleURLFromProduct(
						liferayBomVersion);

					if (Objects.isNull(bundleURL)) {
						return;
					}

					Element bundleUrlElement = document.createElement(
						"liferay.workspace.bundle.url");

					bundleUrlElement.appendChild(
						document.createTextNode(bundleURL));

					propertiesNode.appendChild(bundleUrlElement);

					DOMSource source = new DOMSource(document);

					TransformerFactory transformerFactory =
						TransformerFactory.newInstance();

					Transformer transformer =
						transformerFactory.newTransformer();

					StreamResult result = new StreamResult(
						new File(baseDir, _POM_XML_FILE_NAME));

					transformer.setOutputProperty(OutputKeys.METHOD, "xml");
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
					transformer.setOutputProperty(
						OutputKeys.OMIT_XML_DECLARATION, "yes");

					transformer.transform(source, result);
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to set maven properties", exception);
		}
	}

	private String _decodeBundleUrl(ProductInfo productInfo)
		throws ParseException {

		Base64.Decoder decoder = Base64.getUrlDecoder();

		byte[] byteArray = decoder.decode(productInfo.getBundleUrl());

		Date parsedReleaseDate = _parseDate(productInfo.getReleaseDate());

		Calendar calendar = new GregorianCalendar();

		calendar.setTime(parsedReleaseDate);

		BigInteger bigInteger = new BigInteger(byteArray);

		bigInteger = bigInteger.shiftRight(calendar.get(5));

		return new String(bigInteger.toByteArray());
	}

	private File _findWorkspacePomFile(File dir) {
		if (dir == null) {
			return null;
		}
		else if (Objects.equals(".", dir.toString()) || !dir.isAbsolute()) {
			try {
				dir = dir.getCanonicalFile();
			}
			catch (Exception exception) {
				dir = dir.getAbsoluteFile();
			}
		}

		File file = new File(dir, _POM_XML_FILE_NAME);

		if (file.exists() && _isWorkspacePomFile(file)) {
			return dir;
		}

		return _findWorkspacePomFile(dir.getParentFile());
	}

	@SuppressWarnings("unchecked")
	private String _getBundleURLFromProduct(String liferayBomVersion) {
		try {
			Map<String, Object> productInfos = _getProductInfos();

			if (Objects.isNull(productInfos)) {
				return null;
			}

			for (Map.Entry<String, Object> entryKey : productInfos.entrySet()) {
				ProductInfo productInfo = new ProductInfo(
					(Map<String, String>)entryKey.getValue());

				if (Objects.isNull(productInfo)) {
					return null;
				}

				if (Objects.equals(
						liferayBomVersion,
						productInfo.getTargetPlatformVersion())) {

					return _decodeBundleUrl(productInfo);
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to get bundle url from productInfo", exception);
		}

		return null;
	}

	private File _getpomXMLFile(File dir) {
		File workspaceDir = _getWorkspaceDir(dir);

		if (Objects.nonNull(workspaceDir)) {
			return new File(workspaceDir, _POM_XML_FILE_NAME);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private synchronized Map<String, Object> _getProductInfos() {
		JsonSlurper jsonSlurper = new JsonSlurper();

		DownloadCommand downloadCommand = new DownloadCommand();

		downloadCommand.setCacheDir(_workspaceCacheDir);
		downloadCommand.setConnectionTimeout(5000);
		downloadCommand.setPassword(null);
		downloadCommand.setToken(false);
		downloadCommand.setUserName(null);
		downloadCommand.setQuiet(true);

		Path productInfoJsonPath = null;

		try {
			downloadCommand.setUrl(new URL(_PRODUCT_INFO_URL));
			downloadCommand.execute();

			productInfoJsonPath = downloadCommand.getDownloadPath();

			return (Map<String, Object>)jsonSlurper.parse(
				productInfoJsonPath.toFile());
		}
		catch (Exception exception1) {
			try {
				downloadCommand.setUrl(new URL(_CDN_PRODUCT_INFO_URL));
				downloadCommand.execute();

				productInfoJsonPath = downloadCommand.getDownloadPath();

				return (Map<String, Object>)jsonSlurper.parse(
					productInfoJsonPath.toFile());
			}
			catch (Exception exception2) {
				if (_workspaceCacheDir.exists()) {
					try {
						File localProductInfoFile = new File(
							_workspaceCacheDir, ".product_info.json");

						if (localProductInfoFile.exists()) {
							return (Map<String, Object>)jsonSlurper.parse(
								localProductInfoFile);
						}
					}
					catch (Exception exception3) {
						throw new RuntimeException(
							"Unable read product info", exception3);
					}
				}

				throw new RuntimeException(
					"Unable download product info", exception2);
			}
		}
	}

	private File _getWorkspaceDir(File dir) {
		File mavenParent = _findWorkspacePomFile(dir);

		if (Objects.isNull(mavenParent)) {
			return null;
		}

		if (_isWorkspacePomFile(new File(mavenParent, _POM_XML_FILE_NAME))) {
			return mavenParent;
		}

		File mavenPom = new File(dir, _POM_XML_FILE_NAME);

		if (mavenPom.exists() && _isWorkspacePomFile(mavenPom)) {
			return dir;
		}

		return null;
	}

	private boolean _isWorkspacePomFile(File pomFile) {
		boolean pom = false;

		if (Objects.equals(_POM_XML_FILE_NAME, pomFile.getName()) &&
			pomFile.exists()) {

			pom = true;
		}

		if (pom) {
			try {
				String content = FileUtil.read(pomFile.toPath());

				if (content.contains("portal.tools.bundle.support")) {
					return true;
				}
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		return false;
	}

	private Date _parseDate(String releaseDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		try {
			return simpleDateFormat.parse(releaseDate);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(
				"Unable to read release_date", parseException);
		}
	}

	private static final String _CDN_PRODUCT_INFO_URL =
		"https://releases-cdn.liferay.com/tools/workspace/.product_info.json";

	private static final String _DEFAULT_WORKSPACE_CACHE_DIR_NAME =
		".liferay/workspace";

	private static final String _POM_XML_FILE_NAME = "pom.xml";

	private static final String _PRODUCT_INFO_URL =
		"https://releases.liferay.com/tools/workspace/.product_info.json";

	private static final File _workspaceCacheDir = new File(
		System.getProperty("user.home"), _DEFAULT_WORKSPACE_CACHE_DIR_NAME);

}