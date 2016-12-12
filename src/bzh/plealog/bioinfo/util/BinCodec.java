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
   * Fast method to compute an integer power.
   * 
   * @param base base
   * @param exp exponent
   * 
   * @return base to the power of exponent
   */
  private static int ipow(int base, int exp) {
    // from http://stackoverflow.com/a/101613
    // computing 1 million pow with ipow: 9ms
    // same with Math.pow: 13 ms
    int result = 1;
    while (exp != 0) {
      if ((exp & 1) == 1)
        result *= base;
      exp >>= 1;
      base *= base;
    }

    return result;
  }

  /**
   * Compute the power of 2 which produces an number containing some particular 
   * value.
   * 
   * @param v the value for which we need to find the power of 2 such that
   * number is greater than or equal to v.
   * 
   * @return a number such that 2 to the power of that number contains v
   */
  private static int roundUpToTheNextHighestPowerOf2(int v) {
    // from: http://graphics.stanford.edu/~seander/bithacks.html#RoundUpPowerOf2
    v--;
    v |= v >> 1;
    v |= v >> 2;
    v |= v >> 4;
    v |= v >> 8;
    v |= v >> 16;
    v++;
    return v;
  }

  /**
   * Fast method to compute log base 2 of an integer.
   * */
  private static int binlog(int bits) // returns 0 for bits=0
  {
    // from: http://stackoverflow.com/a/3305710
    int log = 0;
    if ((bits & 0xffff0000) != 0) {
      bits >>>= 16;
      log = 16;
    }
    if (bits >= 256) {
      bits >>>= 8;
      log += 8;
    }
    if (bits >= 16) {
      bits >>>= 4;
      log += 4;
    }
    if (bits >= 4) {
      bits >>>= 2;
      log += 2;
    }
    return log + (bits >>> 1);
  }
  
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
    return binlog(roundUpToTheNextHighestPowerOf2(alphabet.size()));
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
    return binlog(roundUpToTheNextHighestPowerOf2(nb_symbols));
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
    return reverse(complement(bits, alphabet, seq_length), getRequiredEncodingBits(alphabet), seq_length);
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
      decimal += (bits.get(i) ? 1 : 0) * ipow(2, p);
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
    int nbits = binlog(roundUpToTheNextHighestPowerOf2(alphabet.size()));
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
