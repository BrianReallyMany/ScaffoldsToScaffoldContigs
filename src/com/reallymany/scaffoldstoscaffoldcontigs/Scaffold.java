package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

public class Scaffold {
	public String name;
	public ArrayList<ScaffoldContig> scaffoldContigs;
	
	public Scaffold(String n) {
		name = n;
		scaffoldContigs = new ArrayList<ScaffoldContig>();
	}
		
	public String getName() {
		return name;
	}

	public ArrayList<ScaffoldContig> getScaffoldContigs() {
		return scaffoldContigs;
	}

	public void addScaffoldContig(ScaffoldContig sctg) {
		this.scaffoldContigs.add(sctg);
	}
	
	public ScaffoldContig getScaffoldContig(int index) throws ScaffoldContigException {
		if (scaffoldContigs.size() == 0) {
			throw new ScaffoldContigException("No ScaffoldContigs");
		}
		Iterator<ScaffoldContig> sctgIterator = scaffoldContigs.iterator();
		while (sctgIterator.hasNext()) {
			ScaffoldContig currentSctg = sctgIterator.next();
			if ((index >= currentSctg.getBegin()) && 
					index <= currentSctg.getEnd()) {
				return currentSctg;
			}
		}	
		throw new ScaffoldContigException("Correct ScaffoldContig not found");
	}

	public ScaffoldContig getNextScaffoldContig(ScaffoldContig sctg) throws ScaffoldContigException {
		int inputIndex = scaffoldContigs.indexOf(sctg);
		if (inputIndex + 2 <= scaffoldContigs.size()) {
			return scaffoldContigs.get(inputIndex+1);
		} else {
			throw new ScaffoldContigException("index out of bounds at Scaffold.getNextScaffoldContig");
		}		
	}
}
