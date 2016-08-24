package org.sansdemeure.zenindex.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
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
		File src = new File(classLoader.getResource(resourcePath).getFile());
		File target = new File(targetDir, targetName);
		logger.debug("Copying the {} to {} with the name {} ", resourcePath, targetDir.getAbsolutePath(), targetName);
		try {
			Files.copy(Paths.get(src.getAbsolutePath()), Paths.get(target.getAbsolutePath()), REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Unable to copy the " + resourcePath + " to " + targetDir.getAbsolutePath()
					+ " with the name " + targetName, e);
		}
	}

	

}
