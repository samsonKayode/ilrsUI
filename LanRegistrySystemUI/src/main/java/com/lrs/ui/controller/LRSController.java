package com.lrs.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lrs")
public class LRSController {
	
	@GetMapping("/home")
	public String showHome() {
		
		return "users/homepage";
	}

}
