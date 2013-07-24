package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

public class GeneProcessor {
	ArrayList<Gene> genesToWrite;
	ArrayList<Scaffold> allScaffolds;

	public GeneProcessor(ArrayList<Scaffold> scaffolds) throws ScaffoldContigException {
		genesToWrite = new ArrayList<Gene>();
		allScaffolds = scaffolds;	
	}
	// public ArrayList<Gene> prepareGeneForWriting(Gene gene) {
	// geneBeingProcessed = gene;
	// genesToWrite.clear();
	// currentScaffold = findCurrentScaffold();
	// if (!geneSpansMultipleContigs) {
	//   gene.setEnclosingScaffoldContig(findCurrentScaffoldContig());
	//   gene.scaffoldToSctg();
	//   genesToWrite.add(gene);
	//   return genesToWrite;
	// else {
	//   genesToWrite = gene.splitUp();
	//   for gene in genesToWrite do gene.scaffoldToSctg()
	//   return genesToWrite;	
	
	
	public Scaffold findScaffold(Gene gene) throws ScaffoldContigException {
		String scaffoldName = gene.getFeatures().get(0)[0];
		Iterator<Scaffold> scaffoldsIterator = allScaffolds.iterator();
		while (scaffoldsIterator.hasNext()) {
			Scaffold thisScaffold = scaffoldsIterator.next();
				if (thisScaffold.getName().equals(scaffoldName)) {
					return thisScaffold;
			}
		}
		throw new ScaffoldContigException("no such scaffold @ GeneProcessor.findCurrentScaffold()");
	}


	// Assumes geneBeingProcessed does NOT span 2 or more contigs
	public ScaffoldContig findCurrentScaffoldContig(Gene gene) throws ScaffoldContigException {
		int geneStartingIndex = Integer.parseInt(gene.getFeatures().get(0)[3]);
		return findScaffold(gene).getScaffoldContig(geneStartingIndex);
	}


	public boolean spansMultipleContigs(Gene gene, Scaffold scaffold) throws ScaffoldContigException {
		int startIndex = Integer.parseInt(gene.getFeatures().get(0)[3]);
		int endIndex = Integer.parseInt(gene.getFeatures().get(0)[4]);
		ScaffoldContig startSctg = scaffold.getScaffoldContig(startIndex);
		ScaffoldContig endSctg = scaffold.getScaffoldContig(endIndex);
		return !(startSctg.equals(endSctg));
		
	}


	
	
	

}


// Only to be called on Genes which are located on a single scaffold-contig!
// this.enclosingScaffoldContig must be set before calling! Plz be careful!
//public void scaffoldToSctg() throws ScaffoldContigException {
//	if (enclosingScaffoldContig != null) {
//		String[] currentFeature;
//		Iterator<String[]> featuresIterator = features.iterator();
//		while (featuresIterator.hasNext()) {
//			currentFeature = featuresIterator.next();
//			currentFeature[0] = enclosingScaffoldContig.getName();
//			recalculateIndices(currentFeature);
//		}
//	} else {
//		throw new ScaffoldContigException("scaffoldToSctg called on Gene with enclosingScaffoldContig == null");
//	}
//	
//}
//
//private void recalculateIndices(String[] feature) {
//	int newBegin = Integer.parseInt(feature[3]) - enclosingScaffoldContig.getBegin() + 1;
//	int newEnd = Integer.parseInt(feature[4]) - enclosingScaffoldContig.getBegin() + 1;
//	feature[3] = Integer.toString(newBegin);
//	feature[4] = Integer.toString(newEnd);
//}
