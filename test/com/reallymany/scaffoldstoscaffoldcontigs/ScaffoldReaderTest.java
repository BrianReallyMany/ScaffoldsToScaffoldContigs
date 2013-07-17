package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ScaffoldReaderTest {
	ScaffoldReader testSR;
	
	@Before
	public void setUp() {
		testSR = new ScaffoldReader();
	}

	@Test
	public void testScaffoldReader() {
		setUp();
		assertTrue(testSR instanceof ScaffoldReader);
	}

	@Test
	public void testReadScaffoldFile() throws ScaffoldContigException {
		setUp();
		ArrayList<Scaffold> sampleScaffolds;
		sampleScaffolds = testSR.readScaffoldFile("sample.txt");
		assertTrue(sampleScaffolds.size() != 0);
		assertTrue(sampleScaffolds.size() == 2);
		assertTrue(sampleScaffolds.get(0) instanceof Scaffold);
		assertTrue(sampleScaffolds.get(0).getScaffoldContigs().get(0) instanceof ScaffoldContig);
		assertEquals(sampleScaffolds.get(0).getScaffoldContigs().get(0).getBegin(), 1);
		assertEquals(sampleScaffolds.get(0).getScaffoldContigs().size(), 10);
		assertEquals(sampleScaffolds.get(1).getScaffoldContigs().size(), 5);
		assertEquals(sampleScaffolds.get(1).getScaffoldContigs().get(2).getName(), "sctg_0002_0002");
	}

}
