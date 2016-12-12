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

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.DSymbolGraphics;

/**
 * This is a default implementation of DAlphabet.
 * 
 * @author Patrick G. Durand
 */
public class DAlphabetImplem implements DAlphabet {
  private DSymbol[]         _symbols_code;
  private DSymbol[]         _symbols_char;
  private DSymbolImplem     _unknownSymbol;
  private DSymbolImplem     _gapSymbol;
  private DSymbolImplem     _matchSymbol;
  private DSymbolImplem     _misMatchSymbol;
  private DSymbolImplem     _positiveSymbol;
  private DSymbolImplem     _anySymbol;
  private DSymbolImplem     _spaceSymbol;
  private DSymbolImplem     _reusableSymbol;
  private Hashtable<DSymbolImplem,DSymbolImplem>     _specialSymbols;
  private Hashtable<SymbolCodeElement,DSymbolImplem> _specialSymbolCodes;
  private SymbolCodeElement _reusableSymbolCode;
  private String            _name;
  private int               _type;
  private String            _repr;


  public DAlphabetImplem(){
    //create the table that stores the symbols of this alphabet
    _symbols_code = new DSymbol[255];
    _symbols_char = new DSymbol[255];//ASCII Table at worst

    //creates the default symbols for special characters
    _unknownSymbol = new DSymbolImplem(0,'?');
    _gapSymbol = new DSymbolImplem(1,'-');
    _gapSymbol.setGraphics(new DSymbolGraphics(Color.white, Color.lightGray));
    _matchSymbol = new DSymbolImplem(2,'|');
    _misMatchSymbol = new DSymbolImplem(3,' ');
    _positiveSymbol = new DSymbolImplem(4,'+');
    _anySymbol = new DSymbolImplem(5,'*');
    _spaceSymbol = new DSymbolImplem(3,' ');

    //this a Map used to fastly retrieve DSymbol given the char representation
    _specialSymbols = new Hashtable<DSymbolImplem,DSymbolImplem>();
    _specialSymbols.put(_unknownSymbol,_unknownSymbol);
    _specialSymbols.put(_gapSymbol,_gapSymbol);
    _specialSymbols.put(_matchSymbol,_matchSymbol);
    _specialSymbols.put(_misMatchSymbol,_misMatchSymbol);
    _specialSymbols.put(_positiveSymbol,_positiveSymbol);
    _specialSymbols.put(_anySymbol,_anySymbol);
    _specialSymbols.put(_spaceSymbol,_spaceSymbol);
    _reusableSymbol = new DSymbolImplem();

    //this a Map used to fastly retrieve DSymbol given the code representation
    _specialSymbolCodes = new Hashtable<SymbolCodeElement,DSymbolImplem>();
    _specialSymbolCodes.put(
        new SymbolCodeElement(_unknownSymbol, DSymbol.UNKNOWN_SYMBOL_CODE),
        _unknownSymbol);
    _specialSymbolCodes.put(
        new SymbolCodeElement(_gapSymbol, DSymbol.GAP_SYMBOL_CODE),
        _gapSymbol);
    _specialSymbolCodes.put(
        new SymbolCodeElement(_matchSymbol, DSymbol.MATCH_SYMBOL_CODE),
        _matchSymbol);
    _specialSymbolCodes.put(
        new SymbolCodeElement(_misMatchSymbol, DSymbol.MISMATCH_SYMBOL_CODE),
        _misMatchSymbol);
    _specialSymbolCodes.put(
        new SymbolCodeElement(_positiveSymbol, DSymbol.POSITIVE_SYMBOL_CODE),
        _positiveSymbol);
    _specialSymbolCodes.put(
        new SymbolCodeElement(_anySymbol, DSymbol.ANY_SYMBOL_CODE),
        _anySymbol);
    _specialSymbolCodes.put(
        new SymbolCodeElement(_spaceSymbol, DSymbol.SPACE_SYMBOL_CODE),
        _spaceSymbol);
    _reusableSymbolCode = new SymbolCodeElement();

    //sets a default name to this alphabet
    _name = "noName";
  }

