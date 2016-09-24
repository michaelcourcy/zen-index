package org.sansdemeure.zenindex.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.data.repository.JPADocRepository;
import org.sansdemeure.zenindex.handler.CommentExtractorHandler;
import org.sansdemeure.zenindex.handler.HTMLConverterHandler;
import org.sansdemeure.zenindex.handler.OdtHandler;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.ODTResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class BatchService {
	
	@Autowired 
	JPADocRepository docRepository;
	
	@Autowired
	freemarker.template.Configuration freeMarkerConfiguration;
	
	public void start(File directory) throws ParserConfigurationException, SAXException, IOException{
		treatDirectory(directory, null);
		generateIndexConsultation(directory);
	}
	
	public void treatDirectory(File directory, List<Keyword> keywords) throws ParserConfigurationException, SAXException, IOException{
		//TARGET For high performance
		//mark all the doc as not treated 
		//mark all the keywords as not treated and not newDoc
		//For each directory 
			//treat each document in the directory 
				//identify originals (odt, pdf)
				//treat first the odt if no odt try the pdf 
			//TODO consider multi thread treatment		
		//generate index consultation
		//delete all the doc not treated and their html version if relevant  
		//delete all the keyword not treated
		
		//ACTUAL the base is always empty
		//for each directory 
			// delete all html 
			//identify originals by regrouping name 
			//choose odt 
			//call treat document		
		File[] files = directory.listFiles();
		for (File f : files){
			if (f.getName().endsWith(".html")){
				f.delete();
			}else if(f.getName().endsWith(".odt")){
				//do we have a pdf or a words ? 
				String originals = FileUtil.findOriginals(files,f);
				keywords = treatDocument(f, originals, keywords);				
			}else if(f.isDirectory()){
				treatDirectory(f, keywords);
			}
		}
		
			
	}
	
	
	
	/**
	 * I wonder if we should separate the html generation to the comment extraction.
	 * If  I do that it means that we  may activate the two actions in a different 
	 * moment. 
	 * cons :  if we separate this two actions the files may 
	 * have change between.
	 * Pro : it's easier to add another process later. 
	 * 
	 * @param odtFile the odtFile we parse
	 * @param originals that comes eventually with the odt (pdf, docx ...)
	 * @param keywords found from the previous parsing
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	@Transactional
	public List<Keyword> treatDocument(File odtFile, String originals, List<Keyword> keywords) throws ParserConfigurationException, SAXException, IOException{
		//TARGET For high performance
		//check if originals has changed
		//md5 exist so we have nothing to do except changing the path of the doc
			//if the path has changed 
				//change the path 			
				//get the associated keyword from bdd, mark them as treated and add them to the list
			//mark the doc as treated		
		//md5 does not exist 
			//the path exist thus the doc has been changed 
				//update md5
				//delete associated docPart and docPartKeyworld 
				//delete the html version
			//the path does not exist : the doc is a new doc
				//create a new doc with md5 and path
			//apply handler to create html and docPart, keyword and docPartKeyword mark the keywords as treated and newDoc
			//mark the doc as treated 
			//add the keywords to the list
		
		//ACTUAL
		//create a new doc with md5 and path
		//apply handler to create html and docPart, keyword and docPartKeyword 
		//record in the database 
		try (ODTResource odtRessource = new ODTResource(odtFile)) {
			InputStream in = odtRessource.openContentXML();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			CommentExtractorHandler commentExtractor = new CommentExtractorHandler(keywords);
			File htmlFile = new File(odtFile.getParentFile(), FileUtil.changeExtensionToHtml(odtFile.getName()));
			FileOutputStream out = new FileOutputStream(htmlFile);
			OutputStreamWriter writer = new OutputStreamWriter(out, Charset.forName("UTF-8").newEncoder());
			HTMLConverterHandler htmlConverter = new HTMLConverterHandler(writer,freeMarkerConfiguration);
			OdtHandler handler = new OdtHandler(htmlConverter, commentExtractor);
			saxParser.parse(in, handler);
			writer.flush();
			writer.close();
			out.close();
			//create the doc
			Doc d = new Doc();
			d.setMd5(odtRessource.calculateMD5());
			d.setOriginals(originals);
			d.setLastIndex(new Date());	
			//TODO make it relative  
			d.setPath(odtFile.getParent());
			d.setName(FileUtil.removeExtension(odtFile.getName()));
			keywords = new ArrayList<>(commentExtractor.getKeywords().values());
			docRepository.saveNewKeywords(keywords);
			for(DocPart docPart : commentExtractor.getDocparts()){
				d.addDocPart(docPart);
			}
			docRepository.save(d);
		}
		return keywords;
	}
	
	
	
	public void generateIndexConsultation(File directory){
		//TARGET For high performance
		//if all the keywords are not newDoc do nothing ...
		//otherwise
			//delete and generate index.html 
			//for each keyword marked newDoc generate keywordPage
		//delete all keyword page marked as not treated	
		
		//ACTUAL 
		//generate index.html
		//for each keyword generate keyworld page
		//remove old index
//		File index = new File(directory, "index.html");
//		if (index.exists()){
//			index.delete();
//		}
//		File keywordDirectory = new File(directory,"keywords");
//		if (keywordDirectory.exists()){
//			if (keywordDirectory.isDirectory()){
//				FileUtil.deleteDir(keywordDirectory);
//			}else{
//				//unlikely but we never know ..
//				keywordDirectory.delete();
//			}
//		}
//		List<Keyword> keywords = docRepository.getAllKeywords();
//		for (Keyword keyword : keywords){
//			generateKeyWordPage(keywordDirectory,keyword);			
//		}
//		generateIndexPage(keywordDirectory,keyword);
	}
	
	

}