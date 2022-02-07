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

import java.text.MessageFormat;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lovett Li
 * @author Vernon Singleton
 * @author Gregory Amerson
 * @author Raymond Augé
 */
public class GradleDependency implements Comparable<GradleDependency> {

	public static final Pattern gavShortPattern = Pattern.compile(
		"\\s*[\"']{1}(?<group>[^:]+):(?<name>[^:]+)(:(?<version>[^:]" +
			"+)(:(?<classifier>[^@]+)(@(?<extension>[^@]+))?)?)?[\"']{1}\\s*");
	public static final Pattern groupLongPattern = Pattern.compile(
		".*group\\s*:\\s*[\"'](?<group>[^\"']+).*");
	public static final Pattern nameLongPattern = Pattern.compile(
		".*name\\s*:\\s*[\"'](?<name>[^\"']+).*");
	public static final Pattern versionLongPattern = Pattern.compile(
		".*version\\s*:\\s*[\"'](?<version>[^\"']+).*");

	public GradleDependency(String singleLine) {
		if (!singleLine.matches(".*\\s+.*")) {
			throw new IllegalArgumentException(
				"At least on space is required in Gradle dependency" +
					" definitions: " + singleLine);
		}

		String[] parts = singleLine.split("\\s+", 2);

		_configuration = parts[0];
		String reference = parts[1];

		Matcher matcher;

		if (reference.startsWith("project(") ||
			reference.startsWith("files(")) {

			_group = null;
			_name = null;
			_reference = reference;
			_version = null;
		}
		else {
			Matcher gavShortMatcher =
				matcher = gavShortPattern.matcher(reference);

			if (gavShortMatcher.matches()) {
				_group = matcher.group("group");
				_name = matcher.group("name");
				_reference = null;
				_version = matcher.group("version");
			}
			else {
				Matcher groupLongMatcher = groupLongPattern.matcher(reference);
				Matcher nameLongMatcher = nameLongPattern.matcher(reference);
				Matcher versionLongMatcher = versionLongPattern.matcher(
					reference);

				_group =
					groupLongMatcher.matches() ?
						groupLongMatcher.group("group") : null;
				_name =
					nameLongMatcher.matches() ? nameLongMatcher.group("name") :
						null;
				_reference = null;
				_version = versionLongMatcher.matches() ?
					versionLongMatcher.group("version") : null;
			}
		}

		_lineNumber = -1;
		_lastLineNumber = -1;
	}

	public GradleDependency(
		String configuration, String group, String name, String version,
		int lineNumber, int lastLineNumber) {

		_configuration = configuration;
		_group = group;
		_name = name;
		_version = version;
		_lineNumber = lineNumber;
		_lastLineNumber = lastLineNumber;

		_reference = null;
	}

	public GradleDependency clone() {
		return new GradleDependency(
			_configuration, _group, _name, _version, _lineNumber,
			_lastLineNumber);
	}

	@Override
	public int compareTo(GradleDependency other) {
		return toString().compareTo(other.toString());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GradleDependency)) {
			return false;
		}

		GradleDependency other = (GradleDependency)object;

		if (!Objects.equals(_configuration, other._configuration) ||
			!Objects.equals(_reference, other._reference) ||
			!Objects.equals(_group, other._group) ||
			!Objects.equals(_name, other._name)) {

			return false;
		}

		return true;
	}

	public String getConfiguration() {
		return _configuration;
	}

	public String getGroup() {
		return _group;
	}

	public int getLastLineNumber() {
		return _lastLineNumber;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public String getName() {
		return _name;
	}

	public String getReference() {
		return _reference;
	}

	public String getVersion() {
		return _version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_configuration, _group, _name);
	}

	public void setVersion(String version) {
		_version = version;
	}

	@Override
	public String toString() {
		if (_reference != null) {
			return MessageFormat.format("{0} {1}", _configuration, _reference);
		}

		return MessageFormat.format(
			"{0} group: \"{1}\", name: \"{2}\", version: \"{3}\"",
			_configuration, _group, _name, _version);
	}

	private final String _configuration;
	private final String _group;
	private final int _lastLineNumber;
	private final int _lineNumber;
	private final String _name;
	private final String _reference;
	private String _version;

}