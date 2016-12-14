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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRStatistics;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAligned;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignedFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignmentFactory;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceException;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceFactory;

/**
 * This is a default implementation of interface BIteration
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRIteration
 */
public class ISRIteration implements SRIteration{
	private static final long serialVersionUID = 8690595813859912522L;
	/**
	 * @serial
	 */
	private SRStatistics        stat;
	/**
	 * @serial
	 */
    private String             msg;
	/**
	 * @serial
	 */
    private String             qId;
	/**
	 * @serial
	 */
    private String             qDesc;
	/**
	 * @serial
	 */
    private int                qLength;
	/**
	 * @serial
	 */
    private int                num;
	/**
	 * @serial
	 */
    private ArrayList<SRHit>          hitList;

    private transient DSequenceAlignment msa_;
    
    private static final String MSA_BUILD_ERR_MSG_HEADER = "Unable to create MSA";

    public ISRIteration(){
    	hitList = new ArrayList<SRHit>();
    }
    
    public SRIteration clone(boolean shallow){
        ISRIteration ibi = new ISRIteration();
        ibi.copy(this, shallow);
        return ibi;
    }
    /**
     * All the object but the MSA is copied.
     */
    public void copy(ISRIteration src, boolean shallow){
        Enumeration<SRHit> myEnum;
        SRHit              hit;
        
        this.setIterationIterNum(src.getIterationIterNum());
        this.setIterationMessage(src.getIterationMessage());
        if (src.getIterationStat()!=null)
        	this.setIterationStat((SRStatistics) src.getIterationStat().clone());
        this.setIterationQueryDesc(src.getIterationQueryDesc());
        this.setIterationQueryID(src.getIterationQueryID());
        this.setIterationQueryLength(src.getIterationQueryLength());
        if (!shallow){
            myEnum = src.enumerateHit();
            while(myEnum.hasMoreElements()){
                hit = (SRHit) myEnum.nextElement();
                this.addHit(hit.clone(false));
            }
        }
    }
    
    public int getIterationIterNum() {return num;}
    public void setIterationIterNum(int val) {num=val;}

    public String getIterationMessage() {return msg;}
    public void setIterationMessage(String val) {msg=val;}

    public SRStatistics getIterationStat() {return stat;}
    public void setIterationStat(SRStatistics val) {stat=val;}

    public String getIterationQueryID(){return qId;}
    public void setIterationQueryID(String id){qId=id;}
    
    public String getIterationQueryDesc(){return qDesc;}
    public void setIterationQueryDesc(String desc){qDesc=desc;}
    
    public int getIterationQueryLength(){return qLength;}
    public void setIterationQueryLength(int len){qLength=len;}
    
    public void setHitList(ArrayList<SRHit> lst){hitList = lst;}
    public ArrayList<SRHit> getHitList(){return hitList;}
    
    public void addHit(SRHit hit){
        hitList.add(hit);
    }
    
    public Enumeration<SRHit> enumerateHit(){
        return new Enumeration<SRHit>() {
            Iterator<SRHit>  iter;
            boolean         bFirst = true;
            
            private void initialize(){
                iter = hitList.iterator();
                bFirst = false;
            }
            
            public boolean hasMoreElements() {
                if (bFirst)
                    initialize();
                return (iter.hasNext());
            }
            
            public SRHit nextElement() {
                if (bFirst)
                    initialize();
                return (iter.next());
            }
        };
    }

    public SRHit getHit(int index){
        return ((SRHit) hitList.get(index));   
    }
    
    public int countHit(){
        return (hitList.size());   
    }

    public List<SRHit> getHits(){
    	return hitList;
    }
    
    public DSequenceAlignment getMultipleSequenceAlignment(int querySize, int blastType)
    	throws DSequenceException{
    	if (msa_==null)
    		createMSA(querySize, blastType);
    	return msa_;
    }
    
