package org.sansdemeure.zenindex.util;

import org.junit.Assert;
import org.junit.Test;

public class TestChangeExtensionName {
	
	@Test
	public void test(){
		Assert.assertEquals("Sandokai.html",FileUtil.changeExtensionToHtml("Sandokai.odt"));
	}

}
