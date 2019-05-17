/* Copyright (C) 2006-2019 Patrick G. Durand
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
package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;
import bzh.plealog.bioinfo.io.searchresult.csv.AnnotationDataModel;
import bzh.plealog.bioinfo.io.searchresult.csv.ExtractAnnotation;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutput;

public class TestFeatureSystem {
  private static File     blastFile;
  private static File     blastFile2;
	private static SRLoader nativeBlastLoader;
  private static boolean _firstHspOnly = false;
  private static boolean _bestHitOnly = false;
	
  private static boolean setVerboseMode = false;
  
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// init logger system
		BasicConfigurator.configure();
		// init API factories
		CoreSystemConfigurator.initializeSystem();
		// sample NCBI legacy Blast result
		blastFile = new File("data/test/hits_with_full_annot.zml");
		blastFile2 = new File("data/test/hits_with_bco.zml");
		// setup a native BOutput loader
		nativeBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NATIVE_LOADER);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCanRead() {
		assertTrue(nativeBlastLoader.canRead(blastFile));
	}

	@Test
	public void testRead() {
		// read NCBI XML blast file
		SROutput bo = nativeBlastLoader.load(blastFile);
		assertNotNull(bo);
		assertTrue(bo.getIteration(0).countHit()==6);
	}
	private void checkClassification(SRClassification classif, String set_of_ids) {
    StringTokenizer tokenizer;
    
    if (setVerboseMode) {
      System.out.println("   " + set_of_ids);
    }
    if ("-".equals(set_of_ids)) {
      return;
    }
    tokenizer = new StringTokenizer(set_of_ids, ";");
    while(tokenizer.hasMoreTokens()) {
      assertNotNull(classif.getTerm(tokenizer.nextToken()));
    }
    
	}
	@Test
	public void testAnnotation(){
    TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>> annotatedHitsHashMap = null;
    TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>> annotationDictionary = null;

    // LOad sample data set (BLASTp results annotated using BeeDeeM Annotatot Tool)
    SROutput bo = nativeBlastLoader.load(blastFile2);
    assertNotNull(bo);
    
    // Extract Bio Classification: IPR, EC, GO and TAX
    annotatedHitsHashMap = new TreeMap<String, TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, HashMap<String, AnnotationDataModel>>>();
    annotationDictionary = new TreeMap<AnnotationDataModelConstants.ANNOTATION_CATEGORY, TreeMap<String, AnnotationDataModel>>();
    ExtractAnnotation.buildAnnotatedHitDataSet(bo, 0, annotatedHitsHashMap, annotationDictionary);
    SRClassification classif = ExtractAnnotation.buildClassificationDataSet(annotationDictionary);
    
    int i, j, k, size, size2, size3;
    SRIteration iteration;
    SRHit hit;
    SRHsp hsp;
    String s;

    // Loop over each Hit and get Bio Classification data
    size = bo.countIteration();
    for (i = 0; i < size; i++) {// loop on iterations
      iteration = bo.getIteration(i);
      size2 = iteration.countHit();
      for (j = 0; j < size2; j++) {// loop on hits
        hit = iteration.getHit(j);
        if (setVerboseMode) {
          System.out.println("> " + hit.getHitId());
        }
        size3 = hit.countHsp();
        for (k = 0; k < size3; k++) {// loop on hsp
          hsp = hit.getHsp(k);
          s = TxtExportSROutput.getFormattedData(
              annotatedHitsHashMap, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_TAX, false, false);
          checkClassification(classif, s);
          s = TxtExportSROutput.getFormattedData(
              annotatedHitsHashMap, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_GO, false, false);
          checkClassification(classif, s);
          s = TxtExportSROutput.getFormattedData(
              annotatedHitsHashMap, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_EC, false, false);
          checkClassification(classif, s);
          s = TxtExportSROutput.getFormattedData(
              annotatedHitsHashMap, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_IPR, false, false);
          checkClassification(classif, s);
          if (_firstHspOnly) {
            break;
          }
        }
        if (_bestHitOnly) {
          break;
        }
      }
    }
    
    //do some cleaning
    if (annotatedHitsHashMap != null)
      annotatedHitsHashMap.clear();
    if (annotationDictionary != null)
      annotationDictionary.clear();
	}
}
