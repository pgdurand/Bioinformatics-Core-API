/* Copyright (C) 2006-2017 Patrick G. Durand
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
package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutput;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutputOptions;

/**
 * Export a BLAST result as a text file.
 * 
 * @author Patrick G. Durand
 */
public class ExportFile {
  private static File     blastFile;
  private static File     tmpFile;
  private static SRLoader ncbiBlastLoader;

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // init logger system
    BasicConfigurator.configure();
    // init API factories
    CoreSystemConfigurator.initializeSystem();
    // sample NCBI legacy Blast result
    blastFile = new File("data/test/blastp.xml");
    // setup a temp file (will be deleted in tearDownAfterClass())
    tmpFile = File.createTempFile("blastTest", ".txt");
    System.out.println("Temp file is: " + tmpFile.getAbsolutePath());
    // setup an NCBI Blast Loader (XML)
    ncbiBlastLoader = SerializerSystemFactory
        .getLoaderInstance(SerializerSystemFactory.NCBI_LOADER);

    // read NCBI XML blast file
    SROutput bo = ncbiBlastLoader.load(blastFile);

    assert bo != null;

    // write TXT format with standard output parameters
    TxtExportSROutput exporter = new TxtExportSROutput();
    System.out.println("Save BLAST results in: "+tmpFile.getAbsolutePath());
    
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile))){
      exporter.printOutput(bw, bo, new TxtExportSROutputOptions());  
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
    
  }
}
