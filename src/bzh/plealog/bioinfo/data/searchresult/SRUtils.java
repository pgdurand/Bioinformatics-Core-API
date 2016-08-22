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

import java.util.HashSet;

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.searchresult.utils.SREntry;
import bzh.plealog.bioinfo.api.data.searchresult.utils.SRFactory;

public class SRUtils {
	/**
	 * Extract a particular set of hits from a particular iteration of a BlastEntry.
	 * 
	 * @param sQuery the source BlastEntry.
	 * @param iterNum the iteration to extract. Zero based value.
	 * @param hitsNum array of hits IDs. Be carefull: these are not zero-based values
	 * but the ordering number of hits as assigned by the search engine used to
	 * produce the source BlastEntry.
	 * */
	public static SREntry reduceEntry(SREntry sQuery, int iterNum, int[] hitsNum){
		SREntry            entry;
        SROutput               bo;
        SRFactory              bf;
        SRIteration            source, target;
        SRHit                  hit;
        int                   i, j, size;
        HashSet<Integer>      ids = new HashSet<Integer>();
        
        //create a new bo that will contain only the specified iterations
        //need improvement by the use of a class exposing the factory API implementation
        bf = new ISRFactory();
        bo = bf.createBOutput();
        bo.setBlastOutputParam(sQuery.getResult().getBlastOutputParam().clone());
        bo.setBlastType(sQuery.getResult().getBlastType());
        bo.setRequestInfo(sQuery.getResult().getRequestInfo().clone());
        
        for(i=0;i<hitsNum.length;i++){
        	ids.add(hitsNum[i]);
        }
        source = sQuery.getResult().getIteration(iterNum);
        target = source.clone(true);
        size = source.countHit();
    	for(j=0;j<size;j++){
    		hit = source.getHit(j);
    		if (ids.contains(hit.getHitNum())){
    			target.addHit(hit.clone(false));
    		}
    	}
    	bo.addIteration(target);
    	
        //this is the special case where we have results for multiple sequence in a same BOutput
        if (sQuery.getResult().getBlastType()!=SROutput.PSIBLAST && sQuery.getResult().getIterations().size()>1){
        	//update target with Iteration info
        	if (target.getIterationQueryDesc()!=null)
        		bo.getRequestInfo().setValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY, target.getIterationQueryDesc());
        	bo.getRequestInfo().setValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY, target.getIterationQueryLength());
        	if (target.getIterationQueryID()!=null)
        		bo.getRequestInfo().setValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY, target.getIterationQueryID());
        }

        entry = new SREntry(sQuery.getBlastClientName(), sQuery.getQueryName(), 
        		sQuery.getAbsolutePath(), bo, sQuery.getQuery(), sQuery.getDbName(), true);
        entry.setEntryOrderNum(sQuery.getEntryOrderNum());
        entry.setFilterName(sQuery.getFilterName());
        
        return entry;
	}
	/**
	 * Given the sequence coordinate incremental unit. For internal use only.
	 * 
	 * @return possibles values are 1, -1, 3 or -3/
	 */
	public static int getIncrement(int from, int to, int length){
        int inc;
        
        if ((Math.abs(to-from)+1)/length != 1)
            inc = 3;//translated alignment
        else
            inc = 1;//normal alignment
        if (from>to)
            inc = (-inc);
        
        return inc;
    }

	/**
	 * Use this method to remove some data from all Hsp contained in a SROutput. 
	 * 
	 * @param removeSeq if true, hit, query and midline sequences are set to null.
	 * @param removeFeatTable if true, feature tables are removed 
	 */
	public static void removeHspData(SROutput output, boolean removeSeq, boolean removeFeatTable){
    	SRIteration   bi;
        SRHit         hit;
        SRHsp         hsp;
    	int          i, j, k, size, size2, size3;
    	
    	size = output.countIteration();
		for(i=0;i<size;i++){
		    bi = output.getIteration(i);
		    size2 = bi.countHit();
		    for(j=0;j<size2;j++){
		        hit = bi.getHit(j);
		        size3 = hit.countHsp();
		        for(k=0;k<size3;k++){
		            hsp = hit.getHsp(k);
		            if (removeSeq){
		            	hsp.setHit(null);
		            	hsp.setQuery(null);
		            	hsp.setMidline(null);
		            }
		            if (removeFeatTable){
		            	hsp.setFeatures(null);
		            }
		        }
		    }
		}
	}

}
