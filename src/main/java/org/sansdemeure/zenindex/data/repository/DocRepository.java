/**
 * 
 */
package org.sansdemeure.zenindex.data.repository;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.Keyword;

/**
 * @author mcourcy
 *
 */
public interface DocRepository {
	
	Doc save(Doc doc);
	
	Keyword save(Keyword keyword);
	
	Doc getDoc(Long id);

}
