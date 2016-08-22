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
package bzh.plealog.bioinfo.io.searchresult.ncbi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.exolab.castor.util.LocalConfiguration;

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRParameters;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.searchresult.SRStatistics;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoaderException;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriterException;
import bzh.plealog.bioinfo.api.data.sequence.DViewerSystem;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_param;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_stat;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Parameters;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.Statistics;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.resources.Messages;
import bzh.plealog.bioinfo.data.searchresult.ISRHit;
import bzh.plealog.bioinfo.data.searchresult.ISRHsp;
import bzh.plealog.bioinfo.data.searchresult.ISRHspScore;
import bzh.plealog.bioinfo.data.searchresult.ISRHspSequence;
import bzh.plealog.bioinfo.data.searchresult.ISRIteration;
import bzh.plealog.bioinfo.data.searchresult.ISROutput;
import bzh.plealog.bioinfo.data.searchresult.ISRParameters;
import bzh.plealog.bioinfo.data.searchresult.ISRRequestInfo;
import bzh.plealog.bioinfo.data.searchresult.ISRStatistics;
import bzh.plealog.bioinfo.util.CoreUtil;
import bzh.plealog.bioinfo.util.ZipUtil;

/**
 * This is the NCBI implementation of interface BLoader. It means that this loader
 * aims at loading Blast result files coming from the original Blast implementation
 * from NCBI. This loader ONLY handles the XML format described by the NCBI_BlastOutput
 * DTD.
 * 
 * @author Patrick G. Durand
 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader
 */
public class BlastLoader implements SRLoader, SRWriter {

	public static final String SYSTEM_NAME ="XMLBlast";

	public static final String STAT_DB_LEN = "dbLen";
    public static final String STAT_DB_NUM = "dbNum";
    public static final String STAT_HSP_LEN = "hspLen";
    public static final String STAT_EFF_SPACE = "effSpace";
    public static final String STAT_ENTROPY = "entroy";
    public static final String STAT_KAPPA = "kappa";
    public static final String STAT_LAMBDA = "lambda";

    public static final String PARAM_MATCH = "scMatch";
    public static final String PARAM_MISMATCH = "scMismatch";
    public static final String PARAM_INCLUDE = "include";
    public static final String PARAM_FILTER = "filter";
    public static final String PARAM_PATTERN = "pattern";
    public static final String PARAM_QUERY= "query";

    /**
	 * Implementation of BLoader interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader#getSystemName()
	 */
    public String getSystemName(){
    	return SYSTEM_NAME;
    }
    /**
	 * Implementation of BLoader interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader#getName()
	 */
	public String getName() {
		return "NCBI XML formatted data file";
	}
	/**
	 * Implementation of BLoader interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader#getVersion()
	 */
	public String getVersion() {
		return "2.5";
	}
	
	/**
	 * Implementation of BLoader interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader#canRead(File)
	 */
	public boolean canRead(File f) {
		File            f2;
		String          line;
        BufferedReader  bReader;
        FileInputStream fis = null;
        boolean         bOk = false;
        boolean         isZip = false;
        int             i = 0;
        
        try{
        	fis = new FileInputStream(f);
        	f2 = ZipUtil.extract(fis, f.getParent());
        	isZip = true;
        }
        catch(Exception e){
        	f2 = f;
        }
        if (fis!=null){
        	try{fis.close();}catch(Exception ex){}
        }
        try { 
        	bReader = new BufferedReader(new FileReader(f2));
            while ((line=bReader.readLine()) != null) {
                if (line.indexOf("<BlastOutput>")>=0){
                	bOk=true;
                	break;
                }
                i++;
                if (i>5)
                	break;
            }
            bReader.close();
        } 
        catch (Exception e) {  
        } 
        if (isZip)
        	try{f2.delete();}catch(Exception ex){}
        	
        return bOk;
	}
	
	/**
	 * Writer an SROutput into a file. 
	 * 
	 * @param f the file to export data
	 * @param data the data to export
	 * @param append if true, data is added to file f.
	 */
	public void write(File f, SROutput data, boolean append) throws SRWriterException{
        BlastOutput       bo;
        FileOutputStream   fos = null;

		try{
			fos= new FileOutputStream(f, append);
			bo = createBlastOutput((SROutput) data);	
			
			//require to indent the xml output or it is not readable by koriblast
	    	LocalConfiguration.getInstance().getProperties().setProperty("org.exolab.castor.indent","true");
	
	    	bo.marshal(new OutputStreamWriter(fos));
			fos.flush();
		}
		catch (Exception ex){
			throw new SRWriterException(ex.toString());
		}
		finally{
			try{
			if (fos!=null)
				fos.close();
			}
			catch(Exception ex){}
		}
	}
    
