package org.sansdemeure.zenindex;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sansdemeure.zenindex.data.repository.TestDocRepository;
import org.sansdemeure.zenindex.handler.TestCommentExtractor;
import org.sansdemeure.zenindex.handler.TestHTMLConverter;
import org.sansdemeure.zenindex.iterator.TestFileIterator;
import org.sansdemeure.zenindex.service.TestBatchService;
import org.sansdemeure.zenindex.service.TestFreeMarkerTemplate;
import org.sansdemeure.zenindex.util.TestChangeExtensionName;
import org.sansdemeure.zenindex.util.TestDocUtilGetContentXML;
import org.sansdemeure.zenindex.util.TestDocUtilMD5;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestDocRepository.class,
	TestCommentExtractor.class,
	TestHTMLConverter.class,
	TestFileIterator.class,
	TestBatchService.class,
	TestFreeMarkerTemplate.class,
	TestChangeExtensionName.class,
	TestDocUtilGetContentXML.class,
	TestDocUtilMD5.class,
	})
public class SuiteTest {

}
