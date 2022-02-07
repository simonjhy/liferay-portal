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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gregory Amerson
 */
public class FileFunctions {

	public static void copyFile(Path srcPath, Path destPath)
		throws IOException {

		if ((srcPath == null) || (destPath == null) || !Files.exists(srcPath)) {
			return;
		}

		if (Files.isDirectory(srcPath)) {
			copyFolder(srcPath, destPath);
		}
		else {
			Path destParent = destPath.getParent();

			Files.createDirectories(destParent);

			Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static void copyFolder(Path src, Path dest) throws IOException {
		try (Stream<Path> paths = Files.walk(src)) {
			paths.forEach(
				source -> _copy(source, dest.resolve(src.relativize(source))));
		}
	}

	public static void deleteDir(Path dirPath) throws IOException {
		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void deleteDirIfExists(Path dirPath) throws IOException {
		if (Files.exists(dirPath)) {
			deleteDir(dirPath);
		}
	}

	public static boolean deleteQuietly(File file) {
		if (file == null) {
			return false;
		}

		try {
			if (file.isDirectory()) {
				deleteDir(file.toPath());
			}
		}
		catch (final Exception exception) {
		}

		try {
			return file.delete();
		}
		catch (final Exception exception) {
			return false;
		}
	}

	public static String getFileSafeName(String name) {
		return name.replaceAll("[^a-zA-Z0-9-_]", "_");
	}

	public static void moveFile(Path srcPath, Path destPath)
		throws IOException {

		if ((srcPath == null) || (destPath == null) || !Files.exists(srcPath)) {
			return;
		}

		Files.createDirectories(destPath.getParent());

		if (Files.isDirectory(srcPath) && Files.exists(destPath)) {
			try (Stream<Path> destPaths = Files.list(destPath)) {
				if (destPaths.count() > 0) {
					try (Stream<Path> srcPaths = Files.list(srcPath)) {
						List<IOException> moveErrors = srcPaths.map(
							newSrcPath -> {
								try {
									moveFile(
										newSrcPath,
										destPath.resolve(
											srcPath.relativize(newSrcPath)));

									return null;
								}
								catch (IOException ioException) {
									return ioException;
								}
							}
						).filter(
							Objects::nonNull
						).collect(
							Collectors.toList()
						);

						if (!moveErrors.isEmpty()) {
							throw moveErrors.get(0);
						}
					}
				}
			}
		}
		else {
			Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static String read(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[8192];
		int offset = 0;

		while (true) {
			int count = inputStream.read(
				buffer, offset, buffer.length - offset);

			if (count == -1) {
				break;
			}

			offset += count;

			if (offset == buffer.length) {
				byte[] newBuffer = new byte[buffer.length << 1];

				System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

				buffer = newBuffer;
			}
		}

		if (offset == 0) {
			return "";
		}

		return new String(buffer, 0, offset, "UTF-8");
	}

	public static String removeExtension(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

	private static void _copy(Path source, Path dest) {
		try {
			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (Exception exception) {
		}
	}

}