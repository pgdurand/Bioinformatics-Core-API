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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This class represents a Position Specific Matrix. It aims at handling
 * a kind of statistics for each DSymbol of an DAlphabet at every position
 * of a DSequenceAlignment.
 * 
 * @author Patrick G. Durand
 */
public abstract class PositionSpecificMatrix {
	private AlphabetCounter[]  matrix_;
	private DAlphabet          alphabet_;
	private ArrayList<DSymbol> rowHeader_;
	private String             type_;
	private int                nbAlignedSequences_;
	private int                maxCounting_;
	
    private AlphaSymbolComparator SYMBOL_COMPARATOR = new AlphaSymbolComparator();

    /**
     * Designate a real implementation of a Position Specific Counting Matrix.
     */
    public static final String POSITION_SPECIFIC_COUNTING_MATRIX = "PSCM";
    
    /**
     * Sets the DAlphabet.
     */
	protected void setAlphabet(DAlphabet alph){
    	Enumeration<DSymbol> sEnum;
    	
    	if (alph==null)
    		return;
    	alphabet_ = alph;
    	rowHeader_ = new ArrayList<DSymbol>();
    	sEnum = alphabet_.symbols();
    	while(sEnum.hasMoreElements()){
    		rowHeader_.add(sEnum.nextElement());
    	}
    	Collections.sort(rowHeader_, SYMBOL_COMPARATOR);
	}
	/**
	 * Sets the matrix containing the PSM.
	 */
	protected void setMatrix(AlphabetCounter[] psm){
		matrix_ = psm;
	}
	/**
	 * Returns the alphabet.
	 */
	public DAlphabet getAlphabet(){
		return alphabet_;
	}
	/**
	 * Returns the type of this PSM. Return value is one of XXX_MATRIX
	 * constants defined in this class.
	 */
	public String getType(){
		return type_;
	}
	/**
	 * Sets the type of this PSM. Use one of XXX_MATRIX
	 * constants defined in this class.*/
	public void setType(String type){
		type_ = type;
	}
	/**
	 * Returns the number of rows of thios PSM.
	 */
	public int rows(){
		return (alphabet_==null ? 0 : alphabet_.size());
	}
	
	/**
	 * Returns the number of columns of thios PSM.
	 */
	public int columns(){
		return (matrix_!=null ? matrix_.length : 0);
	}
	/**
	 * Returns the statistical value at a particular positions of the PSM.
	 */
	public Integer getCounter(int row, int col){
		DSymbol       symbol;
		
		if (rowHeader_==null || matrix_==null)
			return null;
		symbol = getRowSymbol(row);
		return (matrix_[col].getCounter(symbol));
	}
	/**
	 * Returns the entire statistical array for all DSymbol of an alphabet
	 * at a particular position of the MSA.
	 */
	public AlphabetCounter getCounter(int col){
		if (matrix_==null)
			return null;
		return (matrix_[col]);
	}
	/**
	 * Gets the DSymbol at a particular row. Note that DSymbols contained
	 * in the DAlphabet are sorted by ascending position of their Char
	 * representation.
	 */
	public DSymbol getRowSymbol(int idx){
		if (rowHeader_==null)
			return null;
		return ((DSymbol) rowHeader_.get(idx));
	}
	/**
	 * Sets the number of aligned sequences used to compute this matrix.
	 */
	public void setNbAlignedSequences(int nbSeq){
		nbAlignedSequences_ = nbSeq;
	}
	/**
	 * Returns the maximum value from AlphabetCounter array.
	 */
	public int getMaxCounter(){
		return maxCounting_;
	}
	/**
	 * Sets the maximum value from AlphabetCounter array.
	 */
	public void setMaxCOunter(int mxC){
		maxCounting_ = mxC;
	}
	/**
	 * Returns the number of aligned sequences used to compute this matrix.
	 */
	public int getNbAlignedSequences(){
		return nbAlignedSequences_;
	}
    /**
     * A Comparator used to sort SymbolCounter in ascending order of their
     * char representation.
     */
    private class AlphaSymbolComparator implements Comparator<DSymbol> {
		@Override
        public int compare(DSymbol o1, DSymbol o2) {
            return ( o1.getChar() - o2.getChar() );
        }
    }
	
}
