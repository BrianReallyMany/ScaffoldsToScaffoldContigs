package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class UnsortedGFFDetectorTest {
	UnsortedGFFDetector testDetector;
	
	@Before
	public void setUp() {
		testDetector = new UnsortedGFFDetector("sample_files/sample.gff");
	}

	@Test
	public void testUnsortedGFFDetector() {
		setUp();
		assertTrue(testDetector instanceof UnsortedGFFDetector);
		assertTrue(testDetector.reader instanceof CSVReader);		
	}
	
	@Test
	public void testVerifyFile() {
		setUp();
		assertTrue(testDetector.verifyFile());
		
		UnsortedGFFDetector anotherTestDetector = new UnsortedGFFDetector("sample_files/unsorted_sample.gff");
		assertFalse(anotherTestDetector.verifyFile());
		
	}
	
	@Test
	public void testVerifySortedByLine() {
		setUp();
		testDetector.previousScaffoldNumber = 10;
		String[] someInput = new String[9];
		someInput[0] = "scaffold0007";				
		testDetector.verifySortedByLine(someInput);
		assertFalse(testDetector.isSortedGFF);		
	}
	
	@Test
	public void testGetScaffoldNumber() {
		setUp();
		assertTrue(testDetector.getScaffoldNumber("scaffold00001").equals(1));
		assertTrue(testDetector.getScaffoldNumber("scaffold00238").equals(238));
	}
	
	
}
