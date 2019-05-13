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
   * Set a Classification Term given an ID.
   * 
   * @param key an classification ID.
   * @param o corresponding Term
   */
  public void setTerm(String key, SRCTerm term);

  /**
   * Forces the implementation of a clone method.
   */
  public SRClassification clone();

}
