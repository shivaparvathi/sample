package com.kbmc.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kbmc.model.Arrangement;
import com.kbmc.model.ServiceResponse;

/**
 * Manage Arrangements Service class.
 * 
 * @author ZONE24X7
 */

public interface ManageArrangementsService {

	public List<Arrangement> getArrangements(String kohlsRestAPIUrl);

	public JSONObject createArrangement(Arrangement arrangement,
			String kohlsRestAPIUrl);

	public ServiceResponse updateArrangement(String arrangementInfo,
			String kohlsRestAPIUrl);

	public Arrangement getArrangementDetailsById(Long arrangementId,
			String kohlsRestAPIUrl);

	public ServiceResponse deleteArrangement(Long arrangementId,
			String kohlsRestAPIUrl);

	public ServiceResponse saveArrangement(String arrangementInfo,
			String kohlsRestAPIUrl);

	public JSONArray getItemsInArrangement(Long arrangementIdString,
			String kohlsRestAPIUrl);

	public JSONObject getProducts(String kohlsRestAPIUrl, Integer limit,
			Integer offset);

	public JSONObject getCoupons(String kohlsRestAPIUrl, Integer limit,
			Integer offset);

	public JSONObject getCash(String kohlsRestAPIUrl, Integer limit,
			Integer offset);
}
