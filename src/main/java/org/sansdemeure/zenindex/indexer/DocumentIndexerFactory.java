package org.sansdemeure.zenindex.indexer;

import org.sansdemeure.zenindex.indexer.odt.OdtIndexer;

public class DocumentIndexerFactory {
	
	public static DocumentIndexer build(){
		return new OdtIndexer();
	}

}
