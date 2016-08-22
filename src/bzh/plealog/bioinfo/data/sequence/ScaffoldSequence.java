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

import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * An implementation of a scaffold sequence. Cannot be used when sequence 
 * segments overlaps.
 * 
 * @author Patrick G. Durand
 */
public class ScaffoldSequence implements DSequence {
	private EmptyRulerModel    rModel;
	private DSequence          sequence;
	
	public ScaffoldSequence(DSequence seq) {
		sequence = seq;
	}
	/**
	 * Implementation of DSequence interface.
	 */
	//call this method AFTER adding all segments AND setting the sequence full size
	public DRulerModel createRulerModel(int startPos, int increment){
    	rModel = new EmptyRulerModel(startPos, increment, size());
		return rModel;
    }

	/**
	 * Implementation of DSequence interface.
	 */
	public DRulerModel createRulerModel(int[] arg0) {
		return null;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DAlphabet getAlphabet() {
		return sequence.getAlphabet();
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public int getGapContent() {
		return sequence.getGapContent();
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DRulerModel getRulerModel() {
		return rModel;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DSequenceInfo getSequenceInfo() {
		return sequence.getSequenceInfo();
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public List<DLocation> getSequenceParts() {
		return sequence.getSequenceParts();
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DSequence getSubSequence(int arg0, int arg1, boolean arg2) {
		return sequence.getSubSequence(arg0, arg1, arg2);
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DSymbol getSymbol(int arg0) {
		return sequence.getSymbol(arg0);
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public void setSequenceInfo(DSequenceInfo arg0) {
		sequence.setSequenceInfo(arg0);
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public int size() {
		return sequence.size();
	}

}
