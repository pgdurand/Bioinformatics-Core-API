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

/**
 * This interface defines a ruler model. It is intended to convert ruler
 * coordinates into sequence coordinates.
 *  
 * @author Patrick G. Durand
 */
public interface DRulerModel {
   
	/**
	 * Given a position on the ruler, returns the corresponding position
	 * within a sequence.
	 */
    public int getSeqPos(int idx);
    /**
     * Returns the size of this ruler.
     */
    public int size();
	/**
	* Returns the starting position of this ruler. Sequence coordinate, one-based value.
	*/
    public int getStartPos();
    /**
	* Returns the ruler increment. 
	*/
    public int getIncrement();
	/**
	 * Given a position in the sequence coordinate system, returns the corresponding 
	 * position within the ruler.
	 */
    public int getRulerPos(int seqPos);
}
