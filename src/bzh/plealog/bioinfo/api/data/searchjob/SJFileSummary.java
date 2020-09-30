/* Copyright (C) 2003-2019 Patrick G. Durand
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
package bzh.plealog.bioinfo.api.data.searchjob;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants.ANNOTATION_CATEGORY;
import bzh.plealog.bioinfo.api.data.searchresult.SRCTerm;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspScore;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;
import bzh.plealog.bioinfo.io.searchresult.csv.ExtractAnnotation;

/**
 * This class contains the summary of a BLAST result. Such a summary reports the
 * information related to the best hit of that result.
 * 
 * Directly used for serialization. Modify with caution.
 * 
 * @author Patrick G. Durand
 */
public class SJFileSummary implements Serializable {

  private static final long serialVersionUID = 7467266811057328060L;

  private int nHits;
  private String bestHitAccession;
  private String bestHitDescription;
  private String bestHitLength;
  private String bestHitEValue;
  private String bestHitIdentify;
  private String bestHitSimilarity;
  private String bestHitCoverage;
  private String bestHitScore;
  private String bestHitScoreBits;
  private String bestHitCoverageH;
  private String taxonomy;
  private String organism;
  private String queryLength;
  private String queryFrom;
  private String queryTo;
  private String queryFrame;
  private String queryGaps;
  private String bestHitFrom;
  private String bestHitTo;
  private String bestHitFrame;
  private String bestHitGaps;
  private String alignLength;
  private String nbHsps;
  private String queryId;
  private String queryDescription;
  private String execMsg;
  private String queryRID;
  private SROutput.FEATURES_CONTAINER featContainer;
  private boolean filtered;
  //complete classification (main Terms + FAKE ones (those internal to paths).
  private SRClassification hClassification;
  private SRClassification qClassification;
  
  private transient String totalGaps;
  private transient String percentGaps;
  private transient String mistmatches;
  private transient String LCA;
  private transient String rankLCA;
  private transient String originJobName = NOT_APPLICABLE;
  private transient String originJobId = NOT_APPLICABLE;
  //mains terms only for View purpose
  private transient List<SJTermSummary> mainHitTermsForView;
  private transient List<SJTermSummary> mainQueryTermsForView;
  
  private transient boolean _initialized;
  
  public static final String UNKNOWN = "-";
  public static final String NOT_APPLICABLE = "none";

  public static final int HEADER_SIZE = 128;

  public static final DecimalFormat EVALUE_FORMATTER1 = new DecimalFormat("0.####E0");
  public static final DecimalFormat EVALUE_FORMATTER2 = new DecimalFormat("##.##");
  public static final DecimalFormat PCT_FORMATTER = new DecimalFormat("###.#");
  public static final DecimalFormat SCORE_FORMATTER = new DecimalFormat("####");

  /**
   * Default constructor.
   */
  public SJFileSummary() {
    reset(true);
    setBestHitAccession(NOT_APPLICABLE);
    setBestHitDescription(NOT_APPLICABLE);
  }

  public void reset(boolean updateQuery) {
    setBestHitAccession(NOT_APPLICABLE);
    setBestHitDescription(NOT_APPLICABLE);
    setBestHitEValue(UNKNOWN);
    setBestHitLength(UNKNOWN);
    setBestHitIdentify(UNKNOWN);
    setBestHitSimilarity(UNKNOWN);
    setBestHitCoverage(UNKNOWN);
    setBestHitScore(UNKNOWN);
    setBestHitScoreBits(UNKNOWN);
    setBestHitCoverageH(UNKNOWN);
    setBestHitFrom(UNKNOWN);
    setBestHitTo(UNKNOWN);
    setBestHitFrame(UNKNOWN);
    setBestHitGaps(UNKNOWN);
    setQueryFrom(UNKNOWN);
    setQueryTo(UNKNOWN);
    setQueryFrame(UNKNOWN);
    setQueryGaps(UNKNOWN);
    setAlignLength(UNKNOWN);
    setNbHsps(UNKNOWN);
    if (updateQuery) {
      setQueryId(UNKNOWN);
      setQueryDescription(UNKNOWN);
      setQueryLength(UNKNOWN);
    }
    setExecMsg(null);
    setTaxonomy(UNKNOWN);
    setOrganism(UNKNOWN);
    setFeatContainer(SROutput.FEATURES_CONTAINER.none);
    setFiltered(false);
    setNHits(0);
    setQueryRID(UNKNOWN);
    setTotalGaps(UNKNOWN);
    setPercentGaps(UNKNOWN);
    setMistmatches(UNKNOWN);
    setLCA(UNKNOWN);
    setRankLCA(UNKNOWN);
    setOriginJobName(NOT_APPLICABLE);
  }

