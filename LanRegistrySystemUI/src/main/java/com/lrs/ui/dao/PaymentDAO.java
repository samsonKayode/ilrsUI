package com.lrs.ui.dao;

import com.lrs.ui.model.ActionResult;
import com.lrs.ui.paystack.InitializeTransactionRequest;

public interface PaymentDAO {
	
	public String doPayStack(InitializeTransactionRequest transactionRequest);
	
	public ActionResult checkVerificationLink(String reference);

}
