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

import java.io.File;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.sequence.BankSequenceInfo;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;

/**
 * Explore an annotated BLAST result using Core API.
 * 
 * @author Patrick G. Durand
 */
public class DumpAnnotationManually {
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

    // Now, let's explore the content of the SROutput object.
    // SR stands for Search Result.
    //
    // SROutput API is here: https://pgdurand.github.io/Bioinformatics-Core-API/api/bzh/plealog/bioinfo/api/data/searchresult/package-summary.html
    SRIteration iter;
    SRHit hit;
    SRHsp hsp;
    int i, j;
    FeatureTable ft;
    BankSequenceInfo si;
    
    // we only get the first iteration
    iter = bo.getIteration(0);
    
    //scan through many hits and hsps
    for (i=0; i< iter.countHit(); i++){
      hit = iter.getHit(i);
      for (j=0; j< hit.countHsp(); j++){
        hsp = hit.getHsp(j);
        // a FeatureTable is contained on the HSP.
        ft = hsp.getFeatures();
        // From here you can do whatever you want with Features. 
        // In this sample, we just dump #features
        // Features API: https://pgdurand.github.io/Bioinformatics-Core-API/api/bzh/plealog/bioinfo/api/data/feature/package-summary.html
        System.out.println(String.format("Hit:%d-Hsp:%d: %d features", i, j, ft.features()));
        
        // Now, you can also get description of the Hit as follows:
        si = hit.getSequenceInfo();
        System.out.println(String.format("  %s", si.getOrganism()));
        System.out.println(String.format("  %s", si.getTaxonomy()));
      }
    }
  }
}
