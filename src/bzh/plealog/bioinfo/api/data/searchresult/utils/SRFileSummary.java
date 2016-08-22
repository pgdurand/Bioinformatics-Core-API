/* Copyright (C) 2006-2016 Patrick G. Durand
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
package bzh.plealog.bioinfo.api.data.searchresult.utils;

import java.io.Serializable;

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspScore;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;
import bzh.plealog.bioinfo.util.CoreUtil;

/**
 * This class contains the summary of a BLAST result. Such a summary reports
 * the information related to the best hit of that result.
 * 
 * @author Patrick G. Durand
 */
public class SRFileSummary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7467266811057328060L;
	private int     nHits;
    private String  bestHitAccession;
    private String  bestHitDescription;
    private String  bestHitLength;
    private String  bestHitEValue;
    private String  bestHitIdentify;
    private String  bestHitSimilarity;
    private String  bestHitCoverage;//Query Coverage
    private String  bestHitScore;
    private String  bestHitScoreBits;
    private String  bestHitCoverageH;//Hit Coverage, added Oct 2008
    private String  taxonomy;//added April 2009
    private String  organism;//added April 2009
    private String  queryLength;//added Sept 2009
    private String  queryFrom;//added Sept 2009
    private String  queryTo;//added Sept 2009
    private String  queryFrame;//added Sept 2009
    private String  queryGaps;//added Sept 2009
    private String  bestHitFrom;//added Sept 2009
    private String  bestHitTo;//added Sept 2009
    private String  bestHitFrame;//added Sept 2009
    private String  bestHitGaps;//added Sept 2009
    private String  alignLength;//added Sept 2009
    private String  nbHsps;//added Sept 2009
    private String  queryId;//added Nov 2010
    private String  queryDescription;//added Nov 2010
    private String  execMsg;//added Mar 2011
    private String  queryRID;//added Oct 2011
    private SROutput.FEATURES_CONTAINER featContainer;//added Mar 2011
    private boolean filtered;
    
    private transient boolean _initialized;
    //Adding Fields is allowed. Then also update BFileSummaryIO and BlastIOUtils.
    //Removing fields is totally not allowed for compatibility issues
        
    public static final String UNKNOWN = "-";
    public static final String NOT_APPLICABLE = "none";
    
    public static final int HEADER_SIZE = 128;
    
    /**
     * Default constructor.
     */
    public SRFileSummary(){
    	reset(true);
        setBestHitAccession(UNKNOWN);
        setBestHitDescription(UNKNOWN);
    }
	
    /**
     * Creates a BFileSummary from a BOutput.
     */
    public SRFileSummary(SROutput output){
        this();
        initialize(output);
    }
    public void reset(boolean updateQuery){
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
        if (updateQuery){
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
    }
    /**
     * Initializes this BFileSummary from a BOutput.*/
    public void initialize(SROutput output){
    	BankSequenceInfo  si;
    	SRIteration    iterHits;
        SRHit          hit;
        SRHsp          hsp;
        SRHspScore     scores;
        SRHspSequence  seq;
        double        eval;
        
        if (output.countIteration()==0){
        	reset(false);
            return;
        }
        
        Object o = output.getRequestInfo().getValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY);
        setQueryLength(o!=null?o.toString():UNKNOWN);

        iterHits = output.getIteration(0);
        if (iterHits.countHit()==0){
        	reset(false);
            return;
        }
        
        hit = iterHits.getHit(0);
        nHits = iterHits.countHit();
        setBestHitAccession(hit.getHitAccession());
        
        String definition = hit.getHitDef();
		int    idx = definition.indexOf("[[");
		if(idx!=-1){
			definition = definition.substring(0, idx);
		}
		if (definition.length()>HEADER_SIZE)
			definition = definition.substring(0, HEADER_SIZE);
		
        setBestHitDescription(definition);
        setBestHitLength(String.valueOf(hit.getHitLen()));
        hsp = hit.getHsp(0);
        scores = hsp.getScores();
        eval = scores.getEvalue();
        if (eval>0 && eval<0.1)
            setBestHitEValue(CoreUtil.EVALUE_FORMATTER1.format(eval));
        else
            setBestHitEValue(CoreUtil.EVALUE_FORMATTER2.format(eval));
        eval = scores.getScore();
        if (eval>0 && eval<0.1)
        	setBestHitScore(CoreUtil.EVALUE_FORMATTER1.format(eval));
        else
        	setBestHitScore(CoreUtil.EVALUE_FORMATTER2.format(eval));
        setBestHitScoreBits(CoreUtil.SCORE_FORMATTER.format(scores.getBitScore()));
        //setBestHitScoreBits(EVALUE_FORMATTER2.format(scores.getBitScore()));
        setBestHitIdentify(CoreUtil.PCT_FORMATTER.format(scores.getIdentityP())+"%");
        setBestHitSimilarity(CoreUtil.PCT_FORMATTER.format(scores.getPositiveP())+"%");
        setBestHitCoverage(CoreUtil.PCT_FORMATTER.format(hit.getQueryGlobalCoverage())+"%");
        setBestHitCoverageH(CoreUtil.PCT_FORMATTER.format(hit.getHitGlobalCoverage())+"%");
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
    	if (si!=null){
    		if (si.getOrganism()!=null)
        		organism = si.getOrganism();
    		if (si.getTaxonomy()!=null)
        		taxonomy = si.getTaxonomy();
    	}
        _initialized=true;
    }
    /**
     * Figures out if this BFileSummary has been initialized.
     */
	public boolean isInitialized() {
		return _initialized;
	}
	/**
	 * Sets the initial status of this BFileSummary.
	 */
	public void setInitialized(boolean initialized) {
		_initialized = initialized;
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

	public String toString(){
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
