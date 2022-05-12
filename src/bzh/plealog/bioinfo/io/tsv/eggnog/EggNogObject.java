/* Copyright (C) 2022 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.tsv.eggnog;

import java.lang.reflect.Field;

/**
 * Model class representing an EggNog prediction line as read in a data file.
 * 
 * @see test.TestFeatureSystem for sample use case
 * 
 * @author Patrick G. Durand
 */
@SuppressWarnings("unused")
public class EggNogObject {
  private static String NA = "";//DO NOT modify, leave empty string!
  private String query_name = NA;
  private String seed_eggNOG_ortholog = NA;
  private String seed_ortholog_evalue = NA;
  private String seed_ortholog_score = NA;
  private String best_tax_level = NA;
  private String Preferred_name = NA;
  private String GOs = NA;
  private String EC = NA;
  private String KEGG_ko = NA;
  private String KEGG_Pathway = NA;
  private String KEGG_Module = NA;
  private String KEGG_Reaction = NA;
  private String KEGG_rclass = NA;
  private String BRITE = NA;
  private String KEGG_TC = NA;
  private String CAZy = NA;
  private String BiGG_Reaction = NA;
  private String taxonomic_scope = NA;
  private String eggNOG_OGs = NA;
  private String best_eggNOG_OG = NA;
  private String COG_Functional_cat = NA;
  private String eggNOG_free_text_desc = NA;
  
  protected static final String GO_FIELD = "GOs";
  protected static final String EC_FIELD = "EC";
  
  protected static String[] fieldNames = new String[] {
      "query_name",
      "seed_eggNOG_ortholog",
      "seed_ortholog_evalue",
      "seed_ortholog_score",
      "best_tax_level",
      "Preferred_name",
      GO_FIELD,
      EC_FIELD,
      "KEGG_ko",
      "KEGG_Pathway",
      "KEGG_Module",
      "KEGG_Reaction",
      "KEGG_rclass",
      "BRITE",
      "KEGG_TC",
      "CAZy",
      "BiGG_Reaction",
      "taxonomic_scope",
      "eggNOG_OGs",
      "best_eggNOG_OG",
      "COG_Functional_cat",
      "eggNOG_free_text_desc"
  };

  /**
   * Constructor.
   */
  public EggNogObject(String[] fieldValues) {
    int idx=0;
    try {
      //I did not want to create 22 setter methods!
      //of course, fieldValues must be appropriately formated.
      //of note, during testing: some values may be missing... this
      //is the reason why all fields are set with "n/a" value 
      //(see above field declaration)
      for (String aValue : fieldValues) {
        Field aField;
          aField = getClass().getDeclaredField(fieldNames[idx]);
          aField.set(this, aValue);
          idx++;
          if (idx>=fieldNames.length) {
            break;
          }
      }
    } catch (Exception e1) {
      //should not happen
    }
  }
  
  public String getQuery_name() {
    return query_name;
  }
   
  //Use a index relative to array fieldNames to avoid passing in
  //a bad field name
  public String getFieldValue(int idx) {
    String value = "n/a";
    try {
      Field aField;
      aField = getClass().getDeclaredField(fieldNames[idx]);
      value = aField.get(this).toString();
    } catch (Exception e1) {
      //should not happen since we rely on fieldNames 
    }
    return value;
  }
  
  public String getGeneOntologyIDs() {
    return GOs.isEmpty() ? null : GOs;
  }

  public String getEnzymeID() {
    return EC.isEmpty() ? null : EC;
  }

}
