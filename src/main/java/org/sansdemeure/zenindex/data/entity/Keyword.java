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
