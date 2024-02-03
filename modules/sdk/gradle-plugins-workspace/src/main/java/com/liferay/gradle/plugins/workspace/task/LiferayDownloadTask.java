/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import de.undercouch.gradle.tasks.download.DownloadAction;
import de.undercouch.gradle.tasks.download.DownloadDetails;
import de.undercouch.gradle.tasks.download.DownloadSpec;

import java.io.File;

import java.util.List;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Console;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Simon Jiang
 */
public class LiferayDownloadTask extends DefaultTask implements DownloadSpec {

	public LiferayDownloadTask() {
		boolean offline = getProject(
		).getGradle(
		).getStartParameter(
		).isOffline();

		_action = new DownloadAction(getProject(), this);

		getOutputs().upToDateWhen(
			task -> !(isOnlyIfModified() || isOverwrite()));

		onlyIf(
			task -> {
				if (offline) {
					for (File f : getOutputFiles()) {
						if (f.exists()) {
							if (!isQuiet()) {
								getProject(
								).getLogger(
								).info(
									"Skipping existing file '" + f.getName() +
										"' in offline mode."
								);
							}
						}
						else {
							throw new IllegalStateException(
								"Unable to download file '" + f.getName() +
									"' in offline mode.");
						}
					}

					return false;
				}

				return true;
			});
	}

	@Override
	public void acceptAnyCertificate(boolean accept) {
		_action.acceptAnyCertificate(accept);
	}

	@Override
	public void body(String body) {
		_action.body(body);
	}

	@Override
	public void cachedETagsFile(Object location) {
		_action.cachedETagsFile(location);
	}

	@Override
	public void compress(boolean compress) {
		_action.compress(compress);
	}

	@Override
	public void connectTimeout(int milliseconds) {
		_action.connectTimeout(milliseconds);
	}

	@Override
	public void dest(Object dest) {
		_action.dest(dest);
	}

	@TaskAction
	public Void download() throws Throwable {
		try {
			_action.execute(
				false
			).exceptionally(
				t -> {
					TaskOutputs taskOutputs = getOutputs();

					FileCollection fileCollection = taskOutputs.getFiles();

					File destFile = fileCollection.getSingleFile();

					if (!destFile.exists()) {
						getState().addFailure(
							new TaskExecutionException(this, t));
					}

					return null;
				}
			).thenRun(
				() -> {
					if (_action.isUpToDate()) {
						getState().setDidWork(false);
					}
				}
			);
		}
		catch (Exception exception) {
			Logger logger = getLogger();

			if (logger.isDebugEnabled()) {
				logger.error(exception.getMessage(), exception);
			}
		}

		return null;
	}

	@Override
	public void downloadTaskDir(Object dir) {
		_action.downloadTaskDir(dir);
	}

	@Override
	public void eachFile(Action<? super DownloadDetails> action) {
		_action.eachFile(action);
	}

	@Input
	@Optional
	@Override
	public String getBody() {
		return _action.getBody();
	}

	@Internal
	@Override
	public File getCachedETagsFile() {
		return _action.getCachedETagsFile();
	}

	@Input
	@Override
	public int getConnectTimeout() {
		return _action.getConnectTimeout();
	}

	@Internal // see #getOutputFiles()
	@Override
	public File getDest() {
		return _action.getDest();
	}

	@Internal
	@Override
	public File getDownloadTaskDir() {
		return _action.getDownloadTaskDir();
	}

	@Override
	public String getHeader(String name) {
		return _action.getHeader(name);
	}

	@Input
	@Optional
	@Override
	public Map<String, String> getHeaders() {
		return _action.getHeaders();
	}

	@Input
	@Optional
	@Override
	public String getMethod() {
		return _action.getMethod();
	}

	/**
	 * @return a list of files created by this task (i.e. the destination files)
	 */
	@OutputFiles
	public List<File> getOutputFiles() {
		return _action.getOutputFiles();
	}

	@Input
	@Optional
	@Override
	public String getPassword() {
		return _action.getPassword();
	}

	@Input
	@Override
	public int getReadTimeout() {
		return _action.getReadTimeout();
	}

	@Input
	@Override
	public int getRetries() {
		return _action.getRetries();
	}

	@Input
	@Override
	public Object getSrc() {
		return _action.getSrc();
	}

	@Input
	@Optional
	@Override
	public Object getUseETag() {
		return _action.getUseETag();
	}

	@Input
	@Optional
	@Override
	public String getUsername() {
		return _action.getUsername();
	}

	@Override
	public void header(String name, String value) {
		_action.header(name, value);
	}

	@Override
	public void headers(Map<String, String> headers) {
		_action.headers(headers);
	}

	@Input
	@Override
	public boolean isAcceptAnyCertificate() {
		return _action.isAcceptAnyCertificate();
	}

	@Input
	@Override
	public boolean isCompress() {
		return _action.isCompress();
	}

	@Input
	@Override
	public boolean isOnlyIfModified() {
		return _action.isOnlyIfModified();
	}

	@Input
	@Override
	public boolean isOnlyIfNewer() {
		return _action.isOnlyIfNewer();
	}

	@Input
	@Override
	public boolean isOverwrite() {
		return _action.isOverwrite();
	}

	@Input
	@Override
	public boolean isPreemptiveAuth() {
		return _action.isPreemptiveAuth();
	}

	@Console
	@Override
	public boolean isQuiet() {
		return _action.isQuiet();
	}

	@Input
	@Override
	public boolean isTempAndMove() {
		return _action.isTempAndMove();
	}

	@Override
	public void method(String method) {
		_action.method(method);
	}

	@Override
	public void onlyIfModified(boolean onlyIfModified) {
		_action.onlyIfModified(onlyIfModified);
	}

	@Override
	public void onlyIfNewer(boolean onlyIfNewer) {
		_action.onlyIfNewer(onlyIfNewer);
	}

	@Override
	public void overwrite(boolean overwrite) {
		_action.overwrite(overwrite);
	}

	@Override
	public void password(String password) {
		_action.password(password);
	}

	@Override
	public void preemptiveAuth(boolean preemptiveAuth) {
		_action.preemptiveAuth(preemptiveAuth);
	}

	@Override
	public void quiet(boolean quiet) {
		_action.quiet(quiet);
	}

	@Override
	public void readTimeout(int milliseconds) {
		_action.readTimeout(milliseconds);
	}

	@Override
	public void retries(int retries) {
		_action.retries(retries);
	}

	@Override
	public void src(Object src) {
		_action.src(src);
	}

	@Override
	public void tempAndMove(boolean tempAndMove) {
		_action.tempAndMove(tempAndMove);
	}

	@Override
	public void useETag(Object useETag) {
		_action.useETag(useETag);
	}

	@Override
	public void username(String username) {
		_action.username(username);
	}

	private final DownloadAction _action;

}