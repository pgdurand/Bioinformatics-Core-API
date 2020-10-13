/* Copyright (C) 2020 Patrick G. Durand
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
package bzh.plealog.bioinfo.tools;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.log4j.BasicConfigurator;

import com.plealog.genericapp.api.log.EZLogger;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriterException;
import bzh.plealog.bioinfo.io.gff.iprscan.IprGffObject;
import bzh.plealog.bioinfo.io.gff.iprscan.IprGffReader;
import bzh.plealog.bioinfo.io.gff.iprscan.IprPredictions;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;
import bzh.plealog.bioinfo.io.searchresult.csv.ExtractAnnotation;
import bzh.plealog.bioinfo.util.CmdLineUtils;

/**
 * Utility tool to add IprScan domains predictions into a BLAST or PLAST result file.
 * 
 * Review usage by executing this class with -h argument.
 * 
 * @author Patrick G. Durand
 */
public class ImportIprScanPredictions {

  
  private static final String IN_KEY = "in";
  private static final String IPR_KEY = "ipr";
  private static final String OUT_KEY = "out";
  
  public ImportIprScanPredictions() {
    
  }
  
  /**
   * Setup the valid command-line of the application.
   */
  @SuppressWarnings("static-access")
  private static Options getCmdLineOptions() {
    Options opts;
   
    Option in = OptionBuilder
        .withArgName( IN_KEY )
        .hasArg()
        .isRequired()
        .withDescription( "BLAST input file" )
        .create(IN_KEY);
    Option out = OptionBuilder
        .withArgName( OUT_KEY )
        .hasArg()
        .isRequired()
        .withDescription( "Output file, zml format ready for BlastViewer" )
        .create(OUT_KEY);
    Option ipr = OptionBuilder
        .withArgName( IPR_KEY )
        .hasArg()
        .isRequired()
        .withDescription( "IprScan domain prediction file, GFF3 formated." )
        .create(IPR_KEY);

    opts = new Options();
    opts.addOption(in);
    opts.addOption(out);
    opts.addOption(ipr);
    CmdLineUtils.setHelpOption(opts);
    
    return opts;
  }
  
  /**
   * Add IPRscan domain predictions into queries reported in a BLAST or PLAST result.
   * 
   * @param bo the BLAST/PLAST result to annotate
   * @param gffMap list of IPRscan domain predictions
   * 
   * @return number of queries annotated with domains
   */
  public int annotateBlastWithIprscan(SROutput bo, Map<String, List<IprGffObject>> gffMap) {
    int nqueriesAnnotated = 0; 
    //annotate queries located in the blast results with IPRscan domain prediction
    Enumeration<SRIteration> enumIter = bo.enumerateIteration();
    while(enumIter.hasMoreElements()) {
      SRIteration iter = enumIter.nextElement();
      String qId = iter.getIterationQueryID();
      List<IprGffObject> gffObjs = gffMap.get(qId);
      if (gffObjs==null) {
        qId = iter.getIterationQueryDesc().split(" ")[0];
        gffObjs = gffMap.get(qId);
      }
      if (gffObjs!=null) {
        nqueriesAnnotated++;
        FeatureTable ft = new IprPredictions(gffObjs).getFeatureTable(true);
        iter.setIterationQueryFeatureTable(ft);
      }
    }
    if (nqueriesAnnotated==0) {
      return 0;
    }
    //Re-collect all unique classifications objects if needed (from hits data)
    SRClassification classif = bo.getClassification();
    if (classif==null) {
      classif = ExtractAnnotation.getClassificationdata(bo);
    }
    
    //add annotation data from queries
    enumIter = bo.enumerateIteration();
    while(enumIter.hasMoreElements()) {
      SRIteration iter = enumIter.nextElement();
      ExtractAnnotation.prepareClassificationdata(classif, iter.getIterationQueryFeatureTable());
    }
    
    bo.setClassification(classif);
    return nqueriesAnnotated;
  }
  
