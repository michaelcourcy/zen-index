package org.sansdemeure.zenindex.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class FreeMarkerConfig {
	
	@Bean
	public freemarker.template.Configuration freeMarkerConfiguration(){
		freemarker.template.Configuration cfg = new freemarker.template.Configuration();		
		cfg.setClassForTemplateLoading(this.getClass(), "/templates");
		return cfg;
	}
	

}
