package bzh.plealog.bioinfo.io.searchresult.ncbi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.xml.bind.JAXB;

import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRParameters;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoaderException;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Parameters;
import bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.BlastXML2;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Report;
import bzh.plealog.bioinfo.data.blast.loader.ncbi2.Report.ReportParams;
import bzh.plealog.bioinfo.data.searchresult.ISRHsp;
import bzh.plealog.bioinfo.data.searchresult.ISROutput;
import bzh.plealog.bioinfo.data.searchresult.ISRParameters;
import bzh.plealog.bioinfo.data.searchresult.ISRRequestInfo;
import bzh.plealog.bioinfo.util.ZipUtil;

public class BlastLoader2 implements SRLoader {
  public static final String SYSTEM_NAME ="XMLBlast2";
  public BlastLoader2() {
    // TODO Auto-generated constructor stub
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
        if (i>10)
          break;
      }
    } 
    catch (Exception e) {  
    } 
    if (isZip)
      try{f2.delete();}catch(Exception ex){}

    return bOk;
  }

  private ISRParameters initBParameters(ReportParams bp){
    ISRParameters ibp;
    Parameters   param;
    String       str;

    ibp = new ISRParameters();
    param = bp.getParameters();
    if (param!=null){
      ibp.setValue(BlastLoader.PARAM_MATCH, new Integer(param.getParametersScMatch().intValue()));
      ibp.setValue(BlastLoader.PARAM_MISMATCH,new Integer(param.getParametersScMismatch().intValue()));
      ibp.setValue(SRParameters.GAPOPEN_DESCRIPTOR_KEY,new Integer(param.getParametersGapOpen().intValue()));
      ibp.setValue(SRParameters.GAPEXTEND_DESCRIPTOR_KEY,new Integer(param.getParametersGapExtend().intValue()));
      ibp.setValue(SRParameters.EXPECT_DESCRIPTOR_KEY,new Double(param.getParametersExpect()));
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

    ibr = new ISRRequestInfo();
    if (rep.getReportProgram()!=null)
      ibr.setValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY, rep.getReportProgram());
    if (rep.getReportVersion()!=null)
      ibr.setValue(SRRequestInfo.PRGM_VERSION_DESCRIPTOR_KEY, rep.getReportVersion());
    if (rep.getReportSearchTarget().getTarget().getTargetDb()!=null)
      ibr.setValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY,rep.getReportSearchTarget().getTarget().getTargetDb());
    else
      ibr.setValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY, "unknown");

    //TODO: faire un psi-blast et un bl2seq
    
    str = rep.getReportResults().getResults().getResultsSearch().getSearch().getSearchQueryId();
    if (str!=null)
      ibr.setValue(SRRequestInfo.QUERY_ID_DESCRIPTOR_KEY, str);
    str = rep.getReportResults().getResults().getResultsSearch().getSearch().getSearchQueryTitle();
    if (str!=null)
      ibr.setValue(SRRequestInfo.QUERY_DEF_DESCRIPTOR_KEY, str);
    ibr.setValue(SRRequestInfo.QUERY_LENGTH_DESCRIPTOR_KEY, 
        new Integer(rep.getReportResults().getResults().getResultsSearch().getSearch().getSearchQueryLen().intValue()));
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
    initBOutputIterations(ibi, bo.getBlastOutput_iterations(), isProt, isBlastn);
    return ibi;
}

  private ISROutput createIBoutput(BlastXML2 bo){
    ISROutput          ibi;
    SRIteration        bi;
    SRHit              hit;
    String            prgName;
    int               i, j, k, size, size2, size3;
    boolean           isProt=false, isblastn=false;
    
    if (bo.getBlastOutput2().isEmpty()) {
      return null;
    }
    //all BlastOutput are supposed to be generated by same blast program
    prgName = bo.getBlastOutput2().get(0).getBlastOutput2Report().getReport().getReportProgram().toLowerCase();
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
  
  @Override
  public SROutput load(File f) throws SRLoaderException {
    BlastXML2 bo;
    SROutput boRet = null;
    PipedInputStream writeIn;
    PipedOutputStream readOut;
    FileInputStream fis = null;
    File f2;
    boolean isZip = false;

    try {
      fis = new FileInputStream(f);
      f2 = ZipUtil.extract(fis, f.getParent());
      isZip = true;
    } catch (Exception e) {
      f2 = f;
    }
    if (fis != null) {
      try {
        fis.close();
      } catch (Exception ex) {
      }
    }
    try {
      /*writeIn = new PipedInputStream();
      readOut = new PipedOutputStream(writeIn);
      ReadThread rt = new ReadThread(new BufferedReader(new FileReader(f2)), readOut);
      rt.start();*/

      bo = JAXB.unmarshal(f, BlastXML2.class);
      boRet = createIBoutput(bo);
      
      /*if (!rt.bError) {
        boRet = createIBoutput(bo);
      }*/
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
