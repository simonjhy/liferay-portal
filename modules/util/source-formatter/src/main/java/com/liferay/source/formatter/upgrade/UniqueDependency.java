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

import java.util.List;
import java.util.Objects;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.InputLocation;

/**
 * @author Raymond Augé
 */
public class UniqueDependency extends Dependency {

	public UniqueDependency(Dependency delegate) {
		_delegate = delegate;
	}

	public void addExclusion(Exclusion exclusion) {
		_delegate.addExclusion(exclusion);
	}

	public void clearManagementKey() {
		_delegate.clearManagementKey();
	}

	public Dependency clone() {
		return _delegate.clone();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Dependency)) {
			return false;
		}

		Dependency other = (Dependency)obj;

		if (!Objects.equals(getGroupId(), other.getGroupId()) ||
			!Objects.equals(getArtifactId(), other.getArtifactId()) ||
			!Objects.equals(getClassifier(), other.getClassifier())) {

			return false;
		}

		return true;
	}

	public String getArtifactId() {
		return _delegate.getArtifactId();
	}

	public String getClassifier() {
		return _delegate.getClassifier();
	}

	public List<Exclusion> getExclusions() {
		return _delegate.getExclusions();
	}

	public String getGroupId() {
		return _delegate.getGroupId();
	}

	public InputLocation getLocation(Object key) {
		return _delegate.getLocation(key);
	}

	public String getManagementKey() {
		return _delegate.getManagementKey();
	}

	public String getOptional() {
		return _delegate.getOptional();
	}

	public String getScope() {
		return _delegate.getScope();
	}

	public String getSystemPath() {
		return _delegate.getSystemPath();
	}

	public String getType() {
		return _delegate.getType();
	}

	public String getVersion() {
		return _delegate.getVersion();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getGroupId(), getArtifactId(), getClassifier());
	}

	public boolean isOptional() {
		return _delegate.isOptional();
	}

	public void removeExclusion(Exclusion exclusion) {
		_delegate.removeExclusion(exclusion);
	}

	public void setArtifactId(String artifactId) {
		_delegate.setArtifactId(artifactId);
	}

	public void setClassifier(String classifier) {
		_delegate.setClassifier(classifier);
	}

	public void setExclusions(List<Exclusion> exclusions) {
		_delegate.setExclusions(exclusions);
	}

	public void setGroupId(String groupId) {
		_delegate.setGroupId(groupId);
	}

	public void setLocation(Object key, InputLocation location) {
		_delegate.setLocation(key, location);
	}

	public void setOptional(boolean optional) {
		_delegate.setOptional(optional);
	}

	public void setOptional(String optional) {
		_delegate.setOptional(optional);
	}

	public void setOtherLocation(Object key, InputLocation location) {
		_delegate.setOtherLocation(key, location);
	}

	public void setScope(String scope) {
		_delegate.setScope(scope);
	}

	public void setSystemPath(String systemPath) {
		_delegate.setSystemPath(systemPath);
	}

	public void setType(String type) {
		_delegate.setType(type);
	}

	public void setVersion(String version) {
		_delegate.setVersion(version);
	}

	public String toString() {
		return _delegate.toString();
	}

	private static final long serialVersionUID = -3044878387980005658L;

	private final Dependency _delegate;

}