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
import bzh.plealog.bioinfo.io.gff.iprscan.IprPredictions;

/**
 * Utility class to handle Interproscan scan domain predictions made on nucleotide sequences.
 * 
 * @author Patrick G. Durand
 */
public class NucleotideInterproScanDataHandler extends DefaultHandler {
  
  Map<String, FeatureTable> _predictions;
  FeatureTable _ft = null;
  Feature _feat = null;
  Feature _featOrf = null;
  Feature _featSource = null;
  FeatureLocation _loc = null;
  
  boolean _remap = true;
  boolean _b_protein = false;// begin a new protein
  boolean _b_nuclseq = false;// begin a new nucl sequence
  boolean _b_sequence = false;// begin the sequence associated with above
  boolean _b_domainFound = false;
  String _iprVersion = null;
  String _entryId = null;
  String _seqId = null;
  String _domainId = null;
  String _domainDesc = null;
  String _domainType = null;
  int    _nuclSequenceSize = 0;
  
  public NucleotideInterproScanDataHandler() {
    if(IprXmlUtils._verbose_) {
      System.out.println("*** NucleotideInterproScanDataHandler");
    }
  }
  /**
   * Constructor
   * 
   * @param predictions will be used to store domains. So pass in here an empty instance
   * of a Map.
   */
  public NucleotideInterproScanDataHandler(Map<String, FeatureTable> predictions) {
    this();
    _predictions = predictions;
  }
  
  public NucleotideInterproScanDataHandler(Map<String, FeatureTable> predictions, boolean remap) {
    this();
    _predictions = predictions;
    _remap = remap;
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase(IprXmlUtils.E_nucl_sequence_matches)) {
      _iprVersion = attributes.getValue(IprXmlUtils.S_ipr_version);
    }
    //A nucleotide sequence: start collecting data
    else if (qName.equalsIgnoreCase(IprXmlUtils.E_nucl_sequence)) {
      _ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
      _ft.setSource(String.format(IprXmlUtils.QUAL_IPR, _iprVersion));
      _b_nuclseq = true;
    }
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_protein)) {
      _b_protein = true;
    }
    else if (qName.equalsIgnoreCase(IprXmlUtils.E_sequence)) {
      _b_sequence = true;
    }
    // Prediction for a new sequence
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_xref)) {
      _seqId = attributes.getValue(IprXmlUtils.A_id);
      if (_b_nuclseq && !_b_protein) {
        if(IprXmlUtils._verbose_) {
          System.out.println("> "+_seqId);
        }
        _entryId=_seqId;
        //with nucl sequence, IPRscan provides origin sequence, provided by user (e.g. a contig)
        //then we'll have ORFs (see below)
        _featSource = CoreSystemConfigurator.getFeatureTableFactory().getFInstance();
        _featSource.setKey(IprPrediction.SOURCE);
        _featSource.addQualifier(IprPrediction.ID_QUAL, _seqId);
        _featSource.addQualifier(IprPrediction.STATUS_QUAL, IprPrediction.S_p_by_user);
        _featSource.setFrom(1);
        _featSource.setTo(_nuclSequenceSize);
        _featSource.setStrand(Feature.PLUS_STRAND);
      }
    }
    // an ORF
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_orf)){ 
      //end="1101" start="1" strand="SENSE"
      _featOrf = CoreSystemConfigurator.getFeatureTableFactory().getFInstance();
      _featOrf.setKey(IprPrediction.PROTEIN);
      _featOrf.addQualifier(IprPrediction.ID_QUAL, _seqId);
      int a = Integer.valueOf(attributes.getValue(IprXmlUtils.A_start));
      int b = Integer.valueOf(attributes.getValue(IprXmlUtils.A_end));
      _featOrf.setFrom(a);
      _featOrf.setTo(b);
      _featOrf.setStrand(IprXmlUtils.S_sense.equals(attributes.getValue(IprXmlUtils.A_strand)) ? 
          Feature.PLUS_STRAND : Feature.MINUS_STRAND);
    }
    // a domain
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_signature)){ 
      _domainId = attributes.getValue(IprXmlUtils.A_ac);      
      _domainDesc = attributes.getValue(IprXmlUtils.A_desc);      
      //Create a new Feature
      _feat = CoreSystemConfigurator.getFeatureTableFactory().getFInstance();
      _feat.setKey(IprPrediction.DOMAIN);
      _feat.setStrand(Feature.PLUS_STRAND);
      _loc = new FeatureLocation();
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
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_entry) && _b_protein) {
      IprXmlUtils.handleInterpro(_feat, attributes);
    }
    // GO data
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_go_xref) && _b_protein) {
      IprXmlUtils.handleGeneOntology(_feat, attributes);
    }
    // domain location: now, we should have all information
    // to save new domain prediction
    //else if(qName.equalsIgnoreCase(IprXmlUtils.E_hmmer3_location) || qName.equalsIgnoreCase(IprXmlUtils.E_profilescan_location)) {
    else if(qName.endsWith(IprXmlUtils.E_location_suffix)) {
      IprXmlUtils.handleLocation(_feat, _loc, attributes);
    }
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    if(qName.equalsIgnoreCase(IprXmlUtils.E_protein)) {
      _b_protein = false;
      _b_domainFound = false;
    }
    else if (qName.equalsIgnoreCase(IprXmlUtils.E_nucl_sequence)) {
      if (_ft.features()!=0) {
        _ft.addFeature(_featSource);
        //now, we may have to adjust domain location with regard to user provided
        //sequence. For nucl sequences, IPRscan provide protein predicted domain
        //locations using protein sequence coordinate system... however, we handle
        //a feature table using nucleotide sequence coordinate system.
        if (_remap) {
          IprPredictions.remapLocations(_ft, _entryId);
        }
        _predictions.put(_entryId, _ft);
      }
      //reset all
      _featSource = null;
      _b_protein = _b_sequence = _b_nuclseq = _b_domainFound = false;
      _ft = null;
      _feat = null;
      _loc = null;
      _domainId = _domainDesc = _domainType = null;
      _seqId = _entryId = null;
      _nuclSequenceSize = 0;
    }
    else if(qName.equalsIgnoreCase(IprXmlUtils.E_hmmer3_location) || qName.equalsIgnoreCase(IprXmlUtils.E_profilescan_location)) {
      _feat.setFeatureLocation(_loc);
      if (_domainType!=null) {
        _feat.addQualifier(AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, 
            _domainType + 
            "; " +
            _domainId +
            "; " +
            _domainDesc);
        _ft.addFeature(_feat);
        _b_domainFound = true;
      }
      _feat = null;
      _loc = null;
    }
    else if (qName.equalsIgnoreCase(IprXmlUtils.E_sequence)) {
      _b_sequence = false;
    }
    if(qName.equalsIgnoreCase(IprXmlUtils.E_orf)){ 
      if (_b_domainFound) {
        _ft.addFeature(_featOrf);
      }
      _featOrf = null;
    }
  }

  public void characters(char ch[], int start, int length) throws SAXException {
    if (_b_nuclseq && _b_sequence) {
      _nuclSequenceSize = new String(ch, start, length).length();
    }
  }
}