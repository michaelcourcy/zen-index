/**
 * 
 */
package org.sansdemeure.zenindex.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author mcourcy
 *
 */
public class OdtHandler extends DefaultHandler  {
	
	private HTMLConverterHandler htmlWriter;
	
	private CommentExtractorHandler commentExtractor;
	
	
	public OdtHandler(HTMLConverterHandler htmlWriter, CommentExtractorHandler commentExtractor) {
		this.htmlWriter = htmlWriter;
		this.commentExtractor = commentExtractor;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		htmlWriter.startDocument();
		commentExtractor.startDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		htmlWriter.startElement(uri,localName,qName,attributes);
		commentExtractor.startElement(uri,localName,qName,attributes);
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		htmlWriter.endElement(uri,localName,qName);
		commentExtractor.endElement(uri,localName,qName);
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		htmlWriter.character(new String(ch, start, length));
		commentExtractor.character(new String(ch, start, length));
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		htmlWriter.endDocument();
		commentExtractor.endDocument();
	}

}
