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
	GFFReader testGFFReader1, testGFFReader2;
	ArrayList<String[]> oneGene, twoGene;

	@Before
	public void setUp() {
		try {
			testGFFReader1 = new GFFReader("sample_files/sample.gff");
			testGFFReader2 = new GFFReader("sample_files/two_scaffolds.gff");
		} catch (FileNotFoundException e) {
			System.err.println("File not found: sample_files/sample.gff");
			e.printStackTrace();
		}
	}

	@Test
	public void testGFFReader() {
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
		oneGene = testGFFReader2.readOneGene();
		assertEquals(10, oneGene.size());
		twoGene = testGFFReader2.readOneGene();
		assertEquals(9, twoGene.size());
		assertEquals("2392", twoGene.get(3)[3]);
	}
	
//	@Test
//	public void testGetNextScaffold() {
//		setUp();
//		ArrayList<String[]> scaff = testGFFReader2.getNextScaffold();
//		assertFalse(scaff.isEmpty());
//	}
	
	@After
	public void tearDown() throws IOException {
		testGFFReader1.close();
		testGFFReader2.close();
	}

}
