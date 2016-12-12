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
