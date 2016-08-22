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
package bzh.plealog.bioinfo.api.data.sequence;

import bzh.plealog.bioinfo.api.data.sequence.stat.PositionSpecificMatrix;

/**
 * This interface defines a multiple sequence alignment.
 * 
 * @author Patrick G. Durand
 */
public interface DSequenceAlignment {
	public static final int STANDARD_ALIGNMENT = 1;
	public static final int TRANSLATED_ALIGNMENT = 3;
	public static final String REFERENCE_NAME = "Query";
	public static final String CONSENSUS_NAME = "Consensus";
	
	/**
	 * Returns the number of aligned positions.
	 */
	public int columns();
	/**
	 * Returns the number of aligned sequences.
	 */
	public int rows();
	/**
	 * Returns a sequence given its position in the MSA. Parameter
	 * row is zero-based.
	 */
	public DSequenceAligned getSequence(int row);
	
	/**
	 * Insert in the MSA a consensus sequence as well as a reference sequence.
	 * Both sequences should be added at the top of the MSA, the reference being
	 * before the consensus.
	 */
	public void setSpecialSequences(DSequence consensus, DSequence refSeq);
	
	/**
	 * Returns a symbol given its position in the MSA. Parameters
	 * col and row are zero-based.
	 */
	public DSymbol getSymbol(int col, int row);
	
	/**
	 * Returns the type of alignment. One of DSequenceAlignment.XXX_ALIGNMENT 
	 * constants.
	 */
	public int getType();
	
	/**
	 * Gets a Position Specific Matrix.
	 *  
	 * @param type one of the PositionSpecificMatrix XXX_MATRIX constants
	 * 
	 * @see bzh.plealog.bioinfo.api.data.sequence.stat.PositionSpecificMatrix
	 */
	public PositionSpecificMatrix getPositionSpecificMatrix(String type);
}
