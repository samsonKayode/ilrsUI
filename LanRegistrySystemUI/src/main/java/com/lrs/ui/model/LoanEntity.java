package com.lrs.ui.model;


import java.util.Date;


public class LoanEntity {
	
	private int id;
	
	private String pledged_to;
	
	private Date date;
	
	private String duration;
	
	public LoanEntity() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPledged_to() {
		return pledged_to;
	}

	public void setPledged_to(String pledged_to) {
		this.pledged_to = pledged_to;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	
}
