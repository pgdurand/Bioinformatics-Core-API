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
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.log4j.BasicConfigurator;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutput;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutputOptions;

/**
 * Export an annotated BLAST result as a text file.
 * 
 * @author Patrick G. Durand
 */
public class DumpAnnotation {
  private static File     annotatedBlastFile;
  private static SRLoader nativeBlastLoader;

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // init Apache Log4J logger system (required)
    BasicConfigurator.configure();

    // init API factories (required)
    CoreSystemConfigurator.initializeSystem();
    
    // sample annotated Blast result file created using BeeDeeM's Annotate Utility tool
    annotatedBlastFile = new File("data/test/hits_with_full_annot.zml");
    
    // setup an annotated Blast Loader (ZML)
    nativeBlastLoader = SerializerSystemFactory
        .getLoaderInstance(SerializerSystemFactory.NATIVE_LOADER);

    // read ZML annotated blast file
    SROutput bo = nativeBlastLoader.load(annotatedBlastFile);

    assert bo != null;

    // write TXT format with standard output parameters
    TxtExportSROutput exporter = new TxtExportSROutput();
    
    try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))){
      exporter.printOutput(bw, bo, new TxtExportSROutputOptions());  
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
    
  }
}