	/**
	 * Implementation of SRWriter interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter#write(File, SROutput, Object)
	 */
	public void write(File f, SROutput data, Object options) throws SRWriterException{
		write(f, data, false);
	}
	
	/**
	 * Implementation of SRWriter interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter#write(File, SROutput)
	 */
	public void write(File f, SROutput data) throws SRWriterException{
		write(f, data, false);
	}
	/**
	 * Implementation of BLoader interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader#load(File)
	 */
    public SROutput load(File f) throws SRLoaderException{
        BlastOutput       bo;
        SROutput           boRet = null;
        PipedInputStream  writeIn;
        PipedOutputStream readOut;
        FileInputStream   fis = null;
        File              f2;
        boolean           isZip = false;
        
        try{
        	fis = new FileInputStream(f);
        	f2 = ZipUtil.extract(fis, f.getParent());
        	isZip = true;
        }
        catch(Exception e){
        	f2 = f;
        }
        if (fis!=null){
        	try{fis.close();}catch(Exception ex){}
        }
        try {
			writeIn = new PipedInputStream();
			readOut = new PipedOutputStream( writeIn );
			ReadThread rt = new ReadThread(new BufferedReader(new FileReader(f2)), readOut );
			rt.start();

			bo = (BlastOutput) BlastOutput.unmarshal(new InputStreamReader(writeIn));
			
			if (!rt.bError){
				boRet = createIBoutput(bo);
			}
		} catch (Exception ex) {
			throw new SRLoaderException(ex.toString());
		}
		finally{
			if (isZip){
				try{f2.delete();}catch(Exception ex){}
			}
		}
		return boRet;
	}

    
    private class ReadThread extends Thread implements Runnable {
        private Reader reader = null;
        private OutputStream os = null;    
        protected boolean bError = false;
        
        ReadThread(Reader reader, OutputStream os) {
            this.reader = reader;
            this.os = os;
        }
    
        public void run() {
            BufferedWriter writer = null;
            String         str;
            char           buf[];
            int            read, idx;
            boolean        bFirst = true;

            try {
            	buf = new char[2048];
                //read Tmp file and put data into the SAX parser
            	writer = new BufferedWriter(new OutputStreamWriter(os));
            	while((read=reader.read(buf, 0, 2048))!=-1){
            		if (bFirst){
                		str = new String(buf,0,read);
                		idx = str.indexOf("<BlastOutput");
                		if (idx!=-1){
                			writer.write("<?xml version=\"1.0\"?>\n");
                			str = str.substring(idx);
                			writer.write(str);
                		}
            			bFirst = false;
            		}
            		else{
            			writer.write(buf, 0, read);
            		}
            	}
            	
            } 
            catch (Exception e) {  
                bError = true;
                System.err.println(Messages.getString("BlastLoader.1")+e.toString());
            } 
            try{if (writer!=null){writer.flush();writer.close();}}catch(Exception e){}//not bad
            try{if (reader!=null){reader.close();}}catch(Exception e){}//not bad
        }
    }
    
 
    
