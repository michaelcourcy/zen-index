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
