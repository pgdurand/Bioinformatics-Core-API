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
package bzh.plealog.bioinfo.data.searchresult;

import java.io.StringReader;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.util.DAlphabetUtils;

/**
 * This is a default implementation of interface BHspSequence.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence
 */
public class ISRHspSequence implements SRHspSequence {
  private static final long serialVersionUID = 1557966304878520220L;
  /**
   * @serial
   */
  private int type;
  /**
   * @serial
   */
  private int from;
  /**
   * @serial
   */
  private int to;
  /**
   * @serial
   */
  private int frame;
  /**
   * @serial
   */
  private String sequence;
  /**
   * @serial
   */
  private FeatureTable fTable;

  private transient int realSeqSize;
  private transient DSequence dSequence;
  @SuppressWarnings("unused")
  private transient int nGaps;

  public ISRHspSequence() {
  }

  public SRHspSequence clone(boolean shallow) {
    ISRHspSequence hsp = new ISRHspSequence();
    hsp.copy(this, shallow);
    return hsp;
  }

  public void copy(ISRHspSequence src, boolean shallow) {
    this.setType(src.getType());
    this.setFrom(src.getFrom());
    this.setTo(src.getTo());
    this.setFrame(src.getFrame());
    this.setSequence(src.getSequence());
    this.setSeqFullSize(src.getSeqFullSize());
    if (!shallow) {
      // staring with 2.6: does not clone to save memory
      /*
       * ft = src.getFeatures(); if (ft!=null) this.setFeatures((FeatureTable)
       * ft.clone());
       */
      this.setFeatures(src.getFeatures());
    }
  }

  public int getFrom() {
    return from;
  }

  public void setFrom(int val) {
    from = val;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int val) {
    to = val;
  }

  public int getType() {
    return type;
  }

  public void setType(int t) {
    type = t;
  }

  public int getGaps() {
    // return nGaps;
    if (dSequence == null)
      return 0;
    return dSequence.getGapContent();
  }

  public void setGaps(int val) {
    nGaps = val;
  }

  public int getFrame() {
    return frame;
  }

  public void setFrame(int val) {
    frame = val;
  }

  public FeatureTable getFeatures() {
    return fTable;
  }

  public void setFeatures(FeatureTable ft) {
    if (ft != null && ft.isValid(Math.min(from, to), Math.max(from, to))) {
      fTable = ft;
    } else {
      fTable = null;
    }
  }

  public String getSequence() {
    return sequence;
  }

  public void setSequence(String s) {
    this.sequence = s;
  }

  public DSequence getSequence(SRHsp hsp) {
    if (sequence == null)
      return null;
    if (dSequence != null)
      return dSequence;
    if (type == SRHspSequence.TYPE_MIDLINE) {
      dSequence = DViewerSystem.getSequenceFactory().getSequence(new StringReader(sequence),
          DAlphabetUtils.getComparer_Alphabet());
    } else {
      dSequence = DViewerSystem.getSequenceFactory().getSequence(new StringReader(sequence),
          (hsp.isProteic() ? DAlphabetUtils.getIUPAC_Protein_Alphabet() : DAlphabetUtils.getIUPAC_DNA_Alphabet()));
      int increment = SRUtils.getIncrement(getFrom(), getTo(), hsp.getScores().getAlignLen() - getGaps());
      dSequence.createRulerModel(getFrom(), increment);
    }
    return dSequence;
  }

  // Added to compute coverage %
  public int getSeqFullSize() {
    return realSeqSize;
  }

  public void setSeqFullSize(int val) {
    realSeqSize = val;
  }

}
