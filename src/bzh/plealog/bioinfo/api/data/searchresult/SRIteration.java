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
import java.util.Enumeration;
import java.util.List;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceException;

/**
 * This interface defines a BLAST Iteration.
 * 
 * @author Patrick G. Durand
 */
public interface SRIteration extends Serializable{
	/**
	 * Returns the index number of this iteration.
	 */
    public int getIterationIterNum();
    public void setIterationIterNum(int num);

    /**
     * Returns the message associated to this iteration.
     */
    public String getIterationMessage();
    public void setIterationMessage(String msg);

    /**
     * Returns the SRStatistics object associated to this iteration.
     */
    public SRStatistics getIterationStat();
    public void setIterationStat(SRStatistics stat);

    /**
     * Returns the query sequence ID to which belongs this iteration.
     */
    public String getIterationQueryID();
    public void setIterationQueryID(String id);
    
    /**
     * Returns the description line of the query sequence to which belongs this iteration.
     */
    public String getIterationQueryDesc();
    public void setIterationQueryDesc(String desc);
    
    /**
     * Returns the size of the query sequence to which belongs this iteration.
     */
    public int getIterationQueryLength();
    public void setIterationQueryLength(int len);

    /**
     * Returns the FeatureTable of the query sequence to which belongs this iteration.
     */
    public FeatureTable getIterationQueryFeatureTable();
    public void setIterationQueryFeatureTable(FeatureTable ft);

    public void addHit(SRHit hit);
    
    /**
     * Returns an enumeration over the SRHits contained in this iteration.
     */
    public Enumeration<SRHit> enumerateHit();
    /**
     * Returns a particular BHit.
     */
    public SRHit getHit(int index);
    /**
     * Returns the number of hits contained in this iteration.
     */
    public int countHit();
    
    /**
     * Returns a List of SRHits.
     */
    public List<SRHit> getHits();
    
    /**
     * Returns a Multiple Sequence Alignment from the set of HSPs contained in this
     * BIteration.
     * 
     * @param querySize the size of the query sequence
     * @param blastType the blast type (one of BOutput constants)
     */
    public DSequenceAlignment getMultipleSequenceAlignment(int querySize, int blastType) throws DSequenceException;
    
    /**
     * Forces the implementation of a clone method.
     */
    public SRIteration clone(boolean shallow);
}
