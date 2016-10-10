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
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.ServiceConfig;
import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.repository.JPADocRepository;
import org.sansdemeure.zenindex.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;



/**
 * Build a directory with a single doc and perform test on MD5.
 * 
 * Mainly to try the test tool we've built.
 * 
 * @author mcourcy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class TestBatchService {

	final static Logger logger = LoggerFactory.getLogger(TestBatchService.class);

	File testDir;

		
	@Autowired 
	BatchService batchService;
	
	@Autowired 
	JPADocRepository docRepository;
	
	@Test	
	public void testTreatDocuments() throws ParserConfigurationException, SAXException, IOException {
		testDir = FileUtil.prepareEmptyDirectory(TestBatchService.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_with_overlappingAndInsertedcomments.odt", testDir, "Sandokai.odt");
		File sandokai = new File(testDir, "Sandokai.odt");
		batchService.treatDocument(sandokai, "sandokai.pdf");
		//we should have at least 7 docparts let's test it.
		Doc doc = docRepository.getDocByNameAndPath("Sandokai",testDir.getPath());
		List<DocPart> docParts = docRepository.getAllDocPart(doc.getId());
		Assert.assertEquals(7, docParts.size());
		System.out.println();
		
	}
	
	
	
	

}
