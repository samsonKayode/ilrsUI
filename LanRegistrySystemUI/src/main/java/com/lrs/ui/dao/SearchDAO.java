package com.lrs.ui.dao;

import com.lrs.ui.model.ActionResult;
import com.lrs.ui.model.LandEntity;

public interface SearchDAO {
	
	public ActionResult performSearch(String title_id, String access_code);
	
	public LandEntity getLandDetails(String title_id);

}
