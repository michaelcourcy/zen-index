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
		docPart.setText("C’est vrai, pas de parti pris, les deux sont bons, les deux sont nécessaires. Progressif est nécessaire. Venir régulièrement au zazen, venir le matin, ça, c’est progression. Mais pratiquer zazen chaque jour, c’est le ici et maintenant immédiat. C’est ce que nous faisons maintenant. A l’époque de Sekito, tout le monde parlait de la différence,  san . A l’époque, les disciples s’intéressaient beaucoup à la philosophie du Zen. Quelle école est la meilleure ? Quelle pratique est la meilleure ? Aujourd’hui, on ne s’intéresse pas beaucoup à ces problèmes. Pourtant, il existe aujourd’hui le Zen coréen, le Zen japonais, le Zen de Yasutani, le Zen américain, le Zen de Deshimaru, le Zen mélangé, « rinzoto ».  Aujourd’hui, nous sommes beaucoup plus intéressés par nous-mêmes, nos familles, nos problèmes personnels, toujours la santé : ça, c’est bon pour moi, ça, non.  Est-ce que c’est pire aujourd’hui, ou à l’époque de Sekito ? Non, les problèmes sont différents, c’est tout.");
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
