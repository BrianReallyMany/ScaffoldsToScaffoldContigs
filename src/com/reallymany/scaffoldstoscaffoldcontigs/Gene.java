package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;

public class Gene {
	ArrayList<String[]> features;	
	
	public Gene() {
		features = new ArrayList<String[]>();
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
