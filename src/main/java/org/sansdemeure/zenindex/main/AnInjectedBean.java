/**
 * 
 */
package org.sansdemeure.zenindex.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author mcourcy
 *
 */
@Component
public class AnInjectedBean {
	
	final static Logger logger = LoggerFactory.getLogger(AnInjectedBean.class);
	
	void doSomething(){
		logger.info("I do something from AnInjectedBean");
	}

}
