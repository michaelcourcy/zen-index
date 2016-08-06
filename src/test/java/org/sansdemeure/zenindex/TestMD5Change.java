package org.sansdemeure.zenindex;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.data.config.BDDConfigForTest;
import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.repository.DocRepository;
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
public class TestMD5Change {

	final static Logger logger = LoggerFactory.getLogger(TestMD5Change.class);

	AnnotationConfigApplicationContext ctx;
	File testDir;

		
	@Autowired 
	DocRepository docRepository;

	@Before
	public void before() throws IOException {
		logger.debug("Prepare the directory with the single doc");
		testDir = FileUtil.prepareEmptyDirectory(TestMD5Change.class);
		FileUtil.copyFromResources("docs/1992/Sandokai.odt", testDir, "Sandokai.odt");
	}

	@Test
	public void testSaveADocEntity() {
		logger.info("iterating on " + testDir.getAbsolutePath() + " directory");
		Doc d = new Doc();
		d.setMd5("kqjsj655sqhjh65");
		d.setPath("/path/to/heaven");
		docRepository.save(d);
	}
	
	
	
	
	

}
