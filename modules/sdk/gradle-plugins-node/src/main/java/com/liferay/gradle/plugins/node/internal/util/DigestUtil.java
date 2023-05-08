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

package com.liferay.gradle.plugins.node.internal.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.gradle.api.GradleException;
import org.gradle.api.UncheckedIOException;

import com.liferay.gradle.util.Validator;

/**
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
public class DigestUtil {

	public static String getDigest(File digestFile) {
		if (!digestFile.exists()) {
			return null;
		}

		byte[] bytes = null;

		try {
			bytes = Files.readAllBytes(digestFile.toPath());
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}

		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static String getDigest(Iterable<File> files) {
		SortedSet<File> sortedFiles = null;

		try {
			sortedFiles = _flattenAndSort(files);
		}
		catch (IOException ioException) {
			throw new GradleException("Unable to flatten files", ioException);
		}

		StringBuilder sb = new StringBuilder();

		for (File file : sortedFiles) {
			if (!file.exists()) {
				continue;
			}

			try {
				List<String> lines = Files.readAllLines(
					file.toPath(), StandardCharsets.UTF_8);

				sb.append(Integer.toHexString(lines.hashCode()));
			}
			catch (IOException ioException) {
				final int BUFFER_SIZE = 8192;
		        try (FileInputStream fis = new FileInputStream(file);
		                BufferedInputStream bis = new BufferedInputStream(fis)) {
		               MessageDigest digest = MessageDigest.getInstance("SHA-2");
		               byte[] buffer = new byte[BUFFER_SIZE];
		               int bytesRead;
		               while ((bytesRead = bis.read(buffer)) != -1) {
		                   digest.update(buffer, 0, bytesRead);
		               }
		               sb.append(asHexString(digest.digest()));
		           } catch (NoSuchAlgorithmException | IOException e) {
		               throw new RuntimeException("SHA-2 algorithm not found", e);
		           }				
			}

			sb.append('-');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

    private static String asHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
	
	public static String getDigest(String... array) {
		StringBuilder sb = new StringBuilder();

		for (String s : array) {
			if (Validator.isNotNull(s)) {
				sb.append(Integer.toHexString(s.hashCode()));
				sb.append('-');
			}
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private static SortedSet<File> _flattenAndSort(Iterable<File> files)
		throws IOException {

		final SortedSet<File> sortedFiles = new TreeSet<>(new FileComparator());

		if (files == null) {
			return sortedFiles;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				Files.walkFileTree(
					file.toPath(),
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult visitFile(
								Path path,
								BasicFileAttributes basicFileAttributes)
							throws IOException {

							sortedFiles.add(path.toFile());

							return FileVisitResult.CONTINUE;
						}

					});
			}
			else {
				sortedFiles.add(file);
			}
		}

		return sortedFiles;
	}

	private static class FileComparator implements Comparator<File> {

		@Override
		public int compare(File file1, File file2) {
			String canonicalPath1 = _getCanonicalPath(file1);
			String canonicalPath2 = _getCanonicalPath(file2);

			return canonicalPath1.compareTo(canonicalPath2);
		}

		private String _getCanonicalPath(File file) {
			String canonicalPath = null;

			try {
				canonicalPath = file.getCanonicalPath();
			}
			catch (IOException ioException) {
				String message = "Unable to get canonical path of " + file;

				throw new UncheckedIOException(message, ioException);
			}

			if (File.separatorChar != '/') {
				canonicalPath = canonicalPath.replace(File.separatorChar, '/');
			}

			return canonicalPath;
		}

	}

}