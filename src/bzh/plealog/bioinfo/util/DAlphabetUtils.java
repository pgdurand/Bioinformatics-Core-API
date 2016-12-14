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

import java.awt.Color;
import java.util.Hashtable;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.DSymbolGraphics;

/**
 * Contains some utility methods to handle Alphabet of Symbols.
 * 
 * Notice: there where previously located in class DViewerSystem.
 * 
 * @author Patrick G. Durand
 */
public class DAlphabetUtils {
  private static char[] comp;
  private static Hashtable<String, DAlphabet> _alphabets = new Hashtable<String, DAlphabet>();

  static {
    comp = new char[255];
    for (int i = 0; i < 255; i++) {
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
  public static String reverse(String str) {
    StringBuffer szBuf = new StringBuffer();
    int i, size = str.length() - 1;

    for (i = size; i >= 0; i--) {
      szBuf.append(str.charAt(i));
    }
    return szBuf.toString();

  }

  /**
   * Complement a DNA letter.
   */
  public static char complement(char ch) {
    if (ch > 254)
      return '?';
    else
      return comp[ch];
  }

  /**
   * Complement a DNA string.
   */
  public static String complement(String str) {
    StringBuffer szBuf = new StringBuffer();
    int i, size = str.length();
    for (i = 0; i < size; i++) {
      szBuf.append(complement(str.charAt(i)));
    }
    return szBuf.toString();
  }

  /**
   * Reverse complement a DNA string.
   */
  public static String reverseComplement(String str) {
    return reverse(complement(str));
  }

  /**
   * Utility method to figure out whether a symbol is a particular one.
   * Particular symbols (gap, ...) are defined within interface DSymbol.
   */
  public static boolean isSpecialSymbol(DAlphabet alphabet, DSymbol symbol) {
    char ch;

    ch = alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE).getChar();
    if (ch == symbol.getChar())
      return true;
    ch = alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
    if (ch == symbol.getChar())
      return true;
    ch = alphabet.getSymbol(DSymbol.MATCH_SYMBOL_CODE).getChar();
    if (ch == symbol.getChar())
      return true;
    ch = alphabet.getSymbol(DSymbol.MISMATCH_SYMBOL_CODE).getChar();
    if (ch == symbol.getChar())
      return true;
    ch = alphabet.getSymbol(DSymbol.POSITIVE_SYMBOL_CODE).getChar();
    if (ch == symbol.getChar())
      return true;
    ch = alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar();
    if (ch == symbol.getChar())
      return true;
    return false;
  }

  /**
   * Returns the special symbols of a given Alphabet.
   */
  public static String getSpecialSymbols(DAlphabet alphabet) {
    StringBuffer buf = new StringBuffer();
    buf.append(alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE).getChar());
    buf.append(alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar());
    buf.append(alphabet.getSymbol(DSymbol.MATCH_SYMBOL_CODE).getChar());
    buf.append(alphabet.getSymbol(DSymbol.MISMATCH_SYMBOL_CODE).getChar());
    buf.append(alphabet.getSymbol(DSymbol.POSITIVE_SYMBOL_CODE).getChar());
    buf.append(alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar());
    return buf.toString();
  }

  private static DSymbol addCharInAlphabet(DAlphabet alphabet, char ch, int code) {
    DSymbol symb;

    symb = CoreSystemConfigurator.getSymbolFactory().createDSymbol(code, ch);
    alphabet.addSymbol(code, symb);
    return (symb);
  }

  public static DAlphabet getComparer_Alphabet() {
    DAlphabet alphabet;

    if (_alphabets.containsKey("Comparer")) {
      return ((DAlphabet) _alphabets.get("Comparer"));
    }
    String chars = new String("ACDEFGHIKLMNPQRSTVWYXacdefghiklmnpqrstvwyx");
    alphabet = CoreSystemConfigurator.getAlphabetFactory().createDAlphabet("Comparer", DAlphabet.OTHER_ALPHABET);
    int code_start = 6; // 0..5: reserved code, see DAlphabetImplem;
    for (int i = 0; i < chars.length(); i++) {
      addCharInAlphabet(alphabet, chars.charAt(i), code_start + i);
    }
    _alphabets.put("Comparer", alphabet);
    return alphabet;
  }