	/**
	 * Implementation of BWriter interface.
	 */
    public void multipleWrite(File f, SROutput[] tb) throws SRLoaderException{
    	SROutput        bo;
        
        bo = multipleToOneBOuput(tb);
        write(f,bo);
        
    	return;
    }
    
    
	private SROutput multipleToOneBOuput(SROutput[] tb) {
    	SROutput        	bo;
    	int				i,size,num;
    	
    	size = tb.length;
    	
    	if (size<1){
    		bo=null;
    	}
    	else{
    		bo = tb[0];
    		num=bo.countIteration();
    		if (size>1){
    			for (i=1;i<size;i++){
    				List<SRIteration> liter = tb[i].getIterations();
    				Iterator<SRIteration> it = liter.iterator();
    				while (it.hasNext()){
        				num++;
    					SRIteration iter = it.next();
    					iter.setIterationIterNum(num);
    					bo.addIteration(iter);
    				}
    			}
    		}
    	}
		return bo;
	}
    
    
	/**
	 * Implementation of BLoader interface.
	 * 
	 * @see bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader#multipleLoad(File)
	 */
    public SROutput[] multipleLoad(File f) throws SRLoaderException{
    	ArrayList<SROutput>      boList;
    	SROutput[]      bos;
    	SROutput        bo;
    	PrintWriter    writer = null;
        BufferedReader bReader = null;
        String         str;
        File           output = null;
        FileInputStream   fis = null;
        File              f2;
        int            i, size;
        boolean        doWrite = false;
        boolean        isZip = false;
        
		boList = new ArrayList<SROutput>();
        
        try{
        	fis = new FileInputStream(f);
        	f2 = ZipUtil.extract(fis, f.getParent());
        	isZip = true;
        }
        catch(Exception e){
        	f2 = f;
        }
        if (fis!=null){
        	try{fis.close();}catch(Exception ex){}
        }
        try {
			bReader = new BufferedReader(new FileReader(f2));
			while ((str=bReader.readLine()) != null) {
				if (str.indexOf("<BlastOutput>")>=0){
					output = File.createTempFile("kl_", ".tmp");
					writer = new PrintWriter(
			                new OutputStreamWriter(
			                        new FileOutputStream(output)
			                )
			        );
					writer.println("<?xml version=\"1.0\"?>");
					doWrite=true;
				}
				else if(str.indexOf("</BlastOutput>")>=0){
					writer.println(str);
					doWrite = false;
					writer.flush();
					writer.close();
					bo = load(output);
					if (bo!=null)
						boList.add(bo);
					output.delete();
				}
				
				if (doWrite){
					writer.println(str);
				}
			}
		}
        catch (Exception ex){
        	throw new SRLoaderException(ex.toString());
		}
        finally{
        	try{
        		if (bReader!=null)
        			bReader.close();
        	}
        	catch(Exception e){        		
        	}
        	if (isZip){
            	try{f2.delete();}catch(Exception ex){}
        	}
        }
        if (boList.isEmpty())
        	return null;
        size = boList.size();
        bos = new SROutput[size];
        for(i=0;i<size;i++){
        	bos[i] = (SROutput) boList.get(i);
        }
        
    	return bos;
    }
    
    



