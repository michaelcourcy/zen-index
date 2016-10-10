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

import freemarker.template.TemplateException;



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
public class TestBatchService2 {

	final static Logger logger = LoggerFactory.getLogger(TestBatchService2.class);

	File testDir;

		
	@Autowired 
	BatchService batchService;
	
	@Autowired 
	JPADocRepository docRepository;
	
	@Test	
	public void testTreatDirectory() throws ParserConfigurationException, SAXException, IOException, TemplateException {
		testDir = FileUtil.prepareEmptyDirectory(TestBatchService2.class);
		FileUtil.copyFromResources("docs/1992/Sandokai_with_overlappingAndInsertedcomments.odt", testDir, "Sandokai.odt");
		FileUtil.copyFromResources("docs/1995/Lille_25_juin_95.odt", testDir, "Lille_25_juin_95.odt");
		//add a subfolder to test recursivity
		File subdir = new File(testDir,"sub");
		subdir.mkdir();
		FileUtil.copyFromResources("docs/2016/2016_05_17_DZP.odt", subdir, "2016_05_17_DZP.odt");
		batchService.start(testDir);
		
		//it's a end to end test we're going to check the expected files has been created 
		//but opening the doc and checking the content is consistent with the content of the doc 
		//is a bit heavy, in a future release we'll add the framework to check the content of the generated files. 
		File sandokai_html = new File(testDir,"Sandokai.html");
		Assert.assertTrue(sandokai_html.exists());
		File Lille_25_juin_95_html = new File(testDir, "Lille_25_juin_95.html");
		Assert.assertTrue(Lille_25_juin_95_html.exists());
		File _2016_05_17_DZP_html = new File(subdir, "2016_05_17_DZP.html");
		Assert.assertTrue(_2016_05_17_DZP_html.exists());
		File keywords_dir  = new File(testDir,"keywords");
		Assert.assertTrue(keywords_dir.exists() && keywords_dir.isDirectory());
		Assert.assertEquals(9, keywords_dir.listFiles().length);
		
	}
	
	
	
	

}
