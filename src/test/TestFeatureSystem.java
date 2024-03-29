/* Copyright (C) 2006-2020 Patrick G. Durand
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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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
import bzh.plealog.bioinfo.api.data.searchjob.SJFileSummary;
import bzh.plealog.bioinfo.api.data.searchresult.SRClassification;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRHsp;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.io.gff.iprscan.IprGffObject;
import bzh.plealog.bioinfo.io.gff.iprscan.IprGffReader;
import bzh.plealog.bioinfo.io.gff.iprscan.IprPrediction;
import bzh.plealog.bioinfo.io.gff.iprscan.IprPredictions;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;
import bzh.plealog.bioinfo.io.searchresult.csv.ExtractAnnotation;
import bzh.plealog.bioinfo.io.searchresult.txt.TxtExportSROutput;
import bzh.plealog.bioinfo.io.tsv.eggnog.EggNogObject;
import bzh.plealog.bioinfo.io.tsv.eggnog.EggNogTsvReader;
import bzh.plealog.bioinfo.io.xml.iprscan.IprXmlReader;
import bzh.plealog.bioinfo.tools.ImportIprScanPredictions;

public class TestFeatureSystem {
  private static File     blastFile;
  private static File     blastFile2;
  private static File     blastFile3;
  private static File     blastFile4;
  private static File     eggnogFile;
  private static File     iprscanFile;
  private static File     iprscanProtFileXml;
  private static File     iprscanNuclFileXml;
  private static File     iprscanNuclFileXml2;
  private static File     tmpFile;
  private static SRLoader nativeBlastLoader;
  private static SRLoader ncbiBlastLoader2;
  private static SRWriter nativeBlastWriter;
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
    //eggnog-mapper data file
    eggnogFile = new File("data/test/eggnog-mapper-sample.tsv");
		//iprscan prot data file
		iprscanFile = new File("data/test/ipr-uniprot-sequences.fasta.gff3");
    //iprscan prot data file
    iprscanProtFileXml = new File("data/test/ipr-uniprot-sequences.fasta.xml");
    //iprscan nucl data file
    iprscanNuclFileXml = new File("data/test/ipr-gb-sequences.fasta.xml");
    iprscanNuclFileXml2 = new File("data/test/GAAA01.1_1-500-subset-ipscan.xml");
		//corresponding blastp file (contains sams queries as iprscan date file)
		blastFile3 = new File("data/test/ipr-uniprot-sequences-blastp-sw.xml");
	  // setup a temp file (will be deleted in tearDownAfterClass())
    tmpFile = File.createTempFile("featureTest", ".zml");
    //
    blastFile4 = new File("data/test/blastp-71queries-swissprot-bco.zml");

    //dump tmp file to help debugging
    System.out.println("Temp file is: "+ tmpFile.getAbsolutePath());
		// setup a native BOutput loader
		nativeBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NATIVE_LOADER);
	  // setup an NCBI Blast Loader (XML2 single file)
		ncbiBlastLoader2 = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER2);
	  // setup a native BOutput writer
    nativeBlastWriter = SerializerSystemFactory.getWriterInstance(SerializerSystemFactory.NATIVE_WRITER);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	  //tmpFile.delete();
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
    String token;
    while(tokenizer.hasMoreTokens()) {
      token = tokenizer.nextToken();
      assertNotNull(classif.getTerm(token));
    }
    
	}
	@Test
	public void testAnnotation(){
    // LOad sample data set (BLASTp results annotated using BeeDeeM Annotatot Tool)
    SROutput bo = nativeBlastLoader.load(blastFile2);
    assertNotNull(bo);
    
    // Extract Bio Classification: IPR, EC, GO and TAX
    Map<AnnotationDataModelConstants.ANNOTATION_CATEGORY, SRClassification> ftClassif=null;
    SRClassification classif = ExtractAnnotation.getClassificationdata(bo);
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
          ftClassif = ExtractAnnotation.prepareClassification(
              classif, 
              hsp.getFeatures());
          s = TxtExportSROutput.getFormattedData(
              ftClassif, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_TAX, false, false);
          checkClassification(classif, s);
          s = TxtExportSROutput.getFormattedData(
              ftClassif, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_GO, false, false);
          checkClassification(classif, s);
          s = TxtExportSROutput.getFormattedData(
              ftClassif, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_EC, false, false);
          checkClassification(classif, s);
          s = TxtExportSROutput.getFormattedData(
              ftClassif, iteration, hit, hsp, TxtExportSROutput.BIO_CLASSIF_IPR, false, false);
          checkClassification(classif, s);
          ftClassif.clear();
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
    if (ftClassif != null)
      ftClassif.clear();
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
    iprs.setIprscanVersion(gr.getIprScanVersion());
    iprs.setIprscanDate(gr.getIprScanDate());
    
    assertEquals(iprs.getIprscanVersion(), "5.28-67.0");
    assertEquals(iprs.getIprscanDate(), "11-09-2020");
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

  private void controlAnnotatedBlast(SROutput bo) {
    assertNotNull(bo.getClassification());
    Enumeration<SRIteration> enumIter = bo.enumerateIteration();
    int countAnnotatedQueries=0;
    while(enumIter.hasMoreElements()) {
      SRIteration iter = enumIter.nextElement();
      countAnnotatedQueries += (iter.getIterationQueryFeatureTable()!=null ? 1:0);
    }
    assertEquals(6, countAnnotatedQueries);
  }
  
  @Test
  public void testIprAnnotateBlastResult() {
    //Load interpro-scan data file
    String gff_file=iprscanFile.getAbsolutePath();
    IprGffReader gr = new IprGffReader();
    Map<String, FeatureTable> gffMap = gr.readFile(gff_file);
    assertNotNull(gffMap);
    assertEquals(6, gffMap.size());
    
    //Load corresponding  blastp data file
    SROutput bo = ncbiBlastLoader2.load(blastFile3);
    assertNotNull(bo);
    assertTrue(bo.countIteration()==6);

    //annotate Blast result (queries) with IPRscan predictions
    new ImportIprScanPredictions().annotateBlastWithIprscan(bo, gffMap);
    
    //do a little control: all queries should have been annotated
    controlAnnotatedBlast(bo);
  }

  @Test
  public void testIprAnnotateBlastResult_IO() {
    //Load interpro-scan data file
    String gff_file=iprscanFile.getAbsolutePath();
    IprGffReader gr = new IprGffReader();
    Map<String, FeatureTable> gffMap = gr.readFile(gff_file);
    assertNotNull(gffMap);
    assertEquals(6, gffMap.size());
    
    //Load corresponding  blastp data file
    SROutput bo = ncbiBlastLoader2.load(blastFile3);
    assertNotNull(bo);
    assertTrue(bo.countIteration()==6);

    //annotate Blast result (queries) with IPRscan predictions
    new ImportIprScanPredictions().annotateBlastWithIprscan(bo, gffMap);

    //save annotated blast results
    nativeBlastWriter.write(tmpFile, bo);
    //reload that file
    bo = nativeBlastLoader.load(tmpFile);
    
    //do a little control: all queries should have been annotated
    controlAnnotatedBlast(bo);
    
  }
  
  @Test
  public void testSRFileSummary(){
    System.out.println("> "+new Object(){}.getClass().getEnclosingMethod().getName());
    //Load corresponding  blastp data file
    long time = System.currentTimeMillis();
    SROutput bo = nativeBlastLoader.load(blastFile4);
    System.out.println("Loading time: "+(System.currentTimeMillis()-time) + "ms");
    
    time = System.currentTimeMillis();
    SJFileSummary summary = new SJFileSummary();
    summary.initialize(bo);
    System.out.println("Processing time: "+(System.currentTimeMillis()-time) + "ms");
    
    ArrayList<String> regionIds = new ArrayList<>();
    summary.getHitClassificationForView().forEach(k -> regionIds.add(k.getID()));
    ArrayList<String> typesRef = new ArrayList<String>(Arrays.asList(
        "GO:0042470", "GO:0019904", 
        "GO:0045744", "IPR000308", 
        "IPR023409", "IPR023410",
        "IPR036815","PF00244"));
    assertTrue(regionIds.containsAll(typesRef));
    
    typesRef.add("TAX:9601");
    regionIds.clear();
    Enumeration<String> ids = summary.getHitClassification().getTermIDs();
    while(ids.hasMoreElements()) {
      regionIds.add(ids.nextElement());
    }
    assertTrue(regionIds.containsAll(typesRef));
  }
  
  @Test
  public void testIprAnnotateTool() {
    
    assertTrue(new ImportIprScanPredictions().importData(
        blastFile3.getAbsolutePath(), 
        iprscanFile.getAbsolutePath(), 
        tmpFile.getAbsolutePath()));
    
    //reload result file created above
    SROutput bo = nativeBlastLoader.load(tmpFile);
    
    //do a little control: all queries should have been annotated
    controlAnnotatedBlast(bo);
    
  }
  
  @SuppressWarnings("serial")
  @Test
  public void testRealIprNucDomainsGff() {
    String gff_file="data/test/domains.fsa_nt.gff3";
    
    IprGffReader gr = new IprGffReader();
    Map<String, List<IprGffObject>> gffMap = gr.processFileToMap(gff_file);
    
    assertNotNull(gffMap);
    
    List<IprGffObject> gffObjs = gffMap.get("GAAA01000002.1");
    assertNotNull(gffObjs);
    
    IprPredictions iprs = new IprPredictions(gffObjs);
    FeatureTable ft = iprs.getFeatureTable(true);
    
    assertNotNull(ft);
    assertEquals(ft.features(), 3);
    
    HashSet<String> refValues= new HashSet<String>(){{
      add("source : 1..1068");
      add("protein : 309..575");
      add("protein : 664..1068 (-)");
      
    }};
    Enumeration<Feature> feats = ft.enumFeatures();
    while(feats.hasMoreElements()) {
      Feature feat = feats.nextElement();
      assertTrue(refValues.contains(feat.toString()));
    }
    
    gffObjs = gffMap.get("GAAA01000005.1");
    assertNotNull(gffObjs);
    
    iprs = new IprPredictions(gffObjs);
    ft = iprs.getFeatureTable(true);
    
    assertNotNull(ft);
    assertEquals(ft.features(), 5);
    
    refValues= new HashSet<String>(){{
      add("source : 1..1647");
      add("protein : 759..1256 (-)");
      add("domain : 848..971 (-)");
      add("domain : 848..971 (-)");
      add("protein : 979..1161 (-)");
    }};
    feats = ft.enumFeatures();
    while(feats.hasMoreElements()) {
      Feature feat = feats.nextElement();
      assertTrue(refValues.contains(feat.toString()));
    }
  }
  @Test
  public void testcountHitsByClassification() {
    System.out.println("> "+new Object(){}.getClass().getEnclosingMethod().getName());
    // read NCBI XML blast file
    SROutput bo = nativeBlastLoader.load(blastFile4);
    assertNotNull(bo);
    Map<String, Integer> data = ExtractAnnotation.countHitsByClassification(bo);
    /*for (String key : data.keySet()) {
      System.out.println(key+"="+data.get(key));
    }*/
    @SuppressWarnings("serial")
    Hashtable<String, Integer> refValues= new Hashtable<String, Integer>(){{
      put("GO:F",57);
      put("GO:P",61);
      put("PF",64);
      put("TAX",71);
      put("GO:C",69);
      put("EC",4);
      put("IPR",70);
    }};
    assertEquals(refValues.size(), data.size());
    for (String key : data.keySet()) {
      assertEquals(refValues.get(key), data.get(key));
    }
  }
  
  private void testFeature(FeatureTable ft, List<DomainReference> dRefs) {
    List<String> domains = Collections.list(ExtractAnnotation.getClassificationdata(ft).getTermIDs());
    ArrayList<String> domainsRefs = new ArrayList<>();
    dRefs.forEach(k -> domainsRefs.addAll(k.domainIds));
    assertTrue(domains.containsAll(domainsRefs));
    
    ArrayList<Integer> locs = new ArrayList<>();
    Collections.list(ft.enumFeatures()).forEach(f -> {locs.add(f.getFrom()); locs.add(f.getTo());});
    ArrayList<Integer> locsRefs = new ArrayList<>();
    dRefs.forEach(k -> {locsRefs.add(k.start); locsRefs.add(k.end);});
    assertTrue(locs.containsAll(locsRefs));
    
  }

  @Test
  public void testProtIprScanXmlLoader() {
    //Load interpro-scan data file
    String xml_file=iprscanProtFileXml.getAbsolutePath();
    IprXmlReader reader = new IprXmlReader();
    Map<String, FeatureTable> predictions= reader.readFile(xml_file);
    assertTrue(predictions.isEmpty()==false);
    assertEquals(predictions.size(), 6);
    
    @SuppressWarnings("serial")
    HashMap<String, List<DomainReference>> 
    domains = new HashMap<String, List<DomainReference>>() {{
      put("sp|Q12851|M4K2_HUMAN", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(492,789, new ArrayList<String>(Arrays.asList("PF00780",
                "IPR001180"))));
            add(new DomainReference(16,272, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(16,273, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(482,793, new ArrayList<String>(Arrays.asList("PS50219",
                "IPR001180"))));
          }}
      );
      put("sp|P97756|KKCC1_RAT", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(128,409, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(128,409, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("sp|O14733|MP2K7_HUMAN", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(121,380, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(120,380, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("tr|Q9NA00|Q9NA00_9CRUS", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(213,486, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(212,486, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("sp|P47809|MP2K4_MOUSE", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(102,365, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(100,365, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("tr|Q91356|Q91356_COTCO", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(
              7, 
              169, 
              new ArrayList<String>(Arrays.asList(
                  "IPR001245", "GO:0006468",
                  "GO:0004672","PF07714"))));
            add(new DomainReference(
              1, 
              173, 
              new ArrayList<String>(Arrays.asList(
                  "PS50011", "IPR000719",
                  "GO:0006468","GO:0004672",
                  "GO:0005524"))));  
          }}
      );
    }};
    
    List<String> typesRef = predictions.keySet().stream().collect(Collectors.toList());
    ArrayList<String> regionIds = new ArrayList<>();
    predictions.forEach((k,v) -> regionIds.add(k));
    assertTrue(regionIds.containsAll(typesRef));

    for (String key : typesRef) {
      assertEquals(domains.get(key).size(), predictions.get(key).features());
      testFeature(predictions.get(key), domains.get(key));
    }
  }
  
  @Test
  public void testNuclIprScanXmlLoader() {
    //Load interpro-scan data file
    String xml_file=iprscanNuclFileXml.getAbsolutePath();
    IprXmlReader reader = new IprXmlReader();
    Map<String, FeatureTable> predictions= reader.readFile(xml_file, false);
    assertTrue(predictions.isEmpty()==false);
    assertEquals(predictions.size(), 6);
    
    @SuppressWarnings("serial")
    HashMap<String, List<DomainReference>> 
    domains = new HashMap<String, List<DomainReference>>() {{
      put("S65207.1", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(7,169, new ArrayList<String>(Arrays.asList("PF07714",
                "IPR001245",
                "GO:0004672",
                "GO:0006468"))));
            add(new DomainReference(1,173, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("BC047865.1", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(514,811, new ArrayList<String>(Arrays.asList("PF00780",
                "IPR001180"))));
            add(new DomainReference(46,302, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(504,815, new ArrayList<String>(Arrays.asList("PS50219",
                "IPR001180"))));
            add(new DomainReference(46,303, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("U18310.1", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(111,374, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(109,374, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("AJ292557.1", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(220,493, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(219,493, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("AF014401.1", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(121,380, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(120,380, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
      put("L42810.1", 
          new ArrayList<DomainReference>() {{
            add(new DomainReference(163,444, new ArrayList<String>(Arrays.asList("PF00069",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
            add(new DomainReference(163,444, new ArrayList<String>(Arrays.asList("PS50011",
                "IPR000719",
                "GO:0006468",
                "GO:0004672",
                "GO:0005524"))));
          }}
      );
    }};
    
    List<String> typesRef = predictions.keySet().stream().collect(Collectors.toList());
    ArrayList<String> regionIds = new ArrayList<>();
    predictions.forEach((k,v) -> regionIds.add(k));
    assertTrue(regionIds.containsAll(typesRef));

    for (String key : typesRef) {
      FeatureTable ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
      Enumeration<Feature> enums = predictions.get(key).enumFeatures();
      while(enums.hasMoreElements()) {
        Feature feat = enums.nextElement();
        if (feat.getKey().equals(IprPrediction.DOMAIN)) {
          ft.addFeature(feat);
        }
      }
      assertEquals(domains.get(key).size(), ft.features());
      testFeature(ft, domains.get(key));
    }
  }
  @Test
  public void testNuclIprScanXmlLoader_moreData() {
    //Load interpro-scan data file
    String xml_file=iprscanNuclFileXml2.getAbsolutePath();
    IprXmlReader reader = new IprXmlReader();
    Map<String, FeatureTable> predictions= reader.readFile(xml_file, false);
    assertTrue(predictions.isEmpty()==false);
    assertEquals(predictions.size(), 1);
    
    @SuppressWarnings("serial")
    HashMap<String, List<DomainReference>> 
    domains = new HashMap<String, List<DomainReference>>() {{
      put("GAAA01000070.1", 
          //TIGR, PRINTS and SUPERFAMILY IDs there as a reminder BUT they are 
          // commented out because Core API does not handle them; see 
          // testFeature(), call to ExtractAnnotation.getClassificationdata() method
          new ArrayList<DomainReference>() {{
            add(new DomainReference(75,552, new ArrayList<String>(Arrays.asList(/*"PR00301",*/
                "IPR013126"))));
            add(new DomainReference(76,673, new ArrayList<String>(Arrays.asList(/*"TIGR02350",*/
                "IPR012725",
                "GO:0006457",
                "GO:0005524",
                "GO:0051082"))));
            add(new DomainReference(76,673, new ArrayList<String>(Arrays.asList("PF00012",
                "IPR013126"))));
            add(new DomainReference(451,607, new ArrayList<String>(Arrays.asList(/*"SSF100920",*/
                "IPR029047"))));
            add(new DomainReference(76,256, new ArrayList<String>(/*Arrays.asList("SSF100920")*/)));
            add(new DomainReference(258,449, new ArrayList<String>(/*Arrays.asList("SSF100920"*/)));
          }}
      );
    }};
    
    List<String> typesRef = predictions.keySet().stream().collect(Collectors.toList());
    ArrayList<String> regionIds = new ArrayList<>();
    predictions.forEach((k,v) -> regionIds.add(k));
    assertTrue(regionIds.containsAll(typesRef));
    
    for (String key : typesRef) {
      FeatureTable ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
      Enumeration<Feature> enums = predictions.get(key).enumFeatures();
      while(enums.hasMoreElements()) {
        Feature feat = enums.nextElement();
        if (feat.getKey().equals(IprPrediction.DOMAIN)) {
          ft.addFeature(feat);
        }
      }
      assertEquals(domains.get(key).size(), ft.features());
      testFeature(ft, domains.get(key));
    }
  }
  
  private class DomainReference {
    int start;
    int end;
    List<String> domainIds;
    
    public DomainReference(int start, int end, List<String> domainIds) {
      super();
      this.start = start;
      this.end = end;
      this.domainIds = domainIds;
    }
    
  }
  
  @Test
  public void testEggNogReader_1() {
    EggNogTsvReader gr = new EggNogTsvReader();
    Map<String, List<EggNogObject>> gffMap = gr.processFileToMap(eggnogFile.getAbsolutePath());
    assertNotNull(gffMap);
    assertEquals(4, gffMap.size());
    assertTrue(gffMap.containsKey("prot_Ecto-sp6_F_contig18.5203.1"));
    assertTrue(gffMap.containsKey("prot_Ecto-sp6_F_contig18.5280.1"));
    
    ArrayList<String> types = new ArrayList<>();
    gffMap.forEach((k,v) -> types.addAll(v.stream().map(EggNogObject::getQuery_name).collect(Collectors.toList())));
    List<String> typesRef = Arrays.asList("prot_Ecto-sp6_F_contig18.5203.1","prot_Ecto-sp6_F_contig18.5280.1");
    assertTrue(types.containsAll(typesRef));
  }

  @Test
  public void testEggNogReader_2() {
    EggNogTsvReader gr = new EggNogTsvReader();
    Map<String, FeatureTable> ftMap = gr.readFile(eggnogFile.getAbsolutePath());
    assertNotNull(ftMap);
    assertEquals(4, ftMap.size());
    assertTrue(ftMap.containsKey("prot_Ecto-sp6_F_contig18.5203.1"));
    assertTrue(ftMap.containsKey("prot_Ecto-sp6_F_contig18.5280.1"));
    
    FeatureTable ft = ftMap.get("prot_Ecto-sp6_F_contig18.5203.1");
    assertEquals(ft.features(),1);
    assertEquals(ft.enumFeatures().nextElement().qualifiers(),11);

    ft = ftMap.get("prot_Ecto-sp6_F_contig18.5280.1");
    assertEquals(ft.features(),1);
    assertEquals(ft.enumFeatures().nextElement().qualifiers(),9);

    ft = ftMap.get("prot_Ecto-sp6_F_contig34.10231.1");
    assertEquals(ft.features(),1);
    assertEquals(ft.enumFeatures().nextElement().qualifiers(),55);

    ft = ftMap.get("prot_Ecto-sp6_F_contig136.3266.1");
    assertEquals(ft.features(),1);
    assertEquals(ft.enumFeatures().nextElement().qualifiers(),248);

  }

}