    private Hsp initHsp(ISRHsp iHsp, boolean isProt) {
    	Hsp    hsp;
    	String seq;
    	
    	hsp = new Hsp();
    	
    	//score
        hsp.setHsp_score(iHsp.getScores().getScore());
        hsp.setHsp_bitScore(iHsp.getScores().getBitScore());
        hsp.setHsp_evalue(iHsp.getScores().getEvalue());
        hsp.setHsp_identity(iHsp.getScores().getIdentity());
        hsp.setHsp_positive(iHsp.getScores().getPositive());
        hsp.setHsp_alignLen(iHsp.getScores().getAlignLen());
        hsp.setHsp_density(iHsp.getScores().getDensity());
        hsp.setHsp_gaps(iHsp.getScores().getGaps());
        
        //query sequence
        hsp.setHsp_queryFrom(iHsp.getQuery().getFrom());
        hsp.setHsp_queryTo(iHsp.getQuery().getTo()); 
        hsp.setHsp_queryFrame(iHsp.getQuery().getFrame());
        seq = iHsp.getQuery().getSequence();
        hsp.setHsp_qseq(seq!=null?seq:"-");
        
        //hit sequence
        hsp.setHsp_hitFrame(iHsp.getHit().getFrame());
        hsp.setHsp_hitFrom(iHsp.getHit().getFrom());
        hsp.setHsp_hitTo(iHsp.getHit().getTo());
        seq = iHsp.getHit().getSequence();
        hsp.setHsp_hseq(seq!=null?seq:"-");
        //midline
        if (iHsp.getMidline()!=null)
        	hsp.setHsp_midline(iHsp.getMidline().getSequence());
        
    	hsp.setHsp_num(iHsp.getHspNum());
        
    	return hsp;
	}
    
    
    private ISRHsp initBHsp(Hsp hsp, boolean isProt, boolean isBlastn){
    	ISRHsp         iHsp;
        ISRHspScore    score;
        ISRHspSequence query, hit, midline;
        String        seq;
        int           from, to;
        boolean       doRCalignment = false;
        
        iHsp = new ISRHsp();
        score = new ISRHspScore();
		query = new ISRHspSequence();
		hit = new ISRHspSequence();
		midline = new ISRHspSequence();
		
		//scores
        score.setBitScore(hsp.getHsp_bitScore());
        score.setScore(hsp.getHsp_score());
        score.setEvalue(hsp.getHsp_evalue());
        score.setIdentity((int) hsp.getHsp_identity());
        score.setPositive((int) hsp.getHsp_positive());
        score.setGaps((int) hsp.getHsp_gaps());
        score.setAlignLen((int) hsp.getHsp_alignLen());
        score.setDensity((int) hsp.getHsp_density());
        
        //query sequence
        query.setType(SRHspSequence.TYPE_ALIGNED_SEQ);
        query.setFrame((int) hsp.getHsp_queryFrame());
        from = (int) hsp.getHsp_queryFrom();
        to = (int) hsp.getHsp_queryTo();
        
        if (isBlastn){
        	//this has been added to take into account a blastall's bug in XML dump. 
        	//Exemple:
//        	1 Format standard de Blast : 
//        	Strand = Plus / Minus
//        	Query: 94     ttggtcgtagtccacatcgtcca 116
//        	             ||| |||||||||||||||||||
//        	Sbjct: 339591 ttgttcgtagtccacatcgtcca 339569
//
//
//        	2. Format Xml : ce n'est pas bon. Les coordonn�es sont invers�es, la s�quence query est donn�e en "reverse complement", alors que le frame est donn� en "+1" (strand plus).
//        	             <Hsp_query-from>116</Hsp_query-from>
//        	             <Hsp_query-to>94</Hsp_query-to>
//        	             <Hsp_query-frame>1</Hsp_query-frame>
//        	             <Hsp_qseq>TGGACGATGTGGACTACGACCAA</Hsp_qseq>
//        	             <Hsp_hseq>TGGACGATGTGGACTACGAACAA</Hsp_hseq>

        	if (from>to && query.getFrame()>0){
        		doRCalignment = true;
        	}
        }
        if (query.getFrame()<0){
            query.setFrom(Math.max(from, to));
            query.setTo(Math.min(from, to));
        }
        else{
            query.setFrom(Math.min(from, to));
            query.setTo(Math.max(from, to));
        }
        seq = hsp.getHsp_qseq();
        //added to take into account a case when tblastx reports empty HSP!
        if(seq!=null && seq.equals("-")==false){
        	if (doRCalignment)
        		query.setSequence(DViewerSystem.inverseComplement(seq.toUpperCase()));
       		else
           		query.setSequence(seq.toUpperCase());
        }
        //Hit sequence
        hit.setType(SRHspSequence.TYPE_ALIGNED_SEQ);
        hit.setFrame((int) hsp.getHsp_hitFrame());
        from = (int) hsp.getHsp_hitFrom();
        to = (int) hsp.getHsp_hitTo();
        if (hit.getFrame()<0){
            hit.setFrom(Math.max(from, to));
            hit.setTo(Math.min(from, to));
        }
        else{
            hit.setFrom(Math.min(from, to));
            hit.setTo(Math.max(from, to));
        }
        seq = hsp.getHsp_hseq();
        //added to take into account a case when tblastx reports empty HSP!
        if(seq!=null && seq.equals("-")==false){
        	if (doRCalignment)
        		hit.setSequence(DViewerSystem.inverseComplement(seq.toUpperCase()));
       		else
           		hit.setSequence(seq.toUpperCase());
        }
        //middle sequence
        midline.setType(SRHspSequence.TYPE_MIDLINE);
        seq = hsp.getHsp_midline();
        //added to take into account a case when tblastx reports empty HSP!
        if (seq!=null){
        	if(doRCalignment)
        		midline.setSequence(DViewerSystem.inverse(seq.toUpperCase()));
        	else
        		midline.setSequence(seq.toUpperCase());
        }
        iHsp.setScores(score);
        iHsp.setQuery(query);
        iHsp.setHit(hit);
        iHsp.setMidline(midline);
        
        iHsp.setHspNum((int) hsp.getHsp_num());
        //iHsp.setHspPatternFrom(hsp.getHsp_patternFrom());
        //iHsp.setHspPatternTo(hsp.getHsp_patternTo());
        return iHsp;
    }
    
    
    private Hit_hsps initHitHsps(ISRHit iHit, boolean isProt) {
		Enumeration<SRHsp> enu;
    	Hit_hsps          hit_hsps;
		Hsp hsp;
		
		hit_hsps = new Hit_hsps();
		
		enu = iHit.enumerateHsp();
		while (enu.hasMoreElements()){
			hsp = initHsp((ISRHsp)enu.nextElement(), isProt);
			hit_hsps.addHsp(hsp);
		}		
		return hit_hsps;
	}
    
