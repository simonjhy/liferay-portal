/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		_groupId = Optional.empty();
		_artifactId = Optional.empty();
		_version = Optional.empty();
	}

	public GAV(Object groupId, Object artifactId, Object version) {
		_groupId = Optional.ofNullable(groupId);
		_artifactId = Optional.ofNullable(artifactId);
		_version = Optional.ofNullable(version);
	}

	public GAV(String jarName) {
		_jarName = jarName;

		_groupId = Optional.empty();
		_artifactId = Optional.empty();
		_version = Optional.empty();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GAV)) {
			return false;
		}

		GAV other = (GAV)obj;

		if (!Objects.equals(getGroupId(), other.getGroupId()) ||
			!Objects.equals(getArtifactId(), other.getArtifactId()) ||
			!Objects.equals(getVersion(), other.getVersion())) {

			return false;
		}

		return true;
	}

	public String getArtifactId() {
		return _map(_artifactId);
	}

	public String getGroupId() {
		return _map(_groupId);
	}

	public String getJarName() {
		return _jarName;
	}

	public String getVersion() {
		return _map(_version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getGroupId(), getArtifactId(), getVersion());
	}

	public boolean isRemove() {
		return _remove;
	}

	public boolean isUnknown() {
		if (!_groupId.isPresent() || !_artifactId.isPresent() ||
			!_version.isPresent()) {

			return true;
		}

		return false;
	}

	public void setArtifactId(String artifactId) {
		_artifactId = Optional.of(artifactId);
	}

	public void setGroupId(String groupId) {
		_groupId = Optional.of(groupId);
	}

	public void setRemove(boolean remove) {
		_remove = remove;
	}

	public void setVersion(String version) {
		_version = Optional.of(version);
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

	private String _map(Optional<Object> object) {
		return object.map(
			String.class::cast
		).orElse(
			"<unknown>"
		);
	}

	private Optional<Object> _artifactId;
	private Optional<Object> _groupId;
	private String _jarName;
	private boolean _remove;
	private Optional<Object> _version;

}