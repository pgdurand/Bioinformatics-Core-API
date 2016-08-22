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
package bzh.plealog.bioinfo.data.searchresult;

import java.util.Hashtable;

import bzh.plealog.bioinfo.api.data.searchresult.SRStatistics;

/**
 * This is a default implementation of interface BStatistics.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRStatistics
 */
public class ISRStatistics extends IValueTable implements SRStatistics{
    
	private static final long serialVersionUID = 3470301842832060346L;

	public ISRStatistics(){
    	values = new Hashtable<String, Object>();
    }
    
    public SRStatistics clone(){
        ISRStatistics stat = new ISRStatistics();
        stat.copy(this);
        return stat;
    }

    public void copy(ISRStatistics src){
    	this.values = new Hashtable<String, Object>(src.values); 
    }
}
