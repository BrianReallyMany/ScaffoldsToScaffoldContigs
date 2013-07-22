package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

public class GFFVerifier {
	CSVReader reader;
	Boolean atBeginningOfFile, isValidGFF;
	String[] currentLine;
	String previousScaffoldName;
	ArrayList<String> forbiddenScaffolds;
	
	public GFFVerifier(String filename) {
		try {
			this.reader = new CSVReader(new FileReader(filename), '\t');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		atBeginningOfFile = true;
		isValidGFF = true;
		previousScaffoldName = "";
		forbiddenScaffolds = new ArrayList<String>();
	}
	
	// TODO find out how to make try-catch less ugly
	public Boolean verifyFile() {
		if (atBeginningOfFile) {
			try {
				currentLine = reader.readNext();
				previousScaffoldName = currentLine[0];
				atBeginningOfFile = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		try {
			while ((currentLine = reader.readNext()) != null) {
				if (!currentLine[0].equals(previousScaffoldName)) {
					if (forbiddenScaffolds.contains(currentLine[0])) {
						isValidGFF = false;
					}
					forbiddenScaffolds.add(previousScaffoldName);
					previousScaffoldName = currentLine[0];
				}			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isValidGFF;
	}
	
}

