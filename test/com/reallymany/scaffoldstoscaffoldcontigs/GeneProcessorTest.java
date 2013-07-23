package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GeneProcessorTest {
	GeneProcessor testGP1, testGP2;
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
		assertTrue(testScaffolds.get(0).getScaffoldContigs().get(0) instanceof ScaffoldContig);
		
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
		
		// Create a GeneProcessor
		testGP1 = new GeneProcessor(testGene1, testScaffolds);
		
		// Do it again for a Gene which doesn't span multiple contigs
		testFeatures.clear();
		testStringArray = "scaffold00002	maker	gene	23220	29000	.	+	.	ID=1;Name=BDOR_007864".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00002	maker	mRNA	23220	29000	.	+	.	ID=2;Name=BDOR_007864-RA;Parent=1".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00002	maker	exon	24000	27000	.	+	.	ID=3;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testStringArray = "scaffold00002	maker	CDS	24000	27000	.	+	.	ID=4;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testGene2 = new Gene(testFeatures);
		testGP2 = new GeneProcessor(testGene2, testScaffolds);
	}
	
	
	@Test
	public void testGeneProcessor() throws Exception {
		setUp();
		assertTrue(testGP1 instanceof GeneProcessor);
		assertEquals(testScaffolds, testGP1.allScaffolds);
		assertEquals(testGene1, testGP1.geneBeingProcessed);
		assertTrue(testGP1.genesToWrite instanceof ArrayList);
		assertEquals(testScaffolds.get(0), testGP1.currentScaffold);
		
		assertTrue(testGP2.geneBeingProcessed instanceof Gene);
		assertEquals(testGene2, testGP2.geneBeingProcessed);
		assertEquals(testScaffolds.get(1), testGP2.currentScaffold);
	}
	
	@Test
	public void testFeatureSpansMultipleContigs() throws Exception {
		setUp();
		testStringArray = "scaffold00002	maker	exon	28000	33000	.	+	.	ID=3;Name=BDOR_007864-RA;Parent=2".split("\t");
		assertTrue(testGP2.featureSpansMultipleContigs(testStringArray));
		testStringArray = "scaffold00002	maker	exon	6400	13000	.	+	.	ID=3;Name=BDOR_007864-RA;Parent=2".split("\t");
		assertFalse(testGP1.featureSpansMultipleContigs(testStringArray));
		
	}
	
	@Test
	public void testGeneSpansMultipleContigs() throws Exception {
		setUp();
		assertTrue(testGP1.geneSpansMultipleContigs());
		assertFalse(testGP2.geneSpansMultipleContigs());
	}
	
	
	
	

}


/*
testGene1 = new Gene();
testChildren = new ArrayList<String[]>();
testStringArray = "scaffold00080	maker	gene	106151	109853	.	+	.	ID=1;Name=BDOR_007864".split("\t");
testChildren.add(testStringArray);
*/