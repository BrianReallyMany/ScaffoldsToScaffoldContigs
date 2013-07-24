package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GeneProcessorTest {
	GeneProcessor testGP;
	AGPReader testSR;	
	Scaffold testScaffold;
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
		
		// Initialize a Gene to process
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
	
	
	
	// TODO wtf why is it scaffold00001 when created then scaffold00002 when called?
	
	
	@Test
	public void testGeneProcessor() throws Exception {
		setUp();
		assertTrue(testGP instanceof GeneProcessor);
		assertEquals(testScaffolds, testGP.allScaffolds);
		assertTrue(testGP.genesToWrite instanceof ArrayList);		
		assertTrue(testGP.genesToWrite.isEmpty());
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
	public void testFindCurrentScaffoldContig() throws Exception {
		setUp();
		assertEquals("sctg_0002_0002", testGP.findCurrentScaffoldContig(testGene2).getName());
	}
	
}