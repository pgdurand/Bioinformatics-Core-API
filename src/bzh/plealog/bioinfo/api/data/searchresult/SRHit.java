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

import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;


/**
 * This interface defines a Blast Hit.
 * 
 * @author Patrick G. Durand
 */
public interface SRHit extends Serializable{
	//  *** IMPORTANT NOTICE ***
	//when modifying this API, also modify 
	//filter.implem.datamodel.BGDataModel#initialize()
    /**
     * Returns the accession number of the database sequence aligned with the query.
     */
	public String getHitAccession();
	public void setHitAccession(String acc);
    /**
     * Returns the definition line of the database sequence aligned with the query.
     */
    public String getHitDef();
    public void setHitDef(String def);
    /**
     * Returns the unique ID of the database sequence aligned with the query.
     */
    public String getHitId();
    public void setHitId(String id);
    /**
     * Returns the length of the database sequence aligned with the query.
     */
    public int getHitLen();
    public void setHitLen(int len);
    /**
     * Returns the hit position within the hits list contained in a Blast result.
     */
    public int getHitNum();
    public void setHitNum(int num);
    
    /**
     * Adds a BHsp to this BHit.
     */
    public void addHsp(SRHsp hsp);
    
    /**
     * Returns a enumeration over the HSPs contained in this hit.
     */
    public Enumeration<SRHsp> enumerateHsp();
    /**
     * Returns a a particular HSP contained in this hit.
     */
    public SRHsp getHsp(int index);
    /**
     * Returns the number of HSPs contained in this hit.
     */
    public int countHsp();

    public List<SRHsp> getHsps();

    /**
     * Sets a SequenceInfo to this Hit.
     */
    public void setSequenceInfo(BankSequenceInfo si);
    /**
     * Returns the SequenceInfo associated to this hit.
     */
    public BankSequenceInfo getSequenceInfo();
    
    /**
     * Forces the implemantion of a clone method.
     */
    public SRHit clone(boolean shallow);
    
    public double getQueryGlobalCoverage();
    public double getHitGlobalCoverage();
    public void setQueryGlobalCoverage(double coverage);
    public void setHitGlobalCoverage(double coverage);

}
