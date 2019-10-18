/* Copyright (C) 2019 Patrick G. Durand
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;

import javax.xml.bind.JAXB;

import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRHspSequence;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRParameters;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoaderException;
import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.BlastOutput2;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.BlastXML2;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Hit;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.HitDescr;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Hsp;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Iteration;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Parameters;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Report;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Report.ReportParams;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Results;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Search;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Search.SearchHits;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Search.SearchStat;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Statistics;
import bzh.plealog.bioinfo.data.feature.IFeatureTable;
import bzh.plealog.bioinfo.data.searchresult.ISRHit;
import bzh.plealog.bioinfo.data.searchresult.ISRHsp;
import bzh.plealog.bioinfo.data.searchresult.ISRHspScore;
import bzh.plealog.bioinfo.data.searchresult.ISRHspSequence;
import bzh.plealog.bioinfo.data.searchresult.ISRIteration;
import bzh.plealog.bioinfo.data.searchresult.ISROutput;
import bzh.plealog.bioinfo.data.searchresult.ISRParameters;
import bzh.plealog.bioinfo.data.searchresult.ISRRequestInfo;
import bzh.plealog.bioinfo.data.searchresult.ISRStatistics;
import bzh.plealog.bioinfo.data.sequence.IBankSequenceInfo;
import bzh.plealog.bioinfo.util.DAlphabetUtils;
import bzh.plealog.bioinfo.util.ZipUtil;

public class BlastLoader2 implements SRLoader {
  public static final String SYSTEM_NAME = "XMLBlast2";
  public static String AA_TYPE = "aa";
  public static String NUCL_TYPE = "nucleotide";
  public static String SOURCE_FEAT_TYPE = "source";
  public static String DBXREF_QUAL_TYPE = "dbxref";
  public static String TAXON_CODING_TYPE = "taxon; ";

  private static final String BL2SEQ_ERROR = "Does not handle Blast2Seq results";
  
  public BlastLoader2() {
    
  }

  @Override
  public String getSystemName() {
    return SYSTEM_NAME;
  }

  @Override
  public String getName() {
    return "NCBI XML2 single file formatted data";
  }

  @Override
  public String getVersion() {
    return "1.0";
  }

  @Override
  public boolean canRead(File f) {
    File            f2;
    String          line;
    boolean         bOk = false;
    boolean         isZip = false;
    int             i = 0;

    try (FileInputStream fis = new FileInputStream(f)) {
      f2 = ZipUtil.extract(fis, f.getParent());
      isZip = true;
    }
    catch(Exception e){
      f2 = f;
    }
    try (BufferedReader bReader= new BufferedReader(new FileReader(f2))){ 
      while ((line=bReader.readLine()) != null) {
        if (line.indexOf("<BlastXML2")>=0){
          bOk=true;
        }
        if (line.indexOf("<BlastOutput2>")>=0){
          bOk&=true;
          break;
        }
        i++;
        //above elements have to be located in file head
        if (i>20)
          break;
      }
    } 
    catch (Exception e) {  
    } 
    if (isZip)
      try{f2.delete();}catch(Exception ex){}

    return bOk;
  }

  private ISRHsp initHsp(Hsp hsp, boolean isProt, boolean isBlastn){
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
    score.setBitScore(hsp.getHspBitScore());
    score.setScore(hsp.getHspScore());
    score.setEvalue(hsp.getHspEvalue());
    score.setIdentity(hsp.getHspIdentity());
    score.setPositive(hsp.getHspPositive());
    score.setGaps(hsp.getHspGaps());
    score.setAlignLen(hsp.getHspAlignLen());
    if (hsp.getHspDensity()!=null)
      score.setDensity(hsp.getHspDensity());

    //query sequence
    query.setType(SRHspSequence.TYPE_ALIGNED_SEQ);
    if (hsp.getHspQueryFrame()!=null)
      query.setFrame(hsp.getHspQueryFrame());
    from = hsp.getHspQueryFrom();
    to = hsp.getHspQueryTo();

    if (isBlastn){
      //this has been added to take into account a blastall's bug in XML dump. 
      //Exemple:
      //        1 Format standard from Blast : 
      //        Strand = Plus / Minus
      //        Query: 94     ttggtcgtagtccacatcgtcca 116
      //                     ||| |||||||||||||||||||
      //        Sbjct: 339591 ttgttcgtagtccacatcgtcca 339569
      //
      //
      //        2. Format Xml : it is wrong. Sequences coordinates are switched, query is provided 
      //                        as "reverse complement", whereas frame is provided as "+1" (strand plus).
      //                     <Hsp_query-from>116</Hsp_query-from>
      //                     <Hsp_query-to>94</Hsp_query-to>
      //                     <Hsp_query-frame>1</Hsp_query-frame>
      //                     <Hsp_qseq>TGGACGATGTGGACTACGACCAA</Hsp_qseq>
      //                     <Hsp_hseq>TGGACGATGTGGACTACGAACAA</Hsp_hseq>

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
    seq = hsp.getHspQseq();
    //added to take into account a case when tblastx reports empty HSP!
    if(seq!=null && seq.equals("-")==false){
      if (doRCalignment)
        query.setSequence(DAlphabetUtils.reverseComplement(seq.toUpperCase()));
      else
        query.setSequence(seq.toUpperCase());
    }
    //Hit sequence
    hit.setType(SRHspSequence.TYPE_ALIGNED_SEQ);
    if (hsp.getHspHitFrame()!=null)
      hit.setFrame(hsp.getHspHitFrame());
    from = hsp.getHspHitFrom();
    to = hsp.getHspHitTo();
    if (hit.getFrame()<0){
      hit.setFrom(Math.max(from, to));
      hit.setTo(Math.min(from, to));
    }
    else{
      hit.setFrom(Math.min(from, to));
      hit.setTo(Math.max(from, to));
    }
    seq = hsp.getHspHseq();
    //added to take into account a case when tblastx reports empty HSP!
    if(seq!=null && seq.equals("-")==false){
      if (doRCalignment)
        hit.setSequence(DAlphabetUtils.reverseComplement(seq.toUpperCase()));
      else
        hit.setSequence(seq.toUpperCase());
    }
    //middle sequence
    midline.setType(SRHspSequence.TYPE_MIDLINE);
    seq = hsp.getHspMidline();
    //added to take into account a case when tblastx reports empty HSP!
    if (seq!=null){
      if(doRCalignment)
        midline.setSequence(DAlphabetUtils.reverse(seq.toUpperCase()));
      else
        midline.setSequence(seq.toUpperCase());
    }
    iHsp.setScores(score);
    iHsp.setQuery(query);
    iHsp.setHit(hit);
    iHsp.setMidline(midline);

    iHsp.setHspNum(hsp.getHspNum());
    //iHsp.setHspPatternFrom(hsp.getHsp_patternFrom());
    //iHsp.setHspPatternTo(hsp.getHsp_patternTo());
    return iHsp;
  }

  private void handleTaxonomy(SRHsp hsp, BankSequenceInfo si) {
    if (si==null)
      return;
    FeatureTable ft = new IFeatureTable();
    //We must comply with BeeDeeM annotation handling system
    //but BeeDeeM is not included in this API... so we do hard coding
    Feature f = ft.addFeature(
        SOURCE_FEAT_TYPE, 
        hsp.getHit().getFrom(), 
        hsp.getHit().getTo(), 
        hsp.getHit().getFrame()<0 ? Feature.MINUS_STRAND : Feature.PLUS_STRAND);
    f.addQualifier(
        DBXREF_QUAL_TYPE, 
        TAXON_CODING_TYPE + si.getTaxonomy());
    hsp.setFeatures(ft);
  }
  
  private SRHit initBHit(Hit hit, boolean isProt, boolean isBlastn){
    ISRHit iHit;
    ISRHsp iHsp;
    HitDescr descr;
    int taxID;
    String sciname;
    BankSequenceInfo si = null;
    
    iHit = new ISRHit();
    //for now, only get first description
    descr = hit.getHitDescription().getHitDescr().get(0);
    iHit.setHitAccession(descr.getHitDescrAccession());
    iHit.setHitDef(descr.getHitDescrTitle());
    iHit.setHitId(descr.getHitDescrId());
    iHit.setHitLen(hit.getHitLen());
    iHit.setHitNum(hit.getHitNum());
    //tax info?
    taxID = descr.getHitDescrTaxid();
    sciname = descr.getHitDescrSciname();
    if (sciname!=null) {
      si = new IBankSequenceInfo();
      si.setId(descr.getHitDescrId());
      si.setDescription(descr.getHitDescrTitle());
      si.setMoltype(isProt?AA_TYPE:NUCL_TYPE);
      si.setSequenceSize(hit.getHitLen());
      si.setOrganism(sciname);
      si.setTaxonomy(String.valueOf(taxID));
      iHit.setSequenceInfo(si);
    }
    for (Hsp hsp : hit.getHitHsps().getHsp()) {
      iHsp = initHsp(hsp, isProt, isBlastn);
      if (iHsp!=null) {
        handleTaxonomy(iHsp, si);
        iHit.addHsp(iHsp);
      }
    }
    //added to take into account a case when tblastx reports empty HSP!
    if (iHit.countHsp()==0)
      return null;
    else
      return iHit;        
}
  private void initHits(SRIteration sri, SearchHits shs, boolean isProt, boolean isBlastn) {
    SRHit hit;
    
    for(Hit h : shs.getHit()) {
      hit = initBHit(h, isProt, isBlastn);
      //added to take into account a case when tblastx reports empty HSP!
      if (hit!=null)
        sri.addHit(hit);
    }
  }
  private ISRStatistics initBStatistics(SearchStat iStat){
    ISRStatistics stat;
    Statistics   st;
    
    stat = new ISRStatistics();
    if (iStat!=null && iStat.getStatistics()!=null){
        st = iStat.getStatistics();
        stat.setValue(BlastLoader.STAT_DB_LEN, st.getStatisticsDbLen());
        stat.setValue(BlastLoader.STAT_DB_NUM, st.getStatisticsDbNum());
        stat.setValue(BlastLoader.STAT_HSP_LEN, st.getStatisticsHspLen());
        stat.setValue(BlastLoader.STAT_EFF_SPACE, st.getStatisticsEffSpace());
        stat.setValue(BlastLoader.STAT_ENTROPY, st.getStatisticsEntropy());
        stat.setValue(BlastLoader.STAT_KAPPA, st.getStatisticsKappa());
        stat.setValue(BlastLoader.STAT_LAMBDA, st.getStatisticsLambda());
    }
    return stat;
  }

  private SRIteration initIteration(Search search, int num, boolean isProt, boolean isblastn) {
    SRIteration sri = new ISRIteration();
    sri.setIterationIterNum(num);//it is a one-base value
    sri.setIterationQueryID(search.getSearchQueryId());
    sri.setIterationQueryDesc(search.getSearchQueryTitle());
    sri.setIterationQueryLength(search.getSearchQueryLen());
    sri.setIterationStat(initBStatistics(search.getSearchStat()));
    sri.setIterationMessage(search.getSearchMessage());
    initHits(sri, search.getSearchHits(), isProt, isblastn);
    return sri;
  }
  private void initBOutputIterations(SROutput sro, List<BlastOutput2> lbo, boolean isProt, boolean isblastn) {
    SRIteration sri;
    Search search;
    Results res;
    int num=0;
    
    for(BlastOutput2 bo : lbo) {
      res = bo.getBlastOutput2Report().getReport().getReportResults().getResults();
      //Multi-query BLAST results (i.e. all blast programs but psi-blast)
      if (res.getResultsSearch()!=null) {
        search = res.getResultsSearch().getSearch();
        num++;
        sri = initIteration(search, num, isProt, isblastn);
        sro.addIteration(sri);
      }
      //psi-blast
      else if (res.getResultsIterations()!=null){
        for(Iteration iter : res.getResultsIterations().getIteration()) {
          search = iter.getIterationSearch().getSearch();
          num++;
          sri = initIteration(search, num, isProt, isblastn);
          sro.addIteration(sri);
        }
        //we cannot handle multi-file with psi-blast results
        break;
      }
      //bl2seq
      else {
        //not supported
        continue;
      }
    }
  }
  
  private ISRParameters initBParameters(ReportParams bp){
    ISRParameters ibp;
    Parameters   param;
    String       str;

    ibp = new ISRParameters();
    param = bp.getParameters();
    if (param!=null){
      if (param.getParametersScMatch()!=null)
        ibp.setValue(BlastLoader.PARAM_MATCH, param.getParametersScMatch());
      if (param.getParametersScMismatch()!=null)
        ibp.setValue(BlastLoader.PARAM_MISMATCH, param.getParametersScMismatch());
      if (param.getParametersGapOpen()!=null)
        ibp.setValue(SRParameters.GAPOPEN_DESCRIPTOR_KEY, param.getParametersGapOpen());
      if (param.getParametersGapExtend()!=null)
        ibp.setValue(SRParameters.GAPEXTEND_DESCRIPTOR_KEY, param.getParametersGapExtend());
      ibp.setValue(SRParameters.EXPECT_DESCRIPTOR_KEY,new Double(param.getParametersExpect()));
      if (param.getParametersInclude()!=null)
        ibp.setValue(BlastLoader.PARAM_INCLUDE,new Double(param.getParametersInclude()));
      str = param.getParametersFilter();
      if (str!=null)
        ibp.setValue(BlastLoader.PARAM_FILTER,str);
      str = param.getParametersEntrezQuery();
      if (str!=null)
        ibp.setValue(BlastLoader.PARAM_QUERY,str);
      str = param.getParametersMatrix();
      if (str!=null)
        ibp.setValue(SRParameters.MATRIX_DESCRIPTOR_KEY,str);
    }
    return ibp;
  }
  
  private ISRRequestInfo getRequestInfo(Report rep){
    ISRRequestInfo ibr;
    String str;
    Search s;
    
    //Multi-query BLAST results (i.e. all blast programs but psi-blast)
    if (rep.getReportResults().getResults().getResultsSearch()!=null) {
      s = rep.getReportResults().getResults().getResultsSearch().getSearch();
    }
    //psi-blast
    else if (rep.getReportResults().getResults().getResultsIterations()!=null){
      s = rep.getReportResults().getResults().getResultsIterations().getIteration().get(0).getIterationSearch().getSearch();
    }
    //bl2-seq (not supported for now)
    else {
      throw new RuntimeException(BL2SEQ_ERROR);
    }

    ibr = new ISRRequestInfo();
    if (rep.getReportProgram()!=null)
      ibr.setValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY, rep.getReportProgram());
    if (rep.getReportVersion()!=null)
      ibr.setValue(SRRequestInfo.PRGM_VERSION_DESCRIPTOR_KEY, rep.getReportVersion());
    if (rep.getReportSearchTarget().getTarget().getTargetDb()!=null)
      ibr.setValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY,rep.getReportSearchTarget().getTarget().getTargetDb());
    else
      ibr.setValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY, "unknown");

    str = s.getSearchQueryId();
    if (str!=null)
      ibr.setValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY, str);
    str = s.getSearchQueryTitle();
    if (str!=null)
      ibr.setValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY, str);
    ibr.setValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY, s.getSearchQueryLen());
    return ibr;
  }
  
  private ISROutput initBOutput(Report rep, boolean isProt, boolean isBlastn){
    ISROutput      ibi;
    ISRParameters  ibp;
    ISRRequestInfo ibr;
    
    ibi = new ISROutput();
    ibp = initBParameters(rep.getReportParams());
    ibi.setBlastOutputParam(ibp);
    ibr = getRequestInfo(rep);
    ibi.setRequestInfo(ibr);
    
    return ibi;
}

  private ISROutput createIBoutput(BlastXML2 bo){
    Report            rep;
    ISROutput         ibi;
    SRIteration       bi;
    SRHit             hit;
    String            prgName;
    int               i, j, k, size, size2, size3;
    boolean           isProt=false, isblastn=false;
    
    if (bo.getBlastOutput2().isEmpty()) {
      return null;
    }
    //all BlastOutput are supposed to be generated by same blast program against same DB
    //get first report to init SROutpout data structure
    rep = bo.getBlastOutput2().get(0).getBlastOutput2Report().getReport();
    prgName = rep.getReportProgram().toLowerCase();
    if (prgName.equals("blastp") ||
      prgName.equals("tblastx") ||
      prgName.equals("blastx") ||
      prgName.equals("tblastn")){
        isProt=true;
    }
    if (prgName.equals("blastn")){
      isblastn = true;
    }
    ibi = initBOutput(rep, isProt, isblastn);

    // loop over the many BlastOutput2 XML elements and add them as SRIterations
    initBOutputIterations(ibi, bo.getBlastOutput2(), isProt, isblastn);
    
    //additional init of the data structure
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
  
  @Override
  public SROutput load(File f) throws SRLoaderException {
    BlastXML2 bo;
    SROutput boRet = null;
    File f2;
    boolean isZip = false;

    try (FileInputStream fis = new FileInputStream(f)){
      f2 = ZipUtil.extract(fis, f.getParent());
      isZip = true;
    } catch (Exception e) {
      f2 = f;
    }
    try {
      bo = JAXB.unmarshal(f, BlastXML2.class);
      boRet = createIBoutput(bo);
    } catch (Exception ex) {
      throw new SRLoaderException(ex.toString());
    } finally {
      if (isZip) {
        try {
          f2.delete();
        } catch (Exception ex) {
        }
      }
    }
    return boRet;
  }

  @Override
  /**
   * Always returns null. Not supported for NCBI XML2.
   */
  public SROutput[] multipleLoad(File f) throws SRLoaderException {
    return null;
  }
}
