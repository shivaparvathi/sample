package com.kbmc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

import com.kbmc.model.Arrangement;
import com.kbmc.model.GenericException;
import com.kbmc.model.ServiceResponse;
import com.kbmc.model.Status;
import com.kbmc.service.ManageArrangementsService;

/**
 * Manage Arrangements Service Implementation class.
 * 
 * @author ZONE24X7
 */

public class ManageArrangementsServiceImpl implements ManageArrangementsService {

	private static final Logger log = Logger
			.getLogger(ManageArrangementsServiceImpl.class);

	/**
	 * Get the Arrangement List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Arrangement> Object
	 */

	@Override
	public List<Arrangement> getArrangements(String kohlsRestAPIUrl) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonDataArray = new JSONArray();
		List<Arrangement> arrangements = new ArrayList<Arrangement>();
		Status status;
		try {
			// calling KohlsRestAPI getArrangements service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/arrangement/getArrangements",
					HttpMethod.GET, entity, String.class);
			jsonObject = (JSONObject) parser.parse(result.getBody());

			status=Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) jsonObject.get("data");
					if(jsonDataArray.size()!=0){
						for (Object jsonArray : jsonDataArray) {
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
					break;
				case FAILURE:
					break;
				case NO_DATA:
					break;
				default:
					break;
					
			}
		} catch (Exception exception) {
			log.error("getArrangements " + exception);
			throw new GenericException(exception.getMessage());
		}
		return arrangements;
	}

	/**
	 * Add/Create Arrangement implementation
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,arrangement
	 * @return JSONObject Object
	 */
	@Override
	public JSONObject createArrangement(Arrangement arrangement,
			String kohlsRestAPIUrl) {

		String name = arrangement.getName().trim();
		boolean active = arrangement.getActive();
		String locationKey = arrangement.getLocationKey();
		if (locationKey == null) {
			locationKey = null;
		}
		Long segmentId=arrangement.getSegmentId();
		if(segmentId==0){
			segmentId=null;
			 
		}
		
			RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("active", active);
		map.put("locationKey", locationKey);
		map.put("segmentId", segmentId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		HttpEntity reqEntity = new HttpEntity(reqJson, headers);
		Status status;
		try {
			// calling KohlsRestAPI addArrangement Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/arrangement/addArrangement",
					HttpMethod.POST, reqEntity, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					return jsonObject;
				case FAILURE:	
					jsonObject.put("message", "FAILURE");
					break;
				case RECORD_ALREADY_EXISTS:
					jsonObject.put("successMessage","Arrangement with same name already exists");
				default:
					break;
			}

		} catch (Exception exception) {
			log.error("createArrangement " + exception);
			throw new GenericException(exception.getMessage());
		}
		return jsonObject;
	}

	/**
	 * Update Arrangement implementation
	 * 
	 * @param arrangementInfo
	 *            ,kohlsRestAPIUrl
	 * @return ServiceResponse Object
	 */

	@Override
	public ServiceResponse updateArrangement(String arrangementInfo,
			String kohlsRestAPIUrl) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
		jsonObject = (JSONObject) parser.parse(arrangementInfo);
		long arrangementId = Long.parseLong((String)jsonObject
				.get("arrangementID"));
		String name = (String) jsonObject
				.get("name");
		String locationKey = (String) jsonObject
				.get("locationKey");
		if (locationKey == null) {
			locationKey = null;
		} else {
			locationKey = (String) jsonObject
					.get("locationKey");
		}
		Long segmentId = Long.parseLong((String)jsonObject
				.get("segmentId"));
		if (segmentId == 0) {

			segmentId = null;

		} else {
			segmentId = Long.parseLong((String)jsonObject
					.get("segmentId"));
		}

		boolean active = (Boolean)(jsonObject
				.get("active"));
		RestTemplate restTemplate = new RestTemplate();
		
			
			JSONParser jsonParser=new JSONParser();
			Status status;
			Map<String, Object> map = new HashMap<String, Object>();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			map.put("arrangementId", arrangementId);
			map.put("name", name);
			map.put("active", active);
			map.put("locationKey", locationKey);
			map.put("segmentId", segmentId);

			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity reqEntity = new HttpEntity(reqJson, headers);
			//Calling KohlsRestAPI updateArrangement service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/arrangement/updateArrangement",
					HttpMethod.PUT, reqEntity, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					serviceResponse=saveArrangement(arrangementInfo,kohlsRestAPIUrl);
					if(serviceResponse.getMessage().equalsIgnoreCase("success"))
							serviceResponse.setMessage("Arrangement Successfully updated");			
					else
						serviceResponse.setMessage("Arrangement Successfully updated but Items are Failed to Save in the Arrangement, Try Again");
					break;
				case FAILURE:
					serviceResponse.setMessage("FAILURE");
					break;
				default:
					break;
			}
		} catch (Exception exception) {
			log.error("updateArrangement " + exception);
			throw new GenericException(exception.getMessage());
		}
		return serviceResponse;
	}

	/**
	 * Get Arrangement details by passing arrangementId
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,arrangementId
	 * @return Arrangement Object
	 */

	@Override
	public Arrangement getArrangementDetailsById(Long arrangementId,
			String kohlsRestAPIUrl) {

		Long id = arrangementId;
		Arrangement arrangement = new Arrangement();
		try {
			List<Arrangement> arrangementsList = getArrangements(kohlsRestAPIUrl);
			for (Arrangement arrangements : arrangementsList) {
				if (arrangements.getArrangementId() == id) {
					arrangement = arrangements;
				}
			}
		} catch (Exception exception) {
			log.error("showUpdateArrangement " + exception);
		}

		return arrangement;

	}

	/**
	 * Delete Arrangement Implementation
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,arrangementId
	 * @return ServiceResponse Object
	 */
	@Override
	public ServiceResponse deleteArrangement(Long arrangementId,
			String kohlsRestAPIUrl) {
		Long id = arrangementId;
		Status status;
		ServiceResponse serviceResponse = new ServiceResponse();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		HttpEntity reqEntity = new HttpEntity(headers);
		
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();

		try {
			// Calling KohlsRestAPI deleteArrangement Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl
							+ "/arrangement/deleteArrangement?arrangementId="
							+ id, HttpMethod.DELETE, reqEntity, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =  Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					serviceResponse.setMessage("SUCCESS");
					break;
				case FAILURE:
					serviceResponse.setMessage("FAILURE");
					break;
				default:
					break;
			}

		} catch (Exception exception) {
			log.error("deleteArrangement" + exception);
			throw new GenericException(exception.getMessage());
			
		}
		return serviceResponse;
	}
	
	/**
	 * Save Arrangement Implementation
	 * 
	 * @param arrangementInfo
	 *            ,kohlsRestAPIUrl
	 * @return ServiceResponse Object
	 */
	
	@Override
	public ServiceResponse saveArrangement(String arrangementInfo,
			String kohlsRestAPIUrl) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		Long arrangementId = null;
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			jsonObject = (JSONObject) parser.parse(arrangementInfo);
			arrangementId = Long.parseLong((String)jsonObject
					.get("arrangementID"));
			JSONArray jsonArray=(JSONArray) jsonObject.get("items");
			List<Long> itemsList = new ArrayList<Long>();
			for(Object json :jsonArray)
			{		
			Long itemId=(Long)json;
			itemsList.add(itemId);
		}
			RestTemplate template = new RestTemplate();
			String status;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("arrangementId", arrangementId);
			map.put("items",itemsList);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity entity = new HttpEntity(reqJson, headers);
			//Calling KohlsRestAPI updateItemsInArrangement service
			ResponseEntity<String> result = template
					.exchange(kohlsRestAPIUrl + "/arrangement/updateItemsInArrangement",
							HttpMethod.POST, entity, String.class);

			jsonObject = (JSONObject) parser.parse(result.getBody());
			status = (String) jsonObject.get("status");
			if (status.toLowerCase().equals("success")) {
				serviceResponse
						.setMessage("success");
			} else {
				serviceResponse
						.setMessage("failure");
			}
		} catch (Exception exception) {
			log.error("SaveArrangements" + exception);
		}
		return serviceResponse;
	}

	/**
	 * Get Items In Arrangement Implementation
	 * 
	 * @param arrangementId
	 *            ,kohlsRestAPIUrl
	 * @return JSONArray Object
	 */
	
	public JSONArray getItemsInArrangement(Long arrangementId,String kohlsRestAPIUrl){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		//Calling KohlsRestAPI getItemsInArrangement service
		ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
				+ "/arrangement/getItemsInArrangement?arrangementId="+arrangementId+"&limit=-1&offset=-1", HttpMethod.GET, entity, String.class);
		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = new JSONArray();
		
				
		try {
			jsonObject = (JSONObject) parser.parse(result.getBody());
			jsonArray =  (JSONArray) jsonObject.get("data");
			JSONObject json=(JSONObject) jsonArray.get(0);
			jsonArray=(JSONArray) json.get("data");
			
		
		} catch (Exception exception) {
			log.error("getItemsInArrangement " + exception);
		}
return jsonArray;
		
	}

	public JSONObject getProducts(String kohlsRestAPIUrl, Integer limit,
			Integer offset) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
				+ "/item/getProducts?limit=" + limit + "&offset=" + offset,
				HttpMethod.GET, entity, String.class);

		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		try {
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
		} catch (Exception exception) {
			log.error("getAllProducts " + exception);
		}
		return jsonObject;

	}

	public JSONObject getCash(String kohlsRestAPIUrl, Integer limit,
			Integer offset) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
				+ "/item/getCash?limit=" + limit + "&offset=" + offset,
				HttpMethod.GET, entity, String.class);
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		try {
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
		} catch (Exception exception) {
			log.error("getAllKohlsCash " + exception);
		}

		return jsonObject;

	}

	public JSONObject getCoupons(String kohlsRestAPIUrl, Integer limit,
			Integer offset) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
				+ "/item/getCoupons?limit=" + limit + "&offset=" + offset,
				HttpMethod.GET, entity, String.class);
		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();

		try {
			jsonObject = (JSONObject) parser.parse(result.getBody());
		} catch (Exception exception) {
			log.error("getCoupons " + exception);
		}

		return jsonObject;

	}

}
