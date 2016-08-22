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
 * This class defines the parameters of a search program.
 * 
 * @author Patrick G. Durand
 */
public interface SRParameters extends Serializable{

	public static final String EXPECT_DESCRIPTOR_KEY = "expect";
	public static final String GAPOPEN_DESCRIPTOR_KEY = "gapOpen";
	public static final String GAPEXTEND_DESCRIPTOR_KEY = "gapExtend";
	public static final String MATRIX_DESCRIPTOR_KEY = "matrix";

	/**
	 * Returns a search parameter value.
	 * 
	 * @param key use one of the XXX_KEY defined in this interface.
	 */
	public Object getValue(String key);
    public void setValue(String key, Object o);
    
    /**
     * Forces the implementation of a clone method.
     */
    public SRParameters clone();
}
