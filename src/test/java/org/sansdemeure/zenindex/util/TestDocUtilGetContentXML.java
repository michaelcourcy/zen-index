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
