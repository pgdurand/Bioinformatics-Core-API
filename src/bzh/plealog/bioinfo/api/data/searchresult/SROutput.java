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
package bzh.plealog.bioinfo.api.data.searchresult;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

/**
 * This class is the object representation of a Blast output file.
 * 
 * @author Patrick G. Durand
 */
public interface SROutput extends Serializable{
    /**an array of search program names. Accessing this array can be done using integer
     * program identifier on which one has to remove one.*/
	public static final String[] SEARCH_PRGM = 
        {"blastp","blastn","blastx","tblastn","tblastx","SCANPS","psi-blast"};
    /**Search program type is blastp*/
    public static final int BLASTP = 1;
    /**Search program type is blastn*/
    public static final int BLASTN = 2;
    /**Search program type is blastx*/
    public static final int BLASTX = 3;
    /**Search program type is tblastn*/
    public static final int TBLASTN = 4;
    /**Search program type is tblastx*/
    public static final int TBLASTX = 5;
    /**Search program type is scanps*/
    public static final int SCANPS = 6;
    /**Search program type is psi-blast*/
    public static final int PSIBLAST = 7;
    /**Search program type is unknown*/
    public static final int UNKNOWN_PRGM = 255;
    
    /**
     * An array containing the various search program codes.
     */
    public static final int[] SEARCH_PRGM_CODES = 
        {BLASTP,BLASTN,BLASTX,TBLASTN,TBLASTX,SCANPS,PSIBLAST};
    
    /**an array of sequence types*/
	public static final String[] SEQ_TYPES = 
        {"aa","nuc"};
    
	public static enum FEATURES_CONTAINER {none, allHits, someWithErrors};
	
	/**
     * Result contains protein data.
     */
    public static final int AA_SEQ = 1;
    /**
     * Result contains nucleotide data.
     */
    public static final int NUC_SEQ = 2;


	/**
     * Returns the Blast type. This is one of the BLASTP, BLASTN, BLASTX,
     * TBLASTN oe TBLASTX constant defined in this class.
     */
    public int getBlastType();
    /**
     * Returns the string representation of the Blast type.
     */
    public String getBlastTypeStr();
    public void setBlastType(int type);

	/**
     * Returns the query sequence type. This is one of the AA_SEQ or NUC_SEQ.
     */
    public int getQuerySeqType();
	/**
     * Returns the hit sequence type. This is one of the AA_SEQ or NUC_SEQ.
     */
    public int getHitSeqType();

    /**
     * Returns the BRequestInfo object associated to this result.
     */
    public SRRequestInfo getRequestInfo();
    public void setRequestInfo(SRRequestInfo info);

    /**
     * Returns the BParameters object associated to this result.
     */
    public SRParameters getBlastOutputParam();
    public void setBlastOutputParam(SRParameters param);

    public void addIteration(SRIteration iter);
    /**
     * Figures out if this result contains some hits.
     * 
     * @return false if this result does not contain any hits, true otherwise.
     */
    public boolean isEmpty();
    /**
     * Returns an enumeration over the SRIterations contained in this result.
     */
    public Enumeration<SRIteration> enumerateIteration();
    /**
     * Returns a particular BIteration.
     */
    public SRIteration getIteration(int index);
    /**
     * Returns a List of BIterations contained in this search result.
     */
    public List<SRIteration> getIterations();
    public int countIteration();
    /**
     * Forces the implementation of a clone method.
     */
    public SROutput clone(boolean shallow);
    
    //added for release 2.5+ to set query and hit sequence full size within HSP
    //so that coverage % can be used in the FIlter System
    public void initialize();
    //added for release 2.6+ to check data file produced by developers (KoriViewer Public API)
    public boolean containsValidData();
    
    /**
     * Check if this BOutput contains valid SequenceInfo and Features data.
     */
    public FEATURES_CONTAINER checkFeatures();
    
    /**
     * Return classification data associated to this SROutput.
     */
    public SRClassification getClassification();
    /**
     * Set classification data to this SROutput.
     */
    public void setClassification(SRClassification classif);
    
}
