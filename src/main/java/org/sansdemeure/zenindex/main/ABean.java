/**
 * 
 */
package org.sansdemeure.zenindex.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mcourcy
 *
 */
@Component
public class ABean {
	
	final static Logger logger = LoggerFactory.getLogger(ABean.class);
	
	@Autowired
	AnInjectedBean anInjectedBean;
	
	void doSomething() {
		logger.info("I do something from ABean");
		anInjectedBean.doSomething();
	}

}
