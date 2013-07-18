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
			// Make sure GFF is sorted
			UnsortedGFFDetector detector = new UnsortedGFFDetector(args[1]);
			if (!detector.verifyFile()) {
				System.out.println("GFF file not sorted by scaffold; please sort and try again.");
				return;				
			}
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

// TODO refactor GFFReaderWriter to use Gene class
// TODO change verifyGFF to check only that no scaffolds are spread out (e.g. ten lines of scaffold80, two lines of scaffold200, then another line of scaffold80...
// TODO mockup test files for all this