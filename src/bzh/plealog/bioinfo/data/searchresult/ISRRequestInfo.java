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

import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;

/**
 * This is a default implementation of interface BStatistics.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRStatistics
 */
public class ISRRequestInfo extends IValueTable implements SRRequestInfo{
    
	private static final long serialVersionUID = -6178524255393235236L;

	public ISRRequestInfo(){
    	values = new Hashtable<String, Object>();
    }
    
    public SRRequestInfo clone(){
    	ISRRequestInfo stat = new ISRRequestInfo();
        stat.copy(this);
        return stat;
    }

    public void copy(ISRRequestInfo src){
    	this.values = new Hashtable<String, Object>(src.values); 
    }
}
