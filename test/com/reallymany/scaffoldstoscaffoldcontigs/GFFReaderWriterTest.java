package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GFFReaderWriterTest {
	AGPReader testScaffoldReader = new AGPReader();
	GFFReaderWriter testReaderWriter;
	ArrayList<Scaffold> testScaffolds;
	
	@Before
	public void setUp() {
		testScaffolds = testScaffoldReader.readScaffoldFile("sample_files/sample.agp");
		testReaderWriter = new GFFReaderWriter("sample_files/sample.gff", "sample_files/sampleOut", testScaffolds);
	}

	@Test
	public void testGFFReaderWriter() {
		assertTrue(testReaderWriter instanceof GFFReaderWriter);
	}	
	
	@Test
	public void testProcessInput() {
		testReaderWriter.processInput();
		File theOutput = new File("sample_files/sampleOut.gff");
		assertTrue(theOutput.isFile());
	}
	
	// From here on out the tests take place in bash ...

}
