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

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.plealog.genericapp.api.log.EZLogger;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceFactory;

/**
 * This is a default implementation of DSequenceFactory.
 * 
 * @author Patrick G. Durand
 */
public class DSequenceFactoryImplem implements DSequenceFactory {
    public DSequence getSequence(Reader reader, DAlphabet alphabet){
        DSequenceImplem dSeq;
        StringBuffer    szBuf;
        int             ch;
        
        try{
            szBuf = new StringBuffer();
            while((ch = reader.read())!=-1){
                ch = Character.toUpperCase(ch);
            	if (alphabet.getSymbol(ch)==null){
            		EZLogger.warn("Symbol unknown for: "+ch+". Continuing...");
                    continue;
                }
                szBuf.append((char) ch);
            }
            dSeq = new DSequenceImplem(szBuf.toString(), alphabet);
        }
        catch (IOException ioex){
        	EZLogger.warn("Unable to read sequence data: "+ ioex.toString());
            dSeq = null;
        }
        return dSeq;
    }
    
    public DSequence getSequence(int size, DAlphabet alphabet){
    	if (size==0)
    		return null;
    	return new EmptySequence(alphabet, size);
    }
    
	public DSequence getSequence(StringBuffer buf, DAlphabet alphabet){
        int ch, i , size;
        
    	size = buf.length();
    	for(i=0;i<size;i++){
    		ch = buf.charAt(i);
            ch = Character.toUpperCase(ch);
        	if (alphabet.getSymbol(ch)==null){
        		EZLogger.warn("Symbol unknown for: "+ch+". Continuing...");
                continue;
            }
        	buf.setCharAt(i, (char) ch);
        }
        return new DSequenceImplem(buf.toString(), alphabet);
	}
    public DSequence getSequence(List<DSequence> seqPart, List<DLocation> locPart, 
			DAlphabet alphabet, int size){
    	DSegmentedSequence dSeq;
    	DSequence          seq;
    	int                i, s;
    	
    	if (alphabet==null || seqPart==null || locPart==null || 
    		seqPart.isEmpty() || locPart.isEmpty() ||
    		seqPart.size()!=locPart.size() || size==0)
			return null;
    	s = seqPart.size();
    	for(i=0;i<s;i++){
    		seq = (DSequence) seqPart.get(i);
    		if (seq==null || locPart.get(i)==null)
    			return null;
    		if (seq.getAlphabet().getName().equals(alphabet.getName()) == false)
    			return null;
    	}

    	dSeq = new DSegmentedSequence(alphabet);
    	for(i=0;i<s;i++){
    		dSeq.addSegment((DSequence) seqPart.get(i), (DLocation) locPart.get(i));
    	}
    	dSeq.setSize(size);
    	return dSeq;
    }
}
