package com.kbmc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.kbmc.model.GenericException;
import com.kbmc.model.Location;
import com.kbmc.model.Status;
import com.kbmc.service.ManageLocationService;


import org.apache.log4j.Logger;


/**
 * Manage Location Service implementation Class.
 * 
 * @author ZONE 24X7
 */

public class ManageLocationServiceImpl implements ManageLocationService {

	private static final Logger log = Logger
			.getLogger(ManageLocationServiceImpl.class);

	/**
	 * Get the Location List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Location> Object
	 */
	@SuppressWarnings("unchecked")
	public List<Location> getLocations(String kohlsRestAPIUrl) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Status status;
		List<Location> locationsList = new ArrayList<Location>();
		try {
			// Calling KohlsRestAPI getLocations service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/location/getLocations", HttpMethod.GET,
					entity, String.class);

			JSONObject jsonObject = new JSONObject();
			JSONParser parser = new JSONParser();
			JSONArray jsonDataArray = new JSONArray();

			jsonObject = (JSONObject) parser.parse(result.getBody());
			
			status=Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) jsonObject.get("data");
					if (jsonDataArray.size() != 0) {
					for (Object jsonArray : jsonDataArray) {
							jsonObject = (JSONObject) jsonArray;
							Location location = new Location();
							location.setStoreId((String) jsonObject.get("storeId"));
							if (jsonObject.get("radius").getClass().getSimpleName()
									.equals("Long")) {
								long radius = (Long) jsonObject.get("radius");
								location.setRadius((double) radius);
							} else {
								location.setRadius((Double) jsonObject.get("radius"));
							}
							location.setName((String) jsonObject.get("name"));
							location.setLongitude((Double) jsonObject.get("longitude"));
							location.setLatitude((Double) jsonObject.get("latitude"));
							location.setLocationKey((String) jsonObject
									.get("locationKey"));
							locationsList.add(location);
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

			log.error("getLocations " + exception);
			throw new GenericException(exception.getMessage());
		}

		return locationsList;

	}

	/**
	 * Create Location
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * @return ModelAndView Object
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView createLocation(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		String locationKey = request.getParameter("locationkey").trim();
		String name = request.getParameter("name").trim();
		String storeId = request.getParameter("storeid").trim();
		Double latitude = Double.parseDouble(request.getParameter("latitude"));
		Double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		Double radius = Double.parseDouble(request.getParameter("radius"));
		ModelAndView mav = new ModelAndView("walletmanager/location");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		Status status;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("locationKey", locationKey);
		map.put("storeId", storeId);
		map.put("name", name);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("radius", radius);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		HttpEntity reqEntity = new HttpEntity(reqJson, headers);

		// Calling KohlsRestAPI addLocation service
		try {
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/location/addLocation", HttpMethod.POST,
					reqEntity, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			
			switch(status){
				case SUCCESS:
					mav.addObject("successMessage", "Location Created SuccessFully");
					break;
				case FAILURE:
					mav.addObject("createfailuremsg", true);
					mav.addObject("message", (String) jsonObject.get("message"));
					break;
				default:
					break;
			}

		} catch (Exception exception) {
			log.error("createLocation " + exception);
			throw new GenericException(exception.getMessage());
		}
		mav.addObject("locations", getLocations(kohlsRestAPIUrl));
		return mav;

	}

	/**
	 * Update Location
	 * 
	 * @param request
	 *            ,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView updateLocation(HttpServletRequest request,
			String kohlsRestAPIUrl) {

		String locationKey = request.getParameter("locationkey").trim();
		String storeId = request.getParameter("storeid").trim();
		String name = request.getParameter("name").trim();
		Double latitude = Double.parseDouble(request.getParameter("latitude"));
		Double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		Double radius = Double.parseDouble(request.getParameter("radius"));
		RestTemplate restTemplate = new RestTemplate();
		Status status;
		ModelAndView mav = new ModelAndView("walletmanager/location");
		Location locationobj = new Location();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("locationKey", locationKey);
		map.put("name", name);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("radius", radius);
		map.put("storeId", storeId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		HttpEntity reqEntity = new HttpEntity(reqJson, headers);
		try {
			// Calling KohlsRestAPI updateLocation service

			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/location/updateLocation",
					HttpMethod.PUT, reqEntity, String.class);

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			String message = (String)jsonObject.get("message");

			switch(status){
				case SUCCESS:
					mav.addObject("successMessage", "Location Updated SuccessFully");
					break;
				case FAILURE:
					locationobj.setLatitude(latitude);
					locationobj.setLocationKey(locationKey);
					locationobj.setLongitude(longitude);
					locationobj.setName(name);
					locationobj.setRadius(radius);
					locationobj.setStoreId(storeId);
					mav.addObject("updatefailuremsg", message);
					mav.addObject("update", "update");
					mav.addObject("locationobj", locationobj);
					break;
				default:
					break;
			}
		} catch (Exception exception) {
			log.error("updateLocation " + exception);
			throw new GenericException(exception.getMessage());
		}

		List<Location> location = getLocations(kohlsRestAPIUrl);
		mav.addObject("locations", location);
		return mav;

	}

	/**
	 * Get Location details by Id
	 * 
	 * @param request
	 *            ,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView getLocationDetailsById(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		String locationKey = (request.getParameter("locationkey"));
		ModelAndView mav = new ModelAndView("walletmanager/location");
		try {
			List<Location> locationsList = getLocations(kohlsRestAPIUrl);
			for (Location location : locationsList) {
				if (location.getLocationKey().equals(locationKey)) {
					mav.addObject("locationobj", location);
					mav.addObject("locations", locationsList);
				}
			}
		} catch (Exception exception) {
			log.error("showUpdateLocation " + exception);
			throw new GenericException(exception.getMessage());
		}
		return mav;
	}

	/**
	 * Delete Location
	 * 
	 * @param request
	 *            ,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView deleteLocation(HttpServletRequest request,
			String kohlsRestAPIUrl) {

		String locationKey = request.getParameter("locationkey");
		RestTemplate restTemplate = new RestTemplate();
		Status status;
		ModelAndView mav = new ModelAndView("walletmanager/location");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity reqEntity = new HttpEntity(headers);
		try {
			// Calling KohlsRestAPI deleteLocation service
			
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/location/deleteLocation?locationKey="
							+ locationKey, HttpMethod.DELETE, reqEntity,
					String.class);

			JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			
			switch(status){
				case SUCCESS:
					mav.addObject("successMessage", "Location Deleted SuccessFully");
					break;
				case FAILURE:	
					mav.addObject("deletefailuremsg", true);
					break;
				default:
					break;
			}

		} catch (Exception exception) {
			log.error("deleteLocation " + exception);
			throw new GenericException(exception.getMessage());
		}
		List<Location> locations = getLocations(kohlsRestAPIUrl);
		mav.addObject("locations", locations);
		return mav;
	}

	/**
	 * get Location Names List
	 * 
	 * @param query
	 *            ,kohlsRestAPIUrl
	 * @return List<String> Object
	 */
	@Override
	public List<String> getLocationNames(String query, String kohlsRestAPIUrl) {
		String locationName = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<Location> locations = getLocations(kohlsRestAPIUrl);

		for (Location location : locations) {
			locationName = location.getName().toLowerCase();
			if (locationName.contains(query)) {
				matched.add(location.getName());
			}
		}

		return matched;

	}

}
