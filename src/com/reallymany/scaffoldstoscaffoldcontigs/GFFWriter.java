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
		System.out.println("you rang?");
		String[] bar = {"foo", "bar"};
		this.writeNext(bar);
		Iterator<Gene> geneIterator = listOfGenes.iterator();
		while (geneIterator.hasNext()) {
			Gene currentGene = geneIterator.next();
			this.writeAll(currentGene.getFeatures());	
			String[] foo = {"foo", "bar"};
			this.writeNext(foo);
		}		
	}	
}
