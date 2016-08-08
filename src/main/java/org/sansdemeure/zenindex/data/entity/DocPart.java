/**
 * 
 */
package org.sansdemeure.zenindex.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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
	private String text;
	
	/**
	 * the keywords associated to this 
	 */
	@ManyToMany
	private List<Keyword> keywords;

}
