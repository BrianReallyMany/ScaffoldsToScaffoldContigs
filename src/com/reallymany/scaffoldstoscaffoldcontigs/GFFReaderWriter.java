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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void processInput() {
		String scaffoldName;
		Scaffold currentScaffold;
		ScaffoldContig currentScaffoldContig;
		String[] thisLineIn;
		String[] nextLineOut;
		int thisBegin, thisEnd;
		try {						
			while ((thisLineIn = reader.readNext()) != null) {
				nextLineOut = thisLineIn;
				// get scaffold from input file
				// find corresponding scaffold in referenceScaffolds
				// get ScaffoldContig that corresponds to thisLineIn[3] and [4],
				// make sure they're the same (if not we've got a
				// contig that spans two scaffolds...)
				// nextLineOut[0] is sctg
				// nextLineOut[1,2] don't change
				// nextLineOut[3] is thisLineIn[3]-begin + 1
				// nextLineOut[4] is thisLineIn[4]-end + 1
				// nextLineOut[5-7] don't change
				// nextLineOut[8] gets gsubbed (replace scaffold with sctg)
				scaffoldName = thisLineIn[0];
				try {
					thisBegin = Integer.parseInt(thisLineIn[3]);
					thisEnd = Integer.parseInt(thisLineIn[4]);
					currentScaffold = findScaffoldByName(scaffoldName, referenceScaffolds);
					System.out.println("thisBegin="+thisBegin);
					currentScaffoldContig = currentScaffold.getScaffoldContig(thisBegin);
					if (currentScaffoldContig.equals(currentScaffold.getScaffoldContig(thisEnd))) {
						nextLineOut[0] = currentScaffoldContig.getName();
						nextLineOut[3] = Integer.toString(thisBegin - currentScaffoldContig.getBegin() + 1);
						nextLineOut[4] = Integer.toString(thisEnd - currentScaffoldContig.getEnd() + 1);
						nextLineOut[8] = subScaffoldContigForScaffold(nextLineOut[8]);
					} else {
						throw new ScaffoldContigException("contig spans two scaffolds!");
					}
					
				} catch (ScaffoldContigException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

		private String subScaffoldContigForScaffold(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO why pass scaffoldsList in?
	private Scaffold findScaffoldByName(String scaffoldName,
			ArrayList<Scaffold> scaffoldsList) throws ScaffoldContigException {
		Iterator<Scaffold> scaffoldsIterator = scaffoldsList.iterator();
		while (scaffoldsIterator.hasNext()) {
			Scaffold thisScaffold = scaffoldsIterator.next();
			if (thisScaffold.getName().equals(scaffoldName)) {
				return thisScaffold;
			}
		}
		throw new ScaffoldContigException("no such scaffold");
	}	
	
	
	
}
