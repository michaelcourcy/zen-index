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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.indexer.DocumentIndexer;
import org.sansdemeure.zenindex.util.ODTResource;
import org.sansdemeure.zenindex.util.Pair;
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
	public Pair<List<Keyword> , List<DocPart>> getKeywordsAndDocParts(File originalDocument, List<Keyword> alreadyFoundKeywords) {
		try (ODTResource odtRessource = new ODTResource(originalDocument)) {
			try {
				InputStream in = odtRessource.openContentXML();
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				OdtCommentExtractorHandler commentExtractor = new OdtCommentExtractorHandler(alreadyFoundKeywords);
				saxParser.parse(in, commentExtractor);
				return new Pair<>(
						new ArrayList<>(commentExtractor.getKeywords().values()), 
						commentExtractor.getDocparts()
						);				
			} catch (ParserConfigurationException | SAXException | IOException e) {
				throw new RuntimeException(e);
			}			
		}
	}

}
