package com.reality.form;

public class DoneThings {
	// やったこと
	private String things;
	// 〇△×
	private String completeness;
	// 改善
	private String improvement;
	
	public DoneThings() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DoneThings(String things, String completeness, String improvement) {
		super();
		this.things = things;
		this.completeness = completeness;
		this.improvement = improvement;
	}
	
	public String getThings() {
		return things;
	}
	public void setThings(String things) {
		this.things = things;
	}
	public String getCompleteness() {
		return completeness;
	}
	public void setCompleteness(String completeness) {
		this.completeness = completeness;
	}
	public String getImprovement() {
		return improvement;
	}
	public void setImprovement(String improvement) {
		this.improvement = improvement;
	}
	
	
}
