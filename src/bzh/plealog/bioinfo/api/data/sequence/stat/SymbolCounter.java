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

import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This class is used to maintain the number of matches of a given
 * HSP against a particuler position of the query sequence.
 * 
 * 
 * @author Patrick G. Durand
 */
public class SymbolCounter {
	private Integer iCounter_;
	private int     counter_;
	private DSymbol symbol_;
	
	/**
	 * Creates a counter initialized to zero.
	 */
	public SymbolCounter(){}
	
	/**
	 * Creates a counter initialized to zero.
	 */
	public SymbolCounter(DSymbol symbol){
		setSymbol(symbol);
	}

	/**
	 * Creates a counter initialized to a particular value.
	 */
	public SymbolCounter(int val){
		counter_ = val;
	}
	
	/**
	 * Increments this counter by one.
	 */
	public void increment(){
		counter_++;
		if (iCounter_!=null)
			iCounter_=null;
	}

	/**
	 * Gets the value of this counter.
	 */
	public Integer getCounter() {
		if (iCounter_==null)
			iCounter_ = new Integer(counter_);
		return iCounter_;
	}
	/**
	 * Sets this counter to a particular value.
	 */
	public void setCounter(int counter) {
		this.counter_ = counter;
		if (iCounter_!=null)
			iCounter_=null;
	}

	public DSymbol getSymbol() {
		return symbol_;
	}

	public void setSymbol(DSymbol symbol) {
		this.symbol_ = symbol;
	}
	
}
