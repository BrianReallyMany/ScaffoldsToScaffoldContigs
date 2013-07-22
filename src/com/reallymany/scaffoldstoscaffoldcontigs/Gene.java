package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;

public class Gene {
	Scaffold enclosingScaffold;
	ScaffoldContig enclosingScaffoldContig;	
	ArrayList<String[]> features;
	
	
	public Gene() {
		features = new ArrayList<String[]>();
	}

	public Gene(ArrayList<String[]> myChildren) {
		features = myChildren;
	}	
		
	public Scaffold getEnclosingScaffold() throws ScaffoldContigException {
		if (enclosingScaffold != null) {
			return enclosingScaffold;
		} else {
			throw new ScaffoldContigException("Scaffold enclosingScaffold == null");
		}
	}

	public void setEnclosingScaffold(Scaffold enclosingScaffold) {
		this.enclosingScaffold = enclosingScaffold;
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

	
	
	
	

}