	private void initBHitHsps(ISRHit iHit, Hit_hsps hitHsps, boolean isProt, boolean isBlastn){
        @SuppressWarnings("rawtypes")
		Enumeration  enu;
        ISRHsp         hsp;
        enu = hitHsps.enumerateHsp();
        while(enu.hasMoreElements()){
        	hsp = initBHsp((Hsp) enu.nextElement(), isProt, isBlastn);
            //added to take into account a case when tblastx reports empty HSP!
        	if (hsp!=null)
        		iHit.addHsp(hsp);
        }
    }
	private Hit initHit(ISRHit iHit, boolean isProt) {
		Hit hit;
		Hit_hsps hit_hsps;
		
		hit = new Hit();
		hit.setHit_accession(iHit.getHitAccession());
		hit.setHit_def(CoreUtil.Cleanup(iHit.getHitDef(), false));
		hit.setHit_id(iHit.getHitId());
		hit.setHit_len(iHit.getHitLen());
		hit.setHit_num(iHit.getHitNum());
		hit_hsps = initHitHsps(iHit, isProt);
		hit.setHit_hsps(hit_hsps);	
		
		return hit;
	}
 
	private ISRHit initBHit(Hit hit, boolean isProt, boolean isBlastn){
        ISRHit iHit;
        
        iHit = new ISRHit();
        iHit.setHitAccession(hit.getHit_accession());
        iHit.setHitDef(hit.getHit_def());
        iHit.setHitId(hit.getHit_id());
        iHit.setHitLen((int) hit.getHit_len());
        iHit.setHitNum((int) hit.getHit_num());
        initBHitHsps(iHit, hit.getHit_hsps(), isProt, isBlastn);
        //added to take into account a case when tblastx reports empty HSP!
        if (iHit.countHsp()==0)
        	return null;
        return iHit;        
    }
    
	private Iteration_hits initBOHits(ISRIteration bIter, boolean isProt) {
        Enumeration<SRHit> enu;
		Iteration_hits 	  ith;
		Hit			      hit;
		
		ith = new Iteration_hits();
		enu = bIter.enumerateHit();
		while (enu.hasMoreElements()){
			hit = initHit((ISRHit) enu.nextElement(), isProt);
			if (hit!=null){
				ith.addHit(hit);
			}
		}
		
		return ith;
	}
 

	private void initBIterationHits(ISRIteration bIter, Iteration_hits hits, boolean isProt, boolean isBlastn){
        @SuppressWarnings("rawtypes")
		Enumeration     enu;
        ISRHit           iHit;
        
        if (hits==null)
        	return;
        enu = hits.enumerateHit();
        while(enu.hasMoreElements()){
        	iHit = initBHit((Hit) enu.nextElement(), isProt, isBlastn);
            //added to take into account a case when tblastx reports empty HSP!
        	if (iHit!=null)
        		bIter.addHit(iHit);
        }
    }
    
    
    private Iteration initBOIteration(ISRIteration bIter, boolean isProt) {
    	Iteration		iter;
    	
    	iter = new Iteration();
    	
    	iter.setIteration_iterNum(bIter.getIterationIterNum());
    	iter.setIteration_message(CoreUtil.Cleanup(bIter.getIterationMessage(), true));
    	iter.setIteration_stat(initBOStatistics(bIter.getIterationStat()));
    	iter.setIteration_queryDef(bIter.getIterationQueryID()+" "+CoreUtil.Cleanup(bIter.getIterationQueryDesc(), false));
    	iter.setIteration_queryID(bIter.getIterationQueryID());
    	iter.setIteration_queryLen(bIter.getIterationQueryLength());
    	iter.setIteration_hits(initBOHits(bIter,isProt));
		return iter;
	}
    

	private ISRIteration initBIteration(Iteration iter, boolean isProt, boolean isBlastn){
        ISRIteration    bIter;
        
        bIter = new ISRIteration();
        bIter.setIterationIterNum((int) iter.getIteration_iterNum());
        bIter.setIterationMessage(iter.getIteration_message());
        bIter.setIterationStat(initBStatistics(iter.getIteration_stat()));
        bIter.setIterationQueryDesc(iter.getIteration_queryDef());
        bIter.setIterationQueryID(iter.getIteration_queryID());
        bIter.setIterationQueryLength((int) iter.getIteration_queryLen());
        initBIterationHits(bIter, iter.getIteration_hits(), isProt, isBlastn);
        return bIter;
    }
    
    private BlastOutput_iterations initBOIterations(SROutput b, boolean isProt) {
		
    	BlastOutput_iterations  boi;
    	Enumeration<SRIteration> enu;
    	
    	boi = new BlastOutput_iterations();
    	enu = b.enumerateIteration();    	
    	while (enu.hasMoreElements()){
			boi.addIteration(initBOIteration((ISRIteration) enu.nextElement(), isProt));
    	}
    	return boi;    	
	}

