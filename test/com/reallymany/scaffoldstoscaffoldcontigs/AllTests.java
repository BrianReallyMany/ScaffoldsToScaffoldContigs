package com.reallymany.scaffoldstoscaffoldcontigs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ScaffoldContigTest.class, ScaffoldTest.class, AGPReaderTest.class, 
	GeneTest.class, GFFVerifierTest.class, GFFReaderTest.class,
	GeneProcessorTest.class, GFFWriterTest.class })
public class AllTests {

}
