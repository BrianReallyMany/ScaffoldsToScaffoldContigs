package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

public class GeneProcessor {
	Gene geneBeingProcessed;
	ArrayList<Gene> genesToWrite;
	Scaffold currentScaffold;
	ArrayList<Scaffold> allScaffolds;

	public GeneProcessor(Gene inputGene, ArrayList<Scaffold> scaffolds) throws ScaffoldContigException {
		geneBeingProcessed = inputGene;
		genesToWrite = new ArrayList<Gene>();
		allScaffolds = scaffolds;	
		currentScaffold = findCurrentScaffold();
	}

	private Scaffold findCurrentScaffold() throws ScaffoldContigException {
		String scaffoldName = geneBeingProcessed.getFeatures().get(0)[0];
		Iterator<Scaffold> scaffoldsIterator = allScaffolds.iterator();
		while (scaffoldsIterator.hasNext()) {
			Scaffold thisScaffold = scaffoldsIterator.next();
				if (thisScaffold.getName().equals(scaffoldName)) {
					return thisScaffold;
			}
		}
		throw new ScaffoldContigException("no such scaffold @ GeneProcessor.findCurrentScaffold");
	}
	

	public boolean featureSpansMultipleContigs(String[] feature) throws ScaffoldContigException {
		int startingIndex, endingIndex;
		ScaffoldContig startingSctg, endingSctg;
		startingIndex = Integer.parseInt(feature[3]);
		endingIndex = Integer.parseInt(feature[4]);
		startingSctg = currentScaffold.getScaffoldContig(startingIndex);
		endingSctg = currentScaffold.getScaffoldContig(endingIndex);
		if (startingSctg == endingSctg) {
			return false;
		} else {
			return true;
		}
		
	}

	public boolean geneSpansMultipleContigs() throws ScaffoldContigException {
		return featureSpansMultipleContigs(geneBeingProcessed.getFeatures().get(0));
	}

}
