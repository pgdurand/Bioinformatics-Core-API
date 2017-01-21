/* Copyright (C) 2003-2017 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.sequence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignmentViewPort;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.io.PrintfFormat;

import com.plealog.genericapp.api.log.EZLogger;

public class GCGMsaExport {

  private DSequenceAlignmentViewPort _msa;

  /** number of letters on each row */
  private static final int           MSA_WIDTH        = 50;
  /** number of letters on each block */
  private static final int           BLOCK_WIDTH      = 10;
  /** MSF file header */
  private static final PrintfFormat  HEADER_FORMATTER = new PrintfFormat(
                                                          " %s  MSF: %i  Type: %s  PileUp-like  Check: %6i  ..");
  /** Sequence line header */
  private static final PrintfFormat  SEQ_FORMATER     = new PrintfFormat(
                                                          " Name: %s       Len:%6i  Check:%6i  Weight:  1.00");

  private static String EXPOR_ERR ="Error while exporting MSA: ";
  
  private int                        _blockSize       = BLOCK_WIDTH;
  private int                        _msaWidth        = MSA_WIDTH;
  private boolean                    _dumpHeader      = true;

  /**
   * No default constructor.
   */
  private GCGMsaExport() {
  }

  /**
   * Constructor.
   * 
   * @param model the multiple sequence alignment to export
   */
  public GCGMsaExport(DSequenceAlignmentViewPort model) {
    this();
    _msa = model;
  }

  /**
   * Constructor.
   * 
   * @param model the multiple sequence alignment to export
   * @param blockSize size of sequence block
   * @param msaWidth width of MSA
   * @param dumpHeader figure out whether or not to dump sequence headers
   */
  public GCGMsaExport(DSequenceAlignmentViewPort model, int blockSize,
      int msaWidth, boolean dumpHeader) {
    this(model);
    _blockSize = blockSize;
    _msaWidth = msaWidth;
    _dumpHeader = dumpHeader;
  }

  /**
   * Computes a checksum value a row of the MSA.
   */
  private long getChkSumFromRow(DAlphabet alphabet, int row) {
    int i;
    char ch;
    long check = 0;

    for (i = 0; i < _msa.columns(); i++) {
      ch = _msa.getSymbol(i, row).getChar();
      if (ch == alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar()
          || ch == alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar()
          || ch == alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE).getChar())
        ch = '.';
      check += ((i % 57) + 1) * ch;
    }
    return (check % 10000);
  }

  /**
   * Resets the buffer used to dump MSA data. Resetting means to set a space
   * character at every position of the buffer.
   * 
   * @param buf
   *          the buffer to reset
   * @param len
   *          size of the buffer
   */
  private void resetBuf(StringBuffer buf, int len) {
    for (int i = 0; i < len; i++)
      buf.setCharAt(i, ' ');
  }

  /**
   * Adds a header to the buffer.
   */
  private void setHeader(StringBuffer buf, String header) {
    for (int i = 0; i < header.length(); i++)
      buf.setCharAt(i, header.charAt(i));
  }

  /**
   * Adds a numerical ruler to the buffer.
   * 
   * @param buf
   *          the buffer
   * @param size
   *          buffer&apos; size
   * @param from
   *          starting coord of the numerical ruler
   * @param to
   *          ending coord of the numerical ruler
   */
  private void setRuler(StringBuffer buf, int size, int from, int to,
      int blockStart) {
    String val;
    int i, len;

    val = String.valueOf(from);
    len = val.length();
    for (i = 0; i < len; i++) {
      buf.setCharAt(blockStart + i, val.charAt(i));
    }
    val = String.valueOf(to);
    size -= val.length();
    // do not allow labels overlap
    if (size <= blockStart + len + 1)
      return;
    for (i = 0; i < val.length(); i++) {
      buf.setCharAt(size + i, val.charAt(i));
    }
  }

  /**
   * Adds MSA sequence data to the buffer
   * 
   * @param buf
   *          the buffer
   * @param alphabet
   *          the MSA alphabet
   * @param from
   *          starting position within the MSA absolute coordinate system
   * @param to
   *          ending position within the MSA absolute coordinate system
   * @param row
   *          for which sequence do we have to dump data
   * 
   * */
  private void setSequence(StringBuffer buf, DAlphabet alphabet, int from,
      int to, int row, int blockStart) {
    int i = 0, count = 0;
    char ch;

    while (from <= to) {
      ch = _msa.getSymbol(from, row).getChar();
      if (ch == alphabet.getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar()
          || ch == alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar()
          || ch == alphabet.getSymbol(DSymbol.UNKNOWN_SYMBOL_CODE).getChar())
        ch = '.';
      buf.setCharAt(blockStart + i, ch);
      from++;
      i++;
      if ((count + 1) % 10 == 0)
        i++;
      count++;
    }
  }

  private int checkSizeForRuler(int size, int from, int blockStart) {
    int val;

    val = blockStart + String.valueOf(from).length();
    if (val > size)
      size = val;
    return size;
  }

  /**
   * Export.
   * 
   * @param f file
   * 
   * @return true is export was successfully done. False otherwise and a message
   *         is then added to this class logger.
   */
  public boolean export(File f) {
    boolean bRet = true;

    try (FileOutputStream fos = new FileOutputStream(f)){
      bRet = export(fos);
    } catch (Exception e) {
      EZLogger.warn(EXPOR_ERR + e);
      bRet = false;
    }
    return bRet;
  }

  /**
   * Export.
   * 
   * @param os output stream
   * 
   * @return true is export was succefully done. False otherwise and a message
   *         is then added to this class logger.
   */
  public boolean export(OutputStream os) {
    PrintWriter writer;
    StringBuffer buf;
    String header;
    DAlphabet alphabet;
    long[] chkSum;
    long totalChkSum = 0;
    int i, size, from, to, start, stop, len, blockStart, headerWidth = 0;

    try {
      alphabet = _msa.getSequence(0).getSequence().getAlphabet();
      writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)));
      // compute checksums
      chkSum = new long[_msa.rows()];
      for (i = _msa.fromRow(); i <= _msa.toRow(); i++) {
        chkSum[i] = getChkSumFromRow(alphabet, i);
        totalChkSum += chkSum[i];
      }
      totalChkSum = totalChkSum % 10000;
      // write header
      start = _msa.fromColumn();
      stop = _msa.toColumn();
      size = stop - start + 1;
      if (_dumpHeader) {
        writer.println(HEADER_FORMATTER.sprintf(new Object[] { "msa",
            new Integer(size),
            alphabet.getType() == DAlphabet.PROTEIN_ALPHABET ? "P" : "N",
            new Long(totalChkSum) }));
        writer.println();
      }
      // write seq list
      for (i = _msa.fromRow(); i <= _msa.toRow(); i++) {
        totalChkSum += chkSum[i];
        header = _msa.rowHeader(i);
        len = header.length();
        if (len > headerWidth)
          headerWidth = len;
        if (_dumpHeader) {
          writer.println(SEQ_FORMATER.sprintf(new Object[] { header,
              new Integer(size), new Long(chkSum[i]) }));
        }
      }
      blockStart = headerWidth + 2;
      if (_dumpHeader) {
        writer.println();
        writer.println("//");
        writer.println();
      }

      // write MSA
      // buf is a reusable buffer
      buf = new StringBuffer();
      while (start <= stop) {
        from = start;
        to = Math.min(start + _msaWidth - 1, stop);
        len = to - from + 1;
        // total width of a data line. Data line = header + MSA letters
        // Header width = BLOCK_START letters
        // MSA width = 'len' letters + 1 space character every BLOCK_WIDTH
        // letters
        size = blockStart + len + ((len - 1) / _blockSize);
        // +1: values are zero-based, switch to one-based
        // to display MSA coord system
        size = checkSizeForRuler(size, from + 1, blockStart);
        // recompute buf size
        buf.setLength(size);
        // puts space ch at each position of the buffer
        resetBuf(buf, size);
        // adds the numerical ruler (+1: values are zero-based, switch to
        // one-based
        // to display MSA coord system)
        setRuler(buf, size, from + 1, to + 1, blockStart);
        writer.println(buf.toString());
        // dumps each sequence
        for (i = _msa.fromRow(); i <= _msa.toRow(); i++) {
          resetBuf(buf, size);
          // put seq name (row header)
          header = _msa.rowHeader(i);
          // if (header.length()>HEADER_WIDTH)
          // header = header.substring(0, HEADER_WIDTH);
          setHeader(buf, header);
          // adds sequence letters
          setSequence(buf, alphabet, from, to, i, blockStart);
          writer.println(buf.toString());
        }
        writer.println();
        start += _msaWidth;
      }
      writer.flush();

    } catch (Exception e) {
      EZLogger.warn(EXPOR_ERR + e);
      return false;
    }

    return true;
  }
}
