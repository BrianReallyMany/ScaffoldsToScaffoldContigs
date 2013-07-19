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
	CSVWriter discardsWriter;
	ArrayList<Scaffold> referenceScaffolds;
	
	public GFFReaderWriter(String in, String out, ArrayList<Scaffold> scaffolds) {
		this.referenceScaffolds = scaffolds;
		try {
			reader = new CSVReader(new FileReader(in), '\t');
			writer = new CSVWriter(new FileWriter(out+".gff"), '\t', CSVWriter.NO_QUOTE_CHARACTER);
			discardsWriter = new CSVWriter(new FileWriter(out+".discards.gff"), '\t', CSVWriter.NO_QUOTE_CHARACTER);
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
		ArrayList<String[]> discards = new ArrayList<String[]>();
		Boolean discardingChildren = false;
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
					if (thisLineIn[2].equals("gene")) {
						discardingChildren = false;
					}
					// Note! If this line is the child of a gene
					// which spans two contigs, we write it to discards
					// WITHOUT UPDATING THE INDICES b/c at the moment who cares?
					if (discardingChildren) {
						discards.add(thisLineIn);
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
						discards.add(nextLineOut);
						discardingChildren = true;
//						throw new ScaffoldContigException("Contig spans two scaffolds! Skipping this one.");
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
		// Write discards to file
		discardsWriter.writeAll(discards);
		try {
			discardsWriter.close();
		} catch (IOException e) {
			System.out.println("IOException writing discards to file.");
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
