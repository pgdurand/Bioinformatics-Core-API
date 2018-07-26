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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This class manages keys used to index hits and annotations.
 * Keys are stored within String. There are two key formats,
 * one for hits and one for annotations. Indexes used here are all Integers. <br><br>
 * 1) Hit key: - FILE ID + SEPARATOR + QUERY_ID + SEPARATOR + HIT_ID <br>
 * 2) Annotation key: - FILE ID + SEPARATOR + QUERY_ID + SEPARATOR + HIT_ID + SEPARATOR +
 * HSP_ID + SEPARATOR + ANNOTATION_ID <br><br>
 * Remarks : <br>
 * 1) All keys start with index 0.<br>
 * 2) FILE_ID references file: 'data_seq<FILE_ID>.xml' (which is a query id). <br>
 * 3) QUERY_ID should be understood as an ITERATION_ID for it
 * is an iteration id. <br>
 * 4) ANNOTATION_ID corresponds to the order of appearance of the annotation within 
 * result file 'data_seq<FILE_ID>.xml'.<br>
 * 
 */
public class KeyDataSetManager {

  public static enum KEY_FORMATS {
    FILE_QUERY_HIT, FILE_QUERY_HIT_HSP_ANNOTATION
  };

  public static enum SR_INDEXES {
    FILE, QUERY, HIT, HSP, ANNOTATION
  };

  public static final String KEY_SEPARATOR = "_";
  public static final int DEFAULT_FILE_INDEX = 0;

  /**
   * Convert keys for each entry within the data set according to the input and
   * output key Formats. Keys are stored within a hash map.
   * 
   * @param inputDataSet
   *          - data set containing entries for which keys are to be computed.
   * @param inputDataSetKeyFormat
   *          - KEY_FORMATS - input data set key format, ex:
   *          FILE_QUERY_HIT_HSP_ANNOTATION.
   * @param outputKeyFormat
   *          - KEY_FORMATS - output data set key format, ex: FILE_QUERY_HIT.
   * @return HashMap - converted keys data set.
   */
  public static HashMap<String, String> buildKeys(HashMap<String, String> inputDataSet,
      KEY_FORMATS inputDataSetKeyFormat, KEY_FORMATS outputKeyFormat) {
    HashMap<String, String> outputKeyDataSet = new HashMap<>();
    Set<String> hitKeySet = null;
    Iterator<String> hitKeyIterator = null;
    String hitKey = "";
    String outputKey = "";
    int fileIndex = -1;
    int queryIndex = -1;
    int hitIndex = -1;

    if (inputDataSetKeyFormat == KEY_FORMATS.FILE_QUERY_HIT_HSP_ANNOTATION
        && outputKeyFormat == KEY_FORMATS.FILE_QUERY_HIT) {
      inputDataSet.keySet();
      hitKeySet = inputDataSet.keySet();
      hitKeyIterator = hitKeySet.iterator();
      while (hitKeyIterator.hasNext()) {
        hitKey = (String) hitKeyIterator.next();
        fileIndex = getIndexFromAnnotationKey(hitKey, SR_INDEXES.FILE);
        queryIndex = getIndexFromAnnotationKey(hitKey, SR_INDEXES.QUERY);
        hitIndex = getIndexFromAnnotationKey(hitKey, SR_INDEXES.HIT);
        outputKey = buildHitKeyFromIndex(fileIndex, queryIndex, hitIndex);
        outputKeyDataSet.put(outputKey, outputKey);
      }
    }
    return outputKeyDataSet;
  }

  /**
   * Computes annotation key from parameters given for default file index (0).
   * 
   * @param queryIndex
   *          - the query index.
   * @param hitIndex
   *          - the hit index.
   * @param hspIndex
   *          - the hsp index.
   * @param hspAnnotationKey
   *          - the annotation index.
   * @return String - the complete annotation key.
   */
  public static String buildAnnotaKeyFromIndex(int queryIndex, int hitIndex, int hspIndex, int hspAnnotationKey) {
    return buildAnnotaKeyFromIndex(DEFAULT_FILE_INDEX, queryIndex, hitIndex, hspIndex, hspAnnotationKey);
  }

