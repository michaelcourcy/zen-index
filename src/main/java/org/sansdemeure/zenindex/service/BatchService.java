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
package org.sansdemeure.zenindex.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.data.entity.dto.DocPartInfo;
import org.sansdemeure.zenindex.data.repository.JPADocRepository;
import org.sansdemeure.zenindex.indexer.DocumentIndexer;
import org.sansdemeure.zenindex.indexer.DocumentIndexerFactory;
import org.sansdemeure.zenindex.util.FileUtil;
import org.sansdemeure.zenindex.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchService {

	@Autowired
	JPADocRepository docRepository;

	@Autowired
	TemplateService templateService;

	List<Keyword> keywords = new ArrayList<>();

	static final Logger logger = LoggerFactory.getLogger(BatchService.class);

	public void start(File directory) {
		logger.info("Start indexing on {}",directory.getAbsolutePath());
		treatDirectory(directory);
		logger.info("Indexing finished start generating consultation files");
		generateIndexConsultation(directory);
		logger.info("Consultation files created");		
	}

	public void treatDirectory(File directory) {
		cleanHtmlAndKeywordFiles(directory);
		File[] files = directory.listFiles();
		for (File f : files) {
			if (DocumentIndexerFactory.canProvideAnIndexerForThisFile(f)) {
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
		Pair<List<Keyword> , List<DocPart>> tuple = documentIndexer.getKeywordsAndDocParts(odtFile, keywords);
		keywords = tuple.first;
		docRepository.saveNewKeywords(keywords);
		List<DocPart> docParts = tuple.second;
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
