/**
 * 
 */
package org.sansdemeure.zenindex.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sansdemeure.zenindex.entity.Doc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mcourcy
 *
 */

@Repository
@Transactional(readOnly = true)
public class JPADocRepository implements DocRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Doc save(Doc doc) {
		if (doc.getId() == null) {
			em.persist(doc);
			return doc;
		} else {
			return em.merge(doc);
		}
	}

}
