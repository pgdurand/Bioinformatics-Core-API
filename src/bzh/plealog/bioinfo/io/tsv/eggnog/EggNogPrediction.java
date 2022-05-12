/* Copyright (C) 2022 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.tsv.eggnog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.feature.Feature;

/**
 * Utility class to model a single EggNog Mapper domain prediction. It is used to convert 
 * an EggNogObject to a Feature instance suitable to be used by all other
 * Core APIs in this library.

 * @see test.TestFeatureSystem for sample use case
 * 
 * @author Patrick G. Durand
 */
public class EggNogPrediction {
  private EggNogObject enObject;
  private String key;
  
  //These are the keys to be used to create FeatureTable
  public static final String ID_QUAL = "id";
  public static final String NAME_QUAL = "name";
  public static final String SIGNATURE_QUAL = "description";
  public static final String DBXREF_QUAL = AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF;
  public static final String PRED_SOURCE = "prediction";
  public static final String STATUS_QUAL = "status";
  public static final String STRAND_QUAL = "strand";

  
  //This is the Feature type used to create Feature objects containing EggNog domain predictions
  public static final String DOMAIN = "domain";

  /**
   * Constructor.
   * 
   * @param obj IPRscan domain prediction object
   */
  public EggNogPrediction(EggNogObject obj) {
    setEggNogObject(obj);
  }

  /**
   * Sets a new IprGffObject instance.
   * 
   * @param obj IPRscan domain prediction object
   */
  public void setEggNogObject(EggNogObject obj) {
    enObject = obj;
    
    key = obj.getQuery_name();
  }
 
  /**
   * Returns IprGffObject instance.
   * 
   * @return IprGffObject wrapped in this class
   * 
   */
  public EggNogObject getEggNogObject(){
    return enObject;
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
        && o instanceof EggNogPrediction && 
        ((EggNogPrediction)o).getKey().equals(getKey());
  }
  
  @Override
  public int hashCode() {
    return getKey().hashCode();
  }

  /**
   * Returns the identifier of this object. It is mostly used to get
   * distinct (unique) domain predictions.
   * 
   * @return object key
   * */
  public String getKey() {
    return key;
  }
  
  /**
   * Convert this object to a Feature.
   * 
   * @return a Feature instance. It is worth noting that value for Feature key maybe equal to
   * UNK constant if IprGffObject type is not supported by Feature data model. See
   * IprGffObject.XXX_TYPE constants.
   */
  public Feature getFeature() {
    // Attempt to do some interpretation of IPRscan data
    
    Feature feat = CoreSystemConfigurator.getFeatureTableFactory().getFInstance();

    //handle domain predictions
    
    //basic info: location, type, score
    //location: unfortunately we do not have that information with eggnog mapper data
    feat.setFrom(1);
    feat.setTo(1);
    feat.setKey(DOMAIN);
    feat.setStrand(Feature.PLUS_STRAND);
    feat.addQualifier(PRED_SOURCE, "EggNog");
    String str, field;
    for (int idx=0;idx<EggNogObject.fieldNames.length; idx++) {
      field = EggNogObject.fieldNames[idx]; 
      str = enObject.getFieldValue(idx);
      //GO, EC: got special qualifier formatting, see below
      if ( !str.isEmpty() && 
           !( field.equals(EggNogObject.GO_FIELD) || 
               field.equals(EggNogObject.EC_FIELD) )) {
        feat.addQualifier(field, str);
      }
    }
    
    //Enzyme encoding
    str = enObject.getEnzymeID();
    if (str != null) {
      feat.addQualifier(
          DBXREF_QUAL, 
          AnnotationDataModelConstants.formatDbxrefForQualifier(
              AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC, str, null));
    }
      
    //GeneOntology encoding
    str = enObject.getGeneOntologyIDs();
    if (str != null) {
      List<String> gos = 
          Arrays.asList(str.split(","))
          .stream()
          .filter(s -> s.startsWith(AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO.getEncoding()))
          .collect(Collectors.toList());
      gos.stream().forEach(go -> feat.addQualifier(
          DBXREF_QUAL, 
          AnnotationDataModelConstants.formatDbxrefForQualifier(
              AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO, go, null)));
    }

    return feat;
  }

}
