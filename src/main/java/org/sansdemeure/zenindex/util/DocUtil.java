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

/**
 * Calculate a md5 sum.
 * 
 * @author mcourcy
 *
 */
public class DocUtil {

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

}
