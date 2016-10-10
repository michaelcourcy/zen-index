package org.sansdemeure.zenindex.indexer;

import java.io.File;

import org.sansdemeure.zenindex.indexer.odt.OdtIndexer;

public class DocumentIndexerFactory {
	
	public static DocumentIndexer build(){
		return new OdtIndexer();
	}
	
	public static boolean canProvideAnIndexerForThisFile(File file){
		return file.getName().endsWith(".odt");
	}

}
