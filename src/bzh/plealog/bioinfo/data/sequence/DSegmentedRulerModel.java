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

import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;

/**
 * This is an implementation of a DRulerModel for a segmented sequence.
 * 
 * @author Patrick G. Durand
 */
public class DSegmentedRulerModel implements DRulerModel {
    private DSegmentedSequence _segSeq;
    
    /**
     * No default constructor available.
     */
    private DSegmentedRulerModel(){}
    
    /**
     * Constructor with a DSegmentedSequence.
     */
    public DSegmentedRulerModel(DSegmentedSequence segSeq){
    	this();
    	_segSeq = segSeq;
    }
	
	/**
	 * Implementation of DRulerModel interface.
	 */ 
    public int getSeqPos(int idx){
    	DSequenceSegment sBag;
    	DRulerModel      rModel;
    	int              pos;
    	
    	sBag = _segSeq.getSeqWrapper(idx);
    	if (sBag==null)
    		return -1;
    	rModel = sBag.getSequence().getRulerModel();
    	if (rModel==null)
    		return -1;
    	//the method may return -1 if out of phase for a translated sequence
    	pos = _segSeq.getCorrectedPosition(sBag, idx);
    	
    	return (pos>=0 ? rModel.getSeqPos(pos) : -1);
    }

	/**
	 * Implementation of DRulerModel interface.
	 */ 
    public int size(){
    	return _segSeq.size();
    }
    
    private DRulerModel getRulerModelOfFirstSegment(){
    	Object           firtSegmentKey;
    	DSequenceSegment sBag;
    	
    	firtSegmentKey = _segSeq.getSegments().firstKey();
    	sBag = (DSequenceSegment) _segSeq.getSegments().get(firtSegmentKey);
    	return sBag.getSequence().getRulerModel();
    }
	/**
	 * Implementation of DRulerModel interface.
	 */ 
    public int getStartPos(){
    	DRulerModel      rModel;
    	
    	rModel = getRulerModelOfFirstSegment();
    	if (rModel==null)
    		return -1;
    	return rModel.getStartPos();
    }
    
	/**
	 * Implementation of DRulerModel interface.
	 */ 
    public int getIncrement(){
    	DRulerModel      rModel;
    	
    	rModel = getRulerModelOfFirstSegment();
    	if (rModel==null)
    		return -1;
    	return rModel.getIncrement();
    }
    public int getRulerPos(int seqPos){
    	return -1;//to do: implement!
    }
}
