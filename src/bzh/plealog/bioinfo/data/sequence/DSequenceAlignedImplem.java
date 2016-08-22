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
package bzh.plealog.bioinfo.data.sequence;

import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAligned;

/**
 * This is a default implementation of DSequenceAligned.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceAlignedImplem implements DSequenceAligned {
	private DSequence sequence_;
	private int       startPos_;
	
	public DSequenceAlignedImplem(){}
	
	public DSequenceAlignedImplem(DSequence sequence, int pos){
		setSequence(sequence);
		setStartPosition(pos);
	}
	public void setSequence(DSequence sequence){
		sequence_ = sequence;
	}
	
	public DSequence getSequence() {
		return sequence_;
	}

	public void setStartPosition(int pos){
		startPos_ = pos;
	}
	public int getStartPosition() {
		return startPos_;
	}

	public String toString(){
		StringBuffer szBuf;
		
		szBuf = new StringBuffer();
		szBuf.append(" StartPos: "+startPos_);
		szBuf.append(" Sequence:\n"+sequence_.toString());
		return szBuf.toString();
	}
}
