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
	Gene testGene1;
		
	
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
		testStringArray = "scaffold00001	maker	exon	5	13000	.	+	.	ID=4;Name=BDOR_007864-RA;Parent=2".split("\t");
		testFeatures.add(testStringArray);
		testGene1 = new Gene(testFeatures);
		
		// Create a GeneProcessor
		testGP = new GeneProcessor(testGene1, testScaffolds);
	}

	@Test
	public void testGeneProcessor() throws Exception {
		setUp();
		assertTrue(testGP instanceof GeneProcessor);
		assertEquals(testScaffolds, testGP.allScaffolds);
		assertEquals(testGene1, testGP.geneBeingProcessed);
		assertTrue(testGP.genesToWrite instanceof ArrayList);
	}

}


/*
testGene1 = new Gene();
testChildren = new ArrayList<String[]>();
testStringArray = "scaffold00080	maker	gene	106151	109853	.	+	.	ID=1;Name=BDOR_007864".split("\t");
testChildren.add(testStringArray);
*/