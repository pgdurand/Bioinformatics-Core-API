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

import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;

/**
 * This is a segment of a sequence. This class is used to
 * handle segments of sequence in a DSequenceSegmented.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceSegment {
	private DSequence seq_;
	private DLocation loc_;
	
	/**
	 * Constructor.
	 * 
	 * @param seq the sequence forming this segment.
	 * @param loc the position of this segment in the coordinate system
	 * of the segmented sequence.
	 * */
	public DSequenceSegment(DSequence seq, DLocation loc){
		setSequence(seq);
		setLocation(loc);
	}

	public DSequence getSequence() {
		return seq_;
	}

	public void setSequence(DSequence seq) {
		this.seq_ = seq;
	}

	public DLocation getLocation() {
		return loc_;
	}

	public void setLocation(DLocation loc) {
		this.loc_ = loc;
	}
	
}
