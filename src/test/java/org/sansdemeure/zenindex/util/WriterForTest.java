package org.sansdemeure.zenindex.util;

import java.io.IOException;
import java.io.Writer;

import org.junit.Assert;

/**
 * Useful to check the content of a writer in a test.
 * 
 * @author mcourcy
 *
 */
public class WriterForTest extends Writer{
	
	private StringBuffer content = new StringBuffer();

	@Override
	public void close() throws IOException {}

	@Override
	public void flush() throws IOException {}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		content.append(new String(cbuf, off, len));		
	}
	
	public void assertContains(String needle){
		Assert.assertTrue(content.toString().contains(needle));
	}
	
	@Override
	public String toString() {
		return content.toString();
	}

}
