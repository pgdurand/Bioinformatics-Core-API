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

import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;

/**
 * This is a simple implementation of a ruler model. Used to display a sequence
 * ruler without knowing the sequence letters.
 * 
 * @author Patrick G. Durand
 */
public class EmptyRulerModel implements DRulerModel {
	private int startPos;
	private int increment;
	private int seqSize;
	public EmptyRulerModel(int startPos, int increment, int size){
		this.startPos = startPos;
		this.increment = increment;
		seqSize = size;
	}
	/**
	 * Implementation of DRulerModel interface.
	 */
	public int getIncrement() {
		return increment;
	}

	/**
	 * Implementation of DRulerModel interface.
	 */
	public int getRulerPos(int seqPos) {
		if (startPos<0 && seqPos>=0){
			seqPos--;
			return (seqPos - startPos)/increment;
		}
		else{
			return (seqPos - startPos - 1)/increment;
		}
	}

	/**
	 * Implementation of DRulerModel interface.
	 */
	public int getSeqPos(int idx) {
		int pos = startPos+(increment*idx);
		if (startPos<0 && pos>=0){
			pos++;
		}
		return pos;
	}

	/**
	 * Implementation of DRulerModel interface.
	 */
	public int getStartPos() {
		return startPos;
	}

	/**
	 * Implementation of DRulerModel interface.
	 */
	public int size() {
		return seqSize;
	}
}
