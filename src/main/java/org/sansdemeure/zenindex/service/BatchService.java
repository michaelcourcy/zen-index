package org.sansdemeure.zenindex.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.data.entity.dto.DocPartInfo;
import org.sansdemeure.zenindex.data.repository.JPADocRepository;
import org.sansdemeure.zenindex.indexer.DocumentIndexer;
import org.sansdemeure.zenindex.indexer.DocumentIndexerFactory;
import org.sansdemeure.zenindex.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import freemarker.template.TemplateException;

@Component
public class BatchService {

	@Autowired
	JPADocRepository docRepository;

	@Autowired
	TemplateService templateService;

	List<Keyword> keywords = new ArrayList<>();

	static final Logger logger = LoggerFactory.getLogger(BatchService.class);

	public void start(File directory) {
		treatDirectory(directory);
		generateIndexConsultation(directory);
	}

	public void treatDirectory(File directory) {
		cleanHtmlAndKeywordFiles(directory);
		File[] files = directory.listFiles();
		for (File f : files) {
			if (f.getName().endsWith(".odt")) {
				// do we have a pdf or a words ?
				String originals = FileUtil.findOriginals(files, f);
				treatDocument(f, originals);
			} else if (f.isDirectory()) {
				treatDirectory(f);
			}
		}
	}

	@Transactional
	public void treatDocument(File odtFile, String originals) {
		Doc d = new Doc();
		d.setMd5(FileUtil.calculateMD5(odtFile));
		d.setOriginals(originals);
		d.setLastIndex(new Date());
		d.setPath(odtFile.getParent());
		String name = FileUtil.removeExtension(odtFile.getName());
		d.setName(name);
		DocumentIndexer documentIndexer = DocumentIndexerFactory.build();
		Map<String, Object> model = documentIndexer.content(odtFile);
		File htmlDocument = new File(odtFile.getParent(), name + ".html");
		templateService.generateDocumentPage(model, htmlDocument);
		List<DocPart> docParts = documentIndexer.getKeywordsAndDocParts(odtFile, keywords);
		docRepository.saveNewKeywords(keywords);
		for (DocPart docPart : docParts) {
			d.addDocPart(docPart);
		}
		docRepository.save(d);
	}

	public void generateIndexConsultation(File directory) {
		File keywordDirectory = new File(directory, "keywords");
		keywordDirectory.mkdir();
		for (Keyword keyword : keywords) {
			generateKeyWordPage(keywordDirectory, keyword);
		}
		generateIndexPage(directory);
	}

	private void generateKeyWordPage(File keywordDirectory, Keyword keyword) {
		File keywordFile = new File(keywordDirectory, keyword.getWord() + ".html");
		Map<String, Object> model = new HashMap<>();
		model.put("keyword", keyword.getWord());
		model.put("keywordDirectory", keywordDirectory.getAbsolutePath());
		List<DocPartInfo> docpartInfos = docRepository.getDocPartInfos(keyword);
		model.put("docpartInfos", docpartInfos);
		templateService.generateKeywordPage(model, keywordFile);
	}
	
	private void generateIndexPage(File directory) {
		File indexFile = new File(directory, "index.html");
		Map<String, Object> model = new HashMap<>();
		model.put("keywords", docRepository.getAllKeywords());
		templateService.generateIndexPage(model, indexFile);
	}

	private void cleanHtmlAndKeywordFiles(File directory) {
		cleanKeywordDirectory(directory);
		cleanHtmlFiles(directory);
	}

	private void cleanHtmlFiles(File directory) {
		File[] files = directory.listFiles();
		for (File f : files) {
			if (f.getName().endsWith(".html")) {
				if (!f.delete()) {
					logger.error("Unable to delete {}", f.getAbsolutePath());
				}
			} else if (f.isDirectory()) {
				cleanHtmlFiles(f);
			}
		}
	}

	private void cleanKeywordDirectory(File directory) {
		File keywordDirectory = new File(directory, "keywords");
		if (keywordDirectory.exists()) {
			if (keywordDirectory.isDirectory()) {
				FileUtil.deleteDir(keywordDirectory);
			} else {
				// unlikely but we never know ..
				if (keywordDirectory.delete()) {
					logger.debug("the non directory file keywords has been deleted");
				}
			}
		}
	}

}
