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

import java.util.ArrayList;
import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceException;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;

/**
 * This is a default implementation of DSequence.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceImplem implements DSequence {
    private String        _sequence;
    private DAlphabet     _alphabet;
    private DSymbol       _letter = new DSymbolImplem(0,' ');
    private DRulerModel   _ruler;
    private DSequenceInfo _dsi;
    private List<DLocation> _parts;
    private int           _gapContent = -1;
    
    private DSequenceImplem(){}
    
    public DSequenceImplem(String seq, DAlphabet alphabet){
        this();
    	if (seq==null)
            throw new RuntimeException("Sequence is not defined.");
        if (alphabet==null)
            throw new RuntimeException("Alphabet is not defined.");
        _sequence = seq;
        _alphabet = alphabet;
    }

    public DSymbol getSymbol(int idx){
    	if (idx>=0 && idx<_sequence.length()){
    		return (_alphabet.getSymbol(_sequence.charAt(idx)));
        }
        else{
            return _letter;
        }
    }
    
    /*private String inverse(String str){
    	StringBuffer szBuf = new StringBuffer();
    	int          i, size = str.length()-1;
    	
    	for(i=size;i>=0;i--){
    		szBuf.append(str.charAt(i));
    	}
    	return szBuf.toString();
    }*/
    public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse){
    	String          subSeq;
    	DSequenceImplem seq;
    	int             startPos=-1, i, size, increment;
    	
		size = _sequence.length();
    	if (idxFrom<0 || idxTo<0 || idxFrom==size || idxTo>size || idxFrom>=idxTo)
    		return null;
    	subSeq = _sequence.substring(idxFrom, idxTo);
    	if (inverse){
    		if (_alphabet.getType()==DAlphabet.DNA_ALPHABET){
        		subSeq = DViewerSystem.inverseComplement(subSeq);
    		}
    		else{
        		subSeq = DViewerSystem.inverse(subSeq);
    		}
    	}
    	seq = new DSequenceImplem(subSeq, _alphabet);
        if (this.getSequenceInfo()!=null){
            seq.setSequenceInfo((DSequenceInfo) this.getSequenceInfo().clone());
        }
    	if (_ruler!=null){
    		//if gap, ruler returns -1, so need to get first valid position
    		if (inverse){
        		for(i=idxTo-1;i>=idxFrom;i--){
        			startPos = _ruler.getSeqPos(i);
        			if (startPos!=-1){
        				break;
        			}
        		}
        		increment = -((DRulerModelImplem)_ruler).getIncrement();
    		}
    		else{
        		for(i=idxFrom;i<idxTo;i++){
        			startPos = _ruler.getSeqPos(i);
        			if (startPos!=-1){
        				break;
        			}
        		}
        		increment = ((DRulerModelImplem)_ruler).getIncrement();
    		}
    		seq.createRulerModel(startPos, increment);
    	}
    	return seq;
    }
    
    public int size(){
        return _sequence.length();
    }
    
    public DRulerModel createRulerModel(int startPos, int increment){
        if (_ruler==null)
            _ruler = new DRulerModelImplem(this, startPos, increment);
        return _ruler;
    }
    
    public DRulerModel createRulerModel(int[] coord){
    	if (coord.length != this.size())
    		throw new DSequenceException("invalid coord array size.");
    	if (_ruler==null)
            _ruler = new DRulerModelImplem(coord);
        return _ruler;
    }

    public DRulerModel getRulerModel(){
        return _ruler;   
    }
    
    public String toString(){
        return _sequence;
    }
    
    public DAlphabet getAlphabet(){
    	return _alphabet;	
    }
    
    /**
     * Implementation of DSequence interface. Please note that this method
     * optimizes memory by computing once the returned List. So do NOT edit
     * the DLocation objects contained in the list.
     */
    public List<DLocation> getSequenceParts(){
    	ArrayList<DLocation> list;
    	DLocation pos;
    	char      gapCh, curCh;
    	int       i, size, from, to;
    	boolean   readSeq = false;
    	
    	if (_parts!=null)
    		return _parts;
    	list = new ArrayList<DLocation>();
    	gapCh = _alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
    	size = _sequence.length();
    	from = to = 0;
    	for(i=0;i<size;i++){
    		curCh = _sequence.charAt(i);
    		if (curCh == gapCh && readSeq == true){
    			to = i-1;
    			pos = new DLocation(from, to);
    			list.add(pos);
    			readSeq=false;
    		}
    		else if (curCh != gapCh){
    			if (!readSeq){
        			readSeq = true;
        			from = i;
    			}
    		}
    	}
    	if (readSeq){
			pos = new DLocation(from, size-1);
			list.add(pos);
    	}
    	_parts = list;
    	return list;
    }
    public int getGapContent(){
    	int       i, size, gaps = 0;
        char      ch;
        
        if (_gapContent>=0)
        	return _gapContent;
    	ch = _alphabet.getSymbol(DSymbol.GAP_SYMBOL_CODE).getChar();
        size = _sequence.length();
        for(i=0;i<size;i++){
            if (_sequence.charAt(i)==ch){
                gaps++;
            }
        }
        _gapContent = gaps;
        return gaps;
    }
    public DSequenceInfo getSequenceInfo(){
        return _dsi;
    }
    
    public void setSequenceInfo(DSequenceInfo dsi){
        _dsi = dsi;
    }
}
