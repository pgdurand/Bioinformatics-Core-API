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
package bzh.plealog.bioinfo.api.data.searchresult;

import java.util.Enumeration;
import java.util.Hashtable;

import bzh.plealog.bioinfo.api.data.searchjob.SJTermSummary;

/**
 * Class modeling a SRClassification where all terms are counted.
 * 
 * @author Patrick G. Durand
 */
public class SRClassificationCount {
  Hashtable<String, Hashtable<String, SRClassificationCountTerm>> _countClassif;
  
  public SRClassificationCount() {
    _countClassif = new Hashtable<>();
  }
  
  /**
   * Get a particular classification.
   * 
   * @param vType classification type. relates to view type from SJTermSummary.
   * 
   * @return a classification where IDs are classification ID and values are corresponding SRCTerms.
   * */
  public Hashtable<String, SRClassificationCountTerm> getBCCountClassification(String vType){
    Hashtable<String, SRClassificationCountTerm> classif;
    
    classif = _countClassif.get(vType);
    if (classif==null) {
      classif = new Hashtable<>();
      _countClassif.put(vType, classif);
    }
    return classif;
  }
  /**
   * Add a classification term and ensure counting of terms.
   * 
   * @param vType classification type. relates to view type from SJTermSummary.
   * @param id term id
   * @param term classification term
   * */
  public void addClassification(SJTermSummary term) {
    Hashtable<String, SRClassificationCountTerm> classif;
    SRClassificationCountTerm                    cTerm;
    String vType, id;
    
    id = term.getID();
    vType = term.getViewType();
    classif = getBCCountClassification(vType);
    cTerm = classif.get(id);
    if (cTerm==null) {
      cTerm = new SRClassificationCountTerm(term, 1, 0.d);
      classif.put(id, cTerm);
    }
    else {
      cTerm.setCount(cTerm.getCount()+1);
    }
  }
  /**
   * Call this method as soon as all SJTermSummarys have been added to this CountClassification.
   */
  public void finalizeCounting() {
    Enumeration<String> keys = _countClassif.keys();
    while(keys.hasMoreElements()) {
      Hashtable<String, SRClassificationCountTerm> classif = _countClassif.get(keys.nextElement());
      int total = classif.values().stream().mapToInt(SRClassificationCountTerm::getCount).sum();
      classif.values().stream().forEach(t -> t.setPercent((double)t.getCount()*100.d/(double)total));
    }
  }
}