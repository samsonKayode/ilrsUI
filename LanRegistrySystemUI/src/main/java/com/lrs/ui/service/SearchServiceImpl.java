package com.lrs.ui.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrs.ui.dao.SearchDAO;
import com.lrs.ui.model.ActionResult;
import com.lrs.ui.model.LandEntity;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	SearchDAO searchDAO;
	

	@Override
	public ActionResult performSearch(String title_id, String access_code) {
		
		ActionResult actionResult = searchDAO.performSearch(title_id, access_code);
		
		return actionResult;
	}


	@Override
	public LandEntity getLandDetails(String title_id) {
		
		LandEntity landEntity = searchDAO.getLandDetails(title_id);
		
		return landEntity;
	}

}
