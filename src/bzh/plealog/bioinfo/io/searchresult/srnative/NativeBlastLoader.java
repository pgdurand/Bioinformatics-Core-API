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
package bzh.plealog.bioinfo.io.searchresult.srnative;

import java.io.File;

import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoaderException;

public class NativeBlastLoader implements SRLoader {
	public static final String SYSTEM_NAME ="KBNativeBLoader";
	public boolean canRead(File f) {
		boolean bRet = true;
		try {
			BlastSerializer.load(f);
		} catch (BlastSerializerException e) {
			bRet = false;
		}
		return bRet;
	}

	public String getName() {
		return SYSTEM_NAME;
	}

	public String getSystemName() {
		return SYSTEM_NAME;
	}

	public String getVersion() {
		return "1.0";
	}

	public SROutput load(File f) throws SRLoaderException {
		SROutput bo = null;
		try {
			bo = BlastSerializer.load(f);
		} catch (BlastSerializerException e) {
			throw new SRLoaderException(e.getMessage());
		}
		return bo;
	}

	public SROutput[] multipleLoad(File f) throws SRLoaderException {
		SROutput bo = null;
		try {
			bo = BlastSerializer.load(f);
		} catch (BlastSerializerException e) {
			throw new SRLoaderException(e.getMessage());
		}
		return new SROutput[]{bo};
	}

}
