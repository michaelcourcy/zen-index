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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestRegexpUtil {
	
	@Test
	public void testExtraInfos(){
		String s = "hishihiryo [pertinence:3] [subCat:japan budhism]";
		List<Pair<String, String>> l = RegexpUtil.getExtraInfos(s);
		Assert.assertEquals(2, l.size());
	}
	
	@Test
	public void testJustTheWordWithoutExtraInfo(){
		String s = "hishihiryo [pertinence:3] [subCat:japan budhism]";
		String r = RegexpUtil.getTheWordWithoutExtraInfo(s);
		Assert.assertEquals("hishihiryo", r);
		
		s = "hishihiryo ";
		r = RegexpUtil.getTheWordWithoutExtraInfo(s);
		Assert.assertEquals("hishihiryo", r);
		
		s = " Hishihiryo";
		r = RegexpUtil.getTheWordWithoutExtraInfo(s);
		Assert.assertEquals("Hishihiryo", r);
	}
	
	@Test
	public void getWordWithData(){
		String s = "hishihiryo [pertinence:3] [subCat:japan budhism] [sotheingElse : something else]";
		RegexpUtil.WordWithData wordWithData = RegexpUtil.getWordWithData(s);
		Assert.assertEquals("hishihiryo", wordWithData.word);
		Assert.assertEquals(3, wordWithData.pertinence);
		Assert.assertEquals("subCat:japan budhism|sotheingElse:something else|", wordWithData.extrainfo);		
	} 
	
	@Test
	public void getWordWithData2(){
		String s = "Voie [pertinence:2] [sous cat : dharma] [sous cat : dharma]";
		RegexpUtil.WordWithData wordWithData = RegexpUtil.getWordWithData(s);
		Assert.assertEquals("Voie", wordWithData.word);
		Assert.assertEquals(2, wordWithData.pertinence);
		//because of the \\u00a0 trim does not apply
		Assert.assertEquals("sous cat :dharma|sous cat:dharma|", wordWithData.extrainfo);		
	}
	
	
	public static String toUnicodeValue(char c){
		return "\\u" + Integer.toHexString(c | 0x1000).substring(1);
	}
	
//	public static void main(String[] args) {
//		System.out.println("sous cat : dharma".equals("sous cat : dharma"));
//		System.out.println("sous cat : dharma".length());
//		System.out.println("sous cat : dharma".length());
//		for (int i = 0; i<17; i++){
//			Character c1 = "sous cat : dharma".charAt(i);
//			Character c2 = "sous cat : dharma".charAt(i);
//			System.out.println(c1 + ":" + c2 + " " + toUnicodeValue(c1) + ":" + toUnicodeValue(c2));			
//		}
//	}

}
