package com.lrs.ui.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentModel {

	private int id;
	
	private String title_id;
	
	private String access_code;
	
	private Date date;
	
	private String payer;
	
	public PaymentModel() {
		
	}

	public PaymentModel(String title_id, String access_code, Date date, String payer) {
		this.title_id = title_id;
		this.access_code = access_code;
		this.date = date;
		this.payer = payer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}

	public String getAccess_code() {
		return access_code;
	}

	public void setAccess_code(String access_code) {
		this.access_code = access_code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

}
