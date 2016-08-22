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

import java.util.Arrays;

import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;

/**
 * This is a default implementation of DRulerModel.
 * 
 * @author Patrick G. Durand
 */
public class DRulerModelImplem implements DRulerModel {
	private int[] _rulerValues;
    private int   _increment;
    private int   _startPos;
    
    /**
     * Constructor. Given a DSequence, this implementation creates a continues
     * ruler starting at statrPos and incremented by the value of increment. If
     * DSequence contains gaps, then value -1 are attributed to them.
     */
    public DRulerModelImplem(DSequence seq, int startPos, int increment){
		int  i, size, decal;
        char ch1, ch2, ch;
        size = seq.size();
        _rulerValues = new int[size];
        ch1 = seq.getAlphabet().getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
        ch2 = seq.getAlphabet().getSymbol(DSymbol.SPACE_SYMBOL_CODE).getChar();
        decal=0;
        _increment=increment;
        _startPos=startPos;
        if (startPos<0){
        	for(i=0;i<size;i++)
        		_rulerValues[i] = -1;
        }
        else{
            for(i=0;i<size;i++){
                ch = seq.getSymbol(i).getChar();
            	if (ch!=ch1 && ch!=ch2){
                    _rulerValues[i] = startPos+decal;
                    decal+=increment;
                }
                else{
                    _rulerValues[i] = -1;
                }
            }
        }
    }
    /**
     * Constructor. 
     * 
     *  @param coord an array of sequence coordinates.
     */
    public DRulerModelImplem(int[] coord){
    	_increment = 1;
    	_rulerValues = new int[coord.length];
    	_startPos = coord[0];
    	for(int i=0;i<coord.length;i++){
    		_rulerValues[i] = coord[i];
    	}
    }
    
    public DRulerModelImplem(int start, int stop, int increment){
    	int i, j, size;
    	
    	size = stop-start+1;
    	_increment = increment;
    	_startPos = start;
    	_rulerValues = new int[size];
    	j=0;
    	for(i=start;i<=stop;i+=increment){
    		_rulerValues[j] = i;
    		j++;
    	}
    }
    public int getIncrement(){
    	return _increment;
    }
    public int getStartPos(){
    	return _startPos;
    }
    public int getSeqPos(int idx){
    	if (idx>=0 && idx <_rulerValues.length)
    		return _rulerValues[idx];
    	else
        	return -1;
    }
    public int size(){
        return (_rulerValues.length);
    }
    public int getRulerPos(int seqPos){
    	int val=Arrays.binarySearch(_rulerValues, seqPos);
    	if (val<0)
    		val = -1;
    	return val;
    }
    public String toString(){
    	StringBuffer szBuf;
    	int          i;
    	
    	szBuf = new StringBuffer("[");
    	for(i=0;i<_rulerValues.length;i++){
    		szBuf.append(_rulerValues[i]);
    		if((i+1)<_rulerValues.length){
    			szBuf.append(",");
    		}
    	}
    	szBuf.append("]");
    	return szBuf.toString();
    }
}
