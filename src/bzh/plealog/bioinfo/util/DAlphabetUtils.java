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
package bzh.plealog.bioinfo.util;

/**
 * Contains some utility methods.
 * 
 * Notice: there where previoulsy located in class DViewerSystem.
 * 
 * @author Patrick G. Durand
 * */
public class DAlphabetUtils {
  private static char[] comp;
  
  static {
    comp = new char[255];
    for(int i=0;i<255;i++){
      comp[i] = '?';
    }
    // these standard characters are encoded as ASCII values.
    // So we use that property.
    comp['A'] = 'T';
    comp['C'] = 'G';
    comp['G'] = 'C';
    comp['T'] = 'A';
    comp['R'] = 'Y';
    comp['Y'] = 'R';
    comp['U'] = 'A';
    comp['K'] = 'M';
    comp['M'] = 'K';
    comp['B'] = 'V';
    comp['V'] = 'B';
    comp['D'] = 'H';
    comp['H'] = 'D';
    comp['a'] = 't';
    comp['c'] = 'g';
    comp['g'] = 'c';
    comp['t'] = 'a';
    comp['r'] = 'y';
    comp['y'] = 'r';
    comp['u'] = 'a';
    comp['k'] = 'm';
    comp['m'] = 'k';
    comp['b'] = 'v';
    comp['v'] = 'b';
    comp['d'] = 'h';
    comp['h'] = 'd';
  }
  /**
   * Reverse a string.
   */
  public static String reverse(String str){
    StringBuffer szBuf = new StringBuffer();
    int          i, size = str.length()-1;
    
    for(i=size;i>=0;i--){
      szBuf.append(str.charAt(i));
    }
    return szBuf.toString();
    
  }
  
  /**
   * Complement a DNA letter.
   */
  public static char complement(char ch){
    if (ch>254)
      return '?';
    else
      return comp[ch];
  }
  
  /**
   * Complement a DNA string.
   */
  public static String complement(String str){
    StringBuffer szBuf = new StringBuffer();
    int          i, size = str.length();
    for(i=0;i<size;i++){
      szBuf.append(complement(str.charAt(i)));
    }
    return szBuf.toString();
  }
  
  /**
   * Reverse complement a DNA string.
   */
  public static String reverseComplement(String str){
    return reverse(complement(str));
  }

}
