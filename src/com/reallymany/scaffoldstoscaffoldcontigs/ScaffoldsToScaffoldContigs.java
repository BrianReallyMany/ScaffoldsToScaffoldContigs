package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ScaffoldsToScaffoldContigs {

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
			System.out.println("Output file: "+args[2]+".gff");
			
			AGPReader sr = new AGPReader();
			ArrayList<Scaffold> scaffolds;
			Gene currentGene;
			ArrayList<Gene> currentGeneList;
			GeneProcessor gp = null;
			GFFReader reader = null;
			GFFWriter writer = null;
			
			scaffolds = sr.readScaffoldFile(args[0]);
			
			try {
				reader = new GFFReader(args[1]);
				writer = new GFFWriter(args[2]);
				gp = new GeneProcessor(scaffolds);
			} catch (FileNotFoundException e) {
				System.out.println("Error, could not find file "+args[1]);
				e.printStackTrace();
				return;
			} catch (IOException e) {
				System.out.println("I/O error involving the mysterious file "+args[2]);
				e.printStackTrace();
			} catch (ScaffoldContigException e) {
				System.err.println("Problem initializing Gene Processor.");
				e.printStackTrace();
			}
			
			try {
				while (!reader.getAtEndOfFile()) {
					currentGene = reader.readOneGene();
					currentGeneList = gp.prepareGeneForWriting(currentGene);
					writer.writeGenes(currentGeneList);
				}
				reader.close();
				writer.close();
			} catch (IOException e) {
				System.err.println("I/O exception.");
				e.printStackTrace();
			} catch (ScaffoldContigException e) {
				System.err.println("Problem with GeneProcessor; that's all I know.");
				e.printStackTrace();
			}			
		}
	}
}

// TODO ooops! puts ".1" or ".2" on everything, not just those that are split.
// TODO sctg not getting written, wtf?