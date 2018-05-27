package com.kbmc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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

import com.kbmc.model.GenericException;
import com.kbmc.model.Segment;
import com.kbmc.model.Status;
import com.kbmc.model.UserSegment;
import com.kbmc.service.ManageUserSegmentService;
import com.kbmc.service.SegmentService;

/**
 * Manage User Segment Service implementation class.
 * 
 * @author ZONE24X7
 */

public class ManageUserSegmentServiceImpl implements ManageUserSegmentService {

	private static final Logger log = Logger
			.getLogger(ManageUserSegmentServiceImpl.class);
	private SegmentService segmentService;

	public void setSegmentService(SegmentService segmentService) {
		this.segmentService = segmentService;
	}

	/**
	 * Show UserSegment details
	 * 
	 * @param kohlsRestAPI
	 * @return ModelAndView Object
	 */
	public ModelAndView getUsersSegments(String kohlsRestAPIUrl) {
		List<UserSegment> registeredUsers = getUsers(kohlsRestAPIUrl);

		// get segment list object
		List<Segment> segments = segmentService.getAllSegments(kohlsRestAPIUrl);

		ModelAndView mav = new ModelAndView("walletmanager/usermanagesegment");
		mav.addObject("registeredUsers", registeredUsers);
		mav.addObject("segments", segments);
		return mav;
	}

	/**
	 * Get User list
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<UserSegment>
	 */
	public List<UserSegment> getUsers(String kohlsRestAPIUrl) {
		RestTemplate restTemplate = new RestTemplate();
		

		JSONObject object = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray jsonDataArray;
		List<UserSegment> registeredUsers = new ArrayList<UserSegment>();
		Status status;
		try {
			//calling KohlsRestAPI to getUsers service
			String users = restTemplate.getForObject(kohlsRestAPIUrl + "/user/getUsers",
				String.class);
			object = (JSONObject) parser.parse(users);
			status=Status.valueOfIgnoreCase(Status.class, (String)object.get("status"));

			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) object.get("data");
					if (jsonDataArray.size() != 0) {
						for (Object jsonArray:jsonDataArray) {
							JSONObject jsonObject = (JSONObject) jsonArray;
							UserSegment usersegObj = new UserSegment();
							usersegObj.setId((Long) jsonObject.get("userId"));
							usersegObj.setFirstName((String) jsonObject
									.get("firstName"));
							usersegObj.setLastName((String) jsonObject
									.get("lastName"));
							if (jsonObject.get("segmentId") != null) {
								usersegObj.setSegmentId((Long) jsonObject
										.get("segmentId"));
							} else {
								usersegObj.setSegmentId(0);
							}
							if (jsonObject.get("segmentName") != null) {
								usersegObj.setSegmentName((String) jsonObject
										.get("segmentName"));
							} else {
								usersegObj.setSegmentName("");
							}

							usersegObj.setAddress((String) jsonObject
									.get("address"));
							usersegObj.setCity((String) jsonObject.get("city"));
							usersegObj.setEmail((String) jsonObject.get("email"));
							usersegObj.setChannelName((String) jsonObject
									.get("channelName"));
							usersegObj.setState((String) jsonObject.get("state"));
							registeredUsers.add(usersegObj);
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
			log.error("getUsers " + exception);
			throw new GenericException(exception.getMessage());
		}
		Collections.sort(registeredUsers, new Comparator<UserSegment>() {
			  	@Override
				public int compare(UserSegment userSegment1, UserSegment userSegment2) {
			    return (int) (userSegment2.getId()-userSegment1.getId());
			   }
		});
		return registeredUsers;
	}

	/**
	 * Update User segment service implementation
	 * 
	 * @param request,kohlsRestAPIUrl
	 * @return ModelAndView Object
	 */
	public ModelAndView updateUserSegment(HttpServletRequest request, String kohlsRestAPIUrl) {

		Long id = Long.parseLong(request.getParameter("user_id"));
		Long segmentId = Long.parseLong(request.getParameter("segments"));

		RestTemplate restTemplate = new RestTemplate();
		Status status;

		ModelAndView mav = new ModelAndView("walletmanager/usermanagesegment");
		UserSegment userSegment = new UserSegment();
		userSegment.setId(id);
		userSegment
				.setFirstName(request.getParameter("user_name").split(" ")[0]);
		userSegment
				.setLastName(request.getParameter("user_name").split(" ")[1]);
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", id);
		map.put("segmentId", segmentId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		HttpEntity entity = new HttpEntity(reqJson, headers);

		// Calling KohlsRestAPI for updating user segment
		try {
			ResponseEntity<String> result = restTemplate.exchange(kohlsRestAPIUrl
					+ "/user/updateUserSegment", HttpMethod.PUT, entity,
					String.class);
			JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			switch (status) {
				case SUCCESS: 
						mav.addObject("successMessage","User Segment Updated SuccessFully");
						break;
				case FAILURE: 
						mav.addObject("updatefailuremsg", "User Segment Update Failed");
						mav.addObject("userSegment", userSegment);
						break;
				default:
						break;				
			}

		} catch (Exception exception) {
			log.error("updateUserSegment " + exception);
			throw new GenericException(exception.getMessage());
		}

		List<UserSegment> registeredUsers = getUsers(kohlsRestAPIUrl);
		List<Segment> segments = segmentService.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("registeredUsers", registeredUsers);
		mav.addObject("segments", segments);
		return mav;

	}

	/**
	 * Get user names service implementation 
	 * 
	 * @param query,kohlsRestAPIUrl
	 * @return List<String> Object
	 */
	@Override
	public List<String> getClientUserNames(String query, String kohlsRestAPIUrl) {

		String userName = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<UserSegment> users = getUsers(kohlsRestAPIUrl);
		for (UserSegment userSegment : users) {

			userName = (userSegment.getFirstName() + " " + userSegment
					.getLastName()).toLowerCase();
			if (userName.contains(query)) {
				matched.add(userSegment.getFirstName() + " "
						+ userSegment.getLastName());
			}

		}
		HashSet matchedHashset = new HashSet();
		matchedHashset.addAll(matched);
		matched.clear();
		matched.addAll(matchedHashset);
		return matched;
	}

}
