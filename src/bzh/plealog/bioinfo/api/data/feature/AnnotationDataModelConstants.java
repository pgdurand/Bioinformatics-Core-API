package bzh.plealog.bioinfo.api.data.feature;

import java.util.HashSet;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import bzh.plealog.bioinfo.api.data.searchjob.SJTermSummary;

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
  //Format for values associated to these keys is free, except 'type' and 'encoding': do not modify!
  //type and encoding relate to bzh.plealog.bioinfo.ui.resources.featureWebLink.conf
  public static enum ANNOTATION_CATEGORY {
    TAX ("TAX", "NCBI Taxonomy",         "taxon"),
    GO  ("GO",  "Gene Ontology",         "GO"),
    IPR ("IPR", "InterPro",              "InterPro"),
    PFM ("PF",  "PFAM",                  "Pfam"),
    EC  ("EC",  "Enzyme Commission",     "EC"),
    PS  ("PS",  "Prosite",               "PROSITE"),
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
  
  //Utility Set to locate Qualifier values from FeatureTable that are capable
  //of providing classification terms
  @SuppressWarnings("serial")
  public static Hashtable<String, ANNOTATION_CATEGORY> CLASSIF_TERM_PROVIDER = new Hashtable<String, ANNOTATION_CATEGORY>() {{
    put(ANNOTATION_CATEGORY.TAX.getEncoding(), ANNOTATION_CATEGORY.TAX);
    put(ANNOTATION_CATEGORY.EC.getEncoding(), ANNOTATION_CATEGORY.EC);
    put(ANNOTATION_CATEGORY.GO.getEncoding(), ANNOTATION_CATEGORY.GO);
    put(ANNOTATION_CATEGORY.IPR.getEncoding(), ANNOTATION_CATEGORY.IPR);
    put(ANNOTATION_CATEGORY.PFM.getEncoding(), ANNOTATION_CATEGORY.PFM);
    put(ANNOTATION_CATEGORY.PS.getEncoding(), ANNOTATION_CATEGORY.PS);
  }};

  @SuppressWarnings("serial")
  public static Hashtable<String, ANNOTATION_CATEGORY> CLASSIF_TERM_TYPE = new Hashtable<String, ANNOTATION_CATEGORY>() {{
    put(ANNOTATION_CATEGORY.TAX.getType(), ANNOTATION_CATEGORY.TAX);
    put(ANNOTATION_CATEGORY.EC.getType(), ANNOTATION_CATEGORY.EC);
    put(ANNOTATION_CATEGORY.GO.getType(), ANNOTATION_CATEGORY.GO);
    put(ANNOTATION_CATEGORY.IPR.getType(), ANNOTATION_CATEGORY.IPR);
    put(ANNOTATION_CATEGORY.PFM.getType(), ANNOTATION_CATEGORY.PFM);
    put(ANNOTATION_CATEGORY.PS.getType(), ANNOTATION_CATEGORY.PS);
  }};

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

  //Prepare labels to display ontology names; ontotlogy = biological classification.
  public static final String ENZYME_INDEX_LABEL = 
      ANNOTATION_CATEGORY.EC.getDescription();
  
  public static final String PFAM_INDEX_LABEL = 
      ANNOTATION_CATEGORY.PFM.getDescription();

  public static final String PROSITE_INDEX_LABEL = 
      ANNOTATION_CATEGORY.PS.getDescription();

  public static final String INTERPRO_INDEX_LABEL = 
  ANNOTATION_CATEGORY.IPR.getDescription();
  
  public static final String TAXON_INDEX_LABEL = 
  ANNOTATION_CATEGORY.TAX.getDescription();
  
  public static final String GO_SUBCELLULAR_LOCALISATION_INDEX_LABEL = 
  ANNOTATION_CATEGORY.GO.getType() + ": " +
      ANNOTATION_GO_SUBCATEGORY.C.getDescription();
  
  public static final String GO_BIOLOGICAL_FUNCTION_INDEX_LABEL = 
  ANNOTATION_CATEGORY.GO.getType() + ": " +
      ANNOTATION_GO_SUBCATEGORY.P.getDescription();
  
  public static final String GO_ENZYMATIC_ACTIVITY_INDEX_LABEL = 
  ANNOTATION_CATEGORY.GO.getType() + ": " +
      ANNOTATION_GO_SUBCATEGORY.F.getDescription();
  
  //Prepare codes for GO sub-classifications
  public static final String GO_C_CODE = 
      SJTermSummary.formatViewType(
          ANNOTATION_CATEGORY.GO.getType(), 
          ANNOTATION_GO_SUBCATEGORY.C.name());
  
  public static final String GO_P_CODE = 
  SJTermSummary.formatViewType(
      ANNOTATION_CATEGORY.GO.getType(), 
      ANNOTATION_GO_SUBCATEGORY.P.name());
  
  public static final String GO_F_CODE = 
  SJTermSummary.formatViewType(
      ANNOTATION_CATEGORY.GO.getType(), 
      ANNOTATION_GO_SUBCATEGORY.F.name());
  
  //List onotology labels
  //when updating this array, also update CLASSIF_INDEX and CLASSIF_INDEX_TYPE accordingly
  public static final String[] EXTENDED_FEATURE_INDEX_LABELS = {
    TAXON_INDEX_LABEL,
    ENZYME_INDEX_LABEL, 
    PFAM_INDEX_LABEL,
    INTERPRO_INDEX_LABEL,
    PROSITE_INDEX_LABEL,
    GO_SUBCELLULAR_LOCALISATION_INDEX_LABEL,
    GO_BIOLOGICAL_FUNCTION_INDEX_LABEL,
    GO_ENZYMATIC_ACTIVITY_INDEX_LABEL,
  };
  
  //Associate ontology codes to labels
  @SuppressWarnings("serial")
  public static Hashtable<String, String> CLASSIF_CODE_TO_NAME = new Hashtable<String, String>() {{
    put(ANNOTATION_CATEGORY.TAX.getType(), TAXON_INDEX_LABEL);
    put(ANNOTATION_CATEGORY.EC.getType(), ENZYME_INDEX_LABEL);
    put(ANNOTATION_CATEGORY.PFM.getType(), PFAM_INDEX_LABEL);
    put(ANNOTATION_CATEGORY.IPR.getType(), INTERPRO_INDEX_LABEL);
    put(ANNOTATION_CATEGORY.PS.getType(), PROSITE_INDEX_LABEL);
    put(GO_C_CODE, GO_SUBCELLULAR_LOCALISATION_INDEX_LABEL);
    put(GO_P_CODE, GO_BIOLOGICAL_FUNCTION_INDEX_LABEL);
    put(GO_F_CODE, GO_ENZYMATIC_ACTIVITY_INDEX_LABEL);
  }};
  
  //Reverse of CLASSIF_CODE_TO_NAME
  @SuppressWarnings("serial")
  public static Hashtable<String, String> CLASSIF_NAME_TO_CODE = new Hashtable<String, String>() {{
    put(ENZYME_INDEX_LABEL, ANNOTATION_CATEGORY.EC.getType());
    put(PFAM_INDEX_LABEL, ANNOTATION_CATEGORY.PFM.getType());
    put(INTERPRO_INDEX_LABEL, ANNOTATION_CATEGORY.IPR.getType());
    put(PROSITE_INDEX_LABEL, ANNOTATION_CATEGORY.PS.getType());
    put(TAXON_INDEX_LABEL, ANNOTATION_CATEGORY.TAX.getType());
    put(GO_SUBCELLULAR_LOCALISATION_INDEX_LABEL, GO_C_CODE);
    put(GO_BIOLOGICAL_FUNCTION_INDEX_LABEL, GO_P_CODE);
    put(GO_ENZYMATIC_ACTIVITY_INDEX_LABEL, GO_F_CODE);
  }};
  
  //Associate ontology labels to corresponding AnnotationDataModelConstants.ANNOTATION_CATEGORY
  @SuppressWarnings("serial")
  public static Hashtable<String, ANNOTATION_CATEGORY> CLASSIF_INDEX_TYPE = 
  new Hashtable<String, ANNOTATION_CATEGORY>() {{
    put(ENZYME_INDEX_LABEL, ANNOTATION_CATEGORY.EC);
    put(PFAM_INDEX_LABEL, ANNOTATION_CATEGORY.PFM);
    put(INTERPRO_INDEX_LABEL, ANNOTATION_CATEGORY.IPR);
    put(PROSITE_INDEX_LABEL, ANNOTATION_CATEGORY.PS);
    put(TAXON_INDEX_LABEL, ANNOTATION_CATEGORY.TAX);
    put(GO_SUBCELLULAR_LOCALISATION_INDEX_LABEL, ANNOTATION_CATEGORY.GO);
    put(GO_BIOLOGICAL_FUNCTION_INDEX_LABEL, ANNOTATION_CATEGORY.GO);
    put(GO_ENZYMATIC_ACTIVITY_INDEX_LABEL, ANNOTATION_CATEGORY.GO);
  }};
  
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

  // /begin-note/ these keywords are now in ANNOTATION_CATEGORY (type data field). 
  //              Do the following to ensure backward compatibility.
  public static final String FEATURE_CODE_TAXON = ANNOTATION_CATEGORY.TAX.getType();
  public static final String FEATURE_CODE_GO = ANNOTATION_CATEGORY.GO.getType();
  public static final String FEATURE_CODE_INTERPRO = ANNOTATION_CATEGORY.IPR.getType();
  public static final String FEATURE_CODE_ENZYME = ANNOTATION_CATEGORY.EC.getType();
  // /end-note/
  
  public static final String FEATURE_CODE_ORGANISM = "ORG";
  public static final String FEATURE_QUALIFIER_XREF = "db_xref";
  public static final String FEATURE_QUALIFIER_ENZYME = "EC_number";
  public static final String FEATURE_QUALIFIER_ORGANISM = "organism";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI = ":";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT = ";";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_GO = ":";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF = ";";
  
  // /begin-note/ these keywords are now in ANNOTATION_CATEGORY (encoding data field). 
  //              Do the following to ensure backward compatibility.
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON = ANNOTATION_CATEGORY.TAX.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO = ANNOTATION_CATEGORY.GO.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_INTERPRO = ANNOTATION_CATEGORY.IPR.getEncoding();
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_ENZYME = ANNOTATION_CATEGORY.EC.getEncoding();
  // /end-note/
  
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_PUBMED = "pubmed";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_NIL = "";
  public static final String FEATURE_PROTEIN_KEYWORD = "protein";
  public static final String FEATURE_SOURCE_KEYWORD = "source";
  
  public static final String FEATURE_EMPTY_DESC = "-";
  
  //Utility Set to locate Qualfiers from FeatureTable that are capable
  //of providing classification data
  @SuppressWarnings("serial")
  public static HashSet<String> CLASSIF_PROVIDER = new HashSet<String>() {{
    add(FEATURE_QUALIFIER_XREF);
    add(FEATURE_QUALIFIER_ENZYME);
  }};
  
  
 private static void formatDataLine(StringBuffer buf, ANNOTATION_CATEGORY cat, String id, String description) {
   buf.append(cat.getEncoding());
   buf.append(FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF);
   buf.append(" ");
   buf.append(id);
   if (StringUtils.isNotBlank(description)) {
     buf.append(FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF);
     buf.append(" ");
     buf.append(description);
     //e.g. Iprscan may provide description with or without ending '.'!
     if(description.charAt(description.length()-1)=='.' == false) {
       buf.append(".");
     }
   }
   else {
     buf.append(FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_XREF);
     buf.append(" -.");
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
       formatDataLine(buf, cat, id, description);
       break;
   }
   return buf.toString();
 }
}