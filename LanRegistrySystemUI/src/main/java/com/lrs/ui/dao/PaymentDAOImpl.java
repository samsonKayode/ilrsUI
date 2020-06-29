package com.lrs.ui.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lrs.ui.model.ActionResult;
import com.lrs.ui.paystack.InitializeTransactionRequest;
import com.lrs.ui.paystack.InitializeTransactionResponse;

@Repository
public class PaymentDAOImpl implements PaymentDAO {
	
	@Value("${paystack.testkey}")
	private String secretKey;
	
	@Value("${backend.base}")
	String baseURL;
	
	private static final int STATUS_CODE_OK = 200;

	
	InitializeTransactionResponse initializeTransactionResponse = null;

	@Override
	public String doPayStack(InitializeTransactionRequest transactionRequest) {
		
		String authorization_url=null;
		
		try {
			
			Gson gson = new Gson();
			
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

            }else {
            	
            	System.out.println("Error reading paystack data here====>>");;
            	
            	authorization_url="/lrs/error";
            	
            }
	        
            ObjectMapper mapper = new ObjectMapper();

            initializeTransactionResponse = mapper.readValue(result.toString(), InitializeTransactionResponse.class);
            
            authorization_url = initializeTransactionResponse.getData().getAuthorization_url();
			
		}
		
		catch(Exception nn) {
			
			System.out.println("Exception caught here:=======>>>>"+nn);
			
			authorization_url="/lrs/error";
		}
		
		return authorization_url;
		
	}

	@Override
	public ActionResult checkVerificationLink(String reference) {
		
		ActionResult actionResult=null;
		
		try {
			
			  CloseableHttpClient httpclient = HttpClients.createDefault();
		      
		      HttpGet httpget = new HttpGet(baseURL + "/lrs/payment/verifytransactionwithreference/"+reference);
		      
		      StringBuilder result = new StringBuilder();
		      
		      HttpResponse httpresponse = httpclient.execute(httpget);
		
		if(httpresponse.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
	    	  
	    	  BufferedReader rd = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));

              String line;
              while ((line = rd.readLine()) != null) {
                  result.append(line);
              }
              
              ObjectMapper mapper = new ObjectMapper();

              actionResult = mapper.readValue(result.toString(), ActionResult.class); 
             
	    	  
	      }else {
	    	  
	    	  //status coke not OK...
	    	  actionResult = new ActionResult(2, "server_error", System.currentTimeMillis());
	    	  
	      }

		
	}catch(Exception nn) {
		
		System.out.println("ERROR COMPLETING REQUEST======>>>"+nn);
		actionResult = new ActionResult(3, "severe error", System.currentTimeMillis());
	}
		
		return actionResult;
	}

}
