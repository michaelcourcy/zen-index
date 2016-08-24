/**
 * 
 */
package org.sansdemeure.zenindex.data.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mcourcy
 *
 */

@Repository
@Transactional(readOnly = true)
public class JPADocRepository {

	@PersistenceContext
	private EntityManager em;

	
	@Transactional
	public Doc save(Doc doc) {
		if (doc.getId() == null) {
			em.persist(doc);
			return doc;
		} else {
			return em.merge(doc);
		}
	}

	
	@Transactional
	public Keyword save(Keyword keyword) {
		if (keyword.getId() == null){
			em.persist(keyword);
			return keyword;
		} else {
			return em.merge(keyword);
		}
	}

	@Transactional
	public Doc getDoc(Long id) {
		return em.find(Doc.class,id);
	}

}