  /**
   * Computes annotation key from parameters given.
   * 
   * @param fileIndex
   *          - the file index.
   * @param queryIndex
   *          - the query index.
   * @param hitIndex
   *          - the hit index.
   * @param hspIndex
   *          - the hsp index.
   * @param hspAnnotationKey
   *          - the annotation index.
   * @return String - the complete annotation key.
   */
  public static String buildAnnotaKeyFromIndex(int fileIndex, int queryIndex, int hitIndex, int hspIndex,
      int hspAnnotationKey) {
    return fileIndex + KEY_SEPARATOR + queryIndex + KEY_SEPARATOR + hitIndex + KEY_SEPARATOR + hspIndex + KEY_SEPARATOR
        + hspAnnotationKey;
  }

  /**
   * Computes hit key from parameters given for default file index (0).
   * 
   * @param queryIndex
   *          - the query index.
   * @param hitIndex
   *          - the hit index.
   * @return String - the complete annotation key.
   */
  public static String buildHitKeyFromIndex(int queryIndex, int hitIndex) {
    return buildHitKeyFromIndex(DEFAULT_FILE_INDEX, queryIndex, hitIndex);
  }

  /**
   * Computes hit key from parameters given.
   * 
   * @param fileIndex
   *          - the file index.
   * @param queryIndex
   *          - the query index.
   * @param hitIndex
   *          - the hit index.
   * @return String - the complete annotation key.
   */
  public static String buildHitKeyFromIndex(int FileIndex, int queryIndex, int hitIndex) {
    return FileIndex + KEY_SEPARATOR + queryIndex + KEY_SEPARATOR + hitIndex;
  }

  /**
   * Extract index value from the given 'hit' key for the given index name
   * parameter.
   * 
   * @param hitKey
   *          - the input hit key
   * @param indexName
   *          - the index name which value is to extract
   * @return - int - the index value extracted.
   */
  public static int getIndexFromHitKey(String hitKey, SR_INDEXES indexName) {
    String token = "";
    StringTokenizer st = new StringTokenizer(hitKey, KEY_SEPARATOR);
    if (st.hasMoreTokens()) {
      token = st.nextToken();
      if (SR_INDEXES.FILE == indexName) {
        return Integer.parseInt(token);
      }
      if (st.hasMoreTokens()) {
        token = st.nextToken();
        if (SR_INDEXES.QUERY == indexName) {
          return Integer.parseInt(token);
        }
        if (st.hasMoreTokens()) {
          token = st.nextToken();
          if (SR_INDEXES.HIT == indexName) {
            return Integer.parseInt(token);
          }
        }
      }
    }
    return -1;
  }

  /**
   * Extract index value from the given 'annotation' key for the given index name
   * parameter.
   * 
   * @param annotationKey
   *          - the annotation key
   * @param indexName
   *          - the index name which value is to extract
   * @return - int - the index value extracted.
   */
  public static int getIndexFromAnnotationKey(String annotationKey, SR_INDEXES indexName) {
    String token = "";
    StringTokenizer st = new StringTokenizer(annotationKey, KEY_SEPARATOR);
    if (st.hasMoreTokens()) {
      token = st.nextToken();
      if (SR_INDEXES.FILE == indexName) {
        return Integer.parseInt(token);
      }
      if (st.hasMoreTokens()) {
        token = st.nextToken();
        if (SR_INDEXES.QUERY == indexName) {
          return Integer.parseInt(token);
        }
        if (st.hasMoreTokens()) {
          token = st.nextToken();
          if (SR_INDEXES.HIT == indexName) {
            return Integer.parseInt(token);
          }
          if (st.hasMoreTokens()) {
            token = st.nextToken();
            if (SR_INDEXES.HSP == indexName) {
              return Integer.parseInt(token);
            }
            if (st.hasMoreTokens()) {
              token = st.nextToken();
              if (SR_INDEXES.ANNOTATION == indexName) {
                return Integer.parseInt(token);
              }
            }
          }
        }
      }
    }
    return -1;
  }

  /**
   * Extract 'hit' key from the given 'annotation' key.
   * 
   * @param annotationKey
   * @return 'hit' key
   */
  public static String extractHitKeyFromAnnotationKey(String annotationKey) {
    String tokenFile = "";
    String tokenQuery = "";
    String tokenHit = "";
    StringTokenizer st = new StringTokenizer(annotationKey, KEY_SEPARATOR);
    if (st.hasMoreTokens()) {
      tokenFile = st.nextToken();
      if (st.hasMoreTokens()) {
        tokenQuery = st.nextToken();
        if (st.hasMoreTokens()) {
          tokenHit = st.nextToken();
          return tokenFile + KEY_SEPARATOR + tokenQuery + KEY_SEPARATOR + tokenHit;
        }
      }
    }
    return "";
  }
}
