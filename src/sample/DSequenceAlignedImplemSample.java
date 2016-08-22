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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAligned;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignedFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceFactory;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.data.sequence.DSequenceImplem;

public class DSequenceAlignedImplemSample {

	public static void test1(){
    	DAlphabet       alph;
    	DSequenceImplem qSeq, hSeq;
    	DSequence       seqTmp;
    	DLocation       loc;
		String          qSeqStr = "AAACAGC--TAT";//"AGCT--ATGC";
		String          hSeqStr = "AAA--GCTTT-T";//"ACGTCA--CG";
		List<DLocation> parts;
		int             i, size;
		StringBuffer    szBuf;
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	qSeq = new DSequenceImplem(qSeqStr, alph);
    	hSeq = new DSequenceImplem(hSeqStr, alph);
    	qSeq.createRulerModel(5, 1);
    	hSeq.createRulerModel(12, 1);
    	
    	parts = qSeq.getSequenceParts();
    	
    	size = parts.size();
    	szBuf = new StringBuffer();
    	for(i=0;i<size;i++){
    		loc = (DLocation) parts.get(i);
    		seqTmp = hSeq.getSubSequence(loc.getFrom(), loc.getTo()+1, false);
    		szBuf.append(seqTmp.toString());
    	}
    	System.out.print(szBuf);
	}

	public static DSequenceAligned getSA(DSequence query, DSequence hit){
		DSequenceFactory        sFactory;
		DRulerModel             rModel;
		DSequenceAlignedFactory saFactory;
		DSequence               seqTmp;
    	DLocation               loc;
		List<DLocation>         parts;
		StringBuffer            szBuf;
		int                     i, size, startPos=0;
		
    	parts = query.getSequenceParts();
    	
    	size = parts.size();
    	szBuf = new StringBuffer();
    	rModel = query.getRulerModel();
    	for(i=0;i<size;i++){
    		loc = (DLocation) parts.get(i);
    		if (i==0 && rModel!=null)
    			startPos=rModel.getSeqPos(loc.getFrom())-1;
    		seqTmp = hit.getSubSequence(loc.getFrom(), loc.getTo()+1, false);
    		szBuf.append(seqTmp.toString());
    	}
    	sFactory = DViewerSystem.getSequenceFactory();
    	saFactory = DViewerSystem.getSequenceAlignedFactory();
    	seqTmp = sFactory.getSequence(new StringReader(szBuf.toString()), hit.getAlphabet());
    	return (saFactory.getDSequenceAligned(seqTmp, startPos));
	}
	public static void test2(){
    	DAlphabet        alph;
    	DSequenceImplem  qSeq1, qSeq2, hSeq1, hSeq2;
    	DSequenceAligned sa;
		String           qSeqStr1 = "AGCT--ATGC";
		String           qSeqStr2 = "AAACAGC--TAT";
		String           hSeqStr1 = "ACGTCA--CG";
		String           hSeqStr2 = "AAA--GCTTT-T";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	qSeq1 = new DSequenceImplem(qSeqStr1, alph);
    	hSeq1 = new DSequenceImplem(hSeqStr1, alph);
    	qSeq1.createRulerModel(5, 1);
    	hSeq1.createRulerModel(12, 1);
    	
    	qSeq2 = new DSequenceImplem(qSeqStr2, alph);
    	hSeq2 = new DSequenceImplem(hSeqStr2, alph);
    	qSeq2.createRulerModel(1, 1);
    	hSeq2.createRulerModel(6, 1);

    	sa = getSA(qSeq1, hSeq1);
    	System.out.println(sa.getStartPosition()+" : "+sa.getSequence().toString());
    	sa = getSA(qSeq2, hSeq2);
    	System.out.println(sa.getStartPosition()+" : "+sa.getSequence().toString());
	}

	public static void test3(){
    	DAlphabet          alph;
    	DSequenceImplem    qSeq1, qSeq2, hSeq1, hSeq2;
    	DSequenceAlignment msa;
		String             qSeqStr1 = "AGCT--ATGC";
		String             qSeqStr2 = "AAACAGC--TAT";
		String             hSeqStr1 = "ACGTCA--CG";
		String             hSeqStr2 = "AAA--GCTTT-T";
		ArrayList<DSequenceAligned> seqs;
		int                i, j;
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	qSeq1 = new DSequenceImplem(qSeqStr1, alph);
    	hSeq1 = new DSequenceImplem(hSeqStr1, alph);
    	qSeq1.createRulerModel(5, 1);
    	hSeq1.createRulerModel(12, 1);
    	
    	qSeq2 = new DSequenceImplem(qSeqStr2, alph);
    	hSeq2 = new DSequenceImplem(hSeqStr2, alph);
    	qSeq2.createRulerModel(1, 1);
    	hSeq2.createRulerModel(6, 1);

    	seqs = new ArrayList<DSequenceAligned>();
    	seqs.add(getSA(qSeq1, hSeq1));
    	seqs.add(getSA(qSeq2, hSeq2));
    	msa = DViewerSystem.getSequenceAlignmentFactory().getDSequenceAlignment(seqs, 15, 
    			DSequenceAlignment.STANDARD_ALIGNMENT);
    	
    	if (msa==null){
    		System.err.println("error is msa: "+null);
    		return;
    	}
    	System.out.println("AAACAGCTATGCTAT");
    	for (i=0;i<msa.rows();i++){
        	for (j=0;j<msa.columns();j++){
        		System.out.print(msa.getSymbol(j,i).getChar());
        	}
        	System.out.println();
    	}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CoreSystemConfigurator.initializeSystem();
    	test3();
	}

}
