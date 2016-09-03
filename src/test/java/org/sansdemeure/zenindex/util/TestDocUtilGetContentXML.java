/**
 * 
 */
package org.sansdemeure.zenindex.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * @author mcourcy
 *
 */
public class TestDocUtilGetContentXML {

	@Test
	public void testGetContentXML() throws IOException {
		TestAppender testAppender = new TestAppender();
		File testDir = FileUtil.prepareEmptyDirectory(TestDocUtilGetContentXML.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
		File f = new File(testDir, "Sandokai.odt");
		try (ODTResource odtResource = new ODTResource(f)) {
			odtResource.openContentXML();
			testAppender.verify("content.xml found");
		}

	}

}