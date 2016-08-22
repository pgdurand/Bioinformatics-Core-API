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

import java.awt.Color;
import java.util.Hashtable;

/**
 * This class can be used as a unique wrapper for implementations 
 * of DAlphabetFactory, DSymbolFactory and DSequenceFactory interfaces.
 * 
 * @author Patrick G. Durand
 */
public class DViewerSystem {
    private static DAlphabetFactory          _AFactory;
    private static DSymbolFactory            _SFactory;
    private static DSequenceFactory          _SeqFactory;
    private static DSequenceAlignmentFactory _MSAFactory;
    private static DSequenceAlignedFactory   _SAFactory;
    private static BankSequenceInfoFactory   _BSIFactory;
    
    private static Hashtable<String, DAlphabet> _alphabets = new Hashtable<String, DAlphabet>();
    
    public static void setSequenceAlignmentFactory(DSequenceAlignmentFactory factory){
        if (factory!=null)
        	_MSAFactory=factory;
    }
    
    public static DSequenceAlignmentFactory getSequenceAlignmentFactory(){
    	return _MSAFactory;
    }
    
    public static void setSequenceAlignedFactory(DSequenceAlignedFactory factory){
        if (factory!=null)
        	_SAFactory=factory;
    }
    
    public static DSequenceAlignedFactory getSequenceAlignedFactory(){
    	return _SAFactory;
    }

    public static void setAlphabetFactory(DAlphabetFactory factory) {
        if (factory!=null)
            _AFactory=factory;
    }

    public static void setSymbolFactory(DSymbolFactory factory) {
        if (factory!=null)
            _SFactory=factory;
    }

    public static void setSequenceFactory(DSequenceFactory factory) {
        if (factory!=null)
        _SeqFactory=factory;
    }

    public static DAlphabetFactory getAlphabetFactory() {
        return (_AFactory);
    }

    public static DSymbolFactory getSymbolFactory() {
        return (_SFactory);
    }
    
    public static DSequenceFactory getSequenceFactory() {
        return (_SeqFactory);
    }
    public static void setBankSequenceInfoFactory(BankSequenceInfoFactory bsiFactory){
      _BSIFactory = bsiFactory;
    }
    public static BankSequenceInfoFactory getBankSequenceInfoFactory(){
      return _BSIFactory;
    }
    private static DSymbol addCharInAlphabet(DAlphabet alphabet, char ch){
        DSymbol symb;
        
        symb = _SFactory.createDSymbol(ch, ch);
        alphabet.addSymbol(ch, symb);
        return (symb);
    }
    
    public static DAlphabet getComparer_Alphabet(){
        DAlphabet      alphabet;
        
        if (_alphabets.containsKey("Comparer")){
            return ((DAlphabet) _alphabets.get("Comparer"));
        }
        alphabet = _AFactory.createDAlphabet("Comparer", DAlphabet.OTHER_ALPHABET);
        addCharInAlphabet(alphabet, 'A');
        addCharInAlphabet(alphabet, 'C');
        addCharInAlphabet(alphabet, 'D');
        addCharInAlphabet(alphabet, 'E');
        addCharInAlphabet(alphabet, 'F');
        addCharInAlphabet(alphabet, 'G');
        addCharInAlphabet(alphabet, 'H');
        addCharInAlphabet(alphabet, 'I');
        addCharInAlphabet(alphabet, 'K');
        addCharInAlphabet(alphabet, 'L');
        addCharInAlphabet(alphabet, 'M');
        addCharInAlphabet(alphabet, 'N');
        addCharInAlphabet(alphabet, 'P');
        addCharInAlphabet(alphabet, 'Q');
        addCharInAlphabet(alphabet, 'R');
        addCharInAlphabet(alphabet, 'S');
        addCharInAlphabet(alphabet, 'T');
        addCharInAlphabet(alphabet, 'V');
        addCharInAlphabet(alphabet, 'W');
        addCharInAlphabet(alphabet, 'Y');
        addCharInAlphabet(alphabet, 'X');
        addCharInAlphabet(alphabet, 'a');
        addCharInAlphabet(alphabet, 'c');
        addCharInAlphabet(alphabet, 'd');
        addCharInAlphabet(alphabet, 'e');
        addCharInAlphabet(alphabet, 'f');
        addCharInAlphabet(alphabet, 'g');
        addCharInAlphabet(alphabet, 'h');
        addCharInAlphabet(alphabet, 'i');
        addCharInAlphabet(alphabet, 'k');
        addCharInAlphabet(alphabet, 'l');
        addCharInAlphabet(alphabet, 'm');
        addCharInAlphabet(alphabet, 'n');
        addCharInAlphabet(alphabet, 'p');
        addCharInAlphabet(alphabet, 'q');
        addCharInAlphabet(alphabet, 'r');
        addCharInAlphabet(alphabet, 's');
        addCharInAlphabet(alphabet, 't');
        addCharInAlphabet(alphabet, 'v');
        addCharInAlphabet(alphabet, 'w');
        addCharInAlphabet(alphabet, 'y');
        addCharInAlphabet(alphabet, 'x');
        _alphabets.put("Comparer",alphabet);
        return alphabet;
    }

