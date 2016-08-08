/**
 * 
 */
package org.sansdemeure.zenindex.iterator;

import java.io.File;
import java.util.Iterator;

/**
 * We don't know if the single directory will be the only way 
 * to provide doc to the program, thus we decide to have 
 * it working with an iterator. 
 * 
 * In the future Spring may be used to configure the iterator
 * that will be provided.
 * 
 * @author mcourcy
 *
 */
public class DirectoryIterable implements Iterable<File> {
	
	File directory;

	/**
	 * @param directory
	 */
	public DirectoryIterable(File directory) {
		super();
		this.directory = directory;
	}



	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<File> iterator() {		
		return new DirectoryIterator(directory);
	}

}
