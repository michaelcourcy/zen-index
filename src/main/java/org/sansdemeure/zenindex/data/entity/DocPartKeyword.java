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
	private Integer pertinence;

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
	
	
}
