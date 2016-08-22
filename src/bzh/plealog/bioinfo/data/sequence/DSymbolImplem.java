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

import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.DSymbolGraphics;

/**
 * This is a default implementation of DSymbol.
 * 
 * @author Patrick G. Durand
 */
public class DSymbolImplem implements DSymbol {
    private int             _code;
    private char            _ch;
    private String          _repr;
    private DSymbolGraphics _graphics;
    
    public DSymbolImplem(){
    }

    public DSymbolImplem(int code, char ch){
        setCode(code);
        setChar(ch);
    }
    
    public void setChar(char ch){
        _ch = ch;
        _repr=new Character(ch).toString();
    }

    public char getChar(){
        return _ch;
    }

    public void setCode(int code){
        _code = code;
    }

    public int getCode(){
        return _code;
    }

    public void setGraphics(DSymbolGraphics g){
        _graphics = g;
    }
    
    public DSymbolGraphics getGraphics(){
        return _graphics;
    }

    public String toString(){
        //System.out.println(_repr);
        return _repr;
    }
    
    public int hashCode(){
    	return _ch;
    }
    
    public boolean equals(Object o){
    	if (o==null || (o instanceof DSymbol)==false)
    		return false;
    	return ((((DSymbol)o).getChar()==this.getChar()) ? true : false);
    }
}