	private void initBOutputIterations(ISROutput ibi, BlastOutput_iterations boi, boolean isProt, boolean isBlastn){
        @SuppressWarnings("rawtypes")
		Enumeration enu;
        
        enu = boi.enumerateIteration();
        while(enu.hasMoreElements()){
            ibi.addIteration(initBIteration((Iteration) enu.nextElement(), isProt, isBlastn));
        }
    }

    private Iteration_stat initBOStatistics(SRStatistics stat) {
    	Iteration_stat 	istat;
    	Statistics 		st;
    	Object          obj;
    	
    	istat = new Iteration_stat();
    	st = new Statistics();
    	if (stat!=null){
    		obj = stat.getValue(STAT_DB_LEN);
    		if (obj!=null)
    			st.setStatistics_dbLen(Integer.parseInt(obj.toString()));
    		else
    			st.setStatistics_dbLen(0);
    			
    		obj = stat.getValue(STAT_DB_NUM);
    		if (obj!=null)
    			st.setStatistics_dbNum(Integer.parseInt(obj.toString()));
    		else
    			st.setStatistics_dbNum(0);
    		obj = stat.getValue(STAT_HSP_LEN);
    		if (obj!=null)
    			st.setStatistics_hspLen(Integer.parseInt(obj.toString()));
    		else
    			st.setStatistics_hspLen(0);
    		obj = stat.getValue(STAT_EFF_SPACE);
    		if (obj!=null)
    			st.setStatistics_effSpace(Double.parseDouble(obj.toString()));
    		else
    			st.setStatistics_effSpace(0);
    		obj = stat.getValue(STAT_ENTROPY);
    		if (obj!=null)
    			st.setStatistics_entropy(Double.parseDouble(obj.toString()));
    		else
    			st.setStatistics_entropy(0);
    		obj = stat.getValue(STAT_KAPPA);
    		if (obj!=null)
    			st.setStatistics_kappa(Double.parseDouble(obj.toString()));
    		else
    			st.setStatistics_kappa(0);
    		obj = stat.getValue(STAT_LAMBDA);
    		if (obj!=null)
    			st.setStatistics_lambda(Double.parseDouble(obj.toString()));
    		else
    			st.setStatistics_lambda(0);
    		
    	}
    	else{
    		//added to export Klast ; stat is a required data block for NCBI XML Castor DTD
    		st.setStatistics_dbLen(0);
    		st.setStatistics_dbNum(0);
    		st.setStatistics_hspLen(0);
    		st.setStatistics_effSpace(0);
    		st.setStatistics_entropy(0);
    		st.setStatistics_kappa(0);
    		st.setStatistics_lambda(0);
    	}
    	istat.setStatistics(st);
    	return istat;
	}
	
	
    private ISRStatistics initBStatistics(Iteration_stat iStat){
        ISRStatistics stat;
        Statistics   st;
        
        stat = new ISRStatistics();
        if (iStat!=null && iStat.getStatistics()!=null){
            st = iStat.getStatistics();
            stat.setValue(STAT_DB_LEN, new Integer((int) st.getStatistics_dbLen()));
            stat.setValue(STAT_DB_NUM, new Integer((int) st.getStatistics_dbNum()));
            stat.setValue(STAT_HSP_LEN, new Integer((int) st.getStatistics_hspLen()));
            stat.setValue(STAT_EFF_SPACE, new Double(st.getStatistics_effSpace()));
            stat.setValue(STAT_ENTROPY, new Double(st.getStatistics_entropy()));
            stat.setValue(STAT_KAPPA, new Double(st.getStatistics_kappa()));
            stat.setValue(STAT_LAMBDA, new Double(st.getStatistics_lambda()));
        }
        return stat;
    }



