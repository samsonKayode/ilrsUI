package com.lrs.ui.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        
	        System.out.println("I AM HERE OOOOOO!!!!!!>>>>>>"+statusCode);
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	
	        	System.out.println("I GOT HER TOO BUT NO SHOW");
	            return "error/error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error/error-500";
	        }
	    }
	    return "error/error-base";
	}
	
	@Override
    public String getErrorPath() {
        return "/error";
    }

}
