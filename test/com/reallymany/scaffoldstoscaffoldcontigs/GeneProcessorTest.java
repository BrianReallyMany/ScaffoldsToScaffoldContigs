package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GeneProcessorTest {
	GeneProcessor testGP;
	AGPReader testSR;	
	Scaffold testScaffold;
	ScaffoldContig testScaffoldContig;
	ArrayList<Scaffold> testScaffolds;
	ArrayList<String[]> testFeatures;
	String[] testStringArray;
	Gene testGene1, testGene2;
		
	
	@Before
	public void setUp() throws Exception {
		testSR = new AGPReader();
		testScaffolds = testSR.readScaffoldFile("sample_files/sample.agp");
		// Make sure the AGPReader is cooperating:
		assertTrue(testScaffolds.size() == 2);
		assertTrue(testScaffolds.get(0) instanceof Scaffold);
		assertEquals("scaffold00001", testScaffolds.get(0).getName());
		assertTrue(testScaffolds.get(0).getScaffoldContigs().get(0) instanceof ScaffoldContig);
		
		// Create a GeneProcessor
		testGP = new GeneProcessor(testScaffolds);
		
		// Initialize a Gene to process (spans multiple contigs)
		testFeatures = new ArrayList<String[]>();
		testStringArray = "scaffold00001	maker	gene	5	13000	.	+	.	ID=1;Name=BDOR_007864".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00001	maker	mRNA	5	13000	.	+	.	ID=2;Name=BDOR_007864-RA;Parent=1".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00001	maker	exon	5	4000	.	+	.	ID=3;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00001	maker	exon	6500	12000	.	+	.	ID=4;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testGene1 = new Gene(testFeatures);
			
//		 Do it again for a Gene which doesn't span multiple contigs
		testFeatures = new ArrayList<String[]>();
		testStringArray = "scaffold00002	maker	gene	23220	29000	.	+	.	ID=1;Name=BDOR_007864".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00002	maker	mRNA	23220	29000	.	+	.	ID=2;Name=BDOR_007864-RA;Parent=1".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00002	maker	exon	24000	27000	.	+	.	ID=3;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00002	maker	CDS	24000	27000	.	+	.	ID=4;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testGene2 = new Gene(testFeatures);
	}
	
	
	
	@Test
	public void testGeneProcessor() throws Exception {
		setUp();
		assertTrue(testGP instanceof GeneProcessor);
		assertEquals(testScaffolds, testGP.allScaffolds);
	}	
	
	@Test
	public void testFindScaffold() throws Exception {
		setUp();
		assertTrue(testGP.findScaffold(testGene1) instanceof Scaffold);
		assertEquals(testScaffolds.get(0).getName(), testGP.findScaffold(testGene1).getName());
		assertEquals(testScaffolds.get(1).getName(), testGP.findScaffold(testGene2).getName());
	}
	
	@Test
	public void testSpansMultipleContigs() throws Exception {
		setUp();
		testScaffold = testGP.findScaffold(testGene1);
		assertTrue(testGP.spansMultipleContigs(testGene1, testScaffold));
		testScaffold = testGP.findScaffold(testGene2);
		assertFalse(testGP.spansMultipleContigs(testGene2, testScaffold));
	}
	
	@Test
	public void testFindGeneStartingIndex() throws Exception {
		setUp();
		int start = testGP.findGeneStartingIndex(testGene1);
		assertEquals(5, start);
		assertEquals(23220, testGP.findGeneStartingIndex(testGene2));
	}
	
	@Test
	public void testFindGeneEndingIndex() throws Exception {
		setUp();
		assertEquals(13000, testGP.findGeneEndingIndex(testGene1));
		assertEquals(29000, testGP.findGeneEndingIndex(testGene2));
	}
	
	@Test
	public void testFindFeatureStartingIndex() throws Exception {
		setUp();
		String[] feature1 = testGene1.getFeatures().get(3);
		String[] feature2 = testGene2.getFeatures().get(2);
		assertEquals(6500, testGP.findFeatureStartingIndex(feature1));
		assertEquals(24000, testGP.findFeatureStartingIndex(feature2));
	}
	
	@Test
	public void testFindFeatureEndingIndex() throws Exception {
		setUp();
		String[] feature1 = testGene1.getFeatures().get(2);
		String[] feature2 = testGene2.getFeatures().get(1);
		assertEquals(4000, testGP.findFeatureEndingIndex(feature1));
		assertEquals(29000, testGP.findFeatureEndingIndex(feature2));
	}	
	
	@Test
	public void testScaffoldToScaffoldContig() throws Exception {
		setUp();
		testScaffoldContig = testScaffolds.get(1).getScaffoldContigs().get(1);
		testGene2 = testGP.scaffoldToScaffoldContig(testGene2, testScaffoldContig);
		assertEquals("sctg_0002_0002", testGene2.getFeatures().get(0)[0]);
		assertEquals("791", testGene2.getFeatures().get(2)[3]);
	}
	
	@Test
	public void testRecalculateIndices() throws Exception {
		setUp();
		testScaffoldContig = testScaffolds.get(1).getScaffoldContigs().get(1);
		testGP.recalculateIndices(testGene2.getFeatures().get(0), testScaffoldContig);
		assertEquals("11", testGene2.getFeatures().get(0)[3]);
	}
	
	@Test
	public void testPrepareGeneForWriting() throws Exception {
		setUp();
		ArrayList<Gene> splitUpGenes = testGP.prepareGeneForWriting(testGene1);
		assertTrue(splitUpGenes instanceof ArrayList);
		assertEquals("ID=1.1;Name=BDOR_007864.1", splitUpGenes.get(0).getFeatures().get(0)[8]);
		assertEquals("ID=1.2;Name=BDOR_007864.2", splitUpGenes.get(1).getFeatures().get(0)[8]);
		assertEquals("ID=2.2;Name=BDOR_007864-RA.2;Parent=1.2", splitUpGenes.get(1).getFeatures().get(1)[8]);
		assertEquals(3, splitUpGenes.get(0).getFeatures().size());
		assertEquals(3, splitUpGenes.get(1).getFeatures().size());
		assertEquals("gene", splitUpGenes.get(0).getFeatures().get(0)[2]);
		assertEquals("gene", splitUpGenes.get(1).getFeatures().get(0)[2]);
		assertEquals("5", splitUpGenes.get(0).getFeatures().get(0)[3]);
		assertEquals("183", splitUpGenes.get(1).getFeatures().get(2)[3]);
		assertEquals("5683", splitUpGenes.get(1).getFeatures().get(2)[4]);
		assertEquals("exon", splitUpGenes.get(0).getFeatures().get(2)[2]);
		assertEquals("exon", splitUpGenes.get(1).getFeatures().get(2)[2]);
	}	
	
//	@Test
//	public void testAdjustIndices() throws Exception {
//		setUp();
//		testScaffoldContig = testScaffolds.get(1).getScaffoldContigs().get(1);
//		testGP.adjustIndices(testGene2, testScaffoldContig);
//		assertEquals("11", testGene2.getFeatures().get(0)[3]);
//		assertEquals("791", testGene2.getFeatures().get(3)[3]);
//		assertEquals("5791", testGene2.getFeatures().get(0)[4]);
//		assertEquals("3791", testGene2.getFeatures().get(2)[4]);
//	}
	
	@Test
	public void testGeneEndsOnThisSctg() throws Exception {
		setUp();
		assertTrue(testGP.geneEndsOnThisSctg(testGene1, testScaffolds.get(0).getScaffoldContigs().get(1)));
		assertFalse(testGP.geneEndsOnThisSctg(testGene1, testScaffolds.get(0).getScaffoldContigs().get(0)));
	}
	
	@Test
	public void testAppendSubtype() throws Exception {
		String input = "ID=1;Name=BDOR_007864";
		String expectedOutput = "ID=1.1;Name=BDOR_007864.1";
		assertEquals(expectedOutput, testGP.appendSubtype(1, input));
	}
}


// TODO seems like features are not getting deleted
// TODO 'gene' feature not making it to output guys.