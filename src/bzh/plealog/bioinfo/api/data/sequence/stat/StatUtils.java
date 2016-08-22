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

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This class contains come utility methods. 
 * 
 * @author Patrick G. Durand
 */
public class StatUtils {

    /**
	 * Given an alphabet this method computes a Map of all the special
	 * symbols contained in that alphabet.
	 */
	public static Map<DSymbol, DSymbol> getSpecialSymbolMap(DAlphabet alphabet){
		Hashtable<DSymbol, DSymbol> table;
		DSymbol   symbol;
		
		table = new Hashtable<DSymbol, DSymbol>();
		symbol = alphabet.getSymbol(DSymbol.ANY_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		symbol = alphabet.getSymbol(DSymbol.MATCH_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		symbol = alphabet.getSymbol(DSymbol.MISMATCH_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		symbol = alphabet.getSymbol(DSymbol.POSITIVE_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		symbol = alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		symbol = alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		symbol = alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE);
		if (symbol!=null)
			table.put(symbol, symbol);
		return table;
	}

	/**
	 * Compute the composition of a sequence.
	 */
	public static AlphabetCounter computeComposition(DSequence seq){
		AlphabetCounter alphC;
		int             i, size;
		
		alphC = new AlphabetCounter(seq.getAlphabet(), true);
		size = seq.size();
		for(i=0;i<size;i++){
			alphC.increment(seq.getSymbol(i));
		}
		return alphC;
	}
	/**
	 * Compute the composition of a region of a sequence.
	 * 
	 * @param seq the sequence
	 * @param from starting index. Zero-based absolute value.
	 * @param to ending index. Zero-based absolute value.
	 */
	public static AlphabetCounter computeComposition(DSequence seq, int from, int to){
		AlphabetCounter alphC;
		int             i, size;
		
		alphC = new AlphabetCounter(seq.getAlphabet(), true);
		size = to+1;
		for(i=from;i<size;i++){
			alphC.increment(seq.getSymbol(i));
		}
		return alphC;
	}
	/**
	 * Compute the composition of a region of a sequence.
	 * 
	 * @param seq the sequence
	 * @param locs list of ranges. Zero-based absolute values required.
	 */
	public static AlphabetCounter computeComposition(DSequence seq, List<DLocation> locs){
		AlphabetCounter alphC;
		int             from, to, i;
		
		alphC = new AlphabetCounter(seq.getAlphabet(), true);
		
		for(DLocation loc : locs){
			from = loc.getFrom();
			to = loc.getTo()+1;	
			for(i=from;i<to;i++){
				alphC.increment(seq.getSymbol(i));
			}
		}
		return alphC;
	}
}
