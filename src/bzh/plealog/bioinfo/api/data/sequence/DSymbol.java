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

/**
 * This interface defines a sequence symbol.
 * 
 * @author Patrick G. Durand
 */
public interface DSymbol {
    public static final int UNKNOWN_SYMBOL_CODE = -1;
    
    public static final int GAP_SYMBOL_CODE = -2;
    
    public static final int MATCH_SYMBOL_CODE = -3;

    public static final int MISMATCH_SYMBOL_CODE = -4;

    public static final int POSITIVE_SYMBOL_CODE = -5;
    
    public static final int ANY_SYMBOL_CODE = -6;

    public static final int SPACE_SYMBOL_CODE = -7;

    /**
     * Sets the character representation of this symbol. This character
     * is usually used for display purpose.
     */
    public void setChar(char ch);
    /**
     * Returns the character representation of this symbol.
     */
    public char getChar();
    /**
     * Sets the encoding code associated to this symbol.
     */
    public void setCode(int code);
    /**
     * Returns the encoding code associated to this symbol.
     */
    public int getCode();
    /**
     * Sets the default graphical properties associated to this symbol.
     */
    public void setGraphics(DSymbolGraphics g);
    /**
     * Returns the default graphical properties associated to this symbol.
     */
    public DSymbolGraphics getGraphics();
    /**
     * Forces the implementation of toString method.
     */
    public String toString();
}
