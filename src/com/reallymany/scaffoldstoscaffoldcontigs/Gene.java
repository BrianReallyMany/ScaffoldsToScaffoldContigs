package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;
import java.util.Iterator;

public class Gene {
	ScaffoldContig enclosingScaffoldContig;	
	ArrayList<String[]> features;
	
	
	public Gene() {
		features = new ArrayList<String[]>();
	}

	public Gene(ArrayList<String[]> myChildren) {
		features = myChildren;
	}	
		

	public ScaffoldContig getEnclosingScaffoldContig() throws ScaffoldContigException {
		if (enclosingScaffoldContig != null) {
			return enclosingScaffoldContig;
		} else {
			throw new ScaffoldContigException("ScaffoldContig enclosingScaffoldContig == null");
		}
	}

	public void setEnclosingScaffoldContig(ScaffoldContig enclosingScaffoldContig) {
		this.enclosingScaffoldContig = enclosingScaffoldContig;
	}

	public ArrayList<String[]> getFeatures() {
		return features;
	}
	
	public void addFeature(String[] oneFeature) {
		features.add(oneFeature);
	}

	// Only to be called on Genes which are located on a single scaffold-contig!
	// this.enclosingScaffoldContig must be set before calling! Plz be careful!
	public void scaffoldToSctg() throws ScaffoldContigException {
		if (enclosingScaffoldContig != null) {
			String[] currentFeature;
			Iterator<String[]> featuresIterator = features.iterator();
			while (featuresIterator.hasNext()) {
				currentFeature = featuresIterator.next();
				currentFeature[0] = enclosingScaffoldContig.getName();
				recalculateIndices(currentFeature);
			}
		} else {
			throw new ScaffoldContigException("scaffoldToSctg called on Gene with enclosingScaffoldContig == null");
		}
		
	}

	private void recalculateIndices(String[] feature) {
		int newBegin = Integer.parseInt(feature[3]) - enclosingScaffoldContig.getBegin() + 1;
		int newEnd = Integer.parseInt(feature[4]) - enclosingScaffoldContig.getBegin() + 1;
		feature[3] = Integer.toString(newBegin);
		feature[4] = Integer.toString(newEnd);
	}

	
	
	
	
	

}
