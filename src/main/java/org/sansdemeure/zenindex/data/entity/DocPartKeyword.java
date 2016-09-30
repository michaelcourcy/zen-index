/**
 * 
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
	
	
}
