package org.sansdemeure.zenindex;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.BDDConfigForTest;
import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.repository.DocRepository;
import org.sansdemeure.zenindex.util.DocUtil;
import org.sansdemeure.zenindex.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
	DocRepository docRepository;

	
	@Test
	public void testSaveADocEntity() {
		testDir = FileUtil.prepareEmptyDirectory(TestDocRepository.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
		File sandokai = new File(testDir, "Sandokai.odt");
		Doc d = new Doc();
		d.setMd5(DocUtil.calculateMD5(sandokai));
		d.setPath(sandokai.getAbsolutePath());
		docRepository.save(d);
		logger.info("Everything went fine");
	}
	
	
	
	
	

}
