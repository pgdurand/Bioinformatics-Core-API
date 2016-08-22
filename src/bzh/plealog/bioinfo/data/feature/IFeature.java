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
package bzh.plealog.bioinfo.data.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import bzh.plealog.bioinfo.api.data.feature.FRange;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureLocation;
import bzh.plealog.bioinfo.api.data.feature.Qualifier;
import bzh.plealog.bioinfo.api.data.feature.utils.FeatureSystem;

/**
 * This class is a default implementation of a feature. 
 * 
 * @author Patrick G. Durand
 */
public class IFeature implements Feature{

	private static final long serialVersionUID = -2815540164618892263L;
	/**
	 * @serial
	 */
	private String          key;
	/**
	 * @serial
	 */
    private int             from;
	/**
	 * @serial
	 */
    private int             to;
	/**
	 * @serial
	 */
    private int             strand;
	/**
	 * @serial
	 */
    private ArrayList<Qualifier> qualifiers;
	/**
	 * @serial
	 */
    private FeatureLocation location;
    
    private transient String    _strRepr;
    
    public IFeature(){
        qualifiers = new ArrayList<Qualifier>();
    }
    
    public IFeature(String key, int from, int to, int strand){
        this();
        setKey(key);
        setFrom(from);
        setTo(to);
        setStrand(strand);
        if (from>to)
            throw new IllegalArgumentException("from is greater than to");
    }
    
    /**
     * Implementation of Feature interface.
     */
    public Feature clone(){
    	IFeature feat = new IFeature();
    	feat.copy(this);
    	return feat;
    }

    public void copy(Feature src){
    	int       i, size;
    	
    	this.setKey(src.getKey());
    	this.setFrom(src.getFrom());
    	this.setTo(src.getTo());
    	this.setStrand(src.getStrand());
    	size = src.qualifiers();
    	for(i=0;i<size;i++){
    		this.qualifiers.add((Qualifier)src.getQualifier(i).clone());
    	}
    	if (location != null){
    		this.setFeatureLocation((FeatureLocation) location.clone());
    	}
    }
    /**
     * Implementation of Feature interface.
     */
	public String getKey() {
		return key;
	}

    /**
     * Implementation of Feature interface.
     */
	public int getFrom() {
		return from;
	}

    /**
     * Implementation of Feature interface.
     */
	public int getTo() {
		return to;
	}

    /**
     * Implementation of Feature interface.
     */
	public int getStrand() {
		return strand;
	}

    public FeatureLocation getFeatureLocation(){
    	return location;
    }
    

    public void setKey(String str) {
        if (str==null)
            throw new IllegalArgumentException("key is null");
        key = str;
        _strRepr=null;
    }

    public void setStrand(int i) {
        if (i!=Feature.MINUS_STRAND && i!=Feature.PLUS_STRAND)
            throw new IllegalArgumentException("strand is invalid");
        strand = i;
        _strRepr=null;
    }

    public void setFrom(int i) {
        if (i<0)
            throw new IllegalArgumentException("from is invalid");
        from = i;
        _strRepr=null;
    }

    public void setTo(int i) {
        if (i<0)
            throw new IllegalArgumentException("to is invalid");
        to = i;
        _strRepr=null;
    }

    public void setFeatureLocation(FeatureLocation fl){
    	location = fl;
    	if (fl!=null){
    		setFrom(fl.getStart());
    		setTo(fl.getEnd());
    	}
    }

    public ArrayList<Qualifier> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(ArrayList<Qualifier> quals) {
        qualifiers = quals;
    }

    /**
     * Implementation of Feature interface.
     */
	public Qualifier addQualifier(String name, String value) {
		IQualifier qi;
        
        qi = new IQualifier(name, value);
        addQualifier(qi);
		
        return qi;
	}

	public void addQualifier(Qualifier q){
        qualifiers.add(q);
	}
    /**
     * Implementation of Feature interface.
     */
	public int qualifiers() {
		return qualifiers.size();
	}

    /**
     * Implementation of Feature interface.
     */
	public Enumeration<Qualifier> enumQualifiers() {
		return Collections.enumeration(qualifiers);
	}

    /**
     * Implementation of Feature interface.
     */
    public Qualifier getQualifier(int i){
        if (i<0 ||i>=qualifiers.size())
            return null;
        return ((Qualifier) qualifiers.get(i));
    }

