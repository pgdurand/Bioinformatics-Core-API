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

/**
 * This class defines the information related to a query
 * execution.
 * 
 * @author Patrick G. Durand
 */
public interface SRRequestInfo extends Serializable{
	public static final String PROGRAM_DESCRIPTOR_KEY = "program";
	public static final String PRGM_VERSION_DESCRIPTOR_KEY = "prgmVersion";
	public static final String PRGM_REFERENCE_DESCRIPTOR_KEY = "prgmRef";
	public static final String DATABASE_DESCRIPTOR_KEY = "database";
	public static final String QUERY_ID_DESCRIPTOR_KEY = "qID";
	public static final String QUERY_DEF_DESCRIPTOR_KEY = "qDef";
	public static final String QUERY_LENGTH_DESCRIPTOR_KEY = "qLen";
	public static final String QUERY_SEQ_DESCRIPTOR_KEY = "qSeq";


	/**
	 * Returns a request information value.
	 * 
	 * @param key use one of the XXX_KEY defined in this interface.
	 */
	public Object getValue(String key);
    public void setValue(String key, Object o);
    
    /**
     * Forces the implementation of a clone method.
     */
    public SRRequestInfo clone();
}
