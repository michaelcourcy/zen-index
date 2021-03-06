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
package org.sansdemeure.zenindex.indexer.odt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.indexer.odt.OdtCommentExtractorHandler;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.ODTResource;
import org.sansdemeure.zenindex.util.TestAppender;
import org.xml.sax.SAXException;

/**
 * @author mcourcy
 *
 */
public class TestCommentExtractor {

	@Test
	public void test() throws ParserConfigurationException, SAXException, IOException {
		TestAppender testAppender = new TestAppender();
		File testDir = FileUtil.prepareEmptyDirectory(TestCommentExtractor.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_with_2comments.odt", testDir, "Sandokai.odt");
		File odt = new File(testDir, "Sandokai.odt");
		try (ODTResource odtRessource = new ODTResource(odt)) {
			InputStream in = odtRessource.openContentXML();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			OdtCommentExtractorHandler commentExtractor = new OdtCommentExtractorHandler(null);
			saxParser.parse(in, commentExtractor);
			testAppender.verify("new keywords created");
			testAppender.verify("comments were found");
		}
	}

	@Test
	public void testWithOvelappingAndInsertedComments() throws ParserConfigurationException, SAXException, IOException {
		TestAppender testAppender = new TestAppender();
		File testDir = FileUtil.prepareEmptyDirectory(TestCommentExtractor.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_with_overlappingAndInsertedcomments.odt", testDir,
				"Sandokai.odt");
		File odt = new File(testDir, "Sandokai.odt");
		try (ODTResource odtRessource = new ODTResource(odt)) {
			InputStream in = odtRessource.openContentXML();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			OdtCommentExtractorHandler commentExtractor = new OdtCommentExtractorHandler(null);
			saxParser.parse(in, commentExtractor);
			List<DocPart> docParts = commentExtractor.getDocparts();
			// 3rd docPart is overlapping the 4th
			Assert.assertTrue(docParts.get(2).getText().equals(
					" Le deuxi�me vers parle des personnalit�s humaines, des sensations et des intelligences diverses. � Mais dans la Voie, le Sud et le Nord n�existent pas. � Nous sommes diff�rents, bien s�r, intelligents, moins intelligents, mais sur la Voie, cela ne veut rien dire.  M�me un ma�tre peut �tre stupide. �a ne veut rien dire ; aussi le Sud et le Nord n�existent pas."));
			Assert.assertTrue(docParts.get(3).getText().equals(
					" M�me un ma�tre peut �tre stupide. �a ne veut rien dire ; aussi le Sud et le Nord n�existent pas.   Toujours la guerre entre le Sud et le Nord. En Chine, � l��poque de Sekito, dans les ann�es 700, on parlait toujours de l��cole Nan, et de l��cole Hoku. Nan, c��tait l��cole du Sud, l��cole d�Eno, le sixi�me patriarche, et aussi l��cole de Sekito. L��cole Hoku, c��tait l��cole du Nord avec Ma�tre Jinshu. Nan, c��tait la soi-disant �cole imm�diate. Hoku, c��tait l��cole soi-disant graduelle."));
			// 5th docPart is included in the 6th
			Assert.assertTrue(docParts.get(4).getText().equals(
					" Aujourd�hui, nous sommes beaucoup plus int�ress�s par nous-m�mes, nos familles, nos probl�mes personnels, toujours la sant� : �a, c�est bon pour moi, �a, non."));
			Assert.assertTrue(docParts.get(5).getText().equals(
					" C�est vrai, pas de parti pris, les deux sont bons, les deux sont n�cessaires. Progressif est n�cessaire. Venir r�guli�rement au zazen, venir le matin, �a, c�est progression. Mais pratiquer zazen chaque jour, c�est le ici et maintenant imm�diat. C�est ce que nous faisons maintenant. A l��poque de Sekito, tout le monde parlait de la diff�rence,  san . A l��poque, les disciples s�int�ressaient beaucoup � la philosophie du Zen. Quelle �cole est la meilleure ? Quelle pratique est la meilleure ? Aujourd�hui, on ne s�int�resse pas beaucoup � ces probl�mes. Pourtant, il existe aujourd�hui le Zen cor�en, le Zen japonais, le Zen de Yasutani, le Zen am�ricain, le Zen de Deshimaru, le Zen m�lang�, ��rinzoto��.  Aujourd�hui, nous sommes beaucoup plus int�ress�s par nous-m�mes, nos familles, nos probl�mes personnels, toujours la sant� : �a, c�est bon pour moi, �a, non.  Est-ce que c�est pire aujourd�hui, ou � l��poque de Sekito ? Non, les probl�mes sont diff�rents, c�est tout."));
			// 7th doc part has no text selected
			Assert.assertTrue("".equals(docParts.get(6).getText()));			
			testAppender.verify("new keywords created");
			testAppender.verify("comments were found");
		}
	}

}
