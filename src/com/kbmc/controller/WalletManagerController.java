package com.kbmc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Arrangement;
import com.kbmc.model.Collections;
import com.kbmc.model.Location;
import com.kbmc.model.Offer;
import com.kbmc.model.Segment;
import com.kbmc.model.ServiceResponse;
import com.kbmc.service.ManageArrangementsService;
import com.kbmc.service.ManageCollectionsService;
import com.kbmc.service.ManageLocationService;
import com.kbmc.service.ManageOffersService;
import com.kbmc.service.ManageUserSegmentService;
import com.kbmc.service.SegmentService;

/**
 * This controller class can handle the Wallet Manager operations.
 * 
 * @author ZONE24X7
 * 
 */

@Controller
@RequestMapping(value = { "/walletManager", "/admin" })
public class WalletManagerController {
//reading RestAPIUrl value from property using @value annotation
	@Value(value = "${RestAPIUrl}")
	private String kohlsRestAPIUrl;

	@Autowired
	private ManageUserSegmentService manageUserSegmentService;

	@Autowired
	private ManageLocationService manageLocationService;

	@Autowired
	private SegmentService segmentService;

	@Autowired
	private ManageOffersService manageOffersService;

	@Autowired
	private ManageArrangementsService manageArrangementsService;

	@Autowired
	private ManageCollectionsService manageCollectionsService;

	/**
	 * Manage User Segment
	 */

