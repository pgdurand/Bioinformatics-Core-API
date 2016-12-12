package test;

import static org.junit.Assert.assertTrue;

import java.util.BitSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.data.sequence.DSequenceImplem;
import bzh.plealog.bioinfo.util.BinCodec;
import bzh.plealog.bioinfo.util.DAlphabetUtils;

/**
 * Test class for BinCodec and BitSet-based implementation of DSequenceImplem.
 * 
 * In addition to be a JUnit test class, one can see how to use the BinCodec class.
 * 
 * @author Patrick G. Durand
 * */
public class BinCodecTest {

  private static final String protein = "MASEFKKKLFWRAVVAEFLATTLFVFISIGSALGFKYPVGNNQTAVQDNVKVSLAFGLSIATLAQSVG";
  private static final int protein_size = protein.length();
  private static final String dna = "ACGTRYUKMBVDH";
  private static final int dna_size = dna.length();
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // requires to use DAlphabet / DSequence factories
    CoreSystemConfigurator.initializeSystem();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testAlphabetSize() {
    int nbits = BinCodec.getRequiredEncodingBits(4);
    assertTrue(nbits == 2);
    nbits = BinCodec.getRequiredEncodingBits(5);
    assertTrue(nbits == 3);
    nbits = BinCodec.getRequiredEncodingBits(8);
    assertTrue(nbits == 3);
    nbits = BinCodec.getRequiredEncodingBits(12);
    assertTrue(nbits == 4);
    nbits = BinCodec.getRequiredEncodingBits(24);
    assertTrue(nbits == 5);
    nbits = BinCodec.getRequiredEncodingBits(32);
    assertTrue(nbits == 5);
    nbits = BinCodec.getRequiredEncodingBits(33);
    assertTrue(nbits == 6);
    DAlphabet alphabet = DViewerSystem.getIUPAC_Protein_Alphabet();
    nbits = BinCodec.getRequiredEncodingBits(alphabet);
    assertTrue(alphabet.size() == 25);
    assertTrue(nbits == 5);
    alphabet = DViewerSystem.getIUPAC_DNA_Alphabet();
    nbits = BinCodec.getRequiredEncodingBits(alphabet);
    assertTrue(alphabet.size() == 17);
    assertTrue(nbits == 5);
    alphabet = DViewerSystem.getComparer_Alphabet();
    nbits = BinCodec.getRequiredEncodingBits(alphabet);
    assertTrue(alphabet.size() == 42);
    assertTrue(nbits == 6);
  }

  private void testAlphabetEncoding(DAlphabet alphabet) {
    // we get the bits encoding size for this alphabet, i.e. number of bits
    // needed
    // to encode all symbols of this alphabet
    int nbits = BinCodec.getRequiredEncodingBits(alphabet);

    // we get a sample sequence: protein letter symbols + special authorize
    // chars (e.g. gaps)
    String sequence = alphabet.getSymbolsList() + "*- ";

    // let's encode the sequence to its binary (BitSet based) representation
    BitSet bSequence = BinCodec.encode(alphabet, sequence);

    // check: test the decode operation
    int code, dcode;
    DSymbol symbol;
    for (int i = 0; i < sequence.length(); i++) {
      // symbol from the sequence
      symbol = alphabet.getSymbol(sequence.charAt(i));
      // integer code from that symbol
      code = symbol.getCode();

      // get the code from the binary representation of the sequence
      dcode = BinCodec.decode(bSequence, nbits, i);

      // check for equality between String sequence and BitSet sequence content
      // with respect to the alphabet
      assertTrue(code == dcode);
      assertTrue(alphabet.getSymbol(code).getChar() == alphabet.getSymbol(dcode).getChar());
    }
  }

  @Test
  public void testProteinAlphabetEncoding() {
    // wet get the Protein alphabet
    testAlphabetEncoding(DViewerSystem.getIUPAC_Protein_Alphabet());
  }

  @Test
  public void testDNAAlphabetEncoding() {
    // wet get the Protein alphabet
    testAlphabetEncoding(DViewerSystem.getIUPAC_DNA_Alphabet());
  }

  @Test
  public void testComparerAlphabetEncoding() {
    // wet get the Protein alphabet
    testAlphabetEncoding(DViewerSystem.getComparer_Alphabet());
  }

  @Test
  public void testDSequenceImplem() {
    DAlphabet alphabet = DViewerSystem.getIUPAC_Protein_Alphabet();
    DSequence seq = new DSequenceImplem(protein, alphabet);

    assertTrue(seq.size() == protein_size);
    assertTrue(seq.toString().equals(protein));

    DSymbol osym, dsym;

    for (int i = 0; i < protein_size; i++) {
      osym = alphabet.getSymbol(protein.charAt(i));
      dsym = seq.getSymbol(i);

      assertTrue(osym.getChar() == dsym.getChar());
    }
  }

