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
package org.sansdemeure.zenindex.iterator;

import java.io.File;
import java.util.Iterator;
import java.util.Stack;

/**
 * An iterator of all the sub-directories present in a directory.
 * The root directory is also returned 
 * during the iteration. 
 * 
 * @author mcourcy
 *
 */
public class DirectoryIterator implements Iterator<File> {

	private Stack<File[]> stackFiles = new Stack<>();
	private Stack<Integer> stackCounter = new Stack<>(); 

	/**
	 * @param directory
	 */
	public DirectoryIterator(File directory) {
		super();
		stackFiles.push(directory.listFiles());
		stackCounter.push(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return !stackFiles.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public File next() {
		File[] files = stackFiles.peek();
		int counter = stackCounter.peek();		
		if (counter < files.length) {
			File next = files[counter];
			stackCounter.set(stackCounter.size() - 1, counter + 1);
			if (next.isDirectory() && next.listFiles().length > 0) {
				stackFiles.push(files[counter].listFiles());
				stackCounter.push(0);
			}
			//if it's the last file in the hierarchy comme up 
			//until the end of the stack
			while (!stackCounter.isEmpty() && stackFiles.peek().length == stackCounter.peek() ) {
				stackFiles.pop();
				stackCounter.pop();
			}
			return next;
		}
		// we should not get here it means we call next while the stack is empty
		throw new RuntimeException("No more file to iterate on, always call hasNext before calling next");
	}

}
