/**
 * 
 */
package org.sansdemeure.zenindex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author mcourcy
 *
 */
@Component
public class InjectableBean {
	
	final static Logger logger = LoggerFactory.getLogger(InjectableBean.class);
	
	void doSomething() {
		logger.info("I do something");
	}

}
