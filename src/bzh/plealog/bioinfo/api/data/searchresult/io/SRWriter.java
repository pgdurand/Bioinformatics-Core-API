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
 * This interface defines a Search Result writer.
 * 
 * @author Patrick G. Durand
 */
public interface SRWriter {
    /**
     * Returns the name of this writer.
     */
	public String getName();
    /**
     * Returns the version of this writer.
     */
    public String getVersion();
    /**
     * Writes the content of a search result.
     * 
     * @throws SRWriterException such an exception is reported when an error
     * occurred while writing a data object in a file. 
     */
    public void write(File f, SROutput data) throws SRWriterException;
    /**
     * Writes the content of a search result.
     * 
     * @throws SRWriterException such an exception is reported when an error
     * occurred while writing a data object in a file. 
     */
    public void write(File f, SROutput data, Object options) throws SRWriterException;
}
