/**
 * 
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
