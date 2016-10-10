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
package org.sansdemeure.zenindex.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.Status;

/**
 * Mock Appender for checking the logs. 
 * 
 * 
 * @author mcourcy
 *
 */
public class TestAppender implements Appender<ILoggingEvent> {

	List<String> logMessages = new ArrayList<>();

	/**
	 * Initialize the appender, find the root logger and put it to the debug level, attach himself to it.
	 */
	public TestAppender() {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		root.addAppender(this);
	}

	@Override
	public String getName() {
		return "MockAppender";
	}
	
	public void verify (String text){
		Assert.assertTrue(logMessages.stream().anyMatch(item -> item.contains(text)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.Appender#doAppend(java.lang.Object)
	 */
	@Override
	public void doAppend(ILoggingEvent event) throws LogbackException {
		logMessages.add(event.getFormattedMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.LifeCycle#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.LifeCycle#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.LifeCycle#isStarted()
	 */
	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#setContext(ch.qos.logback.core.
	 * Context)
	 */
	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#getContext()
	 */
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.qos.logback.core.spi.ContextAware#addStatus(ch.qos.logback.core.status
	 * .Status)
	 */
	@Override
	public void addStatus(Status status) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#addInfo(java.lang.String)
	 */
	@Override
	public void addInfo(String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#addInfo(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void addInfo(String msg, Throwable ex) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#addWarn(java.lang.String)
	 */
	@Override
	public void addWarn(String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#addWarn(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void addWarn(String msg, Throwable ex) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#addError(java.lang.String)
	 */
	@Override
	public void addError(String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.ContextAware#addError(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void addError(String msg, Throwable ex) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.qos.logback.core.spi.FilterAttachable#addFilter(ch.qos.logback.core.
	 * filter.Filter)
	 */
	@Override
	public void addFilter(Filter<ILoggingEvent> newFilter) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.spi.FilterAttachable#clearAllFilters()
	 */
	@Override
	public void clearAllFilters() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.qos.logback.core.spi.FilterAttachable#getCopyOfAttachedFiltersList()
	 */
	@Override
	public List<Filter<ILoggingEvent>> getCopyOfAttachedFiltersList() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.qos.logback.core.spi.FilterAttachable#getFilterChainDecision(java.lang
	 * .Object)
	 */
	@Override
	public FilterReply getFilterChainDecision(ILoggingEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.qos.logback.core.Appender#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

}
