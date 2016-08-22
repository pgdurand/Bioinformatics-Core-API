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
import bzh.plealog.bioinfo.api.data.sequence.DSequence;

/**
 * This interface defines a sequence constituing a Blast HSP.
 * 
 * @author Patrick G. Durand
 */
public interface SRHspSequence extends Serializable{
    public static final int TYPE_ALIGNED_SEQ = 1;
    public static final int TYPE_MIDLINE     = 2;
	
	//  *** IMPORTANT NOTICE ***
	//when modifying this API, also modify 
	//filter.implem.datamodel.BGDataModel#initialize()
	/**
	 * Returns the sequence type.
	 * 
	 * @return one of the TYPE_XXX values defined in this interface.
	 */
	public int getType();
	public void setType(int val);

    /**
     * Returns the starting position. Sequence coordinate.
     */
	public int getFrom();
	public void setFrom(int val);

    /**
     * Returns the ending position. Sequence coordinate.
     */
	public int getTo();
    public void setTo(int val);

    /**
     * Returns the number of gaps.
     */
    public int getGaps();
    public void setGaps(int val);

    /**
     * Returns the frame.
     */
    public int getFrame();
    public void setFrame(int val);

    /**
     * Returns the full size of the sequence.
     */
	public int getSeqFullSize();
	public void setSeqFullSize(int val);

    /**
     * Returns the sequence as a String.
     */
    public String getSequence();
    public void setSequence(String seq);
    /**
     * Returns the sequence as a DSequence.
     */
    public DSequence getSequence(SRHsp hsp);

    public void setFeatures(FeatureTable ft);
    /**
     * Returns the FeatureTable associated to the hit sequence. Can be null if this
     * sequence is not the hit sequence and/or no features are available.
     */
    public FeatureTable getFeatures();

    /**
     * Forces the implementation of a clone method.
     */
    public SRHspSequence clone(boolean shallow);
}