  @Test
  public void testDSequenceImplem2() {
    DAlphabet alphabet = DViewerSystem.getIUPAC_Protein_Alphabet();
    DSequence seq = new DSequenceImplem(protein, alphabet);

    String sub_protein = protein.substring(8, 25);
    DSequence sub_seq = seq.getSubSequence(8, 25, false);

    assertTrue(sub_protein.length() == sub_seq.size());
    assertTrue(sub_seq.toString().equals(sub_protein));

    DSymbol osym, dsym;

    for (int i = 0; i < sub_protein.length(); i++) {
      osym = alphabet.getSymbol(sub_protein.charAt(i));
      dsym = sub_seq.getSymbol(i);

      assertTrue(osym.getChar() == dsym.getChar());
    }
  }
  
  @Test
  public void testBitSetReverse() {
    // Get protein alphabet
    DAlphabet alphabet = DViewerSystem.getIUPAC_Protein_Alphabet();
    // Get its binary encoding size; i.e. number of bits required to
    // binary encore all symbols of the alphabet
    int nbits = BinCodec.getRequiredEncodingBits(alphabet);
    // Encode the protein into its binary representation
    BitSet bSequence = BinCodec.encode(alphabet, protein);
    // Binary reverse the encoded sequence
    BitSet bReverseSequence = BinCodec.reverse(bSequence, nbits, protein.length());

    //check if ok
    StringBuffer buf = new StringBuffer();
    int code;
    DSymbol sym;

    for (int i = 0; i < protein.length(); i++) {
      code = BinCodec.decode(bReverseSequence, nbits, i);
      sym = alphabet.getSymbol(code);
      buf.append(sym.getChar());
    }
    assertTrue(DAlphabetUtils.reverse(protein).equals(buf.toString()));
  }
  
  @Test
  public void testBitSetComplement(){
    DAlphabet alphabet = DViewerSystem.getIUPAC_DNA_Alphabet();
    // Get its binary encoding size; i.e. number of bits required to
    // binary encore all symbols of the alphabet
    int nbits = BinCodec.getRequiredEncodingBits(alphabet);
    // Encode the protein into its binary representation
    BitSet bSequence = BinCodec.encode(alphabet, dna);
    // Binary reverse the encoded sequence
    BitSet bComplementSequence = BinCodec.complement(bSequence, alphabet, dna_size);
    
    // check if ok
    StringBuffer buf = new StringBuffer();
    int code;
    DSymbol sym;

    for (int i = 0; i < dna_size; i++) {
      code = BinCodec.decode(bComplementSequence, nbits, i);
      sym = alphabet.getSymbol(code);
      buf.append(sym.getChar());
    }
    assertTrue(DAlphabetUtils.complement(dna).equals(buf.toString()));
  }

  @Test
  public void testBitSetReverseComplement(){
    DAlphabet alphabet = DViewerSystem.getIUPAC_DNA_Alphabet();
    // Get its binary encoding size; i.e. number of bits required to
    // binary encore all symbols of the alphabet
    int nbits = BinCodec.getRequiredEncodingBits(alphabet);
    // Encode the protein into its binary representation
    BitSet bSequence = BinCodec.encode(alphabet, dna);
    // Binary reverse the encoded sequence
    BitSet bReverseComplementSequence = BinCodec.reverseComplement(bSequence, alphabet, dna_size);
    
    // check if ok
    StringBuffer buf = new StringBuffer();
    int code;
    DSymbol sym;

    for (int i = 0; i < dna_size; i++) {
      code = BinCodec.decode(bReverseComplementSequence, nbits, i);
      sym = alphabet.getSymbol(code);
      buf.append(sym.getChar());
    }
    assertTrue(DAlphabetUtils.reverseComplement(dna).equals(buf.toString()));
  }

  private void testBitSubSet(int from, int nletters){
    DAlphabet alphabet = DViewerSystem.getIUPAC_DNA_Alphabet();
    // Get its binary encoding size; i.e. number of bits required to
    // binary encore all symbols of the alphabet
    int nbits = BinCodec.getRequiredEncodingBits(alphabet);
    // Encode the protein into its binary representation
    BitSet bSequence = BinCodec.encode(alphabet, dna);
    // Binary reverse the encoded sequence
    BitSet subSequence = BinCodec.subset(bSequence, nbits, from, nletters);
    
    // check if ok
    StringBuffer buf = new StringBuffer();
    int code;
    DSymbol sym;

    for (int i = 0; i < nletters; i++) {
      code = BinCodec.decode(subSequence, nbits, i);
      sym = alphabet.getSymbol(code);
      buf.append(sym.getChar());
    }
    assertTrue(dna.substring(from, from+nletters).equals(buf.toString()));
  }

  @Test
  public void testBitSubSet(){
    testBitSubSet(0, 1);
    testBitSubSet(2, 5);
    testBitSubSet(0, dna.length());
  }
}
