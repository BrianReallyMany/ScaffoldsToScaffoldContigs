package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

public class GeneProcessor {
	ArrayList<Scaffold> allScaffolds;
	ArrayList<Gene> genesBeingProcessed;

	public GeneProcessor(ArrayList<Scaffold> scaffolds) throws ScaffoldContigException {
		allScaffolds = scaffolds;	
	}
	
	// public void prepareGeneForWriting(Gene gene) {
	// Scaffold currentScaffold = findScaffold(gene);
	// if (!spansMultipleContigs(gene, currentScaffold)) {
	//   ScaffoldContig sctg = findScaffoldContig(gene, currentScaffold);
	//   genesBeingProcessed.add(scaffoldToScaffoldContig(gene, sctg));
	// else {
	//   genesToSplit = splitUp(gene, currentScaffold);
	//   for gene in genesToSplit do prepareGeneForWriting(gene) }
	// NOTE: void method now. must call prepareGeneForWriting on each 'gene'
	// read from gff, then call returnProcessedGenes() for output.
	// but, recursive, so bonus points.
	
	
	// public ArrayList<Gene> returnProcessedGenes() {
	//  ArrayList<Gene> genesToReturn = genesBeingProcessed;
	//  genesBeingProcessed.clear();
	//  return genesToReturn; }
	
	
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

	public int findGeneStartingIndex(Gene gene) {
		return Integer.parseInt(gene.getFeatures().get(0)[3]);
	}
	
	public int findGeneEndingIndex(Gene gene) {
		return Integer.parseInt(gene.getFeatures().get(0)[4]);
	}
	
	public int findFeatureStartingIndex(String[] feature) {
		return Integer.parseInt(feature[3]);
	}
	
	public int findFeatureEndingIndex(String[] feature) {
		return Integer.parseInt(feature[4]);
	}

	// Assumes geneBeingProcessed does NOT span 2 or more contigs
	public ScaffoldContig findScaffoldContig(Gene gene, Scaffold scaffold) throws ScaffoldContigException {
		int geneStartingIndex = findGeneStartingIndex(gene);
		return scaffold.getScaffoldContig(geneStartingIndex);
	}


	public boolean spansMultipleContigs(Gene gene, Scaffold scaffold) throws ScaffoldContigException {
		int startIndex = findGeneStartingIndex(gene);
		int endIndex = findGeneEndingIndex(gene);
		ScaffoldContig startSctg = scaffold.getScaffoldContig(startIndex);
		ScaffoldContig endSctg = scaffold.getScaffoldContig(endIndex);
		return !(startSctg.equals(endSctg));		
	}

	public Gene scaffoldToScaffoldContig(Gene gene,
			ScaffoldContig sctg) {
		String[] currentFeature;
		String sctgName = sctg.getName();
		Iterator<String[]> featuresIterator = gene.getFeatures().iterator();
		while (featuresIterator.hasNext()) {
			currentFeature = featuresIterator.next();
			currentFeature[0] = sctgName;
			recalculateIndices(currentFeature, sctg);
		}
		return gene;
	}

	public void recalculateIndices(String[] feature,
			ScaffoldContig sctg) {
		int newBegin = Integer.parseInt(feature[3]) - sctg.getBegin() + 1;
		int newEnd = Integer.parseInt(feature[4]) - sctg.getBegin() + 1;
		feature[3] = Integer.toString(newBegin);
		feature[4] = Integer.toString(newEnd);
	}

	

	

	


}