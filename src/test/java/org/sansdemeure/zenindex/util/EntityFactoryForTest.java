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
		d.setMd5(FileUtil.calculateMD5(file));
		d.setPath(file.getAbsolutePath());
		String name = file.getName();
		name = name.substring(0, name.lastIndexOf("."));
		d.setName(name);
		d.setOriginals(name+ ".odt,"+name+".docx,"+name+".pdf");
		d.setExtraLocalisationInfo("Corpus 92");
		d.setLastIndex(new Date());
		return d;
	}
	
	public static DocPart makeADocPart(String name){
		DocPart docPart = new DocPart();
		docPart.setText("C�est vrai, pas de parti pris, les deux sont bons, les deux sont n�cessaires. Progressif est n�cessaire. Venir r�guli�rement au zazen, venir le matin, �a, c�est progression. Mais pratiquer zazen chaque jour, c�est le ici et maintenant imm�diat. C�est ce que nous faisons maintenant. A l��poque de Sekito, tout le monde parlait de la diff�rence,  san . A l��poque, les disciples s�int�ressaient beaucoup � la philosophie du Zen. Quelle �cole est la meilleure ? Quelle pratique est la meilleure ? Aujourd�hui, on ne s�int�resse pas beaucoup � ces probl�mes. Pourtant, il existe aujourd�hui le Zen cor�en, le Zen japonais, le Zen de Yasutani, le Zen am�ricain, le Zen de Deshimaru, le Zen m�lang�, ��rinzoto��.  Aujourd�hui, nous sommes beaucoup plus int�ress�s par nous-m�mes, nos familles, nos probl�mes personnels, toujours la sant� : �a, c�est bon pour moi, �a, non.  Est-ce que c�est pire aujourd�hui, ou � l��poque de Sekito ? Non, les probl�mes sont diff�rents, c�est tout.");
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
		docPartKeyword.setPertinence(pertinence);
		return docPartKeyword;
	}

}
