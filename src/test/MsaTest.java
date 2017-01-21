package test;

import java.io.File;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.searchresult.SROutput;
import bzh.plealog.bioinfo.api.data.searchresult.io.SRLoader;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.io.searchresult.ncbi.BlastLoader;

public class MsaTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // requires to use DAlphabet / DSequence factories
    CoreSystemConfigurator.initializeSystem();
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
  public void testBasicMsaBuild() {
    
    SRLoader loader = new BlastLoader();
    
    SROutput output = loader.load(new File("./data/test/blastp.xml"));

    DSequenceAlignment msa = (output.getIteration(0)).getMultipleSequenceAlignment(269, SROutput.BLASTP);
    
    assertTrue(msa.getType()==DSequenceAlignment.STANDARD_ALIGNMENT);
    assertTrue(msa.rows()==20);
    assertTrue(msa.columns()==269);
  }

}
