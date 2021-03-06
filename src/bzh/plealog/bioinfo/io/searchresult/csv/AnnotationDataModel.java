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

import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;

public class AnnotationDataModel {

  private String _accession = "";
  private String _label = "";
  private int _nbHits = 0;
  private AnnotationDataModelConstants.ANNOTATION_CATEGORY _annotationType = null;

  public AnnotationDataModel(String key, String label, AnnotationDataModelConstants.ANNOTATION_CATEGORY type) {
    _accession = key;
    _label = label;
    _annotationType = type;
  }

  public AnnotationDataModel(String key, AnnotationDataModelConstants.ANNOTATION_CATEGORY type) {
    _accession = key;
    _annotationType = type;
  }

  public AnnotationDataModel(String key, String label) {
    _accession = key;
    _label = label;
  }

  public void setNbHits(int nbHits) {
    _nbHits = nbHits;
  }

  public int getNbHits() {
    return _nbHits;
  }

  public String getAccession() {
    return _accession;
  }

  public void setAccession(String key) {
    _accession = key;
  }

  public String getLabel() {
    return _label;
  }

  public void setLabel(String label) {
    _label = label;
  }

  public void setAnnotationType(AnnotationDataModelConstants.ANNOTATION_CATEGORY type) {
    _annotationType = type;
  }

  public AnnotationDataModelConstants.ANNOTATION_CATEGORY getAnnotationType() {
    return _annotationType;
  }

  private String formatFeatures(String key, String label) {
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
  public String toString() {
    String node_label = "";
    node_label = formatFeatures(getAccession(), getLabel());
    node_label += (getNbHits() > 1 ? " (" + getNbHits() + ")" : "");
    return node_label;
  }
}
