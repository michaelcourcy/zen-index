/**
 * 
 */
package org.sansdemeure.zenindex.handler;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

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
public class HTMLConverterHandler {

	//the writer that will create the files. 
	private Writer writer;
	
	//the freemarker configuration to load the right template
	freemarker.template.Configuration freeMarkerConfiguration;

	private final static String LINE_SEPARATOR = System.getProperty("line.separator");

	//the model for freemaker 
	private Map<String, Object> model = new HashMap<>();
	
	final static Logger logger = LoggerFactory.getLogger(HTMLConverterHandler.class);
	
	
	/**
	 * when a comment is empty (ie it doesn't select any text) it comes with 
	 * no officeName attribute. In this case we create a reference to it by counting 
	 * its apparition in the document. 
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

	public HTMLConverterHandler(Writer writer, freemarker.template.Configuration freeMarkerConfiguration) {
		this.writer = writer;
		this.freeMarkerConfiguration = freeMarkerConfiguration;
		model.put("content", "");
		model.put("title", "");
	}

	private void addContent(String s) {
		model.put("content", model.get("content") + s + LINE_SEPARATOR);
	}

	/**
	 * Initialize the header of the html file. TODO use a template. TODO capture
	 * the title. TODO add the style. TODO add external styles to have an
	 * autonomous document.
	 */
	public void startDocument() {
		//TODO see how we hanle the title.
		model.put("title", "");
		logger.debug("header written");
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// a paragraph
		if (qName.equals("text:p")  && !insideAnnotationDefinition) {
			mustGoToTheDoc = true;
			// we list the attributes
			// the class of the element
			String styleClass = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				String sep = (i == attributes.getLength() - 1) ? "" : " ";
				if (attribute.equals("text:style-name")) {
					styleClass += attributes.getValue(i) + sep;
				}
			}
			addContent("<p class=\"" + styleClass + "\">");
			nbParagraphOpen++;
		} else if (qName.equals("text:span") && !insideAnnotationDefinition) {
			mustGoToTheDoc = true;
			// we list the attributes
			// the class of the element
			String styleClass = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				String sep = (i == attributes.getLength() - 1) ? "" : " ";
				if (attribute.equals("text:style-name")) {
					styleClass += attributes.getValue(i) + sep;
				}
			}
			addContent("<span class=\"" + styleClass + "\">");
			nbSpanOpen++;
		} else if (qName.equals("text:line-break")) {
			mustGoToTheDoc = false;
			addContent("<br/>");
			nbBr++;
		} else if (qName.equals("office:annotation")) {
		    insideAnnotationDefinition = true;
			mustGoToTheDoc = false;
			boolean officeNameFound = false;
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if (attribute.equals("office:name")) {
					String value = attributes.getValue(i);
					officeNameFound = true;
					addContent("<a name=\"" + value + "_begin\">");					
				}
			}
			if (!officeNameFound){
				commentWithoutOfficeName ++;
				String officeName = "__Annotation_withoutOfficeName__number_" + commentWithoutOfficeName; 
				addContent("<a name=\"" + officeName + "_begin\">");
				nbAnnotationWithoutOfficeName++;
			}else{
				nbAnnotation++;
			}
		} else if (qName.equals("office:annotation-end")) {
			mustGoToTheDoc = false;
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.getQName(i);
				if (attribute.equals("office:name")) {
					String value = attributes.getValue(i);
					addContent("<a name=\"" + value + "_end\">");
				}
			}
			nbAnnotationEnd++;
		} else {
			mustGoToTheDoc = false;
		}
	}

	public void character(String s) {
		if (mustGoToTheDoc) {
			addContent(s);
			nbCharacter++;
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("office:annotation")) {
			insideAnnotationDefinition = false;
		}	
		if (!insideAnnotationDefinition){
			if (qName.equals("text:p")) {
				addContent("</p>");
				nbParagraphclosed++;
			} else if (qName.equals("text:span")) {
				addContent("</span>");
				nbSpanClosed++;
			}
		}
	}

	public void endDocument() throws IOException, TemplateException {
		//accept html element in the document.
		
		Template temp = freeMarkerConfiguration.getTemplate("document.ftl");
		temp.process(model,writer);
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
			
			logger.debug("{} annotations without office:name attribute found",nbAnnotationWithoutOfficeName);
		}
	}

}
