package com.lrs.ui.model;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchModel {
	
	@Size(min=2, max=30, message="Enter valid title number")
	private String title_id;
	
	@Size(min=5, max=40, message="Enter valid reference/access code")
	private String access_code;
	
	private String message;
	
	private String messageText;
	
	private int messageID;

	
	public SearchModel() {
		
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	
	

}
