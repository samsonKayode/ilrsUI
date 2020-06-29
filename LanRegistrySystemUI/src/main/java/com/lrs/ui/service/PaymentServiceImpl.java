package com.lrs.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrs.ui.dao.PaymentDAO;
import com.lrs.ui.model.ActionResult;
import com.lrs.ui.paystack.InitializeTransactionRequest;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentDAO paymentDAO;

	@Override
	public String doPayStack(InitializeTransactionRequest transactionRequest) {
		

		String aURL = paymentDAO.doPayStack(transactionRequest);
		
		return aURL;
	}

	@Override
	public ActionResult checkVerificationLink(String reference) {
		
		
		ActionResult AR = paymentDAO.checkVerificationLink(reference);
		
		return AR;
	}

}
