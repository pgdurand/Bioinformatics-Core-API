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
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.SRRequestInfo;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.data.taxonomy.core.TaxonomyLoader;
import bzh.plealog.bioinfo.data.taxonomy.core.TaxonomyTerm;
import bzh.plealog.bioinfo.data.taxonomy.loader.ncbi.TaxaSet;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;
import bzh.plealog.bioinfo.io.xml.XmlUtils;

public class TestSerialSystem {
	private static File            blastFile;
  private static File            blastFile2;
  private static File            blastFile3;
  private static File            taxFile;
	private static File            tmpFile;
  private static SRLoader        ncbiBlastLoader;
  private static SRLoader        ncbiBlastLoader2;
	private static SRWriter        ncbiBlastWriter;
	private static SRWriter        nativeBlastWriter;
	private static SRLoader        nativeBlastLoader;
	private static HashSet<String> hitIDs;
  private static String[]        hitIDsXML2;
  private static String[]        hitIDsPsiXML2;
	private static int[]           hitIDsPsiXML2idx;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// init logger system
		BasicConfigurator.configure();
		//switch off debug messages from Castor framework
		Logger.getLogger("org.exolab.castor").setLevel(Level.WARN);
		// init API factories
		CoreSystemConfigurator.initializeSystem();
		// sample NCBI legacy Blast result
		blastFile = new File("data/test/blastp.xml");
    // sample NCBI Blast result XML2 single file
    blastFile2 = new File("data/test/blastp.xml2");
    // sample NCBI Blast result XML2 single file
    blastFile3 = new File("data/test/psi-blast-sw.xml2");
    // sample eFetch/Taxonomy data file
    taxFile = new File("data/test/ncbi-taxonomy-efetch.xml");
		// setup a temp file (will be deleted in tearDownAfterClass())
    tmpFile = File.createTempFile("blastTest", ".xml");
		//dump tmp file to help debugging
		System.out.println("Temp file is: "+ tmpFile.getAbsolutePath());
		// setup an NCBI Blast Loader (XML)
		ncbiBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER);
    // setup an NCBI Blast Loader (XML2 single file)
    ncbiBlastLoader2 = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER2);
		// setup an NCBI Blast Writer (XML)
		ncbiBlastWriter = SerializerSystemFactory.getWriterInstance(SerializerSystemFactory.NCBI_WRITER);
		// setup a native BOutput writer
		nativeBlastWriter = SerializerSystemFactory.getWriterInstance(SerializerSystemFactory.NATIVE_WRITER);
		// setup a native BOutput writer
		nativeBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NATIVE_LOADER);
		// control values to check blastFile
		hitIDs = new HashSet<String>();
		hitIDs.add("gi|11514550|pdb|1FQY|A");
		hitIDs.add("gi|20150315|pdb|1J4N|A");
		hitIDs.add("gi|83754265|pdb|2B6O|A");
		hitIDs.add("gi|61680729|pdb|1YMG|A");
		hitIDs.add("gi|49259096|pdb|1SOR|A");
		hitIDs.add("gi|88192744|pdb|2D57|A");
		hitIDs.add("gi|85544225|pdb|2B5F|A");
		hitIDs.add("gi|85544014|pdb|1Z98|A");
		hitIDs.add("gi|134105082|pdb|2O9D|A");
		hitIDs.add("gi|134105084|pdb|2O9E|A");
		hitIDs.add("gi|39654847|pdb|1RC2|B");
		hitIDs.add("gi|134105085|pdb|2O9F|A");
		hitIDs.add("gi|83754991|pdb|2EVU|A");
		hitIDs.add("gi|21466057|pdb|1LDF|A");
		hitIDs.add("gi|11514194|pdb|1FX8|A");
		hitIDs.add("gi|82407721|pdb|2A9M|L");
		hitIDs.add("gi|56967161|pdb|1XMX|A");
		hitIDs.add("gi|42543068|pdb|1NL0|L");
		hitIDs.add("gi|13399662|pdb|1EVY|A");
		// control values to check blastFile2
		hitIDsXML2 = new String[]{"1BHG_A","3K46_A","3K4A_A","5C71_A","4JHZ_A",
		    "3LPF_A","5C70_A","6D4O_A","6BJQ_A","6BJW_A"};
		// control values to check blastFile3
		hitIDsPsiXML2idx = new int[] {1,14,148,180};
    hitIDsPsiXML2 = new String[]{"gi|6754098|ref|NP_034498.1|","sp|Q47077.1|","sp|Q9SCU9.1|","sp|B2B3C0.1|"};
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		tmpFile.delete();
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCanRead() {
		assertTrue(ncbiBlastLoader.canRead(blastFile));
	}

	@Test
  public void testCanRead2() {
    assertTrue(ncbiBlastLoader2.canRead(blastFile2));
  }

	@Test
  public void testCanReadPsi() {
    assertTrue(ncbiBlastLoader2.canRead(blastFile3));
  }

	@Test
	public void testRead() {
		// read NCBI XML blast file
		SROutput bo = ncbiBlastLoader.load(blastFile);
		assertNotNull(bo);
		assertTrue(bo.getIteration(0).countHit()==19);
	}
  @Test
  public void testReadPsi() {
    // read NCBI XML2 psi-blast file
    SROutput bo = ncbiBlastLoader2.load(blastFile3);
    
    assertNotNull(bo);
    assertTrue(bo.countIteration()==1);
    assertTrue(bo.getIteration(0).countHit()==180);
    for(int i=0;i<hitIDsPsiXML2idx.length;i++) {
      assertEquals(bo.getIteration(0).getHit(hitIDsPsiXML2idx[i]-1).getHitId(), hitIDsPsiXML2[i]);
      assertEquals(bo.getIteration(0).getHit(hitIDsPsiXML2idx[i]-1).getHitNum(), hitIDsPsiXML2idx[i]);
    }
    
    int idx = hitIDsPsiXML2idx[0]-1;
    assertEquals(bo.getIteration(0).getHit(idx).getSequenceInfo().getOrganism(),"Mus musculus");
    assertEquals(bo.getIteration(0).getHit(idx).getSequenceInfo().getTaxonomy(),"10090");
    idx = hitIDsPsiXML2idx[1]-1;
    assertEquals(bo.getIteration(0).getHit(idx).getSequenceInfo().getOrganism(),"Enterobacter cloacae");
    assertEquals(bo.getIteration(0).getHit(idx).getSequenceInfo().getTaxonomy(),"550");
    
    idx = hitIDsPsiXML2idx[2]-1;
    assertEquals(bo.getIteration(0).getHit(idx).getHitLen(),848);
    assertEquals(bo.getIteration(0).getHit(idx).getHitNum(), hitIDsPsiXML2idx[2]);
    assertEquals(bo.getIteration(0).getHit(idx).getHitId(), "sp|Q9SCU9.1|");
    assertEquals(bo.getIteration(0).getHit(idx).getHitAccession(), "Q9SCU9");
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getQuery().getFrom(),98);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getQuery().getTo(),146);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getHit().getFrom(),488);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getHit().getTo(),537);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getScores().getAlignLen(),50);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getScores().getScore(),86.0, 0);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getScores().getBitScore(),37.7196d, 0);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getScores().getIdentity(),12);
    assertEquals(bo.getIteration(0).getHit(idx).getHsp(0).getScores().getPositive(),20);
    assertEquals(bo.getIteration(0).getHit(idx).getSequenceInfo().getOrganism(),"Arabidopsis thaliana");
    assertEquals(bo.getIteration(0).getHit(idx).getSequenceInfo().getTaxonomy(),"3702");
  }
  
  @Test
  public void testRead2() {
    // read NCBI XML2 blast file
    SROutput bo = ncbiBlastLoader2.load(blastFile2);
    
    assertNotNull(bo);
    
    assertEquals(bo.getRequestInfo().getValue(SRRequestInfo.PROGRAM_DESCRIPTOR_KEY),"blastp");
    assertEquals(bo.getRequestInfo().getValue(SRRequestInfo.PRGM_VERSION_DESCRIPTOR_KEY),"BLASTP 2.10.0+");
    assertEquals(bo.getRequestInfo().getValue(SRRequestInfo.DATABASE_DESCRIPTOR_KEY),"pdb_v5");
    
    assertTrue(bo.countIteration()==2);
    assertTrue(bo.getIteration(0).getIterationIterNum()==1);
    assertTrue(bo.getIteration(1).getIterationIterNum()==2);
    assertTrue(bo.getIteration(0).countHit()==10);
    assertTrue(bo.getIteration(1).countHit()==9);
    
    assertEquals(bo.getIteration(0).getIterationQueryID(), "Query_175978");
    assertEquals(bo.getIteration(0).getIterationQueryDesc(), "seq1");
    assertEquals(bo.getIteration(0).getIterationQueryLength(), 118);

    assertEquals(bo.getIteration(1).getIterationQueryID(), "Query_175979");
    assertEquals(bo.getIteration(1).getIterationQueryDesc(), "seq2");
    assertEquals(bo.getIteration(1).getIterationQueryLength(), 129);

    for(int i=0;i<10;i++) {
      assertEquals(bo.getIteration(0).getHit(i).getHitAccession(), hitIDsXML2[i]);
      assertEquals(bo.getIteration(0).getHit(i).getHitNum(), i+1);
    }
    
    assertEquals(bo.getIteration(1).getHit(8).getHitLen(),614);
    assertEquals(bo.getIteration(1).getHit(8).getHitNum(), 9);
    assertEquals(bo.getIteration(1).getHit(8).getHitId(), "pdb|6D4O|A");
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getQuery().getFrom(),17);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getQuery().getTo(),129);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getHit().getFrom(),84);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getHit().getTo(),198);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getScores().getAlignLen(),118);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getScores().getScore(),200.0, 0);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getScores().getBitScore(),81.6481d, 0);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getScores().getIdentity(),47);
    assertEquals(bo.getIteration(1).getHit(8).getHsp(0).getScores().getPositive(),67);
    
    assertEquals(bo.getIteration(1).getHit(0).getSequenceInfo().getOrganism(),"Homo sapiens");
    assertEquals(bo.getIteration(1).getHit(0).getSequenceInfo().getTaxonomy(),"9606");
    assertEquals(bo.getIteration(1).getHit(8).getSequenceInfo().getOrganism(),"[Eubacterium] eligens");
    assertEquals(bo.getIteration(1).getHit(8).getSequenceInfo().getTaxonomy(),"39485");
  }

  private void checkContent(SROutput bo){
		// check iterations
		assertTrue(bo.countIteration()==1);
		SRIteration iter = bo.getIteration(0);
		
		// check hits
		assertTrue(iter.countHit()==19);
		Enumeration<SRHit> enumHits = iter.enumerateHit();
		SRHit              hit;
		while (enumHits.hasMoreElements()){
			hit = enumHits.nextElement();
			assertTrue(hitIDs.contains(hit.getHitId()));
		}

	}
	
  @Test
	public void testContent() {
		// read NCBI XML blast file
		SROutput bo = ncbiBlastLoader.load(blastFile);
		assertNotNull(bo);
		// check content
		checkContent(bo);
	}
	
	@Test
	public void testWriteContent(){
		// read NCBI XML blast file
		SROutput bo = ncbiBlastLoader.load(blastFile);
		assertNotNull(bo);
		assertTrue(bo.getIteration(0).countHit()==19);
		// write new File
		ncbiBlastWriter.write(tmpFile, bo);
		nativeBlastWriter.write(tmpFile, bo);
	}

	@Test
	public void testReadWriteContent(){
		// read NCBI XML blast file
		SROutput bo = ncbiBlastLoader.load(blastFile);
		assertNotNull(bo);
		assertTrue(bo.getIteration(0).countHit()==19);
		
		// write new File using native BOutput Zipped XML 
		nativeBlastWriter.write(tmpFile, bo);
		
		// reload the file
		SROutput newBO = nativeBlastLoader.load(tmpFile);
		
		//check everything is ok
		assertNotNull(newBO);
		// check content
		checkContent(newBO);
	}
	
	
	@Test
	public void testTaxonomyLoaderCanonical() {
	  TaxaSet tSet = (TaxaSet) XmlUtils.loadXmlFile(taxFile, TaxaSet.class);//JAXB.unmarshal(f, TaxaSet.class);
	  assertNotNull(tSet);
	  assertEquals(2, tSet.getTaxon().size());
    List<TaxonomyTerm> tTerms = TaxonomyLoader.loadCanonical(tSet.getTaxon().get(0));
    assertEquals(8, tTerms.size());
    assertEquals(TaxonomyLoader.getPathNames(tTerms), "Eukaryota;Metazoa;Chordata;Mammalia;Primates;Hominidae;Homo;Homo sapiens");
    assertEquals(TaxonomyLoader.getPathRanks(tTerms), "d__;k__;p__;c__;o__;f__;g__;s__");
    assertEquals(TaxonomyLoader.getPathIds(tTerms), "2759;33208;7711;40674;9443;9604;9605;9606");
	  tTerms = TaxonomyLoader.loadCanonical(tSet.getTaxon().get(1));
	  assertNotNull(tTerms);
	  assertEquals(8, tTerms.size());
    assertEquals(TaxonomyLoader.getPathNames(tTerms), "Eukaryota;Metazoa;Chordata;Mammalia;Rodentia;Muridae;Mus;Mus musculus");
    assertEquals(TaxonomyLoader.getPathRanks(tTerms), "d__;k__;p__;c__;o__;f__;g__;s__");
    assertEquals(TaxonomyLoader.getPathIds(tTerms), "2759;33208;7711;40674;9989;10066;10088;10090");
	}
  @Test
  public void testTaxonomyLoaderFull() {
    TaxaSet tSet = (TaxaSet) XmlUtils.loadXmlFile(taxFile, TaxaSet.class);//JAXB.unmarshal(f, TaxaSet.class);
    assertNotNull(tSet);
    assertEquals(2, tSet.getTaxon().size());
    List<TaxonomyTerm> tTerms = TaxonomyLoader.loadFull(tSet.getTaxon().get(0));
    assertEquals(16, tTerms.size());
    assertEquals(TaxonomyLoader.getPathNames(tTerms), "Eukaryota;Metazoa;Chordata;Craniata;Sarcopterygii;Mammalia;Euarchontoglires;Primates;Haplorrhini;Simiiformes;Catarrhini;Hominoidea;Hominidae;Homininae;Homo;Homo sapiens");
    assertEquals(TaxonomyLoader.getPathIds(tTerms), "2759;33208;7711;89593;8287;40674;314146;9443;376913;314293;9526;314295;9604;207598;9605;9606");
    tTerms = TaxonomyLoader.loadFull(tSet.getTaxon().get(1));
    assertNotNull(tTerms);
    assertEquals(14, tTerms.size());
    assertEquals(TaxonomyLoader.getPathNames(tTerms), "Eukaryota;Metazoa;Chordata;Craniata;Sarcopterygii;Mammalia;Euarchontoglires;Rodentia;Myomorpha;Muridae;Murinae;Mus;Mus;Mus musculus");
    assertEquals(TaxonomyLoader.getPathIds(tTerms), "2759;33208;7711;89593;8287;40674;314146;9989;1963758;10066;39107;10088;862507;10090");
  }

}
