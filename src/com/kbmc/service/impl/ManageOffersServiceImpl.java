package com.kbmc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.kbmc.model.Offer;
import com.kbmc.model.Status;

import com.kbmc.service.ManageOffersService;

/**
 * Manage Offer Service Implementation class.
 * 
 * @author ZONE24X7
 */

public class ManageOffersServiceImpl implements ManageOffersService {
	private static final Logger log = Logger
			.getLogger(ManageOffersServiceImpl.class);

	/**
	 * Get the Offers List
	 * 
	 * @param kohlsRestAPIUrl
	 * @return List<Offer> Object
	 */
	@Override
	public List<Offer> getOffers(String kohlsRestAPIUrl) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		List<Offer> offers = new ArrayList<Offer>();
		Status status;
		try {
			// Calling KohlsRestAPI getOffers Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/coupon/getOffers", HttpMethod.GET,
					entity, String.class);

			JSONObject jsonObject = new JSONObject();
			JSONParser parser = new JSONParser();
			JSONArray jsonDataArray = new JSONArray();

			jsonObject = (JSONObject) parser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));
			switch (status) {
			case SUCCESS:
				jsonDataArray = (JSONArray) jsonObject.get("data");
				if (jsonDataArray.size() != 0) {
					for (Object jsonArray : jsonDataArray) {
						jsonObject = (JSONObject) jsonArray;
						Offer offer = new Offer();
						offer.setOfferId(Long.parseLong((String) jsonObject
								.get("offerId")));
						offer.setTitle((String) jsonObject.get("title"));
						offer.setCouponCode((String) jsonObject
								.get("couponCode"));
						offer.setDescription((String) jsonObject
								.get("description"));
						offer.setIconURL((String) jsonObject.get("iconURL"));
						long utc_startDate = Long.parseLong((String) jsonObject
								.get("startDate"));
						Date startDate = new Date(utc_startDate * 1000L);
						long utc_endDate = Long.parseLong((String) jsonObject
								.get("expiryDate"));
						Date expiryDate = new Date(utc_endDate * 1000L);

						offer.setStartDate(startDate);
						offer.setExpiryDate(expiryDate);
						offer.setPassbookURL((String) jsonObject
								.get("passbookURL"));
						offer.setFeedImageURL((String) jsonObject
								.get("feedImageURL"));
						offer.setDiscountPercentage(Double
								.parseDouble((String) jsonObject
										.get("discountPercentage")));
						offers.add(offer);
					}
				}
				break;
			case FAILURE:
				offers.add(new Offer());
				break;
			case NO_DATA:
				break;
			default:
				break;
			}
		} catch (Exception exception) {
			log.error("getOffers " + exception);
			throw new GenericException(exception.getMessage());
		}

		return offers;
	}

	/**
	 * Create Offer
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * 
	 * @return ModelAndView Object
	 */
	@Override
	public ModelAndView createOffer(HttpServletRequest request,
			String kohlsRestAPIUrl) {

		String title = request.getParameter("title").trim();
		String couponCode = request.getParameter("couponCode").trim();
		String description = request.getParameter("description");
		String startdate = request.getParameter("startDate");
		String expirydate = request.getParameter("expiryDate");
		String iconURL = request.getParameter("iconURL");
		Double discountPercentage = Double.parseDouble(request
				.getParameter("discountPercentage"));
		String passbookURL = request.getParameter("passbookURL");
		String feedImageURL = request.getParameter("feedImageURL");
		ModelAndView mav = new ModelAndView("walletmanager/offers");

		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		String startDate = null;
		String expiryDate = null;
		Status status;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm");
			SimpleDateFormat newFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm");
			startDate = String
					.valueOf(formatter.parse(startdate).getTime() / 1000L);
			expiryDate = String
					.valueOf(formatter.parse(expirydate).getTime() / 1000L);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("title", title);
			map.put("couponCode", couponCode);
			map.put("iconURL", iconURL);
			map.put("description", description);
			map.put("startDate", startDate);
			map.put("expiryDate", expiryDate);
			map.put("discountPercentage", String.valueOf(discountPercentage));
			map.put("passbookURL", passbookURL);
			map.put("feedImageURL", feedImageURL);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity reqEntity = new HttpEntity(reqJson, headers);

			// Calling KohlsRestAPI addOffer Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/coupon/addOffer", HttpMethod.POST,
					reqEntity, String.class);

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));
		
			switch (status) {

			case SUCCESS:
				mav.addObject("successMessage", "Offer Created SuccessFully");
				break;
			case FAILURE:
				mav.addObject("createfailuremsg",
						(String) jsonObject.get("message"));
				break;
			case CONSTRAINT_VIOLATION:
				mav.addObject("createfailuremsg",
						(String) jsonObject.get("message"));
				break;
			default:
				break;
			}

		} catch (Exception exception) {
			log.error("createOffer " + exception);
			throw new GenericException(exception.getMessage());
		}
		mav.addObject("offers", getOffers(kohlsRestAPIUrl));
		return mav;
	}

	/**
	 * Update Offer
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * 
	 * @return ModelAndView Object
	 */
	@Override
	public ModelAndView updateOffer(HttpServletRequest request,
			String kohlsRestAPIUrl) {

		long id = Long.parseLong(request.getParameter("id"));
		String title = request.getParameter("title").trim();
		String couponCode = request.getParameter("couponCode").trim();
		String description = request.getParameter("description");
		String startdate = request.getParameter("startDate");
		String expirydate = request.getParameter("expiryDate");
		String iconURL = request.getParameter("iconURL");
		Double discountPercentage = Double.parseDouble(request
				.getParameter("discountPercentage"));
		String passbookURL = request.getParameter("passbookURL");
		String feedImageURL = request.getParameter("feedImageURL");
		Offer offer = new Offer();

		ModelAndView mav = new ModelAndView("walletmanager/offers");

		try {
			offer.setExpiryDate(new Date(expirydate));
			offer.setStartDate(new Date(startdate));
			offer.setCouponCode(couponCode);
			offer.setDescription(description);
			offer.setDiscountPercentage(discountPercentage);
			offer.setFeedImageURL(feedImageURL);
			offer.setIconURL(iconURL);
			offer.setOfferId(id);
			offer.setPassbookURL(passbookURL);
			offer.setTitle(title);

			SimpleDateFormat formatter = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm");
			startdate = String
					.valueOf(formatter.parse(startdate).getTime() / 1000);

			expirydate = String
					.valueOf(formatter.parse(expirydate).getTime() / 1000);

			RestTemplate restTemplate = new RestTemplate();
			Status status;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			map.put("couponCode", couponCode);
			map.put("iconURL", iconURL);
			map.put("description", description);
			map.put("startDate", startdate);
			map.put("expiryDate", expirydate);
			map.put("discountPercentage", String.valueOf(discountPercentage));
			map.put("passbookURL", passbookURL);

			map.put("feedImageURL", feedImageURL);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject reqJson = new JSONObject();
			reqJson.putAll(map);
			HttpEntity reqEntity = new HttpEntity(reqJson, headers);

			// Calling KohlsRestAPI updateOffer Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/coupon/updateOffer", HttpMethod.PUT,
					reqEntity, String.class);

			JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));

			switch (status) {
			case SUCCESS:
				mav.addObject("successMessage", "Offer Updated SuccessFully");
				break;
			case FAILURE:
				mav.addObject("updatefailuremsg", jsonObject.get("message"));
				mav.addObject("update", "update");
				mav.addObject("offerobj", offer);
				break;
			default:
				break;
			}

		} catch (Exception exception) {
			log.error("updateOffer " + exception);
			throw new GenericException(exception.getMessage());
		}
		List<Offer> offers = getOffers(kohlsRestAPIUrl);
		mav.addObject("offers", offers);
		return mav;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView getOfferDetailsById(HttpServletRequest request,
			String kohlsRestAPIUrl) {

		long id = Long.parseLong(request.getParameter("id"));
		ModelAndView mav = new ModelAndView("walletmanager/offers");
		try {
			List<Offer> offerslist = getOffers(kohlsRestAPIUrl);
			for (Offer offer : offerslist) {
				if (offer.getOfferId() == id) {
					mav.addObject("offerobj", offer);
					mav.addObject("offers", offerslist);
				}
			}

		} catch (Exception exception) {
			log.error("showUpdateOffer " + exception);
			throw new GenericException(exception.getMessage());
		}
		return mav;
	}

	/**
	 * Delete Offer
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,request
	 * 
	 * @return ModelAndView Object
	 */
	@Override
	public ModelAndView deleteOffer(HttpServletRequest request,
			String kohlsRestAPIUrl) {
		Long offerId = Long.parseLong(request.getParameter("id"));
		Status status;
		ModelAndView mav = new ModelAndView("walletmanager/offers");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity reqEntity = new HttpEntity(headers);
		try {
			// Calling KohlsRestAPI deleteOffer Service
			ResponseEntity<String> result = restTemplate.exchange(
					kohlsRestAPIUrl + "/coupon/deleteOffer?offerId=" + offerId,
					HttpMethod.DELETE, reqEntity, String.class);

			JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();

			jsonObject = (JSONObject) jsonParser.parse(result.getBody());
			status = Status.valueOfIgnoreCase(Status.class,
					(String) jsonObject.get("status"));

			switch (status) {
			case SUCCESS:
				mav.addObject("successMessage", "Offer Deleted SuccessFully");
				break;
			case FAILURE:
				mav.addObject("deletefailuremsg", true);
				break;
			default:
				break;
			}

		} catch (Exception exception) {
			log.error("deleteOffer " + exception);
			throw new GenericException(exception.getMessage());
		}
		List<Offer> offers = getOffers(kohlsRestAPIUrl);
		mav.addObject("offers", offers);
		return mav;

	}

	/**
	 * Get Offer Names List
	 * 
	 * @param kohlsRestAPIUrl
	 *            ,query
	 * 
	 * @return List<String> Object
	 */
	@Override
	public List<String> getOfferNames(String query, String kohlsRestAPIUrl) {
		String offername = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<Offer> offers = getOffers(kohlsRestAPIUrl);
		for (Offer offer : offers) {
			offername = offer.getTitle().toLowerCase();
			if (offername.contains(query)) {
				matched.add(offer.getTitle());
			}
		}
		HashSet matchedHashset = new HashSet();
		matchedHashset.addAll(matched);
		matched.clear();
		matched.addAll(matchedHashset);
		return matched;

	}

}
