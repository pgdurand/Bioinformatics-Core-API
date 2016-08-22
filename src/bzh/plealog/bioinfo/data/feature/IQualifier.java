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


import bzh.plealog.bioinfo.api.data.feature.Qualifier;

/**
 * This class is a default implementation of a qualifier. 
 * 
 * @author Patrick G. Durand
 */
public class IQualifier implements Qualifier{
	private static final long serialVersionUID = 3507017161145209871L;
	/**
	 * @serial
	 */
	private String name;
	/**
	 * @serial
	 */
    private String value;
    
    public IQualifier(){
    }

    public IQualifier(String name, String value){
        setName(name);
        setValue(value);
    }
    
    /**
     * Implementation of Qualifier interface.
     */
    public Qualifier clone(){
    	IQualifier qual = new IQualifier();
    	qual.copy(this);
    	return qual;
    }
    
    public void copy(Qualifier src){
    	this.setName(src.getName());
    	this.setValue(src.getValue());
    }
    /**
     * Implementation of Qualifier interface.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Implementation of Qualifier interface.
     */
    public String getValue(){
        return value;
    }

    public void setName(String str) {
        if (str==null)
            throw new IllegalArgumentException("key is null");
		name = str;
	}

    public void setValue(String str) {
		value = (str==null ? "":str);
	}

    public String toString(){
        return (name +" = "+value);
    }
}
