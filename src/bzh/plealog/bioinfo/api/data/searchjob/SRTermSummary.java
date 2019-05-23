/* Copyright (C) 2003-2019 Patrick G. Durand
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
package bzh.plealog.bioinfo.api.data.searchjob;

import java.text.MessageFormat;

import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.searchresult.SRCTerm;

/**
 * Utility class to setup a data model aims at displaying SRClassification
 * data.
 * 
 * @author Patrick G. Durand
 */
public class SRTermSummary {
  private String _id;
  private SRCTerm _term;
  private String _viewType = "-";
  
  private static MessageFormat VIEW_TYPE_FORMAT = new MessageFormat("{0}:{1}")  ;
  
  public SRTermSummary(String id, SRCTerm term) {
    _id = id;
    _term = term;
    //handle sub-classification (C, P or F) for GO. 
    if (getType().equals(AnnotationDataModelConstants.ANNOTATION_CATEGORY.GO.name())) {
      //GO description is: "C:cytoplasm"
      //in this example, we have to get first letter
      _viewType = formatViewType(getType(),getDescription().substring(0, 1));
    }
    else {
      _viewType = getType();
    }
  }

  public String getID() {
    return _id;
  }
  public String getDescription() {
    return _term.getDescription();
  }
  public String getType() {
    return _term.getType();
  }
  public String getPath() {
    return _term.getPath();
  }
  /**
   * Return type to be used for UI purpose. For instance getType() returns GO
   * while getViewType() can return one of GOP, GOF, GOC. 
   */
  public String getViewType() {
    return _viewType;
  }
  
  public static String formatViewType(String type, String subtype) {
    return VIEW_TYPE_FORMAT.format(new Object[]{type, subtype});
  }
}
