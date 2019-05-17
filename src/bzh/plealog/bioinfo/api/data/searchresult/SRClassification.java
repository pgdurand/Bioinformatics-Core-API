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
package bzh.plealog.bioinfo.api.data.searchresult;

import java.io.Serializable;
import java.util.Enumeration;

/**
 * This is an object representation of classification data. It aims at storing
 * in a raw format data coming from a biological classification such as Enzyme,
 * GO, Taxonomy, etc. Raw format means, we only store key-value pairs where key 
 * is a classification entry and value is a Term.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRClassification
 */
public interface SRClassification extends Serializable {

  /**
   * Returns a Classification Term given an ID.
   * 
   * @param key an classification ID.
   */
  public SRCTerm getTerm(String key);

  /**
   * Add a Classification Term given an ID. This method does not check whether or not term
   * already exists in this SRClassification.
   * 
   * @param key a classification ID.
   * @param term corresponding Term
   */
  public void addTerm(String key, SRCTerm term);

  /**
   * Add a new term.
   * 
   * @param key term ID
   * 
   * @return a new SRCTerm instance if not existing yet, existing instance otherwise.
   */
  public SRCTerm addTerm(String key);
  

  /**
   * Forces the implementation of a clone method.
   */
  public SRClassification clone();

  /**
   * Enumerate over all Classification IDs.
   * 
   * @return enumeration of classification IDs.
   */
  public Enumeration<String> getTermIDs();
  
  /**
   * Content of this SRClassification.
   * 
   * @return number of classification IDs contained in this SRClassification
   */
  public int size();
}
