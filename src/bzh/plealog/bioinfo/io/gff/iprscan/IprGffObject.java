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
package bzh.plealog.bioinfo.io.gff.iprscan;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import test.TestSerialSystem;

/**
 * Model class representing an IPRscan prediction line as read in a gff3 data file.
 * 
 * @see TestSerialSystem for sample use case
 * 
 * @author Patrick G. Durand
 */
public class IprGffObject {
  //each gff object is associated with sequence region id
  private String regionId;
  //Standard GFF3 data file provides these data, on each line:
  //seqid|source|type|start|end|score|strand|phase|attributes
  private String seqid;
  private String source;
  private String type;
  private String start;
  private String end;
  private String score;
  private String strand;
  private String phase;
  private String attributes;

  //for internal use
  private String objKey = null;
  private Map<String, String> attr_map = null;
  
  //Sample attributes are:
  //Name=PF00696;signature_desc=Amino acid kinase family;Target=null 84 314;status=T;ID=match$8_84_314;Ontology_term="GO:0008652";date=15-04-2013;Dbxref="InterPro:IPR001048","Reactome:REACT_13"
  public static final String NAME_ATTR = "Name";
  public static final String SIGNATURE_ATTR = "signature_desc";
  public static final String ONTOLOGY_ATTR = "Ontology_term";
  public static final String DBXREF_ATTR = "Dbxref";
  public static final String TARGET_ATTR = "Target";
  public static final String STATUS_ATTR = "status";
  public static final String ID_ATTR = "ID";
  public static final String DATE_ATTR = "date";

  //Accepted types for Feature data model, see IprPrediction.getFeature()
  public static final String PMATCH_TYPE = "protein_match";
  public static final String NUC_TYPE = "nucleic_acid";
  public static final String ORF_TYPE = "ORF";
  public static final String POLYP_TYPE = "polypeptide";
  
  /**
   * Constructor.
   */
  public IprGffObject() {
    
  }
  /**
   * Constructor with expected data items.
   */
  public IprGffObject(String regionId, String seqid, String source, String type, String start, String end, String score, String strand,
      String phase, String attributes) {
    super();
    this.regionId = regionId;
    this.seqid = seqid;
    this.source = source;
    this.type = type;
    this.start = start;
    this.end = end;
    this.score = score;
    this.strand = strand;
    this.phase = phase;
    this.attributes = attributes;
  }
  
  //Notice: toString(), equals() and hashcode() are mostly overridden to
  // enable appropriate display of values (equals) and stream filtering
  // of distinct values (equals and hashcode)
  
  @Override
  public String toString(){
    return getKey();
  }
  
  @Override
  public boolean equals(Object o) {
    return o!=null 
        && o instanceof IprGffObject && 
        ((IprGffObject)o).getKey().equals(getKey());
  }
  
  @Override
  public int hashCode() {
    return getKey().hashCode();
  }

  public String getKey() {
    if (objKey==null) {
      objKey = seqid+":"+source+"/"+type+" ("+start+"-"+end+")";
    }
    return objKey;
  }
  
  public String getRegionId() {
    return regionId;
  }
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }
  public String getSeqid() {
    return seqid;
  }
  public void setSeqid(String seqid) {
    this.seqid = seqid;
    objKey=null;
  }
  public String getSource() {
    return source;
  }
  public void setSource(String source) {
    this.source = source;
    objKey=null;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
    objKey=null;
  }
  public String getStart() {
    return start;
  }
  public void setStart(String start) {
    this.start = start;
    objKey=null;
  }
  public String getEnd() {
    return end;
  }
  public void setEnd(String end) {
    this.end = end;
    objKey=null;
  }
  public String getScore() {
    return score;
  }
  public void setScore(String score) {
    this.score = score;
  }
  public String getStrand() {
    return strand;
  }
  public void setStrand(String strand) {
    this.strand = strand;
  }
  public String getPhase() {
    return phase;
  }
  public void setPhase(String phase) {
    this.phase = phase;
  }
  public String getAttributes() {
    return attributes;
  }
  public void setAttributes(String attributes) {
    this.attributes = attributes;
    attr_map = null;
  }

  /**
   * Return value associated to a particular key.
   * 
   * @param key key name. See one of XXX_ATTR constants defined in this class.
   * 
   * @return corresponding value or null if not found
   */
  public String getAttributeValue(String key) {
    if (attr_map==null) {
      attr_map = Arrays.asList(this.getAttributes().split(";"))
          .stream()
          .map(s -> s.split("="))
          .collect(Collectors.toMap(a -> a[0], a -> a[1].replaceAll("\"", "")));
    }
    return attr_map.get(key);
  }
  
}
