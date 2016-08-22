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
package bzh.plealog.bioinfo.api.data.sequence;

/**
 * This class is just used to set an identifier and a description to a DSequence.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceInfo {
    private String _name;
    private String _id;
    
    public DSequenceInfo(){
    }
    
    public DSequenceInfo(String name, String id) {
		super();
		this._name = name;
		this._id = id;
	}

	public Object clone(){
        DSequenceInfo dsi = new DSequenceInfo();
        dsi.copy(this);
        return dsi;
    }
    
    protected void copy(DSequenceInfo src){
        this.setName(src.getName());
        this.setId(src.getId());
    }
    
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
    
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }
    
}