	@RequestMapping(method = RequestMethod.GET, value = { "/segments",
			"/getUserSegments", "/updateUserSegment" })
	public ModelAndView welcomeWalletManager() {

		ModelAndView mav = manageUserSegmentService
				.getUsersSegments(kohlsRestAPIUrl);
		mav.addObject("selected_header", "segment");

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateUserSegment")
	public ModelAndView updateUserSegment(HttpServletRequest request) {

		ModelAndView mav = manageUserSegmentService.updateUserSegment(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "segment");
		return mav;
	}

	/**
	 * Manage Segments
	 */

	@RequestMapping(method = RequestMethod.GET, value = { "/getSegments",
			"/showupdateSegment", "/updateSegment", "/createSegment",
			"/deleteSegment", "/searchSegment" })
	public ModelAndView welcomeSegmentWalletManager() {
		List<Segment> list = segmentService.getAllSegments(kohlsRestAPIUrl);
		ModelAndView mav = new ModelAndView("walletmanager/segment");
		mav.addObject("selected_header", "segment");
		mav.addObject("segments", list);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createSegment")
	public ModelAndView createSegment(HttpServletRequest request,
			HttpSession session) {
		ModelAndView mav = segmentService.createSegment(kohlsRestAPIUrl,
				request, session);
		List<Segment> segmentlist = segmentService
				.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("selected_header", "segment");
		mav.addObject("segments", segmentlist);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/showupdateSegment")
	public ModelAndView getSegmentDetailsById(HttpServletRequest request,
			HttpSession session) {
		ModelAndView mav = segmentService.getSegmentDetailsById(
				kohlsRestAPIUrl, request, session);
		List<Segment> segmentlist = segmentService
				.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("segments", segmentlist);
		mav.addObject("selected_header", "segment");
		mav.addObject("update", "update");
		return mav;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateSegment")
	public ModelAndView updateSegment(HttpServletRequest request,
			HttpSession session) {
		ModelAndView mav = segmentService.updateSegment(kohlsRestAPIUrl,
				request, session);
		List<Segment> segmentlist = segmentService
				.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("selected_header", "segment");
		mav.addObject("segments", segmentlist);
		return mav;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateItemsInSegment")
	@ResponseBody
	public ServiceResponse updateItemsInSegment(
			@RequestParam("segmentInfo") String segmentInfo) {
		return segmentService
				.updateItemsInSegment(kohlsRestAPIUrl, segmentInfo);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSegment")
	public ModelAndView deleteSegment(HttpServletRequest request,
			HttpSession session) {

		ModelAndView mav = segmentService.deleteSegment(kohlsRestAPIUrl,
				request, session);
		List<Segment> segmentlist = segmentService
				.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("segments", segmentlist);
		mav.addObject("selected_header", "segment");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/searchSegment")
	public ModelAndView searchSegment(HttpServletRequest request,
			HttpSession session) {

		ModelAndView mav = new ModelAndView("walletmanager/segment");
		List<Segment> segmentlist = segmentService
				.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("search", true);
		mav.addObject("segments", segmentlist);
		mav.addObject("selected_header", "segment");
		mav.addObject("searchAttribute", request.getParameter("searchtext")
				.trim());
		return mav;
	}

	/**
	 * Manage Locations
	 */

	@RequestMapping(method = RequestMethod.GET, value = { "/manageLocations",
			"/getLocations", "/createLocation", "/updateLocation",
			"/showUpdateLocation", "/deleteLocation", "/searchLocation" })
	public ModelAndView getLocations() {
		List<Location> locations = manageLocationService
				.getLocations(kohlsRestAPIUrl);
		ModelAndView mav = new ModelAndView("walletmanager/location");
		mav.addObject("selected_header", "location");
		mav.addObject("locations", locations);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createLocation")
	public ModelAndView getLocations(HttpServletRequest request) {

		ModelAndView mav = manageLocationService.createLocation(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "location");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateLocation")
	public ModelAndView updateLocation(HttpServletRequest request) {

		ModelAndView mav = manageLocationService.updateLocation(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "location");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/showUpdateLocation")
	public ModelAndView getLocationDetailsById(HttpServletRequest request) {

		ModelAndView mav = manageLocationService.getLocationDetailsById(
				request, kohlsRestAPIUrl);
		mav.addObject("selected_header", "location");
		mav.addObject("update", "update");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteLocation")
	public ModelAndView deleteLocation(HttpServletRequest request) {

		ModelAndView mav = manageLocationService.deleteLocation(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "location");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/searchLocation")
	public ModelAndView searchLocation(HttpServletRequest request,
			HttpSession session) {

		ModelAndView mav = new ModelAndView("walletmanager/location");
		List<Location> locations = manageLocationService
				.getLocations(kohlsRestAPIUrl);
		mav.addObject("search", true);
		mav.addObject("locations", locations);
		mav.addObject("selected_header", "location");
		mav.addObject("searchAttribute", request.getParameter("searchtext")
				.trim());
		return mav;
	}

	/**
	 * Manage Offers
	 */

	@RequestMapping(method = RequestMethod.GET, value = { "/manageOffers",
			"/getOffers", "/createOffer", "/updateOffer", "/deleteOffer",
			"/showUpdateOffer" })
	public ModelAndView getOffers() {

		List<Offer> offers = manageOffersService.getOffers(kohlsRestAPIUrl);
		ModelAndView mav = new ModelAndView("walletmanager/offers");
		mav.addObject("offers", offers);
		mav.addObject("selected_header", "offer");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createOffer")
	public ModelAndView createOffer(HttpServletRequest request) {
		ModelAndView mav = manageOffersService.createOffer(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "offer");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateOffer")
	public ModelAndView updateOffer(HttpServletRequest request) {

		ModelAndView mav = manageOffersService.updateOffer(request,
				kohlsRestAPIUrl);

		mav.addObject("selected_header", "offer");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/showUpdateOffer")
	public ModelAndView getOfferDetailsById(HttpServletRequest request) {

		ModelAndView mav = manageOffersService.getOfferDetailsById(request,
				kohlsRestAPIUrl);
		mav.addObject("update", "update");
		mav.addObject("selected_header", "offer");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteOffer")
	public ModelAndView deleteOffer(HttpServletRequest request) {

		ModelAndView mav = manageOffersService.deleteOffer(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "offer");
		return mav;
	}

	/**
	 * Manage Arrangements
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/createArrangement")
	@ResponseBody
	public JSONObject createArrangement(@RequestBody Arrangement arrangement) {

		return manageArrangementsService.createArrangement(arrangement,
				kohlsRestAPIUrl);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateArrangement")
	@ResponseBody
	public ServiceResponse updateArrangement(
			@RequestParam("arrangementInfo") String arrangementInfo) {
		ServiceResponse serviceResponse = manageArrangementsService
				.updateArrangement(arrangementInfo, kohlsRestAPIUrl);

		return serviceResponse;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/showUpdateArrangement")
	@ResponseBody
	public JSONObject getArrangementDetailsById(
			@RequestParam("arrangementId") Long arrangementId) {
		Arrangement arrangement = manageArrangementsService
				.getArrangementDetailsById(arrangementId, kohlsRestAPIUrl);
		JSONArray items = manageArrangementsService.getItemsInArrangement(
				arrangementId, kohlsRestAPIUrl);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("arrangement", arrangement);
		jsonObject.put("itemsArray", items);
		return jsonObject;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteArrangement")
	@ResponseBody
	public ServiceResponse deleteArrangement(
			@RequestParam("arrangementId") Long arrangementId) {
		return manageArrangementsService.deleteArrangement(arrangementId,
				kohlsRestAPIUrl);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getProducts")
	@ResponseBody
	public JSONObject getProducts(@RequestParam("limit") Integer limit,
			@RequestParam("offset") Integer offset) {

		JSONObject productsData = manageArrangementsService.getProducts(
				kohlsRestAPIUrl, limit, offset);
		return productsData;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getOffer")
	@ResponseBody
	public JSONObject getOffer(@RequestParam("limit") Integer limit,
			@RequestParam("offset") Integer offset) {
		JSONObject couponsData = manageArrangementsService.getCoupons(
				kohlsRestAPIUrl, limit, offset);

		return couponsData;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getkohlscash")
	@ResponseBody
	public JSONObject getKohlsCash(@RequestParam("limit") Integer limit,
			@RequestParam("offset") Integer offset) {
		JSONObject cashData = manageArrangementsService.getCash(
				kohlsRestAPIUrl, limit, offset);
		return cashData;

	}

	/**
	 * Manage Collections
	 */

	@RequestMapping(method = RequestMethod.GET, value = { "/getCollections",
			"/manageCollections", "/createCollection", "/updateCollection" })
	public ModelAndView manageCollections(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("walletmanager/collections");
		List<Collections> collections = manageCollectionsService
				.getCollections(kohlsRestAPIUrl);
		List<Location> locations = manageLocationService
				.getLocations(kohlsRestAPIUrl);
		List<Arrangement> arrangements = manageArrangementsService
				.getArrangements(kohlsRestAPIUrl);
		List<Segment> segments = segmentService.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("collections", collections);
		mav.addObject("locations", locations);
		mav.addObject("selected_header", "collection");
		mav.addObject("arrangements", arrangements);
		mav.addObject("segments", segments);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createCollection")
	public ModelAndView createCollection(HttpServletRequest request) {
		ModelAndView mav = manageCollectionsService.createCollection(request,
				kohlsRestAPIUrl);
		List<Location> locations = manageLocationService
				.getLocations(kohlsRestAPIUrl);
		List<Arrangement> arrangements = manageArrangementsService
				.getArrangements(kohlsRestAPIUrl);
		List<Segment> segments = segmentService.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("locations", locations);
		mav.addObject("arrangements", arrangements);
		mav.addObject("selected_header", "collection");
		mav.addObject("segments", segments);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateCollection")
	public ModelAndView updateCollection(HttpServletRequest request) {

		ModelAndView mav = manageCollectionsService.updateCollection(request,
				kohlsRestAPIUrl);
		List<Location> locations = manageLocationService
				.getLocations(kohlsRestAPIUrl);
		List<Arrangement> arrangements = manageArrangementsService
				.getArrangements(kohlsRestAPIUrl);
		List<Segment> segments = segmentService.getAllSegments(kohlsRestAPIUrl);
		mav.addObject("locations", locations);
		mav.addObject("arrangements", arrangements);
		mav.addObject("selected_header", "collection");
		mav.addObject("segments", segments);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/showUpdateCollection")
	@ResponseBody
	public JSONObject getCollectionDetailsById(@RequestParam("id") Long id) {
		JSONObject jsonObject = manageCollectionsService
				.getCollectionDetailsById(kohlsRestAPIUrl, id);
		return jsonObject;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteCollection")
	@ResponseBody
	public ServiceResponse deleteCollection(
			@RequestParam("collectionId") Long collectionId) {
		return manageCollectionsService.deleteCollection(collectionId,
				kohlsRestAPIUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getArrangementsByCollectionId")
	@ResponseBody
	public List<Arrangement> getArrangementsByCollectionId(
			@RequestParam("collectionId") Long collectionId) {
		List<Arrangement> arrangementsResponse = manageCollectionsService
				.getArrangementsByCollectionId(collectionId, kohlsRestAPIUrl);
		return arrangementsResponse;
	}

	// Search Implementation

	@RequestMapping(method = RequestMethod.GET, value = "/getClientUserNames")
	@ResponseBody
	public List<String> getClientUserNameList(@RequestParam("term") String query) {

		return manageUserSegmentService.getClientUserNames(query.trim(),
				kohlsRestAPIUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSegmentNames")
	@ResponseBody
	public List<String> getSegmentNameList(@RequestParam("term") String query) {

		return segmentService.getSegmentNames(query.trim(), kohlsRestAPIUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/locationNameList")
	@ResponseBody
	public List<String> getLocationNameList(@RequestParam("term") String query) {

		return manageLocationService.getLocationNames(query.trim(),
				kohlsRestAPIUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/offerNameList")
	@ResponseBody
	public List<String> getOfferNameList(@RequestParam("term") String query) {

		return manageOffersService.getOfferNames(query.trim(), kohlsRestAPIUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/collectionNameList")
	@ResponseBody
	public List<String> getCollectionNameList(@RequestParam("term") String query) {

		return manageCollectionsService.getCollectionNames(query.trim(),
				kohlsRestAPIUrl);
	}
	@RequestMapping(method=RequestMethod.POST,value="/addMarkdownProduct")
	@ResponseBody
	public JSONObject addMarkdownProduct(@RequestBody String jsonObject){
		return manageCollectionsService.addMarkdownProduct(kohlsRestAPIUrl, jsonObject);
	}}
