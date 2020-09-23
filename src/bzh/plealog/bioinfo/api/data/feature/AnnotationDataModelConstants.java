package bzh.plealog.bioinfo.api.data.feature;

import org.apache.commons.lang.StringUtils;

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
public class AnnotationDataModelConstants {

  //Special keys to get information about Classifications
  //Format for values associated to these keys is free
  public static enum ANNOTATION_CATEGORY {
    TAX ("TAX","NCBI Taxonomy", "taxon"),
    GO ("GO", "Gene Ontology", "GO"),
    IPR ("IPR", "InterPro", "InterPro"),
    PFM ("PFM", "PFAM", "Pfam"),
    EC ("EC", "Enzyme Commission", "EC"),
    PS ("PS", "Prosite", "PROSITE"),
    LCA ("LCA", "Least Common Ancestor", "LCA");
    
    private String type;
    private String description;
    private String encoding;
    ANNOTATION_CATEGORY(String type, String description, String encoding){
      this.type = type;
      this.description = description;
      this.encoding = encoding;
    }
    
    public String getType() {
      return type;
    }
    public String getDescription() {
      return description;
    }
    public String getEncoding() {
      return encoding;
    }
  }
  //Special keys to get information about GO sub-classifications
  public static enum ANNOTATION_GO_SUBCATEGORY {
    P ("P","Biological Process"), 
    F ("F","Molecular Function"),
    C ("C","Cellular Component");
  
    private String type;
    private String description;
    
    ANNOTATION_GO_SUBCATEGORY(String type, String description){
      this.type = type;
      this.description = description;
    }
    
    public String getType() {
      return type;
    }
    public String getDescription() {
      return description;
    }
  }

  // general
  public static final String SEPARATOR = "_";
  public static final String SPACE = " ";

  // selected item - feature classification
  public static final int FEATURE_LABEL_MAX_LEN = 40;
  public static final String FEATURE_LABEL_CONTINUATION_TAG = "...";
  public static final String FEATURE_LABEL_SEPARATOR_OPEN = "[";
  public static final String FEATURE_LABEL_SEPARATOR_CLOSE = "]";
  public static final String CATEGORY_CODE_SEPARATOR = ":";
  public static final String FEATURE_ACCESSION_SEPARATOR = "|";
  public static final String CATEGORY_FEATURES_TERMINATOR = ";";

  // Biological classification stuffs
  public static final String FEATURE_LABEL_TAXON = "Taxonomy";
  public static final String FEATURE_LABEL_GO = "GeneOntology";
  public static final String FEATURE_LABEL_INTERPRO = "InterPro";
  public static final String FEATURE_LABEL_ENZYME = "Enzyme";
  public static final String FEATURE_LABEL_ORGANISM = "Organism";
  public static final String FEATURE_LABEL_PUBMED = "pubmed";
  public static final String FEATURE_CODE_TAXON = ANNOTATION_CATEGORY.TAX.getType();
  public static final String FEATURE_CODE_GO = ANNOTATION_CATEGORY.GO.getType();
  public static final String FEATURE_CODE_INTERPRO = ANNOTATION_CATEGORY.IPR.getType();
  public static final String FEATURE_CODE_ENZYME = ANNOTATION_CATEGORY.EC.getType();
  public static final String FEATURE_CODE_ORGANISM = "ORG";
  public static final String FEATURE_QUALIFIER_XREF = "db_xref";
  public static final String FEATURE_QUALIFIER_ENZYME = "EC_number";
  public static final String FEATURE_QUALIFIER_ORGANISM = "organism";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI = ":";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT = ";";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_GO = ":";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF = ";";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON = ANNOTATION_CATEGORY.TAX.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO = ANNOTATION_CATEGORY.GO.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_INTERPRO = ANNOTATION_CATEGORY.IPR.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_ENZYME = ANNOTATION_CATEGORY.EC.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_PUBMED = "pubmed";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_NIL = "";
  public static final String FEATURE_PROTEIN_KEYWORD = "protein";
  public static final String FEATURE_SOURCE_KEYWORD = "source";
  
 private static void formatDataLine(StringBuffer buf, ANNOTATION_CATEGORY cat, String id, String description) {
   buf.append(cat.getEncoding());
   buf.append(FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF);
   buf.append(" ");
   buf.append(id);
   if (StringUtils.isNotBlank(description)) {
     buf.append(FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF);
     buf.append(" ");
     buf.append(description);
     buf.append(".");
   }
 }
 public static String formatDbxrefForQualifier(ANNOTATION_CATEGORY cat, String id, String description) {
   StringBuffer buf = new StringBuffer();
   switch(cat) {
   case TAX:
     buf.append(ANNOTATION_CATEGORY.TAX.getEncoding());
     buf.append(FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI);
     buf.append(id);
     break;
     default:
       buf.append(cat.getEncoding());
       formatDataLine(buf, cat, id, description);
       break;
   }
   return buf.toString();
 }
}