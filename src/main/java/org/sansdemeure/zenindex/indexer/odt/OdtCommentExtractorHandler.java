/**
 * 
 */
package org.sansdemeure.zenindex.indexer.odt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.DocPartKeyword;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.util.Pair;
import org.sansdemeure.zenindex.util.RegexpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A comment extractor extract all the comments in a odt doc and build docparts
 * with associated keywords.
 * 
 * In the treatment we never interact with the database. We do it later when the
 * extraction is done. Thus the docPart, keyworld and docPartKeyword are
 * actually not linked to the JPA session.
 * 
 * Not linking the objects to the JPA session make the class much more testable
 * also.
 * 
 * @author mcourcy
 *
 */
public class OdtCommentExtractorHandler extends DefaultHandler {
	
	private static final String TEXT_P = "text:p";

	static final Logger logger = LoggerFactory.getLogger(OdtCommentExtractorHandler.class);

	/**
	 * docParts found in this document.
	 */
	private List<DocPart> docparts = new ArrayList<>();

	/**
	 * docParts open during the treatments, tracked with the annotation name.
	 */
	private Map<String, DocPart> openDocparts = new HashMap<>();

	/**
	 * the last DocPart open during the process.
	 */
	private DocPart lastOpenDocPart;

	/**
	 * we may assign the text found after </office:annotation> if an
	 * <office:annotation-end> is correponding.
	 */
	private Map<String, String> potentialTextCommented = new HashMap<>();

	/**
	 * All the keywords are put in a hashmap for faster retrieval.
	 */
	private Map<String, Keyword> keywords = new HashMap<>();

	/**
	 * is the dcCreator (ie the author of the comment) element opened.
	 */
	private boolean dcCreatorOpened = false;

	/**
	 * Are we still defining the annotation, before the </office:annotation>.
	 */
	private boolean insideAnnotationDefinition = false;
	
	/**
	 * The list of style that make a pagebreak.
	 */
	private Set<String> pageBreakers = new HashSet<>();
	
	/**
	 * Increase every time we identify a pageBreak.
	 */
	private Integer nbPages = 1;

	/**
	 * Are we inside an annotation definition setting the comments.
	 */
	private boolean commentElementOpened = false;

	/**
	 * when a comment is empty (ie it doesn't select any text) it comes with 
	 * no officeName attribute. In this case we create a reference to it by counting 
	 * its apparition in the document. 
	 */
	private int commentWithoutOfficeName = 0;

	private boolean insideStyleDefinition = false;

	private String activeStyleNameDefinition;
	
	/**
	 * Initiate the extractor with the already found or created keyword.
	 * @param keywords
	 */
	public OdtCommentExtractorHandler(List<Keyword> listkeywords){
		if (listkeywords==null) {
			return;
		}
		for (Keyword keyword : listkeywords){
			keywords.put(keyword.getWord(), keyword);
		}
	}

	/**
	 * Use the map to retreive an already created keyworld or create a new one
	 * add it to the map and return it.
	 * 
	 * The code that create the CommentExtractorHandler may already initiate this 
	 * Map with keyworlds previoulsly found/created by the treatment of the other
	 * documents.
	 * 
	 * @param keyword
	 * @return
	 */
	private Keyword getOrCreateAndAddKeyword(String keyword) {
		if (keywords.get(keyword) != null) {
			return keywords.get(keyword);
		} else {
			Keyword keywordObj = new Keyword();
			keywordObj.setWord(keyword);
			keywords.put(keyword, keywordObj);
			return keywordObj;
		}
	}

	
	@Override
	public void startDocument() {
		//Nothing to do at the start of the doc
	}

