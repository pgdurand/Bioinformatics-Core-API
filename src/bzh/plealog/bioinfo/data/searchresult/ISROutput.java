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
package bzh.plealog.bioinfo.data.searchresult;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.feature.utils.FeatureSystem;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRParameters;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;

/**
 * This is a default implementation of interface BOutput
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SROutput
 */
public class ISROutput implements SROutput{
	private static final long     serialVersionUID = 4156139841997726023L;
	private transient int         blastType_;
    @SuppressWarnings("unused")
	private int                   xmlVersion = 1;
    private SRRequestInfo          requestInfo;
    private SRParameters           parameters;
    private ArrayList<SRIteration> iterationList;
    
    public ISROutput(){
    	iterationList = new ArrayList<SRIteration>();
    }

    public SROutput clone(boolean shallow){
        ISROutput ibo = new ISROutput();
        ibo.copy(this, shallow);
        return ibo;
    }
    
    public void copy(ISROutput src, boolean shallow){
        Enumeration<SRIteration> myEnum;
        SRIteration              it;
        
        this.setBlastType(src.getBlastType());
        
        this.setRequestInfo(src.getRequestInfo());
        this.setBlastOutputParam(src.getBlastOutputParam());
        this.setEmpty(src.isEmpty());
        if (!shallow){
            myEnum = src.enumerateIteration();
            while(myEnum.hasMoreElements()){
                it = (SRIteration) myEnum.nextElement();
                this.addIteration(it.clone(false));
            }
        }
    }
    
    public int getBlastType(){
        if (blastType_>0)
            return blastType_;
        
        String prgm = (String) this.getRequestInfo().getValue(
        		SRRequestInfo.PROGRAM_DESCRIPTOR_KEY);
        blastType_ = SROutput.UNKNOWN_PRGM;
        if (prgm==null)
        	return blastType_;
        for(int i=0;i<SROutput.SEARCH_PRGM.length;i++){
            if (prgm.equalsIgnoreCase(SROutput.SEARCH_PRGM[i])){
                blastType_ = SROutput.SEARCH_PRGM_CODES[i];
                break;
            }
        }
        return blastType_;
    }
    public void setBlastType(int type){
    	blastType_ = type;
    }
    public String getBlastTypeStr(){
    	return SROutput.SEARCH_PRGM[getBlastType()-1];
    }
	/**
     * Returns the query sequence type. This is one of the AA_SEQ or NUC_SEQ.
     */
    public int getQuerySeqType(){
        //do not use blastType_ directly: getQuerySeqType() can be called
        //while blastType_ is not yet initialised!
    	int bType = getBlastType();
        if (bType == SROutput.BLASTP ||bType == SROutput.SCANPS || bType == SROutput.PSIBLAST || bType == SROutput.TBLASTN)
    		return SROutput.AA_SEQ;
    	else
    		return SROutput.NUC_SEQ;
    }
	/**
     * Returns the hit sequence type. This is one of the AA_SEQ or NUC_SEQ.
     */
    public int getHitSeqType(){
        //do not use blastType_ directly: getQuerySeqType() can be called
        //while blastType_ is not yet initialised!
        int bType = getBlastType();
    	if (bType == SROutput.BLASTP ||bType == SROutput.SCANPS|| bType == SROutput.PSIBLAST || bType == SROutput.BLASTX)
    		return SROutput.AA_SEQ;
    	else
    		return SROutput.NUC_SEQ;
    }
    
    public SRParameters getBlastOutputParam(){return parameters;}
    public void setBlastOutputParam(SRParameters val){parameters=val;}

    public SRRequestInfo getRequestInfo(){return requestInfo;}
    public void setRequestInfo(SRRequestInfo val){requestInfo=val;}
    
    public void setIterList(ArrayList<SRIteration> lst){iterationList = lst;}
    public ArrayList<SRIteration> getIterList(){return iterationList;}

