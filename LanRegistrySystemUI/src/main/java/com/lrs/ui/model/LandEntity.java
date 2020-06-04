package com.lrs.ui.model;


import java.io.Serializable;
import java.util.List;


public class LandEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String title_id;
	
	private String location;
	
	private String size;
	
	private String description;
	
	private OwnerEntity ownerEntity;
	
	private List<LoanEntity> loanEntity;

	
	public LandEntity() {
		
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OwnerEntity getOwnerEntity() {
		return ownerEntity;
	}

	public void setOwnerEntity(OwnerEntity ownerEntity) {
		this.ownerEntity = ownerEntity;
	}
	
	public List<LoanEntity> getLoanEntity() {
		return loanEntity;
	}

	public void setLoanEntity(List<LoanEntity> loanEntity) {
		this.loanEntity = loanEntity;
	}
	
	/*

	public void addOwner(OwnerEntity tempOwner) {
		
		if(ownerEntity==null) {
			ownerEntity = null;
		}
		ownerEntity=tempOwner;
		tempOwner.setLandEntity(this);
	}
	
	public void addLoans(LoanEntity tempLoan) {
		
		if(loanEntity==null) {
			loanEntity = new HashSet<>();
		}
		loanEntity.add(tempLoan);
		tempLoan.setLandEntity(this);		
	}
	
	*/
	
	
}
