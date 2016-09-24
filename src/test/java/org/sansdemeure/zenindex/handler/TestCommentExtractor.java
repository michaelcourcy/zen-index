/**
 * 
 */
package org.sansdemeure.zenindex.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;
import org.sansdemeure.zenindex.util.ODTResource;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.TestAppender;
import org.xml.sax.Attributes;
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
			// we don't want to test the converter, so avoid side effect
			HTMLConverterHandler htmlConverter = new HTMLConverterHandler(null,null) {
				public void startDocument() {}
				public void startElement(String uri, String localName, String qName, Attributes attributes) {}
				public void character(String s) {}
				public void endElement(String uri, String localName, String qName) {}
				public void endDocument() {}
			};
			CommentExtractorHandler commentExtractor = new CommentExtractorHandler(null);
			OdtHandler handler = new OdtHandler(htmlConverter, commentExtractor);
			saxParser.parse(in, handler);
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
			// we don't want to test the converter, so avoid side effect
			HTMLConverterHandler htmlConverter = new HTMLConverterHandler(null,null) {
				public void startDocument() {}
				public void startElement(String uri, String localName, String qName, Attributes attributes) {}
				public void character(String s) {}
				public void endElement(String uri, String localName, String qName) {}
				public void endDocument() {}
			};
			CommentExtractorHandler commentExtractor = new CommentExtractorHandler(null);
			OdtHandler handler = new OdtHandler(htmlConverter, commentExtractor);
			saxParser.parse(in, handler);
			List<DocPart> docParts = commentExtractor.getDocparts();
			// 3rd docPart is overlapping the 4th
			Assert.assertTrue(docParts.get(2).getText().equals(
					" Le deuxième vers parle des personnalités humaines, des sensations et des intelligences diverses. « Mais dans la Voie, le Sud et le Nord n’existent pas. » Nous sommes différents, bien sûr, intelligents, moins intelligents, mais sur la Voie, cela ne veut rien dire.  Même un maître peut être stupide. Ça ne veut rien dire ; aussi le Sud et le Nord n’existent pas."));
			Assert.assertTrue(docParts.get(3).getText().equals(
					" Même un maître peut être stupide. Ça ne veut rien dire ; aussi le Sud et le Nord n’existent pas.   Toujours la guerre entre le Sud et le Nord. En Chine, à l’époque de Sekito, dans les années 700, on parlait toujours de l’école Nan, et de l’école Hoku. Nan, c’était l’école du Sud, l’école d’Eno, le sixième patriarche, et aussi l’école de Sekito. L’école Hoku, c’était l’école du Nord avec Maître Jinshu. Nan, c’était la soi-disant école immédiate. Hoku, c’était l’école soi-disant graduelle."));
			// 5th docPart is included in the 6th
			Assert.assertTrue(docParts.get(4).getText().equals(
					" Aujourd’hui, nous sommes beaucoup plus intéressés par nous-mêmes, nos familles, nos problèmes personnels, toujours la santé : ça, c’est bon pour moi, ça, non."));
			Assert.assertTrue(docParts.get(5).getText().equals(
					" C’est vrai, pas de parti pris, les deux sont bons, les deux sont nécessaires. Progressif est nécessaire. Venir régulièrement au zazen, venir le matin, ça, c’est progression. Mais pratiquer zazen chaque jour, c’est le ici et maintenant immédiat. C’est ce que nous faisons maintenant. A l’époque de Sekito, tout le monde parlait de la différence,  san . A l’époque, les disciples s’intéressaient beaucoup à la philosophie du Zen. Quelle école est la meilleure ? Quelle pratique est la meilleure ? Aujourd’hui, on ne s’intéresse pas beaucoup à ces problèmes. Pourtant, il existe aujourd’hui le Zen coréen, le Zen japonais, le Zen de Yasutani, le Zen américain, le Zen de Deshimaru, le Zen mélangé, « rinzoto ».  Aujourd’hui, nous sommes beaucoup plus intéressés par nous-mêmes, nos familles, nos problèmes personnels, toujours la santé : ça, c’est bon pour moi, ça, non.  Est-ce que c’est pire aujourd’hui, ou à l’époque de Sekito ? Non, les problèmes sont différents, c’est tout."));
			// 7th doc part has no text selected
			Assert.assertTrue(docParts.get(6).getText()==null);			
			testAppender.verify("new keywords created");
			testAppender.verify("comments were found");
		}
	}

}
