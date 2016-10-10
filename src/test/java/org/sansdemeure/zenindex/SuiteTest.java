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
package org.sansdemeure.zenindex;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sansdemeure.zenindex.data.repository.TestDocRepository;
import org.sansdemeure.zenindex.data.repository.TestDocRepository2;
import org.sansdemeure.zenindex.indexer.odt.TestCommentExtractor;
import org.sansdemeure.zenindex.indexer.odt.TestHTMLConverter;
import org.sansdemeure.zenindex.iterator.TestFileIterator;
import org.sansdemeure.zenindex.service.TestBatchService;
import org.sansdemeure.zenindex.service.TestBatchService2;
import org.sansdemeure.zenindex.service.TestFreeMarkerTemplate;
import org.sansdemeure.zenindex.util.TestChangeExtensionName;
import org.sansdemeure.zenindex.util.TestDocUtilGetContentXML;
import org.sansdemeure.zenindex.util.TestDocUtilMD5;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestDocRepository.class,
	TestDocRepository2.class,
	TestCommentExtractor.class,
	TestHTMLConverter.class,
	TestFileIterator.class,
	TestBatchService.class,
	TestBatchService2.class,
	TestFreeMarkerTemplate.class,
	TestChangeExtensionName.class,
	TestDocUtilGetContentXML.class,
	TestDocUtilMD5.class,
	})
public class SuiteTest {

}
