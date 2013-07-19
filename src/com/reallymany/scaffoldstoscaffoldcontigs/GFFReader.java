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
	
	public ArrayList<String[]> readOneGene() throws IOException {
		ArrayList<String[]> nextGeneArray = new ArrayList<String[]>();
		if (atBeginningOfFile) {
			nextLine = this.readNext();	
			atBeginningOfFile = false;
		}
		
		nextGeneArray.add(nextLine);
		while ((nextLine = this.readNext()) != null) {
			if (nextLine[2].equals("gene")) {
				return nextGeneArray;
			} else {
				nextGeneArray.add(nextLine);
			}
		}		
		atEndOfFile = true;
		return nextGeneArray;
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
			System.out.println(nextLine[0]);
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
