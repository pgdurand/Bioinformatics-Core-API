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
package bzh.plealog.bioinfo.api.data.sequence.stat;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;

/**
 * This class represents a Position Specific Counting Matrix. It aims at handling
 * the number of time each DSymbol of an DAlphabet is represented at every position
 * of a DSequenceAlignment.
 * 
 * @author Patrick G. Durand
 */
public class PositionSpecificCountingMatrix extends PositionSpecificMatrix {

	/**
	 * No default constructor available.
	 */
	private PositionSpecificCountingMatrix(){}
	
	/**
	 * Constructorwith a MSA.
	 */
	public PositionSpecificCountingMatrix(DSequenceAlignment msa){
		this();
		AlphabetCounter[] pscm;
		AlphabetCounter   aCounter;
    	DAlphabet         alph;
    	int               i, j, rows, cols, max=0, val;

    	if (msa==null || msa.rows()==0 || msa.columns()==0)
    		return;
    	rows = msa.rows();
    	cols = msa.columns();
    	pscm = new AlphabetCounter[cols];
    	alph = msa.getSequence(0).getSequence().getAlphabet();
    	setAlphabet(alph);
    	for(i=0;i<cols;i++){
    		aCounter = new AlphabetCounter(alph, false);
    		for(j=0;j<rows;j++){
				aCounter.increment(msa.getSymbol(i, j));
        	}
    		val = aCounter.getMaxCounter();
    		if (val>max){
    			max=val;
    		}
    		pscm[i] = aCounter;
    	}
    	setMatrix(pscm);
    	setNbAlignedSequences(msa.rows());
    	setMaxCOunter(max);
		setType(PositionSpecificMatrix.POSITION_SPECIFIC_COUNTING_MATRIX);
	}
}
