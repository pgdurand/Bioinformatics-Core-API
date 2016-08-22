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

import java.util.HashSet;
import java.util.Iterator;

/**
 * This class defines a family of symbols.
 * 
 * @author Patrick G. Durand
 */
public class DSymbolFamily {

	private HashSet<DSymbol> _symbols;
	private String           _name;
	//one of DAlphabet.XXX_ALPHABET constants
	private int              _type;
	
	/**
	 * Constructor.
	 * 
	 * @param name the name of the family. Free text allowed.
	 * @param type one of DAlphabet.XXX_ALPHABET constants.
	 * */
	public DSymbolFamily(String name, int type){
		_name = name;
		_type = type;
		_symbols = new HashSet<DSymbol>();
	}
	/**
	 * Adds a symbol to this family.
	 */
	public void addSymbol(DSymbol symb){
		_symbols.add(symb);
	}
	/**
	 * Gets the name of this family.
	 */
	public String getName(){
		return _name;
	}
	/**
	 * Sets the name of this family.
	 */
	public void setName(String name){
		_name = name;
	}
	/**
	 * Gets the type of this family. Returned value is one of DAlphabet.XXX_ALPHABET constants.
	 */
	public int getType() {
		return _type;
	}
	/**
	 * Sets the type of this family. 
	 * @param type one of DAlphabet.XXX_ALPHABET constants.
	 */
	public void setType(int type) {
		_type = type;
	}
	/**
	 * Returns an iterator over the symbols contained in this family.
	 */
	public Iterator<DSymbol> getSymbols(){
		return _symbols.iterator();
	}
	/**
	 * Returns a string representation of this family. It is a concatenation of the
	 * string representation of each symbol contained in the family.
	 */
	public String getSymbolsRepr(){
		Iterator<DSymbol> iter;
		StringBuffer      buf;
		
		buf = new StringBuffer();
		iter = _symbols.iterator();
		while(iter.hasNext()){
			buf.append(iter.next().getChar());
		}
		return buf.toString();
	}
}
