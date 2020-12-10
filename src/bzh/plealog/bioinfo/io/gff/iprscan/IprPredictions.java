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
package bzh.plealog.bioinfo.io.gff.iprscan;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import com.plealog.genericapp.api.log.EZLogger;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;

/**
 * Utility class to model IPRscan domain predictions. It is used to convert list
 * of IprGffObjects to a FeatureTable instance suitable to be used by all other
 * Core APIs in this library.
 * 
 * @see test.TestFeatureSystem for sample use case

 * @author Patrick G. Durand
 */
public class IprPredictions {

  private List<IprPrediction> gffObjs;
  private String iprscanVersion;
  private String date;
  
  /**
   * Constructor.
   * 
   * @param objs list of IPRscan predictions as read from file
   */
  public IprPredictions(List<IprGffObject> objs) {
    gffObjs = new ArrayList<IprPrediction>();
    for(IprGffObject gff : objs) {
      gffObjs.add(new IprPrediction(gff));
    }
  }

  public String getIprscanVersion() {
    return iprscanVersion;
  }

  public void setIprscanVersion(String iprscanVersion) {
    this.iprscanVersion = iprscanVersion;
  }

  public void setIprscanDate(String d) {
    this.date = d;
  }

  public String getIprscanDate() {
    return date;
  }


  /**
   * Return number of domain predictions.
   * 
   * @return number of domain predictions.
   */
  public int size() {
    return gffObjs.size();
  }

  /**
   * Returns all domains.
   * 
   * @return all domain predictions
   */
  public List<IprPrediction> getAllDomains(){
    return gffObjs;
  }
  
  /**
   * Returns unique domain predictions. Rely upon the use of equal method from
   * IprPrediciton class.
   * 
   * @return list of unique domain predictions.
   */
  public List<IprPrediction> filterUniqueDomains(){
    return gffObjs.stream().distinct().collect(Collectors.toList());
  }

  /**
   * Return a FeatureTable representation of list of domain predictions.
   * 
   * @param uniqueDomainId figure out whether or not return unique domains. 
   * 
   * @see IprPredictions#filterUniqueDomains()
   * 
   * @return a FeatureTable representation of list of (unique) domain predictions
   */
  public FeatureTable getFeatureTableBak(boolean uniqueDomainId) {
    List<IprPrediction> filtered_objs;
    FeatureTable ft;
    Feature feat;
    int protStartOnNuc=0, protStopOnNuc=0, strand=0;
    
    ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
    
    if (uniqueDomainId) {
      filtered_objs = filterUniqueDomains();
    }
    else {
      filtered_objs = gffObjs;
    }
    
    for(IprPrediction pred : filtered_objs) {
      feat = pred.getFeature();
      if (feat.getKey().equals(IprPrediction.UNK)==false) {
        ft.addFeature(feat);
        //the following enables to detect nucleotide sequence provided by user
        if (feat.getKey().equals(IprPrediction.PROTEIN)) {
          protStartOnNuc = feat.getFrom();
          protStopOnNuc = feat.getTo();
          strand = feat.getStrand();
        }
      }
    }
    
    ft.setSource(getIprscanVersion());
    ft.setDate(getIprscanDate());
    
    //now, we may have to adjust domain location with regard to user provided
    //sequence. In case of protein: nothing to do. In case of nucleotide: remap
    // Iprscan predicted domains to user_provided nucleotide one.
    if (protStartOnNuc==0 && protStopOnNuc==0 && strand==0){
      //we have protein sequences, no remap do to
      return ft;
    }
    //remap protein domain location to nucleotide sequence coordinate system
    boolean reverseLocation = strand==Feature.MINUS_STRAND;
    Enumeration<Feature> enumFeats = ft.enumFeatures();
    while(enumFeats.hasMoreElements()) {
      feat = enumFeats.nextElement();
      if (reverseLocation){
        //in Feature data model, 'from' must be less than 'to'... as Iprscan does
        feat.setFrom(protStopOnNuc - (feat.getTo()-1)*3);
        feat.setTo(protStopOnNuc - (feat.getFrom()-1)*3);
        feat.setStrand(Feature.MINUS_STRAND);
      }
      else {
        feat.setFrom(protStartOnNuc + (feat.getFrom()-1)*3);
        feat.setTo(protStartOnNuc + (feat.getTo()-1)*3);
      }
    }
    return ft;
  }
  public FeatureTable getFeatureTable(boolean uniqueDomainId) {
    List<IprPrediction> filtered_objs;
    FeatureTable ft;
    Feature feat;
    boolean doRemap = false;
    
    
    ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
    ft.setSource(getIprscanVersion());
    ft.setDate(getIprscanDate());
    
    if (uniqueDomainId) {
      filtered_objs = filterUniqueDomains();
    }
    else {
      filtered_objs = gffObjs;
    }
    
    for(IprPrediction pred : filtered_objs) {
      feat = pred.getFeature();
      if (feat.getKey().equals(IprPrediction.UNK)==false) {
        ft.addFeature(feat);
        //the following enables to detect nucleotide sequence provided by user
        if (feat.getKey().equals(IprPrediction.PROTEIN)) {
          doRemap = true;
        }
      }
    }
    
    //now, we may have to adjust domain location with regard to user provided
    //sequence. For nucl sequences, IPRscan provides protein predicted domain
    //locations using protein sequence coordinate system... however, we handle
    //a feature table using nucleotide sequence coordinate system.
    //In case of protein: nothing to do. In case of nucleotide: remap
    //Iprscan predicted domains to user_provided nucleotide one.
    if (!doRemap){
      //we have protein sequences, no remap do to
      return ft;
    }
    remapLocations(ft, gffObjs.get(0).getIprGffObject().getRegionId());
    return ft;
  }

  public static void remapLocations(FeatureTable ft, String seqId) {
    //remap protein domain location to nucleotide sequence coordinate system
    int protStartOnNuc=0, protStopOnNuc=0, strand=0, from, to;
    boolean reverseLocation = strand==Feature.MINUS_STRAND;
    Enumeration<Feature> enumFeats = ft.enumFeatures();
    while(enumFeats.hasMoreElements()) {
      Feature feat = enumFeats.nextElement();
      if (feat.getKey().equals(IprPrediction.DOMAIN)==false) {
        //to remap domains, retrieve location of ORF on nucleic_sequence.
        //Since ORF is always reported BEFORE domains, this code is fine
        if (feat.getKey().equals(IprPrediction.PROTEIN)) {
          protStartOnNuc = feat.getFrom();
          protStopOnNuc = feat.getTo();
          strand = feat.getStrand();
          reverseLocation = strand==Feature.MINUS_STRAND;
        }
        continue;
      }
      //remap locations ONLY for features of type domain
      from = feat.getFrom();
      to = feat.getTo();
      try {
        if (reverseLocation){
          //in Feature data model, 'from' must be less than 'to'... as Iprscan does
          feat.setFrom(protStopOnNuc - (to-1)*3);
          feat.setTo(protStopOnNuc - (from-1)*3);
          feat.setStrand(Feature.MINUS_STRAND);
        }
        else {
          feat.setFrom(protStartOnNuc + (from-1)*3);
          feat.setTo(protStartOnNuc + (to-1)*3);
        }
      }
      catch(IllegalArgumentException t) {
        //catching in a loop is not really appropriate... however, we would like
        //to continue loading data even is some features are wrongly remapped
        EZLogger.warn(String.format("> %s: domain %d..%d: %s", 
            seqId, 
            from, 
            to,
            t.getMessage()));
      }
    }
  }
}
