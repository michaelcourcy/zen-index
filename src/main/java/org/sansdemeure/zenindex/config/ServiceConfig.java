package org.sansdemeure.zenindex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"org.sansdemeure.zenindex.service" })
@Import(value={BDDConfig.class,FreeMarkerConfig.class})
public class ServiceConfig {
	
}
