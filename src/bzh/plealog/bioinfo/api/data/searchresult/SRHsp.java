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
package bzh.plealog.bioinfo.api.data.searchresult;

import java.io.Serializable;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;

/**
 * This interface defines a Blast HSP.
 * 
 * @author Patrick G. Durand
 */
public interface SRHsp extends Serializable{
	//  *** IMPORTANT NOTICE ***
	//when modifying this API, also modify 
	//filter.implem.datamodel.BGDataModel#initialize()
	/**
	 * Returns the index number of this HSP.
	 */
	public int getHspNum();
	public void setHspNum(int num);
	
	/**
	 * Returns the scores associated to this HSP.
	 */
    public SRHspScore getScores();
    public void setScores(SRHspScore score);

    /**
     * Returns the aligned query sequence.
     */
    public SRHspSequence getQuery();
    public void setQuery(SRHspSequence seq);

    /**
     * Returns the aligned hit sequence.
     */
    public SRHspSequence getHit();
    public void setHit(SRHspSequence seq);

    /**
     * Returns the central line of the pairwise alignment.
     */
    public SRHspSequence getMidline();
    public void setMidline(SRHspSequence seq);

    /**
     * Returns the pattern associated to this HSP. Phi-Blast only.
     */
    public SRHspPattern  getPattern();
    public void setPattern(SRHspPattern pat);

    /**
     * Figures out if this HSP contains proteic sequences.
     */
    public boolean isProteic();
    public void setProteic(boolean proteic);

    /**
     * Sets a FeatureTable on this HSP. If the FeatureTable contains data spanning outside HSP's hit
     * sequence coordinates, please consider using the cut(from, to) method of FeatureTable.
     */
    public void setFeatures(FeatureTable ft);
    /**
     * Returns the FeatureTable associated to the hit sequence. 
     */
    public FeatureTable getFeatures();
    
    //added for release 2.5+ to set query and hit sequence full size within HSP
    //so that coverage % can be used in the Filter System
    /**
     * Returns the query coverage.
     */
    public double getQueryCoverage();
    /**
     * Returns the hit coverage.
     */
    public double getHitCoverage();
    public void setQueryCoverage(double coverage);
    public void setHitCoverage(double coverage);
    /**
     * Forces the implementation of a clone method.
     */
    public SRHsp clone(boolean shallow);
}
