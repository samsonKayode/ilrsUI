package com.lrs.ui.paystack;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaData {
	
	@Size(min=5, max=20)
	@NotBlank(message = "Title number is mandatory")
	@NotEmpty(message = "Title number is mandatory")
	private String title_id;

	@Size(min=10, max=15)
	@NotBlank(message = "Phone number is mandatory")
	@NotEmpty(message = "Phone number is mandatory")
	private String phone;
	
	public MetaData() {
		
	}

	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