	private BlastOutput_param initBOParam(SRParameters blastOutputParam) {
		BlastOutput_param bp;
        Parameters   param;
		bp = new BlastOutput_param();
		param = new Parameters();
		
		Object obj;
		
		if (blastOutputParam!=null){
			
			obj = blastOutputParam.getValue(PARAM_MATCH);
				param.setParameters_scMatch(obj!=null?Long.parseLong(obj.toString()):Long.parseLong("-1"));
			
			obj = blastOutputParam.getValue(PARAM_MISMATCH);
				param.setParameters_scMismatch(obj!=null?Long.parseLong(obj.toString()):Long.parseLong("-1"));

			obj = blastOutputParam.getValue(SRParameters.GAPOPEN_DESCRIPTOR_KEY);
				param.setParameters_gapOpen(obj!=null?Integer.parseInt(obj.toString()):Integer.parseInt("-1"));
			
			obj = blastOutputParam.getValue(SRParameters.GAPEXTEND_DESCRIPTOR_KEY);
				param.setParameters_gapExtend(obj!=null?Integer.parseInt(obj.toString()):Integer.parseInt("-1"));
			
			obj = blastOutputParam.getValue(SRParameters.EXPECT_DESCRIPTOR_KEY);
				param.setParameters_expect(obj!=null?Double.parseDouble(obj.toString()):Double.parseDouble("-1"));
			
			obj = blastOutputParam.getValue(PARAM_INCLUDE);
				param.setParameters_include(obj!=null?Double.parseDouble(obj.toString()):Double.parseDouble("-1"));

			obj = blastOutputParam.getValue(PARAM_FILTER);
				param.setParameters_filter(obj!=null?obj.toString():"not specified");			

			obj = blastOutputParam.getValue(PARAM_QUERY);
				param.setParameters_entrezQuery(obj!=null?obj.toString():"not specified");

			obj = blastOutputParam.getValue(SRParameters.MATRIX_DESCRIPTOR_KEY);
				param.setParameters_matrix(obj!=null?obj.toString():"not specified");
		}
		bp.setParameters(param);
		return bp;
	}
    
    private ISRParameters initBParameters(BlastOutput_param bp){
        ISRParameters ibp;
        Parameters   param;
        String       str;
        
        ibp = new ISRParameters();
        param = bp.getParameters();
        if (param!=null){
        	if (param.hasParameters_scMatch())
				ibp.setValue(PARAM_MATCH, new Integer((int) param.getParameters_scMatch()));
			if (param.hasParameters_scMismatch())
        		ibp.setValue(PARAM_MISMATCH,new Integer((int) param.getParameters_scMismatch()));
			if (param.hasParameters_gapOpen())
				ibp.setValue(SRParameters.GAPOPEN_DESCRIPTOR_KEY,new Integer((int) param.getParameters_gapOpen()));
			if (param.hasParameters_gapExtend())
				ibp.setValue(SRParameters.GAPEXTEND_DESCRIPTOR_KEY,new Integer((int) param.getParameters_gapExtend()));
			if (param.hasParameters_expect())
				ibp.setValue(SRParameters.EXPECT_DESCRIPTOR_KEY,new Double(param.getParameters_expect()));
			if (param.hasParameters_include())
				ibp.setValue(PARAM_INCLUDE,new Double(param.getParameters_include()));
			str = param.getParameters_filter();
			if (str!=null)
				ibp.setValue(PARAM_FILTER,str);
			str = param.getParameters_entrezQuery();
			if (str!=null)
				ibp.setValue(PARAM_QUERY,str);
			str = param.getParameters_matrix();
			if (str!=null)
				ibp.setValue(SRParameters.MATRIX_DESCRIPTOR_KEY,str);
        }
        return ibp;
    }

    
	private void initRequestInfo(BlastOutput bo, SROutput b) {
		
		SRRequestInfo sri = b.getRequestInfo();
		
		Object obj;
		
		obj = sri.getValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY);
		bo.setBlastOutput_program(obj!=null?obj.toString():"not specified");
			
		obj = sri.getValue(SRRequestInfo.PRGM_VERSION_DESCRIPTOR_KEY);
		bo.setBlastOutput_version(obj!=null?obj.toString():"not specified");
		
		obj = sri.getValue(SRRequestInfo.PRGM_REFERENCE_DESCRIPTOR_KEY);
		bo.setBlastOutput_reference(obj!=null?CoreUtil.Cleanup(obj.toString(), false):"not specified");
		