  private void prepareClassificationData(SROutput output) {
    // Get unique set of Bio Classification IDs
    if (output.getClassification()!=null) {
      //Step 1 : best hit part
      SRClassification classification = CoreSystemConfigurator.getSRFactory().creationBClassification();
      SRClassification hitClassification;
      
      // Collect unique set of Bio Classification IDs of first hit only
      hitClassification = ExtractAnnotation.getClassificationdata(output.getIteration(0).getHit(0), true);
      
      //then discard FAKE terms (those making path of Terms associated to hits)
      Enumeration<String> ids = hitClassification.getTermIDs();
      String id;
      SRCTerm term;
      //prepare the view List of main Terms
      LinkedList<SJTermSummary> mapTerms = new LinkedList<>();
      while(ids.hasMoreElements()) {
        id = ids.nextElement();
        //refClassification contains full Term data (desc + path)
        term = output.getClassification().getTerm(id);
        if (term==null)//backward compatibility: Pfam IDs were not handled in previous releases
          term = hitClassification.getTerm(id);
        classification.addTerm(id, term);
        if ((term.getType().equals(SRCTerm.FAKE_TERM)||
            term.getType().equals(ANNOTATION_CATEGORY.TAX.name()))==false) {
          mapTerms.add(new SJTermSummary(id, term));
        }
      }
      setHitClassification(classification);  
      mapTerms.sort(Comparator.comparing(SJTermSummary::getViewType).thenComparing(SJTermSummary::getID));
      setHitClassificationForView(mapTerms);
      
      //Step 2 : query part
      if (output.getIteration(0).getIterationQueryFeatureTable()!=null) {
        SRClassification queryClassification = CoreSystemConfigurator.getSRFactory().creationBClassification();
        classification = ExtractAnnotation.getClassificationdata(
            output.getIteration(0).getIterationQueryFeatureTable());
        
        ids = classification.getTermIDs();
        //prepare the view List of main Terms
        mapTerms = new LinkedList<>();
        while(ids.hasMoreElements()) {
          id = ids.nextElement();
          //refClassification contains full Term data (desc + path)
          term = output.getClassification().getTerm(id);
          if (term==null)//backward compatibility: Pfam IDs were not handled in previous releases
            term = classification.getTerm(id);
          queryClassification.addTerm(id, term);
          if ((term.getType().equals(SRCTerm.FAKE_TERM)||
              term.getType().equals(ANNOTATION_CATEGORY.TAX.name()))==false) {
            mapTerms.add(new SJTermSummary(id, term));
          }
        }
        setQueryClassification(queryClassification);  
        mapTerms.sort(Comparator.comparing(SJTermSummary::getViewType).thenComparing(SJTermSummary::getID));
        setQueryClassificationForView(mapTerms);
      }
    }

  }
  /**
   * Initializes this BFileSummary from a BOutput.
   */
  public void initialize(SROutput output) {
    BankSequenceInfo si;
    SRIteration iterHits;
    SRHit hit;
    SRHsp hsp;
    SRHspScore scores;
    SRHspSequence seq;
    double eval;

    if (output.countIteration() == 0) {
      reset(false);
      return;
    }

    // init Query fields
    Object o = output.getRequestInfo().getValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY);
    setQueryLength(o != null ? o.toString() : UNKNOWN);

