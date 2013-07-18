package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;

public class ScaffoldsToScaffoldContigs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: ScaffoldToScaffoldContigs <agp_file> <sorted_gff_file> <output_file_prefix>");			
		} else {
			System.out.println("Using agp file: "+args[0]);
			System.out.println("Rewriting gff file: "+args[1]);
			System.out.println("Output file: "+args[2]);
			
			ScaffoldReader sr = new ScaffoldReader();
			GFFReaderWriter gffRW;
			ArrayList<Scaffold> scaffolds;
			
			scaffolds = sr.readScaffoldFile(args[0]);
			gffRW = new GFFReaderWriter(args[1], args[2], scaffolds);

			gffRW.processInput();			
		}
	}
}

// TODO check for sorted gff before start
// TODO write discards to another file
// TODO if discard gene, discard all its children
// TODO handle fragments in agp file
// TODO mockup test files for all this