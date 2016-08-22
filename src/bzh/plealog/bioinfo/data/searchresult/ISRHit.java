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

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;

/**
 * This is a default implementation of interface BHit
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.SRHit
 */
public class ISRHit implements SRHit{

	private static final long serialVersionUID = -8598064813592414758L;
	/**
	 * @serial
	 */
	private String    accession;
	/**
	 * @serial
	 */
    private String    definition;
	/**
	 * @serial
	 */
    private String    identitifer;
	/**
	 * @serial
	 */
    private int       length;
	/**
	 * @serial
	 */
    private int       number;
	/**
	 * @serial
	 */
    private ArrayList<SRHsp> hspList;
	/**
	 * @serial
	 */
    private BankSequenceInfo seqInfo;
	/**
	 * @serial
	 */
    private double       queryGlobalCoverage;
	/**
	 * @serial
	 */
    private double       hitGlobalCoverage;

    public ISRHit(){
    	hspList = new ArrayList<SRHsp>();
    }
    
    public SRHit clone(boolean shallow){
        ISRHit hit = new ISRHit();
        hit.copy(this, shallow);
        return hit;
    }
    
    public void copy(ISRHit src, boolean shallow){
        Enumeration<SRHsp>  myEnum;
        SRHsp               hsp;
        
        this.setHitAccession(src.getHitAccession());
        this.setHitDef(src.getHitDef());
        this.setHitId(src.getHitId());
        this.setHitLen(src.getHitLen());
        this.setHitNum(src.getHitNum());
        this.setQueryGlobalCoverage(src.getQueryGlobalCoverage());
        this.setHitGlobalCoverage(src.getHitGlobalCoverage());
        
        if (!shallow){
            myEnum = src.enumerateHsp();
            while(myEnum.hasMoreElements()){
                hsp = (SRHsp) myEnum.nextElement();
                this.addHsp(hsp.clone(false));
            }
            //starting with KB 2.6: does not clone anymore to save memory
            /*si = src.getSequenceInfo();
            if (si!=null)
            	this.setSequenceInfo((SequenceInfo) si.clone());*/
            this.setSequenceInfo(src.getSequenceInfo());
        }
    }
    
    public String getHitAccession(){return accession;}
    public void setHitAccession(String val){accession=val;}

    public String getHitDef(){return definition;}
    public void setHitDef(String val){definition=val;}

    public String getHitId(){return identitifer;}
    public void setHitId(String val){identitifer=val;}

    public int getHitLen(){return length;}
    public void setHitLen(int val){length=val;}

    public int getHitNum(){return number;}
    public void setHitNum(int val){number=val;}

    public void setHspList(ArrayList<SRHsp> lst){hspList = lst;}
    public ArrayList<SRHsp> getHspList(){return hspList;}

    public void addHsp(SRHsp hsp){
        hspList.add(hsp);
    }
    
    public Enumeration<SRHsp> enumerateHsp(){
        return new Enumeration<SRHsp>() {
            Iterator<SRHsp>  iter;
            boolean         bFirst = true;
            
            private void initialize(){
                iter = hspList.iterator();
                bFirst = false;
            }
            
            public boolean hasMoreElements() {
                if (bFirst)
                    initialize();
                return (iter.hasNext());
            }
            
            public SRHsp nextElement() {
                if (bFirst)
                    initialize();
                return (iter.next());
            }
        };
    }

    public SRHsp getHsp(int index){
        return ((SRHsp) hspList.get(index));   
    }
    
    public List<SRHsp> getHsps(){
    	return hspList;
    }
    
    public int countHsp(){
        return (hspList.size());   
    }
    public void setSequenceInfo(BankSequenceInfo si){
    	seqInfo = si;
    }
    public BankSequenceInfo getSequenceInfo(){
    	return seqInfo;
    }
    public double getQueryGlobalCoverage(){
   		return queryGlobalCoverage;
    }
    public double getHitGlobalCoverage(){
   		return hitGlobalCoverage;
    }
    public void setQueryGlobalCoverage(double coverage){
    	queryGlobalCoverage = coverage;
    }
    public void setHitGlobalCoverage(double coverage){
    	hitGlobalCoverage = coverage;
    }

    /**
     * Implementation to avoid returning the name of the class.
     * */
    public String toString(){
    	return "-";
    }
}
