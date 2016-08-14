package org.sansdemeure.zenindex.data.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Describe an ODT file.
 * 
 * @author mcourcy
 *
 */
@Entity
public class Doc extends AbstractEntity {

	/**
	 * The path of the file in the filsystem.
	 *
	 */
	private String path;

	/**
	 * The last time index was made on this document.
	 */
	private Date lastIndex;
	
	/**
	 * a comma separated list of url to the original version of the documents.
	 * It contains at least the odt version of the doc.
	 */
	private String originals;
	
	/**
	 * Extra information about the word like importance, localisation in a
	 * corpus etc....
	 */
	private String extraLocalisationInfo;

	/**
	 * @return the @see #originals
	 */
	public String getOriginals() {
		return originals;
	}

	/**
	 * Set @see #originals
	 */
	public void setOriginals(String originals) {
		this.originals = originals;
	}

	/**
	 * The docparts of this doc.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "doc_id")
	private Set<DocPart> docParts;

	/**
	 * @return the @see #lastIndex
	 */
	public Date getLastIndex() {
		return lastIndex;
	}

	/**
	 * @return the @see #docParts
	 */
	public Set<DocPart> getDocParts() {
		return docParts;
	}

	/**
	 * Set @see #docParts
	 */
	public void setDocParts(Set<DocPart> docParts) {
		this.docParts = docParts;
	}

	/**
	 * Set @see #lastIndex
	 */
	public void setLastIndex(Date lastIndex) {
		this.lastIndex = lastIndex;
	}

	/**
	 * The md5 field is the md5 result on this file.
	 */
	private String md5;

	/**
	 * @return the @see #path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set @see #path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the @see #md5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * Set @see #md5
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public void addDocPart(DocPart docPart){
		if (docParts==null){
			docParts = new HashSet<>();
		}
		docParts.add(docPart);
	}

	/**
	 * @return the @see #extraLocalisationInfo
	 */
	public String getExtraLocalisationInfo() {
		return extraLocalisationInfo;
	}

	/**
	 * Set @see #extraLocalisationInfo
	 */
	public void setExtraLocalisationInfo(String extraLocalisationInfo) {
		this.extraLocalisationInfo = extraLocalisationInfo;
	}

}