    public static DAlphabet getIUPAC_DNA_Alphabet(){
        DAlphabet      alphabet;
        DSymbol        symb;
        DSymbolGraphics a, g, c, t, n;
        
        if (_alphabets.containsKey("IUPAC_DNA")){
            return ((DAlphabet) _alphabets.get("IUPAC_DNA"));
        }
        a = new DSymbolGraphics(Color.white, Color.red);
        //a.setNegativeColor(true);
        g = new DSymbolGraphics(Color.white, Color.green.darker());
        //g.setNegativeColor(true);
        c = new DSymbolGraphics(Color.white, Color.pink.darker());
        //c.setNegativeColor(true);
        t = new DSymbolGraphics(Color.white, Color.blue);
        //t.setNegativeColor(true);
        n = new DSymbolGraphics(Color.white, Color.gray);
        //n.setNegativeColor(true);
        alphabet = _AFactory.createDAlphabet("IUPAC_DNA", DAlphabet.DNA_ALPHABET);
        //standards nucleotides
        symb = addCharInAlphabet(alphabet, 'A');
        symb.setGraphics(a);
        symb = addCharInAlphabet(alphabet, 'G');
        symb.setGraphics(g);
        symb = addCharInAlphabet(alphabet, 'C');
        symb.setGraphics(c);
        symb = addCharInAlphabet(alphabet, 'T');
        symb.setGraphics(t);
        symb = addCharInAlphabet(alphabet, 'N');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'U');
        symb.setGraphics(a);
        //special codes (see http://en.wikipedia.org/wiki/Fasta_format)
        symb = addCharInAlphabet(alphabet, 'R');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'Y');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'K');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'M');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'S');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'W');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'B');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'D');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'H');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'V');
        symb.setGraphics(n);
        symb = addCharInAlphabet(alphabet, 'X');
        symb.setGraphics(n);
        _alphabets.put("IUPAC_DNA",alphabet);
        return alphabet;
    }

    public static String inverse(String str){
    	StringBuffer szBuf = new StringBuffer();
    	int          i, size = str.length()-1;
    	
    	for(i=size;i>=0;i--){
    		szBuf.append(str.charAt(i));
    	}
    	return szBuf.toString();
    	
    }
    public static String complement(String str){
    	StringBuffer szBuf = new StringBuffer();
    	int          i, size = str.length();
    	char         ch;
    	for(i=0;i<size;i++){
    		ch = str.charAt(i);
    		switch(ch){
    		case 'A':szBuf.append('T');break;
    		case 'C':szBuf.append('G');break;
    		case 'G':szBuf.append('C');break;
    		case 'T':szBuf.append('A');break;
    		case 'R':szBuf.append('Y');break;
    		case 'Y':szBuf.append('R');break;
    		case 'U':szBuf.append('A');break;
    		case 'K':szBuf.append('M');break;
    		case 'M':szBuf.append('K');break;
    		case 'B':szBuf.append('V');break;
    		case 'V':szBuf.append('B');break;
    		case 'D':szBuf.append('H');break;
    		case 'H':szBuf.append('D');break;
    		case 'a':szBuf.append('t');break;
    		case 'c':szBuf.append('g');break;
    		case 'g':szBuf.append('c');break;
    		case 't':szBuf.append('a');break;
    		case 'r':szBuf.append('y');break;
    		case 'y':szBuf.append('r');break;
    		case 'u':szBuf.append('a');break;
    		case 'k':szBuf.append('m');break;
    		case 'm':szBuf.append('k');break;
    		case 'b':szBuf.append('v');break;
    		case 'v':szBuf.append('b');break;
    		case 'd':szBuf.append('h');break;
    		case 'h':szBuf.append('d');break;
        	default:
        		szBuf.append(ch);
    		}
    	}
    	return szBuf.toString();
    }
    public static String inverseComplement(String str){
    	return inverse(complement(str));
    }
    public static DAlphabet getIUPAC_Protein_Alphabet(){
        DAlphabet       alphabet;
        DSymbol         symb;
        DSymbolGraphics acid, basic, pho, struct, cys, other;
        
        if (_alphabets.containsKey("IUPAC_Protein")){
            return ((DAlphabet) _alphabets.get("IUPAC_Protein"));
        }
        acid = new DSymbolGraphics(Color.white, Color.red);
        basic = new DSymbolGraphics(Color.white, Color.blue);
        pho = new DSymbolGraphics(Color.white, Color.green.darker());
        struct = new DSymbolGraphics(Color.white, Color.pink);
        cys = new DSymbolGraphics(Color.white, Color.orange);
        other = new DSymbolGraphics(Color.white, Color.black);
        //gap = new DSymbolGraphics(Color.white, Color.lightGray);
        
        alphabet = _AFactory.createDAlphabet("IUPAC_Protein", DAlphabet.PROTEIN_ALPHABET);
        symb = addCharInAlphabet(alphabet, 'A');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'B');//ambiguous: Asp or Asn
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'C');
        symb.setGraphics(cys);
        symb = addCharInAlphabet(alphabet, 'D');
        symb.setGraphics(acid);
        symb = addCharInAlphabet(alphabet, 'E');
        symb.setGraphics(acid);
        symb = addCharInAlphabet(alphabet, 'F');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'G');
        symb.setGraphics(struct);
        symb = addCharInAlphabet(alphabet, 'H');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'I');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'J');//ambiguous: Leu or Ile
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'K');
        symb.setGraphics(basic);
        symb = addCharInAlphabet(alphabet, 'L');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'M');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'N');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'P');
        symb.setGraphics(struct);
        symb = addCharInAlphabet(alphabet, 'Q');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'R');
        symb.setGraphics(basic);
        symb = addCharInAlphabet(alphabet, 'S');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'T');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'U');//selenocysteine
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'V');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'W');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'Y');
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'X');
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'Z');//ambiguous: Glu or Gln
        symb.setGraphics(other);
        
        _alphabets.put("IUPAC_Protein",alphabet);
        return alphabet;
    }
    /**
     * Utility method to figure out whether a symbol is a particular one. 
     * Particular symbols (gap, ...) are defined within interface DSymbol.
     */
    public static boolean isSpecialSymbol(DAlphabet alphabet, DSymbol symbol){
        char ch;
        
        ch = alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE).getChar();
        if (ch==symbol.getChar())
            return true;
        ch = alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
        if (ch==symbol.getChar())
            return true;
        ch = alphabet.getSymbol(DSymbol.MATCH_SYMBOL_CODE).getChar();
        if (ch==symbol.getChar())
            return true;
        ch = alphabet.getSymbol(DSymbol.MISMATCH_SYMBOL_CODE).getChar();
        if (ch==symbol.getChar())
            return true;
        ch = alphabet.getSymbol(DSymbol.POSITIVE_SYMBOL_CODE).getChar();
        if (ch==symbol.getChar())
            return true;
        ch = alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar();
        if (ch==symbol.getChar())
            return true;
        return false;
    }
    /**
     * Returns the special symbols of a given Alphabet.
     */
    public static String getSpecialSymbols(DAlphabet alphabet){
    	StringBuffer buf = new StringBuffer();
    	buf.append(alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE).getChar());
    	buf.append(alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar());
    	buf.append(alphabet.getSymbol(DSymbol.MATCH_SYMBOL_CODE).getChar());
    	buf.append(alphabet.getSymbol(DSymbol.MISMATCH_SYMBOL_CODE).getChar());
    	buf.append(alphabet.getSymbol(DSymbol.POSITIVE_SYMBOL_CODE).getChar());
    	buf.append(alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar());
    	return buf.toString();
    }
}
