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
	
	public ArrayList<Gene> prepareGeneForWriting(Gene gene) throws ScaffoldContigException {
		Scaffold scaffold = findScaffold(gene);
		Boolean reachedLastSctg = false;
		int currentGeneIndex = 1;
		Gene currentGene;
		ArrayList<Gene> splitUpGenes = new ArrayList<Gene>();
		ScaffoldContig currentSctg;
		try {
			currentSctg = scaffold.getScaffoldContig(findGeneStartingIndex(gene));
		} catch (ScaffoldContigException e) {
			e.printStackTrace();
			return splitUpGenes;
		}
		Boolean spansMultipleContigs = !geneEndsOnThisSctg(gene, currentSctg);
		
		while (!reachedLastSctg) {
			currentGene = new Gene(gene);
			adjustIndices(currentGene, currentSctg);			
			if (spansMultipleContigs) {
				appendSubtype(currentGeneIndex, currentGene);			
			}
			splitUpGenes.add(scaffoldToScaffoldContig(currentGene, currentSctg));			
			if (geneEndsOnThisSctg(gene, currentSctg)) {				
				reachedLastSctg = true;
			} else {				
				currentSctg = scaffold.getNextScaffoldContig(currentSctg);
				currentGeneIndex ++;
			}
		}		
		return splitUpGenes;
	}
	
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

	// NOTE technically only checks that gene ends BEFORE the end of this Sctg...
	public boolean geneEndsOnThisSctg(Gene gene, ScaffoldContig currentSctg) {
		return (findGeneEndingIndex(gene) <= currentSctg.getEnd());
	}
	
	// This function also takes care of removing features not located on current sctg
	public void adjustIndices(Gene gene, ScaffoldContig sctg) {
		int sctgBegin = sctg.getBegin();
		int sctgEnd = sctg.getEnd();
		int featureBegin, featureEnd;
		String[] currentFeature;
		Iterator<String[]> featureIterator = gene.getFeatures().iterator();
		while (featureIterator.hasNext()) {
			currentFeature = featureIterator.next();
			featureBegin = Integer.parseInt(currentFeature[3]);
			featureEnd = Integer.parseInt(currentFeature[4]);
			if ((featureEnd < sctgBegin) || (featureBegin > sctgEnd)) {
				// Feature is not located on this ScaffoldContig at all
				featureIterator.remove();
			} else {
				if (featureBegin < sctgBegin) {
					System.err.println("Current feature ("+currentFeature[0]+" -- "+
							currentFeature[2]+ " -- startIndex "+
							currentFeature[3]+ ") starts before beginning of contig. Changing startIndex " +
							currentFeature[3]+ " to "+ sctgBegin);
					currentFeature[3] = Integer.toString(sctgBegin);
				} else {					
					currentFeature[3] = Integer.toString(featureBegin - sctgBegin + 1);
				}
				if (featureEnd > sctgEnd) {
					System.err.println("Current feature ("+currentFeature[0]+" -- "+
							currentFeature[2]+ " -- endIndex " +
							currentFeature[4] + ") extends beyond end of contig. Changing endIndex " +
							currentFeature[4]+ " to "+ sctgEnd);
					currentFeature[4] = Integer.toString(sctgEnd);
				} else {
					currentFeature[4] = Integer.toString(featureEnd - sctgBegin + 1);
				}
			}
		}		
	}

	public Gene scaffoldToScaffoldContig(Gene gene,
			ScaffoldContig sctg) {
		String[] currentFeature;
		String sctgName = sctg.getName();
		Iterator<String[]> featuresIterator = gene.getFeatures().iterator();
		while (featuresIterator.hasNext()) {
			currentFeature = featuresIterator.next();
			currentFeature[0] = sctgName;
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
			if (n==1) {	// The "Name=" field; a special case
				String[] nameField = lastColumn[n].split("-");
				nameField[0] = nameField[0].concat(addOn);
				lastColumn[n] = StringUtils.join(nameField, '-');
			} else {
				lastColumn[n] = lastColumn[n].concat(addOn);
			}
		}
		output = StringUtils.join(lastColumn, ';');
		return output;
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

	
}