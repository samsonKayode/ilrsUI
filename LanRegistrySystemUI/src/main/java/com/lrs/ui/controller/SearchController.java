package com.lrs.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lrs.ui.model.ActionResult;
import com.lrs.ui.model.LandEntity;
import com.lrs.ui.model.SearchModel;
import com.lrs.ui.service.SearchService;

@Controller
@RequestMapping("/lrs")
public class SearchController {

	
	@Autowired
	SearchService searchService;
	
	@Value("${server_error_message}")
	String server_error_message;
	
	@Value("${invalid_error_message}")
	String invalid_error_message;	
	
	
	//LandEntity..
	
	@ModelAttribute("landEntity")
	public LandEntity landEntity() {
		return new LandEntity();
	}
	
	
	@ModelAttribute("searchModel")
	public SearchModel searchModel() {
		return new SearchModel();
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String showSearch(Model model) {
		
		return "search/search-form";
	}
	
	@RequestMapping(value="/search/verify", method= RequestMethod.POST)
	public String verifyData(@Valid @ModelAttribute("searchModel") SearchModel searchModel, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		String response_page=null;
		
		if(bindingResult.hasErrors()) {
			
			//validation error kicked in..
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchModel", bindingResult);
			redirectAttributes.addFlashAttribute("searchModel", searchModel);
			
			return "redirect:/lrs/search";
		}else {
			//performing search service..
			
			ActionResult AR = searchService.performSearch(searchModel.getTitle_id(), searchModel.getAccess_code());
			searchModel.setMessage(AR.getMessage());
			searchModel.setMessageID(AR.getStatus());
			
			if(AR.getMessage().equals("server_error")) {
				
				searchModel.setMessageText(server_error_message);
				
				redirectAttributes.addFlashAttribute("messageID", searchModel.getMessageID());
				redirectAttributes.addFlashAttribute("messageText", searchModel.getMessageText());
				redirectAttributes.addFlashAttribute("searchModel", searchModel);
				
				response_page ="redirect:/lrs/search";
			}
			
			if(AR.getMessage().equals("error")) {
				
				
				searchModel.setMessageText(invalid_error_message);
				redirectAttributes.addFlashAttribute("messageID", searchModel.getMessageID());
				redirectAttributes.addFlashAttribute("messageText", searchModel.getMessageText());
				
				redirectAttributes.addFlashAttribute("searchModel", searchModel);
				
				response_page ="redirect:/lrs/search";
			}
			
			//success...
			
			if(AR.getMessage().equals("success")) {
				
				// call get land details service...
				
				LandEntity landEntity = searchService.getLandDetails(searchModel.getTitle_id());
				
				
				response_page ="redirect:/lrs/home";
			}
			
			System.out.println("SEARCH MODEL MESSAGE ====>"+searchModel.getMessage());
			System.out.println("SEARCH MODEL MESSAGE TEXT ====>"+searchModel.getMessageText());
			
			
		}
		
		return response_page;
	}

}
