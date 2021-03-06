/*
 * This file is part of zen-index.

    zen-index is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    zen-index is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with zen-index.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sansdemeure.zenindex.main;


import java.io.File;
import java.time.Duration;
import java.time.Instant;

import org.sansdemeure.zenindex.config.ServiceConfig;
import org.sansdemeure.zenindex.service.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	static final  Logger logger = LoggerFactory.getLogger(Main.class);

	
	public static void main(String[] args) {
		
		Instant start = Instant.now(); 
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ServiceConfig.class);
		ctx.refresh();

		BatchService batch = (BatchService) ctx.getBean("batchService");
		batch.start(new File(args[0]));
		
		ctx.close();
		
		Instant end = Instant.now();
		logger.info("Duration of batch {}", Duration.between(start, end));

	}	

}