		obj = sri.getValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY);
		bo.setBlastOutput_db(obj!=null?obj.toString():"not specified");		
		
		obj = sri.getValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY);
		bo.setBlastOutput_queryID(obj!=null?obj.toString():"not specified");
		
		obj = sri.getValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY);
		bo.setBlastOutput_queryDef(obj!=null?CoreUtil.Cleanup(obj.toString(), false):"not specified");
		
		obj = sri.getValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY);
		bo.setBlastOutput_queryLen(obj!=null?Long.parseLong(obj.toString()):Long.parseLong("-1"));
		
		obj = sri.getValue(SRRequestInfo.QUERY_SEQ_DESCRIPTOR_KEY);
		bo.setBlastOutput_querySeq(obj!=null?obj.toString():"not specified");		
	}
    
    private ISRRequestInfo getRequestInfor(BlastOutput bo){
        ISRRequestInfo ibr;
        
        ibr = new ISRRequestInfo();
        if (bo.getBlastOutput_program()!=null)
        	ibr.setValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY, bo.getBlastOutput_program());
        if (bo.getBlastOutput_version()!=null)
        ibr.setValue(SRRequestInfo.PRGM_VERSION_DESCRIPTOR_KEY, bo.getBlastOutput_version());
        /* This code has been commented out since the original Blast ref contains non UTF-8
         * char for the name of Schaffer... this poses some problems with XML serialization
         * framework. 
        if (bo.getBlastOutput_reference()!=null){
        	ibr.setValue(BRequestInfo.PRGM_REFERENCE_DESCRIPTOR_KEY, bo.getBlastOutput_reference());
        }*/
        if (bo.getBlastOutput_db()!=null)
        	ibr.setValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY, bo.getBlastOutput_db());
        else
        	ibr.setValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY, "unknown");
        if (bo.getBlastOutput_queryID()!=null)
        	ibr.setValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY, bo.getBlastOutput_queryID());
        if (bo.getBlastOutput_queryDef()!=null)
	        ibr.setValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY, bo.getBlastOutput_queryDef());
        if (bo.hasBlastOutput_queryLen())
        	ibr.setValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY, 
        		new Integer((int) bo.getBlastOutput_queryLen()));
        if (bo.getBlastOutput_querySeq()!=null)
        	ibr.setValue(SRRequestInfo.QUERY_SEQ_DESCRIPTOR_KEY, bo.getBlastOutput_querySeq());
    	return ibr;
    }
    
    private BlastOutput initBlastOutput(SROutput b, boolean isProt){
		BlastOutput 	bo;
		
		BlastOutput_param bop;
		BlastOutput_iterations boi;
		
        //IBParameters  ibp;
        //IBRequestInfo ibr;
        
        bo = new BlastOutput();
        
        //copy params
        bop = initBOParam(b.getBlastOutputParam());
        bo.setBlastOutput_param(bop);
        
        //copy request infos
        initRequestInfo(bo,b);
        
        //copy iterations
        boi = initBOIterations(b,isProt);
        bo.setBlastOutput_iterations(boi);
        
        return bo;
    }	
	
	private ISROutput initBOutput(BlastOutput bo, boolean isProt, boolean isBlastn){
        ISROutput      ibi;
        ISRParameters  ibp;
        ISRRequestInfo ibr;
        
        ibi = new ISROutput();
        ibp = initBParameters(bo.getBlastOutput_param());
        ibi.setBlastOutputParam(ibp);
        ibr = getRequestInfor(bo);
        ibi.setRequestInfo(ibr);
        initBOutputIterations(ibi, bo.getBlastOutput_iterations(), isProt, isBlastn);
        return ibi;
    }
    
	public BlastOutput createBlastOutput(SROutput b) {
		BlastOutput 		bo;
		String 				prgName;
		SRRequestInfo 		bri;
		boolean				isProt=false;
		
		bri= b.getRequestInfo();
		prgName = bri.getValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY).toString();
        if (prgName.equals("blastp") ||
            	prgName.equals("tblastx") ||
            	prgName.equals("blastx") ||
            	prgName.equals("tblastn")){
                isProt=true;
            }
		bo = initBlastOutput(b, isProt);

		return bo;
	}
    
    private ISROutput createIBoutput(BlastOutput bo){
        ISROutput          ibi;
        SRIteration        bi;
        SRHit              hit;
        String            prgName;
        int               i, j, k, size, size2, size3;
        boolean           isProt=false, isblastn=false;
        
        prgName = bo.getBlastOutput_program().toLowerCase();
        if (prgName.equals("blastp") ||
        	prgName.equals("tblastx") ||
        	prgName.equals("blastx") ||
        	prgName.equals("tblastn")){
            isProt=true;
        }
        if (prgName.equals("blastn")){
        	isblastn = true;
        }
        ibi = initBOutput(bo, isProt, isblastn);
        if (!ibi.isEmpty()){
            size = ibi.countIteration();
            for(i=0;i<size;i++){
                bi = ibi.getIteration(i);
                size2 = bi.countHit();
                for(j=0;j<size2;j++){
                    hit = bi.getHit(j);
                    size3 = hit.countHsp();
                    for(k=0;k<size3;k++){
                        ((ISRHsp)hit.getHsp(k)).setProteic(isProt);
                    }
                }
            }
        }
        ibi.initialize();
        return ibi;
    }
   
}
