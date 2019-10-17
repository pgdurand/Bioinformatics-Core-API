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
package bzh.plealog.bioinfo.io.searchresult;

import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.io.searchresult.ncbi.BlastLoader;
import bzh.plealog.bioinfo.io.searchresult.ncbi.BlastLoader2;
import bzh.plealog.bioinfo.io.searchresult.srnative.NativeBlastLoader;
import bzh.plealog.bioinfo.io.searchresult.srnative.NativeBlastWriter;

/**
 * This class has to be used to read/write SearhResult objects.
 * 
 * @author Patrick G. Durand
 */
public class SerializerSystemFactory {
	
	/**
	 * Loader to handle NCBI XML formatted results.
	 */
	public static final int NCBI_LOADER = 1;
  /**
   * Loader to handle NCBI XML2 formatted results.
   */
  public static final int NCBI_LOADER2 = 2;
	/**
	 * Loader to handle the native data.
	 */
	public static final int NATIVE_LOADER   = 3;
	
	/**
	 * Writer to handle the native data.
	 */
	public static final int NATIVE_WRITER   = 4;
	
	/**
	 * Writer to handle the text data.
	 */
	public static final int NCBI_WRITER  = 6;
	
	/**
	 * Gets a freshly created object of a SRLoader.
	 * 
	 * @param l one of the available loader. 
	 * 
	 * @return a loader or null if loader not available.
	 */
	public static SRLoader getLoaderInstance(int l){
		SRLoader loader = null;
		switch(l){
    case NCBI_LOADER:
      loader = new BlastLoader();
      break;
    case NCBI_LOADER2:
      loader = new BlastLoader2();
      break;
		case NATIVE_LOADER:
			loader = new NativeBlastLoader();
			break;
		}
		return loader;
	}
	/**
	 * Gets a freshly created object of a SRWriter.
	 * 
	 * @param w one of the available writer.
	 * 
	 * @return a writer or null if loader not available.
	 */
	public static SRWriter getWriterInstance(int w){
		SRWriter writer = null;
		switch(w){
		case NATIVE_WRITER:
			writer = new NativeBlastWriter();
			break;
		case NCBI_WRITER:
			writer = new BlastLoader();
			break;
		}
		return writer;
	}
}
