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
package bzh.plealog.bioinfo.data.sequence;

import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAligned;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignmentViewPort;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.stat.PositionSpecificMatrix;

/**
 * This is a implementation of DSequenceAlignmentViewPort enabling to export a
 * multiple sequence alignment in various formats.
 * 
 * @author Patrick G. Durand
 */
public class ExportableMSA implements DSequenceAlignmentViewPort {

  private DSequenceAlignment _msa;
  private List<String>       _headers;
  private int                _minRow;
  private int                _maxRow;
  private int                _minCol;
  private int                _maxCol;

  /**
   * Constructor.
   * 
   * @param msa
   *          the multiple sequence alignment to export
   * @param headers
   *          header names of each sequence in the MSA
   * @param minRow
   *          region to export
   * @param maxRow
   *          region to export
   * @param minCol
   *          region to export
   * @param maxCol
   *          region to export
   * */
  public ExportableMSA(DSequenceAlignment msa, List<String> headers,
      int minRow, int maxRow, int minCol, int maxCol) {
    _msa = msa;
    _headers = headers;
    _minRow = minRow;
    _maxRow = maxRow;
    _minCol = minCol;
    _maxCol = maxCol;
  }

  /**
   * @see DSequenceAlignmentViewPort#fromColumn()
   * */
  public int fromColumn() {
    return _minCol;
  }

  /**
   * @see DSequenceAlignmentViewPort#fromRow()
   * */
  public int fromRow() {
    return _minRow;
  }

  /**
   * @see DSequenceAlignmentViewPort#toColumn()
   * */
  public int toColumn() {
    return _maxCol;
  }

  /**
   * @see DSequenceAlignmentViewPort#toRow()
   * */
  public int toRow() {
    return _maxRow;
  }

  /**
   * @see DSequenceAlignmentViewPort#rowHeader(int)
   * */
  public String rowHeader(int row) {
    return (String) _headers.get(row);
  }

  /**
   * @see DSequenceAlignment#columns()
   * */
  public int columns() {
    return _msa.columns();
  }

  /**
   * @see DSequenceAlignment#getPositionSpecificMatrix(String)
   * */
  public PositionSpecificMatrix getPositionSpecificMatrix(String type) {
    return _msa.getPositionSpecificMatrix(type);
  }

  /**
   * @see DSequenceAlignment#getSequence(int)
   * */
  public DSequenceAligned getSequence(int row) {
    return _msa.getSequence(row);
  }

  /**
   * @see DSequenceAlignment#getSymbol(int, int)
   * */
  public DSymbol getSymbol(int col, int row) {
    return _msa.getSymbol(col, row);
  }

  /**
   * @see DSequenceAlignment#getType()
   * */
  public int getType() {
    return _msa.getType();
  }

  /**
   * @see DSequenceAlignment#rows()
   * */
  public int rows() {
    return _msa.rows();
  }

  /**
   * @see DSequenceAlignment#setSpecialSequences(DSequence, DSequence)
   * */
  public void setSpecialSequences(DSequence consensus, DSequence refSeq) {
    // there is no need to implement this method to export MSA
  }
}
