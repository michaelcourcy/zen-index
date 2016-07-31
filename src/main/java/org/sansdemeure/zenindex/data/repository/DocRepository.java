/**
 * 
 */
package org.sansdemeure.zenindex.data.repository;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.springframework.stereotype.Repository;

/**
 * @author mcourcy
 *
 */
public interface DocRepository {
	
	Doc save(Doc doc);

}
