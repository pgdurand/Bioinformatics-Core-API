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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import bzh.plealog.bioinfo.data.taxonomy.loader.ncbi.Taxon;

/**
 * Utility class to prepare taxonomy objects from NCBI Taxon serialized objects.
 * Those ones come from eFetch utility XML documents.
 * 
 * @author Patrick G. DUrand
 */
public abstract class TaxonomyLoader {

  private enum PATH_TYPE {NAME, RANK, ID}
  private static HashSet<String> CANONICAL_TAX_PATH;
  
  static {
    CANONICAL_TAX_PATH = new HashSet<>();
    CANONICAL_TAX_PATH.add("superkingdom");
    CANONICAL_TAX_PATH.add("kingdom");
    CANONICAL_TAX_PATH.add("phylum");
    CANONICAL_TAX_PATH.add("class");
    CANONICAL_TAX_PATH.add("order");
    CANONICAL_TAX_PATH.add("family");
    CANONICAL_TAX_PATH.add("genus");
    CANONICAL_TAX_PATH.add("species");
  }
  
  private static List<TaxonomyTerm> load(Taxon ncbiTax, boolean canonical){
    ArrayList<TaxonomyTerm> tRanks;
    TaxonomyRank tRank;
    
    tRanks = new ArrayList<>();
    
    for (Taxon tax:ncbiTax.getLineageEx().getTaxon()) {
      if (canonical && !CANONICAL_TAX_PATH.contains(tax.getRank()))
          continue;
      tRank = TaxonomyRank.getTaxonomyRank(tax.getRank());
      //skip unknown rank ('no rank' in NCBI taxonomy)
      if (tRank==null)
        continue;
      tRanks.add(new TaxonomyTerm(tax.getTaxId().getvalue(), 
          tax.getScientificName(), tRank));
    }
    tRank = TaxonomyRank.getTaxonomyRank(ncbiTax.getRank());
    if(tRank!=null) {
      tRanks.add(new TaxonomyTerm(ncbiTax.getTaxId().getvalue(), 
        ncbiTax.getScientificName(), tRank));
    }
    return tRanks;
  }
  
  /**
   * Get the full lineage from a Taxon object.
   * 
   * @param ncbiTax NCBI Taxon object
   * 
   * @return full lineage*/
  public static List<TaxonomyTerm> loadFull(Taxon ncbiTax){
    return load(ncbiTax, false);
  }
  
  /**
   * Get the canonical lineage from a Taxon object.
   * 
   * @param ncbiTax NCBI Taxon object
   * 
   * @return canonical lineage*/
  public static List<TaxonomyTerm> loadCanonical(Taxon ncbiTax){
    return load(ncbiTax, true);
  }

  private static String getPathGeneric(List<TaxonomyTerm> tTerms, PATH_TYPE type) {
    StringBuffer buf = new StringBuffer();
    
    for(TaxonomyTerm term:tTerms) {
      switch(type) {
      case ID:
        buf.append(term.getId());
        break;
      case RANK:
        buf.append(term.getRank().getLevelCode());
        break;
      case NAME:
      default:
        buf.append(term.getSciname());
        break;
      }
      buf.append(";");
    }
    //remove terminal semi-colon
    return buf.substring(0, buf.length()-1);
  }
  
  /**
   * Get path of scientific names as String.
   * 
   * @param tTerms path of taxon terms
   * 
   * @return string representation of scientific name path
   * */
  public static String getPathNames(List<TaxonomyTerm> tTerms) {
    return getPathGeneric(tTerms, PATH_TYPE.NAME);
  }
  
  /**
   * Get path of ranks as String. 
   * 
   * @param tTerms path of taxon terms
   * 
   * @return string representation of rank path
   * */
  public static String getPathRanks(List<TaxonomyTerm> tTerms) {
    return getPathGeneric(tTerms, PATH_TYPE.RANK);
  }

  /**
   * Get path of taxon ID as String.
   * 
   * @param tTerms path of taxon terms
   * 
   * @return string representation of ID path
   * */
  public static String getPathIds(List<TaxonomyTerm> tTerms) {
    return getPathGeneric(tTerms, PATH_TYPE.ID);
  }
}
