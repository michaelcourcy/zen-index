/**
 * 
 */
package org.sansdemeure.zenindex.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.sansdemeure.zenindex.util.DocUtil;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.TestAppender;
import org.sansdemeure.zenindex.util.TestDocUtilGetContentXML;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author mcourcy
 *
 */
public class TestHTMLConverter {
	
	@Test
	public void test() throws ParserConfigurationException, SAXException, IOException{
		TestAppender testAppender = new TestAppender();
		File testDir = FileUtil.prepareEmptyDirectory(TestHTMLConverter.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_with_2comments.odt", testDir, "Sandokai.odt");
		File odt = new File(testDir, "Sandokai.odt");
		InputStream in = DocUtil.getContentXML(odt);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser  saxParser = factory.newSAXParser();
		Writer mockwriter = new Writer() {			
			@Override
			public void write(String s){
				//if you want to see the effect in the console uncomment
				//System.out.print(s);
			}			
			@Override
			public void write(char[] cbuf, int off, int len) throws IOException {}			
			@Override
			public void flush() throws IOException {}
			@Override
			public void close() throws IOException {}
		};
		HTMLConverter htmlConverter = new HTMLConverter(mockwriter);
		//we don't want to test the commentExtractor, so avoid side effect
		CommentExtractor commentExtractor = new CommentExtractor(){
			public void startDocument(){}
			public void startElement(String uri, String localName, String qName, Attributes attributes){}
			public void character(String s){}
			public void endElement(String uri, String localName, String qName){}
			public void endDocument(){}
		};
        OdtHandler handler   = new OdtHandler(htmlConverter,commentExtractor);
        saxParser.parse(in, handler);
        testAppender.verify("paragraphs were created");
        testAppender.verify("spans were created");
        testAppender.verify("2 annotations were created");
        testAppender.verify("All paragraphs were closed");
        testAppender.verify("All spans were closed");
	}

}
