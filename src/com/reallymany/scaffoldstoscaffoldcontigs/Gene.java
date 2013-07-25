package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;

public class Gene {
	ArrayList<String[]> features;	
	
	public Gene() {
		features = new ArrayList<String[]>();
	}
	
	public Gene(Gene otherGene) {
		this.features = copyFeatures(otherGene.features);
	}

	private ArrayList<String[]> copyFeatures(ArrayList<String[]> inputFeatures) {
		ArrayList<String[]> copiedFeatures = new ArrayList<String[]>(inputFeatures.size());
		for (String[] feature : inputFeatures) {
			copiedFeatures.add(copyOneFeature(feature));
		}
		return copiedFeatures;
	}

	private String[] copyOneFeature(String[] feature) {
		String[] copiedFeature = new String[feature.length];
		for (int i=0; i<feature.length; i++) {
			copiedFeature[i] = feature[i];
		}
		return copiedFeature;
	}

	public Gene(ArrayList<String[]> myChildren) {
		features = myChildren;
	}			

	public ArrayList<String[]> getFeatures() {
		return features;
	}
	
	public void addFeature(String[] oneFeature) {
		features.add(oneFeature);
	}
}
