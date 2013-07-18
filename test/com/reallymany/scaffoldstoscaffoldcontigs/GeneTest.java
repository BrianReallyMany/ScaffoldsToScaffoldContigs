package com.reallymany.scaffoldstoscaffoldcontigs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GeneTest {
	Gene testGene;

	@Before
	public void setUp() {
		testGene = new Gene();
	}

	@Test
	public void testGene() {
		setUp();
		assertTrue(testGene instanceof Gene);
		assertEquals(testGene.spansTwoContigs, false);
		assertTrue(testGene.children instanceof ArrayList);
	}

}
