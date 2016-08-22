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
package bzh.plealog.bioinfo.data.sequence;

import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DSequenceAligned;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignmentFactory;

/**
 * This is a default implementation of DSequenceAlignmentFactory.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceAlignmentFactoryImplem implements
		DSequenceAlignmentFactory {

	public DSequenceAlignment getDSequenceAlignment(List<DSequenceAligned> sequences,
			int alignSize, int type) {
		DSequenceAlignmentImplem msa;
		Object                   seq;
		int                      i, size;
		
		msa = new DSequenceAlignmentImplem(type);
		if (sequences==null || alignSize<=0)
			return msa;
		size = sequences.size();
		for(i=0;i<size;i++){
			seq = sequences.get(i);
			if (seq instanceof DSequenceAligned){
				msa.addSequence((DSequenceAligned)seq);
			}
		}
		msa.setColumns(alignSize);
		return msa;
	}

}
