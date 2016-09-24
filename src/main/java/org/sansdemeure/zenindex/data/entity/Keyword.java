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

	@Override
	public int hashCode() {
		if (getId()!=null){
			return super.hashCode();
		}
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getId()!=null){
			return super.equals(obj);
		}
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Keyword other = (Keyword) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	

}
