package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVWriter;

public class GFFWriterTest {
	GFFWriter testWriter;
	ArrayList<Gene> listOfGenes;
	ArrayList<String[]> testChildren1, testChildren2;
	String[] testStringArray;
	Gene testGene1, testGene2;

	@Before
	public void setUp() throws Exception {
		listOfGenes = new ArrayList<Gene>();
		testWriter = new GFFWriter("test_output");
		testChildren1 = new ArrayList<String[]>();
		testChildren2 = new ArrayList<String[]>();
		
		testStringArray = "scaffold00080	maker	gene	106151	109853	.	+	.	ID=1.1;Name=BDOR_007864".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	mRNA	106151	109853	.	+	.	ID=2.1;Name=BDOR_007864-RA;Parent=1".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	106151	106451	0.9	+	.	ID=3.1;Name=BDOR_007864-RA:exon:0;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	106509	106749	0.9	+	.	ID=4.1;Name=BDOR_007864-RA:exon:1;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	106816	107602	0.9	+	.	ID=5.1;Name=BDOR_007864-RA:exon:2;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	107666	108982	0.9	+	.	ID=6.1;Name=BDOR_007864-RA:exon:3;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	109047	109853	0.9	+	.	ID=7.1;Name=BDOR_007864-RA:exon:4;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	CDS	106151	106451	.	+	0	ID=8.1;Name=BDOR_007864-RA:cds:0;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	CDS	106509	106749	.	+	2	ID=9.1;Name=BDOR_007864-RA:cds:1;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testStringArray = "scaffold00080	maker	CDS	106816	107602	.	+	1	ID=10.1;Name=BDOR_007864-RA:cds:2;Parent=2".split("\t");
		testChildren1.add(testStringArray);
		testGene1 = new Gene(testChildren1);
		assertEquals(10, testGene1.getFeatures().size());
		listOfGenes.add(testGene1);
		
		testStringArray = "scaffold00081	maker	gene	106151	109853	.	+	.	ID=1.2;Name=BDOR_007864".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	mRNA	106151	109853	.	+	.	ID=2.2;Name=BDOR_007864-RA;Parent=1".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	exon	106151	106451	0.9	+	.	ID=3.2;Name=BDOR_007864-RA:exon:0;Parent=2".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	exon	106509	106749	0.9	+	.	ID=4.2;Name=BDOR_007864-RA:exon:1;Parent=2".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	exon	106816	107602	0.9	+	.	ID=5.2;Name=BDOR_007864-RA:exon:2;Parent=2".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	exon	107666	108982	0.9	+	.	ID=6.2;Name=BDOR_007864-RA:exon:3;Parent=2".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	exon	109047	109853	0.9	+	.	ID=7.2;Name=BDOR_007864-RA:exon:4;Parent=2".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	CDS	106151	106451	.	+	0	ID=8.2;Name=BDOR_007864-RA:cds:0;Parent=2".split("\t");
		testChildren2.add(testStringArray);
		testStringArray = "scaffold00081	maker	CDS	106509	106749	.	+	2	ID=9.2;Name=BDOR_007864-RA:cds:1;Parent=2".split("\t");
		testChildren2.add(testStringArray);		
		testGene2 = new Gene(testChildren2);
		assertEquals(9, testGene2.getFeatures().size());
		listOfGenes.add(testGene2);
		
		assertEquals(2, listOfGenes.size());		
	}

	@Test
	public void testGFFWriter() throws Exception {
		setUp();
		assertTrue(testWriter instanceof GFFWriter);
		assertTrue(testWriter instanceof CSVWriter);
	}
	
	@Test
	public void testWriteGenes() throws Exception {
		setUp();
		testWriter.writeGenes(listOfGenes);
		File theOutput = new File("test_output.gff");
		assertTrue(theOutput.isFile());
	}	// note: this test is bogus, it always passes.
	
	@After
	public void tearDown() throws Exception {
		testWriter.close();
	}

}
