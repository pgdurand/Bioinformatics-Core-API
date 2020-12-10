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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;

/**
 * Import Interproscan predicted domains from XML files. 
 * 
 * For (1) performance issue and (2) independence with regards of evolving Interproscan XML schema, 
 * this class relies upon the use of a dedicated SAX parser.
 * 
 * @author Patrick G. Durand
 */
public class IprXmlReader {

  //Docs: https://interproscan-docs.readthedocs.io/en/latest/UserDocs.html
  //XSD: ftp://ftp.ebi.ac.uk/pub/software/unix/iprscan/5/schemas/interproscan-model-4.0.xsd

  /**
   * Load data.
   * 
   * @param f path to an Interproscan XML formatted data file
   * @param remap figure out whether or not we have to remap locations (nucleotide only)
   * 
   * @return predicted domains as a map where keys are sequence IDs and values are
   * corresponding FeatureTables containing predicted domains. Return null in case of
   * SAX parsing error.
   */
  public Map<String, FeatureTable> readFile(File f, boolean remap) {
    //auto-detect data file type
    boolean isNucl = false;
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setValidating(true);
      SAXParser saxParser = factory.newSAXParser();
      DetectIprDataFileTypeHandler handler = new DetectIprDataFileTypeHandler();
      saxParser.parse(f, handler);
    } 
    catch (StopScanningException ste) {
      isNucl = ste.isNucleotide();
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    //scan data file and get predicted domains
    HashMap<String, FeatureTable> predictions = new HashMap<>();    
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setValidating(true);
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(
          f, 
          isNucl ? new NucleotideInterproScanDataHandler(predictions, remap) : new ProteinInterproScanDataHandler(predictions));
    } catch (Exception e) {
      predictions.clear();
      e.printStackTrace();
      return null;
    }
    return predictions;
  }
  
  /**
   * Load data. 
   * 
   * Do remap of locations for nucleotide sequences.
   * 
   * @param f path to an Interproscan XML formatted data file
   * 
   * @return predicted domains as a map where keys are sequence IDs and values are
   * corresponding FeatureTables containing predicted domains. Return null in case of
   * SAX parsing error.
   */

  public Map<String, FeatureTable> readFile(File f) {
    return readFile(f, true);
  }
}
