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

package com.liferay.source.formatter.upgrade.util;

import com.liferay.source.formatter.upgrade.LugbotConfig;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CheckoutResult;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.DeleteBranchCommand;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.RawParseUtils;

/**
 * @author Gregory Amerson
 */
public class GitFunctions {

	public static final String REFS_HEADS = "refs/heads/";

	public static Ref checkoutBranch(
			Path repoPath, String branchName, boolean resetHard)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			CheckoutCommand checkoutCommand = git.checkout();

			checkoutCommand.setForced(resetHard);
			checkoutCommand.setName(branchName);

			Ref ref = checkoutCommand.call();

			if (resetHard) {
				StatusCommand statusCommand = git.status();

				Status status = statusCommand.call();

				Set<String> untracked = status.getUntracked();

				List<Exception> exceptions = new ArrayList<>();

				untracked.stream(
				).map(
					repoPath::resolve
				).filter(
					Files::exists
				).forEach(
					path -> {
						try {
							Files.delete(path);
						}
						catch (IOException e) {
							exceptions.add(e);
						}
					}
				);

				if (!exceptions.isEmpty()) {
					throw new IOException(exceptions.get(0));
				}

				statusCommand = git.status();

				status = statusCommand.call();

				untracked = status.getUntracked();

				if (!untracked.isEmpty()) {
					String untrackedFiles = untracked.stream(
					).collect(
						Collectors.joining(System.lineSeparator())
					);

					throw new IOException(
						"Unable to reset branch to " + branchName +
							"\nThe following files are untracked:\n" +
								untrackedFiles);
				}
			}

