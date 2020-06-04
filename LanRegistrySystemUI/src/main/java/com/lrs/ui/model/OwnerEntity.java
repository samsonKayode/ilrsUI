package com.lrs.ui.model;

import java.io.Serializable;

public class OwnerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String firstname;
	
	private String lastname;
	
	private String phone;
	
	private String email;
	
	private String address;
	
	private String state;
	
	
	
	public OwnerEntity() {
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
