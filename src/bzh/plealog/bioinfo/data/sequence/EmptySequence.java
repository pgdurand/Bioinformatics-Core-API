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

import java.util.ArrayList;
import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This is a simple implementation of a sequence. Can be used to model a sequence
 * without knowing the sequence letters.
 * 
 * @author Patrick G. Durand
 */
public class EmptySequence implements DSequence {

	private int             size;
	private EmptyRulerModel rModel;
	private DAlphabet       alphabet;
	private DSequenceInfo   sInfo;
	
	private EmptySequence(){
		super();
	}
	
	public EmptySequence(DAlphabet alphabet, int size){
		this();
		this.size = size;
		this.alphabet = alphabet;
		rModel = new EmptyRulerModel(1, 1, size);
	}
	/**
	 * Implementation of DSequence interface.
	 */
	public DRulerModel createRulerModel(int startPos, int increment) {
		rModel = new EmptyRulerModel(startPos, increment, size);
		return rModel;
	}
	/**
	 * Implementation of DSequence interface. Always return null.
	 */
	public DRulerModel createRulerModel(int[] coord) {
		return null;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DAlphabet getAlphabet() {
		return alphabet;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public int getGapContent() {
		return 0;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DRulerModel getRulerModel() {
		return rModel;
	}

	public DSequenceInfo getSequenceInfo() {
		return sInfo;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public List<DLocation> getSequenceParts() {
		ArrayList<DLocation> parts = new ArrayList<DLocation>();
		int from = rModel.getSeqPos(0), to = rModel.getSeqPos(size-1);
		parts.add(new DLocation(
			    Math.min(from, to), 
			    Math.max(from, to), 
				rModel.getIncrement()<0?DLocation.STRAND_MINUS:DLocation.STRAND_PLUS));
		return null;
	}
	/**
	 * Implementation of DSequence interface.
	 */
	//note: idx values are absolutes (ruler model). idxTo is exclusive.
	public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse) {
		EmptySequence seq;
		seq = new EmptySequence(alphabet, idxTo-idxFrom);
		int from = inverse ? idxTo-1 : idxFrom;
		from = rModel.getSeqPos(from);
		seq.createRulerModel(from, inverse?-1:+1);
		return seq;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public DSymbol getSymbol(int idx) {
		return alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE);
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public void setSequenceInfo(DSequenceInfo dsi) {
		sInfo = dsi;
	}

	/**
	 * Implementation of DSequence interface.
	 */
	public int size() {
		return size;
	}
}
