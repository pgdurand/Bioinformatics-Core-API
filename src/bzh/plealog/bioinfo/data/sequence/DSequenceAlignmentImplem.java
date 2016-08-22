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
import java.util.Hashtable;

import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAligned;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.stat.PositionSpecificCountingMatrix;
import bzh.plealog.bioinfo.api.data.sequence.stat.PositionSpecificMatrix;

/**
 * This is a default implementation of DSequenceAlignment.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceAlignmentImplem implements DSequenceAlignment {

	private ArrayList<DSequenceAligned>               sequences_;
	private Hashtable<String, PositionSpecificMatrix> psm_;
	private boolean   hasConsensus_;
	private boolean   hasRefSeq_;
	private int       columns_;
	private int       type_;
	
	public DSequenceAlignmentImplem(int type){
		sequences_ = new ArrayList<DSequenceAligned>();
		psm_ = new Hashtable<String, PositionSpecificMatrix>();
		type_ = type;
	}
	
	/**
	 * Sets the number of aligned positions.
	 */
	public void setColumns(int col){
		columns_ = col;
	}
	
	/**
	 * Adds a sequence.
	 */
	public void addSequence(DSequenceAligned seq){
		sequences_.add(seq);
	}
	
	public int columns() {
		return columns_;
	}

	public int rows() {
		return sequences_.size();
	}

	public DSequenceAligned getSequence(int row) {
		if (sequences_.isEmpty())
			return null;
		return ((DSequenceAligned)sequences_.get(row));
	}
	public void setSpecialSequences(DSequence consensus, DSequence refSeq){
		if (refSeq!=null && refSeq.size()==this.columns()){
			DSequenceAlignedImplem dai = new DSequenceAlignedImplem(refSeq, 0);
			if (hasRefSeq_)
				sequences_.remove(0);
			sequences_.add(0, dai);
			hasRefSeq_ = true;
		}
		else{
			hasRefSeq_ = false;
		}
		if (consensus!=null && consensus.size()==this.columns()){
			DSequenceAlignedImplem dai = new DSequenceAlignedImplem(consensus, 0);
			if (hasConsensus_)
				sequences_.remove(hasRefSeq_?1:0);
			sequences_.add(hasRefSeq_?1:0, dai);
			hasConsensus_ = true;
		}
		else{
			hasConsensus_ = false;
		}
	}

	public DSymbol getSymbol(int col, int row) {
		DSequenceAligned seq;
		DSequence        sequence;
		DSymbol          space, val;
		int              seqStartPos, seqSize;
		
		seq = this.getSequence(row);
		if (seq==null)
			return null;
		sequence = seq.getSequence();
		space = sequence.getAlphabet().getSymbol(DSymbol.SPACE_SYMBOL_CODE);
		seqStartPos = seq.getStartPosition();
		seqSize = sequence.size();
		if (col>=seqStartPos && col<=(seqStartPos+seqSize-1)){
			val = sequence.getSymbol(col-seqStartPos);
		}
		else{
			val = space;
		}
		return val;
	}
	public int getType(){
		return type_;
	}
	
	public PositionSpecificMatrix getPositionSpecificMatrix(String type){
		PositionSpecificMatrix matrix = null;
		
		if (psm_.containsKey(type)){
			return ((PositionSpecificMatrix) psm_.get(type));
		}
		
		if (type.equals(PositionSpecificMatrix.POSITION_SPECIFIC_COUNTING_MATRIX)){
			matrix = new PositionSpecificCountingMatrix(this);
			psm_.put(PositionSpecificMatrix.POSITION_SPECIFIC_COUNTING_MATRIX, matrix);
		}
		return matrix;
	}
}
