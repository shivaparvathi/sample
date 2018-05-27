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


import com.kbmc.service.ManageChannelsService;
import com.kbmc.model.Channel;
import com.kbmc.model.ChannelType;
import com.kbmc.model.GenericException;
import com.kbmc.model.Status;


/**
 * Manage Channels Service Implementation class.
 * 
 * @author ZONE24X7
 * 
 */

public class ManageChannelsServiceImpl implements ManageChannelsService {

	private static final Logger log = Logger
			.getLogger(ManageChannelsServiceImpl.class);

	private ManageChannelTypesServiceImpl manageChannelTypesService;

	public void setManageChannelTypesService(
			ManageChannelTypesServiceImpl manageChannelTypesService) {
		this.manageChannelTypesService = manageChannelTypesService;
	}
	
	/**
	 * Get the Channels List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Channel> Object
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> getChannels(String kohlsRestAPIUrl) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Status status;

		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonDataArray = new JSONArray();
		List<Channel> channelsList = new ArrayList<Channel>();
		try {
			//calling KohlsRestAPI getChannels Service
		
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/channel/getAllChannels",
					HttpMethod.GET, entity, String.class);
			jsonObject = (JSONObject) parser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));
			switch (status) {
			case SUCCESS:
				jsonDataArray = (JSONArray) jsonObject.get("data");
				if (jsonDataArray.size() != 0) {
					for (Object jsonArray:jsonDataArray) {
						jsonObject = (JSONObject) jsonArray;
						Channel channel = new Channel();
						channel.setChannelId((Long) jsonObject.get("channelId"));
						String name = (String) jsonObject.get("name");
						// As we have to show the channel name here we are
						// splitting it to two parts as channel type is appended
						// to channel name
						channel.setName(name.substring(name.indexOf("_")+1));
						channel.setStatus(((String) jsonObject.get("status"))
								.toLowerCase());
						channel.setImage((String) jsonObject.get("image"));
						channel.setThumbnailImage((String) jsonObject
								.get("thumbnailImage"));
						channel.setDescription((String) jsonObject
								.get("description"));
						channel.setChannelType((String) jsonObject
								.get("channelType"));
						channel.setCollectionId((ArrayList<Integer>) jsonObject
								.get("collectionId"));
						channelsList.add(channel);
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
			log.error("getChannels " + exception);
			throw new GenericException(exception.getMessage());
		}

		return channelsList;
	}

	/**
	 * Create Channel
	 * 
	 * @param kohlsRestAPIUrl,request
	 * @return ModelAndView Object
	 */

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView createChannel(HttpServletRequest request,
			String kohlsRestAPIUrl) {

		ModelAndView mav = new ModelAndView("channelmanager/channels");
		String name = request.getParameter("channelName");
		List<Channel> channelsList = getChannels(kohlsRestAPIUrl);
		for (Channel channel : channelsList) {
			if (channel.getName().equals(name)) {
				mav.addObject("errormsg", true);
				mav.addObject("channels", channelsList);
				mav.addObject("create", "create");
				return mav;
			}
		}

		String status = request.getParameter("status");
		String image = request.getParameter("imageURL");
		String thumbnailImage = request.getParameter("thumbnailURL");
		String description = request.getParameter("description");
		String channelType = request.getParameter("channelType");
		String[] collectionsArray = request.getParameterValues("collections");
		ArrayList<Integer> collectionId = new ArrayList<Integer>();
		if (collectionsArray != null) {
			if (collectionsArray.length == 0) {
			} else {
				for (String selectedCollectionId:collectionsArray) {
					Integer collection_id = Integer
							.parseInt(selectedCollectionId);
					collectionId.add(collection_id);
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", channelType.split("_")[1].substring(0, 1).toUpperCase()
				+ "_" + name);
		map.put("status", status);
		map.put("image", image);
		map.put("thumbnailImage", thumbnailImage);
		map.put("description", description);
		map.put("channelType", Long.parseLong(channelType.split("_")[0]));
		map.put("collectionId", collectionId);
		// TODO waiting for response, Sending featured as Hard Coded value
		map.put("featured", "DISABLED");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		Status statusObj;

		HttpEntity reqEntity = new HttpEntity(reqJson, headers);

		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			// Calling Kohls API createChannel Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/channel/createChannel",
					HttpMethod.POST, reqEntity, String.class);
			jsonObject = (JSONObject) parser.parse(result.getBody());

			statusObj = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));
			switch (statusObj) {
			case SUCCESS:
				mav.addObject("successMessage", "Channel Created SuccessFully");
				break;
			case FAILURE:
				mav.addObject("creationfailuremsg", jsonObject.get("message"));
				break;
			default:
				break;
			}

		} catch (Exception exception) {
			log.error("createChannel " + exception);
			throw new GenericException(exception.getMessage());
		}
		return mav;

	}

	/**
	 * Update Channel
	 * 
	 * @param request
	 *            ,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */

	@Override
	public ModelAndView updateChannel(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		long channelId = Long.parseLong(request.getParameter("channelId"));
		String channelName = request.getParameter("channelName");
		String status = request.getParameter("status");
		String image = request.getParameter("imageURL");
		String thumbnailImage = request.getParameter("thumbnailURL");
		String description = request.getParameter("description");
		String channelType = request.getParameter("channelType");
		ArrayList<Integer> collectionId = new ArrayList<Integer>();
		String[] collectionsArray = request.getParameterValues("collections");
		if (collectionsArray != null) {
			if (collectionsArray.length == 0) {
			} else {
				for (String selectedCollectionId:collectionsArray) {
					collectionId.add(Integer.parseInt(selectedCollectionId));
				}
			}
		}

		Channel channelobj = new Channel();
		channelobj.setChannelId(channelId);
		channelobj.setChannelType(channelType);
		channelobj.setDescription(description);
		channelobj.setImage(image);
		channelobj.setName(channelName);
		channelobj.setStatus(status);
		channelobj.setThumbnailImage(thumbnailImage);
		channelobj.setCollectionId(collectionId);
		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		ModelAndView mav = new ModelAndView("channelmanager/channels");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		HttpEntity entity = new HttpEntity(headers);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelId", channelId);
		map.put("name", channelType.split("_")[1].substring(0, 1).toUpperCase()
				+ "_" + channelName);
		map.put("status", status);
		map.put("image", image);
		map.put("thumbnailImage", thumbnailImage);
		map.put("description", description);
		map.put("channelType", Long.parseLong(channelType.split("_")[0]));
		map.put("collectionId", collectionId);
		// TODO waiting for response, Sending hardcoded value
		map.put("featured", "DISABLED");

		reqJson.putAll(map);
		HttpEntity reqEntity = new HttpEntity(reqJson, headers);
		Status statusObject;

		try {
			// Calling updateChannel External Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/channel/updateChannel", HttpMethod.PUT,
					reqEntity, String.class);

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			statusObject = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));
			switch (statusObject) {
			case SUCCESS:
				mav.addObject("successMessage", "Channel Updated Successfully");
				break;
			case FAILURE:
				mav.addObject("updatefailuremsg", true);
				mav.addObject("update", "update");
				mav.addObject("channelobj", channelobj);
				break;
			default:
				break;
			}
		} catch (Exception exception) {
			log.error("updateChannel" + exception);
			throw new GenericException(exception.getMessage());
		}
		mav.addObject("channelTypes", getChannels(kohlsRestAPIUrl));
		return mav;
	}

	/**
	 * Get Channel Details By Id
	 * 
	 * @param request
	 *            ,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */

	@Override
	public ModelAndView getChannelDetailsById(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		long channelId = Long.parseLong(request.getParameter("channelId"));
		ModelAndView mav = new ModelAndView("channelmanager/channels");
		List<Channel> channels = getChannels(kohlsRestAPIUrl);
		for (Channel channel : channels) {
			if (channel.getChannelId() == channelId) {
				mav.addObject("channelobj", channel);
				mav.addObject(
						"selectedChannelType",
						getChannelTypeIdByName(channel.getChannelType(),
								kohlsRestAPIUrl));
			}
		}
		return mav;
	}

	/**
	 * Delete Channel
	 * 
	 * @param request
	 *            ,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */

	@Override
	public ModelAndView deleteChannel(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		Long channelId = Long.parseLong(request.getParameter("channelId"));
		ModelAndView mav = new ModelAndView("channelmanager/channels");
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity reqEntity = new HttpEntity(headers);
	
		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		Status status;
		try {
			// calling Kohls API deleteChannel Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/channel/deleteChannel?channelId="
							+ channelId, HttpMethod.DELETE, reqEntity,
					String.class);
			jsonObject = (JSONObject) parser.parse(result.getBody());

			status = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));
			switch (status) {
			case SUCCESS:
				mav.addObject("successMessage", "Channel Deleted Successfully");
				break;
			case FAILURE:
				mav.addObject("deletefailuremsg", "Unable To Delete Channel");
				break;
			case CONSTRAINT_VIOLATION:
				mav.addObject("deletefailuremsg", jsonObject.get("message"));
				break;
			default:
				break;
			}

		} catch (Exception exception) {
			log.error("deleteChannel " + exception);
			throw new GenericException(exception.getMessage());
		}
		return mav;

	}

	/**
	 * get Channels Names List
	 * 
	 * @param query
	 *            ,kohlsRestAPIUrl
	 * @return List<String> Object
	 */

	public List<String> getChannelNameList(String query, String kohlsRestAPIUrl) {

		String name = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<Channel> list = getChannels(kohlsRestAPIUrl);
		for (Channel channel : list) {
			name = channel.getName().toLowerCase();
			if (name.contains(query)) {
				matched.add(channel.getName());
			}
		}
		return matched;
	}

	/*
	 * Get ChannelType Id By Name
	 * 
	 * @param channelTypeName,kohlsRestAPIUrl
	 * 
	 * @return ChannelType Object
	 */

	public ChannelType getChannelTypeIdByName(String channelTypeName,
			String kohlsRestAPIUrl) {

		List<ChannelType> channelTypeList = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		ChannelType channelType=null;
		for (ChannelType chType : channelTypeList) {
			if (chType.getName().equals(channelTypeName)) {
				channelType = chType;
			}
		}
		return channelType;
	}
}
