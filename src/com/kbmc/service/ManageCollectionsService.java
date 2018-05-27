package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Arrangement;
import com.kbmc.model.Collections;
import com.kbmc.model.ServiceResponse;

/**
 * Manage Collections Service Interface.
 * 
 * @author ZONE24X7
 * 
 */

public interface ManageCollectionsService {
	public List<Collections> getCollections(String kohlsRestAPIUrl);

	public ModelAndView createCollection(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView updateCollection(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ServiceResponse deleteCollection(Long collectionId,
			String kohlsRestAPIUrl);

	public JSONObject getCollectionDetailsById(String kohlsRestAPIUrl, Long id);

	public List<Arrangement> getArrangementsByCollectionId(Long collectionId,
			String kohlsRestAPIUrl);

	public List<String> getCollectionNames(String query, String kohlsRestAPIUrl);
	
	public JSONObject addMarkdownProduct(String kohlsRestAPIUrl,String jsonObject);
	
	public JSONObject getItemDetailsByItemId(String kohlsRestAPIUrl,Long itemId,String itemType);

}
