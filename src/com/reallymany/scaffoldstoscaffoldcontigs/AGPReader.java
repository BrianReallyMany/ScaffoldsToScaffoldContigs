package com.reallymany.scaffoldstoscaffoldcontigs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;

public class AGPReader {
	ArrayList<Scaffold> allScaffolds;
	
	public AGPReader() {
		allScaffolds = new ArrayList<Scaffold>();
	}
	
	public ArrayList<Scaffold> readScaffoldFile(String filename) {
		try {
			CSVReader reader = new CSVReader(new FileReader(filename), '\t');
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine[5].startsWith("sctg")) {
					updateAllScaffolds(nextLine[0], Integer.parseInt(nextLine[1]),
							Integer.parseInt(nextLine[2]), nextLine[5]);
				}
				
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Input file " + filename + " not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException. Sorry, that's all I know.");
			e.printStackTrace();
		}
		
		return allScaffolds;
	}

	private void updateAllScaffolds(String scaffold, int begin,
			int end, String scaffoldContig) {
		// First see if this scaffold is already in allScaffolds
		Iterator<Scaffold> scaffoldIterator = allScaffolds.iterator();
		while (scaffoldIterator.hasNext()) {
			Scaffold currentScaffold = scaffoldIterator.next();			
			if (currentScaffold.getName().equals(scaffold)) {
				ScaffoldContig sctg = new ScaffoldContig(scaffoldContig, begin, end);
				currentScaffold.addScaffoldContig(sctg);
				return;
			}
		}				
		// if we reach this point, allScaffolds doesn't yet contain this scaffold.
		ScaffoldContig newSctg = new ScaffoldContig(scaffoldContig, begin, end);
		Scaffold newScaffold = new Scaffold(scaffold);
		newScaffold.addScaffoldContig(newSctg);
		allScaffolds.add(newScaffold);
	}
	
}