  public DAlphabetImplem(String name, int type){
    this();
    _name = name;
    _type = type;
  }
  public int getType(){
    return _type;
  }
  public DSymbol getSymbol(int code){
    DSymbol symbol;

    _reusableSymbolCode.setCode(code);
    symbol = (DSymbol) _specialSymbolCodes.get(_reusableSymbolCode);
    if (symbol!=null)
      return symbol;
    if ((code>=0 && code<_symbols_code.length) && _symbols_code[code]!=null)
      return _symbols_code[code];
    else
      return _unknownSymbol;
  }

  public DSymbol getSymbol(char ch){
    DSymbol symbol;

    _reusableSymbol.setChar(ch);
    symbol = (DSymbol) _specialSymbols.get(_reusableSymbol);
    if (symbol!=null)
      return symbol;
    if ((ch>=0 && ch<_symbols_char.length) && _symbols_char[ch]!=null)
      return _symbols_char[ch];
    else
      return _unknownSymbol;
  }


  public boolean addSymbol(int code, DSymbol symbol){
    if (code<0 || code>=_symbols_code.length)
      return false;
    _symbols_code[code] = symbol;
    _symbols_char[symbol.getChar()] = symbol;
    _repr = null;
    return true;
  }

  public String getName(){
    return _name;
  }

  public int size(){
    int i, size, num;

    size = _symbols_char.length;
    num=0;
    for (i=0;i<size;i++){
      if (_symbols_char[i]!=null) num++;
    }
    return (num);
  }

  public DSymbol[] toArray(){
    DSymbol[] symbols;
    int j, i, size;

    size = this.size();
    if (size==0)
      return null;
    symbols = new DSymbol[size];
    j=0;
    for (i=0;i<_symbols_char.length;i++){
      if (_symbols_char[i]!=null) {
        symbols[j] = _symbols_char[i];
        j++;
      }
    }
    return (symbols);
  }

  public Enumeration<DSymbol> symbols(){
    return new Enumeration<DSymbol>(){
      private boolean bInit = false;
      private DSymbol[] symbols;
      private int idx;

      private void init(){
        symbols = toArray();
        idx=-1;
        bInit = true;
      }

      public boolean hasMoreElements(){
        if (!bInit) init();
        if (symbols==null)
          return false;
        if ((idx+1) < symbols.length)
          return true;
        return false;
      }
      public DSymbol nextElement(){
        if (!bInit) init();
        if (symbols==null)
          return null;
        if ((idx+1) < symbols.length){
          idx++;
          return (symbols[idx]);
        }
        return null;
      }
    };
  }

  public String getSymbolsList(){
    if (_repr!=null)
      return _repr;
    StringBuffer buf = new StringBuffer();

    for (int i=0;i<_symbols_char.length;i++){
      if (_symbols_char[i]!=null)
        buf.append(_symbols_char[i].getChar());
    }
    _repr = buf.toString();
    return _repr;
  }
  private class SymbolCodeElement{
    private int     _code;
    private DSymbol _symbol;

    public SymbolCodeElement(){}

    public SymbolCodeElement(DSymbol symbol, int code){
      _code = code;
      _symbol = symbol;
    }

    public int getCode() {
      return _code;
    }

    public void setCode(int code) {
      this._code = code;
    }

    @SuppressWarnings("unused")
    public DSymbol getSymbol() {
      return _symbol;
    }

    @SuppressWarnings("unused")
    public void setSymbol(DSymbol symbol) {
      this._symbol = symbol;
    }

    public int hashCode(){
      return _code;
    }

    public boolean equals(Object o){
      if (o==null || (o instanceof SymbolCodeElement)==false)
        return false;
      return ((((SymbolCodeElement)o).getCode()==this.getCode()) ? true : false);
    }

  }
}