    /**
     * Creates a Multiple Sequence Alignment from the set of HSPs contained in this
     * BIteration.
     * 
     * @param querySize the size of the query sequence
     * @param blastType the blast type (one of BOutput constants)
     */
	private void createMSA(int querySize, int blastType) throws DSequenceException{
		SRHit                      hit;
		SRHsp                      hsp;
		DSequenceAligned          seq;
		DSequenceFactory          sFactory;
		DSequenceAlignedFactory   saFactory;
		DSequenceAlignmentFactory msaFactory;
		ArrayList<DSequenceAligned> seqList;
		int                       i, hits, j, hsps, msaType;
		
    	//check factory system
		saFactory = CoreSystemConfigurator.getSequenceAlignedFactory();
    	if (saFactory==null)
    		throw new DSequenceException(MSA_BUILD_ERR_MSG_HEADER+": no aligned sequence factory.");
    	sFactory = CoreSystemConfigurator.getSequenceFactory();
    	if (sFactory==null)
    		throw new DSequenceException(MSA_BUILD_ERR_MSG_HEADER+": no sequence factory.");
    	msaFactory = CoreSystemConfigurator.getSequenceAlignmentFactory();
    	if (msaFactory==null)
    		throw new DSequenceException(MSA_BUILD_ERR_MSG_HEADER+": no MSA factory.");
		//creates MSA
		seqList = new ArrayList<DSequenceAligned>();
		hits = this.countHit();
		if (blastType == SROutput.TBLASTX || blastType == SROutput.BLASTX || blastType == SROutput.TBLASTN){
			msaType = DSequenceAlignment.TRANSLATED_ALIGNMENT;
		}
		else{
			msaType = DSequenceAlignment.STANDARD_ALIGNMENT;
		}
		for(i=0;i<hits;i++){
			hit = this.getHit(i);
			hsps = hit.countHsp();
			for(j=0;j<hsps;j++){
				hsp = hit.getHsp(j);
				seq = getAlignedSequence(
						saFactory,
						createSegmentedSequence(
								sFactory,
								hsp.getQuery().getSequence(hsp), 
								hsp.getHit().getSequence(hsp), 
								querySize,
								msaType));
				if (seq!=null)
					seqList.add(seq);
			}
		}
		msa_ = getMSA(msaFactory, seqList, querySize, msaType);
	}
    /**
     * Create a multiple sequence aligment from a list of aligned sequences.
     * 
     * @param msaFactory a MSA factory
     * @param seqs a list of aligned sequences
     * @param msaSize the size of the alignment
     * @param msaType the type of the MSA. Must be one of 
     * DSequenceAlignment.STANDARD_ALIGNMENT of DSequenceAlignment.TRANSLATED_ALIGNMENT.
     * */
    private DSequenceAlignment getMSA(DSequenceAlignmentFactory msaFactory, 
    		List<DSequenceAligned> seqs, int msaSize, int msaType)throws DSequenceException{
    	if(seqs==null || seqs.size()==0 || msaSize==0)
    		throw new DSequenceException(MSA_BUILD_ERR_MSG_HEADER+": no data.");
    	return (msaFactory.getDSequenceAlignment(seqs, msaSize, msaType));
    }
    
    /**
     * Create an aligned sequence object from a segmented sequence.
     * 
     * @param saFactory the aligned sequence factory.
     * @param segment the segmented sequence representing an aligned sequence.
     */
    private DSequenceAligned getAlignedSequence(DSequenceAlignedFactory saFactory, 
    		DSequence segment)throws DSequenceException{
    	if (segment==null){
    		throw new DSequenceException(MSA_BUILD_ERR_MSG_HEADER+": sequence segment is null.");
    	}
    	
    	return (saFactory.getDSequenceAligned(segment, 0));
    }
    /**
     * Create a segmented sequence given a Blast pairwise alignement. This method
     * analyses the alignement between the query and the hit sequences, and creates
     * a segmented sequence for the hit one. Theses segments corresponds to the parts
     * of the hit sequence that are aligned to the query sequence.
     * 
     * @param sFactory the sequence factory
     * @param query the query sequence
     * @param hit the hit sequence
     * @param seqSize the sequence size
     * @param msaType the type of the MSA. Must be one of 
     * DSequenceAlignment.STANDARD_ALIGNMENT of DSequenceAlignment.TRANSLATED_ALIGNMENT.
     */
    private DSequence createSegmentedSequence(DSequenceFactory sFactory, 
    		DSequence query, DSequence hit, int seqSize, int msaType)throws DSequenceException{
		DRulerModel             rModel;
		DSequence               seqTmp;
    	DLocation               loc, loc2;
		List<DLocation>         parts;
		ArrayList<DSequence>    seqPart;
		ArrayList<DLocation>    locPart;
		int                     i, nParts, from, to;
		boolean                 inverse=false;
		
		if (query==null || hit==null)
			return null;
		
    	parts = query.getSequenceParts();
    	
    	nParts = parts.size();
    	rModel = query.getRulerModel();
    	if (rModel==null){
    		throw new DSequenceException(MSA_BUILD_ERR_MSG_HEADER+": no ruler defined for query sequence.");
    	}
    	seqPart = new ArrayList<DSequence>();
    	locPart = new ArrayList<DLocation>();
    	for(i=0;i<nParts;i++){
    		//location of query seq part in absolute coord system
    		loc = (DLocation) parts.get(i);
    		//retrieves coord in query coordinates 
    		//these coordinates are then used to position hit parts
    		from = rModel.getSeqPos(loc.getFrom())-1;
    		to = rModel.getSeqPos(loc.getTo())-1;
    		if ((from>to) /*&& (msaType != DSequenceAlignment.TRANSLATED_ALIGNMENT)*/){
    			inverse=true;
    		}
    		seqTmp = hit.getSubSequence(loc.getFrom(), loc.getTo()+1, inverse);
    		//create a Dlocation with from always < to
    		loc2 = new DLocation(inverse ? to:from, inverse ? from:to);
    		//loc2 = new DLocation(Math.min(to,from), Math.max(from,to));
    		seqPart.add(seqTmp);
    		locPart.add(loc2);
    	}
    	return (sFactory.getSequence(seqPart, locPart, hit.getAlphabet(), seqSize));
	}    
}
