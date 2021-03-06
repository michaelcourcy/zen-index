/*
 * This file is part of zen-index.

    zen-index is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    zen-index is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with zen-index.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sansdemeure.zenindex.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static File prepareEmptyDirectory(Class<?> testClass){
		logger.trace("preparing empty directory for {}", testClass.getSimpleName());
		File homeDir = new File(System.getProperty("user.home"));
		File testDir = new File(homeDir, "zenindextest");
		if (!testDir.exists()) {
			logger.debug("Directory {} does not exist yet", testDir.getAbsolutePath());
			testDir.mkdirs();
		}
		File testDirClass = new File(testDir, testClass.getSimpleName());
		if (testDirClass.exists()) {
			logger.debug("Deleting directory {}", testDirClass.getAbsolutePath());
			deleteDir(testDirClass);
		}

		logger.debug("Creating directory {}", testDirClass.getAbsolutePath());
		testDirClass.mkdirs();

		return testDirClass;
	}
	
	public static void deleteDir(File file) {
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				deleteDir(f);
			}
		}
		if (!file.delete()){
			logger.error("Unable to delete {}", file.getAbsolutePath());
		}
	}

	/**
	 * Copy a file from the resources directory to the target directory with the
	 * targetName you choose.
	 * 
	 * @param resourcePath
	 * @param targetDir
	 * @param targetName
	 */
	public static void copyFromResources(String resourcePath, File targetDir, String targetName) {
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		URL url = classLoader.getResource(resourcePath);
		if (url==null){
			throw new RuntimeException("Problem loading " + resourcePath);
		}
		File src = new File(url.getFile());
		File target = new File(targetDir, targetName);
		logger.debug("Copying the {} to {} with the name {} ", resourcePath, targetDir.getAbsolutePath(), targetName);
		try {
			Files.copy(Paths.get(src.getAbsolutePath()), Paths.get(target.getAbsolutePath()), REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Unable to copy the " + resourcePath + " to " + targetDir.getAbsolutePath()
					+ " with the name " + targetName, e);
		}
	}
	
	public static String changeExtensionToHtml(String fileName){
		return fileName.substring(0,fileName.lastIndexOf('.'))+".html";
	}

	public static String removeExtension(String fileName) {
		return fileName.substring(0,fileName.lastIndexOf("."));
	}

	public static String findOriginals(File[] files, File f) {
		String name = removeExtension(f.getName());
		List<String> origininals = new ArrayList<>();
		List<String> potentialOrigininals = Arrays.asList(
				name + ".pdf",
				name + ".doc",
				name + ".docx");
		for (File fc : files){
			if (potentialOrigininals.contains(fc.getName())){
				origininals.add(fc.getName());
			}
		}
		return String.join(",", origininals);
	}
	
	public static String calculateMD5(File file) {
		InputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			fis = new FileInputStream(file);
			byte[] dataBytes = new byte[1024];

			int nread = 0;

			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
			;

			byte[] mdbytes = md.digest();

			// convert the byte to hex format
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	

}
