package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GeneTest {
	Gene testGene1, testGene2, testGene3;
	ArrayList<String[]> testChildren;
	String[] testStringArray = new String[9];
	Scaffold testScaffold;
	ScaffoldContig testSctg;

	@Before
	public void setUp() {
		testGene1 = new Gene();
		testChildren = new ArrayList<String[]>();
		testStringArray = "scaffold00080	maker	gene	106151	109853	.	+	.	ID=1;Name=BDOR_007864".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	mRNA	106151	109853	.	+	.	ID=2;Name=BDOR_007864-RA;Parent=1".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	106151	106451	0.9	+	.	ID=3;Name=BDOR_007864-RA:exon:0;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	106509	106749	0.9	+	.	ID=4;Name=BDOR_007864-RA:exon:1;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	106816	107602	0.9	+	.	ID=5;Name=BDOR_007864-RA:exon:2;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	107666	108982	0.9	+	.	ID=6;Name=BDOR_007864-RA:exon:3;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	exon	109047	109853	0.9	+	.	ID=7;Name=BDOR_007864-RA:exon:4;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	CDS	106151	106451	.	+	0	ID=8;Name=BDOR_007864-RA:cds:0;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	CDS	106509	106749	.	+	2	ID=9;Name=BDOR_007864-RA:cds:1;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testStringArray = "scaffold00080	maker	CDS	106816	107602	.	+	1	ID=10;Name=BDOR_007864-RA:cds:2;Parent=2".split("\t");
		testChildren.add(testStringArray);
		testGene2 = new Gene(testChildren);
		
		testScaffold = new Scaffold("scaffold00001");
		testSctg = new ScaffoldContig("sctg_0001_0001", 1, 4968);
	}

	@Test
	public void testGene() {
		setUp();
		assertTrue(testGene1 instanceof Gene);
		assertTrue(testGene1.getFeatures() instanceof ArrayList);
		
		assertTrue(testGene2 instanceof Gene);
		assertTrue(testGene2.features instanceof ArrayList);
		assertEquals("106151", testGene2.getFeatures().get(7)[3]);
	}
	
	@Test
	public void testGeneGettersAndSetters() throws ScaffoldContigException {
		setUp();
		try {
			testGene1.getEnclosingScaffold();
			fail("Didn't throw exception!");
		} catch (ScaffoldContigException e) {}
		try {
			testGene2.getEnclosingScaffoldContig();
			fail("Didn't throw excpetion!");
		} catch (ScaffoldContigException e) {}
		
		testGene1.setEnclosingScaffold(testScaffold);
		assertEquals(testScaffold, testGene1.getEnclosingScaffold());
		
		testGene2.setEnclosingScaffoldContig(testSctg);
		assertEquals(testSctg, testGene2.getEnclosingScaffoldContig());
	}
	
	@Test
	public void testAddFeature() {
		setUp();
		assertEquals(10, testGene2.getFeatures().size());
		
		testStringArray = "scaffold00080	maker	foo	106816	107602	.	+	1	ID=10;Name=BDOR_007864-RA:cds:2;Parent=2".split("\t");
		testGene2.addFeature(testStringArray);
		assertEquals(11, testGene2.getFeatures().size());
		assertEquals("foo", testGene2.getFeatures().get(10)[2]);
	}
	
}