    public boolean isEmpty(){
        int     i, nIter, totHits=0;
        
        nIter = this.countIteration();
        for(i=0;i<nIter;i++){
        	totHits += this.getIteration(i).countHit();
        }
        /*if (this.countIteration()!=0){
            if (this.getIteration(0).countHit()!=0){
                bRet = false;
            }
        }*/
        return totHits==0;
    }
    public void setEmpty(boolean val){}
    
    public void addIteration(SRIteration it){
        iterationList.add(it);
    }

    public Enumeration<SRIteration> enumerateIteration(){
        return new Enumeration<SRIteration>() {
            Iterator<SRIteration>  iter;
            boolean   bFirst = true;
            
            private void initialize(){
                iter = iterationList.iterator();
                bFirst = false;
            }
            
            public boolean hasMoreElements() {
                if (bFirst)
                    initialize();
                return (iter.hasNext());
            }
            
            public SRIteration nextElement() {
                if (bFirst)
                    initialize();
                return (iter.next());
            }
        };
    }
    
    public SRIteration getIteration(int index){
        return ((SRIteration) iterationList.get(index));   
    }
    
    public int countIteration(){
        return (iterationList.size());   
    }
    public List<SRIteration> getIterations(){
    	return iterationList;
    }
    
    //added for release 2.5+ to set query and hit sequence full size within HSP
    //so that coverage % can be used in the FIlter System
    public void initialize(){
    	BitSet       bitsHit, bitsQuery;
        FeatureTable ft;
    	SRIteration   bi;
        SRHit         hit;
        SRHsp         hsp;
        Integer      obj;
    	int          i, j, k, size, size2, size3, qSize, hSize, val, from, to, g, saqSize, sahSize;
    	boolean      qtranslated, htranslated, computeGCoverage;
    	
    	size = this.countIteration();
    	obj = (Integer) this.getRequestInfo().getValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY);
    	if (obj!=null)
    		qSize = obj;
    	else
    		qSize = 0;
    	
    	qtranslated = (getBlastType() == SROutput.BLASTX || getBlastType() == SROutput.TBLASTX);
    	htranslated = (getBlastType() == SROutput.TBLASTN || getBlastType() == SROutput.TBLASTX);
    	
