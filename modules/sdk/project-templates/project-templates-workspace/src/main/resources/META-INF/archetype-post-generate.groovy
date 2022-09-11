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

@Grab('com.liferay:com.liferay.portal.tools.bundle.support:3.7.3')
@Grab('org.jdom:jdom2:2.0.6.1')
@Grab('org.codehaus.groovy:groovy-json:2.5.11')
@Grab('org.json:json:20190722')

import com.liferay.portal.tools.bundle.support.commands.DownloadCommand
import java.util.Map
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import groovy.json.JsonSlurper
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.io.File
import java.util.Objects
import java.math.BigInteger
import java.net.URI
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

_DEFAULT_WORKSPACE_CACHE_DIR_NAME = ".liferay/workspace"
_POM_XML_FILE_NAME = "pom.xml"
_PRODUCT_INFO_URL = "https://releases.liferay.com/tools/workspace/.product_info.json"
_workspaceCacheDir = new File(System.getProperty("user.home"), _DEFAULT_WORKSPACE_CACHE_DIR_NAME)

class ProductInfo {

ProductInfo(Map<String, String> productMap) {
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

String _safeGet(Map<String, String> map, String key, String defVal) {
	 if (map != null){
		 return map.get(key)
	 }
	 else{
	   return defVal
	 }
}

String getAppServerTomcatVersion() {
	  return _appServerTomcatVersion;
}

String getBundleUrl() {
	  return _bundleUrl;
}

String getLiferayDockerImage() {
	  return _liferayDockerImage;
}

String getLiferayProductVersion() {
	  return _liferayProductVersion;
}

String getReleaseDate() {
	  return _releaseDate;
}

String getTargetPlatformVersion() {
	  return _targetPlatformVersion;
}

boolean isPromoted() {
	  return _promoted;
}

String _appServerTomcatVersion;
String _bundleUrl;
String _liferayDockerImage;
String _liferayProductVersion;
Boolean _promoted = false;
String _releaseDate;
String _targetPlatformVersion;
}

File _getpomXMLFile(File dir) {

	File workspaceDir = _getWorkspaceDir(dir);

	if (Objects.nonNull(workspaceDir)){
		return new File(workspaceDir, "pom.xml");
	}

	return null;
}

File _findWorkspacePomFile(File dir) {
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

	File file = new File(dir, "pom.xml");

	if (file.exists() && _isWorkspacePomFile(file)) {
		return dir;
	}

	return _findWorkspacePomFile(dir.getParentFile());
}

boolean _isWorkspacePomFile(File pomFile) {
	boolean pom = false;

	if (Objects.equals("pom.xml", pomFile.getName()) &&
		pomFile.exists()) {

		pom = true;
	}

	if (pom) {
		try {
			String content = pomFile.getText("UTF-8");

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

String _getBundleURLFromProduct(String liferayBomVersion) {
	try {
		Map<String, Object> productInfos = _getProductInfos();

		if (Objects.isNull(productInfos)) {
			return null;
		}

		for (Map.Entry<String, Object> entryKey :
				productInfos.entrySet()) {

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

synchronized Map<String, Object> _getProductInfos() {

	Map<String, Object> productInfoMap = null;

	JsonSlurper jsonSlurper = new JsonSlurper();

	try {
		DownloadCommand downloadCommand = new DownloadCommand();

		downloadCommand.setCacheDir(_workspaceCacheDir);
		downloadCommand.setConnectionTimeout(5000);
		downloadCommand.setPassword(null);
		downloadCommand.setToken(false);
		downloadCommand.setUrl(new URL(_PRODUCT_INFO_URL));
		downloadCommand.setUserName(null);
		downloadCommand.setQuiet(true);

		downloadCommand.execute();

		productInfoJsonPath = downloadCommand.getDownloadPath();

		return jsonSlurper.parse(productInfoJsonPath.toFile());
	}
	catch (Exception exception) {
		throw new RuntimeException(
			"Unable download product info", exception);
	}

	return null;
}

File _getWorkspaceDir(File dir) {
	File mavenParent = _findWorkspacePomFile(dir);

	if (Objects.isNull(mavenParent)){
		return null;
	}

	if (_isWorkspacePomFile(new File(mavenParent, "pom.xml"))) {
		return mavenParent;
	}

	File mavenPom = new File(dir, "pom.xml");

	if (mavenPom.exists() && _isWorkspacePomFile(mavenPom)) {
		return dir;
	}

	return null;
}

void _addBundleUrlProperties(File baseDir) {
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
			"http://xml.org/sax/features/external-parameter-entities",false);

		documentBuilderFactory.setXIncludeAware(false);
		documentBuilderFactory.setExpandEntityReferences(false);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		def pomFile = _getpomXMLFile(baseDir)

		if (Objects.isNull(pomFile)){
			return;
		}

		Document document = documentBuilder.parse(pomFile);

		Element documentElement = document.getDocumentElement();

		documentElement.normalize();

		NodeList propertiesNodeList = document.getElementsByTagName("properties");

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

				Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

				StreamResult result = new StreamResult(new File(projectPath.toFile(), "pom.xml"));

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

Date _parseDate(String releaseDate) throws ParseException {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	try {
		return simpleDateFormat.parse(releaseDate);
	} catch (ParseException parseException) {
		throw new RuntimeException(
			"Unable to read release_date", excepparseExceptiontion);
	}
}

String _decodeBundleUrl(ProductInfo productInfo) {
	Base64.Decoder decoder = Base64.getUrlDecoder();

	byte[] byteArray = decoder.decode(productInfo.getBundleUrl());
	Date parsedReleaseDate = _parseDate(productInfo.getReleaseDate());
	Calendar calendar = new GregorianCalendar();

	calendar.setTime(parsedReleaseDate);

	BigInteger bigInteger = new BigInteger(byteArray);

	bigInteger = bigInteger.shiftRight(calendar.get(5));

	return new String(bigInteger.toByteArray());
}

Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

Path buildGradlePath = projectPath.resolve("build.gradle")
Path propertiesPath = projectPath.resolve("gradle.properties")
Path propertiesLocalPath = projectPath.resolve("gradle-local.properties")
Path settingsGradlePath = projectPath.resolve("settings.gradle")

Files.deleteIfExists buildGradlePath
Files.deleteIfExists propertiesPath
Files.deleteIfExists propertiesLocalPath
Files.deleteIfExists settingsGradlePath

_addBundleUrlProperties(projectPath.toFile());