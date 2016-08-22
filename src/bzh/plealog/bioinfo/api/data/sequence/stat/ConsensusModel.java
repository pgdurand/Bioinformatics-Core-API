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
package bzh.plealog.bioinfo.api.data.sequence.stat;


/**
 * This interface defines a data model representing a consensus sequence.
 * 
 * @author Patrick G. Durand
 */
public interface ConsensusModel {
	/**
	 * Compute a consensus model given a threshold. The threshold
	 * corresponds to the number of times a particular DSymbol is observed at
	 * a particular position of a DSequenceAlignment. So, each ConsensusCell of
	 * a ConsensusModel should contain the DSymbol(s) that are observed at least
	 * &apos;threshold&apos; times.
	 */
	public void computeConsensus(int threshold);
	
	/**
	 * Returns the ConsensusModel as an array of ConsensusCell objects.
	 */
	public ConsensusCell[] getConsensusCells();
}
