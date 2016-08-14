/**
 * 
 */
package org.sansdemeure.zenindex.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculate a md5 sum.
 * 
 * @author mcourcy
 *
 */
public class DocUtil {
	
	final static Logger logger = LoggerFactory.getLogger(DocUtil.class);

	public static String calculateMD5(File file) {
		InputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
		    fis = new FileInputStream(file);
		    byte[] dataBytes = new byte[1024];

		    int nread = 0;

		    while ((nread = fis.read(dataBytes)) != -1) {
		      md.update(dataBytes, 0, nread);
		    };

		    byte[] mdbytes = md.digest();

		    //convert the byte to hex format
		    StringBuffer sb = new StringBuffer("");
		    for (int i = 0; i < mdbytes.length; i++) {
		    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if (fis != null) fis.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
		
	/**
	 * Read the content of an odt to get content.xml as a stream.
	 * @param odt
	 * @throws IOException 
	 */
	public static InputStream getContentXML(File odt){
		
		try {
			ZipFile zipFile = new ZipFile(odt);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
 
			while(entries.hasMoreElements()){
			    ZipEntry entry = entries.nextElement();
			    if (entry.getName().contains("content.xml")){
			    	InputStream stream = zipFile.getInputStream(entry);
			    	logger.debug("content.xml found");
			    	return stream;
			    }
			}
		} catch (ZipException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		throw new RuntimeException("Unable to find content.xml in the odt " + odt.getAbsolutePath() );
		
	}

}
