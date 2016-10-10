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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Make the association between a keyword and a docPart.
 * 
 * @author mcourcy
 *
 */
@Entity
public class DocPartKeyword extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "keyword_id")
	private Keyword keyword;
	
	@ManyToOne
	@JoinColumn(name = "doc_part_id")
	private DocPart docPart;
	
	/**
	 * Level of pertinence of this keyword. IE if the docPart bring a 
	 * good comprehension for the understanding of this keyword.
	 */
	private Integer pertinence = 1;
	
	/**
	 * Any extra information found in the pattern [key:value] for key different of pertinence.
	 */
	String extraInfo;

	/**
	 * @return the @see #keyword
	 */
	public Keyword getKeyword() {
		return keyword;
	}

	/**
	 * Set @see #keyword
	 */
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the @see #docPart
	 */
	public DocPart getDocPart() {
		return docPart;
	}

	/**
	 * Set @see #docPart
	 */
	public void setDocPart(DocPart docPart) {
		this.docPart = docPart;
		docPart.addDocPartKeyword(this);
	}

	/**
	 * @return the @see #pertinence
	 */
	public Integer getPertinence() {
		return pertinence;
	}

	/**
	 * Set @see #pertinence
	 */
	public void setPertinence(Integer pertinence) {
		this.pertinence = pertinence;
	}

	@Override
	public int hashCode() {
		if (getId()!=null){
			return super.hashCode();
		}
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((docPart == null) ? 0 : docPart.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(getId()!=null) return super.equals(obj);
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocPartKeyword other = (DocPartKeyword) obj;
		if (docPart == null) {
			if (other.docPart != null)
				return false;
		} else if (!docPart.equals(other.docPart))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		return true;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
	
}
