package com.reality.form;

import java.util.ArrayList;
import java.util.Map;

public class DailyReportForm {

	// やったこと
	private DoneThings doneThings;
	// 所感
	private String reflection;

	private ArrayList<DoneThings> doneThingsList = new ArrayList<>();

	public DailyReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyReportForm(ArrayList<DoneThings> doneThingsList, String reflection) {
		super();
		this.doneThingsList = doneThingsList;
		this.reflection = reflection;
	}

	public DoneThings getDoneThings() {
		return doneThings;
	}

	public void setDoneThings(DoneThings doneThings) {
		this.doneThings = doneThings;
		if (doneThings.getThings() != null) {
			setDoneThingsList(doneThings);
		}		
	}

	public ArrayList<DoneThings> getDoneThingsList() {
		return doneThingsList;
	}

	public void setDoneThingsList(ArrayList<DoneThings> doneThingsList) {
		this.doneThingsList = doneThingsList;
	}
	
	public void setDoneThingsList(DoneThings doneThings) {
		String[] thingsArr = doneThings.getThings().split(",");
		String[] comArr = doneThings.getCompleteness().split(",");
		String[] impArr = doneThings.getImprovement().split(",");
		
		for (int i = 0; i < thingsArr.length; i++) {
			try {
				this.doneThingsList.add(new DoneThings(thingsArr[i], comArr[i], impArr[i]));
			} catch (Exception e) {
				// TODO: handle exception
				this.doneThingsList.add(new DoneThings(thingsArr[i], comArr[i], ""));
			}
		}
	}

	public String getReflection() {
		return reflection;
	}

	public void setReflection(String reflection) {
		this.reflection = reflection;
	}

}
