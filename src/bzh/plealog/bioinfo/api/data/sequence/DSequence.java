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

import java.util.List;

/**
 * This interface implements a sequence
 * 
 * @author Patrick G. Durand
 */
public interface DSequence {
    /**
     * Returns the symbol located at a particular position within a sequence.
     */
    public DSymbol getSymbol(int idx);
    /**
     * Returns the size of the sequence.
     */
    public int size();
    /**
     * Creates a ruler model for this sequence.
     * @param startPos starting position within the sequence (zero-based value)
     * @param increment increment value (+1 or +3 for translated nucleic)
     */
    public DRulerModel createRulerModel(int startPos, int increment);
    
    /**
     * Creates a ruler model for this sequence.
     * @param coord an array of sequence coordinates. Size of this array must be
     * equals to the size of this sequence, otherwise a DSequenceException is
     * thrown.
     */
    public DRulerModel createRulerModel(int[] coord);
    /**
     * Returns the ruler model associated to this sequence.
     */
    public DRulerModel getRulerModel();
    /**
     * Forces the implementation of toString.
     */
    public String toString();
    
    /**
     * Returns the alphabet used by this sequence.
     */
    public DAlphabet getAlphabet();
    
    /**
     * Returns the regions of the sequence that are not gaps.
     * 
     * @return a List of DLocation objects. In these objects, positions are 
     * absolute meaning that they are in the range [0, this.size()-1]. 
     */
    public List<DLocation> getSequenceParts();
    
    /**
     * Returns the number of gap characters contained in this sequence.
     */
    public int getGapContent();
    
    /** 
     * Returns the sub-sequence that begins at the specified index idxFrom and 
     * extends to the index idxTo-1. If the internal DRulerModel of source 
     * DSequence is initialized, this method should initialize the DRulerModel 
     * of the resulting sub-sequence. Method may return null if indices are 
     * invalid. 
     */
    public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse);
    
    /**
     * Returns a DSequenceInfo object attached to this sequence. This method
     * may return null.
     */
    public DSequenceInfo getSequenceInfo();
    
    /**
     * Attaches a DSequenceInfo object to this sequence. 
     */
    public void setSequenceInfo(DSequenceInfo dsi);
}
