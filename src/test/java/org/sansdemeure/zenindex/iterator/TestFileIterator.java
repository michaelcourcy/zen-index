/**
 * 
 */
package org.sansdemeure.zenindex.iterator;

import java.io.File;

import org.junit.Test;
import org.sansdemeure.zenindex.util.FileUtil;

import org.junit.Assert;

/**
 * @author mcourcy
 *
 */
public class TestFileIterator {
	
	@Test
	public void testIteration(){
		File testDir = FileUtil.prepareEmptyDirectory(TestFileIterator.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
		FileUtil.copyFromResources("docs/1992/Dix_tableaux_du_taureau.odt", testDir, "10tableaux.odt");
		File sub = new File(testDir, "sub");
		sub.mkdir();
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", sub, "Sandokai.odt");
		FileUtil.copyFromResources("docs/1992/Dix_tableaux_du_taureau.odt", sub, "10tableaux.odt");
		sub = new File(testDir, "sub2");
		sub.mkdir();
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", sub, "Sandokai.odt");
		FileUtil.copyFromResources("docs/1992/Dix_tableaux_du_taureau.odt", sub, "10tableaux.odt");
		sub = new File(sub, "subOfSub2");
		sub.mkdir();
		
		int compteur = 0;
		DirectoryIterator it = new DirectoryIterator(testDir);
		while (it.hasNext()){
			compteur++;
			System.out.println(it.next().getAbsolutePath());
		}
		Assert.assertEquals(9, compteur);
	}

}
