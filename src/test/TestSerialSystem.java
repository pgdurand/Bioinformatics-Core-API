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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SRHit;
import bzh.plealog.bioinfo.api.data.searchresult.SRIteration;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRWriter;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;

public class TestSerialSystem {
	private static File            blastFile;
	private static File            tmpFile;
	private static SRLoader         ncbiBlastLoader;
	private static SRWriter         ncbiBlastWriter;
	private static SRWriter         nativeBlastWriter;
	private static SRLoader         nativeBlastLoader;
	private static HashSet<String> hitIDs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// init logger system
		BasicConfigurator.configure();
		// init API factories
		CoreSystemConfigurator.initializeSystem();
		// sample NCBI legacy Blast result
		blastFile = new File("data/test/blastp.xml");
		// setup a temp file (will be deleted in tearDownAfterClass())
		tmpFile = File.createTempFile("blastTest", ".xml");
		System.out.println("Temp file is: "+ tmpFile.getAbsolutePath());
		// setup an NCBI Blast Loader (XML)
		ncbiBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER);
		// setup an NCBI Blast Writer (XML)
		ncbiBlastWriter = SerializerSystemFactory.getWriterInstance(SerializerSystemFactory.NCBI_WRITER);
		// setup a native BOutput writer
		nativeBlastWriter = SerializerSystemFactory.getWriterInstance(SerializerSystemFactory.NATIVE_WRITER);
		// setup a native BOutput writer
		nativeBlastLoader = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NATIVE_LOADER);
		// control values
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
	public void testRead() {
		// read NCBI XML blast file
		SROutput bo = ncbiBlastLoader.load(blastFile);
		assertNotNull(bo);
		assertTrue(bo.getIteration(0).countHit()==19);
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
}
