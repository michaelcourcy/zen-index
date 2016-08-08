package org.sansdemeure.zenindex.data.entity;

import java.util.Date;

import javax.persistence.Entity;

/**
 * Describe an ODT file.
 * 
 * @author mcourcy
 *
 */
@Entity
public class Doc extends AbstractEntity{

	/**
	 * The path of the file in the filsystem.
	 *
	 */
	private String path;
	
	/**
	 * the last time index was made on this document.
	 */
	private Date lastIndex;
	
	/**
	 * @return the @see #lastIndex
	 */
	public Date getLastIndex() {
		return lastIndex;
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

		
	
	

}