    o = output.getRequestInfo().getValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY);
    setQueryId(o != null ? o.toString() : UNKNOWN);
    
    o = output.getRequestInfo().getValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY);
    setQueryDescription(o != null ? o.toString() : UNKNOWN);

    //do we hat hits?
    iterHits = output.getIteration(0);
    if (iterHits.countHit() == 0) {
      reset(false);
      return;
    }

    //init best hit fields
    hit = iterHits.getHit(0);
    nHits = iterHits.countHit();
    setBestHitAccession(hit.getHitAccession());

    String definition = hit.getHitDef();
    int idx = definition.indexOf("[[");
    if (idx != -1) {
      definition = definition.substring(0, idx);
    }
    if (definition.length() > HEADER_SIZE)
      definition = definition.substring(0, HEADER_SIZE);

    setBestHitDescription(definition);
    setBestHitLength(String.valueOf(hit.getHitLen()));
    hsp = hit.getHsp(0);
    scores = hsp.getScores();
    eval = scores.getEvalue();
    if (eval > 0 && eval < 0.1)
      setBestHitEValue(EVALUE_FORMATTER1.format(eval));
    else
      setBestHitEValue(EVALUE_FORMATTER2.format(eval));
    eval = scores.getScore();
    if (eval > 0 && eval < 0.1)
      setBestHitScore(EVALUE_FORMATTER1.format(eval));
    else
      setBestHitScore(EVALUE_FORMATTER2.format(eval));
    setBestHitScoreBits(SCORE_FORMATTER.format(scores.getBitScore()));
    setBestHitIdentify(PCT_FORMATTER.format(scores.getIdentityP()) + "%");
    setBestHitSimilarity(PCT_FORMATTER.format(scores.getPositiveP()) + "%");
    setTotalGaps(String.valueOf(scores.getGaps()));
    setPercentGaps(PCT_FORMATTER.format(scores.getGapsP()) + "%");
    setMistmatches(String.valueOf(scores.getMismatches()));
    setBestHitCoverage(PCT_FORMATTER.format(hit.getQueryGlobalCoverage()) + "%");
    setBestHitCoverageH(PCT_FORMATTER.format(hit.getHitGlobalCoverage()) + "%");
    seq = hsp.getHit();
    setBestHitFrom(String.valueOf(seq.getFrom()));
    setBestHitTo(String.valueOf(seq.getTo()));
    setBestHitFrame(String.valueOf(seq.getFrame()));
    setBestHitGaps(String.valueOf(seq.getGaps()));
    seq = hsp.getQuery();
    setQueryFrom(String.valueOf(seq.getFrom()));
    setQueryTo(String.valueOf(seq.getTo()));
    setQueryFrame(String.valueOf(seq.getFrame()));
    setQueryGaps(String.valueOf(seq.getGaps()));

    setAlignLength(String.valueOf(hsp.getScores().getAlignLen()));
    setNbHsps(String.valueOf(hit.countHsp()));
    setFeatContainer(output.checkFeatures());
    si = hit.getSequenceInfo();
    if (si != null) {
      if (si.getOrganism() != null)
        organism = si.getOrganism();
      if (si.getTaxonomy() != null)
        taxonomy = si.getTaxonomy();
    }

    prepareClassificationData(output);

    _initialized = true;
  }

  /**
   * Sets the initial status of this BFileSummary.
   */
  public void setInitialized(boolean initialized) {
    _initialized = initialized;
  }

  public boolean isInitialized() {
    return _initialized;
  }
  
  /**
   * Returns the accession of best hit.
   */
  public String getBestHitAccession() {
    return bestHitAccession;
  }

  /**
   * Sets the accession of best hit.
   */
  public void setBestHitAccession(String hitAccession) {
    bestHitAccession = hitAccession;
  }

  /**
   * Returns the description of best hit.
   */
  public String getBestHitDescription() {
    return bestHitDescription;
  }

  /**
   * Sets the description of best hit.
   */
  public void setBestHitDescription(String hitDescription) {
    bestHitDescription = hitDescription;
  }

  /**
   * Returns the e-value of best hit.
   */
  public String getBestHitEValue() {
    return bestHitEValue;
  }

  /**
   * Sets the e-value of best hit.
   */
  public void setBestHitEValue(String hitEValue) {
    bestHitEValue = hitEValue;
  }

  /**
   * Returns the length of best hit.
   */
  public String getBestHitLength() {
    return bestHitLength;
  }

  /**
   * Sets the length of best hit.
   */
  public void setBestHitLength(String hitLength) {
    bestHitLength = hitLength;
  }

  public String getBestHitCoverage() {
    return bestHitCoverage;
  }

  public void setBestHitCoverage(String bestHitCoverage) {
    this.bestHitCoverage = bestHitCoverage;
  }

  public String getBestHitIdentify() {
    return bestHitIdentify;
  }

  public void setBestHitIdentify(String bestHitIdentify) {
    this.bestHitIdentify = bestHitIdentify;
  }

  public String getBestHitSimilarity() {
    return bestHitSimilarity;
  }

  public void setBestHitSimilarity(String bestHitSimilarity) {
    this.bestHitSimilarity = bestHitSimilarity;
  }

  /**
   * Returns the number of hits.
   */
  public int getNHits() {
    return nHits;
  }

  /**
   * Sets the number of hits.
   */
  public void setNHits(int hits) {
    nHits = hits;
  }

  public String getBestHitScore() {
    return bestHitScore;
  }

  public void setBestHitScore(String bestHitScore) {
    this.bestHitScore = bestHitScore;
  }

  public String getBestHitScoreBits() {
    return bestHitScoreBits;
  }

  public void setBestHitScoreBits(String bestHitScoreBits) {
    this.bestHitScoreBits = bestHitScoreBits;
  }

  public String getBestHitCoverageH() {
    return bestHitCoverageH;
  }

  public void setBestHitCoverageH(String bestHitCoverageH) {
    this.bestHitCoverageH = bestHitCoverageH;
  }

  public String getTaxonomy() {
    return taxonomy;
  }

  public String getOrganism() {
    return organism;
  }

  public void setTaxonomy(String taxonomy) {
    this.taxonomy = taxonomy;
  }

  public void setOrganism(String organism) {
    this.organism = organism;
  }

  public String getQueryLength() {
    return queryLength;
  }

  public String getQueryFrom() {
    return queryFrom;
  }

  public String getQueryTo() {
    return queryTo;
  }

  public String getQueryFrame() {
    return queryFrame;
  }

  public String getBestHitFrom() {
    return bestHitFrom;
  }

  public String getBestHitTo() {
    return bestHitTo;
  }

  public String getBestHitFrame() {
    return bestHitFrame;
  }

  public void setQueryLength(String queryLength) {
    this.queryLength = queryLength;
  }

  public void setQueryFrom(String queryFrom) {
    this.queryFrom = queryFrom;
  }

  public void setQueryTo(String queryTo) {
    this.queryTo = queryTo;
  }

  public void setQueryFrame(String queryFrame) {
    this.queryFrame = queryFrame;
  }

  public void setBestHitFrom(String bestHitFrom) {
    this.bestHitFrom = bestHitFrom;
  }

  public void setBestHitTo(String bestHitTo) {
    this.bestHitTo = bestHitTo;
  }

  public void setBestHitFrame(String bestHitFrame) {
    this.bestHitFrame = bestHitFrame;
  }

  public String getQueryGaps() {
    return queryGaps;
  }

  public String getBestHitGaps() {
    return bestHitGaps;
  }

  public void setQueryGaps(String queryGaps) {
    this.queryGaps = queryGaps;
  }

  public void setBestHitGaps(String bestHitGaps) {
    this.bestHitGaps = bestHitGaps;
  }

  public String getAlignLength() {
    return alignLength;
  }

  public void setAlignLength(String alignLength) {
    this.alignLength = alignLength;
  }

  public String getNbHsps() {
    return nbHsps;
  }

  public void setNbHsps(String nbHsps) {
    this.nbHsps = nbHsps;
  }

  public String getQueryId() {
    return queryId;
  }

  public String getQueryDescription() {
    return queryDescription;
  }

  public String getExecMsg() {
    return execMsg;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public void setQueryDescription(String queryDescription) {
    this.queryDescription = queryDescription;
  }

  public void setExecMsg(String execMsg) {
    this.execMsg = execMsg;
  }

  public SROutput.FEATURES_CONTAINER getFeatContainer() {
    return featContainer;
  }

  public void setFeatContainer(SROutput.FEATURES_CONTAINER featContainer) {
    this.featContainer = featContainer;
  }

  public boolean isFiltered() {
    return filtered;
  }

  public void setFiltered(boolean filtered) {
    this.filtered = filtered;
  }

  public String getQueryRID() {
    return queryRID;
  }

  public void setQueryRID(String queryRID) {
    this.queryRID = queryRID;
  }

  public String getTotalGaps() {
    return totalGaps;
  }

  public void setTotalGaps(String totalGaps) {
    this.totalGaps = totalGaps;
  }

  public String getPercentGaps() {
    return percentGaps;
  }

  public void setPercentGaps(String percentGaps) {
    this.percentGaps = percentGaps;
  }

  public String getMistmatches() {
    return mistmatches;
  }

  public void setMistmatches(String mistmatches) {
    this.mistmatches = mistmatches;
  }

  public String getLCA() {
    return LCA;
  }

  public void setLCA(String LCA) {
    this.LCA = LCA;
  }

  public String getRankLCA() {
    return rankLCA;
  }

  public void setRankLCA(String rankLCA) {
    this.rankLCA = rankLCA;
  }

  public String getOriginJobName() {
    return originJobName;
  }

  public void setOriginJobName(String originJobName) {
    this.originJobName = originJobName;
  }

  public String getOriginJobId() {
    return originJobId;
  }

  public void setOriginJobId(String originJobId) {
    this.originJobId = originJobId;
  }

  public void setHitClassification(SRClassification classification) {
    this.hClassification = classification;
  }

  public SRClassification getHitClassification() {
    return hClassification;
  }
  
  public void setHitClassificationForView(List<SJTermSummary> terms) {
    mainHitTermsForView = terms;
  }
  
  public List<SJTermSummary> getHitClassificationForView(){
    return mainHitTermsForView;
  }

  public void setQueryClassification(SRClassification classification) {
    this.qClassification = classification;
  }

  public SRClassification getQueryClassification() {
    return qClassification;
  }
  
  public void setQueryClassificationForView(List<SJTermSummary> terms) {
    mainQueryTermsForView = terms;
  }
  
  public List<SJTermSummary> getQueryClassificationForView(){
    return mainQueryTermsForView;
  }

  /**
   * Returned a filtered list of SRTermSummary.
   * 
   * @param types list of String representation of AnnotationDataModelConstants.ANNOTATION_CATEGORY values.
   * We use that design to enable sub-filtering of GO terms. Indeed, in addition to ANNOTATION_CATEGORY.GO, one
   * can use the following strings: GOC, GOP, GOF. In this parameter is null full list of SRTermSummary
   * is returned.
   * */
  public List<SJTermSummary> getHitClassificationForView(List<String> types){
    if (types==null || mainHitTermsForView==null) {
      return mainHitTermsForView;
    }
    ArrayList<SJTermSummary> newList;
    String type;
    newList = new ArrayList<>();
    for(SJTermSummary term : mainHitTermsForView) {
      type = term.getViewType();
      if (types.contains(type)) {
        newList.add(term);
      }
    }
    return newList;
  }

  /**
   * Returned a filtered list of SRTermSummary.
   * 
   * @param types list of String representation of AnnotationDataModelConstants.ANNOTATION_CATEGORY values.
   * We use that design to enable sub-filtering of GO terms. Indeed, in addition to ANNOTATION_CATEGORY.GO, one
   * can use the following strings: GOC, GOP, GOF. In this parameter is null full list of SRTermSummary
   * is returned.
   * */
  public List<SJTermSummary> getQueryClassificationForView(List<String> types){
    if (types==null || mainQueryTermsForView==null) {
      return mainQueryTermsForView;
    }
    ArrayList<SJTermSummary> newList;
    String type;
    newList = new ArrayList<>();
    for(SJTermSummary term : mainQueryTermsForView) {
      type = term.getViewType();
      if (types.contains(type)) {
        newList.add(term);
      }
    }
    return newList;
  }

  public String toString() {
    StringBuffer szBuf;

    szBuf = new StringBuffer();
    szBuf.append("  Nb hits: ");
    szBuf.append(getNHits());
    szBuf.append("\n Accesion: ");
    szBuf.append(getBestHitAccession());
    szBuf.append("\n Description: ");
    szBuf.append(getBestHitDescription());
    szBuf.append("\n Length: ");
    szBuf.append(getBestHitLength());
    szBuf.append("\n E-Value: ");
    szBuf.append(getBestHitEValue());
    return szBuf.toString();
  }

}
