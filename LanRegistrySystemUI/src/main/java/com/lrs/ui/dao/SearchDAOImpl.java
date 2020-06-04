package com.lrs.ui.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrs.ui.model.ActionResult;
import com.lrs.ui.model.LandEntity;


@Repository
public class SearchDAOImpl implements SearchDAO {
	
	@Value("${backend.base}")
	String baseURL;
	
	private static final int STATUS_CODE_OK = 200;

	@Override
	public ActionResult performSearch(String title_id, String access_code) {
		// TODO Auto-generated method stub
		
		ActionResult actionResult=null;
		
		try {
			
		      CloseableHttpClient httpclient = HttpClients.createDefault();
		      
		      HttpGet httpget = new HttpGet(baseURL + "/lrs/verify/data/list/"+title_id+"/"+access_code);
		      
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
		    	  
		    	  System.out.println("ACTION RESULT MESSAGE========>>>"+actionResult.getMessage());
		    	  
		      }

			
		}catch(Exception nn) {
			
			System.out.println("ERROR COMPLETING REQUEST======>>>"+nn);
		}
		
		return actionResult;
	}
	
	
	

	@Override
	public LandEntity getLandDetails(String title_id) {
		
		LandEntity landEntity = null;
		
		try {
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
		      
		      HttpGet httpget = new HttpGet(baseURL + "/lrs/lands/data/list/"+title_id);
		      
		      StringBuilder result = new StringBuilder();
		      
		      HttpResponse httpresponse = httpclient.execute(httpget);
		      
		      if(httpresponse.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
		    	  
		    	  BufferedReader rd = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));

	                String line;
	                while ((line = rd.readLine()) != null) {
	                    result.append(line);
	                }
	                
	                ObjectMapper mapper = new ObjectMapper();

	                landEntity = mapper.readValue(result.toString(), LandEntity.class); 
	               
		    	  
		      }else {
		    	  
		    	  //status coke not OK...
		    	  landEntity=null;
		    	  System.out.println("Error completing request");
		    	  
		      }
			
		}catch(Exception nn) {
			
			System.out.println("ERROR COMPLETING REQUEST======>>>"+nn);
		}
		
		
		return landEntity;
	}
	
}
