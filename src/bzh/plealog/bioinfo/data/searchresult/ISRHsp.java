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
package bzh.plealog.bioinfo.data.searchresult;

import com.plealog.genericapp.api.log.EZLogger;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspPattern;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspScore;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;

/**
 * This is a default implementation of interface BHsp
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRHsp
 */
public class ISRHsp implements SRHsp{
	private static final long serialVersionUID = -8990654710285151070L;
	/**
	 * @serial
	 */
	private int          hspNum;
	/**
	 * @serial
	 */
    private SRHspScore    score;
	/**
	 * @serial
	 */
    private SRHspSequence querySequence;
	/**
	 * @serial
	 */
    private SRHspSequence hitSequence;
	/**
	 * @serial
	 */
    private SRHspSequence midlineSequence;
	/**
	 * @serial
	 */
    private boolean      isProteic;
	/**
	 * @serial
	 */
    private double       queryCoverage;
	/**
	 * @serial
	 */
    private double       hitCoverage;

    private transient SRHspPattern  pattern;
    
    public ISRHsp(){}
    
	public SRHsp clone(boolean shallow){
        ISRHsp hsp = new ISRHsp();
        hsp.copy(this, shallow);
        return hsp;
    }
    
    public void copy(ISRHsp src, boolean shallow){
    	SRHspSequence seq;
    	
    	this.setHspNum(src.getHspNum());
        this.setScores(src.getScores().clone());
        seq = src.getQuery();
        if (seq!=null)
        	this.setQuery(seq.clone(shallow));
        else
        	this.setQuery(null);
        seq = src.getHit();
        if (seq!=null)
        	this.setHit(seq.clone(shallow));
        else this.setHit(null);
        seq = src.getMidline();
        if (seq!=null)
        	this.setMidline(seq.clone(shallow));
        else
        	this.setMidline(null);
        this.setPattern(src.getPattern());
        this.setProteic(src.isProteic());
        // added for no sequence boutput management
        this.setQueryCoverage(src.getQueryCoverage());
        this.setHitCoverage(src.getHitCoverage());
    }
    public int getHspNum(){return hspNum;}
    public void setHspNum(int val){hspNum=val;}
    
    public SRHspScore getScores(){return score;}
    public void setScores(SRHspScore s){score=s;}

    public SRHspSequence getQuery(){return querySequence;}
    public void setQuery(SRHspSequence q){
    	querySequence=q;
    }

    public SRHspSequence getHit(){return hitSequence;}
    public void setHit(SRHspSequence q){
    	hitSequence=q;
    }
    
	public SRHspSequence getMidline() {return midlineSequence;}
	public void setMidline(SRHspSequence mid) {midlineSequence = mid;}

	public SRHspPattern getPattern(){return pattern;}
    public void setPattern(SRHspPattern val){pattern=val;}

    public boolean isProteic(){return isProteic;}
    public void setProteic(boolean val){isProteic=val;}

    public FeatureTable getFeatures(){
        //Starting with release 2.5: Features stored in the Hit sequence
    	return hitSequence!=null?hitSequence.getFeatures():null;
    }
    public void setFeatures(FeatureTable ft){
        //Starting with release 2.5: Features stored in the Hit sequence
    	if(hitSequence!=null){
    		hitSequence.setFeatures(ft);
    		if (ft!=null && hitSequence.getFeatures()==null){//check if FeatureTable was invalid
    		  EZLogger.warn("FeatureTable discarded on HSP "+hspNum);
    		}
    	}
    }

    public double getQueryCoverage(){
   		return queryCoverage;
    }
    public double getHitCoverage(){
   		return hitCoverage;
    }
    public void setQueryCoverage(double coverage){
    	queryCoverage = coverage;
    }
    public void setHitCoverage(double coverage){
    	hitCoverage = coverage;
    }

}
