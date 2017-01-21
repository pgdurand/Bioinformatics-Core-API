/* Copyright (C) 2003-2017 Patrick G. Durand
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

import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;

/**
 * This interface models a Multiple Sequence Alignment intended to be
 * exported using a particular format.
 * 
 * @author Patrick G. Durand
 */
public interface DSequenceAlignmentViewPort extends DSequenceAlignment{
    
    /**
     * Starting column of the MSA. Zero-based value.
     */
    public int fromColumn();
    /**
     * Ending column of the MSA. Zero-based value.
     */
    public int toColumn();
    /**
     * Starting row of the MSA. Zero-based value.
     */
    public int fromRow();
    /**
     * Ending row of the MSA. Zero-based value.
     */
    public int toRow();
    /**
     * Returns the header for a particular row.
     * 
     * @param row row index. Value in the range [0..rows()-1].
     * @return a name. Usually it is a sequence name.
     */
    public String rowHeader(int row);
    
}
