package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScaffoldTest {
	Scaffold testScaffold1, testScaffold2;
	ScaffoldContig testSctg1, testSctg2A, testSctg2B, testSctg2C;

	@Before
	public void setUp() {
		testScaffold1 = new Scaffold("scaffold00001");
		testSctg1 = new ScaffoldContig("sctg_0001_0001", 1, 4968);
		
		testScaffold2 = new Scaffold("scaffold00002");
		testSctg2A = new ScaffoldContig("sctg_0002_0001", 1, 2000);
		testSctg2B = new ScaffoldContig("sctg_0002_0002", 2100, 4000);
		testSctg2C = new ScaffoldContig("sctg_0002_0003", 4100, 6000);
		testScaffold2.addScaffoldContig(testSctg2A);
		testScaffold2.addScaffoldContig(testSctg2B);
		testScaffold2.addScaffoldContig(testSctg2C);
		
	}

	@Test
	public void testScaffold() throws Exception {
		setUp();
		assertTrue(testScaffold1 instanceof Scaffold);
		assertEquals(testScaffold1.scaffoldContigs.size(), 0);
		
		testScaffold1.addScaffoldContig(testSctg1);
		assertEquals(testScaffold1.scaffoldContigs.size(), 1);
		
		assertEquals(testSctg1.getName(), testScaffold1.getScaffoldContig(1).getName());
		assertEquals(testSctg1.getName(), testScaffold1.getScaffoldContig(4000).getName());
		assertEquals(testSctg1.getName(), testScaffold1.getScaffoldContig(4968).getName());
		
		try {
			testScaffold1.getScaffoldContig(4969);
			fail("Didn't throw exception!");
		} catch (ScaffoldContigException e) {
		}
		try {
			testScaffold1.getScaffoldContig(0);
			fail("Didn't throw exception!");
		} catch (ScaffoldContigException e) {
		}
	}
	
	@Test
	public void testGetNextScaffoldContig() throws Exception {
		setUp();
		assertEquals(testSctg2B, testScaffold2.getNextScaffoldContig(testSctg2A));
		assertEquals(testSctg2C, testScaffold2.getNextScaffoldContig(testSctg2B));
		try {
			testScaffold2.getNextScaffoldContig(testSctg2C);
			fail("Didn't throw exception!");
		} catch (ScaffoldContigException e) {
		}
	}
	
}
