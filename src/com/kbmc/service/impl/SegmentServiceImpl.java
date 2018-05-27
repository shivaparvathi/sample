package com.kbmc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.kbmc.model.ServiceResponse;
import com.kbmc.model.Status;
import com.kbmc.service.SegmentService;


import org.apache.log4j.Logger;

/**
 * Segment service implementation class.
 * @author ZONE 24X7
 */

public class SegmentServiceImpl implements SegmentService {

	private static final Logger log = Logger
			.getLogger(SegmentServiceImpl.class);
	
	/** 
	 * Get the Segments List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Segment> Object
	 */

	public List<Segment> getAllSegments(String kohlsRestAPIUrl) {
		List<Segment> segmentList = new ArrayList<Segment>();
		RestTemplate template = new RestTemplate();
		Status status;
		try {
		//Calling KohlsRestAPI  getSegments service
		 
		String allSegments = template.getForObject(
				kohlsRestAPIUrl + "/segment/getSegments", String.class);
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonDataArray;
		
			jsonObject = (JSONObject) jsonParser.parse(allSegments);
			status=Status.valueOfIgnoreCase(Status.class,(String)jsonObject.get("status"));	
			switch(status){
				case SUCCESS:
					jsonDataArray = (JSONArray) jsonObject.get("data");
					if (jsonDataArray.size() != 0) {
						for (Object jsonArray:jsonDataArray) {
							JSONObject object = (JSONObject) jsonArray;
							Segment segment = new Segment();
							segment.setId((Long) object.get("id"));
							segment.setName((String) object.get("name"));
							segment.setDescription((String) object
									.get("description"));
							segment.setSegmentMetadata(object
									.get("segmentMetadata"));
							segmentList.add(segment);
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
			log.error("getAllSegments " + exception);
			throw new GenericException(exception.getMessage());
		}
		if(segmentList.size()>0){
			
				Collections.sort(segmentList, new Comparator<Segment>() {
					   @Override
					   public int compare(Segment segment1, Segment segment2) {
					    return (int) (segment2.getId()-segment1.getId());
					   }
			});
			
		}
		return segmentList;

	}
	
	/** 
	 * Add/Create Segment implementation
	 * 
	 * @param kohlsRestAPIUrl,request,session
	 * @return ModelAndView Object
	 */

	public ModelAndView createSegment(String kohlsRestAPIUrl, HttpServletRequest request,
			HttpSession session) {
		String segmentName = request.getParameter("name").trim();
		String segmentDesc = request.getParameter("description").trim();

		ModelAndView mav = new ModelAndView("walletmanager/segment");

		RestTemplate template = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		Status status;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", segmentName);
		map.put("description", segmentDesc);
		map.put("segmentMetadata", " ");

		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);

		try {
			
			//Calling KohlsRestAPI  addSegment service
			String result = template.postForObject(kohlsRestAPIUrl + "/segment/addSegment",
					reqJson, String.class);
			jsonObject = (JSONObject) jsonParser.parse(result);
			status = Status.valueOfIgnoreCase(Status.class,(String)jsonObject.get("status"));

			switch(status){
				case SUCCESS:
						mav.addObject("successMessage","Segment Created SuccessFully");
						break;
				case FAILURE:
						mav.addObject("creationfailuremsg", jsonObject.get("message"));
						break;
				case RECORD_ALREADY_EXISTS:
						mav.addObject("creationfailuremsg", jsonObject.get("message"));
						break;
				default:
						break;
			}

		} catch (Exception exception) {
			log.error("createSegment " + exception);
			throw new GenericException(exception.getMessage());
		}
		return mav;

	}

	/** 
	 * GetSegment details by passing segmentId
	 * 
	 * @param kohlsRestAPIUrl,request,session
	 * @return ModelAndView Object
	 */
	public ModelAndView getSegmentDetailsById(String kohlsRestAPIUrl,
			HttpServletRequest request, HttpSession session) {

		Long segmentId = Long.parseLong(request.getParameter("id"));
		ModelAndView mav = new ModelAndView("walletmanager/segment");
		RestTemplate template = new RestTemplate();
		Status status;
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = new JSONArray();
		String response;
		long products=0;
		long offers=0;
		long kohlsCash=0;
		long markdownProducts=0;
		try {
		//Calling KohlsRestAPI  getSegment details by passing segmentId service
		response = template.getForObject(kohlsRestAPIUrl
				+ "/segment/getSegment?segmentId=" + segmentId, String.class);
		
		
			jsonObject = (JSONObject) jsonParser.parse(response);

			status=Status.valueOfIgnoreCase(Status.class, (String) jsonObject.get("status"));
			switch(status){
				case SUCCESS:
					JSONArray msg = (JSONArray) jsonObject.get("data");
					JSONObject segmentJson = (JSONObject) msg.get(0);
					Segment segment = new Segment();
					segment.setId((Long) segmentJson.get("id"));
					segment.setName((String) segmentJson.get("name"));
					segment.setDescription((String) segmentJson.get("description"));
					mav.addObject("segmentobj", segment);
					break;
				case FAILURE:
					break;
				default:
					break;	
			}

		} catch (Exception exception) {
			log.error("getSegmentDetailsById " + exception);
			throw new GenericException(exception.getMessage());
		}
		try {
			//Calling KohlsRestAPI  getSegment details by passing segmentId service
			 response = template.getForObject(kohlsRestAPIUrl
					+ "/segment/getItemsBySegmentId?segmentId=" + segmentId+"&offset=-1&limit=-1", String.class);
			
			
				jsonObject = (JSONObject) jsonParser.parse(response);

				status=Status.valueOfIgnoreCase(Status.class, (String) jsonObject.get("status"));
				switch(status){
					case SUCCESS:
						jsonObject = (JSONObject) jsonParser.parse(response);
						jsonArray =  (JSONArray) jsonObject.get("data");
						JSONObject json=(JSONObject) jsonArray.get(0);
						jsonArray=(JSONArray) json.get("data");
						for(Object item:jsonArray){
							jsonObject = (JSONObject) item;
							if(jsonObject.get("type").equals("GP")){
								products = products+1;
							}
							else if(jsonObject.get("type").equals("OC")){
								offers = offers+1;
							}
							else if(jsonObject.get("type").equals("CS")){
								kohlsCash = kohlsCash+1;
							}
							else if(jsonObject.get("type").equals("MD")){
								markdownProducts = markdownProducts+1;
							}
							else
							{
								
							}
							
						}
						mav.addObject("items", jsonArray);
						
						
						break;
					case FAILURE:
						break;
					case NO_DATA:
						break;
					default:
						break;	
				}

			} catch (Exception exception) {
				log.error("getItemsBySegmentId " + exception);
				throw new GenericException(exception.getMessage());
			}
		mav.addObject("products",products);
		mav.addObject("offers",offers);
		mav.addObject("kohlsCash",kohlsCash);
		mav.addObject("markdownProducts",markdownProducts);
		return mav;
	}

	/** 
	 * Update segment service implementation 
	 * 
	 * @param kohlsRestAPIUrl,request,session
	 * @return ModelAndView Object
	 */
	public ModelAndView updateSegment(String kohlsRestAPIUrl, HttpServletRequest request,
			HttpSession session) {

		Long id = Long.parseLong(request.getParameter("id"));
		String segmentDesc = request.getParameter("description").trim();
		String segmentName = request.getParameter("name").trim();
		Status status;
		ModelAndView mav = new ModelAndView("walletmanager/segment");
		RestTemplate template = new RestTemplate();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("name", segmentName);
		map.put("description", segmentDesc);
		map.put("segmentMetadata", " ");
		Segment segment = new Segment();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject reqJson = new JSONObject();
		reqJson.putAll(map);
		HttpEntity entity = new HttpEntity(reqJson, headers);
		segment.setId(id);
		segment.setName(segmentName);
		segment.setDescription(segmentDesc);
		// calling the KohlsRestAPI updateSegment Service
		try {
			ResponseEntity<String> result = template.exchange(kohlsRestAPIUrl
					+ "/segment/updateSegment", HttpMethod.PUT, entity,
					String.class);
			JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status =Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
			
			switch(status){
				case SUCCESS: 
						mav.addObject("successMessage", "Segment Updated Successfully");
						break;
				case FAILURE:
						mav.addObject("updatefailuremsg", true);
						mav.addObject("update", "update");
						mav.addObject("segmentobj", segment);
						break;
				default:
						break;
			}
		} catch (Exception exception) {
			log.error("updateSegment " + exception);
			throw new GenericException(exception.getMessage());
		}

		return mav;

	}
	/** 
	 * DeleteSegment service implementation
	 * 
	 * @param kohlsRestAPIUrl,request,session
	 * @return ModelAndView Object
	 */
	public ModelAndView deleteSegment(String kohlsRestAPIUrl,
			HttpServletRequest request, HttpSession session) {
		Long id = Long.parseLong(request.getParameter("id"));
		RestTemplate template = new RestTemplate();
		ModelAndView deleteSegmentView = new ModelAndView(
				"walletmanager/segment");
		Status status;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity reqEntity = new HttpEntity(headers);

		try {
			//Calling KohlsRestAPI deleteSegment service
			ResponseEntity<String> result = template.exchange(kohlsRestAPIUrl
					+ "/segment/deleteSegment?segmentId="+id, HttpMethod.DELETE, reqEntity,
					String.class);
			JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class, (String)jsonObject.get("status"));
		
			switch(status){
				case SUCCESS:
						deleteSegmentView.addObject("successMessage", "Segment Deleted Successfully");
						break;
				case FAILURE:
						deleteSegmentView.addObject("deletefailuremsg",jsonObject.get("message"));
						break;
				case CONSTRAINT_VIOLATION:
						deleteSegmentView.addObject("deletefailuremsg",jsonObject.get("message"));
						break;
				default:
						break;
			}
			
		} catch (Exception exception) {
			log.error("deleteSegment " + exception);
			throw new GenericException(exception.getMessage());
		}
		return deleteSegmentView;
	}

	/** 
	 * GetSegment names service implementation
	 * 
	 * @param query,kohlsRestAPIUrl
	 * @return List<String> Object
	 */
	@Override
	public List<String> getSegmentNames(String query, String kohlsRestAPIUrl) {

		String segmentName = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<Segment> segments = getAllSegments(kohlsRestAPIUrl);
		for (Segment segment : segments) {
			segmentName = segment.getName().toLowerCase();
			if (segmentName.contains(query))
				matched.add(segment.getName());
		}

		HashSet matchedHashset = new HashSet();
		matchedHashset.addAll(matched);
		matched.clear();
		matched.addAll(matchedHashset);
		return matched;
	}
		
	/** 
	 * Save Items to Segment
	 * 
	 * @param segmentInfo,kohlsRestAPIUrl
	 * @return ServiceResponse Object
	 */
	@Override
	public ServiceResponse updateItemsInSegment(String kohlsRestAPIUrl,String segmentInfo) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		Long segmentId = null;
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			jsonObject = (JSONObject) parser.parse(segmentInfo);
			segmentId = Long.parseLong((String)jsonObject
					.get("segmentId"));
			JSONArray jsonArray=(JSONArray) jsonObject.get("items");
			List<Long> itemsList = new ArrayList<Long>();
			for(Object json :jsonArray)
			{		
			Long itemId=Long.parseLong((String)json);
			itemsList.add(itemId);
		}
			RestTemplate template = new RestTemplate();
			String status;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("segmentId", segmentId);
			map.put("items",itemsList);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity entity = new HttpEntity(reqJson, headers);
			//Calling KohlsRestAPI updateItemsInSegment service
			ResponseEntity<String> result = template
					.exchange(kohlsRestAPIUrl + "/segment/updateItemsInSegment",
							HttpMethod.PUT, entity, String.class);

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
			log.error("updateItemsInSegments" + exception);
			throw new GenericException(exception.getMessage());
		}
		return serviceResponse;
	}

	

}
