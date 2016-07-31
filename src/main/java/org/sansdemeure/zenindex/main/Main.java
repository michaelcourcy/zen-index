package org.sansdemeure.zenindex.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);

	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		ABean aBean = (ABean) ctx.getBean("mainRunner");
		aBean.doSomething();
		
		ctx.close();

	}	

}

