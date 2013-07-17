package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class GFFReaderWriter {
	CSVReader reader;
	CSVWriter writer;
	ArrayList<Scaffold> referenceScaffolds;
	
	public GFFReaderWriter(String in, String out, ArrayList<Scaffold> scaffolds) {
		this.referenceScaffolds = scaffolds;
		try {
			reader = new CSVReader(new FileReader(in), '\t');
			writer = new CSVWriter(new FileWriter(out), '\t');
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException!");
			e.printStackTrace();
		}		
	}

	// TODO omg refactor me plz
	public void processInput() {
		String scaffoldName;
		Scaffold currentScaffold;
		ScaffoldContig currentScaffoldContig;
		String[] thisLineIn;
		String[] nextLineOut;
		int thisBegin, thisEnd;
		try {						
			while ((thisLineIn = reader.readNext()) != null) {				
				try {
					// Ignore comments
					if (thisLineIn[0].startsWith("#")) {
						continue;
					}
					nextLineOut = thisLineIn;
					scaffoldName = thisLineIn[0];
					thisBegin = Integer.parseInt(thisLineIn[3]);
					thisEnd = Integer.parseInt(thisLineIn[4]);
					currentScaffold = findScaffoldByName(scaffoldName);
					currentScaffoldContig = currentScaffold.getScaffoldContig(thisBegin);
					if (currentScaffoldContig.equals(currentScaffold.getScaffoldContig(thisEnd))) {
						nextLineOut[0] = currentScaffoldContig.getName();
						nextLineOut[3] = Integer.toString(thisBegin - currentScaffoldContig.getBegin() + 1);
						nextLineOut[4] = Integer.toString(thisEnd - currentScaffoldContig.getBegin() + 1);
						writer.writeNext(nextLineOut);
					} else {
						throw new ScaffoldContigException("Contig spans two scaffolds! Skipping this one.");
					}					
				} catch (ScaffoldContigException e) {
					System.err.println("Error reading/writing scaffolds...");
					e.printStackTrace();
				}				
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found");			
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException, sorry");
			e.printStackTrace();
		}		
	}

	public Scaffold findScaffoldByName(String scaffoldName) throws ScaffoldContigException {
		Iterator<Scaffold> scaffoldsIterator = referenceScaffolds.iterator();
		while (scaffoldsIterator.hasNext()) {
			Scaffold thisScaffold = scaffoldsIterator.next();
			if (thisScaffold.getName().equals(scaffoldName)) {
				return thisScaffold;
			}
		}
		throw new ScaffoldContigException("no such scaffold");
	}	
	
	
	
}