    public String getStringRepr(){
        StringBuffer szBuf;
        Enumeration<Qualifier>  myEnum;
        int          i;
        szBuf = new StringBuffer();
        
        szBuf.append(key);
        szBuf.append(" : ");
        szBuf.append(strand==Feature.PLUS_STRAND ? from : to);
        szBuf.append("..");
        szBuf.append(strand==Feature.PLUS_STRAND ? to : from);
        szBuf.append("\n");
        if (qualifiers()!=0){
            i=1;
            myEnum = enumQualifiers();
            while(myEnum.hasMoreElements()){
                szBuf.append("  ");
                szBuf.append(i++);
                szBuf.append(". ");
                szBuf.append(myEnum.nextElement().toString());
                szBuf.append("\n");
            }
        }
        if (location!=null){
        	szBuf.append("  FLoc: "+location.toString());
        	szBuf.append("\n");
        }
        return (szBuf.toString());
    }

    public String toString(){
        StringBuffer szBuf;

        if (_strRepr!=null)
            return _strRepr;
            
        szBuf = new StringBuffer();
        
        szBuf.append(key);
        szBuf.append(" : ");
        szBuf.append(from);
        szBuf.append("..");
        szBuf.append(to);
        if (strand==Feature.MINUS_STRAND){
        	szBuf.append(" (-)");
        }
        _strRepr = szBuf.toString();
        return (_strRepr);
    }
    public boolean isValid(int hitFrom, int hitTo){
    	FeatureLocation loc;
    	FRange          r;
    	Qualifier       qual;
    	int             i, size, a, b;
    	
    	//check the key name
    	if (key==null){
    		FeatureSystem.LOGGER.debug("Feature key not defined");
    		return false;
    	}
    	//check the location
    	if (from==0 || to==0){
    		FeatureSystem.LOGGER.debug("Feature: from and/or to is not defined");
    		return false;
    	}
    	if (from>to){
    		FeatureSystem.LOGGER.debug("Feature: from > to");
    		return false;
    	}
    	if (!(from>=hitFrom && to<=hitTo)){
    		FeatureSystem.LOGGER.debug("Feature location out of Hsp location");
    		return false;
    	}
    	size = this.qualifiers();
    	//check the qualifiers
    	for (i=0;i<size;i++){
    		qual = this.getQualifier(i);
    		if (qual.getName()==null || qual.getValue()==null){
        		FeatureSystem.LOGGER.debug("Qualifier ["+(i+1)+"] is invalid");
        		return false;
        	}
    	}
    	//check the FeatureLocation
    	loc = this.getFeatureLocation();
    	if (loc!=null){
    		if (loc.getStart()>loc.getEnd()){
        		FeatureSystem.LOGGER.debug("FeatureLocation: start>end");
        		return false;
        	}
    		size = loc.elements();
    		for(i=0;i<size;i++){
    			r = loc.getRange(i);
    			a = r.getFrom().getStart();
    			b = r.getTo().getEnd();
    	    	if (a==0 || b==0){
    	    		FeatureSystem.LOGGER.debug("FRange["+(i+1)+"]: start and/or end is not defined");
    	    		return false;
    	    	}
    	    	if (a>b){
    	    		FeatureSystem.LOGGER.debug("FRange["+(i+1)+"]: start>end");
    	    		return false;
    	    	}
    	    	if (a<hitFrom || b>hitTo){
    	    		FeatureSystem.LOGGER.debug("FRange["+(i+1)+"]: out of Hsp location");
    	    		return false;
    	    	}
    		}
    	}
    	return true;
    }
    
    public Feature cut(int from, int to){
    	if (this.to >= from && this.from<=to){
        	IFeature feat = (IFeature) this.clone();
        	FeatureLocation loc = this.getFeatureLocation();
        	if (loc!=null){
        		feat.setFeatureLocation(loc.cut(from, to));
        	}
        	else{
            	if (feat.getFrom()<=from)
            		feat.setFrom(from);
            	if (feat.getTo()>=to)
            		feat.setTo(to);
        	}
        	return feat;
		}
    	else{
    		return null;
    	}
    }
    /**
	 * Figures out if a feature spans beyond left limit.
	 */
	public boolean spanLeft(){
		return getFrom()<0;
	}

	/**
	 * Figures out if a feature spans beyond right limit.
	 */
	public boolean spanRight(){
		return getTo()<0;
	}
	public String getLoc() throws Exception{
		StringBuffer buf;
		buf = new StringBuffer();
		if (this.spanLeft())
			buf.append("<");
		buf.append(this.getFrom());
		buf.append("..");
		if (this.spanRight())
			buf.append(">");
		buf.append(this.getTo());
		return buf.toString();
    }
}