	/**
	 * Inside an annotation element are put all the comments.
	 * 
	 * The text between the element office:annotation and office:annotation-end
	 * is the text commented.
	 * 
	 * See an example in
	 * src/test/resources/docs/1992/content_of_sandokai_with_2comments.xml line
	 * 580.
	 *
	 * <office:annotation office:name="__Annotation__3378_342229383">
	 * <dc:creator>Auteur inconnu</dc:creator>
	 * <dc:date>2016-08-12T14:08:24.29</dc:date> <text:p text:style-name="P41">
	 * <text:span text:style-name="T9">Phénomènes</text:span> </text:p>
	 * </office:annotation> Parce qu’ils sont indépendants, les phénomènes
	 * s’interpénètrent. Perçus par les sens, ils apparaissent sans rapport.
	 * S’il n’en était pas ainsi, il n’y aurait pas possibilité d’échapper à la
	 * différenciation
	 * <office:annotation-end office:name="__Annotation__3378_342229383" />
	 * 
	 * Sometime no text is selected for the comment, for instance the comment is
	 * put between two word, resulting in a situation where there is no
	 * corresponding <office:annotation-end>
	 * 
	 * See an example in
	 * src/test/resources/docs/1992/content_of_sandokai_with_comment_without_selection.xml
	 * line 631
	 * 
	 * <text:p text:style-name="P6"> 13) Vos pieds marchent sur la Voie
	 * <office:annotation> <dc:creator>Auteur inconnu</dc:creator>
	 * <dc:date>2016-08-24T22:44:29.695000000</dc:date>
	 * <text:p text:style-name="P41">
	 * <text:span text:style-name="T9">Dharma</text:span> </text:p>
	 * </office:annotation> . Comprenez-le si vous voulez la réaliser. </text:p>
	 * 
	 * We must be able to manage this two cases.
	 * 
	 * 
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		detectInsideStyleDefinition(qName, attributes); 
		detectParagraphStyleDefinition(qName, attributes); 
		detectAnnotation(qName, attributes);
		detectAnnotationCreator(qName);
		detectParagraphInAnnotation(qName); 
		detectAnnotationEnd(qName, attributes);
		detectPageBreak(qName, attributes);
	}

	private void detectPageBreak(String qName, Attributes attributes) {
		//page breakers
		if ("text:soft-page-break".equals(qName)) {
			nbPages++;
		} else if (TEXT_P.equals(qName) && !insideAnnotationDefinition) { 
			checkParagraphClassInPageBreakers(attributes);
		}
	}

	private void checkParagraphClassInPageBreakers(Attributes attributes) {
		for (int i = 0; i < attributes.getLength(); i++) {
			String attribute = attributes.getQName(i);
			if ("text:style-name".equals(attribute)) {
				String styleName = attributes.getValue(i);
				if (pageBreakers.contains(styleName)){
					nbPages++;
				}
				break;
			}
		}
	}

	private void detectParagraphInAnnotation(String qName) {
		if (TEXT_P.equals(qName) && insideAnnotationDefinition) {
			commentElementOpened = true;
		}
	}

	private void detectAnnotationEnd(String qName, Attributes attributes) {
		if ("office:annotation-end".equals(qName)) {
			// an annotation is finished we must associate the text
			// found inside the docPart.
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if ("office:name".equals(attribute)) {
					String officeName = attributes.getValue(i);
					// we must remove it as it will be no longer
					// fed with the text flow
					DocPart docPartToClose = openDocparts.remove(officeName);
					// we can add the corresponding text
					String textToClose = potentialTextCommented.remove(officeName);
					docPartToClose.setText(textToClose);
					docPartToClose.setPageEnd(nbPages);
					docparts.add(docPartToClose);
					break;
				}
			}
		}
	}

	private void detectAnnotationCreator(String qName) {
		if ("dc:creator".equals(qName)) {
			dcCreatorOpened = true;
		}
	}

	private void detectAnnotation(String qName, Attributes attributes) {
		if ("office:annotation".equals(qName)) {
			insideAnnotationDefinition = true;
			DocPart docPart = new DocPart();
			lastOpenDocPart = docPart;
			boolean officeNameFound = false;
			docPart.setPageStart(nbPages);
			//by default pageStart are equal to pageEnd 
			//detectAnnotationEnd may change it
			docPart.setPageEnd(nbPages);
			//the text may be empty in case of comment without any text 
			docPart.setText("");
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if ("office:name".equals(attribute)) {
					String officeName = attributes.getValue(i);
					docPart.setAnnotationName(officeName);
					openDocparts.put(officeName, docPart);
					potentialTextCommented.put(officeName, "");
					officeNameFound = true;
					break;
				}
			}
			if (!officeNameFound){
				commentWithoutOfficeName ++;
				String officeName = "__Annotation_withoutOfficeName__number_" + commentWithoutOfficeName; 
				docPart.setAnnotationName(officeName);
				openDocparts.put(officeName, docPart);
				//no potential text to feed.
			}
		}
	}

	private void detectParagraphStyleDefinition(String qName, Attributes attributes) {
		if (insideStyleDefinition && "style:paragraph-properties".equals(qName)){
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if ("fo:break-before".equals(attribute)) {
					addBreaker(attributes, i);
					break;
				}
			}			
		}
	}

	private void addBreaker(Attributes attributes, int i) {
		if("page".equals(attributes.getValue(i))){
			pageBreakers.add(activeStyleNameDefinition);
		}
	}

	private void detectInsideStyleDefinition(String qName, Attributes attributes) {
		if ("style:style".equals(qName)){
			insideStyleDefinition  = true;
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if ("style:name".equals(attribute)) {
					activeStyleNameDefinition = attributes.getValue(i);
					break;
				}
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		String s = new String(ch, start, length);	
		if (dcCreatorOpened) {
			lastOpenDocPart.setAuthor(s);
		} else if (commentElementOpened) {
			processKeyWords(lastOpenDocPart, s);
		} else if (!insideAnnotationDefinition) {
			// otherwise we are potentially feeding the open comments
			// because we are not anymore inside the definition of an annotation
			// and getting text.
			for (String annotationName : openDocparts.keySet()) {
				potentialTextCommented.put(annotationName, potentialTextCommented.get(annotationName) + " " + s);
			}
		}

	}

	 
	private void processKeyWords(DocPart docPart, String allTheKeywordsWithextradata) {
		String[] keywordsArray = allTheKeywordsWithextradata.split(",");
		for (String keywordWithExtraData : keywordsArray) {
			String elementTrimed = keywordWithExtraData.trim();
			RegexpUtil.WordWithData wordWithData = RegexpUtil.getWordWithData(elementTrimed);
			Keyword keyword = getOrCreateAndAddKeyword(wordWithData.word);
			DocPartKeyword docPartKeyword = new DocPartKeyword();
			docPartKeyword.setDocPart(docPart);
			docPartKeyword.setPertinence(wordWithData.pertinence);
			docPartKeyword.setExtraInfo(wordWithData.extrainfo);
			docPartKeyword.setKeyword(keyword);
			docPart.addDocPartKeyword(docPartKeyword);
		}
	}	

	

	@Override
	public void endElement(String uri, String localName, String qName) {
		if ("style:style".equals(qName)){
			insideStyleDefinition  = false;
		} else 	if ("office:annotation".equals(qName)) {
			insideAnnotationDefinition = false;
		} else if (TEXT_P.equals(qName) && insideAnnotationDefinition) {
			commentElementOpened = false;
		} else if ("dc:creator".equals(qName)) {
			dcCreatorOpened = false;
		}
		
	}

	@Override
	public void endDocument() {
		// some office:annotation may not have corresponding
		// office:annotation-end
		// if no text was selected
		// in this case simply add them to the docParts of the document
		// with no corresponding text.
		for (String annotationName : openDocparts.keySet()) {
			docparts.add(openDocparts.remove(annotationName));
		}
		//brings debug information
		if (logger.isDebugEnabled()){
			if (!docparts.isEmpty()){
				logger.debug("{} comments were found",docparts.size());
			}else{
				logger.debug("no comment were found");
			}
			//new keyword created
			long newKeyWordCreated = keywords.values().stream().filter(k->k.getId()==null).count();
			if (newKeyWordCreated>0){
				logger.debug("{} new keywords created",newKeyWordCreated);
			}else{
				logger.debug("no keyword created");
			}			
		}		
	}

	public List<DocPart> getDocparts() {
		return docparts;
	}

	public void setDocparts(List<DocPart> docparts) {
		this.docparts = docparts;
	}

	public Map<String, Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Map<String, Keyword> keywords) {
		this.keywords = keywords;
	}

}
