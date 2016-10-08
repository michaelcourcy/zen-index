/**
 * 
 */
package org.sansdemeure.zenindex.indexer.odt;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Create the html version of the document.
 * 
 * 
 * 
 * @author mcourcy
 *
 */
public class OdtHTMLConverterHandler extends DefaultHandler {

	private static final String CONTENT = "content";

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	// the model for freemaker
	private Map<String, Object> model = new HashMap<>();

	static final Logger logger = LoggerFactory.getLogger(OdtHTMLConverterHandler.class);

	/**
	 * when a comment is empty (ie it doesn't select any text) it comes with no
	 * officeName attribute. In this case we create a reference to it by
	 * counting its apparition in the document.
	 */
	private int commentWithoutOfficeName = 0;

	int nbParagraphOpen = 0;
	int nbParagraphclosed = 0;
	int nbSpanOpen = 0;
	int nbSpanClosed = 0;
	int nbBr = 0;
	int nbCharacter = 0;
	int nbAnnotation = 0;
	int nbAnnotationWithoutOfficeName = 0;
	int nbAnnotationEnd = 0;

	/**
	 * Whether or not the characters should go in the doc.
	 */
	private boolean mustGoToTheDoc = false;

	private boolean insideAnnotationDefinition;

	public OdtHTMLConverterHandler() {
		model.put(CONTENT, "");
		// TODO see how we hanle the title.
		model.put("title", "");
	}

	private void addContent(String s) {
		model.put(CONTENT, model.get(CONTENT) + s + LINE_SEPARATOR);
	}

	@Override
	public void startDocument() {
		//nothing to do
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// a paragraph
		if ("text:p".equals(qName) && !insideAnnotationDefinition) {
			mustGoToTheDoc = true;
			// we list the attributes
			// the class of the element
			String styleClass = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				String sep = (i == attributes.getLength() - 1) ? "" : " ";
				if ("text:style-name".equals(attribute)) {
					styleClass += attributes.getValue(i) + sep;
				}
			}
			addContent("<p class=\"" + styleClass + "\">");
			nbParagraphOpen++;
		} else if ("text:span".equals(qName) && !insideAnnotationDefinition) {
			mustGoToTheDoc = true;
			// we list the attributes
			// the class of the element
			String styleClass = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				String sep = (i == attributes.getLength() - 1) ? "" : " ";
				if ("text:style-name".equals(attribute)) {
					styleClass += attributes.getValue(i) + sep;
				}
			}
			addContent("<span class=\"" + styleClass + "\">");
			nbSpanOpen++;
		} else if ("text:line-break".equals(qName)) {
			mustGoToTheDoc = false;
			addContent("<br/>");
			nbBr++;
		} else if ("office:annotation".equals(qName)) {
			insideAnnotationDefinition = true;
			mustGoToTheDoc = false;
			boolean officeNameFound = false;
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if ("office:name".equals(attribute)) {
					String value = attributes.getValue(i);
					officeNameFound = true;
					addContent("<a name=\"" + value + "_begin\">");
				}
			}
			if (!officeNameFound) {
				commentWithoutOfficeName++;
				String officeName = "__Annotation_withoutOfficeName__number_" + commentWithoutOfficeName;
				addContent("<a name=\"" + officeName + "_begin\">");
				nbAnnotationWithoutOfficeName++;
			} else {
				nbAnnotation++;
			}
		} else if ("office:annotation-end".equals(qName)) {
			mustGoToTheDoc = false;
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if ("office:name".equals(attribute)) {
					String value = attributes.getValue(i);
					addContent("<a name=\"" + value + "_end\">");
				}
			}
			nbAnnotationEnd++;
		} else {
			mustGoToTheDoc = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length){		
		if (mustGoToTheDoc) {
			addContent(new String(ch, start, length));
			nbCharacter++;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if ("office:annotation".equals(qName) || "office:annotation-end".equals(qName)) {
			insideAnnotationDefinition = false;
			mustGoToTheDoc = true;
		}
		if (!insideAnnotationDefinition) {
			if ("text:p".equals(qName)) {
				addContent("</p>");
				nbParagraphclosed++;
			} else if ("text:span".equals(qName)) {
				addContent("</span>");
				nbSpanClosed++;
			}
		}
	}

	@Override
	public void endDocument() {
			if (logger.isDebugEnabled()) {
			if (nbParagraphOpen == 0) {
				logger.debug("No paragraph created");
			} else {
				logger.debug("{} paragraphs were created", nbParagraphOpen);
			}

			if (nbSpanOpen == 0) {
				logger.debug("No span created");
			} else {
				logger.debug("{} spans were created", nbSpanOpen);
			}

			if (nbAnnotation == 0) {
				logger.debug("No annotation created");
			} else {
				logger.debug("{} annotations were created", nbAnnotation);
			}

			if (nbParagraphOpen == nbParagraphclosed) {
				logger.debug("All paragraphs were closed");
			} else {
				logger.debug("All paragraphs were not closed");
			}

			if (nbSpanOpen == nbSpanClosed) {
				logger.debug("All spans were closed");
			} else {
				logger.debug("All spans were not closed");
			}

			if (nbAnnotation == nbAnnotationEnd) {
				logger.debug("All annotation have a corresponding end");
			} else {
				logger.debug("Not all annotation have a corresponding end");
			}

			logger.debug("{} annotations without office:name attribute found", nbAnnotationWithoutOfficeName);
		}
	}
	
	 public Map<String, Object> getModel() {
		return model;
	}

}
