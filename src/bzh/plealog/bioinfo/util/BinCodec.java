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

import java.util.BitSet;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * Contains utility methods to encode back and forth protein or AND sequence
 * to a compact binary representation. The latter relies on a standard Java 
 * BitSet object.
 * 
 * @author Patrick G. Durand
 */
public class BinCodec {
  
  /**
   * Encode an integer value into a BitSet.
   * 
   * @param bits where to encode the integer
   * @param code the integer to encode with its binary representation into bits
   * @param encoding_size nb bits used to encode integer
   * @param seq_pos sequence location coordinate. Zero-based value.
   */
  private static void encode(BitSet bits, int code, int encoding_size, int seq_pos){
    // The binary representation of a integer implies that bits
    // are read from right to left (bit of low weight is on the right).
    // However, we code that binary representation from LEFT to
    // RIGHT in the BitSet
    int pos = seq_pos*encoding_size;
    while (code != 0) {
      // binary encoding will not exceed nbits
      bits.set(pos, (code & 1) != 0 ? true : false);
      pos++;
      code >>>= 1;
    }
  }

  /**
   * Computes number of bits required to encode all symbols of an alphabet.
   * 
   * @param alphabet the alphabet
   * 
   * @return number of bits required to encode all symbols using a compact
   * binary representation
   * */
  public static int getRequiredEncodingBits(DAlphabet alphabet) {
    return BinCodecUtils.binlog(BinCodecUtils.roundUpToTheNextHighestPowerOf2(alphabet.size()));
  }

  /**
   * Computes number of bits required to encode all symbols of an alphabet.
   * 
   * @param nb_symbols number of symbols contained in a particular alphabet
   * 
   * @return number of bits required to encode all symbols using a compact
   * binary representation
   * */
  public static int getRequiredEncodingBits(int nb_symbols) {
    return BinCodecUtils.binlog(BinCodecUtils.roundUpToTheNextHighestPowerOf2(nb_symbols));
  }

  /**
   * Reverse a BitSet while respecting its binary encoding.
   * 
   * @param bits the binary representation of a sequence
   * @param encoding_size number of bits required to encode all symbols 
   * of an alphabet. See getRequiredEncodingBits().
   * @param seq_length size of the sequence
   * 
   * @return the reverse representation of a sequence
   * */
  public static BitSet reverse(BitSet bits, int encoding_size, int seq_length){
    BitSet new_seq = new BitSet(seq_length * encoding_size + 1);
    int k=0;
    
    for(int i=seq_length-1;i>=0;i--){
      encode(new_seq, decode(bits, encoding_size, i), encoding_size, k++);
    }
    return new_seq;
  }
  
  /**
   * Complement a BitSet while respecting its binary encoding.
   * 
   * @param bits the binary representation of a sequence
   * @param alphabet the alphabet.
   * @param seq_length size of the sequence
   * 
   * @return the complement representation of a sequence
   * */
  public static BitSet complement(BitSet bits, DAlphabet alphabet, int seq_length){
    int encoding_size = getRequiredEncodingBits(alphabet);
    BitSet new_seq = new BitSet(seq_length * encoding_size + 1);
    int code;
    char ch;
    
    for(int i=0;i<seq_length;i++){
      code = decode(bits, encoding_size, i);
      ch = alphabet.getSymbol(code).getChar();
      ch = DAlphabetUtils.complement(ch);
      code = alphabet.getSymbol(ch).getCode();
      encode(new_seq, code, encoding_size, i);
    }
    return new_seq;
  }

  /**
   * ReverseComplement a BitSet while respecting its binary encoding.
   * 
   * @param bits the binary representation of a sequence
   * @param alphabet the alphabet.
   * @param seq_length size of the sequence
   * 
   * @return the complement representation of a sequence
   * */
  public static BitSet reverseComplement(BitSet bits, DAlphabet alphabet, int seq_length){
    int encoding_size = getRequiredEncodingBits(alphabet);
    BitSet new_seq = new BitSet(seq_length * encoding_size + 1);
    int k=0;
    int code;
    char ch;
    
    for(int i=seq_length-1;i>=0;i--){
      code = decode(bits, encoding_size, i);
      ch = alphabet.getSymbol(code).getChar();
      ch = DAlphabetUtils.complement(ch);
      code = alphabet.getSymbol(ch).getCode();
      encode(new_seq, code, encoding_size, k++);
    }
    return new_seq;
  }
  
  /**
   * Extract the subset of a binary representation of a sequence
   * while respecting its encoding.
   * 
   * @param bits source sequence
   * @param encoding_size number of bits required to encode all symbols 
   * of an alphabet. See getRequiredEncodingBits().
   * @param seq_from start location on the source sequence. Zero based value.
   * @param nletters number of letters to retain from seq_from.
   * 
   * @return a sub sequence
   * */
  public static BitSet subset(BitSet bits, int encoding_size, int seq_from, int nletters){
    int from = seq_from*encoding_size;
    return bits.get(from, from+(nletters*encoding_size)+1);
  }
  
  /**
   * Decode a particular location in a binary representation of a sequence.
   * 
   * @param bits the binary representation of the sequence
   * @param encoding_size number of bits required to encode all symbols 
   * of an alphabet. See getRequiredEncodingBits().
   * @param seq_pos sequence location coordinate. Zero-based value.
   * 
   * @return the integer code of the DSymbol located at location seq_pos
   */
  public static int decode(BitSet bits, int encoding_size, int seq_pos) {
    int decimal = 0;
    int p = 0;

    // see encodeSequence() method: bits are encoded from left to right
    // in the BitSet, which is the reverse of the classical binary
    // representation of an integer
    int k_start = seq_pos * encoding_size;
    int k_stop = k_start + encoding_size;
    for (int i = k_start; i < k_stop; i++) {
      decimal += (bits.get(i) ? 1 : 0) * BinCodecUtils.ipow(2, p);
      p++;
    }
    return decimal;
  }

  /**
   * Encode a sequence into a compact binary representation.
   * 
   * @param alphabet the alphabet used to encode the sequence
   * @param sequence the sequence to encode
   * 
   * @return a compact binary representation of a sequence
   * */
  public static BitSet encode(DAlphabet alphabet, String sequence) {
    // how many bits to encode all symbols?
    int nbits = BinCodecUtils.binlog(BinCodecUtils.roundUpToTheNextHighestPowerOf2(alphabet.size()));
    // Size of the sequence to encode
    int seq_length = sequence.length();

    // Will contain the binary coded sequence
    BitSet bSequence = new BitSet(seq_length * nbits + 1);
    DSymbol symbol;
    // Start the encoding procedure
    for (int i = 0; i < seq_length; i++) {
      // retrieve a symbol from alphabet given sequence's character
      symbol = alphabet.getSymbol(sequence.charAt(i));
      encode(bSequence, symbol.getCode(), nbits, i);
    }
    return bSequence;
  }

}
