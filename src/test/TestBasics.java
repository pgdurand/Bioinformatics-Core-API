/* Copyright (C) 2006-2021 Patrick G. Durand
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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.io.searchresult.SerializerSystemFactory;

public class TestBasics {
	private static File            blastFile;
	private static SRLoader        ncbiBlastLoader2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// init logger system
		BasicConfigurator.configure();
		//switch off debug messages from Castor framework
		Logger.getLogger("org.exolab.castor").setLevel(Level.WARN);
		// init API factories
		CoreSystemConfigurator.initializeSystem();
		// sample NCBI legacy Blast result
		blastFile = new File("data/test/UM80ZU99013_s.xml");
    // setup an NCBI Blast Loader (XML)
    ncbiBlastLoader2 = SerializerSystemFactory.getLoaderInstance(SerializerSystemFactory.NCBI_LOADER2);
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
	public void testRead() {
		// read NCBI XML 2 blast file
		SROutput bo = ncbiBlastLoader2.load(blastFile);
		assertNotNull(bo);
		assertTrue(bo.getIteration(0).countHit()==1);
	}
}
