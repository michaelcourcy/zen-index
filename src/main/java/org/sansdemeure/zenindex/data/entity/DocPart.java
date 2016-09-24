/**
 * 
 */
package org.sansdemeure.zenindex.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The portion of text that has been extracted from the doc it has keywords.
 * 
 * @author mcourcy
 *
 */
@Entity
public class DocPart extends AbstractEntity{
	
	/**
	 * the text of this docPart, exctract from the comment in the odt.
	 */
	@Column(columnDefinition = "LONGVARCHAR")
	// @Lob
	private String text;
	
	/**
	 * the name of the annotation in the doc. 
	 */
	private String annotationName;
	
	/**
	 * In wich part this docPart start. 
	 * If the docPart start and end in the same page 
	 * pageStart is the same than pageEnd.
	 */
	private Integer pageStart;
	
	/**
	 * In wich part this docPart stop. 
	 * If the docPart start and end in the same page 
	 * pageStart is the same than pageEnd.
	 */
	private Integer pageEnd;
	
	/**
	 * the keywords associated to this docPart
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "docPart")	
	private Set<DocPartKeyword> docPartKeywords;
	
	/**
	 * The doc this docPart belong to. 
	 */
	@ManyToOne
    @JoinColumn(name="doc_id")
	private Doc doc;
	
	/**
	 * The author of this docPart. It's information is important 
	 * as some automatic process could be authors.
	 */
	private String author;	
	
	
	/**
	 * @return the @see #htmlLink
	 */
	public String getAnnotationName() {
		return annotationName;
	}

	/**
	 * Set @see #htmlLink
	 */
	public void setAnnotationName(String annotationName) {
		this.annotationName = annotationName;
	}

	/**
	 * @return the @see #pageStart
	 */
	public Integer getPageStart() {
		return pageStart;
	}

	/**
	 * Set @see #pageStart
	 */
	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	/**
	 * @return the @see #pageEnd
	 */
	public Integer getPageEnd() {
		return pageEnd;
	}

	/**
	 * Set @see #pageEnd
	 */
	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}

	/**
	 * @return the @see #docPartKeywords
	 */
	public Set<DocPartKeyword> getDocPartKeywords() {
		return docPartKeywords;
	}

	/**
	 * Set @see #docPartKeywords
	 */
	public void setDocPartKeywords(Set<DocPartKeyword> docPartKeywords) {
		this.docPartKeywords = docPartKeywords;
	}



	/**
	 * @return the @see #text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set @see #text
	 */
	public void setText(String text) {
		this.text = text;
	}

	
	public void addDocPartKeyword(DocPartKeyword docPartKeyword){
		if (docPartKeywords==null){
			docPartKeywords = new HashSet<>();
		}
		docPartKeywords.add(docPartKeyword);
	}

	/**
	 * @return the @see #author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Set @see #author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	@Override
	public int hashCode() {
		if (getId()!=null){
			return super.hashCode();
		}
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((annotationName == null) ? 0 : annotationName.hashCode());
		result = prime * result + ((doc == null) ? 0 : doc.hashCode());
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
		DocPart other = (DocPart) obj;
		if (annotationName == null) {
			if (other.annotationName != null)
				return false;
		} else if (!annotationName.equals(other.annotationName))
			return false;
		if (doc == null) {
			if (other.doc != null)
				return false;
		} else if (!doc.equals(other.doc))
			return false;
		return true;
	}

	
	
	
	
	

}
