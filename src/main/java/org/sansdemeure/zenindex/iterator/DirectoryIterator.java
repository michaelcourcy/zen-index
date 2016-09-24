/**
 * 
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
