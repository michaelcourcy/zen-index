package org.sansdemeure.zenindex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
		basePackages = {"org.sansdemeure.zenindex" }) 
		
class AppConfig {

	@Bean
	SpringTest mainRunner() {
		return new SpringTest();
	}

}
