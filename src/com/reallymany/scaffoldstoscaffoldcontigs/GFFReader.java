package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

public class GFFReader extends CSVReader {
	Boolean atBeginningOfFile = true;
	Boolean atEndOfFile = false;
	String[] nextLine = null;
	String currentScaffoldName;
	
	public GFFReader(String filename) throws FileNotFoundException {
		super(new FileReader(filename), '\t');
	}
	
	public String[] readOneLine() throws IOException {		
		nextLine = this.readNext();
		return nextLine;
	}
	
	public Gene readOneGene() throws IOException {
		Gene nextGene = new Gene();
		if (atBeginningOfFile) {
			nextLine = this.readNext();	
			atBeginningOfFile = false;
		}
		
		nextGene.addFeature(nextLine);
		while ((nextLine = this.readNext()) != null) {
			if (nextLine[2].equals("gene")) {
				return nextGene;
			} else {
				nextGene.addFeature(nextLine);
			}
		}		
		atEndOfFile = true;
		return nextGene;
	}

	
	public ArrayList<String[]> readOneScaffold() throws IOException {
		ArrayList<String[]> nextScaffoldArray = new ArrayList<String[]>();
		if (atBeginningOfFile) {
			nextLine = this.readNext();	
			currentScaffoldName = nextLine[0];
			atBeginningOfFile = false;
		}
		
		nextScaffoldArray.add(nextLine);
		while ((nextLine = this.readNext()) != null) {
			if (!nextLine[0].equals(currentScaffoldName)) {
				currentScaffoldName = nextLine[0];
				return nextScaffoldArray;
			} else {
				nextScaffoldArray.add(nextLine);
			}
		}		
		atEndOfFile = true;
		return nextScaffoldArray;
	}

	public Boolean getAtEndOfFile() {
		return atEndOfFile;
	}
	
}
