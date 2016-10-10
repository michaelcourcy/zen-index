package org.sansdemeure.zenindex.indexer;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.sansdemeure.zenindex.data.entity.DocPart;
import org.sansdemeure.zenindex.data.entity.Keyword;
import org.sansdemeure.zenindex.util.Pair;

public interface DocumentIndexer {
	
	Map<String, Object> content(File originalDocument);
	
	Pair<List<Keyword>, List<DocPart>> getKeywordsAndDocParts(File originalDocument, List<Keyword> alreadyFoundKeywords);

}
