package com.lrs.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.lrs.ui.paystack.InitializeTransactionRequest;
import com.lrs.ui.paystack.InitializeTransactionResponse;
import com.lrs.ui.paystack.RandomReference;
import com.lrs.ui.service.PaymentService;

@Controller
@RequestMapping("/lrs")
public class LRSController {
	
	@Autowired
	Gson gson;
	
	@Autowired
	PaymentService paymentService;
	
	@Value("${backend.base}")
	private String baseURL;
	
	@Value("${callback.url}")
	private String callbackURL;
	
	@Value("${paystack.testkey}")
	private String secretKey;
	
	@Value("${mybase.URL}")
	private String myBaseURL;
	
	//@Value("{transaction_message}")
	//private String transaction_message;
	
	InitializeTransactionResponse initializeTransactionResponse = null;
	
	//private static final int STATUS_CODE_OK = 200;
	
	RandomReference randomReference = new RandomReference();
	
	
	
	@GetMapping("/home")
	public String showHome() {
		
		return "users/homepage";
	}
	
	@ModelAttribute("transactionRequest")
    public InitializeTransactionRequest transactionRequest() {
        return new InitializeTransactionRequest();
    }
	
	@RequestMapping(value="/buytoken", method = RequestMethod.GET)
	public String buyToken(Model theModel) {
		
		theModel.addAttribute("transaction_message","transaction_message");
		
		return "users/buytoken";
	}
	
	//show Error page..
	
	@RequestMapping(value="/failed", method = RequestMethod.GET)
	public String errorpage() {
		
		return "users/paymenterror";
	}
	
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public String oktransaction() {
		
		return "users/paymentsuccess";
	}
	
	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	public String moveToPayStack(@Valid @ModelAttribute ("transactionRequest") InitializeTransactionRequest transactionRequest, 
			BindingResult theBind, RedirectAttributes redirectAttributes) {
		
		String authorization_url=null;
		
		String refNo;
		
		if(theBind.hasErrors() ) {
		
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transactionRequest", theBind);
			redirectAttributes.addFlashAttribute("transactionRequest", transactionRequest);
			
			return "redirect:/lrs/buytoken";
			
		}else {
			
			refNo=randomReference.getAlphaNumericString(20);
			transactionRequest.setAmount(510000);
			transactionRequest.setReference(refNo);
			transactionRequest.setCallback_url(callbackURL+refNo+"/"+transactionRequest.getEmail()
			+"/"+transactionRequest.getMetadata().getTitle_id());
			
			authorization_url = paymentService.doPayStack(transactionRequest);
		}
		
		return "redirect:"+authorization_url;
		
	}
	

}
