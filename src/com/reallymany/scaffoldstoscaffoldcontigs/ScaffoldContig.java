package com.reallymany.scaffoldstoscaffoldcontigs;

public class ScaffoldContig {
	public String name;
	public int begin, end;
	
	public ScaffoldContig(String n, int b, int e) {
		this.name = n;
		this.begin = b;
		this.end = e;
	}

	public String getName() {
		return name;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}
}