			return ref;
		}
	}

	public static Set<String> cleanRepo(Path repoPath)
		throws GitAPIException, IOException, NoWorkTreeException {

		try (Git git = Git.open(repoPath.toFile())) {
			CleanCommand cleanCommand = git.clean();

			cleanCommand.setCleanDirectories(true);
			cleanCommand.setForce(true);
			cleanCommand.setIgnore(false);

			return cleanCommand.call();
		}
	}

	public static Optional<RevCommit> commitChanges(
			Path repoPath, String message, Collection<String> addFilePatterns,
			LugbotConfig lugbotConfig)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			StatusCommand statusCommand = git.status();

			Status status = statusCommand.call();

			if (status.isClean()) {
				return Optional.empty();
			}

			if (!addFilePatterns.isEmpty()) {
				AddCommand addCommand = git.add();

				String osProperty = System.getProperty("os.name");
				
				
				if (System.getProperty(
						"os.name"
					).toLowerCase(
					).startsWith(
						"win"
					)) {

					addFilePatterns = addFilePatterns.stream(
					).map(
						pattern -> pattern.replaceAll("\\\\", "/")
					).collect(
						Collectors.toList()
					);
				}

				addFilePatterns.stream(
				).map(
					p -> p.isEmpty() ? "." : p
				).forEach(
					addCommand::addFilepattern
				);

				addCommand.call();
			}

			CommitCommand commitCommand = git.commit();

			String personIdent = Optional.ofNullable(
				lugbotConfig.commiterIdentity
			).orElse(
				"Lugbot <lugbot@liferay.com>"
			);

			PersonIdent enteredAuthor = RawParseUtils.parsePersonIdent(
				personIdent);
			PersonIdent enteredCommitter = RawParseUtils.parsePersonIdent(
				personIdent);

			Date commitDate = new Date();
			TimeZone timeZone = TimeZone.getDefault();

			PersonIdent authorIdent = new PersonIdent(
				enteredAuthor, commitDate, timeZone);
			PersonIdent committerIdent = new PersonIdent(
				enteredCommitter, commitDate, timeZone);

			commitCommand.setAuthor(authorIdent);
			commitCommand.setCommitter(committerIdent);

			CommitCommand allCommitCommand = commitCommand.setAll(true);

			CommitCommand allCommitMessageCommand =
				allCommitCommand.setMessage(message);

			RevCommit commitRevCommit = allCommitMessageCommand.call();

			return Optional.of(commitRevCommit);
		}
	}

	public static Optional<RevCommit> commitChanges(
			Path repoPath, String message, LugbotConfig lugbotConfig)
		throws GitAPIException, IOException {

		return commitChanges(
			repoPath, message, Collections.emptySet(), lugbotConfig);
	}

	public static CheckoutResult createBranch(Path repoPath, String branchName)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			CheckoutCommand checkoutCommand = git.checkout();

			checkoutCommand.setCreateBranch(true);
			checkoutCommand.setName(branchName);

			checkoutCommand.call();

			return checkoutCommand.getResult();
		}
	}

	public static List<String> deleteBranch(Path repoPath, String branchName)
		throws GitAPIException, IOException {

		return deleteBranch(repoPath, branchName, false);
	}

	public static List<String> deleteBranch(
			Path repoPath, String branchName, boolean force)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			DeleteBranchCommand deleteBranchCommand = git.branchDelete();

			deleteBranchCommand.setBranchNames(branchName);
			deleteBranchCommand.setForce(force);

			return deleteBranchCommand.call();
		}
	}

	public static List<DiffEntry> diff(
			Path repoPath, String newRefStr, String oldRefStr,
			Path diffOutputPath)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			ObjectId head = repository.resolve(newRefStr);
			ObjectId oldHead = repository.resolve(oldRefStr);

			try (ObjectReader reader = repository.newObjectReader();
				FileOutputStream fosFileOutputStream = new FileOutputStream(
					diffOutputPath.toFile())) {

				CanonicalTreeParser newTree = new CanonicalTreeParser();

				newTree.reset(reader, head);

				CanonicalTreeParser oldTree = new CanonicalTreeParser();

				oldTree.reset(reader, oldHead);

				DiffCommand diffCommand = git.diff();

				diffCommand.setNewTree(newTree);
				diffCommand.setOldTree(oldTree);
				diffCommand.setOutputStream(fosFileOutputStream);

				return diffCommand.call();
			}
		}
	}

	public static List<Ref> getBranches(Path repoPath)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			ListBranchCommand listBranchCommand = git.branchList();

			return listBranchCommand.call();
		}
	}

	public static RevCommit getCommit(Path repoPath, Ref ref)
		throws IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			try (RevWalk revWalk = new RevWalk(repository)) {
				return revWalk.parseCommit(ref.getObjectId());
			}
		}
	}

	public static List<RevCommit> getCommits(
			Path repoPath, Ref fromRef, Ref toRef)
		throws IOException {

		List<RevCommit> commits = new ArrayList<>();

		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			try (RevWalk revWalk = new RevWalk(repository)) {
				RevCommit startCommit = revWalk.parseCommit(
					toRef.getObjectId());

				revWalk.markStart(startCommit);

				for (RevCommit revCommit : revWalk) {
					if (Objects.equals(
							revCommit.getId(), fromRef.getObjectId())) {

						break;
					}

					commits.add(revCommit);
				}
			}
		}

		return commits;
	}

	public static Ref getCurrentBranch(Path repoPath) throws IOException {
		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			return repository.findRef(repository.getBranch());
		}
	}

	public static String getCurrentBranchName(Path repoPath)
		throws IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			return repository.getBranch();
		}
	}

	public static List<String> getLocalBrancheNames(Path repoPath)
		throws IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			RefDatabase refDatabase = repository.getRefDatabase();

			List<Ref> headRefs = refDatabase.getRefsByPrefix(REFS_HEADS);

			return headRefs.stream(
			).map(
				ref -> ref.getName()
			).map(
				name -> name.substring(REFS_HEADS.length())
			).collect(
				Collectors.toList()
			);
		}
	}

	public static Status getStatus(Path repoPath)
		throws GitAPIException, IOException, NoWorkTreeException {

		try (Git git = Git.open(repoPath.toFile())) {
			StatusCommand statusCommand = git.status();

			return statusCommand.call();
		}
	}

	public static boolean isGitRepo(Path repositoryPath) {
		try (Git git = Git.open(repositoryPath.toFile())) {
			return true;
		}
		catch (IOException ioException) {
		}

		return false;
	}

	public static MergeResult mergeBranch(Path repoPath, String branchName)
		throws GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			Repository repository = git.getRepository();

			ObjectId objectId = repository.resolve(branchName);

			if (objectId == null) {
				objectId = repository.resolve("refs/heads/" + branchName);

				if (objectId == null) {
					throw new IllegalArgumentException(
						MessageFormat.format(
							"Could not find objectId for {0}", branchName));
				}
			}

			MergeCommand mergeCommand = git.merge();

			mergeCommand.include(objectId);
			mergeCommand.setCommit(true);
			mergeCommand.setFastForward(MergeCommand.FastForwardMode.NO_FF);
			mergeCommand.setMessage(
				MessageFormat.format(
					"Merged {0} into {1}", branchName, repository.getBranch()));
			mergeCommand.setStrategy(MergeStrategy.RECURSIVE);

			return mergeCommand.call();
		}
	}

	public static Ref resetRepo(Path repoPath)
		throws CheckoutConflictException, GitAPIException, IOException {

		try (Git git = Git.open(repoPath.toFile())) {
			ResetCommand resetCommand = git.reset();

			resetCommand.setMode(ResetCommand.ResetType.HARD);

			return resetCommand.call();
		}
	}

}