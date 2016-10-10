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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpUtil {
	
	private static Pattern extraInfoPattern = Pattern.compile("\\[([\\w\\s\\u00a0]+)\\:([\\w\\s\\u00a0]+)\\]");
	
	public static List<Pair<String, String>> getExtraInfos(String toBeParsed){
		Matcher m = extraInfoPattern.matcher(toBeParsed);
		List<Pair<String, String>> list = new ArrayList<>();
		while (m.find()){
			Pair<String, String> kv = new Pair<>();
			kv.first = m.group(1).trim();
			kv.second = m.group(2).trim();
			list.add(kv);
		}
		return list;
	}
	
	public static String getTheWordWithoutExtraInfo(String toBeParsed){
		Matcher m = extraInfoPattern.matcher(toBeParsed);
		if (!m.find()){
			return toBeParsed.trim();
		}else{
			int start = m.start();
			return toBeParsed.substring(0, start).trim();
		}		
	}
	
	public static WordWithData getWordWithData(String toBeParsed){
		WordWithData wordWithData = new WordWithData();
		List<Pair<String, String>> list = getExtraInfos(toBeParsed);
		StringBuilder extraInfo = new StringBuilder();
		for (Pair<String, String> kv: list){
			if ("pertinence".equals(kv.first)){
				wordWithData.pertinence = Integer.parseInt(kv.second);				
			}else{
				extraInfo.append(kv.first).append(":").append(kv.second).append("|");
			}
		}
		wordWithData.extrainfo = extraInfo.toString();
		wordWithData.word = getTheWordWithoutExtraInfo(toBeParsed);
		return wordWithData;
	}
	

	
	public static class WordWithData{
		public int pertinence = 1;
		public String extrainfo = "";
		public String word = "";
	}

}
