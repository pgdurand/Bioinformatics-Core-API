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

import java.io.Reader;
import java.util.List;

/**
 * This interface defines a sequence factory.
 * 
 * @author Patrick G. Durand
 */
public interface DSequenceFactory {
    /*Reader: this is a reader of a stream of 'codes' (see DAlphabet)*/
    /**
     * Given a reader to a sequence and a particular alphabet, returns an 
     * instance of a sequence.
     */
	public DSequence getSequence(Reader reader, DAlphabet alphabet);
	
	/**
	 * Create a sequence with no letters. Introduced to handle contig, assembly and so on.
	 */
	public DSequence getSequence(int size, DAlphabet alphabet);

	public DSequence getSequence(StringBuffer buf, DAlphabet alphabet);
	
	/**
	 * Creates a sequence given a list of DSequence and a 
	 * corresponding list of Dlocation. Actually this method is
	 * intended to create a segmented representation of a sequence.
	 * The method should check that all sequence parts use the same 
	 * alphabet.
	 * 
	 * @param seqPart a list of DSequence
	 * @param locPart a list of DLocation
	 * @param alphabet the alphabet to use. All DSequence reported in seqPart
	 * must also use that alphabet.
	 * @param size the total size of the sequence
	 */
	public DSequence getSequence(List<DSequence> seqPart, List<DLocation> locPart, 
			DAlphabet alphabet, int size);
}
