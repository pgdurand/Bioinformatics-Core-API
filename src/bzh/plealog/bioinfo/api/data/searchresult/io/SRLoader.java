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
package bzh.plealog.bioinfo.api.data.searchresult.io;

import java.io.File;

import bzh.plealog.bioinfo.api.data.searchresult.SROutput;

/**
 * This interface defines a Blast loader. Such a loader aims at converting
 * a Blast file into an object representation , namely a BOutput.
 * 
 * @author Patrick G. Durand
 */
public interface SRLoader{
	/**
	 * Returns the name of the loader that is internally used by the
	 * software to identify the BLAST loader.
	 * */
	public String getSystemName();
	
    /**
     * Returns the name of this loader.
     */
	public String getName();
	
    /**
     * Returns the version of this loader.
     */
    public String getVersion();
    
    /**
     * Figures out whether this loader is capable of reading the data pointed to by
     * the reader.  
     */
    public boolean canRead(File f);
     
    /**
     * Reads the content of a Blast result file, and returns its object 
     * representation.
     * 
     * @throws SRLoaderException such an exception should be reported when an error
     * occurred while reading a Blast output file. 
     */
    public SROutput load(File f) throws SRLoaderException;

    /**
     * Reads the content of a Blast object representation, and returns a file.
     * 
     * @throws SRLoaderException such an exception should be reported when an error
     * occurred while writing a Blast output file. 
     */
    public SROutput[] multipleLoad(File f) throws SRLoaderException;
    

    
}
