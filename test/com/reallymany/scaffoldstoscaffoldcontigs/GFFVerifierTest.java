package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class GFFVerifierTest {
	GFFVerifier testDetector, testDetector2, testDetector3;
	
	@Before
	public void setUp() {
		testDetector = new GFFVerifier("sample_files/sample.gff");
		testDetector2 = new GFFVerifier("sample_files/unsorted_sample.gff");
		testDetector3 = new GFFVerifier("sample_files/unsorted_yet_valid.gff");
	}

	@Test
	public void testUnsortedGFFDetector() {
		setUp();
		assertTrue(testDetector instanceof GFFVerifier);
		assertTrue(testDetector.reader instanceof CSVReader);		
	}
	
	@Test
	public void testVerifyFile() {
		setUp();
		assertTrue(testDetector.verifyFile());		
		assertFalse(testDetector2.verifyFile());			
		assertTrue(testDetector3.verifyFile());		
	}
	
//	@Test
//	public void testValidateLineByLine() {
//		setUp();
//		String[] someInput = new String[9];
//		someInput[0] = "scaffold0007";				
//		testDetector.validateLineByLine(someInput);
//		assertFalse(testDetector.isValidGFF);		
//	}
	
	
	
}
