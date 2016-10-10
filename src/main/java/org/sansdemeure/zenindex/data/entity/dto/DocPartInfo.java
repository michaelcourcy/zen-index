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
package org.sansdemeure.zenindex.data.entity.dto;

public class DocPartInfo {
	
	
	
	public DocPartInfo(String name, String path, String annotationName, Integer pageStart, Integer pageEnd, String text,
			Integer pertinence, String extraInfo) {
		super();
		this.name = name;
		this.path = path;
		this.annotationName = annotationName;
		this.pageStart = pageStart;
		this.pageEnd = pageEnd;
		this.text = text;
		this.pertinence = pertinence;
		this.extraInfo = extraInfo;
	}

	/**
	 * The name of the doc that contains this docpart
	 */
	private String name;
	
	/**
	 * The path of the doc.
	 */
	private String path;
	
	/**
	 * the name of the annotation in the doc.
	 */
	private String annotationName;
	
	/**
	 * Page start of the docPart.
	 */
	private Integer pageStart;
	
	/**
	 * Page end of the docPart.
	 */
	private Integer pageEnd;
	
	/**
	 * The text of the docpart.
	 */
	private String text;
	
	/**
	 * the pertinence of the word inside the docpart.
	 */
	private Integer pertinence;
	
	/**
	 * Any extra Info provided in the [key:value] form.
	 */
	private String extraInfo;

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getAnnotationName() {
		return annotationName;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public Integer getPageEnd() {
		return pageEnd;
	}

	public String getText() {
		return text;
	}

	public Integer getPertinence() {
		return pertinence;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
	

}
