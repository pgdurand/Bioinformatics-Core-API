package bzh.plealog.bioinfo.util;

public class BinCodecUtils {
  /**
   * Fast method to compute an integer power.
   * 
   * @param base base
   * @param exp exponent
   * 
   * @return base to the power of exponent
   */
  public static int ipow(int base, int exp) {
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
  public static int roundUpToTheNextHighestPowerOf2(int v) {
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
  public static int binlog(int bits) // returns 0 for bits=0
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

}
