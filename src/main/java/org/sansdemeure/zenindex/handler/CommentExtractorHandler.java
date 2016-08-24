/**
 * 
 */
package org.sansdemeure.zenindex.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.DocPartKeyword;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.xml.sax.Attributes;

/**
 * A comment extractor extract all the comments in a doc and build docparts with
 * associated keywords.
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
public class CommentExtractorHandler {

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
	 * is the dcCreator element opened.
	 */
	private boolean dcCreatorOpened = false;

	/**
	 * Are we still defining the annotation.
	 */
	private boolean insideAnnotationDefinition = false;

	/**
	 * Use the map to retreive an already created keyworld or create a new one
	 * add it to the map and return it.
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

	/**
	 * Increase every time we identify a pageBreak.
	 */
	private Integer nbPages = 0;

	/**
	 * Are we inside an annotation definition setting the comments.
	 */
	private boolean commentElementOpened = false;;

	public void startDocument() {

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
	 * put between two word, resulting in a situation where theres no
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
	 * 
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equals("office:annotation")) {
			insideAnnotationDefinition = true;
			DocPart docPart = new DocPart();
			lastOpenDocPart = docPart;
			docPart.setPageStart(nbPages);
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if (attribute.equals("office:name")) {
					String officeName = attributes.getValue(i);
					docPart.setAnnotationName(officeName);
					openDocparts.put(officeName, docPart);
					potentialTextCommented.put(officeName, "");
					break;
				}
			}
		} else if (qName.equals("dc:creator")) {
			dcCreatorOpened = true;
		} else if (qName.equals("office:annotation-end")) {
			// an annotation is finished we must associate the text
			// found inside the docPart.
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if (attribute.equals("office:name")) {
					String officeName = attributes.getValue(i);
					// we must remove it as il will be no longer
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
		} else if (qName.equals("text:p") && insideAnnotationDefinition) {
			commentElementOpened = true;
		} else if (qName.equals("text:soft-page-break")) {
			nbPages++;
		}
	}

	public void character(String s) {

		if (dcCreatorOpened) {
			lastOpenDocPart.setAuthor(s);
		} else if (commentElementOpened) {
			processKeyWords(lastOpenDocPart, s);
		} else {
			// otherwise we are potentially feeding the open comments
			for (String annotationName : openDocparts.keySet()) {
				potentialTextCommented.put(annotationName, openDocparts.get(annotationName) + " " + s);
			}
		}

	}

	private void processKeyWords(DocPart docPart, String s) {
		String[] keywords_array = s.split(",");
		for (String element : keywords_array) {
			Keyword keyword = getOrCreateAndAddKeyword(element);
			DocPartKeyword docPartKeyword = new DocPartKeyword();
			docPartKeyword.setDocPart(docPart);
			docPartKeyword.setKeyword(keyword);
			docPart.addDocPartKeyword(docPartKeyword);
		}

	}

	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("office:annotation")) {
			insideAnnotationDefinition = false;
		} else if (qName.equals("text:p") && insideAnnotationDefinition) {
			commentElementOpened = false;
		} else if (qName.equals("dc:creator")) {
			dcCreatorOpened = false;
		}
	}

	public void endDocument() {
		// some office:annotation may not have corresponding
		// office:annotation-end
		// if no text was selected
		// in this case simply add them to the docParts of the document
		// with no corresponding text.
		for (String annotationName : openDocparts.keySet()) {
			docparts.add(openDocparts.remove(annotationName));
		}
	}

}
