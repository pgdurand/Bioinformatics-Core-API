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

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;

public interface SRHitHSP {

	public abstract SRHit getHit();

	public abstract int getHspNum();

	public abstract void setHit(SRHit hit);

	public abstract void setHspNum(int i);

	public abstract int getQuerySize();

	public abstract void setQuerySize(int i);

	public abstract int getBlastType();

	public abstract void setBlastType(int i);

	public abstract void setBlastClient(String bc);

	public abstract String getBlastCLient();

	/**
	 * Returns the query sequence type. This is one of the AA_SEQ or NUC_SEQ.
	 */
	public abstract int getQuerySeqType();

	/**
	 * Returns the hit sequence type. This is one of the AA_SEQ or NUC_SEQ.
	 */
	public abstract int getHitSeqType();

	public abstract boolean equals(Object obj);

}
