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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import test.TestSerialSystem;

/**
 * Utility class to model a single IPRscan domain prediction. It is used to convert 
 * an IprGffObject to a FeatureT instance suitable to be used by all other
 * Core APIs in this library.

 * @see TestSerialSystem for sample use case
 * 
 * @author Patrick G. Durand
 */
public class IprPrediction {
  private IprGffObject gffObject;
  private String key;
  
  //These are the keys to be used to create FeatureTable
  public static final String ID_QUAL = "id";
  public static final String NAME_QUAL = "name";
  public static final String SIGNATURE_QUAL = "description";
  public static final String DBXREF_QUAL = "dbxref";

  //These are the attributes to look for supported protein domains
  // in GFF3 attributes as reported by IprScan.
  public static final Set<String> ACCEPTED_DOMAINS = new HashSet<>(Arrays.asList("Pfam", "ProSiteProfiles"));

  //This is the Feature type used to create Feature objects containing IPR domain predictions
  public static final String DOMAIN = "domain";
  public static final String SOURCE = "source";
  public static final String PROTEIN = "protein";
  public static final String UNK = "unknown";

  /**
   * Constructor.
   * 
   * @param obj IPRscan domain prediction object
   */
  public IprPrediction(IprGffObject obj) {
    setIprGffObject(obj);
  }

  /**
   * Sets a new IprGffObject instance.
   * 
   * @param obj IPRscan domain prediction object
   */
  public void setIprGffObject(IprGffObject obj) {
    gffObject = obj;
    
    String name=gffObject.getAttributeValue(IprGffObject.NAME_ATTR);
    if (name==null) {
      name=gffObject.getAttributeValue(IprGffObject.ID_ATTR);
    }
    key = gffObject.getSource()+"/"+gffObject.getType()+":"+name;
  }
 
  /**
   * Returns IprGffObject instance.
   * 
   * @return IprGffObject wrapped in this class
   * 
   */
  public IprGffObject getIprGffObject(){
    return gffObject;
  }
  
  //Notice: toString(), equals() and hashcode() are mostly overridden to
  // enable appropriate display of values (equals) and stream filtering
  // of distinct values (equals and hashcode)

  @Override
  public String toString(){
    return getKey();
  }
  
  @Override
  public boolean equals(Object o) {
    return o!=null 
        && o instanceof IprPrediction && 
        ((IprPrediction)o).getKey().equals(getKey());
  }
  
  @Override
  public int hashCode() {
    return getKey().hashCode();
  }

  /**
   * Returns the identifier of this object. It is mostly used to get
   * distinct (unique) domain prediction.
   * 
   * @return object key
   * */
  public String getKey() {
    return key;
  }
  
  /**
   * Convert this object to a Feature.
   * 
   * @return a Feature instance. It is worth noting that Feature key maybe equal to
   * UNK constant if IprGffObject type is not supported by Feature data model. See
   * IprGffObject.XXX_TYPE constants.
   */
  public Feature getFeature() {
    // Attempt to do some interpretation of IPRscan data
    
    Feature feat = CoreSystemConfigurator.getFeatureTableFactory().getFInstance();

    if (!ACCEPTED_DOMAINS.contains(gffObject.getSource())) {
      //handle source of this prediction, which is user provided prot/nuc sequence
      feat.setFrom(Integer.valueOf(gffObject.getStart()));
      feat.setTo(Integer.valueOf(gffObject.getEnd()));
      feat.setStrand(Feature.PLUS_STRAND);
      switch (gffObject.getSource()) {
      case IprGffObject.NUC_TYPE:
        feat.setKey(SOURCE);
        feat.addQualifier(ID_QUAL, gffObject.getAttributeValue(IprGffObject.ID_ATTR));
        break;
      case IprGffObject.ORF_TYPE:
        feat.setKey(PROTEIN);
        feat.addQualifier(ID_QUAL, gffObject.getAttributeValue(IprGffObject.ID_ATTR));
        break;
        default:
          feat.setKey(UNK);
      };
    }
    else {
      //handle domain predictions
      feat.setFrom(Integer.valueOf(gffObject.getStart()));
      feat.setTo(Integer.valueOf(gffObject.getEnd()));
      feat.setKey(DOMAIN);
      feat.setStrand(Feature.PLUS_STRAND);
      String str=gffObject.getAttributeValue(IprGffObject.NAME_ATTR);
      if(str != null) {
        feat.addQualifier(NAME_QUAL, str);
      }
      str = gffObject.getAttributeValue(IprGffObject.SIGNATURE_ATTR);
      if (str != null) {
        feat.addQualifier(SIGNATURE_QUAL, str);
      }
      //Collect IPR ids and add separate dbxref qualifier for each
      str = gffObject.getAttributeValue(IprGffObject.DBXREF_ATTR);
      if (str != null) {
        List<String> iprs = 
            Arrays.asList(str.split(","))
            .stream()
            .filter(s -> s.startsWith("InterPro"))
            .map(s -> s.split(":")[1])
            .collect(Collectors.toList());
        iprs.stream().forEach(ipr -> feat.addQualifier(DBXREF_QUAL, ipr));  
      }
      //do the same for GO
      str = gffObject.getAttributeValue(IprGffObject.ONTOLOGY_ATTR);
      if (str != null) {
        List<String> gos = 
            Arrays.asList(str.split(","))
            .stream()
            .filter(s -> s.startsWith("GO"))
            .collect(Collectors.toList());
        gos.stream().forEach(go -> feat.addQualifier(DBXREF_QUAL, go)); 
      }
    }
    return feat;
  }

}
