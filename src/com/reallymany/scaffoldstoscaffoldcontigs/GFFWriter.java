package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVWriter;

public class GFFWriter extends CSVWriter {

	public GFFWriter(String filename) throws IOException {
		super(new FileWriter(filename+".gff"), '\t', CSVWriter.NO_QUOTE_CHARACTER);
	}

	public void writeGenes(ArrayList<Gene> listOfGenes) {		
		Iterator<Gene> geneIterator = listOfGenes.iterator();
		while (geneIterator.hasNext()) {
			Gene currentGene = geneIterator.next();
			this.writeAll(currentGene.getFeatures());	
		}		
	}	
}
