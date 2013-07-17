package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScaffoldTest {
	Scaffold testScaffold;
	ScaffoldContig sctg = new ScaffoldContig("sctg_0001_0001", 1, 4968);

	@Before
	public void setUp() {
		testScaffold = new Scaffold("scaffold00001");
	}

	@Test
	public void testScaffold() throws Exception {
		setUp();
		assertTrue(testScaffold instanceof Scaffold);
		assertEquals(testScaffold.scaffoldContigs.size(), 0);
		
		testScaffold.addScaffoldContig(sctg);
		assertEquals(testScaffold.scaffoldContigs.size(), 1);
		
		assertEquals(sctg.getName(), testScaffold.getScaffoldContig(1).getName());
		assertEquals(sctg.getName(), testScaffold.getScaffoldContig(4000).getName());
		assertEquals(sctg.getName(), testScaffold.getScaffoldContig(4968).getName());
		
		try {
			testScaffold.getScaffoldContig(4969);
			fail("Didn't throw exception!");
		} catch (ScaffoldContigException e) {
		}
		try {
			testScaffold.getScaffoldContig(0);
			fail("Didn't throw exception!");
		} catch (ScaffoldContigException e) {
		}
	}
	
}
