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
package bzh.plealog.bioinfo.io.xml.iprscan;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class to detect protein versus nucleotide Interproscan data file.
 * 
 * @author Patrick G. Durand
 */
public class DetectIprDataFileTypeHandler extends DefaultHandler {
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    //Raise an exception to force SAX parser to end data file parsing, 
    //as soon as we have detected these elements.
    if (qName.equalsIgnoreCase(IprXmlUtils.E_protein_matches)) {
      throw new StopScanningException(false);
    }
    else if (qName.equalsIgnoreCase(IprXmlUtils.E_nucl_sequence_matches)) {
      throw new StopScanningException(true);
    }
  }
}