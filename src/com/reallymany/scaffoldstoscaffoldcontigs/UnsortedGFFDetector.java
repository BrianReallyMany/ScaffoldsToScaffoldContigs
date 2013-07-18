package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class UnsortedGFFDetector {
	String gffFilename;
	Integer previousScaffoldNumber = -1;
	CSVReader reader;
	Boolean isSortedGFF = true;

	public UnsortedGFFDetector(String filename) {
		this.gffFilename = filename;
		try {
			reader = new CSVReader(new FileReader(gffFilename), '\t');
		} catch (FileNotFoundException e) {
			System.err.println("File not found: "+gffFilename);
			e.printStackTrace();
		}
	}
	
	public Boolean verifyFile() {
		readGFF();
		return isSortedGFF;
	}

	public void readGFF() {
		String[] currentLine;		
		try {
			while ((currentLine = reader.readNext()) != null) {			
				verifySortedByLine(currentLine);
			}
		} catch (IOException e) {
			System.err.println("IOException trying to read GFF file -- UnsortedGFFDetector");
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("IOException trying to close CSVReader.");
			e.printStackTrace();
		}
		
	}

	void verifySortedByLine(String[] inputLine) {
		String currentScaffoldName = inputLine[0];
		Integer currentScaffoldNumber = getScaffoldNumber(currentScaffoldName);
		if (currentScaffoldNumber < previousScaffoldNumber) {
			isSortedGFF = false;
		}
		previousScaffoldNumber = currentScaffoldNumber;		
	}

	Integer getScaffoldNumber(String currentScaffoldName) {
		return Integer.parseInt(currentScaffoldName.replaceFirst("scaffold", ""));
	}
	
//	public read
	
}