  public static DAlphabet getIUPAC_DNA_Alphabet() {
    DAlphabet alphabet;
    DSymbol symb;
    DSymbolGraphics a, g, c, t, n;

    if (_alphabets.containsKey("IUPAC_DNA")) {
      return ((DAlphabet) _alphabets.get("IUPAC_DNA"));
    }
    a = new DSymbolGraphics(Color.white, Color.red);
    // a.setNegativeColor(true);
    g = new DSymbolGraphics(Color.white, Color.green.darker());
    // g.setNegativeColor(true);
    c = new DSymbolGraphics(Color.white, Color.pink.darker());
    // c.setNegativeColor(true);
    t = new DSymbolGraphics(Color.white, Color.blue);
    // t.setNegativeColor(true);
    n = new DSymbolGraphics(Color.white, Color.gray);
    // n.setNegativeColor(true);
    alphabet = CoreSystemConfigurator.getAlphabetFactory().createDAlphabet("IUPAC_DNA", DAlphabet.DNA_ALPHABET);
    // standards nucleotides
    symb = addCharInAlphabet(alphabet, 'A', 6);// code: 0..5: reserved code, see
                                               // DAlphabetImplem;
    symb.setGraphics(a);
    symb = addCharInAlphabet(alphabet, 'G', 7);
    symb.setGraphics(g);
    symb = addCharInAlphabet(alphabet, 'C', 8);
    symb.setGraphics(c);
    symb = addCharInAlphabet(alphabet, 'T', 9);
    symb.setGraphics(t);
    symb = addCharInAlphabet(alphabet, 'N', 10);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'U', 11);
    symb.setGraphics(a);
    // special codes (see http://en.wikipedia.org/wiki/Fasta_format)
    symb = addCharInAlphabet(alphabet, 'R', 12);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'Y', 13);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'K', 14);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'M', 15);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'S', 16);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'W', 17);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'B', 18);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'D', 19);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'H', 20);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'V', 21);
    symb.setGraphics(n);
    symb = addCharInAlphabet(alphabet, 'X', 22);
    symb.setGraphics(n);
    _alphabets.put("IUPAC_DNA", alphabet);
    return alphabet;
  }

  public static DAlphabet getIUPAC_Protein_Alphabet() {
    DAlphabet alphabet;
    DSymbol symb;
    DSymbolGraphics acid, basic, pho, struct, cys, other;

    if (_alphabets.containsKey("IUPAC_Protein")) {
      return ((DAlphabet) _alphabets.get("IUPAC_Protein"));
    }
    acid = new DSymbolGraphics(Color.white, Color.red);
    basic = new DSymbolGraphics(Color.white, Color.blue);
    pho = new DSymbolGraphics(Color.white, Color.green.darker());
    struct = new DSymbolGraphics(Color.white, Color.pink);
    cys = new DSymbolGraphics(Color.white, Color.orange);
    other = new DSymbolGraphics(Color.white, Color.black);
    // gap = new DSymbolGraphics(Color.white, Color.lightGray);

    alphabet = CoreSystemConfigurator.getAlphabetFactory().createDAlphabet("IUPAC_Protein", DAlphabet.PROTEIN_ALPHABET);
    symb = addCharInAlphabet(alphabet, 'A', 6);// code: 0..5: reserved code, see
                                               // DAlphabetImplem;
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'B', 7);// ambiguous: Asp or Asn
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'C', 8);
    symb.setGraphics(cys);
    symb = addCharInAlphabet(alphabet, 'D', 9);
    symb.setGraphics(acid);
    symb = addCharInAlphabet(alphabet, 'E', 10);
    symb.setGraphics(acid);
    symb = addCharInAlphabet(alphabet, 'F', 11);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'G', 12);
    symb.setGraphics(struct);
    symb = addCharInAlphabet(alphabet, 'H', 13);
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'I', 14);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'J', 15);// ambiguous: Leu or Ile
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'K', 16);
    symb.setGraphics(basic);
    symb = addCharInAlphabet(alphabet, 'L', 17);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'M', 18);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'N', 19);
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'P', 20);
    symb.setGraphics(struct);
    symb = addCharInAlphabet(alphabet, 'Q', 22);
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'R', 23);
    symb.setGraphics(basic);
    symb = addCharInAlphabet(alphabet, 'S', 24);
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'T', 25);
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'U', 26);// selenocysteine
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'V', 27);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'W', 28);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'Y', 29);
    symb.setGraphics(pho);
    symb = addCharInAlphabet(alphabet, 'X', 30);
    symb.setGraphics(other);
    symb = addCharInAlphabet(alphabet, 'Z', 31);// ambiguous: Glu or Gln
    symb.setGraphics(other);

    _alphabets.put("IUPAC_Protein", alphabet);
    return alphabet;
  }

}
