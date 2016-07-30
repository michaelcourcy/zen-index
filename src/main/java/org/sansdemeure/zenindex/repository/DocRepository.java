/**
 * 
 */
package org.sansdemeure.zenindex.repository;

import org.sansdemeure.zenindex.entity.Doc;
import org.springframework.stereotype.Repository;

/**
 * @author mcourcy
 *
 */
public interface DocRepository {
	
	Doc save(Doc doc);

}
