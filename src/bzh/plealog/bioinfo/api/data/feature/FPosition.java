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
 * Defines a position. This class can model the three kind of positions defined in
 * the official NCBI/EBI/DDBJ Feature Table specification document: unique position,
 * between bases position and in range position.<br><br> 
 * Note: FPosition alone has no real meaning. They have to be combined by pair
 * to form a FRange which represents a real feature location. Among others, this is
 * the reason why dbXref is assigned to a FRange.
 * 
 * @author Patrick G. Durand
 */
public class FPosition implements Serializable {
	private static final long serialVersionUID = 4648821054637524512L;
	/**
	 * @serial
	 */
	private boolean sps;
	/**
	 * @serial
	 */
    private boolean spe;
	/**
	 * @serial
	 */
    private int     s;
	/**
	 * @serial
	 */
    private int     e;
	/**
	 * @serial
	 */
    private String  t = UNIQUE;

    /**
     * The symbol representing a position that falls in between two bases
     * as described in NCBI/EBI/DDBJ Feature Table.
     */
    public static final String BETWEEN_BASES = "^";
    
    /**
     * The symbol representing a position that occupies a single base somewhere
     * in a range as described in NCBI/EBI/DDBJ Feature Table.
     */
    public static final String IN_RANGE = ".";

    /**
     * A position that is really a position. Start equals End.
     */
    public static final String UNIQUE = "s";
    
    /**
     * Constructor only available for cloning and serialization. Do not use.
     */
    public FPosition(){}
    
    /**
     * Constructs a unique position where start equals end. Value is one-based.
     * @param p the point position
     */
    public FPosition(int p) {
        this(false,false,p);
    }
    
    /**
     * Constructs a range position where start is different than end.
     * @param s the start position. Value is one-based.
     * @param e the end position. Value is one-based.
     */
    public FPosition(int s, int e) {
        this(false,false,s,e,null);
    }
    
    /**
     * Constructs a position where start equals end, with optionally fuzzy start and
     * end.
     * @param fs pass in true to specify spanning start
     * @param fe pass in true to specify a spanning end
     * @param p the point position. Value is one-based.
     */
    public FPosition(boolean fs, boolean fe, int p) {
        this(fs,fe,p,p,null);
    }
    
    /**
     * Constructs a range position, with optionally fuzzy start and
     * end. 
     * @param fs pass in true to specify spanning start
     * @param fe pass in true to specify a spanning end
     * @param s the start of the position. Value is one-based.
     * @param e the end of the position. Value is one-based.
     * @param t the type of the position. Use one of the constants 
     * BETWEEN_BASES, IN_RANGE or UNIQUE defined in this class.
     */
    public FPosition(boolean fs, boolean fe, int s, int e, String t) {
        this.sps = fs;
        this.spe = fe;
        this.s = s;
        this.e = e;
        this.t = t;
    }
    /**
     * Figures out if the start of the position is not known.
     */
    public boolean getSpanningStart() {
    	return this.sps;
    }
    
    /**
     * Figures out if the end of the position is not known.
     */
    public boolean getSpanningEnd() {
    	return this.spe;
    }
    
    /**
     * Returns the start of this position. Value is one-based.
     */
    public int getStart() {
    	return this.s;
    }
    
    /**
     * Returns the end of this position. Value is one-based.
     */
    public int getEnd()  {
    	return this.e;
    }
    
    /**
     * Returns the type of this position. One of the constants 
     * BETWEEN_BASES, IN_RANGE or UNIQUE defined in this class.
     */
    public String getType() {
    	return this.t;
    }

    /**
     * Specify whether or not the start is fuzzy.
     */
    public void setSpanningStart(boolean fs) {
    	this.sps = fs;
    }
    
    /**
     * Specify whether or not the end is fuzzy.
     */
    public void setSpanningEnd(boolean fe) {
    	this.spe = fe;
    }
    
    /**
     * Sets the start of this position. Value is one-based.
     */
    public void setStart(int s) {
    	this.s = s;
    }
    
    /**
     * Sets the end of this position. Value is one-based.
     */
    public void setEnd(int e) {
    	this.e = e;
    }
    
    /**
     * Sets the type of this position. Use one of the constants 
     * BETWEEN_BASES, IN_RANGE or UNIQUE defined in this class.
     */
    public void setType(String t) {
    	this.t = t;
    }

    public boolean equals(Object o) {
        if (!(o instanceof FPosition)) return false;
        if (o==this) return true;
        FPosition them = (FPosition)o;
        if (this.getSpanningStart() != them.getSpanningStart()) return false;
        if (this.getSpanningEnd() != them.getSpanningEnd()) return false;
        if (this.getStart()!=them.getStart()) return false;
        if (this.getEnd()!=them.getEnd()) return false;
        if (this.getType()!=null || them.getType()!=null) {
            if (this.getType()!=null && them.getType()!=null) {
                if (!this.getType().equals(them.getType())) return false;
            }
            else return false;
        }
        return true;
    }  
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (s!=e) {
            if (this.getSpanningStart()) sb.append("<");
            sb.append(this.s);
            sb.append(this.t);
            if (this.getSpanningEnd()) sb.append(">");
            sb.append(this.e);
        }
        else{
        	//for a single base position spanning cannot be set to true
        	//on both side. 
            if (this.getSpanningStart()) sb.append("<");
            if (this.getSpanningEnd()) {
            	if (!this.getSpanningStart())
            		sb.append(">");
            }
            sb.append(this.s);
        }
        return sb.toString();
    }
	/**
	 * Cut this FPosition given a new range of sequence coordinates.
	 */
    public FPosition cut(int start, int stop){
    	FPosition fp;
    	
    	//current position is outside from,to: return null
    	if (e<start || s>stop){
    		return null;
    	}
    	fp = new FPosition();
    	fp.copy(this);
    	if (s<=start){
    		fp.setStart(start);
    		if (s<start)
    			fp.setSpanningStart(true);
    	}
    	if (e>=stop){
    		fp.setEnd(stop);
    		if (e>stop)
    			fp.setSpanningEnd(true);
    	}
    	return fp;
    }
    /**
     * Forces the implementation of a clone method.
     */
    public Object clone(){
    	FPosition fp = new FPosition();
    	fp.copy(this);
    	return fp;
    }

    public void copy(FPosition src){
    	this.setStart(src.getStart());
    	this.setEnd(src.getEnd());
    	this.setSpanningStart(src.getSpanningStart());
    	this.setSpanningEnd(src.getSpanningEnd());
    	this.setType(src.getType());
    }
}
