package org.sansdemeure.zenindex.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.data.config.FreeMarkerConfig;
import org.sansdemeure.zenindex.util.WriterForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FreeMarkerConfig.class)
public class TestFreeMarkerTemplate {

	@Autowired
	freemarker.template.Configuration freeMarkerConfiguration;
	
	/**
	 * Test the configuration of freemarker with spring.
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	@Test
	public void testTemplateMerging() throws IOException, TemplateException{
		// Create the root hash. We use a Map here, but it could be a JavaBean too.
		Map<String, Object> root = new HashMap<>();
		// Put string "user" into the root
		root.put("user", "Big Joe");
		Template temp = freeMarkerConfiguration.getTemplate("test.ftl");
		WriterForTest wt = new WriterForTest();
		temp.process(root, wt);
		wt.assertContains("Big Joe");
	}
	
}
