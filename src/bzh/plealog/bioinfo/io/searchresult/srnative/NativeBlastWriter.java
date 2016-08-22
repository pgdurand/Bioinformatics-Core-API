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
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriterException;

public class NativeBlastWriter implements SRWriter {
	public static final String SYSTEM_NAME ="BONativeWriter";

	public String getName() {
		return SYSTEM_NAME;
	}

	public String getSystemName() {
		return SYSTEM_NAME;
	}

	public String getVersion() {
		return "1.0";
	}

	public void write(File f, SROutput data) throws SRWriterException {
		try {
			BlastSerializer.save((SROutput) data, f);
		} catch (BlastSerializerException e) {
			throw new SRWriterException(e.getMessage());
		}
	}
	public void write(File f, SROutput data, Object options) throws SRWriterException{
		this.write(f, data);
	}

}
