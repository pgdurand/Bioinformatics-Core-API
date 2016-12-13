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

import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.data.sequence.EmptySequence;
import bzh.plealog.bioinfo.util.DAlphabetUtils;

public class EmptySequenceSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EmptySequence seq = new EmptySequence(DAlphabetUtils.getIUPAC_DNA_Alphabet(), 1350);
		DRulerModel   rModel;
		
		//check default ruler model
		rModel = seq.getRulerModel();
		System.out.println("From: "+rModel.getSeqPos(0)+", to: "+rModel.getSeqPos(seq.size()-1));
		
		//rModel = seq.createRulerModel(1350, -1);
		//System.out.println("From: "+rModel.getSeqPos(0)+", to: "+rModel.getSeqPos(seq.size()-1));

		//check symbol: should be ?
		System.out.println("Symbol :"+seq.getSymbol(12).getChar());
		
		//check subSequence
		DSequence seq2 = seq.getSubSequence(12, 1265, false);
		rModel = seq2.getRulerModel();
		System.out.println("From: "+rModel.getSeqPos(0)+", to: "+rModel.getSeqPos(seq2.size()-1));
	}

}
