package com.reallymany.scaffoldstoscaffoldcontigs;

import java.util.ArrayList;

public class GeneProcessor {
	Gene geneBeingProcessed;
	ArrayList<Gene> genesToWrite;
	Scaffold currentScaffold;
	ArrayList<Scaffold> allScaffolds;

	public GeneProcessor(Gene inputGene, ArrayList<Scaffold> scaffolds) {
		geneBeingProcessed = inputGene;
		genesToWrite = new ArrayList<Gene>();
		allScaffolds = scaffolds;		
	}

}
