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

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureLocation;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.io.gff.iprscan.IprPrediction;

/**
 * Utility class to handle Interproscan domain predictions made on protein sequences.
 * 
 * @author Patrick G. Durand
 */
public class ProteinInterproScanDataHandler extends DefaultHandler {
  //Store all reported sequences with predicted domains as FeatureTable objects
  Map<String, FeatureTable> _predictions;

  //Objects fill in during XML file parsing
  FeatureTable _ft = null;
  Feature _feat = null;
  FeatureLocation _loc = null;
  String _iprVersion = null;
  String _seqId = null;
  String _domainId = null;
  String _domainDesc = null;
  String _domainType = null;
  
  public ProteinInterproScanDataHandler() {
    if(IprXmlUtils._verbose_) {
      System.out.println("*** ProteinInterproScanDataHandler");
    }
  }
  /**
   * Constructor
   * 
   * @param predictions will be used to store domains. So pass in here an empty instance
   * of a Map.
   */
  public ProteinInterproScanDataHandler(Map<String, FeatureTable> predictions) {
    this();
    _predictions = predictions;
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase(IprXmlUtils.E_protein_matches)) {
      _iprVersion = attributes.getValue(IprXmlUtils.S_ipr_version);
    }
    //A protein sequence: start collecting data
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_protein)) {
      _ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
      _ft.setSource(String.format(IprXmlUtils.QUAL_IPR, _iprVersion));
      
    }
    // Prediction for a new sequence
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_xref)) {
      _seqId = attributes.getValue(IprXmlUtils.A_id);
      if(IprXmlUtils._verbose_) {
        System.out.println("> "+_seqId);
      }
    }
    // a domain
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_signature)){ 
      _domainId = attributes.getValue(IprXmlUtils.A_ac);      
      _domainDesc = attributes.getValue(IprXmlUtils.A_desc);
      if (_domainDesc==null) {
        _domainDesc = attributes.getValue(IprXmlUtils.A_name);
      }
      //Create a new Feature
      _feat = CoreSystemConfigurator.getFeatureTableFactory().getFInstance();
      _feat.setKey(IprPrediction.DOMAIN);
      _feat.setStrand(Feature.PLUS_STRAND);
      if(IprXmlUtils._verbose_) {
        System.out.println("\""+attributes.getValue(IprXmlUtils.A_ac)+"\",");
      }
    } 
    // a domain type
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_signature_library_release)){
      _domainType = attributes.getValue(IprXmlUtils.A_library);
      _domainType = IprXmlUtils.ACCEPTED_DOMAINS.get(_domainType);
    }
    // IPR data
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_entry)) {
      IprXmlUtils.handleInterpro(_feat, attributes);
    }
    // GO data
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_go_xref)) {
      IprXmlUtils.handleGeneOntology(_feat, attributes);
    }
    // domain location: now, we should have all information
    // to save new domain prediction
    else if (qName.equalsIgnoreCase(IprXmlUtils.E_locations)) {
      _loc = new FeatureLocation();
    }
    else if(qName.endsWith(IprXmlUtils.E_location_suffix)) {
      IprXmlUtils.handleLocation(_feat, _loc, attributes);
    }
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    if(qName.equalsIgnoreCase(IprXmlUtils.E_protein)) {
      //save protein prediction
      if (_ft.features()!=0) {
        _predictions.put(_seqId, _ft);
      }
      //reset all
      _ft = null;
      _feat = null;
      _loc = null;
      _domainId = _domainDesc = _domainType = null;
    }
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_locations)) {
      _loc.setElements(_loc.getAscentSortedElements());
      _feat.setFeatureLocation(_loc);
      if (_domainType!=null) {
        _feat.addQualifier(AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, 
            _domainType + 
            "; " +
            _domainId +
            "; " +
            (_domainDesc!=null?_domainDesc:""));
        _ft.addFeature(_feat);
      }
      _feat = null;
      _loc = null;
    }
  }

  public void characters(char ch[], int start, int length) throws SAXException {
  }
}