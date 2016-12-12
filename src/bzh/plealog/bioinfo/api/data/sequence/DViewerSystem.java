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
    private static DSymbol addCharInAlphabet(DAlphabet alphabet, char ch, int code){
        DSymbol symb;
        
        symb = _SFactory.createDSymbol(code, ch);
        alphabet.addSymbol(code, symb);
        return (symb);
    }
    
    public static DAlphabet getComparer_Alphabet(){
        DAlphabet      alphabet;
        
        if (_alphabets.containsKey("Comparer")){
            return ((DAlphabet) _alphabets.get("Comparer"));
        }
        String chars = new String("ACDEFGHIKLMNPQRSTVWYXacdefghiklmnpqrstvwyx");
        alphabet = _AFactory.createDAlphabet("Comparer", DAlphabet.OTHER_ALPHABET);
        int code_start = 6; //0..5: reserved code, see DAlphabetImplem;
        for(int i=0;i<chars.length();i++){
          addCharInAlphabet(alphabet, chars.charAt(i), code_start+i);
        }
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
        symb = addCharInAlphabet(alphabet, 'A', 6);//code: 0..5: reserved code, see DAlphabetImplem;
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
        //special codes (see http://en.wikipedia.org/wiki/Fasta_format)
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
        _alphabets.put("IUPAC_DNA",alphabet);
        return alphabet;
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
        symb = addCharInAlphabet(alphabet, 'A', 6);//code: 0..5: reserved code, see DAlphabetImplem;
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'B', 7);//ambiguous: Asp or Asn
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
        symb = addCharInAlphabet(alphabet, 'J', 15);//ambiguous: Leu or Ile
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
        symb = addCharInAlphabet(alphabet, 'U', 26);//selenocysteine
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'V', 27);
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'W', 28);
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'Y', 29);
        symb.setGraphics(pho);
        symb = addCharInAlphabet(alphabet, 'X', 30);
        symb.setGraphics(other);
        symb = addCharInAlphabet(alphabet, 'Z', 31);//ambiguous: Glu or Gln
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
