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
	String nextGeneName;
	
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

	public Boolean getAtEndOfFile() {
		return atEndOfFile;
	}

//	public ArrayList<String[]> getNextScaffold() {
//	ArrayList<String[]> nextScaffoldArray = new ArrayList<String[]>();

//		try {
////			nextScaffold.add(this.readNext());
//			while ((nextLine = this.readNext()) != null) {
//				if (!nextLine[2].equals("gene")) {
//					nextScaffold.add(nextLine);
//				} else {
//					return nextScaffold;
//				}			
//			}
//		} catch (IOException e1) {
//			System.err.println("IOException in GFFReader.readNext()");
//			e1.printStackTrace();
//		}
//		
//		try {
//			this.close();
//		} catch (IOException e) {
//			System.err.println("IOException in GFFReader.close");
// 			e.printStackTrace();
//		}
//		// TODO Auto-generated method stub
//		return nextScaffold;
//	}
	
}
