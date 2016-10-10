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

public class Pair <K,V> {
	
	public K first;
	public V second;
	
	public Pair(){
		super();
	}
	
	public Pair(K key, V value) {
		super();
		this.first = key;
		this.second = value;
	}
	
	

}
