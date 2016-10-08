package org.sansdemeure.zenindex.indexer.odt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.indexer.DocumentIndexer;
import org.sansdemeure.zenindex.util.ODTResource;
import org.xml.sax.SAXException;

public class OdtIndexer implements DocumentIndexer{
	
	
	@Override
	public Map<String, Object> content(File originalDocument) {
		try (ODTResource odtRessource = new ODTResource(originalDocument)) {
			try {
				InputStream in = odtRessource.openContentXML();
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				OdtHTMLConverterHandler htmlConverter = new OdtHTMLConverterHandler();
				saxParser.parse(in, htmlConverter);
				return htmlConverter.getModel();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				throw new RuntimeException(e);
			}			
		}
	}

	@Override
	public List<DocPart> getKeywordsAndDocParts(File originalDocument, List<Keyword> alreadyFoundKeywords) {
		try (ODTResource odtRessource = new ODTResource(originalDocument)) {
			try {
				InputStream in = odtRessource.openContentXML();
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				OdtCommentExtractorHandler commentExtractor = new OdtCommentExtractorHandler(alreadyFoundKeywords);
				saxParser.parse(in, commentExtractor);
				return commentExtractor.getDocparts();				
			} catch (ParserConfigurationException | SAXException | IOException e) {
				throw new RuntimeException(e);
			}			
		}
	}

}
