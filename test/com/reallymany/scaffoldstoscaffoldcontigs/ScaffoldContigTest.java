package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScaffoldContigTest {

	@Test
	public void testScaffoldContig() {
		ScaffoldContig testSctg = new ScaffoldContig("sctg_0001_0001", 1, 4968);
		assertTrue(testSctg instanceof ScaffoldContig);
		assertEquals(4968, testSctg.getEnd());
		assertEquals("sctg_0001_0001", testSctg.getName());
	}

}
