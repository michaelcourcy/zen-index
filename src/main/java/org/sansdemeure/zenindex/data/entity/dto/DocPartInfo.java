package org.sansdemeure.zenindex.data.entity.dto;

/**
 * Only a dto (data transfer object) to aggregate data around a keyword. 
 * 
 * @author mcourcy
 *
 */
public class DocPartInfo {
	
	
	
	public DocPartInfo(String name, String path, String annotationName, Integer pageStart, Integer pageEnd, String text,
			Integer pertinence) {
		super();
		this.name = name;
		this.path = path;
		this.annotationName = annotationName;
		this.pageStart = pageStart;
		this.pageEnd = pageEnd;
		this.text = text;
		this.pertinence = pertinence;
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
	
	

}
