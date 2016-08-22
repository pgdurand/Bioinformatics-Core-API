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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This class is used to maintain the number of matches of all the
 * DSymbols of a given DAlphabet. 
 * 
 * @author Patrick G. Durand
 */
public class AlphabetCounter {
	private Hashtable<DSymbol, SymbolCounter> symbols_;
	private DAlphabet               alphabet_;
	private SymbolCounterComparator scComparator_ = new SymbolCounterComparator();
	private SymbolLetterComparator  slComparator_ = new SymbolLetterComparator();
	private boolean                 countSpecials_;
	
	private static final Integer ZERO_COUNTER = new Integer(0);
	
	public enum SORT_TYPE {
		COUNT_SORT, LETTER_SORT 
	}
	
	private AlphabetCounter(){}
	
	/**
	 * Constructor.
	 * 
	 * @param alphabet the alphabet for which symbols have to be counted
	 * @param countSpecials pass true is special symbols have to be counted.
	 * These symbols are defined in the interface DSymbol.
	 */
	public AlphabetCounter(DAlphabet alphabet, boolean countSpecials){
		this();
		Enumeration<DSymbol>  values;
		DSymbol               symbol;
		Map<DSymbol, DSymbol> specialSymbols;
		
		alphabet_ = alphabet;
		countSpecials_ = countSpecials;
		
		specialSymbols = StatUtils.getSpecialSymbolMap(alphabet);
		symbols_ = new Hashtable<DSymbol, SymbolCounter>();
		values = alphabet.symbols();
		while(values.hasMoreElements()){
			symbol = (DSymbol) values.nextElement();
			if (specialSymbols.containsKey(symbol)){
				if (countSpecials){
					symbols_.put(symbol, new SymbolCounter(symbol));
				}
			}
			else{
				symbols_.put(symbol, new SymbolCounter(symbol));
			}
		}
	}
	
	/**
	 * Returns the alphabet for which symbols can be counted. 
	 */
	public DAlphabet getAlphabet(){
		return alphabet_;
	}
	
	/**
	 * Figures out if special symbols are counted.
	 */
	public boolean countSpecialSymbols(){
		return countSpecials_;
	}

	/**
	 * Increments the counter of a particular symbol.
	 */
	public void increment(DSymbol symbol){
		SymbolCounter counter;
		
		counter = (SymbolCounter) symbols_.get(symbol);
		if (counter!=null)
			counter.increment();
	}
	/**
	 * Returns the maximum value of this AlphabetCounter. This value is obtained by
	 * summing counters for all symbols of the Alphabet.
	 */
	public int getMaxCounter(){
		Iterator<SymbolCounter> iter;
		int                     max = 0;
		
		iter = symbols_.values().iterator();
		while(iter.hasNext()){
			max += iter.next().getCounter().intValue();
		}
		return max;
	}
	/**
	 * Returns the number of symbols contains here.
	 */
	public int symbols(){
		return symbols_.size();
	}
	/**
	 * Gets the value of the counter of a particular symbol.
	 */
	public Integer getCounter(DSymbol symbol) {
		SymbolCounter counter;
		
		counter = (SymbolCounter) symbols_.get(symbol);
		return (counter!=null ? counter.getCounter() : ZERO_COUNTER);
	}
	
	/**
	 * Gets an array of SymbolCounter objects for which counter value is
	 * in the range defined by min and max parameters. The returned array
	 * is sorted by ascending order of SymbolCounter&apos;s counter value.
	 */
	public SymbolCounter[] getCounters(int min, int max){
		return getCounters(min, max, SORT_TYPE.COUNT_SORT);
	}
	public SymbolCounter[] getCounters(int min, int max, SORT_TYPE st){
		SymbolCounter[]          counters;
		SymbolCounter            counter;
		Iterator<SymbolCounter>  vals;
		ArrayList<SymbolCounter> list;
		int                      count, i, size;
		
		vals = symbols_.values().iterator();
		list = new ArrayList<SymbolCounter>();
		while(vals.hasNext()){
			counter = vals.next();
			count = counter.getCounter().intValue();
			if (count>=min && count<=max){
				list.add(counter);
			}
		}
		if (list.isEmpty())
			return null;
		size = list.size();
		counters = new SymbolCounter[size];
		for(i=0;i<size;i++)
			counters[i] = list.get(i);
		switch(st){
			case LETTER_SORT:
				Arrays.sort(counters, slComparator_);
				break;
			case COUNT_SORT:
			default:
				Arrays.sort(counters, scComparator_);
				break;
		}
		return counters;
	}
	/**
	 * Returns all SymbolCounters contained in this AlphabetCounter.
	 */
	public SymbolCounter[] getAllCounters(SORT_TYPE st){
		return this.getCounters(0, Integer.MAX_VALUE, st);
	}
	/**
	 * Returns all SymbolCounters contained in this AlphabetCounter sorted
	 * by ascending order of their counter value.
	 */
	public SymbolCounter[] getAllCounters(){
		return this.getCounters(0, Integer.MAX_VALUE, SORT_TYPE.COUNT_SORT);
	}
	public String toString(){
		StringBuffer            szBuf;
		SymbolCounter           counter;
		Iterator<SymbolCounter> vals;
		
		szBuf = new StringBuffer();
		vals = symbols_.values().iterator();
		while(vals.hasNext()){
			counter = vals.next();
			if (counter != null){
				szBuf.append(counter.getSymbol().getChar());
				szBuf.append("(");
				szBuf.append(counter.getCounter());
				szBuf.append(")");
				if (vals.hasNext())
					szBuf.append(", ");
			}
		}		
		return szBuf.toString();
	}
    /**
     * A Comparator used to sort SymbolCounter in ascending order of their
     * counter value.
     */
    private class SymbolCounterComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            return ( ((SymbolCounter) o1).getCounter().intValue() - 
            		 ((SymbolCounter) o2).getCounter().intValue() );
        }
    }
    /**
     * A Comparator used to sort SymbolCounter in ascending order of their
     * counter value.
     */
    private class SymbolLetterComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            String l1, l2;
            
            l1 = ((SymbolCounter) o1).getSymbol().toString();
            l2 = ((SymbolCounter) o2).getSymbol().toString();
        	return ( l1.compareTo(l2) );
        }
    }
}
