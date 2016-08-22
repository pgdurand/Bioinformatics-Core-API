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
package bzh.plealog.bioinfo.api.data.searchresult.utils;

import java.io.File;

import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;

/**
 * This interface defines a SearchResult entry. Such an entry wraps a Search 
 * Result file, i.e. a SROutput object.
 * 
 * @author Patrick G. Durand
 */
public class SREntry {

	private String    _blastClientName;
	private String    _queryName;
    private String    _name;
    private String    _path;
    private String    _repr;
    private String    _filterName;
    private String    _dbName;
    private Integer   _entryOrderNum;
    private SROutput   _result;
    private DSequence _query;
    private boolean   _view;
    
    /**
     * Creates a BlastEntry.
     * 
     * @param bClientName the search tool name
     * @param name the name of this Blast result
     * @param path the absolute path where the file is located
     * @param result a Blast Result
     * @param query the query sequence
     * @param dbName the databank name
     * @param view set to true if this is a simple view
     * */
    public SREntry(String bClientName, String name, String path, SROutput result, DSequence query,
    		String dbName, Boolean view){
    	setBlastClientName(bClientName);
    	setName(name);
        setAbsolutePath(path);
        setResult(result);
        setQuery(query);
        setDbName(dbName);
        setView(view);
        
        String reducePath = path.substring(0, path.lastIndexOf(File.separator));
        int    idx = reducePath.lastIndexOf(File.separator);
        if (idx>=0){
            setQueryName(reducePath.substring(idx+1, reducePath.length()));
        }else{
        	setQueryName("");
        }
    }
    
	/**
	 * Returns the name of this Blast entry.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the name of the Blast Client that produced this Blast entry.
	 */
	public String getBlastClientName() {
		return _blastClientName;
	}

	/**
	 * Returns the name of the query from which this Blast entry
	 * comes from.
	 */
	public String getQueryName() {
		return _queryName;
	}

	/**
	 * Returns the Blast result data of this Blast entry.
	 */
	public SROutput getResult() {
		return _result;
	}

	/**
	 * Returns the absolute path pointing to the file containing the Blast 
	 * result file.
	 */
	public String getAbsolutePath() {
		return _path;
	}

	/**
	 * Sets the name of this Blast entry.
	 */
	public void setName(String string) {
		_name = string;
		_repr=null;
	}

	/**
	 * Sets the name of the Blast Client that produced this Blast entry.
	 */
	public void setBlastClientName(String string) {
		_blastClientName = string;
		_repr=null;
	}
	/**
	 * Sets the name of the query from which this Blast entry
	 * comes from.
	 */
	public void setQueryName(String qName) {
		_queryName = qName;
		_repr=null;
	}

	/**
	 * Returns the Blast result data of this Blast entry.
	 */
	public void setResult(SROutput output) {
		_result = output;
		_repr=null;
	}

	/**
	 * Returns the absolute path pointing to the file containing the Blast 
	 * result file.
	 */
	public void setAbsolutePath(String path) {
		_path = path;
		_repr=null;
	}
	/**
	 * Sets the filter name used to create this entry from another one.
	 */
	public void setFilterName(String fName){
		_filterName = fName;
	}
	/**
	 * Returns the filter name used to create this entry from another one.
	 */
	public String getFilterName(){
		return _filterName;
	}
    /**
     * Sets the query sequence that was used to create this result.
     */
    public DSequence getQuery() {
        return _query;
    }

    /**
     * Returns the query sequence that was used to create this result.
     */
    public void setQuery(DSequence query) {
        _query = query;
    }

    /**
     * Returns the ordering number of this BlastEntry within a whole
     * BlastQuery.
     */
    public Integer getEntryOrderNum() {
		return _entryOrderNum;
	}

    /**
     * Sets the ordering number of this BlastEntry within a whole
     * BlastQuery.
     */
	public void setEntryOrderNum(Integer orderNum) {
		_entryOrderNum = orderNum;
	}

	/**
	 * Specify whether or not this BlastEntry is a view. A view contains a subset
	 * of hits and hsps from another view or reference result.
	 */
	public void setView(boolean view){
		_view = view;
	}
	public boolean isView(){
		return _view;
	}
	
	public String getDbName() {
		return _dbName;
	}

	public void setDbName(String name) {
		_dbName = name;
	}

	public String toString(){
    	StringBuffer buf;
    	String prg;

    	if (_repr!=null){
    		return _repr;
    	}
    	buf = new StringBuffer();
    	prg = (String) _result.getRequestInfo().getValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY);
    	buf.append(getQueryName());
    	buf.append(": ");
    	buf.append(_name);
    	buf.append(" (");
    	buf.append(prg!=null?prg:"?");
    	buf.append(")");
    	_repr = buf.toString();
        return _repr;
    }
}
