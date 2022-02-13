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
import java.util.Optional;

/**
 * @author Gregory Amerson
 */
public class GAV {

	public GAV() {
		_groupIdOptional = Optional.empty();
		_artifactIdOptional = Optional.empty();
		_versionOptional = Optional.empty();
	}

	public GAV(Object groupId, Object artifactId, Object version) {
		_groupIdOptional = Optional.ofNullable(groupId);
		_artifactIdOptional = Optional.ofNullable(artifactId);
		_versionOptional = Optional.ofNullable(version);
	}

	public GAV(String jarName) {
		_jarName = jarName;

		_groupIdOptional = Optional.empty();
		_artifactIdOptional = Optional.empty();
		_versionOptional = Optional.empty();
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GAV)) {
			return false;
		}

		GAV other = (GAV)object;

		if (!Objects.equals(getGroupId(), other.getGroupId()) ||
			!Objects.equals(getArtifactId(), other.getArtifactId()) ||
			!Objects.equals(getVersion(), other.getVersion())) {

			return false;
		}

		return true;
	}

	public String getArtifactId() {
		return _map(_artifactIdOptional);
	}

	public String getGroupId() {
		return _map(_groupIdOptional);
	}

	public String getJarName() {
		return _jarName;
	}

	public String getVersion() {
		return _map(_versionOptional);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getGroupId(), getArtifactId(), getVersion());
	}

	public boolean isRemove() {
		return _remove;
	}

	public boolean isUnknown() {
		if (!_groupIdOptional.isPresent() || !_artifactIdOptional.isPresent() ||
			!_versionOptional.isPresent()) {

			return true;
		}

		return false;
	}

	public void setArtifactId(String artifactId) {
		_artifactIdOptional = Optional.of(artifactId);
	}

	public void setGroupId(String groupId) {
		_groupIdOptional = Optional.of(groupId);
	}

	public void setRemove(boolean remove) {
		_remove = remove;
	}

	public void setVersion(String version) {
		_versionOptional = Optional.of(version);
	}

	public String toCompileDependency() {
		if (isUnknown()) {
			return MessageFormat.format(
				"// Unknown dependency: {0}", getJarName());
		}

		return MessageFormat.format(
			"compile group: \"{0}\", name: \"{1}\", version: \"{2}\"",
			getGroupId(), getArtifactId(), getVersion());
	}

	@Override
	public String toString() {
		if (_jarName != null) {
			return MessageFormat.format("JAR:{0}", getJarName());
		}

		return MessageFormat.format(
			"GAV:{0}:{1}:{2}", getGroupId(), getArtifactId(), getVersion());
	}

	public String toTPDependency(String configuration) {
		return MessageFormat.format(
			"{0} group: \"{1}\", name: \"{2}\"", configuration, getGroupId(),
			getArtifactId());
	}

	private String _map(Optional<Object> objectOptional) {
		return objectOptional.map(
			String.class::cast
		).orElse(
			"<unknown>"
		);
	}

	private Optional<Object> _artifactIdOptional;
	private Optional<Object> _groupIdOptional;
	private String _jarName;
	private boolean _remove;
	private Optional<Object> _versionOptional;

}