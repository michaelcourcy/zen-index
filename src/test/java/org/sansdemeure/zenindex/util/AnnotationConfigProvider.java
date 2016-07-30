package org.sansdemeure.zenindex.util;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class AnnotationConfigProvider {
	
	public static AnnotationConfigApplicationContext getConfig(Class AppConfigClass){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfigClass);
		ctx.refresh();
		return ctx;
	}

}
