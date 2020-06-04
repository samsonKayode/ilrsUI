package com.lrs.ui.service;

import com.lrs.ui.model.ActionResult;
import com.lrs.ui.model.LandEntity;

public interface SearchService {
	
	public ActionResult performSearch(String title_id, String access_code);
	
	public LandEntity getLandDetails(String title_id);


}
