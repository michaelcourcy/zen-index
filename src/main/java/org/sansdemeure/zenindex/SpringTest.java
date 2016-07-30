package org.sansdemeure.zenindex;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class SpringTest {

	final static Logger logger = LoggerFactory.getLogger(SpringTest.class);

	@Autowired
	InjectableBean injectableBean;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		SpringTest springTest = (SpringTest) ctx.getBean("mainRunner");
		springTest.run();

	}

	void run() {
		injectableBean.doSomething();
		logger.info("running from bean");
	}

}

