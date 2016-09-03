package org.sansdemeure.zenindex.service;

import java.io.File;

import org.sansdemeure.zenindex.data.repository.JPADocRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentManager {
	
	@Autowired 
	JPADocRepository docRepository;
	
	/**
	 * I wonder if we should 
	 * @param odtFile
	 */
	public void treatDocument(File odtFile){
		
	}
	

}
