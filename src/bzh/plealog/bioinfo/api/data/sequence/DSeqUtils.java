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
package bzh.plealog.bioinfo.api.data.sequence;

import java.io.StringReader;
import java.text.MessageFormat;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.util.DAlphabetUtils;

public class DSeqUtils {
	public static DSequence getSequence(String seq, boolean isProteic){
        DSequence dSequence;
        dSequence = CoreSystemConfigurator.getSequenceFactory().getSequence(
                new StringReader(seq),
                (isProteic ? 
                DAlphabetUtils.getIUPAC_Protein_Alphabet() : 
                  DAlphabetUtils.getIUPAC_DNA_Alphabet()));
        dSequence.createRulerModel(1, 1);
        return dSequence;
    }
	private static final MessageFormat H_FORMATTER = new MessageFormat("{0} {1} [{2}-{3}]");
	
	public static String formatSequenceHeader(String id, String desc, int from, int to){
		//integers are switched to String to avoid Java formatting integers by introducing
		//space chars (by default Java formats the integer '1265' as '1 265', and we do not
		//want that).
		return H_FORMATTER.format(new Object[]{id, desc, String.valueOf(from), String.valueOf(to)});
	}
}
