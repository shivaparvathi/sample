package com.kbmc.service.impl;

import java.util.ArrayList;
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

import com.kbmc.model.ChannelType;
import com.kbmc.model.GenericException;
import com.kbmc.model.Status;
import com.kbmc.service.ManageChannelTypesService;

/**
 * Manage ChannelType implementation class.
 * 
 * @author ZONE24X7
 * 
 */

public class ManageChannelTypesServiceImpl implements ManageChannelTypesService {

	private static final Logger log = Logger
			.getLogger(ManageChannelTypesServiceImpl.class);


	/**
	 * Get the Channel type List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Collection> Object
	 */
	@Override
	public List<ChannelType> getChannelTypes(String kohlsRestAPIUrl) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonDataArray = new JSONArray();
		List<ChannelType> channelTypeList = new ArrayList<ChannelType>();
		Status status;
		try {
			// Calling KohlsRestAPI getChannelTypes Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/channel/getChannelTypes",
					HttpMethod.GET, entity, String.class);
			jsonObject = (JSONObject) parser.parse(result.getBody());
			status=Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) jsonObject.get("data");
					if (jsonDataArray.size() != 0) {
						for (Object jsonArray:jsonDataArray) {
							jsonObject = (JSONObject) jsonArray;
							ChannelType channelType = new ChannelType();
							channelType.setId((Long) jsonObject.get("id"));
							channelType.setName((String) jsonObject.get("name"));
							if (jsonObject.get("cronExpression") != null) {
								channelType.setCronExpression((String) jsonObject
										.get("cronExpression"));
							} else {
								channelType.setCronExpression("");
							}

							channelType.setParameters((String) jsonObject
									.get("parameters"));
							channelType.setStatus(((String) jsonObject
									.get("status")).toLowerCase());
							channelType
									.setBacklog((Long) jsonObject.get("backlog"));
							channelTypeList.add(channelType);
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
			log.error("getChannelTypes " + exception);
			throw new GenericException(exception.getMessage());
		}
		return channelTypeList;
	}

	/**
	 * Update Channel Type
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * 
	 * @return ModelAndView Object
	 */
	@Override
	public ModelAndView updateChannelType(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		Long channelTypeId = Long
				.parseLong((String) request.getParameter("id"));
		String channelTypeName = request.getParameter("name");
		String parameters = request.getParameter("parameters");
		String cronExpression = request.getParameter("cron_expression");
		String status = request.getParameter("status");
		long backlog = 0;
		if (request.getParameter("backlog").trim().equals("")) {
		} else {
			backlog = Long.parseLong(request.getParameter("backlog"));
		}

		ChannelType channeltypeobj = new ChannelType();
		channeltypeobj.setName(channelTypeName);
		channeltypeobj.setCronExpression(cronExpression);
		channeltypeobj.setParameters(parameters);
		channeltypeobj.setBacklog(backlog);
		channeltypeobj.setStatus(status);

		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		ModelAndView mav = new ModelAndView("channelmanager/channeltypes");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", channelTypeId);
		map.put("name", channelTypeName);
		map.put("parameters", parameters);
		map.put("cronExpression", cronExpression);
		map.put("status", status);
		map.put("backlog", backlog);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		Status responseStatus;
		reqJson.putAll(map);
		HttpEntity reqEntity = new HttpEntity(reqJson, headers);

		try {
			// Calling KohlsRestAPI updateChannelType Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/channel/updateChannelType",
					HttpMethod.PUT, reqEntity, String.class);

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			responseStatus = Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			
			switch(responseStatus){
				case SUCCESS:
					mav.addObject("successMessage", "Channel Type Updated SuccessFully");
					break;
				case FAILURE:
					mav.addObject("updatefailuremsg", true);
					mav.addObject("update", "update");
					mav.addObject("channeltypeobj", channeltypeobj);
					break;
				default:
					break;
			}
		} catch (Exception exception) {
			log.error("updateChannelType " + exception);
			throw new GenericException(exception.getMessage());
		}
		mav.addObject("channelTypes", getChannelTypes(kohlsRestAPIUrl));
		return mav;
	}

	/**
	 * Get ChannelType Details By Id
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * 
	 * @return ModelAndView Object
	 */
	@Override
	public ModelAndView getChannelTypeDetailsById(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		Long channelTypeId = Long
				.parseLong((String) request.getParameter("id"));
		ModelAndView mav = new ModelAndView("channelmanager/channeltypes");
		List<ChannelType> channelTypes = getChannelTypes(kohlsRestAPIUrl);

		for (ChannelType channelType : channelTypes) {
			if (channelType.getId() == channelTypeId.longValue()) {
				mav.addObject("channeltypeobj", channelType);
				mav.addObject("channelTypes", channelTypes);
			}
		}
		return mav;
	}

	/**
	 * Get ChannelTypes Name List
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,query
	 * 
	 * @return List<String> Object
	 */
	@Override
	public List<String> getChannelTypeNameList(String query,
			String kohlsRestAPIUrl) {

		String name = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<ChannelType> list = getChannelTypes(kohlsRestAPIUrl);
		for (ChannelType channelType : list) {
			name = channelType.getName().toLowerCase();
			if (name.contains(query)) {
				matched.add(channelType.getName());
			}
		}
		return matched;

	}
}
