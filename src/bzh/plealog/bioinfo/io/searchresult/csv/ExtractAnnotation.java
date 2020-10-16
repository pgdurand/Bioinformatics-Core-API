/* Copyright (C) 2006-2020 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.searchresult.csv;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.feature.Qualifier;
import bzh.plealog.bioinfo.api.data.searchresult.SRCTerm;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;

public class ExtractAnnotation {

  private static void addClassifTerm(SRClassification classification, String id, String desc, 
      AnnotationDataModelConstants.ANNOTATION_CATEGORY cat, ExtractAnnotationVisitor visitor) {
    
    ExtractAnnotationVisitor.VISIT_TYPE vType = ExtractAnnotationVisitor.VISIT_TYPE.NEXT;
    SRCTerm                             term = classification.getTerm(id);
    
    if (term == null) {
      term = CoreSystemConfigurator.getSRFactory().creationBTerm();
      term.setDescription(desc);
      term.setType(cat.getType());
      if(cat.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX) ||
          cat.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)){
        id = cat.getType()+":"+id;
      }
      classification.addTerm(id, term);
      vType = ExtractAnnotationVisitor.VISIT_TYPE.FIRST;
    }
    if (visitor!=null) {
      visitor.termVisited(term, vType);
    }
  }
  

  /**
   * Collect classification data from a Feature.
   * 
   * @param classif classification object filled in by this method
   * @param feature from where to collect classification data
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * */
  public static void prepareClassificationdata(SRClassification classif, Feature feature, ExtractAnnotationVisitor visitor) {
    Enumeration<Qualifier> enumQualifiers;
    Qualifier qualifier;
    String qName, qValue, id, desc, term;
    int idx;

    enumQualifiers = feature.enumQualifiers();
    while (enumQualifiers.hasMoreElements()) {
      qualifier = (Qualifier) enumQualifiers.nextElement();
      qName = qualifier.getName();
      if (!AnnotationDataModelConstants.CLASSIF_PROVIDER.contains(qName)) {
        continue;
      }
      qValue = qualifier.getValue();
      //NCBI Taxonomy
      if (qValue.startsWith(AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON)) {
        StringTokenizer tokenizer = new StringTokenizer(qValue, 
            AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI+
            AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT);
        if (tokenizer.countTokens()==2) {
          tokenizer.nextToken();//skip FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON
          id = tokenizer.nextToken().trim();
          addClassifTerm(
              classif, 
              id, 
              null, 
              AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX,
              visitor);
          continue;
        }
        
      }
      if (qName.equals(AnnotationDataModelConstants.FEATURE_QUALIFIER_ENZYME)) {
        addClassifTerm(
            classif, 
            qValue, 
            null, 
            AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC,
            visitor);
        continue;
      }
      // Standard Swissprot db_xref format:
      // annotation_type + SEPARATOR + BLANK + key + SEPARATOR + BLANK + label
      // e.g. InterPro; IPR000485; HTH_AsnC_lrp.
      idx = qValue.indexOf(AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT);
      if (idx<0) {
        continue;
      }
      term = qValue.substring(0, idx).trim();
      //do we handle that classification?
      if (AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER.containsKey(term)){
        StringTokenizer tokenizer = new StringTokenizer(qValue, 
            AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT);
        tokenizer.nextToken();//skip FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON
        id = tokenizer.nextToken().trim();
        if (tokenizer.hasMoreTokens()) {
          desc = tokenizer.nextToken().trim();
          if (desc.equals("-.")) {//no description
            desc=AnnotationDataModelConstants.FEATURE_EMPTY_DESC;
          }
        }
        else {
          desc=AnnotationDataModelConstants.FEATURE_EMPTY_DESC;
        }
        addClassifTerm(
            classif, 
            id, 
            desc, 
            AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER.get(term),
            visitor);
      }
    }
  }
  
  public static void prepareClassificationdata(SRClassification classif, Feature feature) {
    prepareClassificationdata(classif, feature, null);
  }
  
  /**
   * Collect classification data from a FeatureTable.
   * 
   * @param classif classification object filled in by this method
   * @param featureTable from where to collect classification data
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * */
  public static void prepareClassificationdata(SRClassification classif, 
      FeatureTable featureTable, ExtractAnnotationVisitor visitor) {
    Enumeration<Feature> enumFeatures;
    if (featureTable==null)
      return;
    enumFeatures = featureTable.enumFeatures();
    while (enumFeatures.hasMoreElements()) {
      prepareClassificationdata(classif, (Feature) enumFeatures.nextElement());
      
    }
  }

  public static void prepareClassificationdata(SRClassification classif, FeatureTable featureTable) {
    prepareClassificationdata(classif, featureTable, null);
  }
  
  /**
   * Collect classification data from a FeatureTable.
   * 
   * @param featureTable from where to collect classification data
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(FeatureTable featureTable) {
    return getClassificationdata(featureTable, null);
  }
  public static SRClassification getClassificationdata(FeatureTable featureTable, 
      ExtractAnnotationVisitor visitor) {
    SRClassification classif = CoreSystemConfigurator.getSRFactory().creationBClassification();
    prepareClassificationdata(classif, featureTable, visitor);
    return classif;
  }

  /**
   * Collect classification data from a SRHit.
   * 
   * @param classif classification object filled in by this method
   * @param hit from where to collect classification data
   * @param firstHspOnly retrieve data from first HSP or all HSPs
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * */
  public static void prepareClassificationdata(SRClassification classif, SRHit hit, boolean firstHspOnly) {
    prepareClassificationdata(classif, hit, firstHspOnly, null);
  }
  
  public static void prepareClassificationdata(SRClassification classif, SRHit hit, boolean firstHspOnly, 
      ExtractAnnotationVisitor visitor) {
    int k, size3;
    SRHsp hsp;
    // Loop over each Hit and get Bio Classification data
    size3 = hit.countHsp();
    for (k = 0; k < size3; k++) {// loop on hsp
      hsp = hit.getHsp(k);
      prepareClassificationdata(classif, hsp.getFeatures(), visitor);
      if (firstHspOnly) {
        break;
      }
    }
  }

  /**
   * Collect classification data from a SRIteration.
   * 
   * @param classif classification object filled in by this method
   * @param sri from where to collect classification data
   * @param bestHitOnly retrieve data from best hit only or all hit
   * @param firstHspOnly retrieve data from first HSP or all HSPs
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * */
  public static void prepareClassificationdata(SRClassification classif, SRIteration sri, 
      boolean bestHitOnly, boolean firstHspOnly) {
    prepareClassificationdata(classif, sri, bestHitOnly, firstHspOnly, null);
  }
  
  public static void prepareClassificationdata(SRClassification classif, SRIteration sri, 
      boolean bestHitOnly, boolean firstHspOnly, ExtractAnnotationVisitor visitor) {
    int j, size2;
    // Loop over each Hit and get Bio Classification data
    size2 = sri.countHit();
    for (j = 0; j < size2; j++) {// loop on hits
      prepareClassificationdata(classif, sri.getHit(j), firstHspOnly, visitor);
      if (bestHitOnly) {
        break;
      }
    }
  }

  /**
   * Collect classification data from a SROutput.
   * 
   * @param bo from where to collect classification data
   * @param bestHitOnly retrieve data from best hit only or all hit
   * @param firstHspOnly retrieve data from first HSP or all HSPs
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(SROutput bo, boolean bestHitOnly, boolean firstHspOnly) {
    return getClassificationdata(bo, bestHitOnly, firstHspOnly, null);
  }
  public static SRClassification getClassificationdata(SROutput bo, boolean bestHitOnly, 
      boolean firstHspOnly, ExtractAnnotationVisitor visitor) {
    SRClassification classif = CoreSystemConfigurator.getSRFactory().creationBClassification();
    int i, size;
    // Loop over each Hit and get Bio Classification data
    size = bo.countIteration();
    for (i = 0; i < size; i++) {// loop on iterations
      prepareClassificationdata(classif, bo.getIteration(i), bestHitOnly, firstHspOnly, visitor);
    }
    return classif;
  }

  /**
   * Collect classification data from a SROutput. All hits and all HSPs are scanned from data.
   * 
   * @param bo from where to collect classification data
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(SROutput bo) {
    return getClassificationdata(bo, null);
  }
  
  public static SRClassification getClassificationdata(SROutput bo, ExtractAnnotationVisitor visitor) {
    return getClassificationdata(bo, false, false, visitor);
  }

  /**
   * Collect classification data from a SRIteration.
   * 
   * @param sri from where to collect classification data
   * @param bestHitOnly retrieve data from best hit only or all hit
   * @param firstHspOnly retrieve data from first HSP or all HSPs
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(SRIteration sri, boolean bestHitOnly, boolean firstHspOnly) {
    return getClassificationdata(sri, bestHitOnly, firstHspOnly, null);
  }
  public static SRClassification getClassificationdata(SRIteration sri, boolean bestHitOnly, 
      boolean firstHspOnly, ExtractAnnotationVisitor visitor) {
    SRClassification classif = CoreSystemConfigurator.getSRFactory().creationBClassification();
    prepareClassificationdata(classif, sri, bestHitOnly, firstHspOnly, visitor);
    return classif;
  }
  
  /**
   * Collect classification data from a SRIteration. All hits and all HSPs are scanned from data.
   * 
   * @param sri from where to collect classification data
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(SRIteration sri) {
    return getClassificationdata(sri, null);
  }
  public static SRClassification getClassificationdata(SRIteration sri, ExtractAnnotationVisitor visitor) {
    return getClassificationdata(sri, false, false, visitor);
  }
  
  /**
   * Collect classification data from a SRHit.
   * 
   * @param hit from where to collect classification data
   * @param firstHspOnly retrieve data from first HSP or all HSPs
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(SRHit hit, boolean firstHspOnly) {
    return getClassificationdata(hit, firstHspOnly, null);
  }
  public static SRClassification getClassificationdata(SRHit hit, boolean firstHspOnly, ExtractAnnotationVisitor visitor) {
    SRClassification classif = CoreSystemConfigurator.getSRFactory().creationBClassification();
    prepareClassificationdata(classif, hit, firstHspOnly, visitor);
    return classif;
  }

  /**
   * Collect classification data from a SRHit. Collect data from all HSPs.
   * 
   * @param hit from where to collect classification data
   * 
   * @see AnnotationDataModelConstants.CLASSIF_TERM_PROVIDER to review collected classification data
   * 
   * @return collected classification data
   * */
  public static SRClassification getClassificationdata(SRHit hit) {
    return getClassificationdata(hit, null);
  }
  public static SRClassification getClassificationdata(SRHit hit, ExtractAnnotationVisitor visitor) {
    return getClassificationdata(hit, false, visitor);
  }
  

  /**
   * Update classification of a SROutput.
   * 
   * @param refClassif reference classification
   * @param sro SROutput on which SRClassification is going to be updated using refClassif
   * as reference of SRTerms.
   */
  public static void updateClassificationdata(SRClassification refClassif, SROutput sro) {
    if (refClassif==null || refClassif.size()==0) {
      return;
    }
    SRClassification sroClassif = getClassificationdata(sro);
    if (sroClassif==null || refClassif.size()==0) {
      return;
    }
    SRClassification newRefClassif = CoreSystemConfigurator.getSRFactory().creationBClassification();
    Enumeration<String> ids = sroClassif.getTermIDs();
    while(ids.hasMoreElements()) {
      String id = ids.nextElement();
      SRCTerm term = refClassif.getTerm(id);
      if (term!=null) {
        newRefClassif.addTerm(id, term);
      }
    }
    if (refClassif.size()!=0) {
      sro.setClassification(newRefClassif);
    }
  }
  
  /**
   * Add a classification to another one.
   * 
   * @param classif1 a classification. Cannot be null.
   * @param classif2 another classification. Can be null. Content of classif2 is added to classif1.
   */
  public static void addClassificationdata(SRClassification classif1, SRClassification classif2) {
    if (classif2==null || classif2.size()==0) {
      return;
    }
    Enumeration<String> ids = classif2.getTermIDs();
    while(ids.hasMoreElements()) {
      String id = ids.nextElement();
      if (classif1.getTerm(id)==null) {
        classif1.addTerm(id, classif2.getTerm(id));
      }
    }
  }
  /**
   * Merge two classifications.
   * 
   * @param classif1 a classification. Can be null.
   * @param classif2 another classification. Can be null.
   * 
   * @return merged classifications. A new classification object is returned, classif1
   * nor classif2 is modified.
   */
  public static SRClassification mergeClassificationdata(SRClassification classif1, SRClassification classif2) {
    SRClassification newRefClassif = CoreSystemConfigurator.getSRFactory().creationBClassification();
    
    if (classif1!=null && classif1.size()!=0) {
      Enumeration<String> ids = classif1.getTermIDs();
      while(ids.hasMoreElements()) {
        String id = ids.nextElement();
        newRefClassif.addTerm(id, classif1.getTerm(id));
      }
    }
    addClassificationdata(newRefClassif, classif2);
    
    return newRefClassif;
  }
  /**
   * Prepare the classification data from a feature Table. Returned data is organized by 
   * classification category.
   * 
   * @param refClassif reference classification
   * @param featureTable a feature table
   * 
   * @return classification data or null if no such data gathered from feature table
   * */
  public static Map<AnnotationDataModelConstants.ANNOTATION_CATEGORY, SRClassification> 
    prepareClassification(SRClassification refClassif, FeatureTable featureTable){
    
    SRClassification ftClassif = getClassificationdata(featureTable, null);
    
    if (ftClassif.size()==0)
      return null;
    
    Hashtable<AnnotationDataModelConstants.ANNOTATION_CATEGORY, SRClassification> 
                         data = new Hashtable<>();
    Enumeration<String>  ids = ftClassif.getTermIDs();
    String               id;
    SRCTerm              term;
    SRClassification     catClassif;
    AnnotationDataModelConstants.ANNOTATION_CATEGORY cat;
    
    while(ids.hasMoreElements()) {
      id = ids.nextElement();
      //refClassification contains full Term data (desc + path)
      if (refClassif!=null)
        term = refClassif.getTerm(id);
      else
        term = null;
      if (term==null)//backward compatibility: Pfam IDs were not handled in previous releases
        term = ftClassif.getTerm(id);
      cat = AnnotationDataModelConstants.CLASSIF_TERM_TYPE.get(term.getType());
      catClassif = data.get(cat);
      if (catClassif==null) {
        catClassif = CoreSystemConfigurator.getSRFactory().creationBClassification();
        data.put(cat, catClassif);
      }
      catClassif.addTerm(id, term);
    }
    return data;
  }
  
  /**
   * Format classification data as a string.
   * 
   * @param ftClassif classification data
   * @param restrictToCategoty filter classification data to a particular one. 
   * If null then all classification types are reported.
   * 
   * @return a string
   * */
  public static String getFormattedFeatures(
      Map<AnnotationDataModelConstants.ANNOTATION_CATEGORY, SRClassification> ftClassif,
      AnnotationDataModelConstants.ANNOTATION_CATEGORY restrictToCategoty) {
    
    Iterator<AnnotationDataModelConstants.ANNOTATION_CATEGORY> iteratorCategories;
    AnnotationDataModelConstants.ANNOTATION_CATEGORY category;
    SRClassification classif;
    Enumeration<String> classifIDs;
    String classifId;
    StringBuffer featureCell = new StringBuffer();
    
    iteratorCategories = ftClassif.keySet().iterator();
    while (iteratorCategories.hasNext()) {
      category = iteratorCategories.next();
      if (restrictToCategoty!=null && category.equals(restrictToCategoty)==false) {
        continue;
      }
      classif = ftClassif.get(category);
      classifIDs = classif.getTermIDs();
      while (classifIDs.hasMoreElements()) {
        classifId = classifIDs.nextElement();
        featureCell.append(classifId);
        featureCell.append(AnnotationDataModelConstants.CATEGORY_FEATURES_TERMINATOR);
      }
    }
    return featureCell.toString();
  }

}