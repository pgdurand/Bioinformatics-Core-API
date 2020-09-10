/* Copyright (C) 2006-2019 Patrick G. Durand
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.txt
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 */
package bzh.plealog.bioinfo.io.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import bzh.plealog.bioinfo.util.CoreUtil;

/**
 * This is a basic XSL transformer class.
 * 
 * @author Patrick Durand
 */
public class CoreXSLTransformer implements EntityResolver {

	private static final Logger	_logger	= Logger.getLogger("CoreXSLTransformer");

	/**
	 * Default constructor.
	 */
	public CoreXSLTransformer() {
	}

	private String transformFile(InputStream xml, InputStream is) throws Exception {
		StreamSource source = new StreamSource(is);
		Transformer transformer = TransformerFactory.newInstance().newTransformer(source);
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		StringWriter writer = new StringWriter();
		XMLReader reader = XMLReaderFactory.createXMLReader();
		reader.setEntityResolver(new CoreXSLTransformer());
		transformer.transform(new SAXSource(reader, new InputSource(xml)), new StreamResult(writer));
		return writer.toString();
	}

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		return new InputSource(new ByteArrayInputStream(new byte[0]));
	}

  /**
   * Transform source file using XSLT.
   * 
   * @param source file to transform
   * @param xs path to XSL file
   * 
   * @throws IOException if XSL file cannot be located
   * 
   * @return file containing result of XSL transformation or null.
   */
	public File transformFile(File source, File xsl) {
		File output = null;
		try {
			output = File.createTempFile("klhec_", ".tmp");
			if (!transformFile(source, output, xsl)) {
			  CoreUtil.deleteQuietly(output);
				output = null;
			}
		} catch (IOException e) {
			_logger.warn(e);
		} finally {
			if (output != null) {
				output.deleteOnExit();
			}
		}
		return output;
	}

  /**
   * Transform source file using XSLT.
   * 
   * @param source file to transform
   * @param xs path to XSL file
   * 
   * @throws IOException if XSL file cannot be located
   * 
   * @return file containing result of XSL transformation or null.
   */
	public File transformFile(File source, String xslFileName) {
		File output = null;
		File resFile;
		InputStream in = null;

		try {

			try {
				resFile = new File(xslFileName);
				// first, try to locate the file in the user conf dir
				if (resFile.exists()) {
					in = new FileInputStream(resFile);
				} else {//load from Jar
					in = CoreXSLTransformer.class.getResourceAsStream(xslFileName);
				}
			} catch (Exception ex) {
				throw new IOException("Unable to locate resource: " + xslFileName + ": " + ex.getMessage());
			}

			output = File.createTempFile("klhec_", ".tmp");
			if (!transformFile(source, output, in)) {
			  CoreUtil.deleteQuietly(output);
				output = null;
			}
		} catch (IOException e) {
			_logger.warn(e);
		} finally {
			CoreUtil.closeQuietly(in);
			if (output != null) {
				output.deleteOnExit();
			}
		}
		return output;
	}

  /**
   * Transform source file using XSLT.
   * 
   * @param source file to transform
   * @param dest file to create
   * @param xs path to XSL file
   * 
   * @return true if transformation succeeded.
   */
	public boolean transformFile(File source, File dest, File xsl) {
		FileInputStream xs = null;
		boolean bRet = false;
		try {
			xs = new FileInputStream(xsl);
			bRet = transformFile(source, dest, xs);
		} catch (Exception e) {
			_logger.warn(e);
		} finally {
			CoreUtil.closeQuietly(xs);
		}
		return bRet;
	}

	/**
	 * Transform source file using XSLT.
	 * 
	 * @param source file to transform
	 * @param dest file to create
	 * @param xs path to XSL file
	 * 
	 * @return true if transformation succeeded.
	 */
	public boolean transformFile(File source, File dest, InputStream xs) {
		InputStream in = null;
		FileOutputStream out = null;
		boolean bRet = false;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);
			out.write(transformFile(in, xs).getBytes());
			out.flush();
			bRet = true;
		} catch (Exception e) {
			_logger.warn(e);
		} finally {
		  CoreUtil.closeQuietly(in);
		  CoreUtil.closeQuietly(out);
		}
		return bRet;
	}
}