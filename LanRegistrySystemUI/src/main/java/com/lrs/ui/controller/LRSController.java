package com.lrs.ui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lrs.ui.paystack.InitializeTransactionRequest;
import com.lrs.ui.paystack.InitializeTransactionResponse;
import com.lrs.ui.paystack.RandomReference;

@Controller
@RequestMapping("/lrs")
public class LRSController {
	
	@Autowired
	Gson gson;
	
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
	
	private static final int STATUS_CODE_OK = 200;
	
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
	
	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	public String moveToPayStack(@Valid @ModelAttribute ("transactionRequest") InitializeTransactionRequest transactionRequest, 
			BindingResult theBind, RedirectAttributes redirectAttributes) {
		
		String authorization_url=null;
		
		//theModel.addAttribute("transaction_message",transaction_message);
		
		if(theBind.hasErrors() ) {
			
			System.out.println("BIND 1 ERRORS====>"+theBind.getAllErrors());
			
			//model.addAttribute("payment",transactionRequest);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transactionRequest", theBind);
			redirectAttributes.addFlashAttribute("transactionRequest", transactionRequest);
			 //return "users/buytoken";
			
			return "redirect:/lrs/buytoken";
			
		}else {
			try {
			Gson gson = new Gson();
			
			String refNo=randomReference.getAlphaNumericString(20);
            
			transactionRequest.setAmount(510000);
			
			transactionRequest.setReference(refNo);
			
			transactionRequest.setCallback_url(callbackURL+refNo+"/"+transactionRequest.getEmail()
			+"/"+transactionRequest.getMetadata().getTitle_id());

            StringEntity postingString = new StringEntity(gson.toJson(transactionRequest));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer "+secretKey);
            
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);
            
            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Error Occurred while initializing transaction. SERVER RESPONSE CODE===>>> "+ response.getStatusLine().getStatusCode());
            }
            ObjectMapper mapper = new ObjectMapper();

            initializeTransactionResponse = mapper.readValue(result.toString(), InitializeTransactionResponse.class);
            
            authorization_url = initializeTransactionResponse.getData().getAuthorization_url();
            
            System.out.println("AUTH URL====>>>"+authorization_url);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

		
	}
		return "redirect:"+authorization_url;
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

}
