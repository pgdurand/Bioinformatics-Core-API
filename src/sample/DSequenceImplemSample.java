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

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.data.sequence.DSequenceImplem;

public class DSequenceImplemSample {
    
	public static void test1(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
		String          seqStr = "ATAT";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	System.out.println("Parts for "+ seqStr +": "+seq.getSequenceParts());
	}

	public static void test2(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
		String          seqStr = "AT-AT";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	System.out.println("Parts for "+ seqStr +": "+seq.getSequenceParts());
	}
	public static void test3(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
		String          seqStr = "AT--AT";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	System.out.println("Parts for "+ seqStr +": "+seq.getSequenceParts());
	}
	public static void test4(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
		String          seqStr = "-AT--AT";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	System.out.println("Parts for "+ seqStr +": "+seq.getSequenceParts());
	}
	public static void test5(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
		String          seqStr = "--AT--TA--A-A";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	System.out.println("Parts for "+ seqStr +": "+seq.getSequenceParts());
	}
	
	public static void test6(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "ATGCATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	subSeq = seq.getSubSequence(0, 0, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (0,0) "+ subSeq);
    	subSeq = seq.getSubSequence(-1, 0, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (-1,0) "+ subSeq);
    	subSeq = seq.getSubSequence(0, -1, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (0,-1) "+ subSeq);
    	subSeq = seq.getSubSequence(8, 0, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (8,0) "+ subSeq);
    	subSeq = seq.getSubSequence(0, 9, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (0,9) "+ subSeq);
    	subSeq = seq.getSubSequence(5, 1, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (5,1) "+ subSeq);
	}
	public static void test7(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "ATGCATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	subSeq = seq.getSubSequence(0, 1, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (0,1) "+ subSeq);
    	subSeq = seq.getSubSequence(1, 5, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (1,5) "+ subSeq);
    	subSeq = seq.getSubSequence(0, 8, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Sub-sequence (0,8) "+ subSeq);
	}
	public static void test8(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "ATGCATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	subSeq = seq.getSubSequence(0, 1, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Source ruler model "+ seq.getRulerModel());
    	System.out.println("Sub-sequence (0,1) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
	}
	public static void test9(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "ATGCATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	seq.createRulerModel(1, 1);
    	subSeq = seq.getSubSequence(0, 8, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Source ruler model "+ seq.getRulerModel());
    	System.out.println("Sub-sequence (0,8) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
    	subSeq = seq.getSubSequence(2, 6, false);
    	System.out.println("Sub-sequence (2,6) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
	}
	public static void test10(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "ATG--CATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	seq.createRulerModel(1, 1);
    	subSeq = seq.getSubSequence(0, 8, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Source ruler model "+ seq.getRulerModel());
    	System.out.println("Sub-sequence (0,8) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
    	subSeq = seq.getSubSequence(2, 6, false);
    	System.out.println("Sub-sequence (2,6) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
	}
	public static void test11(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "--ATG--CATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	seq.createRulerModel(1, 1);
    	subSeq = seq.getSubSequence(0, 8, false);
    	System.out.println("Source sequence "+ seq);
    	System.out.println("Source ruler model "+ seq.getRulerModel());
    	System.out.println("Sub-sequence (0,8) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
    	subSeq = seq.getSubSequence(3, 7, false);
    	System.out.println("Sub-sequence (3,7) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
    	subSeq = seq.getSubSequence(3, 5, false);
    	System.out.println("Sub-sequence (3,5) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
    	subSeq = seq.getSubSequence(1, 5, false);
    	System.out.println("Sub-sequence (1,5) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
	}
	public static void test12(){
    	DAlphabet       alph;
    	DSequenceImplem seq;
    	DSequence       subSeq;
		String          seqStr = "ATGCATGC";
		
    	alph = DViewerSystem.getIUPAC_DNA_Alphabet();
    	seq = new DSequenceImplem(seqStr, alph);
    	seq.createRulerModel(8,-1);
    	subSeq = seq.getSubSequence(0, 8, true);
    	System.out.println("Sub-sequence (0,8) "+ subSeq);
    	System.out.println("Sub-sequence ruler model "+ subSeq.getRulerModel());
	}
    
	public static void main(String[] args){
		CoreSystemConfigurator.initializeSystem();
//    	test1();
//    	test2();
//    	test3();
//    	test4();
//    	test5();
    	test12();
    }

}
