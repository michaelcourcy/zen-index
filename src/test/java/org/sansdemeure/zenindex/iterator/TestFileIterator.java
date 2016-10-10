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
package org.sansdemeure.zenindex.iterator;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.sansdemeure.zenindex.util.FileUtil;

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
