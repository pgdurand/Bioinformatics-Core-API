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
package bzh.plealog.bioinfo.data.taxonomy.core;

/**
 * Core API simple Taxon object. 
 * 
 * @author Patrick G. DUrand
 * */
public class TaxonomyTerm {
  private String id;
  private String sciname;
  private TaxonomyRank rank;
  
  public TaxonomyTerm() {
    
  }

  public TaxonomyTerm(String id, String sciname) {
    super();
    this.id = id;
    this.sciname = sciname;
  }

  public TaxonomyTerm(String id, String sciname, TaxonomyRank rank) {
    super();
    this.id = id;
    this.sciname = sciname;
    this.rank = rank;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSciname() {
    return sciname;
  }

  public void setSciname(String sciname) {
    this.sciname = sciname;
  }

  public TaxonomyRank getRank() {
    return rank;
  }

  public void setRank(TaxonomyRank rank) {
    this.rank = rank;
  }
}
