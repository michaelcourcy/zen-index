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
	 * The parent path of the file in the filsystem.
	 *
	 */
	private String path;
	
	/**
	 * the name of the doc without any extension.
	 * 
	 */
	private String name;

	

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
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy ="doc")
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
		docPart.setDoc(this);
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

	/**
	 * @return the @see {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set @see {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		if (getId()!=null){
			return super.hashCode();
		}
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		Doc other = (Doc) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
	

	

}
