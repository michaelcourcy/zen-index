/**
 * 
 */
package org.sansdemeure.zenindex.indexer.odt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.FreeMarkerConfig;
import org.sansdemeure.zenindex.indexer.odt.OdtHTMLConverterHandler;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.ODTResource;
import org.sansdemeure.zenindex.util.TestAppender;
import org.sansdemeure.zenindex.util.WriterForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

/**
 * @author mcourcy
 * 
 * We have a strange behaviour on the doc docs/2016/2016_05_17_DZP.odt the converter 
 * do not output the begining of the text.
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FreeMarkerConfig.class)
public class TestHTMLConverter2 {
	
	@Autowired
	freemarker.template.Configuration freeMarkerConfiguration;

	@Test
	public void test() throws ParserConfigurationException, SAXException, IOException {
		TestAppender testAppender = new TestAppender();
		File testDir = FileUtil.prepareEmptyDirectory(TestHTMLConverter2.class);
		FileUtil.copyFromResources("docs/2016/2016_05_17_DZP.odt", testDir, "2016_05_17_DZP.odt");
		File odt = new File(testDir, "2016_05_17_DZP.odt");
		try (ODTResource odtRessource = new ODTResource(odt)) {
			InputStream in = odtRessource.openContentXML();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			WriterForTest wt = new WriterForTest();
			OdtHTMLConverterHandler htmlConverter = new OdtHTMLConverterHandler();
			saxParser.parse(in, htmlConverter);
			Map<String, Object> model = htmlConverter.getModel();
			String content = (String) model.get("content"); 
			Assert.assertTrue(content.contains("Il y a beaucoup de"));
			Assert.assertTrue(content.contains("Les dents se touchent"));			
		} 

	}

}
