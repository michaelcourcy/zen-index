package org.sansdemeure.zenindex.util;

/**
 * Useful to store a "bi-tuple" (doublet in french ?) 
 * @author mcourcy
 *
 * @param <K>
 * @param <V>
 */
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
