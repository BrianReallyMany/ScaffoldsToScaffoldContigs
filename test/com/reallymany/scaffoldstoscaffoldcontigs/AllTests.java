package com.reallymany.scaffoldstoscaffoldcontigs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ScaffoldContigTest.class, ScaffoldTest.class, ScaffoldReaderTest.class, 
	GFFReaderWriterTest.class })
public class AllTests {

}
