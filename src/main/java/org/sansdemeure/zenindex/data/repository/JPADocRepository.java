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
