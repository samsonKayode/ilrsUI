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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lrs.ui.paystack.InitializeTransactionRequest;
import com.lrs.ui.paystack.InitializeTransactionResponse;
import com.lrs.ui.paystack.MetaData;
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
	
	InitializeTransactionResponse initializeTransactionResponse = null;
	
	private static final int STATUS_CODE_OK = 200;
	
	RandomReference randomReference = new RandomReference();
	
	
	
	@GetMapping("/home")
	public String showHome() {
		
		return "users/homepage";
	}
	
	@RequestMapping("/buytoken")
	public String buyToken(Model theModel) {

		InitializeTransactionRequest trequest = new InitializeTransactionRequest();
		
		theModel.addAttribute("payment", trequest);
		
		return "users/buytoken";
	}
	
	@PostMapping("/makepayment")
	public String moveToPayStack(@Valid @ModelAttribute ("payment") InitializeTransactionRequest transactionRequest, BindingResult theBind, Model theModel) {
	//public String moveToPayStack(@Valid InitializeTransactionRequest transactionRequest, BindingResult theBind, Model theModel) {
		
		//MetaData meta = transactionRequest.getMetadata();
		
		//theModel.addAttribute("phone", meta.getPhone());
		
		String authorization_url=null;
		
		if(theBind.hasErrors()) {
			
			System.out.println("I GOT HERE BINDING ERROR");
			
			 return "users/buytoken";
			
		}else {
		
		
		try {
			Gson gson = new Gson();
			
			String refNo=randomReference.getAlphaNumericString(20);
            
			transactionRequest.setAmount(510000);
			
			transactionRequest.setReference(refNo);
			
			transactionRequest.setCallback_url(callbackURL+refNo+"/"+transactionRequest.getEmail()+"/"+transactionRequest.getMetadata().getTitle_id());

            StringEntity postingString = new StringEntity(gson.toJson(transactionRequest));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer "+secretKey);
            
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);
            
            
            
            System.out.println("EMAIL===>>>"+transactionRequest.getEmail());
            System.out.println("CALLBACK===>>>"+transactionRequest.getCallback_url());
            System.out.println("REFRENCE NUMBER===>>>"+transactionRequest.getReference());
            System.out.println("AMOUNT===>>>"+transactionRequest.getAmount());
            System.out.println("TITLE ID===>>>"+transactionRequest.getMetadata().getTitle_id());
            
            System.out.println("PHONE NO===>>>"+transactionRequest.getMetadata().getPhone());
            
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
            //throw new Exception("Failure initializaing paystack transaction");
        }

		
	}
		return "redirect:"+authorization_url;
	}

}
