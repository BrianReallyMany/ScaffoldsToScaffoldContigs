package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

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
	// TODO is this necessary? better just to test and if fits on one contig,
	// just use Scaffold.getScaffoldContig(findGeneStartingIndex(gene))??
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

	// TODO could probably be clever and use this fn to process genes whether
	// they span multiple contigs or not, but one thing at a time
	public ArrayList<Gene> splitUp(Gene gene, Scaffold scaffold) throws ScaffoldContigException {
		Boolean reachedLastSctg = false;
		int currentGeneIndex = 1;
		Gene currentGene;
//		String[] currentFeature;
		ArrayList<Gene> splitUpGenes = new ArrayList<Gene>();
		ScaffoldContig currentSctg = scaffold.getScaffoldContig(findGeneStartingIndex(gene));
		
//		// first gene is a special case; its begin index doesn't get changed...
//		ScaffoldContig currentSctg = scaffold.getScaffoldContig(findGeneStartingIndex(gene));
//		currentGene = new Gene(gene);
//		
//		// wait a minute, didn't i just copy the whole gene?
//		System.out.println(currentGene.getFeatures().get(3)[3]);
//		
//		// "initialize" new gene with the first row...
//		currentFeature = gene.getFeatures().get(0);
//		currentGene.addFeature(currentFeature);
//
//		// then edit that row
//		currentGene.getFeatures().get(0)[4] = Integer.toString(currentSctg.getEnd());
//		currentGene.getFeatures().get(0)[8] = appendSubtype(currentGeneIndex, currentGene.getFeatures().get(0)[8]);
//		splitUpGenes.add(currentGene);
		
		
		// now we have our first new gene, though it currently contains only the first row.
		// time to start looping ...
		while (!reachedLastSctg) {
			currentGene = new Gene(gene);
			adjustIndices(currentGene, currentSctg);
			appendSubtype(currentGeneIndex, currentGene);
			splitUpGenes.add(currentGene);
			
			// is this the last sctg?
			if (geneEndsOnThisSctg(gene, currentSctg)) {				
				reachedLastSctg = true;
			} else {				
				currentSctg = scaffold.getNextScaffoldContig(currentSctg);
				currentGeneIndex ++;
			}
		}		
		return splitUpGenes;
	}

	private void appendSubtype(int currentGeneIndex, Gene gene) {
		for (String[] feature : gene.getFeatures()) {
			feature[8] = appendSubtype(currentGeneIndex, feature[8]);
		}		
	}
	
	public String appendSubtype(int currentGeneIndex, String string) {
		String output;
		String addOn = "." + Integer.toString(currentGeneIndex);
		String[] lastColumn = string.split(";");
		for (int n=0; n < lastColumn.length; n++) {
			lastColumn[n] = lastColumn[n].concat(addOn);
		}
		output = StringUtils.join(lastColumn, ';');
		return output;
	}

	public void adjustIndices(Gene gene, ScaffoldContig sctg) {
		int sctgBegin = sctg.getBegin();
		int sctgEnd = sctg.getEnd();
		int featureBegin, featureEnd;
		String[] currentFeature;
		for (int i=0; i<gene.getFeatures().size(); i++) {
			currentFeature = gene.getFeatures().get(i);
			featureBegin = Integer.parseInt(currentFeature[3]);
			featureEnd = Integer.parseInt(currentFeature[4]);
			if ((featureEnd < sctgBegin) || (featureBegin > sctgEnd)) {
				// Feature is not on this ScaffoldContig at all
				// TODO
			} else {
				if (featureBegin < sctgBegin) {
					currentFeature[3] = Integer.toString(sctgBegin);
				} else {
					currentFeature[3] = Integer.toString(featureBegin - sctgBegin + 1);
				}
				if (featureEnd > sctgEnd) {
					currentFeature[4] = Integer.toString(sctgEnd);
				} else {
					currentFeature[4] = Integer.toString(featureEnd - sctgBegin + 1);
				}
			}
		}
		
	}

	// NOTE technically only checks that gene ends BEFORE the end of this Sctg...
	public boolean geneEndsOnThisSctg(Gene gene, ScaffoldContig currentSctg) {
		return (findGeneEndingIndex(gene) <= currentSctg.getEnd());
	}

	

	

	

	


}