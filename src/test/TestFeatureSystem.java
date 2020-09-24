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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.AnnotationDataModelConstants;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import bzh.plealog.bioinfo.api.data.feature.Qualifier;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.io.gff.iprscan.IprGffObject;
import bzh.plealog.bioinfo.io.gff.iprscan.IprGffReader;
import bzh.plealog.bioinfo.io.gff.iprscan.IprPrediction;
import bzh.plealog.bioinfo.io.gff.iprscan.IprPredictions;
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
  @Test
  public void testIprGffReader() {
    String gff_file="data/test/iprscan.gff";
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(gff_file);
    assertNotNull(gffMap);
    assertEquals(1, gffMap.size());
    gffMap.forEach((k,v) -> assertTrue(k.equals("AACH01000027")));
    ArrayList<String> types = new ArrayList<>();
    gffMap.forEach((k,v) -> types.addAll(v.stream().map(IprGffObject::getSource).collect(Collectors.toList())));
    List<String> typesRef = Arrays.asList("provided_by_user", "getorf", "getorf", "Pfam");
    assertTrue(types.containsAll(typesRef));
  }
  
  @Test
  public void testIprGffReader2() {
    String gff_file="data/test/iprscan2.gff";
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(gff_file);
    assertNotNull(gffMap);
    assertEquals(4, gffMap.size());
    ArrayList<String> regionIds = new ArrayList<>();
    gffMap.forEach((k,v) -> regionIds.add(k));
    List<String> typesRef = Arrays.asList(
        "GAAA01000103.1", "GAAA01000204.1", 
        "GAAA01000404.1", "GAAA01000403.1");
    int[] counts = {20, 10, 3, 4};
    assertTrue(regionIds.containsAll(typesRef));
    int idx=0;
    for (String key : typesRef) {
      assertEquals(gffMap.get(key).size(), counts[idx]);
      idx++;
    }
  }
  @Test
  public void testFilterUniqueDomains() {
    String gff_file="data/test/iprscan2.gff";
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(gff_file);
    assertNotNull(gffMap);
    assertEquals(4, gffMap.size());
    
    IprPredictions iprs = new IprPredictions(gffMap.get("GAAA01000103.1"));
    
    List<IprPrediction> prs = iprs.getAllDomains();
    assertEquals(20, prs.size());
    Hashtable<String, Long> refValues= new Hashtable<>();
    refValues.put("nucleic_acid", 1l);
    refValues.put("ORF", 1l);
    refValues.put("polypeptide", 1l);
    refValues.put("protein_match", 17l);
    Map<String, Long> nameCount = prs
        .stream()
        .map(i -> i.getIprGffObject().getType())
        .collect(Collectors.groupingBy(string -> string, Collectors.counting()));
    nameCount.forEach((name, count) -> {
      assertEquals(count, refValues.get(name));
    });

    prs = iprs.filterUniqueDomains();
    assertEquals(6, prs.size());
    Hashtable<String, Long> refValues2= new Hashtable<>();
    refValues2.put("nucleic_acid", 1l);
    refValues2.put("ORF", 1l);
    refValues2.put("polypeptide", 1l);
    refValues2.put("protein_match", 3l);
    Map<String, Long> nameCount2 = prs
        .stream()
        .map(i -> i.getIprGffObject().getType())
        .collect(Collectors.groupingBy(string -> string, Collectors.counting()));
    nameCount2.forEach((name, count) -> {
      assertEquals(count, refValues2.get(name));
    });
  }
    
  @Test
  public void testGffAttributeSplitting() {
    String attr="Name=PF00696;signature_desc=Amino acid kinase family;Target=null 84 314;status=T;ID=match$8_84_314;Ontology_term=\"GO:0008652\";date=15-04-2013;Dbxref=\"InterPro:IPR001048\",\"Reactome:REACT_13\",\"InterPro:IPR00725\"";
    
    IprGffObject obj = new IprGffObject();
    
    obj.setAttributes(attr);
    assertEquals(obj.getAttributeValue(IprGffObject.NAME_ATTR),"PF00696");
    assertEquals(obj.getAttributeValue(IprGffObject.SIGNATURE_ATTR),"Amino acid kinase family");

    List<String> gos = 
        Arrays.asList(obj.getAttributeValue(IprGffObject.ONTOLOGY_ATTR).split(","))
        .stream()
        .filter(s -> s.startsWith("GO"))
        .collect(Collectors.toList());
    assertEquals(gos.size(), 1);
    assertEquals(gos.get(0), "GO:0008652");
    
    List<String> iprs = 
        Arrays.asList(obj.getAttributeValue(IprGffObject.DBXREF_ATTR).split(","))
        .stream()
        .filter(s -> s.startsWith("InterPro"))
        .map(s -> s.split(":")[1])
        .collect(Collectors.toList());
    assertEquals(iprs.size(), 2);
    assertEquals(iprs.get(0), "IPR001048");
    assertEquals(iprs.get(1), "IPR00725");
  }

  private void testFeatures(Map<String, List<IprGffObject>> gffMap,String seqID, Hashtable<String, Long> refValues, int gffObject, int feats) {
    IprPredictions iprs = new IprPredictions(gffMap.get(seqID));
    
    List<IprPrediction> prs = iprs.getAllDomains();
    assertEquals(gffObject, prs.size());
    Map<String, Long> nameCount = prs
        .stream()
        .map(i -> i.getIprGffObject().getType())
        .collect(Collectors.groupingBy(string -> string, Collectors.counting()));
    nameCount.forEach((name, count) -> {
      assertEquals(count, refValues.get(name));
    });
    
    FeatureTable ft = iprs.getFeatureTable(true);
    assertEquals(feats, ft.features());
    Enumeration<Feature> enumFeats = ft.enumFeatures();
    if (!setVerboseMode)
      return;
    System.out.println("-- "+seqID);
    while(enumFeats.hasMoreElements()) {
      Feature feat = enumFeats.nextElement();
      System.out.println(feat);
      Enumeration<Qualifier> enumQuals = feat.enumQualifiers();
      while(enumQuals.hasMoreElements()) {
        Qualifier qual = enumQuals.nextElement();
        System.out.println("  " + qual);
      }
    }
  }

  @SuppressWarnings("serial")
  @Test
  public void testRealIprProtGff() {
    String gff_file="data/test/ipr-uniprot-sequences.fasta.gff3";
    
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(gff_file);
    
    assertNotNull(gffMap);
    assertEquals(6, gffMap.size());
    
    ArrayList<String> regionIds = new ArrayList<>();
    gffMap.forEach((k,v) -> regionIds.add(k));
    List<String> typesRef = Arrays.asList(
        "sp|P97756|KKCC1_RAT", "sp|O14733|MP2K7_HUMAN", 
        "tr|Q9NA00|Q9NA00_9CRUS", "sp|Q12851|M4K2_HUMAN", 
        "sp|P47809|MP2K4_MOUSE", "tr|Q91356|Q91356_COTCO");
    int[] counts = {3, 3, 3, 5, 3, 3};
    assertTrue(regionIds.containsAll(typesRef));
    int idx=0;
    for (String key : typesRef) {
      assertEquals(gffMap.get(key).size(), counts[idx]);
      idx++;
    }
    
    testFeatures(
        gffMap, 
        "sp|P97756|KKCC1_RAT", 
        new Hashtable<String, Long>(){{put("polypeptide", 1l);put("protein_match", 2l);}}, 
        3, 
        2);//polypeptide is discarded, then remains two domains
    testFeatures(
        gffMap, 
        "sp|O14733|MP2K7_HUMAN", 
        new Hashtable<String, Long>(){{put("polypeptide", 1l);put("protein_match", 2l);}}, 
        3, 
        2);//polypeptide is discarded, then remains two domains
    testFeatures(
        gffMap, 
        "tr|Q9NA00|Q9NA00_9CRUS", 
        new Hashtable<String, Long>(){{put("polypeptide", 1l);put("protein_match", 2l);}}, 
        3, 
        2);//polypeptide is discarded, then remains two domains
    testFeatures(
        gffMap, 
        "sp|P47809|MP2K4_MOUSE", 
        new Hashtable<String, Long>(){{put("polypeptide", 1l);put("protein_match", 2l);}}, 
        3, 
        2);//polypeptide is discarded, then remains two domains
    testFeatures(
        gffMap, 
        "tr|Q91356|Q91356_COTCO", 
        new Hashtable<String, Long>(){{put("polypeptide", 1l);put("protein_match", 2l);}}, 
        3, 
        2);//polypeptide is discarded, then remains two domains
    testFeatures(
        gffMap, 
        "sp|Q12851|M4K2_HUMAN", 
        new Hashtable<String, Long>(){{put("polypeptide", 1l);put("protein_match", 4l);}}, 
        5, 
        4);
    
  }
  @SuppressWarnings("serial")
  @Test
  public void testRealIprNucGff() {
    String gff_file="data/test/ipr-gb-sequences.fasta.gff3";
    
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(gff_file);
    
    assertNotNull(gffMap);
    assertEquals(6, gffMap.size());
    
    ArrayList<String> regionIds = new ArrayList<>();
    gffMap.forEach((k,v) -> regionIds.add(k));
    List<String> typesRef = Arrays.asList(
        "BC047865.1", "L42810.1", 
        "AF014401.1", "U18310.1", 
        "S65207.1", "AJ292557.1");
    int[] counts = {7, 5, 5, 5, 5, 5};
    assertTrue(regionIds.containsAll(typesRef));
    int idx=0;
    for (String key : typesRef) {
      assertEquals(gffMap.get(key).size(), counts[idx]);
      idx++;
    }
    
    testFeatures(
        gffMap, 
        "BC047865.1", 
        new Hashtable<String, Long>(){{put("nucleic_acid", 1l);put("ORF", 1l);
          put("polypeptide", 1l);put("protein_match", 4l);}}, 
        7, 
        6);
    testFeatures(
        gffMap, 
        "L42810.1", 
        new Hashtable<String, Long>(){{put("nucleic_acid", 1l);put("ORF", 1l);
          put("polypeptide", 1l);put("protein_match", 2l);}}, 
        5, 
        4);
    testFeatures(
        gffMap, 
        "AF014401.1", 
        new Hashtable<String, Long>(){{put("nucleic_acid", 1l);put("ORF", 1l);
          put("polypeptide", 1l);put("protein_match", 2l);}}, 
        5, 
        4);
    testFeatures(
        gffMap, 
        "U18310.1", 
        new Hashtable<String, Long>(){{put("nucleic_acid", 1l);put("ORF", 1l);
          put("polypeptide", 1l);put("protein_match", 2l);}}, 
        5, 
        4);
    testFeatures(
        gffMap, 
        "S65207.1", 
        new Hashtable<String, Long>(){{put("nucleic_acid", 1l);put("ORF", 1l);
          put("polypeptide", 1l);put("protein_match", 2l);}}, 
        5, 
        4);
    testFeatures(
        gffMap, 
        "AJ292557.1", 
        new Hashtable<String, Long>(){{put("nucleic_acid", 1l);put("ORF", 1l);
          put("polypeptide", 1l);put("protein_match", 2l);}}, 
        5, 
        4);
  }

}
