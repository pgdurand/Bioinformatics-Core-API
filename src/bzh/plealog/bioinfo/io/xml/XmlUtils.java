/* Copyright (C) 2020 Patrick G. Durand
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

import java.io.File;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

/**
 * Utility class.
 * 
 * @author Patrick G. Durand
 */
public class XmlUtils {
  private static final Logger LOG = Logger.getLogger(XmlUtils.class.getSimpleName());
  /**
   * Load XML document without any validation of DTD. Thus enabling data load without any remote connection.
   * 
   * @param f the data file to load
   * @param cl the class model
   * 
   * @return loaded data as an object or null if cannot load for any reasons. Errors, if any, are logged.
   */
  public static Object loadXmlFile(File f, Class<?> cl) {
    Object obj = null;
    
    try {
      //prepare a JAXB unmarshaller that won't check for DTD declared in DOCTYPE, if any
      JAXBContext jc = JAXBContext.newInstance(cl);

      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      spf.setFeature("http://xml.org/sax/features/validation", false);
      
      SAXSource source = new SAXSource(
          spf.newSAXParser().getXMLReader(), 
          new InputSource(new FileReader(f)));

      obj = jc.createUnmarshaller().unmarshal(source);
    }
    catch (Exception ex) {
      LOG.warn(ex);
    }
    return obj;
    
  }

}
