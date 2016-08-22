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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A FeatureLocation is used to describe a feature position that cannot be represented using
 * a single pair of from and to values. This class is intended to handle locations as described in
 * the official NCBI/EBI/DDBJ Feature Table specification document.
 * 
 * @author Patrick G. Durand
 */
public class FeatureLocation implements Serializable {
	private static final long serialVersionUID = 8577348322193250245L;
	/**
	 * @serial
	 */
	private ArrayList<FRange> elements;
	/**
	 * @serial
	 */
	private String    type = OTHER;
	/**
	 * @serial
	 */
	private int       strand;
	
	private transient int    start_ = -1;
	private transient int    end_ = -1;
	private transient String strRepr_;
	/**This type of FeatureLocation is intended to model a join.
	 **/
	public static final String JOIN = "join";
	/**This type of FeatureLocation is intended to model an ordered set of locations.
	 **/
	public static final String ORDER = "order";
	/**This type of FeatureLocation is intended to model a location that is neither
	 * a join nor an ordered set of locations.
	 **/
	public static final String OTHER = "other";
	
	/**This type of FeatureLocation is intended to model a complementary location.
	 **/
	public static final String COMPLEMENT = "complement";
	
	private static final AscentPositionComparator COMPA = new AscentPositionComparator();
	
	/**
	 * Constructor.
	 */
	public FeatureLocation(){
		elements = new ArrayList<FRange>();
	}
	/**
	 * Sets the list of FRange constituting this location.
	 */
	public void setElements(ArrayList<FRange> lst){
		elements = lst;
		computeLimits();
		computeStrRepr();
	}
	/**
	 * Returns the list of FRange constituting this location.
	 */
	public ArrayList<FRange> getElements(){
		return elements;
	}
	/**
	 * Returns the list of FRange constituting this location. FRange are sorted
	 * by ascending order of their positions.
	 */
	public ArrayList<FRange> getAscentSortedElements(){
		if (elements()==1)
			return this.getElements();
		FeatureLocation fLoc = (FeatureLocation)this.clone();
		Collections.sort(fLoc.getElements(), COMPA);
		return fLoc.getElements();
	}
	/**
	 * Returns the number of FRange constituting this location.
	 */
	public int elements(){
		return elements.size();
	}
	/**
	 * Returns a FRange.
	 */
	public FRange getRange(int i){
		return (FRange) elements.get(i);
	}
	/**
	 * Adds a FRange. FRanges have to be added in ascending order of their 
	 * location on the sequence.
	 */
	public void addRange(FRange fp){
		elements.add(fp);
		computeLimits();
		computeStrRepr();
	}
	/**
	 * Gets the type of this location. Returns one of JOIN, ORDER or OTHER constant
	 * defines in this class.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of this location. Use one of JOIN, ORDER or OTHER constant
	 * defines in this class.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the strand. Returns one of the XXX_STRAND constants defined in the Feature interface.
	 */
	public int getStrand() {
		return strand;
	}

	/**
	 * Sets the strand. Use one of the XXX_STRAND constants defined in the Feature interface.
	 */
	public void setStrand(int strand) {
		this.strand = strand;
	}

	/**
	 * Returns the start position of this location.
	 */
	public int getStart(){
		if (start_==-1)
			computeLimits();
		return start_;
	}
	/**
	 * Returns the end position of this location.
	 */
	public int getEnd(){
		if (end_==-1)
			computeLimits();
		return end_;
	}
	private void computeLimits(){
		FRange fr;
		int i, size, val, start = Integer.MAX_VALUE, end = Integer.MIN_VALUE;
		
		size = elements();
		for(i=0;i<size;i++){
			fr = getRange(i);
			//do not tale into account external link 
			if (fr.getDbXref()!=null)
				continue;
			val = Math.min(fr.getFrom().getStart(), fr.getTo().getEnd());
			if (val<start)
				start = val;
			val = Math.max(fr.getFrom().getStart(), fr.getTo().getEnd());
			if (val>end)
				end = val;
		}
		start_ = start;
		end_ = end;
	}
	private void computeStrRepr(){
		StringBuffer buf = new StringBuffer();
		boolean      putLocOperator, putCompOperator;
		int          i, size;
		
		putLocOperator = (!getType().equals(OTHER));
		putCompOperator = (getStrand()==Feature.MINUS_STRAND);
		
		if (putCompOperator){
			buf.append(COMPLEMENT);
			buf.append("(");
		}
		if (putLocOperator){
			buf.append(getType());
			buf.append("(");
		}
		size = elements();
		for(i=0;i<size;i++){
			buf.append(getRange(i).toString());
			if ((i+1)<size)
				buf.append(",");
		}
		if (putLocOperator){
			buf.append(")");
		}
		if (putCompOperator){
			buf.append(")");
		}
		strRepr_ = buf.toString();
	}
	/**
	 * Returns a nice String representation of this location.
	 */
	public String toString(){
		if (strRepr_==null)
			computeStrRepr();
		return strRepr_;
	}
	/**
	 * Cut this location given a new range of sequence coordinates.
	 */
	public FeatureLocation cut(int start, int stop){
		FeatureLocation   fl;
		ArrayList<FRange> tmpE, newE;
		FRange            fr;
		boolean           curSeqRange;
		int               i, size;
		
		//outside range ?
		if (this.getStart()> stop || this.getEnd()<start){
			return null;
		}
		tmpE = new ArrayList<FRange>();
		size = this.elements();
		//step 1 : keep all range within start-stop
		for(i=0;i<size;i++){
			fr = this.getRange(i);
			if (fr.getDbXref()!=null){
				tmpE.add(fr);
			}
			else if (fr.getTo().getEnd()>=start && fr.getFrom().getStart()<=stop){
				tmpE.add(fr);
			}
		}
		if (tmpE.isEmpty())
			return null;
		//step2: adjust range
		newE = new ArrayList<FRange>();
		size = tmpE.size();
		curSeqRange = false;
		for(i=0;i<size;i++){
			fr = (FRange) tmpE.get(i);
			fr = fr.cut(start, stop);
			if (fr!=null){
				newE.add(fr);
				if (fr.getDbXref()==null){
					curSeqRange = true;
				}
			}
		}
		if (newE.isEmpty() || curSeqRange==false)
			return null;
		fl = new FeatureLocation();
		fl.setStrand(this.getStrand());
		if (newE.size()==1)
			fl.setType(FeatureLocation.OTHER);
		else
			fl.setType(this.getType());
		fl.setElements(newE);
		return fl;
	}
    /**
     * Forces the implementation of a clone method.
     */
	public Object clone(){
		FeatureLocation fl = new FeatureLocation();
		fl.copy(this);
		return fl;
	}
	public void copy(FeatureLocation src){
		ArrayList<FRange> newElements;
		int               i, size;
		
		this.setType(src.getType());
		this.setStrand(src.getStrand());
		size = src.elements();
		newElements = new ArrayList<FRange>();
		for(i=0;i<size;i++){
			newElements.add((FRange) src.getRange(i).clone());
		}
		this.setElements(newElements);
	}
    protected static class AscentPositionComparator implements Comparator<FRange> {
        public int compare(FRange o1, FRange o2) {
            if (o1==null || o2==null)
                return 0;
            if (o1.getFrom().getStart() < o2.getFrom().getStart())
                return -1;
            else if (o1.getFrom().getStart() > o2.getFrom().getStart())
                return 1;
            else
                return 0;
        }
    }

}
