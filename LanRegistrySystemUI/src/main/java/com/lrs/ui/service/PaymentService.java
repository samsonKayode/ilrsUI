package com.lrs.ui.service;

import com.lrs.ui.model.ActionResult;
import com.lrs.ui.paystack.InitializeTransactionRequest;

public interface PaymentService {
	
	public String doPayStack(InitializeTransactionRequest transactionRequest);
	
	public ActionResult checkVerificationLink(String reference);

}
