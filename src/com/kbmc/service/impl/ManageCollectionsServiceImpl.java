package com.kbmc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Arrangement;
import com.kbmc.model.Collections;
import com.kbmc.model.ServiceResponse;
import com.kbmc.model.Status;
import com.kbmc.service.ManageCollectionsService;
import com.kbmc.model.GenericException;

/**
 * Manage Collections implementation class.
 * 
 * @author ZONE24X7
 * 
 */
public class ManageCollectionsServiceImpl implements ManageCollectionsService {

	private static final Logger log = Logger
			.getLogger(ManageCollectionsServiceImpl.class);

	/**
	 * Get the Collection List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Collection> Object
	 */
	@Override
	@SuppressWarnings("deprecation")
	public List<Collections> getCollections(String kohlsRestAPIUrl) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

	
		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonDataArray = new JSONArray();
		List<Collections> collections = new ArrayList<Collections>();
		Status status;
		try {
			// Calling KohlsRestAPI getCollections service
			
			ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
				+ "/collection/getCollections", HttpMethod.GET, entity,
				String.class);	
			jsonObject = (JSONObject) parser.parse(result.getBody());
			status= Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) jsonObject.get("data");
					if(jsonDataArray.size()!=0){
						for (Object jsonArray:jsonDataArray) {
							jsonObject = (JSONObject) jsonArray;
							Collections collection = new Collections();
							collection.setCollectionId((Long) jsonObject
									.get("collectionId"));
							collection.setName((String) jsonObject.get("name"));
							collection.setDescription((String) jsonObject
									.get("description"));
							collection.setCollectionType((String) jsonObject
									.get("collectionType"));
							Date startDate;
							Date endDate;
							if (jsonObject.get("startDate") != null) {
								long utc_startDate = (Long) jsonObject.get("startDate");
								startDate = new Date(utc_startDate * 1000L);
							} else {
								Date date = new Date();
								long startdatevalue = date.getTime();
								startDate = new Date(startdatevalue);
							}
							if (jsonObject.get("endDate") != null) {
								long utc_endDate = (Long) jsonObject.get("endDate");
								endDate = new Date(utc_endDate * 1000L);
							} else {
								Date date = new Date();
								long enddatevalue = date.getTime();
								endDate = new Date(enddatevalue);
							}
							collection.setStartDate(startDate);
							collection.setEndDate(endDate);
							collection.setActive((Boolean) jsonObject.get("active"));
							JSONArray arrangementsDataArray = new JSONArray();
							ArrayList<Long> arrangementId = new ArrayList<Long>();
							arrangementsDataArray = (JSONArray) jsonObject.get("arrangements");
							for (Object arrangementsArray:arrangementsDataArray) {
								if ((Long)arrangementsArray == null) {
									arrangementId.add(0L);
								} else {
									long arrangement_id = (Long) arrangementsArray;
									arrangementId.add(arrangement_id);
								}
							}
							collection.setArrangementId(arrangementId);
							JSONArray channelDataArray = new JSONArray();
							ArrayList<String> channelNameList = new ArrayList<String>();
							channelDataArray = (JSONArray) jsonObject.get("channels");
							for (Object channelArray:channelDataArray) {
								if ((String)channelArray == null) {
									channelNameList.add(null);
								} else {
									String channel_name = (String) channelArray;
									channelNameList.add(channel_name);
								}
							}
							collection.setChannelName(channelNameList);
							collections.add(collection);
						}
					}
				case FAILURE:
					break;
				case NO_DATA:
					break;
				default:
					break;
			}
		} catch (Exception exception) {
			log.error("getCollections " + exception);
			throw new GenericException(exception.getMessage());
		}
		return collections;
	}

	/**
	 * Add/Create Collection implementation
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * @return ModelAndView Object
	 */

	@Override
	@SuppressWarnings("unchecked")
	public ModelAndView createCollection(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		String name = request.getParameter("name").trim();
		String description = request.getParameter("description");
		String collectionType = request.getParameter("collectionType");
		String startdate = request.getParameter("startDate");
		String enddate = request.getParameter("endDate");
		Boolean active = Boolean.parseBoolean(request.getParameter("Active"));
		String[] arrangementsArray = request.getParameterValues("arrangements");
		ArrayList<Long> arrangementId = new ArrayList<Long>();
		for (String selectedArrangementId:arrangementsArray) {
			arrangementId.add(Long.parseLong(selectedArrangementId));
		}
		ModelAndView mav = new ModelAndView("walletmanager/collections");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		Status status;
		Long startDate = null;
		Long endDate = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm");
			SimpleDateFormat newFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm");
			startDate = formatter.parse(startdate).getTime() / 1000L;
			endDate = formatter.parse(enddate).getTime() / 1000L;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("description", description);
			map.put("collectionType", collectionType);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("active", active);
			map.put("arrangements", arrangementId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity reqEntity = new HttpEntity(reqJson, headers);

			// Calling KohlsRestAPI createCollection service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/collection/addCollection",
					HttpMethod.POST, reqEntity, String.class);

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					mav.addObject("successMessage","Collection Created SuccessFully");
					break;
				case FAILURE:
					mav.addObject("createfailuremsg", jsonObject.get("message"));
					break;
				case RECORD_ALREADY_EXISTS:
				     mav.addObject("successMessage","Collection with same name already exists");
				default:
					break;
			}

		} catch (Exception exception) {
			log.error("createCollection " + exception);
			throw new GenericException(exception.getMessage());
		}
		List<Collections> collections = getCollections(kohlsRestAPIUrl);
		mav.addObject("collections", collections);
		return mav;
	}

	/**
	 * Update collection service implementation
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * @return ModelAndView Object
	 */

	@Override
	public ModelAndView updateCollection(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		Long collectionId = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name").trim();
		String description = request.getParameter("description");
		String collectionType = request.getParameter("collectionType");
		String startdate = request.getParameter("startDate");
		String enddate = request.getParameter("endDate");
		String activeValue = request.getParameter("Active");
		boolean active=false;
		if(activeValue.equalsIgnoreCase("enabled")){
			 active=true;
		}
		String[] arrangementsArray = request.getParameterValues("arrangements");
		ArrayList<Long> arrangementId = new ArrayList<Long>();
		for (String selectedArrangementId:arrangementsArray) {
			arrangementId.add(Long.parseLong(selectedArrangementId));
		}
		ModelAndView mav = new ModelAndView("walletmanager/collections");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		Status status = null;
		Long startDate = null;
		Long endDate = null;
		try {

			SimpleDateFormat formatter = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm");
			SimpleDateFormat newFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm");
			startDate = formatter.parse(startdate).getTime() / 1000L;
			endDate = formatter.parse(enddate).getTime() / 1000L;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("collectionId", collectionId);
			map.put("name", name);
			map.put("description", description);
			map.put("collectionType", collectionType);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("active", active);
			map.put("arrangements", arrangementId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity reqEntity = new HttpEntity(reqJson, headers);

			// Calling KohlsRestAPI updateCollection service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/collection/updateCollection",
					HttpMethod.PUT, reqEntity, String.class);

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					mav.addObject("successMessage","Collection updated SuccessFully");
					break;
				case FAILURE:
					mav.addObject("updatefailuremsg", true);
					mav.addObject("update", "update");
					break;
				default:
					break;
			}

		} catch (Exception exception) {
			log.error("updateCollection " + exception);
			throw new GenericException(exception.getMessage());
		}
		List<Collections> collections = getCollections(kohlsRestAPIUrl);
		mav.addObject("collections", collections);
		return mav;
	}

	/**
	 * GetCollection details by passing collectionId
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,id
	 * @return Collection Object
	 */

	@Override
	public JSONObject getCollectionDetailsById(String kohlsRestAPIUrl, Long id) {
		  Long collectionId = id;
		  
		  Collections collection = new Collections();
		  List<Collections> collectionslist = getCollections(kohlsRestAPIUrl);
		  for (Collections collections : collectionslist) {
		   if (collections.getCollectionId() == collectionId) {
		    collection = collections;
		   }
		  }
		  ArrayList<String> channelNameList = new ArrayList<String>();
		  for (int j = 0; j < collection.getChannelName().size(); j++) {
		   if (collection.getChannelName().get(j) == null) {
		    channelNameList.add(null);
		    collection.setChannelName(channelNameList);

		   } else {
		    channelNameList.addAll(collection.getChannelName());
		   }
		  }
		  List<Arrangement> arrangements = getArrangementsByCollectionId(collectionId,kohlsRestAPIUrl);
		  JSONObject json = new JSONObject();
		  json.put("collection", collection);
		  json.put("arrangements", arrangements);
		  return json;
		 }

	/**
	 * Delete collection service implementation
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,collectionId
	 * @return ServiceResponse object
	 */
	@Override
	public ServiceResponse deleteCollection(Long collectionId,
			String kohlsRestAPIUrl) {

		Long id = collectionId;
		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		String status = null;
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity reqEntity = new HttpEntity(headers);

		// Calling KohlsRestAPI deleteCollection service
		ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
				+ "/collection/deleteCollection?collectionId=" + id,
				HttpMethod.DELETE, reqEntity, String.class);

		try {

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = (String) jsonObject.get("status");
			serviceResponse.setMessage(status);
		} catch (Exception exception) {
			log.error("deleteCollection " + exception);
			throw new GenericException(exception.getMessage());
		}
		return serviceResponse;

	}

	/**
	 * Get Arrangement details by passing collectionId
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,collectionId
	 * @return List<Arrangement> object
	 */

	@Override
	public List<Arrangement> getArrangementsByCollectionId(Long collectionId,
			String kohlsRestAPIUrl) {

		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collectionId", collectionId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		HttpEntity reqEntity = new HttpEntity(reqJson, headers);
		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonDataArray = new JSONArray();
		Status status;

		List<Arrangement> arrangements = new ArrayList<Arrangement>();
		try {
			// Calling KohlsRestAPI getArrangementsByCollectionId service
			ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
					+ "/arrangement/getArrangementsByCollectionId?collectionId="
					+ collectionId, HttpMethod.GET, reqEntity, String.class);
			jsonObject = (JSONObject) parser.parse(result.getBody());
			
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			
			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) jsonObject.get("data");
					if(jsonDataArray.size()!=0){
						for (Object jsonArray:jsonDataArray) {
							jsonObject = (JSONObject) jsonArray;
							Arrangement arrangement = new Arrangement();

							arrangement.setActive((Boolean) jsonObject.get("active"));
							arrangement.setArrangementId((Long) jsonObject
									.get("arrangementId"));
							arrangement.setName((String) jsonObject.get("name"));

							String locationKey;
							if (jsonObject.get("locationKey") != null) {
								locationKey = (String) jsonObject.get("locationKey");
							} else {
								locationKey = null;
							}
							arrangement.setLocationKey(locationKey);
							Long segmentId;
							if (jsonObject.get("segmentId") != null) {
								segmentId = (Long) jsonObject.get("segmentId");
							} else {
								segmentId = 0L;
							}
							arrangement.setSegmentId(segmentId);
							arrangements.add(arrangement);
						}
					}
				case FAILURE:
					break;
				case NO_DATA:
					break;
				default:
					break;
			}
		} catch (Exception exception) {
			log.error("getArrangementsByCollectionId " + exception);
			throw new GenericException(exception.getMessage());
		}
		return arrangements;
	}
	
	/**
	 * Add Mark down product
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,object
	 * @return JSONObject
	 */
	@Override
	public JSONObject addMarkdownProduct(String kohlsRestAPIUrl,String object) {
		RestTemplate restTemplate = new RestTemplate();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		Status status = null;
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity reqEntity = new HttpEntity(object,headers);
		try{
			// Calling KohlsRestAPI addMarkdownProduct service
			ResponseEntity<String> result = restTemplate
					.exchange(kohlsRestAPIUrl + "/item/addMarkdownProduct",
							HttpMethod.POST, reqEntity, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch (status) {
				case SUCCESS:
					JSONArray jsonArray=(JSONArray) jsonObject.get("data");
					jsonObject=(JSONObject) jsonArray.get(0);
					jsonObject=getItemDetailsByItemId(kohlsRestAPIUrl,(Long)jsonObject.get("itemId"),"MD");
					break;
				case FAILURE:
					break;
				case CONSTRAINT_VIOLATION:
					break;
				case INVALID_PARAMETERS:
					break;
				default:
					break;
			}
		}catch(Exception exception){
			log.error("addMarkdownProduct " + exception);
			throw new GenericException(exception.getMessage());
		}
		return jsonObject;
	}

	/**
	 * getItemDetailsByItemId
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,itemId, itemType
	 * @return JSONObject
	 */
	@Override
	public JSONObject getItemDetailsByItemId(String kohlsRestAPIUrl,Long itemId,String itemType){
		RestTemplate restTemplate = new RestTemplate();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		Status status = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity reqEntity = new HttpEntity(headers);
		try{
			// Calling KohlsRestAPI getItemDetailsByItemId service
			ResponseEntity<String> result = restTemplate
					.exchange(kohlsRestAPIUrl + "/item/getItem?itemType="+itemType+"&itemId="+itemId,
							HttpMethod.GET, reqEntity, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					break;
				case FAILURE:
					break;
				case CONSTRAINT_VIOLATION:
					break;
				default:
					break;
			}
		}catch(Exception exception){
			log.error("getItemDetailsByItemId " + exception);
			throw new GenericException(exception.getMessage());
		}
		return jsonObject;
	}
	
	
	/**
	 * Get Collection names by passing query string
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,query
	 * @return List<String> object
	 */
	@Override
	public List<String> getCollectionNames(String query, String kohlsRestAPIUrl) {

		String collectionName = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<Collections> collectionList = getCollections(kohlsRestAPIUrl);
		for (Collections collections : collectionList) {
			collectionName = collections.getName().toLowerCase();
			if (collectionName.contains(query)) {
				matched.add(collections.getName());
			}
		}
		return matched;
	}

}
