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
package bzh.plealog.bioinfo.api.data.searchresult.utils;

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;

/**
 * This interface defines a container for a Blast Hit. This is a utility
 * class used in the GUI of KLblaster to display a particular HSP contained
 * in a particuler Hit.
 * 
 * @author Patrick G. Durand
 */
public class SRHitHspImplem implements SRHitHSP {
    private SRHit   _hit;
    private String _blastClient;
    private int    _hspNum;
    private int    _querySize;
    private int    _blastType;
    
    /**
     * Creates a BlastHitHsp.
     * 
     * @param hit the hit containing the HSP to display
     * @param bc the BlastClient that produced this BlastHitHsp
     * @param hspNum the HSP to display, identified by its order number within the Hit
     * @param qSize the query size
     * @param bType the Blast type, one of BOutput.BLASTXXX integer constants
     */
    public SRHitHspImplem(SRHit hit, String bc, int hspNum, int qSize, int bType){
        _hit=hit;
        _blastClient=bc;
        _hspNum=hspNum;
        _querySize = qSize;
        _blastType = bType;
    }
    
	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getHit()
	 */
	@Override
	public SRHit getHit() {
		return _hit;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getHspNum()
	 */
	@Override
	public int getHspNum() {
		return _hspNum;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#setHit(com.plealog.bioinfo.api.data.blast.BHit)
	 */
	@Override
	public void setHit(SRHit hit) {
		_hit = (SRHit) hit;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#setHspNum(int)
	 */
	@Override
	public void setHspNum(int i) {
		_hspNum = i;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getQuerySize()
	 */
	@Override
	public int getQuerySize() {
		return _querySize;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#setQuerySize(int)
	 */
	@Override
	public void setQuerySize(int i) {
		_querySize = i;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getBlastType()
	 */
	@Override
	public int getBlastType() {
		return _blastType;
	}

	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#setBlastType(int)
	 */
	@Override
	public void setBlastType(int i) {
		_blastType = i;
	}
	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#setBlastClient(java.lang.String)
	 */
	@Override
	public void setBlastClient(String bc){
		_blastClient = bc;
	}
	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getBlastCLient()
	 */
	@Override
	public String getBlastCLient(){
		return _blastClient;
	}
	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getQuerySeqType()
	 */
    @Override
	public int getQuerySeqType(){
    	if (_blastType == SROutput.BLASTP ||_blastType == SROutput.SCANPS || _blastType == SROutput.PSIBLAST|| _blastType == SROutput.TBLASTN)
    		return SROutput.AA_SEQ;
    	else
    		return SROutput.NUC_SEQ;
    }
	/* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#getHitSeqType()
	 */
    @Override
	public int getHitSeqType(){
    	if (_blastType == SROutput.BLASTP  ||_blastType == SROutput.SCANPS || _blastType == SROutput.PSIBLAST|| _blastType == SROutput.BLASTX)
    		return SROutput.AA_SEQ;
    	else
    		return SROutput.NUC_SEQ;
    }
    /* (non-Javadoc)
	 * @see com.plealog.bioinfo.api.data.blast.BlastHitHSPInterface#equals(java.lang.Object)
	 */
    @Override
	public boolean equals(Object obj){
    	SRHitHSP src;
    	if (obj instanceof SRHitHspImplem == false)
    		return false;
    	src = (SRHitHSP) obj;
    	return (src.getHit()==this._hit && src.getHspNum() == this._hspNum);
    }
}
