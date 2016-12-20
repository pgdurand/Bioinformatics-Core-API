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

/**
 * This interface defines the scores associated to a Blast HSP.
 * 
 * @author Patrick G. Durand
 */
public interface SRHspScore extends Serializable{
    
	//  *** IMPORTANT NOTICE ***
	//when modifying this API, also modify 
	//filter.implem.datamodel.BGDataModel#initialize()
	/**
	 * Returns the bit score.
	 */
	public double getBitScore();
	public void setBitScore(double val);

	/**
	 * Returns the score.
	 */
	public double getScore();
    public void setScore(double val);

	/**
	 * Returns the evalue.
	 */
    public double getEvalue();
    public void setEvalue(double val);

	/**
	 * Returns the number of identities.
	 */
    public int getIdentity();
	/**
	 * Returns the identity percent.
	 */
    public double getIdentityP();
    public void setIdentity(int val);

	/**
	 * Returns the number of positives.
	 */
    public int getPositive();
	/**
	 * Returns the positive percent.
	 */
    public double getPositiveP();
    public void setPositive(int val);

	/**
	 * Returns the number of gaps.
	 */
    public int getGaps();
	/**
	 * Returns the gap percent.
	 */
    public double getGapsP();
    public void setGaps(int val);

	/**
	 * Returns the alignment size.
	 */
    public int getAlignLen();
    public void setAlignLen(int val);

    public int getDensity();
    public void setDensity(int val);

    public int getMismatches();
    public void setMismatches(int val);

    /**
     * Forces the implementation of a clone method.
     */
    public SRHspScore clone();
}
