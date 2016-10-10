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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.BDDConfig;
import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.DocPartKeyword;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.data.entity.dto.DocPartInfo;
import org.sansdemeure.zenindex.util.EntityFactoryForTest;
import org.sansdemeure.zenindex.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Assert;



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
public class TestDocRepository2 {

	final static Logger logger = LoggerFactory.getLogger(TestDocRepository2.class);

	File testDir;

		
	@Autowired 
	JPADocRepository docRepository;

		
	@Test	
	public void testSaveADocEntityWithDocPartsAndKeywords() {
		testDir = FileUtil.prepareEmptyDirectory(TestDocRepository2.class);
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
		
		List<DocPartInfo> dpis = docRepository.getDocPartInfos(keyword);
		//as we build the 2 doc part arond the same keyword we should have a list of 2 elements
		Assert.assertEquals(2, dpis.size());
		
		
	}
	
	
	
	

}
