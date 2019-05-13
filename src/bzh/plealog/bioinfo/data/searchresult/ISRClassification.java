/* Copyright (C) 2019 Patrick G. Durand
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
package bzh.plealog.bioinfo.data.searchresult;

import java.util.Hashtable;

import bzh.plealog.bioinfo.api.data.searchresult.SRCTerm;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;

/**
 * This is a default implementation of interface SRClassification.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRClassification
 */
public class ISRClassification implements SRClassification {
  private static final long serialVersionUID = 1743408357928344598L;
  private Hashtable<String, SRCTerm> terms;
  
  public ISRClassification() {
    terms = new Hashtable<String, SRCTerm>();
  }

  public SRCTerm getTerm(String key) {
    return terms.get(key);
  }

  public void setTerm(String key, SRCTerm term) {
    terms.put(key, term);
  }

  @Override
  public SRClassification clone() {
    ISRClassification stat = new ISRClassification();
    stat.copy(this);
    return stat;
  }

  public void copy(ISRClassification src) {
    this.terms = new Hashtable<String, SRCTerm>(src.terms);
  }

}
