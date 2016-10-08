package org.sansdemeure.zenindex.indexer;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;

public interface DocumentIndexer {
	
	Map<String, Object> content(File originalDocument);
	
	List<DocPart> getKeywordsAndDocParts(File originalDocument, List<Keyword> alreadyFoundKeywords);

}
