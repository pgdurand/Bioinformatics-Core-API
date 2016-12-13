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

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.plealog.genericapp.api.log.EZLogger;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceException;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.util.BinCodec;

/**
 * This is a default implementation of DSequence.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceImplem implements DSequence {
  private BitSet _sequence;
  private DAlphabet _alphabet;
  private DSymbol _letter = new DSymbolImplem(0, ' ');
  private DRulerModel _ruler;
  private DSequenceInfo _dsi;
  private List<DLocation> _parts;
  private int _gapContent = -1;
  private int _nbits;
  private int _size;

  /**
   * Default constructor is not available.
   */
  private DSequenceImplem() {
  }

  /**
   * Constructor from a String.
   * 
   * @param seq the sequence to encode
   * @param alphabet the alphabet used to encode the string
   */
  public DSequenceImplem(String seq, DAlphabet alphabet) {
    this();
    if (seq == null)
      throw new RuntimeException("Sequence is not defined.");
    if (alphabet == null)
      throw new RuntimeException("Alphabet is not defined.");
    _alphabet = alphabet;
    _nbits = BinCodec.getRequiredEncodingBits(_alphabet);
    _size = seq.length();
    _sequence = BinCodec.encode(_alphabet, seq);
  }

  /**
   * Constructor from a Reader.
   * 
   * @param reader the reader giving access to the sequence to encode
   * @param alphabet the alphabet used to encode the string
   */
  public DSequenceImplem(Reader reader, DAlphabet alphabet) throws IOException{
    this();
    
    if (reader == null)
      throw new RuntimeException("Reader is not defined.");
    if (alphabet == null)
      throw new RuntimeException("Alphabet is not defined.");
    
    _alphabet = alphabet;
    _nbits = BinCodec.getRequiredEncodingBits(_alphabet);
    _sequence = new BitSet();
    _size=0;

    int ichr;
    char ch;
    DSymbol sym;
    
    // we must have chars, so we cast
    while ((ichr = reader.read()) != -1) {
      //most Alphabet only enable upper case letters
      ch = Character.toUpperCase((char) ichr);
      sym = alphabet.getSymbol(ch);
      if (sym == null) {
        EZLogger.warn("Symbol unknown for: " + ch + ". Continuing...");
        continue;
      }
      BinCodec.encode(_sequence, sym.getCode(), _nbits, _size);
      _size++;
    }
  }

  protected DSequenceImplem(BitSet seq, DAlphabet alphabet, int seq_size) {
    this();
    if (seq == null)
      throw new RuntimeException("Sequence is not defined.");
    if (alphabet == null)
      throw new RuntimeException("Alphabet is not defined.");
    _alphabet = alphabet;
    _nbits = BinCodec.getRequiredEncodingBits(_alphabet);
    _size = seq_size;
    _sequence = seq;
  }

  public DSymbol getSymbol(int idx) {
    if (idx >= 0 && idx < _sequence.length()) {
      return (_alphabet.getSymbol(BinCodec.decode(_sequence, _nbits, idx)));
    } else {
      return _letter;
    }
  }

  public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse) {
    DSequenceImplem seq;
    int startPos = -1, i, size, increment;
    BitSet subSeq;

    size = _sequence.length();
    if (idxFrom < 0 || idxTo < 0 || idxFrom == size || idxTo > size || idxFrom >= idxTo)
      return null;

    size = idxTo - idxFrom;
    subSeq = BinCodec.subset(_sequence, _nbits, idxFrom, size);

    if (inverse) {
      if (_alphabet.getType() == DAlphabet.DNA_ALPHABET) {
        subSeq = BinCodec.reverseComplement(subSeq, _alphabet, _size);
      } else {
        subSeq = BinCodec.reverse(subSeq, _nbits, _size);
      }
    }
    seq = new DSequenceImplem(subSeq, _alphabet, size);
    if (this.getSequenceInfo() != null) {
      seq.setSequenceInfo((DSequenceInfo) this.getSequenceInfo().clone());
    }
    if (_ruler != null) {
      // if gap, ruler returns -1, so need to get first valid position
      if (inverse) {
        for (i = idxTo - 1; i >= idxFrom; i--) {
          startPos = _ruler.getSeqPos(i);
          if (startPos != -1) {
            break;
          }
        }
        increment = -((DRulerModelImplem) _ruler).getIncrement();
      } else {
        for (i = idxFrom; i < idxTo; i++) {
          startPos = _ruler.getSeqPos(i);
          if (startPos != -1) {
            break;
          }
        }
        increment = ((DRulerModelImplem) _ruler).getIncrement();
      }
      seq.createRulerModel(startPos, increment);
    }
    return seq;
  }

  public int size() {
    return _size;
  }

  public DRulerModel createRulerModel(int startPos, int increment) {
    if (_ruler == null)
      _ruler = new DRulerModelImplem(this, startPos, increment);
    return _ruler;
  }

  public DRulerModel createRulerModel(int[] coord) {
    if (coord.length != this.size())
      throw new DSequenceException("invalid coord array size.");
    if (_ruler == null)
      _ruler = new DRulerModelImplem(coord);
    return _ruler;
  }

  public DRulerModel getRulerModel() {
    return _ruler;
  }

  public String toString() {
    StringBuffer buf;

    buf = new StringBuffer();

    for (int i = 0; i < _size; i++) {
      buf.append(_alphabet.getSymbol(BinCodec.decode(_sequence, _nbits, i)).getChar());
    }

    return buf.toString();
  }

  public DAlphabet getAlphabet() {
    return _alphabet;
  }

  /**
   * Implementation of DSequence interface. Please note that this method
   * optimizes memory by computing once the returned List. So do NOT edit the
   * DLocation objects contained in the list.
   */
  public List<DLocation> getSequenceParts() {
    ArrayList<DLocation> list;
    DLocation pos;
    char gapCh, curCh;
    int i, size, from, to;
    boolean readSeq = false;

    if (_parts != null)
      return _parts;
    list = new ArrayList<DLocation>();
    gapCh = _alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
    size = _sequence.length();
    from = to = 0;
    for (i = 0; i < size; i++) {
      curCh = _alphabet.getSymbol(BinCodec.decode(_sequence, _nbits, i)).getChar();
      if (curCh == gapCh && readSeq == true) {
        to = i - 1;
        pos = new DLocation(from, to);
        list.add(pos);
        readSeq = false;
      } else if (curCh != gapCh) {
        if (!readSeq) {
          readSeq = true;
          from = i;
        }
      }
    }
    if (readSeq) {
      pos = new DLocation(from, size - 1);
      list.add(pos);
    }
    _parts = list;
    return list;
  }

  public int getGapContent() {
    int i, size, gaps = 0;
    char ch;

    if (_gapContent >= 0)
      return _gapContent;
    ch = _alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
    size = _sequence.length();
    for (i = 0; i < size; i++) {
      if (_alphabet.getSymbol(BinCodec.decode(_sequence, _nbits, i)).getChar() == ch) {
        gaps++;
      }
    }
    _gapContent = gaps;
    return gaps;
  }

  public DSequenceInfo getSequenceInfo() {
    return _dsi;
  }

  public void setSequenceInfo(DSequenceInfo dsi) {
    _dsi = dsi;
  }
}
