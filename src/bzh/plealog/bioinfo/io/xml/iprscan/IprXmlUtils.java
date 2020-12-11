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
package bzh.plealog.bioinfo.io.xml.iprscan;

import java.util.HashMap;

import org.xml.sax.Attributes;

import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants.ANNOTATION_CATEGORY;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants.ANNOTATION_GO_SUBCATEGORY;
import bzh.plealog.bioinfo.api.data.feature.FPosition;
import bzh.plealog.bioinfo.api.data.feature.FRange;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureLocation;

/**
 * Utility class containing shared code used by data handlers.
 * 
 * @author Patrick G. Durand
 */

public class IprXmlUtils {
  //for debug only: print out raw content of domain predictions
  public static boolean _verbose_ = false;

  //map Interproscan XML terms to Core API data model
  @SuppressWarnings("serial")
  public static HashMap<String, String> 
    GO_ONTOLOGY = new HashMap<String, String>() {{
      put("CELLULAR_COMPONENT", ANNOTATION_GO_SUBCATEGORY.C.getType());
      put("BIOLOGICAL_PROCESS", ANNOTATION_GO_SUBCATEGORY.P.getType());
      put("MOLECULAR_FUNCTION", ANNOTATION_GO_SUBCATEGORY.F.getType());
  }};
  
  //This SAX parser is designed to handle particular domain predictors
  @SuppressWarnings("serial")
  public static final HashMap<String, String> 
    ACCEPTED_DOMAINS = 
      new HashMap<String, String>() {{ 
        //Stad Core API managed classifications
        put("PFAM", AnnotationDataModelConstants.ANNOTATION_CATEGORY.PFM.getEncoding()); 
        put("PROSITE_PROFILES", AnnotationDataModelConstants.ANNOTATION_CATEGORY.PS.getEncoding());
        //Added: classifications providing IPR and GO data
        put("PRINTS", "PRINTS"); 
        put("TIGRFAM", "TIGRFAM"); 
        put("SUPERFAMILY", "SUPERFAMILY"); 
        }};
        
  //Interproscan data model elements handled by this SAX parser
  public static final String E_protein_matches            = "protein-matches";
  public static final String E_nucl_sequence_matches      = "nucleotide-sequence-matches";
  public static final String E_protein                    = "protein";
  public static final String E_nucl_sequence              = "nucleotide-sequence";
  public static final String E_sequence                   = "sequence";
  public static final String E_xref                       = "xref";
  public static final String E_orf                        = "orf";
  public static final String E_signature                  = "signature";
  public static final String E_signature_library_release  = "signature-library-release";
  public static final String E_entry                      = "entry";
  public static final String E_go_xref                    = "go-xref";
  public static final String E_hmmer3_location            = "hmmer3-location";
  public static final String E_profilescan_location       = "profilescan-location";
  public static final String E_locations                  = "locations";
  public static final String E_location_suffix            = "-location";

  //Interproscan data model attributes handled by this SAX parser
  public static final String A_id              = "id";
  public static final String A_ac              = "ac";
  public static final String A_name            = "name";
  public static final String A_desc            = "desc";
  public static final String A_category        = "category";
  public static final String A_library         = "library";
  public static final String A_start           = "start";
  public static final String A_end             = "end";
  public static final String A_strand          = "strand";
  public static final String A_score           = "score";
  public static final String A_evalue          = "evalue";

  public static final String S_sense       = "SENSE";
  public static final String S_ipr_version = "interproscan-version";

  public static final String QUAL_IPR      = "InterproScan %s";


  public static void handleInterpro(Feature feat, Attributes attributes) {
    feat.addQualifier(AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, 
        ANNOTATION_CATEGORY.IPR.getEncoding() + 
        "; " +
        attributes.getValue(A_ac) +
        "; " +
        attributes.getValue(A_desc));
    if(_verbose_) {
      System.out.println("\""+attributes.getValue(A_ac)+"\",");
    }
  }
  
  public static void handleGeneOntology(Feature feat, Attributes attributes) {
    feat.addQualifier(AnnotationDataModelConstants.FEATURE_QUALIFIER_XREF, 
        ANNOTATION_CATEGORY.GO.getEncoding() + 
        "; " +
        attributes.getValue(A_id) + 
        "; " +
        GO_ONTOLOGY.get(attributes.getValue(A_category))+": "+attributes.getValue(A_name));
    if(_verbose_) {
      System.out.println("\""+attributes.getValue(A_id)+"\",");
    }
  }
  
  public static void handleLocation(Feature feat, FeatureLocation loc, Attributes attributes) {
    int a = Integer.valueOf(attributes.getValue(A_start));
    int b = Integer.valueOf(attributes.getValue(A_end));
    loc.addRange(new FRange(new FPosition(Math.min(a, b)), new FPosition(Math.max(a, b))));
    String value = attributes.getValue(A_score);
    if (value!=null) {
      feat.addQualifier(A_score, value);
    }
    value = attributes.getValue(A_evalue);
    if (value!=null) {
      feat.addQualifier(A_evalue, value);
    }
    if(_verbose_) {
      System.out.println(Math.min(a, b)+","+Math.max(a, b));
    }
  }
}
