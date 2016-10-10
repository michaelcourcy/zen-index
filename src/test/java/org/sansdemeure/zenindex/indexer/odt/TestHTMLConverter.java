/**
 * 
 */
package org.sansdemeure.zenindex.indexer.odt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.FreeMarkerConfig;
import org.sansdemeure.zenindex.indexer.odt.OdtHTMLConverterHandler;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.ODTResource;
import org.sansdemeure.zenindex.util.TestAppender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

/**
 * @author mcourcy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FreeMarkerConfig.class)
public class TestHTMLConverter {
	
	
	@Test
	public void test() throws ParserConfigurationException, SAXException, IOException {
		TestAppender testAppender = new TestAppender();
		File testDir = FileUtil.prepareEmptyDirectory(TestHTMLConverter.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_with_2comments.odt", testDir, "Sandokai.odt");
		File odt = new File(testDir, "Sandokai.odt");
		try (ODTResource odtRessource = new ODTResource(odt)) {
			InputStream in = odtRessource.openContentXML();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			OdtHTMLConverterHandler htmlConverter = new OdtHTMLConverterHandler();
			saxParser.parse(in, htmlConverter);
			testAppender.verify("paragraphs were created");
			testAppender.verify("spans were created");
			testAppender.verify("2 annotations were created");
			testAppender.verify("All paragraphs were closed");
			testAppender.verify("All spans were closed");
		} 

	}

}
