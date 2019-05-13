package bzh.plealog.bioinfo.api.data.feature;

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
  TAX, // NCBI Taxonomy 
  GO,  //Gene Ontology 
  IPR, //InterPro
  EC,  //Enzyme Commission Nomenclature 
  LCA  //Least Common Ancestor (computed)
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
  public static final String FEATURE_CODE_TAXON = "TAX";
  public static final String FEATURE_CODE_GO = "GO";
  public static final String FEATURE_CODE_INTERPRO = "IPR";
  public static final String FEATURE_CODE_ENZYME = "EC";
  public static final String FEATURE_CODE_ORGANISM = "ORG";
  public static final String FEATURE_QUALIFIER_XREF = "db_xref";
  public static final String FEATURE_QUALIFIER_ENZYME = "EC_number";
  public static final String FEATURE_QUALIFIER_ORGANISM = "organism";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_NCBI = ":";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_SWISSPROT = ";";
  public static final String FEATURE_QUALIFIER_ANNOTATION_SEPARATOR_GO = ":";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_TAXON = "taxon";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_GO = "GO";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_INTERPRO = "InterPro";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_ENZYME = "EC";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_PUBMED = "pubmed";
  public static final String FEATURE_QUALIFIER_ANNOTATION_KEYWORD_NIL = "";
  public static final String FEATURE_PROTEIN_KEYWORD = "protein";
  public static final String FEATURE_SOURCE_KEYWORD = "source";
}