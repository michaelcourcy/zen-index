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
