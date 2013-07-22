package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class GFFReaderTest {
	GFFReader testGFFReader1, testGFFReader2, testGFFReader3;
	ArrayList<String[]> oneGene, twoGene, scaff, scaff2, scaff3;
	Gene testGene1, testGene2;

	@Before
	public void setUp() throws FileNotFoundException {
		testGFFReader1 = new GFFReader("sample_files/sample.gff");
		testGFFReader2 = new GFFReader("sample_files/two_scaffolds.gff");
		testGFFReader3 = new GFFReader("sample_files/three_scaffolds.gff");		
	}

	@Test
	public void testGFFReader() throws FileNotFoundException {
		setUp();
		assertTrue(testGFFReader1 instanceof GFFReader);
		assertTrue(testGFFReader1 instanceof CSVReader);
	}
	
	@Test
	public void testReadOneLine() throws IOException {
		setUp();
		assertEquals("scaffold00001", testGFFReader1.readOneLine()[0]);
		assertEquals("mRNA", testGFFReader1.readOneLine()[2]);		
	}
	
	@Test
	public void testReadOneGene() throws IOException {
		testGene1 = testGFFReader2.readOneGene();
		assertEquals(10, testGene1.getFeatures().size());
		testGene2 = testGFFReader2.readOneGene();
		assertEquals(9, testGene2.getFeatures().size());
		assertEquals("2392", testGene2.getFeatures().get(3)[3]);
	}
	
	@Test
	public void testReadOneScaffold() throws IOException {
		setUp();
		scaff = testGFFReader2.readOneScaffold();
		assertFalse(scaff.isEmpty());
		assertEquals(10, scaff.size());
		scaff2 = testGFFReader2.readOneScaffold();
		assertEquals(9, scaff2.size());
		scaff3 = testGFFReader3.readOneScaffold();
		assertEquals(10, scaff3.size());
		scaff3 = testGFFReader3.readOneScaffold();
		assertEquals(9, scaff3.size());
		scaff3 = testGFFReader3.readOneScaffold();
		assertEquals(4, scaff3.size());
	}
	
	@After
	public void tearDown() throws IOException {
		testGFFReader1.close();
		testGFFReader2.close();
		testGFFReader3.close();
	}

}
