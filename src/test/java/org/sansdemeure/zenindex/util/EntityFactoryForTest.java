/**
 * 
 */
package org.sansdemeure.zenindex.util;

import java.io.File;
import java.util.Date;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.DocPartKeyword;
import org.sansdemeure.zenindex.data.entity.Keyword;

/**
 * Build Entity to facilitate the writing of the test
 * 
 * @author mcourcy
 *
 */
public class EntityFactoryForTest {
	
	public static Doc makeADoc(File file){
		Doc d = new Doc();
		ODTResource odtResource= new ODTResource(file);
		d.setMd5(odtResource.calculateMD5());
		d.setPath(file.getAbsolutePath());
		String name = file.getName();
		name = name.substring(0, name.lastIndexOf("."));
		d.setOriginals(name+ ".odt,"+name+".docx,"+name+".pdf");
		d.setExtraLocalisationInfo("Corpus 92");
		d.setLastIndex(new Date());
		return d;
	}
	
	public static DocPart makeADocPart(String name){
		DocPart docPart = new DocPart();
		docPart.setText("L’obscurité existe dans la lumière, ne voyez pas que le côté obscur. La lumière existe dans l’obscurité, ne voyez pas que le côté lumineux");
		docPart.setAnnotationName("JHHJAAhJ_begin");
		docPart.setAuthor("Corpus 92");		
		docPart.setPageStart(6);
		docPart.setPageEnd(6);
		return docPart;
	}
	
	public static Keyword makeAKeyword(String name){
		Keyword keyword = new Keyword();
		keyword.setWord(name);
		return keyword;
	}
	
	public static DocPartKeyword makeADocPartKeyword(Integer pertinence){
		DocPartKeyword docPartKeyword = new DocPartKeyword();
		docPartKeyword.setPertinence(1);
		return docPartKeyword;
	}

}
