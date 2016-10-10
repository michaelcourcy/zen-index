package org.sansdemeure.zenindex.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class TemplateService {
	
	@Autowired
	freemarker.template.Configuration freeMarkerConfiguration;
	
	private void generate(Map<String, Object> model, File htmlDocument, String template){
		try {
			Template temp = freeMarkerConfiguration.getTemplate(template);
			FileOutputStream out = new FileOutputStream(htmlDocument);
			OutputStreamWriter writer = new OutputStreamWriter(out, Charset.forName("UTF-8").newEncoder());
			temp.process(model,writer);
			out.close();
			writer.close();
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void generateDocumentPage(Map<String, Object> content, File htmlDocument){
		generate(content,htmlDocument,"document.ftl");		
	}
	
	public void generateIndexPage(Map<String, Object> content, File htmlindex){
		generate(content,htmlindex,"index.ftl");
	}
	
	public void generateKeywordPage(Map<String, Object> content, File htmlKeyword){
		generate(content,htmlKeyword,"keyword.ftl");
	}
	

}
