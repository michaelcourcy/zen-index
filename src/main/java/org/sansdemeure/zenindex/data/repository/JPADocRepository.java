/**
 * 
 */
package org.sansdemeure.zenindex.data.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sansdemeure.zenindex.data.entity.Doc;
import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.data.entity.dto.DocPartInfo;
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


	@Transactional
	public void saveNewKeywords(List<Keyword> keywordsFound) {
		for (Keyword k : keywordsFound){
			if (k.getId() == null){
				save(k);
			}
		}
		
	}
	
	@Transactional
	public List<DocPart> getAllDocPart() {
		return em.createQuery("select dp from DocPart dp").getResultList();
	}
	
	@Transactional
	public List<DocPart> getAllDocPart(Long docId) {
		return em.createQuery("select dp from DocPart dp inner join dp.doc d where d.id = :id")
				.setParameter("id", docId)
				.getResultList();
	}


	public Doc getDocByNameAndPath(String name, String path) {
		return (Doc) em.createQuery("select d from Doc d where d.name like :name and d.path like :path")
				.setParameter("name", name)
				.setParameter("path", path)
				.getSingleResult();
	}


	public List<Keyword> getAllKeywords() {
		return em.createQuery("From Keyword order by word").getResultList();
	}


	public List<DocPartInfo> getDocPartInfos(Keyword keyword) {
		return em.createQuery(
				"select new " + DocPartInfo.class.getName() + "("
				+ "d.name,"
				+ "d.path,"
				+ "dp.annotationName,"
				+ "dp.pageStart,"
				+ "dp.pageEnd,"
				+ "dp.text,"
				+ "dpk.pertinence,"
				+ "dpk.extraInfo"
				+ ")"
				+ " from DocPart as dp"
				+ " join dp.doc as d"
				+ " join dp.docPartKeywords as dpk"
				+ " join dpk.keyword as k"
				+ " where k.id = :k_id"
 		)
		.setParameter("k_id", keyword.getId())
		.getResultList();
	}
	
	

}
