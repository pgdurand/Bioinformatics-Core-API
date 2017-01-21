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

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.util.CoreUtil;
import bzh.plealog.bioinfo.util.DAlphabetUtils;
import bzh.plealog.bioinfo.util.ExportMonitor;

/**
 * This class is responsible for saving sequences into a Fasta formatted file.
 * 
 * @author Patrick G. Durand
 */
public class FastaExport {
  /** number of letters on each line of the Fasta file (default is 60) */
  private int _width = 60;

  private static final String NO_NAME = "no_name";
  private static final String NO_ID = "no_id";
  
  public FastaExport() {
  }

  /**
   * Returns the number of letters put on each line of the Fasta file.
   */
  public int getWidth() {
    return _width;
  }

  /**
   * Sets the number of letters put on each line of the Fasta file.
   */
  public void setWidth(int width) {
    _width = width;
  }

  /**
   * Export data.
   * 
   * @param os
   *          the OutputStream where to save data
   * @param seqs
   *          the sequence(s) to save
   * @param noSpecials
   *          if true, does not write out special symbols
   * 
   * @throws Exception
   *           this exception is thrown if something wrong occurred during save
   *           process.
   */
  public void export(OutputStream os, DSequence[] seqs, boolean noSpecials)
      throws Exception {
    PrintWriter writer;
    DSequenceInfo dsi;
    DSequence seq;
    DSymbol symbol;
    DAlphabet alphabet;
    int i, j, size, counter;

    if (os == null || seqs == null)
      return;
    writer = new PrintWriter(new BufferedOutputStream(os));
    for (i = 0; i < seqs.length; i++) {
      seq = seqs[i];
      dsi = seq.getSequenceInfo();
      writer.print(">");
      if (dsi != null && dsi.getName() != null) {
        writer.println(CoreUtil.replaceAll(dsi.getName(), ">", "|"));
      } else {
        writer.println("no name");
      }
      size = seq.size();
      counter = 0;
      alphabet = seq.getAlphabet();
      for (j = 0; j < size; j++) {
        symbol = seq.getSymbol(j);
        if (noSpecials) {
          if (DAlphabetUtils.isSpecialSymbol(alphabet, symbol)) {
            continue;
          }
        }
        writer.print(symbol.getChar());
        if (counter != 0 && (counter + 1) % _width == 0)
          writer.println();
        counter++;
      }
      writer.println();
    }
    writer.flush();
  }

  private String getSequenceID(DSequence seq){
    if (seq.getSequenceInfo()!=null && seq.getSequenceInfo().getId()!=null){
      return seq.getSequenceInfo().getId();
    }
    else{
      return NO_ID;
    }
  }
  private String getSequenceName(DSequence seq){
    if (seq.getSequenceInfo()!=null && seq.getSequenceInfo().getName()!=null){
      return seq.getSequenceInfo().getName();
    }
    else{
      return NO_NAME;
    }
  }

  /**
   * Export data.
   * 
   * @param os
   *          the OutputStream where to save data
   * @param seqs
   *          the sequence(s) to save
   * 
   * @throws Exception
   *           this exception is thrown if something wrong occurred during save
   *           process.
   */
  public void export(OutputStream os, DSequence[] seqs) throws Exception {
    this.export(os, seqs, null);
  }

  public void export(OutputStream os, DSequence[] seqs, String discardChars)
      throws Exception {
    PrintWriter writer;
    DSequence seq;
    String str;
    char ch;
    int i, j, size, counter;

    if (os == null || seqs == null)
      return;
    writer = new PrintWriter(new BufferedOutputStream(os));
    for (i = 0; i < seqs.length; i++) {
      seq = seqs[i];
      writer.print(">");
      writer.print(getSequenceID(seq));
      str = getSequenceID(seq);
      if (str != null && !str.equals(NO_NAME)) {
        writer.print(" ");
        writer.print(CoreUtil.replaceAll(str, ">", "|"));
      }
      writer.println();
      counter = 0;
      str = seq.toString();
      size = str.length();
      for (j = 0; j < size; j++) {
        ch = str.charAt(j);
        if (discardChars != null && discardChars.indexOf(ch) != -1) {
          continue;
        }
        writer.print(ch);
        if (counter != 0 && (counter + 1) % _width == 0)
          writer.println();
        counter++;
      }
      writer.println();
    }
    writer.flush();
  }

  public void export(OutputStream os, Enumeration<DSequence> seqs)
      throws Exception {
    export(os, seqs, null, null);
  }

  public void export(OutputStream os, Enumeration<DSequence> seqs,
      ExportMonitor monitor) throws Exception {
    export(os, seqs, null, monitor);
  }

  public void export(OutputStream os, Enumeration<DSequence> seqs,
      String discardChars) throws Exception {
    export(os, seqs, discardChars, null);
  }

  public void export(OutputStream os, Enumeration<DSequence> seqs,
      String discardChars, ExportMonitor monitor) throws Exception {
    PrintWriter writer;
    DSequence seq;
    String str;
    char ch;
    int j, size, counter;

    if (os == null || seqs == null)
      return;
    writer = new PrintWriter(new BufferedOutputStream(os));
    try {
      while (seqs.hasMoreElements()) {
        if (monitor != null && monitor.stopProcessing()) {
          throw new InterruptedException("export cancelled by user");
        }
        seq = seqs.nextElement();
        if (seq == null) {
          if (monitor != null) {
            monitor.addToProgress(1);
          }
          continue;
          // throw new Exception("sequence not found");
        }
        writer.print(">");
        writer.print(getSequenceID(seq));
        str = getSequenceName(seq);
        if (str != null && !str.equals(NO_NAME)) {
          writer.print(" ");
          writer.print(CoreUtil.replaceAll(str, ">", "|"));
        }
        writer.println();
        counter = 0;
        str = seq.toString();
        size = str.length();
        for (j = 0; j < size; j++) {
          ch = str.charAt(j);
          if (discardChars != null && discardChars.indexOf(ch) != -1) {
            continue;
          }
          writer.print(ch);
          if (counter != 0 && (counter + 1) % _width == 0)
            writer.println();
          counter++;
        }
        if (monitor != null) {
          monitor.addToProgress(1);
        }
        writer.println();
      }
    } finally {
      writer.flush();
    }
  }

  /**
   * Save a set of sequences within a temporary file.
   * 
   * @return a file or null if something wrong occurs.
   */
  /*public File saveSequences(FastaSequence[] fseqs) throws Exception {
    FileOutputStream fos = null;
    FastaExport exporter;
    File f;

    exporter = new FastaExport();
    f = File.createTempFile("kls", ".fas");
    fos = new FileOutputStream(f);
    exporter.export(fos, fseqs);
    try {
      if (fos != null) {
        fos.flush();
        fos.close();
      }
    } catch (Exception e) {
    }
    return f;
  }*/
}
