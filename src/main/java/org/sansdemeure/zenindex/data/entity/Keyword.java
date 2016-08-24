/**
 * 
 */
package org.sansdemeure.zenindex.data.entity;

import javax.persistence.Entity;

/**
 * @author mcourcy
 *
 */
@Entity
public class Keyword extends AbstractEntity {

	/**
	 * The word itself.
	 */
	private String word;

		/**
	 * @return the @see #word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Set @see #word
	 */
	public void setWord(String word) {
		this.word = word;
	}
	

}
