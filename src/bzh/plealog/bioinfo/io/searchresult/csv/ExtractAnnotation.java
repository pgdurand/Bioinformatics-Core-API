/* Copyright (C) 2006-2018 Patrick G. Durand
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;

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

  private static final Logger LOG = Logger.getLogger(ExtractAnnotation.class.getSimpleName());

  private static HashSet<AnnotationDataModelConstants.ANNOTATION_CATEGORY> DEF_CAT = new HashSet<AnnotationDataModelConstants.ANNOTATION_CATEGORY>();

  static {
    DEF_CAT.add(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC);
    DEF_CAT.add(AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO);
    DEF_CAT.add(AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR);
    DEF_CAT.add(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX);
  }

  /**
   * Utility method to prepare an instance of SRClassification filled from an annotationDictionary object.
   * SRClassification contains SRCTerms where only Type if appropriately set.
   * */
  public static SRClassification buildClassificationDataSet(
      TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>> annotationDictionary) {
    SRClassification classification = CoreSystemConfigurator.getSRFactory().creationBClassification();
    
    //loop over Classification categories
    String catStr;
    SRCTerm term;
    AnnotationDataModelConstants.ANNOTATION_CATEGORY cat;
    Iterator<AnnotationDataModelConstants.ANNOTATION_CATEGORY> cats = annotationDictionary.keySet().iterator();
    StringBuilder sb = new StringBuilder(32);
    while(cats.hasNext()) {
      cat = cats.next();
      catStr = cat.name();
      //loop over IDs of a particular classification
      Iterator<String> ids = annotationDictionary.get(cat).keySet().iterator();
      while(ids.hasNext()) {
        sb.setLength( 0 );
        if(cat.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX)){
          sb.append(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX.name());
          sb.append(AnnotationDataModelConstants.CATEGORY_CODE_SEPARATOR);
        }
        else if(cat.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)){
          sb.append(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC.name());
          sb.append(AnnotationDataModelConstants.CATEGORY_CODE_SEPARATOR);
        }
        sb.append(ids.next());
        term = classification.addTerm(sb.toString());
        term.setType(catStr);
      }
    }
    return classification;
  }

  /**
   * Construct the tree of the annotation of a SROutput for default categories. Default categories
   * are EC, TAX, GO and IPR.
   * 
   * @param bo
   *          the SROutput
   * @param file_index
   *          the number of the SROutput in the map
   * @param annotatedHitsHashMap
   *          the TreeMap which will contain the annotations organized by SROutput-SRHit-SRHsp triplets.
   * @param annotationDictionary
   *          the TreeMap containing all annotation IDs organized by category.
 * */
  public static void buildAnnotatedHitDataSet(SROutput bo, int file_index,
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>> annotationDictionary) {
    ExtractAnnotation.buildAnnotatedHitDataSet(bo, file_index, annotatedHitsHashMap, annotationDictionary,
        DEF_CAT);
  }

  
  /**
   * Construct the tree of the annotation of a SROutput for a specific category.
   * 
   * @param bo
   *          the SROutput
   * @param file_index
   *          the number of the SROutput in the map
   * @param annotatedHitsHashMap
   *          the TreeMap which will contain the annotations
   * @param annotationDictionary
   * @param categoryToRetrieve
   *          category to retrieve. Cannot be null.
   */
  public static void buildAnnotatedHitDataSet(SROutput bo, int file_index,
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>> annotationDictionary,
      Set<AnnotationDataModelConstants.ANNOTATION_CATEGORY> categoryToRetrieve) {

    SRIteration hitList = null;
    SRHit hit = null;
    int nb_query = 0;
    int nb_hits = 0;
    int query_index = 0;
    int hit_index = 0;
    int hit_num_index = 0;
    boolean getFirstHspOnly = true;
    HashMap<String, AnnotationDataModel> featuresHashMap = null;
    TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>> categoryHashMap = null;
    boolean useInternalAnnotationKey = false;

    // filtering case
    // in some cases bo will be null
    // if all data from a given dataset are filtered
    if (bo == null) {
      LOG.debug("buildAnnotatedHitDataSet: empty dataset for file index [" + file_index + "] !");
      return;
    }

    if (categoryToRetrieve == null) {
      categoryToRetrieve = DEF_CAT;
    }
    // loop on SRIterations
    nb_query = bo.countIteration();
    for (query_index = 0; query_index < nb_query; query_index++) {
      hitList = bo.getIteration(query_index);
      // loop on SRHits
      nb_hits = hitList.countHit();
      for (hit_index = 0; hit_index < nb_hits; hit_index++) {
        hit = hitList.getHit(hit_index);
        categoryHashMap = new TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>();
        hit_num_index = hit.getHitNum() - 1;

        if (categoryToRetrieve.contains(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX)) {
          // extract TAXON - xref
          featuresHashMap = new HashMap<String, AnnotationDataModel>();
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
          categoryHashMap.put(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX, featuresHashMap);
          fillAnnotationDictionary(annotationDictionary, AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX, featuresHashMap);
        }

        if (categoryToRetrieve.contains(AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO)) {
          // extract GO - xref
          featuresHashMap = new HashMap<String, AnnotationDataModel>();
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
          categoryHashMap.put(AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO, featuresHashMap);
          fillAnnotationDictionary(annotationDictionary, AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO, featuresHashMap);
        }
        if (categoryToRetrieve.contains(AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR)) {
          // extract INTERPRO - xref
          featuresHashMap = new HashMap<String, AnnotationDataModel>();
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_INTERPRO, getFirstHspOnly,
              useInternalAnnotationKey, featuresHashMap);
          categoryHashMap.put(AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR, featuresHashMap);
          fillAnnotationDictionary(annotationDictionary, AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR, featuresHashMap);
        }
        if (categoryToRetrieve.contains(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)) {
          // extract ENZYME - xref
          featuresHashMap = new HashMap<String, AnnotationDataModel>();
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_ENZYME,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
          categoryHashMap.put(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC, featuresHashMap);
          fillAnnotationDictionary(annotationDictionary, AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC, featuresHashMap);
          // extract ENZYME
          featuresHashMap = new HashMap<String, AnnotationDataModel>();
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_ENZYME, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_NIL,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
          if (!categoryHashMap.containsKey(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)) {
            categoryHashMap.put(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC, featuresHashMap);
          } else {
            // add ENZYME - EC number annotation to already loaded ENZYME - xref
            categoryHashMap.get(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC).putAll(featuresHashMap);
          }
          fillAnnotationDictionary(annotationDictionary, AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC, featuresHashMap);
        }

        // fillAnnotationDictionary (annotationDictionary, ANNOTATION_CATEGORY.ENZYME,
        // featuresHashMap);

        // add annotation hit entry
        annotatedHitsHashMap.put(KeyDataSetManager.buildHitKeyFromIndex(file_index, query_index, hit_num_index),
            categoryHashMap);
      }
    }
  }

  public static void fillAnnotationDictionary(
      TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>> annotationDictionary,
      AnnotationDataModelConstants.ANNOTATION_CATEGORY category, HashMap<String, AnnotationDataModel> featuresHashMap) {
    Iterator<String> iterator = null;
    AnnotationDataModel annotationDataModel = null;
    String annotationKey = "";
    TreeMap<String, AnnotationDataModel> annotationHashMap = null;

    if (annotationDictionary == null) {
      LOG.debug("annotationDictionary is null ! no action performed !");
      return;
    }

    if (annotationDictionary.containsKey(category)) {
      annotationHashMap = annotationDictionary.get(category);
    } else {
      annotationHashMap = new TreeMap<String, AnnotationDataModel>();
      annotationDictionary.put(category, annotationHashMap);
    }
    iterator = featuresHashMap.keySet().iterator();
    while (iterator.hasNext()) {
      annotationKey = (String) iterator.next();
      annotationDataModel = featuresHashMap.get(annotationKey);
      if (!annotationHashMap.containsKey(annotationKey)) {
        annotationHashMap.put(annotationKey, annotationDataModel);
      }
    }
  }

  public static void countClassificationCategory(SROutput koriblastDataSet, AnnotationDataModelConstants.ANNOTATION_CATEGORY categoryToRetrieve,
      HashMap<String, AnnotationDataModel> featuresHashMap) {
    SRIteration hitList = null;
    SRHit hit = null;
    int nb_query = 0;
    int nb_hits = 0;
    int query_index = 0;
    int file_index = 0;
    int hit_index = 0;
    int hit_num_index = 0;
    boolean getFirstHspOnly = true;
    boolean useInternalAnnotationKey = false;

    // filtering case
    // in some cases koriblastDataSet will be null
    // if all data from a given dataset are filtered
    if (koriblastDataSet == null || categoryToRetrieve == null) {
      return;
    }
    // loop on SRIterations
    nb_query = koriblastDataSet.countIteration();
    for (query_index = 0; query_index < nb_query; query_index++) {
      hitList = koriblastDataSet.getIteration(query_index);
      // loop on SRHits
      nb_hits = hitList.countHit();
      for (hit_index = 0; hit_index < nb_hits; hit_index++) {
        hit = hitList.getHit(hit_index);
        hit_num_index = hit.getHitNum() - 1;

        if (categoryToRetrieve.equals((AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX))) {
          // extract TAXON - xref
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
        }
        if (categoryToRetrieve.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO)) {
          // extract GO - xref
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
        }
        if (categoryToRetrieve.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR)) {
          // extract INTERPRO - xref
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_INTERPRO, getFirstHspOnly,
              useInternalAnnotationKey, featuresHashMap);
        }
        if (categoryToRetrieve.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)) {
          // extract ENZYME - xref
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_ENZYME,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
          // extract ENZYME
          extractFeatureFromHitHsp(hit, file_index, query_index, hit_num_index,
              AnnotationDataModelConstants.FEATURE_QUALIFIER_ENZYME, AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_NIL,
              getFirstHspOnly, useInternalAnnotationKey, featuresHashMap);
        }
      }
    }
  }

  /**
   * Extract the annotation from a hit
   * 
   * @param hit
   * @param file_index
   * @param query_index
   * @param hit_index
   * @param qualifierKeyWord
   * @param annotationKeyWord
   * @param getFirstHspOnly
   * @param useInternalAnnotationKey
   * @param hspAnnotationHashMap
   */
  private static void extractFeatureFromHitHsp(SRHit hit, int file_index, int query_index, int hit_index,
      String qualifierKeyWord, String annotationKeyWord, boolean getFirstHspOnly, boolean useInternalAnnotationKey,
      HashMap<String, AnnotationDataModel> hspAnnotationHashMap) {
    FeatureTable featureTable = null;
    Feature feature = null;
    Enumeration<Feature> enumFeatures = null;
    Enumeration<Qualifier> enumQualifiers = null;
    Qualifier qualifier = null;
    SRHsp hsp = null;
    StringTokenizer tokenizer = null;
    AnnotationDataModel hspAnnotationDataModel = null, tmpDataModel;
    String hspAnnotationKey = "";
    String key = "";
    String label = "";
    String separator = "";
    int hsp_index = 0;
    int annotation_index = 0;
    int nb_hsp = 0;

    // loop on SRHsps
    nb_hsp = hit.countHsp();
    for (hsp_index = 0; hsp_index < nb_hsp; hsp_index++) {
      annotation_index = 0;
      // get features
      hsp = hit.getHsp(hsp_index);
      featureTable = hsp.getFeatures();
      if (featureTable == null) {
        // LOG.warn ("No feature table for hsp index [" + hsp_index + "], hit index [" +
        // hit_index +"], iteration index [" +query_index+"] skipping ...");
        continue;
      }
      enumFeatures = featureTable.enumFeatures();
      while (enumFeatures.hasMoreElements()) {
        feature = (Feature) enumFeatures.nextElement();
        enumQualifiers = feature.enumQualifiers();
        while (enumQualifiers.hasMoreElements()) {
          qualifier = (Qualifier) enumQualifiers.nextElement();
          hspAnnotationDataModel = null;
          key = "";
          label = "";
          separator = "";

          // extract annotation method depends on qualifier keyword provided
          if (qualifierKeyWord.equals(AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF)
              && qualifier.getName().equals(qualifierKeyWord)
              && (qualifier.getValue()
                  .startsWith(annotationKeyWord + AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI)
                  || qualifier.getValue().startsWith(
                      annotationKeyWord + AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT))) {

            // use a string tokenizer to extract annotations with format
            // annotation_type + SEPARATOR + BLANK + key + SEPARATOR + BLANK + label
            // e.g. InterPro; IPR000485; HTH_AsnC_lrp.
            // special case
            // GO:0005792 annotation_type->GO separator->: key-> GO:0005792
            // instead of 0005792

            // identify annotation origin NCBI or SWISSPROT
            if (qualifier.getValue()
                .startsWith(annotationKeyWord + AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI)) {
              separator = AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI;
            } else if (qualifier.getValue()
                .startsWith(annotationKeyWord + AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT)) {
              separator = AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT;
            }

            tokenizer = new StringTokenizer(qualifier.getValue(), separator);
            // skip feature db name ("GO", "Interpro")
            tokenizer.nextToken();

            // get feature key ("IPR013083")
            if (tokenizer.hasMoreTokens()) {
              key = tokenizer.nextToken();
              key = key.trim();
              // GO special treatment for NCBI data
              if (annotationKeyWord.equals(AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO)
                  && separator.equals(AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI)) {
                key = AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO
                    + AnnotationDataModelConstants.FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_GO + key;
              }
            }

            // get feature label ("Znf_RING." for key "IPR001841;")
            if (tokenizer.hasMoreTokens()) {
              label = tokenizer.nextToken();
              label = label.trim();
            }

            // reformat feature
            hspAnnotationDataModel = new AnnotationDataModel(key, label);
          } else if ((qualifierKeyWord.equals(AnnotationDataModelConstants.FEATURE_QUALIFIER_ENZYME)
              || qualifierKeyWord.equals(AnnotationDataModelConstants.FEATURE_QUALIFIER_ORGANISM))
              && qualifier.getName().equals(qualifierKeyWord)) {
            hspAnnotationDataModel = new AnnotationDataModel(qualifier.getValue(), "");
            key = qualifier.getValue();
          } else {
            continue;
          }
          // extract only non-empty annotation
          // if (!hspAnnotation.equals("")) {
          if (hspAnnotationDataModel != null) {
            // build hsp annotation key
            if (useInternalAnnotationKey) {
              hspAnnotationKey = KeyDataSetManager.buildAnnotaKeyFromIndex(file_index, query_index, hit_index,
                  hsp_index, annotation_index);
            } else {
              hspAnnotationKey = key;
            }

            synchronized (DEF_CAT) {// critical section since nbHits is updated on shared AnnotationDataModel
                                    // objects
              // store index
              if (!hspAnnotationHashMap.containsKey(hspAnnotationKey)) {
                hspAnnotationDataModel.setNbHits(1);
                hspAnnotationHashMap.put(hspAnnotationKey, hspAnnotationDataModel);
              } else {
                tmpDataModel = hspAnnotationHashMap.get(hspAnnotationKey);
                tmpDataModel.setNbHits(tmpDataModel.getNbHits() + 1);
              }
            }
            annotation_index++;
          }

        }
      }
      if (getFirstHspOnly) {
        // iterate on first Hsp only
        break;
      }
    }
  }

  public static String getCode(AnnotationDataModelConstants.ANNOTATION_CATEGORY category) {
    String label = "N/A";
    switch (category) {
    case LCA:
      label = AnnotationDataModelConstants.FEATURE_CODE_TAXON;
      break;
    case TAX:
      label = AnnotationDataModelConstants.FEATURE_CODE_TAXON;
      break;
    case GO:
      label = AnnotationDataModelConstants.FEATURE_CODE_GO;
      break;
    case IPR:
      label = AnnotationDataModelConstants.FEATURE_CODE_INTERPRO;
      break;
    case EC:
      label = AnnotationDataModelConstants.FEATURE_CODE_ENZYME;
      break;
    /*
     * case ORGANISM: label = CONSTANTS_KEYWORDS.FEATURE_CODE_ORGANISM; break;
     */
    }
    return label;
  }

  public static AnnotationDataModelConstants.ANNOTATION_CATEGORY getCategory(String cat) {
    if (AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX.toString().equals(cat)) {
      return AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX;
    } else if (AnnotationDataModelConstants.ANNOTATION_CATEGORY.LCA.toString().equals(cat)) {
      return AnnotationDataModelConstants.ANNOTATION_CATEGORY.LCA;
    } else if (AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO.toString().equals(cat)) {
      return AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO;
    } else if (AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR.toString().equals(cat)) {
      return AnnotationDataModelConstants.ANNOTATION_CATEGORY.IPR;
    } else if (AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC.toString().equals(cat)) {
      return AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC;
    } else {
      return null;
    }
  }

  public static String formatFeatures(String key, String label) {
    String feature = "";
  
    feature = key;
    if (!label.equals("")) {
      // truncate
      if (label.length() > AnnotationDataModelConstants.FEATURE_LABEL_MAX_LEN) {
        label = label.substring(0, AnnotationDataModelConstants.FEATURE_LABEL_MAX_LEN) + AnnotationDataModelConstants.SPACE
            + AnnotationDataModelConstants.FEATURE_LABEL_CONTINUATION_TAG;
      }
      feature += AnnotationDataModelConstants.SPACE + AnnotationDataModelConstants.FEATURE_LABEL_SEPARATOR_OPEN + label
          + AnnotationDataModelConstants.FEATURE_LABEL_SEPARATOR_CLOSE;
    }
    return feature;
  }

  public static String getFormattedFeatures(
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      int output_file_index, int iteration_index, int hit_index) {
    return getFormattedFeatures(annotatedHitsHashMap, output_file_index, iteration_index, hit_index, null);
  }
  /**
   * Formats the annotation to write them in a csv cell.
   * 
   * @param annotatedHitsHashMap
   *          the tree which contains the information about annotation
   * @param output_file_index
   *          the number of the output analyzed
   * @param iteration_index
   *          the number of the iteration
   * @param hit_index
   *          the number of the hit
   * 
   * @return the annotation formatted
   */
  public static String getFormattedFeatures(
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      int output_file_index, int iteration_index, int hit_index, AnnotationDataModelConstants.ANNOTATION_CATEGORY restrictToCategoty) {
    HashMap<String, AnnotationDataModel> featureTreeMap = null;
    TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>> hitFeaturesTreeMap = null;
    Iterator<AnnotationDataModelConstants.ANNOTATION_CATEGORY> iteratorCategories = null;
    AnnotationDataModelConstants.ANNOTATION_CATEGORY category = null;
    Iterator<String> iteratorFeatures = null;
  
    StringBuffer featureCell =new StringBuffer();
    String hit_key = "-1";
    String featureKey = "";
    AnnotationDataModel annotationDataModel = null;
  
    if (annotatedHitsHashMap == null)
      return "";
  
    hit_key = KeyDataSetManager.buildHitKeyFromIndex(output_file_index, iteration_index, hit_index);
    if (annotatedHitsHashMap.containsKey(hit_key)) {
      hitFeaturesTreeMap = (TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>) annotatedHitsHashMap
          .get(hit_key);
      iteratorCategories = hitFeaturesTreeMap.keySet().iterator();
      while (iteratorCategories.hasNext()) {
        category = iteratorCategories.next();
        if (restrictToCategoty!=null && category.equals(restrictToCategoty)==false) {
          continue;
        }
        featureTreeMap = (HashMap<String, AnnotationDataModel>) hitFeaturesTreeMap.get(category);
        iteratorFeatures = featureTreeMap.keySet().iterator();
        while (iteratorFeatures.hasNext()) {
          featureKey = (String) iteratorFeatures.next();
          annotationDataModel = featureTreeMap.get(featureKey);
          if (category.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX)) {
            featureCell.append(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX.name());
            featureCell.append(AnnotationDataModelConstants.CATEGORY_CODE_SEPARATOR);
          }
          else if (category.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)) {
            featureCell.append(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC.name());
            featureCell.append(AnnotationDataModelConstants.CATEGORY_CODE_SEPARATOR);
          }
          featureCell.append(annotationDataModel.getAccession());
          featureCell.append(AnnotationDataModelConstants.CATEGORY_FEATURES_TERMINATOR);
        }
      }
    }
    return featureCell.toString();
  }
  /**
   * Utility method to prepare an instance of SRClassification filled from an annotatedHitsHashMap object.
   * SRClassification contains SRCTerms where only Type if appropriately set.
   * 
   * Before calling this method, SRClassification must be created to en empty instance.
   * */
  private static void buildClassificationDataSet(
      SRClassification refClassification,
      SRClassification classification,
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      int output_file_index, int iteration_index, int hit_index) {
    HashMap<String, AnnotationDataModel>  featureTreeMap = null;
    Iterator<String>                      iteratorFeatures = null;
    String                                hit_key, featureKey, id;
    AnnotationDataModel                   annotationDataModel = null;
    SRCTerm                               term;
    TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, 
    HashMap<String, AnnotationDataModel>>                      hitFeaturesTreeMap = null;
    Iterator<AnnotationDataModelConstants.ANNOTATION_CATEGORY> iteratorCategories = null;
    AnnotationDataModelConstants.ANNOTATION_CATEGORY           category = null;
    
    // get ID of the particular hit
    hit_key = KeyDataSetManager.buildHitKeyFromIndex(output_file_index, iteration_index, hit_index);
    // got it in data structure containing associated Classification (Features) dta?
    if (annotatedHitsHashMap.containsKey(hit_key)) {
      // get classification data for that hit
      hitFeaturesTreeMap = (TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>) 
          annotatedHitsHashMap.get(hit_key);
      //and loop over all data
      iteratorCategories = hitFeaturesTreeMap.keySet().iterator();
      StringBuilder sb = new StringBuilder(32);
      while (iteratorCategories.hasNext()) {
        category = iteratorCategories.next();
        /*if (restrictToCategoty!=null && category.equals(restrictToCategoty)==false) {
          continue;
        }*/
        featureTreeMap = (HashMap<String, AnnotationDataModel>) hitFeaturesTreeMap.get(category);
        iteratorFeatures = featureTreeMap.keySet().iterator();
        while (iteratorFeatures.hasNext()) {
          featureKey = (String) iteratorFeatures.next();
          annotationDataModel = featureTreeMap.get(featureKey);
          
          sb.setLength( 0 );
          if(category.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX)){
            sb.append(AnnotationDataModelConstants.ANNOTATION_CATEGORY.TAX.name());
            sb.append(AnnotationDataModelConstants.CATEGORY_CODE_SEPARATOR);
          }
          else if(category.equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC)){
            sb.append(AnnotationDataModelConstants.ANNOTATION_CATEGORY.EC.name());
            sb.append(AnnotationDataModelConstants.CATEGORY_CODE_SEPARATOR);
          }
          sb.append(annotationDataModel.getAccession());
          id = sb.toString();
          term = refClassification.getTerm(id);
          classification.addTerm(id, term);
        }
      }
    }
    
  }

  /**
   * Utility method to prepare an instance of SRClassification filled from an annotatedHitsHashMap object.
   * SRClassification contains SRCTerms where only Type if appropriately set.
   * */
  public static SRClassification buildClassificationDataSet(
      SRClassification refClassification,
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      int output_file_index, int iteration_index, int hit_index) {
    SRClassification classification = CoreSystemConfigurator.getSRFactory().creationBClassification();
    buildClassificationDataSet(refClassification, classification, annotatedHitsHashMap, output_file_index, iteration_index, hit_index);
    return classification;
  }
  /**
   * Utility method to prepare an instance of SRClassification filled from an annotatedHitsHashMap object.
   * SRClassification contains SRCTerms where only Type if appropriately set.
   * */
  public static SRClassification buildClassificationDataSet(
      SRClassification refClassification,
      TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap,
      int output_file_index, int iteration_index, int hit_index_from, int hit_index_to) {
    SRClassification classification = CoreSystemConfigurator.getSRFactory().creationBClassification();
    for(int i = hit_index_from ; i <= hit_index_to ; i++) {
      buildClassificationDataSet(refClassification, classification, annotatedHitsHashMap, output_file_index, iteration_index, i);
    }
    return classification;
  }

}
