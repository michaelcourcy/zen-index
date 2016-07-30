package org.sansdemeure.zenindex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LoggerTest {
	
	final static Logger logger = LoggerFactory.getLogger(LoggerTest.class);

	public static void main(String[] args) {
		logger.info("Entering application.");	   
	    logger.info("Exiting application.");
	}

}
