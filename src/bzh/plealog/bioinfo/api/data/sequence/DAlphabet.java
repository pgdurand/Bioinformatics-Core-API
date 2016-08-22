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

import java.util.Enumeration;

/**
 * This interface defines an alphabet.
 * 
 * @author Patrick G. Durand
 */
public interface DAlphabet {
    
	public static int OTHER_ALPHABET   = 0;
	public static int PROTEIN_ALPHABET = 1;
	public static int RNA_ALPHABET     = 2;
	public static int DNA_ALPHABET     = 3;

	public static final String AA_STR = "amino acids";
	public static final String NUC_STR = "nucleotides";
	public static final String OTHER_STR = "letters";
	
    /**
     * Returns the symbol given its code.
     */
	public DSymbol getSymbol(int code);
    /**
     * Returns the symbol given its char representation.
     */
	public DSymbol getSymbol(char ch);
    /**
     * Adds a new symbol to this Alphabet.
     */
	public boolean addSymbol(int code, DSymbol symbol);
    /**
     * Returns the name of this Alphabet.
     */
	public String getName();
    /**
     * Returns the number of Symbols contained in this Alphabet.
     */
	public int size();
    /**
     * Returns a enumeration over the Symbols contained in this Alphabet.
     */
	public Enumeration<DSymbol> symbols();
	
	/**
	 * Returns one of DAlphabet.XXX_ALPHABET constants.
	 */
	public int getType();
	
	/**
	 * Returns a list of symbols as a string.
	 */
	public String getSymbolsList();
}
