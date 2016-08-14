/**
 * 
 */
package org.sansdemeure.zenindex.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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
