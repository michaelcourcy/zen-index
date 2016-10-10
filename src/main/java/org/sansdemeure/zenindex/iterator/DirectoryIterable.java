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