  /**
   * Annotate a BLAST or PLAST results file with IprScan domains.
   * 
   * @param bo_file the BLAST or PLAST result file. Can be either legacy NCBI BLAST, NCBI BLAST 2 or ZML
   * @param iprs_file the IprScan result file GFF3 format
   * @param out_file where to write results
   * 
   * @return true if success false otherwise
   */
  public boolean importData(File bo_file, File iprs_file, File out_file) {
    SROutput sro = null;

    SRLoader nativeBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NATIVE_LOADER);
    SRLoader ncbiBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER);
    SRLoader ncbiBlastLoader2 = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER2);
    
    try {
      if (ncbiBlastLoader.canRead(bo_file)) {
        sro = ncbiBlastLoader.load(bo_file);
      }
      else if (ncbiBlastLoader2.canRead(bo_file)) {
        sro = ncbiBlastLoader2.load(bo_file);
      }
      else if (nativeBlastLoader.canRead(bo_file)) {
        sro = nativeBlastLoader.load(bo_file);
      }
      else {
        throw new RuntimeException(String.format("unable to read BLAST file: %s",bo_file.getAbsolutePath()));
      }
    } catch (Exception e) {
      EZLogger.warn(e.toString());
      return false;
    }
    
    if(sro==null || sro.isEmpty()) {
      EZLogger.warn("BLAST file is empty");
      return false;
    }
    int nqueries = sro.getIterations().size();
    
    EZLogger.info(
        String.format("BLAST file contains results for %d %s", 
            nqueries, nqueries>1 ? "queries" : "query"));
    
    //Load interpro-scan data file
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(iprs_file.getAbsolutePath());
    
    if(gffMap==null || gffMap.isEmpty()) {
      EZLogger.warn(
          String.format("No IprScan predictons loaded from: %s", iprs_file.getAbsolutePath()));
      return false;
    }
    
    EZLogger.info(
        String.format("IprScan predictions imported for %d sequences", gffMap.keySet().size()));
    
    //annotate Blast result (queries) with IPRscan predictions
    int nqueriesAnnotated = annotateBlastWithIprscan(sro, gffMap);
    EZLogger.info(
        String.format("%d %s been annotated with IPRscan domains", 
            nqueriesAnnotated, nqueriesAnnotated>1 ? "queries have" : "query has"));
    
    
    
    // setup a native BOutput writer and write results
    SRWriter nativeBlastWriter = SerializerSystemFactory.getWriterInstance(SerializerSystemFactory.NATIVE_WRITER);
    try {
      nativeBlastWriter.write(out_file, sro);
    } catch (SRWriterException e) {
      EZLogger.warn(e.toString());
      return false;
    }
    
    return true;
  }

  /**
   * Annotate a BLAST or PLAST results file with IprScan domains.
   * 
   * Environment variables are accepted in file paths. e.g. $HOME, (Linux and macOS) %HOME% (Windows).
   * 
   * @param bo_file the BLAST or PLAST result file. Can be either legacy NCBI BLAST, NCBI BLAST 2 or ZML
   * @param iprs_file the IprScan result file GFF3 format
   * @param out_file where to write results
   * 
   * @return true if success false otherwise
   */
  public boolean importData(String bo_file, String iprs_file, String out_file) {
    String input = CmdLineUtils.expandEnvVars(bo_file);
    String iprscan = CmdLineUtils.expandEnvVars(iprs_file);
    String output = CmdLineUtils.expandEnvVars(out_file);
    
    return importData(new File(input), new File(iprscan), new File(output));
  }
  
  /**
   * Entry point of the tool.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    CommandLine cmdLine;
    Options options;
    
    BasicConfigurator.configure();
    options = getCmdLineOptions();
    cmdLine = CmdLineUtils.handleArguments(args, options, ImportIprScanPredictions.class.getSimpleName(), "");
    if (cmdLine == null) {
      System.exit(1);
    }

    if (!new ImportIprScanPredictions().importData(
        cmdLine.getOptionValue(IN_KEY), 
        cmdLine.getOptionValue(IPR_KEY), 
        cmdLine.getOptionValue(OUT_KEY))) {
      System.exit(1);
    }
    
  }

}
