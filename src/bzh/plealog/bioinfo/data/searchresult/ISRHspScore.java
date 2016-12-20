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

import bzh.plealog.bioinfo.api.data.searchresult.SRHspScore;

/**
 * This is a default implementation of interface BHspScore.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRHspScore
 */
public class ISRHspScore implements SRHspScore{
	private static final long serialVersionUID = 716970455226540554L;
	/**
	 * @serial
	 */
	private double       bitScore;
	/**
	 * @serial
	 */
    private double       score;
	/**
	 * @serial
	 */
    private double       evalue;
	/**
	 * @serial
	 */
    private int          identity;
	/**
	 * @serial
	 */
    private int          positive;
	/**
	 * @serial
	 */
    private int          gaps;
	/**
	 * @serial
	 */
    private int          alignLen;
	/**
	 * @serial
	 */
    private int          density;

    protected int          mismatches;

    public ISRHspScore(){}
    
	public SRHspScore clone(){
        ISRHspScore hsp = new ISRHspScore();
        hsp.copy(this);
        return hsp;
    }
    
    public void copy(ISRHspScore src){
        this.setBitScore(src.getBitScore());
        this.setScore(src.getScore());
        this.setEvalue(src.getEvalue());
        this.setIdentity(src.getIdentity());
        this.setPositive(src.getPositive());
        this.setGaps(src.getGaps());
        this.setAlignLen(src.getAlignLen());
        this.setDensity(src.getDensity());
    }
    
    public double getBitScore(){return bitScore;}
    public void setBitScore(double val){bitScore=val;}

    public double getScore(){return score;}
    public void setScore(double val){score=val;}

    public double getEvalue(){return evalue;}
    public void setEvalue(double val){evalue=val;}

    public int getIdentity(){return identity;}
    public double getIdentityP(){return 100.0*(double)identity/(double)alignLen;};
    public void setIdentity(int val){identity=val;}

    public int getPositive(){return positive;}
    public double getPositiveP(){return 100.0*(double)positive/(double)alignLen;};
    public void setPositive(int val){positive=val;}

    public int getGaps(){return gaps;}
    public double getGapsP(){return 100.0*(double)gaps/(double)alignLen;};
    public void setGaps(int val){gaps=val;}

    public int getAlignLen(){return alignLen;}
    public void setAlignLen(int val){alignLen=val;}
    
    public int getDensity(){return density;}
    public void setDensity(int val){density=val;}

    public int getMismatches(){return mismatches;}
    public void setMismatches(int val){mismatches=val;}

}
