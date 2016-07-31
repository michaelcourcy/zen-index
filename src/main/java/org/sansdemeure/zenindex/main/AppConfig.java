package org.sansdemeure.zenindex.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
		basePackages = {"org.sansdemeure.zenindex.main" }) 
		
public class AppConfig {

	@Bean
	ABean mainRunner() {
		return new ABean();
	}

}
