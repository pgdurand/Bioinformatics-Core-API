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
import java.util.Enumeration;

/**
 * This class defines a feature table associated to a sequence. As the name implied,
 * a Feature Table is made of Features. However, it is also possible to associated to
 * a Feature Table some values representing the origin of the data. These values are:
 * Source, Message, Date and Status.
 * 
 * @author Patrick G. Durand
 */
public interface FeatureTable extends Cloneable, Serializable {
    public static final int POS_SORTER = 1; 
	
    public static final int OK_STATUS = 1;
    public static final int ERROR_STATUS = 2;
    public static final String OK_STATUS_S = "Ok";
    public static final String ERROR_STATUS_S = "Error";
    
    /**
     * Adds a new feature within this table of features.
     *  
     * @param key the key name of the feature
     * @param from starting position of the feature on the sequence (use
     * zero-based value)
     * @param to ending position of the feature on the sequence (use
     * zero-based value)
     * @param strand strand location of the feature on the sequence (use
     * one of the Feature.xxxSTRAND constants)
     * 
     * @return a newly created Feature
     */
	public Feature addFeature(String key, int from, int to, int strand);
	/**
	 * Adds a new feature within this table of features. 
	 */
	public void addFeature(Feature feat);
	
    /**
     * Returns the number of Features contained in this table.
     */
	public int features();
    /**
     * Returns an enumeration over the Features contained in this table.
     */
    public Enumeration<Feature> enumFeatures();
    
    /**
     * Sorts the Features.
     * 
     * @param sortType one of the XXX_SORTER constants.
     */
    public void sort(int sortType);
    
    /**
     * Forces the implementation of a clone method.
     */
    public Object clone();
    
    /**
     * Returns the status of this table.
     */
    public int getStatus();
    /**
     * Sets the status of this table. Use this value to specify the status of the Feature
     * Table. Accepted values are OK_STATUS or ERROR_STATUS defined in this class.
     */
    public void setStatus(int status);
    
    /**
     * Returns the message associated to this table.
     */
    public String getMessage();
    /**
     * Sets the message associated to this table. Use this value to specify a message
     * associated to this table, especially when the status is set to error.
     */
    public void setMessage(String msg);

    /**
     * Returns the source of this table.
     */
    public String getSource();
    /**
     * Sets the source of this table. Use this value to specify from where the
     * Feature Table comes from.
     */
    public void setSource(String src);

    /**
     * Sets the date of this table.
     */
    public String getDate();
    /**
     * Returns the date of this table.  Use this value to specify when the
     * Feature Table has been retrieved/created.
     */
    public void setDate(String date);

    //added for release 2.6+ to check data file produced by developers (KoriViewer Public API)
    /**
     * For internal use only.
     * */
    public boolean isValid(int hitFrom, int hitTo);
    /**
     * Cut a feature table given new from and to values. Given from and to values, all features located
     * outside the range are discarded, all features located fully within the range are conserved, as well
     * as all features spanning from and/or to values. For the latter case, feature's coordinates are updated
     * to fit within the range. Please provide a from value always less than to value. If the feature table 
     * is out of range given the new coordinates, then this method return null.
     */
    public FeatureTable cut(int from, int to);
}