		for(i=0;i<size;i++){
		    bi = this.getIteration(i);
		    val = bi.getIterationQueryLength();
		    if (val!=0)
		    	qSize = val;
		    size2 = bi.countHit();
		    for(j=0;j<size2;j++){
		        hit = bi.getHit(j);
		        hSize = hit.getHitLen();
		        size3 = hit.countHsp();
		        //hit coverage are equal to zero when they have not yet been computed
		        //so, when they are computed (these values are serialized), do not recompute them
		        computeGCoverage = (hit.getHitGlobalCoverage()==0d || hit.getQueryGlobalCoverage()==0d);
		        bitsHit=bitsQuery=null;
		        if(computeGCoverage){
			        if (size3!=1){
				        bitsHit = new BitSet(hSize);
				    	bitsQuery = new BitSet(qSize);
			        }
		        }
		    	for(k=0;k<size3;k++){
		            hsp = hit.getHsp(k);
		            hsp.getQuery().setSeqFullSize(qSize);
		            hsp.getHit().setSeqFullSize(hSize);
		            saqSize = Math.abs(hsp.getQuery().getFrom()-hsp.getQuery().getTo())+1;
		            sahSize = Math.abs(hsp.getHit().getFrom()-hsp.getHit().getTo())+1;
		            //coverage: compute without using sequence
		            if (hSize!=0)
		            	hsp.setHitCoverage((double)sahSize/(double)hSize * 100.d);
		            if (qSize!=0)
		            	hsp.setQueryCoverage((double)saqSize/(double)qSize * 100.d);
		            if (bitsHit!=null){
		            	bitsHit.set(Math.min(hsp.getHit().getFrom(),hsp.getHit().getTo()), 
		            			    Math.max(hsp.getHit().getFrom(),hsp.getHit().getTo()), 
		            				true);
		            }
		            if (bitsQuery!=null){
		            	bitsQuery.set(Math.min(hsp.getQuery().getFrom(),hsp.getQuery().getTo()), 
		            			    Math.max(hsp.getQuery().getFrom(),hsp.getQuery().getTo()), 
		            				true);
		            }
		            //compute gaps content
		            if (htranslated){
		            	sahSize = sahSize/3;
		            }
		            g = hsp.getScores().getAlignLen()-sahSize;
		            hsp.getHit().setGaps(g);
		            if (qtranslated){
		            	saqSize = saqSize/3;
		            }
		            g = hsp.getScores().getAlignLen()-saqSize;
		            hsp.getQuery().setGaps(g);
		            //handle features
		            ft = hsp.getFeatures();
		            from = hsp.getHit().getFrom();
		            to = hsp.getHit().getTo();
		            if (ft!=null && !ft.isValid(Math.min(from, to), Math.max(from, to))){
		            	FeatureSystem.LOGGER.warn("Iter["+(i+1)+"].Hit["+(j+1)+"].Hsp["+(k+1)+": FeatureTable discarded.");
		            	hsp.setFeatures(null);
		            }
		        }
		    	if (computeGCoverage){
			    	if (bitsHit==null){
			    		hit.setHitGlobalCoverage(hit.getHsp(0).getHitCoverage());
			    	}
			    	else if(hSize!=0){
			    		hit.setHitGlobalCoverage((double)bitsHit.cardinality()/(double)hSize * 100.d);
			    	}
			    	if (bitsQuery==null){
			    		hit.setQueryGlobalCoverage(hit.getHsp(0).getQueryCoverage());
			    	}
			    	else if(qSize!=0){
			    		hit.setQueryGlobalCoverage((double)bitsQuery.cardinality()/(double)qSize * 100.d);
			    	}
		    	}
		    }
		}
    }
    public boolean containsValidData(){
        FeatureTable ft;
    	SRIteration   bi;
        SRHit         hit;
        SRHsp         hsp;
    	int          i, j, k, size, size2, size3, from, to;
    	boolean      bRet = true;
    	
    	size = this.countIteration();
		for(i=0;i<size;i++){
		    bi = this.getIteration(i);
		    size2 = bi.countHit();
		    for(j=0;j<size2;j++){
		        hit = bi.getHit(j);
		        size3 = hit.countHsp();
		        for(k=0;k<size3;k++){
		            hsp = hit.getHsp(k);
		            ft = hsp.getFeatures();
		            from = hsp.getHit().getFrom();
		            to = hsp.getHit().getTo();
		            if (ft!=null && !ft.isValid(Math.min(from, to), Math.max(from, to))){
		            	FeatureSystem.LOGGER.warn("Iter["+(i+1)+"].Hit["+(j+1)+"].Hsp["+(k+1)+": FeatureTable discarded.");
		            	bRet = false;
		            }
		        }
		    }
		}
		return bRet;
    }
    public FEATURES_CONTAINER checkFeatures(){
    	FeatureTable ft;
    	SRIteration   bi;
        SRHit         hit;
        SRHsp         hsp;
    	int          i, j, k, size, size2, size3, hspCounter=0, featCounter=0, errFeats=0;
    	
    	size = this.countIteration();
		for(i=0;i<size;i++){
		    bi = this.getIteration(i);
		    size2 = bi.countHit();
		    for(j=0;j<size2;j++){
		        hit = bi.getHit(j);
		        size3 = hit.countHsp();
		        for(k=0;k<size3;k++){
		            hsp = hit.getHsp(k);
		            hspCounter++;
		            ft = hsp.getFeatures();
		            if (ft!=null){
		            	if (ft.getStatus()==FeatureTable.ERROR_STATUS){
		            		errFeats++;
			            }
		            	else{
		            		featCounter++;
		            	}
		            }
		        }
		    }
		}
		if (hspCounter==0)//no HSPs -> no features !
			return FEATURES_CONTAINER.none;
		
		if (hspCounter==featCounter)
			return FEATURES_CONTAINER.allHits;
		else if (featCounter!=0 || errFeats!=0)
			return FEATURES_CONTAINER.someWithErrors;
		else
			return FEATURES_CONTAINER.none;
    }
}


