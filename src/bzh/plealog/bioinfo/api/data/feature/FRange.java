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
package bzh.plealog.bioinfo.api.data.feature;

import java.io.Serializable;

/**
 * Defines a range of values that made the basic elements of a FeatureLocation.
 * 
 * @author Patrick G. Durand
 */
public class FRange implements Serializable {
	private static final long serialVersionUID = 4758980171206128200L;
	/**
	 * @serial
	 */
	private FPosition from;
	/**
	 * @serial
	 */
	private FPosition to;
	/**
	 * @serial
	 */
	private String    dbXref;
	
	public FRange(){}
	
	/**
	 * Constructor with a pair of positions. Values are one-based.
	 */
	public FRange(FPosition from, FPosition to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Constructor with a pair of positions located on another sequence.  Values from
	 * and to are one-based.
	 */
	public FRange(FPosition from, FPosition to, String dbXref) {
		super();
		this.from = from;
		this.to = to;
		this.dbXref = dbXref;
	}
	/**
	 * Returns the start position of this range. Value is one-based.
	 */
	public FPosition getFrom() {
		return from;
	}
	/**
	 * Returns the end position of this range. Value is one-based.
	 */
	public FPosition getTo() {
		return to;
	}
	/**
	 * Sets the start position of this range. Value is one-based.
	 */
	public void setFrom(FPosition from) {
		this.from = from;
	}
	/**
	 * Sets the end position of this range. Value is one-based.
	 */
	public void setTo(FPosition to) {
		this.to = to;
	}
	/**
	 * Returns the sequence ID to which belongs a compound range. Please note that dbXRef
	 * is only used when a complex feature location spans several sequences.
	 */
	public String getDbXref() {
		return dbXref;
	}

	/**
	 * Sets the sequence ID to which belongs a compound range. Please note that dbXRef
	 * is only used when a complex feature location spans several sequences.
	 */
	public void setDbXref(String dbXref) {
		this.dbXref = dbXref;
	}

    /**
     * Forces the implementation of a clone method.
     */
	public Object clone(){
		FRange range = new FRange();
		range.copy(this);
		return range;
	}
	/**
	 * Figures out if this FRange corresponds to a single location. Returns true only if from equals to end.
	 */
	public boolean isUniquePosition(){
		if (getFrom().equals(getTo()))
			return true;
		else
			return false;
	}
	/**
	 * Figures out if this FRange corresponds to a simple range.
	 */
	public boolean isSimpleRange(){
		if (getFrom().getType().equals(FPosition.UNIQUE)&&getTo().getType().equals(FPosition.UNIQUE)
				&& getFrom().getStart()!=getTo().getStart())
			return true;
		else
			return false;
	}
	/**
	 * Cut this FRange given a new range of sequence coordinates.
	 */
	public FRange cut(int start, int stop){
		FRange    fr;
		FPosition fp1, fp2;
		
		//FRange with a dbXref are not concern by the cut operation
		if (this.getDbXref()!=null)
			return (FRange) this.clone();
		
		fp1 = from.cut(start, stop);
		fp2 = to.cut(start, stop);
		//from is outside range
		if (fp1==null){
			fp1 = new FPosition(start);
			fp1.setSpanningStart(true);
		}
		//to is outside range
		if (fp2==null){
			fp2 = new FPosition(stop);
			fp2.setSpanningEnd(true);
		}
		fr = new FRange(fp1, fp2, this.getDbXref());
		return fr;
	}
	public String toString(){
		StringBuffer buf;
		
		buf = new StringBuffer();
		if (dbXref!=null){
			buf.append(dbXref);
			buf.append(":");
		}
		if (isUniquePosition())
			buf.append(getFrom().toString());
		else
			buf.append(getFrom().toString()+".."+getTo().toString());
		return buf.toString();
	}
	public void copy(FRange src){
		this.setFrom((FPosition) src.getFrom().clone());
		this.setTo((FPosition) src.getTo().clone());
		this.setDbXref(src.getDbXref());
	}
}
