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
package org.sansdemeure.zenindex.data.repository;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.BDDConfig;
import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.DocPartKeyword;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.util.EntityFactoryForTest;
import org.sansdemeure.zenindex.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Build a directory with a single doc and perform test on MD5.
 * 
 * Mainly to try the test tool we've built.
 * 
 * @author mcourcy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BDDConfig.class)
public class TestDocRepository {

	final static Logger logger = LoggerFactory.getLogger(TestDocRepository.class);

	File testDir;

		
	@Autowired 
	JPADocRepository docRepository;

	
	@Test
	public void testSaveADocEntitySimple() {		
		testDir = FileUtil.prepareEmptyDirectory(TestDocRepository.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
		File sandokai = new File(testDir, "Sandokai.odt");
		Doc d = EntityFactoryForTest.makeADoc(sandokai);
		DocPart docPart = EntityFactoryForTest.makeADocPart("Sandokai");
		d.addDocPart(docPart);
		docRepository.save(d);
		d = docRepository.getDoc(1L);
		Assert.assertNotNull(d);
		List<DocPart> docparts = docRepository.getAllDocPart();
		Assert.assertEquals(1, docparts.size());
	}
	
	
	@Test	
	@Transactional
	public void testSaveADocEntityWithDocPartsAndKeywords() {
		testDir = FileUtil.prepareEmptyDirectory(TestDocRepository.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
		File sandokai = new File(testDir, "Sandokai.odt");
		Doc d = EntityFactoryForTest.makeADoc(sandokai);
		Keyword keyword = EntityFactoryForTest.makeAKeyword("lumière");
		DocPart docPart = EntityFactoryForTest.makeADocPart("Sandokai");
		DocPartKeyword docPartKeyword = EntityFactoryForTest.makeADocPartKeyword(1);
		DocPart docPart2 = EntityFactoryForTest.makeADocPart("Sandokai2");
		DocPartKeyword docPartKeyword2 = EntityFactoryForTest.makeADocPartKeyword(2);
		
		//first save the keyword
		keyword = docRepository.save(keyword);
		
		//then create the relationships
		d.addDocPart(docPart);
		docPartKeyword.setKeyword(keyword);
		docPartKeyword.setDocPart(docPart);
		
		d.addDocPart(docPart2);
		docPartKeyword2.setKeyword(keyword);
		docPartKeyword2.setDocPart(docPart2);
		
		d = docRepository.save(d);
		
		//check with another request that the relationship are well stored
		
		d = docRepository.getDoc(d.getId());
		Assert.assertFalse(d.getDocParts().isEmpty());
		DocPart dc = d.getDocParts().iterator().next();
		Assert.assertFalse(dc.getDocPartKeywords().isEmpty());
		DocPartKeyword dck = dc.getDocPartKeywords().iterator().next();
		Assert.assertEquals("lumière", dck.getKeyword().getWord());
		
		//check all docParts are stored 
		//The BDD is empty at list we should find 2 doc Part
		List<DocPart> docparts = docRepository.getAllDocPart();
		Assert.assertTrue(docparts.size()>2);
		
	}
	
	
	
	

}
