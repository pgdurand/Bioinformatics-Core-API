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
import java.util.Comparator;
import java.util.Enumeration;

import com.plealog.genericapp.api.log.EZLogger;

import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.feature.utils.FeatureSystem;

/**
 * This class is a default implementation of a feature table. 
 * 
 * @author Patrick G. Durand
 */
public class IFeatureTable implements FeatureTable {
	private static final long serialVersionUID = 8175131448739461390L;
	/**
	 * @serial
	 */
	@SuppressWarnings("unused")
  private int       xmlVersion = 1;
	/**
	 * @serial
	 */
	private ArrayList<Feature> features;
	/**
	 * @serial
	 */
    private int       status = FeatureTable.OK_STATUS;
	/**
	 * @serial
	 */
    private String    message = "-";
	/**
	 * @serial
	 */
    private String    source = "?";
	/**
	 * @serial
	 */
    private String    date = "?";
    
    public IFeatureTable(){
        features = new ArrayList<Feature>();
    }
    
    /**
     * Implementation of FeatureTable interface.
     */
    public Object clone(){
    	IFeatureTable ft = new IFeatureTable();
    	ft.copy(this);
    	return ft;
    }
    public void copy(FeatureTable ft){
    	Enumeration<Feature>   mye;
    	
    	this.setDate(ft.getDate());
    	this.setMessage(ft.getMessage());
    	this.setSource(ft.getSource());
    	this.setStatus(ft.getStatus());
    	mye = ft.enumFeatures();
    	while(mye.hasMoreElements()){
    		this.features.add((Feature) mye.nextElement().clone());
    	}
    	
    }
    /**
     * Implementation of FeatureTable interface.
     */
	public Feature addFeature(String key, int from, int to, int strand) {
		IFeature fi;
        
        fi = new IFeature(key, from, to, strand);
        addFeature(fi);
        
		return fi;
	}
    /**
     * Implementation of FeatureTable interface.
     */
	public void addFeature(Feature feat){
		if (feat instanceof IFeature)
			features.add(feat);
	}
	
    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> feats) {
        features = feats;
    }

    /**
     * Implementation of FeatureTable interface.
     */
	public int features() {
		return features.size();
	}

    /**
     * Implementation of FeatureTable interface.
     */
	public Enumeration<Feature> enumFeatures() {
		return Collections.enumeration(features);
	}
    /**
     * Implementation of FeatureTable interface.
     */
	public void sort(int sortType){
		if (sortType != FeatureTable.POS_SORTER)
			return;
		Collections.sort(features, new FeaturePositionComparator());
	}
    public String toString(){
        StringBuffer szBuf;
        Enumeration<Feature>  myEnum;
        int          i;
        
        szBuf = new StringBuffer();
        
        if (features()!=0){
            i=1;
            myEnum = enumFeatures();
            while(myEnum.hasMoreElements()){
                szBuf.append(i++);
                szBuf.append(". ");
                szBuf.append(myEnum.nextElement().toString());
                szBuf.append("\n");
            }
        }
        else{
            szBuf.append("No data.");
        }
        return (szBuf.toString());
    }
    protected class FeaturePositionComparator implements Comparator<Feature>{
    	public int compare(Feature o1, Feature o2){
    		if (o1.getFrom() > o2.getFrom()) {
    			return +1;
    		} else if (o1.getFrom() < o2.getFrom()) {
    			return -1;
    		} else {
    			return 0;
    		}
    	}
    }
    public int getStatus(){
    	return status;
    }
    public void setStatus(int status){
    	this.status = status; 
    }
    
    public String getMessage(){
    	return message;
    }
    public void setMessage(String msg){
    	message = msg;
    }
    public String getSource(){
    	return source;
    }
    public void setSource(String src){
    	this.source = src;
    }
    
    public String getDate(){
    	return date;
    }
    public void setDate(String date){
    	this.date = date;
    }
    /**
     * For internal use only.
     * */
    public boolean isValid(int hitFrom, int hitTo){
    	int i, size;
    	
    	size = this.features();
    	for(i=0;i<size;i++){
    		if (!((Feature)this.getFeatures().get(i)).isValid(hitFrom, hitTo)){
    			EZLogger.debug("Feature ["+(i+1)+"] is invalid");
    			return false;
    		}
    	}
    	return true;
    }
    public FeatureTable cut(int from, int to){
    	IFeatureTable ft;
    	Feature       feat;
    	int           i, size;
    	
    	ft = new IFeatureTable();
    	size = this.features();
    	for(i=0;i<size;i++){
    		feat = ((Feature)this.getFeatures().get(i)).cut(from, to);
    		if (feat!=null){
    			ft.addFeature(feat);
    		}
    	}
    	if (ft.features()==0){
    		return null;
    	}
    	else{
    		return ft;
    	}
    }
}
