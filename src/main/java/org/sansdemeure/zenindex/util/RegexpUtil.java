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
