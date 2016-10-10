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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mcourcy
 *
 */
public class TestDocUtilMD5 {
	
	@SuppressWarnings("resource")
	@Test
	public void testcalculateMD5(){
		File testDir = FileUtil.prepareEmptyDirectory(TestDocUtilMD5.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_original.odt", testDir, "Sandokai.odt");
		FileUtil.copyFromResources("docs/1992/Sandokai_exact_copy.odt", testDir, "Sandokai_exact_copy.odt");
		FileUtil.copyFromResources("docs/1992/Sandokai_one_extra_character.odt", testDir, "Sandokai_one_extra_character.odt");
		File sandokai = new File(testDir,"Sandokai.odt");
		File sandokai_one_extra_character = new File(testDir,"Sandokai_one_extra_character.odt");
		File sandokai_exact_copy = new File(testDir,"Sandokai_exact_copy.odt");
		String md51 = FileUtil.calculateMD5(sandokai);		
		String md52 = FileUtil.calculateMD5(sandokai_one_extra_character);
		String md53 = FileUtil.calculateMD5(sandokai_exact_copy);	
		Assert.assertNotEquals(md51, md52);
		Assert.assertEquals(md51, md53);
	}
	
	
	
}
