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
 * Create a wrapper around ODT to get the content of the zip, calculate md5 and
 * so on.
 * 
 * @author mcourcy
 *
 */
public class ODTResource implements AutoCloseable{

	File odt;
	ZipFile zipFile = null;
	InputStream contentXMLStream = null;

	public ODTResource(File odt) {
		this.odt = odt;
	}

	final static Logger logger = LoggerFactory.getLogger(ODTResource.class);

	

	/**
	 * Read the content of an odt to get content.xml as a stream.
	 * 
	 * @param odt
	 * @throws IOException
	 */
	public InputStream openContentXML() {
		try {
			zipFile = new ZipFile(odt);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.getName().contains("content.xml")) {
					contentXMLStream = zipFile.getInputStream(entry);
					logger.debug("content.xml found");
					return contentXMLStream;
				}
			}
		} catch (ZipException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		throw new RuntimeException("Unable to find content.xml in the odt " + odt.getAbsolutePath());
	}

	@Override
	public void close() {
		if (contentXMLStream !=null ){
			try {
				contentXMLStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (zipFile != null) {
			try {
				zipFile.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
