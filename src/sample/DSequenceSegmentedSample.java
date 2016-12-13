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
package sample;

import java.util.ArrayList;
import java.util.List;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceFactory;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.data.sequence.DSequenceImplem;
import bzh.plealog.bioinfo.util.DAlphabetUtils;

public class DSequenceSegmentedSample {

  public static DSequence createSegmentedSequence(DSequence query, DSequence hit, int seqSize) {
    DSequenceFactory sFactory;
    DRulerModel rModel;
    DSequence seqTmp;
    DLocation loc;
    List<DLocation> parts;
    ArrayList<DSequence> seqPart;
    ArrayList<DLocation> locPart;
    int i, nParts, from, to;

    parts = query.getSequenceParts();

    nParts = parts.size();
    rModel = query.getRulerModel();
    if (rModel == null)
      return null;
    seqPart = new ArrayList<DSequence>();
    locPart = new ArrayList<DLocation>();
    for (i = 0; i < nParts; i++) {
      // location of query seq part in absolute coord system
      loc = (DLocation) parts.get(i);
      seqTmp = hit.getSubSequence(loc.getFrom(), loc.getTo() + 1, false);
      // retrieves coord in query coordinates
      from = rModel.getSeqPos(loc.getFrom()) - 1;
      to = rModel.getSeqPos(loc.getTo()) - 1;
      // these coordinates are then used to position hit parts
      loc.setFrom(from);
      loc.setTo(to);
      seqPart.add(seqTmp);
      locPart.add(loc);
    }
    sFactory = DViewerSystem.getSequenceFactory();
    return (sFactory.getSequence(seqPart, locPart, hit.getAlphabet(), seqSize));
  }

  public static void test1() {
    DAlphabet alph;
    DSequenceImplem qSeq1, qSeq2, hSeq1, hSeq2;
    String qSeqStr1 = "AGCT--ATGC";
    String qSeqStr2 = "AAACAGC--TAT";
    String hSeqStr1 = "ACGTCA--CG";
    String hSeqStr2 = "AAA--GCTTT-T";
    DSequence seg1, seg2;
    StringBuffer szBuf;
    int i, size;

    alph = DAlphabetUtils.getIUPAC_DNA_Alphabet();
    qSeq1 = new DSequenceImplem(qSeqStr1, alph);
    hSeq1 = new DSequenceImplem(hSeqStr1, alph);
    qSeq1.createRulerModel(5, 1);
    hSeq1.createRulerModel(12, 1);

    qSeq2 = new DSequenceImplem(qSeqStr2, alph);
    hSeq2 = new DSequenceImplem(hSeqStr2, alph);
    qSeq2.createRulerModel(1, 1);
    hSeq2.createRulerModel(6, 1);

    size = 15;
    seg1 = createSegmentedSequence(qSeq1, hSeq1, size);
    seg2 = createSegmentedSequence(qSeq2, hSeq2, size);

    System.out.println("Segment 1: " + seg1 + " (" + seg1.size() + ", " + seg1.getGapContent() + ")");
    szBuf = new StringBuffer();
    for (i = 0; i < size; i++) {
      szBuf.append(seg1.getSymbol(i).getChar());
    }
    System.out.println("Segment 1 complet: " + szBuf.toString());
    System.out.println("Segment 1 parts: " + seg1.getSequenceParts());
    System.out.println("Segment 2: " + seg2 + " (" + seg2.size() + ", " + seg2.getGapContent() + ")");
    szBuf = new StringBuffer();
    for (i = 0; i < size; i++) {
      szBuf.append(seg2.getSymbol(i).getChar());
    }
    System.out.println("Segment 2 complet: " + szBuf.toString());
    System.out.println("Segment 2 parts: " + seg2.getSequenceParts());
  }

  public static void test2() {
    DAlphabet alph;
    DSequenceImplem qSeq1, hSeq1;
    String qSeqStr1 = "--AGCT--ATGCTA";
    String hSeqStr1 = "TTACGTCA--CG--";
    DSequence seg1;
    StringBuffer szBuf;
    int i, size;

    alph = DAlphabetUtils.getIUPAC_DNA_Alphabet();
    qSeq1 = new DSequenceImplem(qSeqStr1, alph);
    hSeq1 = new DSequenceImplem(hSeqStr1, alph);
    qSeq1.createRulerModel(5, 1);
    hSeq1.createRulerModel(10, 1);

    size = 15;
    seg1 = createSegmentedSequence(qSeq1, hSeq1, size);

    System.out.println("Segment 1: " + seg1 + " (" + seg1.size() + ", " + seg1.getGapContent() + ")");
    szBuf = new StringBuffer();
    for (i = 0; i < size; i++) {
      szBuf.append(seg1.getSymbol(i).getChar());
    }
    System.out.println("Segment 1 complet: " + szBuf.toString());
    System.out.println("Segment 1 parts: " + seg1.getSequenceParts());
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    CoreSystemConfigurator.initializeSystem();
    test2();
  }

}
