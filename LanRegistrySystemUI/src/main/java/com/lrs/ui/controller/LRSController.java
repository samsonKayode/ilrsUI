package com.lrs.ui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
import com.lrs.ui.model.InitializeTransactionResponse;
import com.lrs.ui.model.PaymentModel;

@Controller
@RequestMapping("/lrs")
public class LRSController {
	
	@Autowired
	Gson gson;
	
	@Value("${backend.base}")
	private String baseURL;
	
	InitializeTransactionResponse initializeTransactionResponse = null;
	
	private static final int STATUS_CODE_OK = 200;
	
	
	
	@GetMapping("/home")
	public String showHome() {
		
		return "users/homepage";
	}
	
	@GetMapping("/buytoken")
	public String buyToken() {
		
		return "users/buytoken";
	}
	
	@PostMapping("/makepayment")
	public String moveToPayStack(@ModelAttribute ("payment") PaymentModel paymentModel) {
		
		String authorization_url=null;
		
		try {
			Gson gson = new Gson();

            StringEntity postingString = new StringEntity(gson.toJson(paymentModel));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(baseURL+"lrs/payment/makepayment");
            post.setEntity(postingString);
            
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Error Occurred while initializing transaction");
            }
            ObjectMapper mapper = new ObjectMapper();

            initializeTransactionResponse = mapper.readValue(result.toString(), InitializeTransactionResponse.class);
            
            authorization_url = initializeTransactionResponse.getData().getAuthorization_url();
            
            System.out.println("AUTH URL====>>>"+authorization_url);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new Exception("Failure initializaing paystack transaction");
        }

		return authorization_url;
	}

}
