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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sansdemeure.zenindex.config.FreeMarkerConfig;
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
