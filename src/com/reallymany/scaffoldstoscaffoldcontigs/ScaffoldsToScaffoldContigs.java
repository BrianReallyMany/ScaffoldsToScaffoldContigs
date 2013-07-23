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
			GFFVerifier detector = new GFFVerifier(args[1]);
			if (!detector.verifyFile()) {
				System.out.println("GFF file not sorted by scaffold; please sort and try again.");
				return;				
			}
			
			System.out.println("Using agp file: "+args[0]);
			System.out.println("Rewriting gff file: "+args[1]);
			System.out.println("Output file: "+args[2]);
			
			AGPReader sr = new AGPReader();
			GFFReaderWriter gffRW;
			ArrayList<Scaffold> scaffolds;
			
			scaffolds = sr.readScaffoldFile(args[0]);
			gffRW = new GFFReaderWriter(args[1], args[2], scaffolds);

			gffRW.processInput();			
		}
	}
}

// TODO should GeneProcessor only take allScaffolds for constructor and then take Genes
// as arguments? in other words do we have a 1:1 Gene:GeneProcessor ratio or should it
// be N:1 ?
// TODO where does the Name=... field get updated? in Gene.split()?
// TODO be more rigid about Gene.enclosingThings? like require at construction? maybe the
// allScaffolds thing should be global ish after AGPReader generates it?
// TODO write GFFWriter (constructor takes filename, method writeGenes takes ArrayList<Gene>)
// TODO consider using TreeSet for Scaffold.scaffoldContigs ?