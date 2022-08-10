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

package com.liferay.gradle.plugins.workspace.task;

import de.undercouch.gradle.tasks.download.Verify;
import de.undercouch.gradle.tasks.download.VerifyAction;

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Simon Jiang
 */
public class VerifyBundleTask extends Verify {

	public VerifyBundleTask() {
		_action = new VerifyBundleAction(getProject());
	}

	@Override
	public void algorithm(String algorithm) {
		_action.algorithm(algorithm);
	}

	@Override
	public void checksum(String checksum) {
		_action.checksum(checksum);
	}

	@Input
	@Optional
	@Override
	public String getAlgorithm() {
		return _action.getAlgorithm();
	}

	@Input
	@Override
	public String getChecksum() {
		return _action.getChecksum();
	}

	@InputFile
	@Override
	public File getSrc() {
		return _action.getSrc();
	}

	@Override
	public void src(Object src) {
		_action.src(src);
	}

	/**
	 * Starts verifying
	 * @throws IOException if the file could not be verified
	 * @throws NoSuchAlgorithmException if the given algorithm is not available
	 */
	@TaskAction
	public void verify() throws IOException, NoSuchAlgorithmException {
		_action.execute();
	}

	private final VerifyAction _action;

}