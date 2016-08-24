package org.sansdemeure.zenindex.data.repository;

import java.io.File;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.BDDConfigForTest;
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
@ContextConfiguration(classes = BDDConfigForTest.class)
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
		docRepository.save(d);				
	}
	
	
	@Test	
	@Transactional
	public void testSaveADocEntityWithDocPartsAndKeywords() {
		testDir = FileUtil.prepareEmptyDirectory(TestDocRepository.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
		File sandokai = new File(testDir, "Sandokai.odt");
		Doc d = EntityFactoryForTest.makeADoc(sandokai);
		DocPart docPart = EntityFactoryForTest.makeADocPart("Sandokai");
		Keyword keyword = EntityFactoryForTest.makeAKeyword("lumière");
		DocPartKeyword docPartKeyword = EntityFactoryForTest.makeADocPartKeyword(1);
		
		//first save the keyword
		keyword = docRepository.save(keyword);
		
		//then create the relationships
		d.addDocPart(docPart);		
		docPartKeyword.setKeyword(keyword);
		docPartKeyword.setDocPart(docPart);
		
		d = docRepository.save(d);
		
		//check with another request that the relationship are well stored
		
		d = docRepository.getDoc(d.getId());
		Assert.assertFalse(d.getDocParts().isEmpty());
		DocPart dc = d.getDocParts().iterator().next();
		Assert.assertFalse(dc.getDocPartKeywords().isEmpty());
		DocPartKeyword dck = dc.getDocPartKeywords().iterator().next();
		Assert.assertEquals("lumière", dck.getKeyword().getWord());
		
	}
	
	
	
	

}
