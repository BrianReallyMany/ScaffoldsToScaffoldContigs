package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GFFReaderWriterTest {
	ScaffoldReader testScaffoldReader = new ScaffoldReader();
	GFFReaderWriter testReaderWriter;
	ArrayList<Scaffold> testScaffolds;
	
	@Before
	public void setUp() {
		testScaffolds = testScaffoldReader.readScaffoldFile("test_files/sample.txt");
		testReaderWriter = new GFFReaderWriter("test_files/sample.gff", "test_files/sampleOut", testScaffolds);
	}

	@Test
	public void testGFFReaderWriter() {
		assertTrue(testReaderWriter instanceof GFFReaderWriter);
	}	
	
	@Test
	public void testProcessInput() {
		testReaderWriter.processInput();
		File theOutput = new File("test_files/sampleOut.gff");
		assertTrue(theOutput.isFile());
	}
	
	// From here on out the tests take place in bash ...

}
