package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

public class GeneProcessor {
	ArrayList<Scaffold> allScaffolds;

	public GeneProcessor(ArrayList<Scaffold> scaffolds) throws ScaffoldContigException {
		allScaffolds = scaffolds;	
	}
	
	// public ArrayList<Gene> prepareGeneForWriting(Gene gene) {
	// ArrayList<Gene> genesToWrite = new ArrayList<Gene>();
	// Scaffold currentScaffold = findScaffold(gene);
	// if (!spansMultipleContigs(gene, currentScaffold)) {
	//   ScaffoldContig sctg = findScaffoldContig(gene, currentScaffold);
	//   genesToWrite.add(scaffoldToScaffoldContig(gene, sctg));
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
	public ScaffoldContig findScaffoldContig(Gene gene, Scaffold scaffold) throws ScaffoldContigException {
		int geneStartingIndex = Integer.parseInt(gene.getFeatures().get(0)[3]);
		return scaffold.getScaffoldContig(geneStartingIndex);
	}


	public boolean spansMultipleContigs(Gene gene, Scaffold scaffold) throws ScaffoldContigException {
		int startIndex = Integer.parseInt(gene.getFeatures().get(0)[3]);
		int endIndex = Integer.parseInt(gene.getFeatures().get(0)[4]);
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