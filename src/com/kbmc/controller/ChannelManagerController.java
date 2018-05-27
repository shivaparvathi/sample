package com.kbmc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Channel;
import com.kbmc.model.ChannelType;
import com.kbmc.model.Collections;
import com.kbmc.service.ManageChannelTypesService;
import com.kbmc.service.ManageChannelsService;
import com.kbmc.service.ManageCollectionsService;
import com.kbmc.model.Channel;
import com.kbmc.model.ChannelType;
import com.kbmc.model.Collections;
/**
 * This controller class can handle the channel operations
 * 
 * @author ZONE24X7
 */

@Controller
@RequestMapping(value = { "/channelManager", "/admin" })
public class ChannelManagerController {

	@Value(value = "${RestAPIUrl}")
	private String kohlsRestAPIUrl;

	@Autowired
	private ManageChannelTypesService manageChannelTypesService;

	@Autowired
	private ManageChannelsService manageChannelsService;

	@Autowired
	private ManageCollectionsService manageCollectionsService;

	@RequestMapping(method = RequestMethod.GET, value = { "/getChannelTypes",
			"/channels", "/updateChannelType", "/showUpdateChannelType" })
	public ModelAndView welcomeChannelManager() {

		ModelAndView mav = new ModelAndView("channelmanager/channeltypes");
		List<ChannelType> channelTypes = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		mav.addObject("selected_header", "channelType");
		mav.addObject("channelTypes", channelTypes);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateChannelType")
	public ModelAndView updateChannelType(HttpServletRequest request) {
		ModelAndView mav = manageChannelTypesService.updateChannelType(request,
				kohlsRestAPIUrl);
		mav.addObject("selected_header", "channelType");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/showUpdateChannelType")
	public ModelAndView getChannelTypeDetailsByName(HttpServletRequest request) {
		ModelAndView mav = manageChannelTypesService
				.getChannelTypeDetailsById(request, kohlsRestAPIUrl);
		mav.addObject("selected_header", "channelType");
		mav.addObject("update", "update");
		return mav;
	}

	/**
	 * Manage Channels
	 */
	@RequestMapping(method = RequestMethod.GET, value = { "/getChannels",
			"/createChannel", "/deleteChannel", "/updateChannel",
			"/showUpdateChannel" })
	public ModelAndView getChannels() {

		List<Channel> channels = manageChannelsService
				.getChannels(kohlsRestAPIUrl);
		List<Collections> collections = manageCollectionsService
				.getCollections(kohlsRestAPIUrl);
		List<ChannelType> channelTypes = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		ModelAndView channelsMav = new ModelAndView("channelmanager/channels");
		channelsMav.addObject("selected_header", "channel");
		channelsMav.addObject("collections", collections);
		channelsMav.addObject("channels", channels);
		channelsMav.addObject("channelTypes", channelTypes);
		return channelsMav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createChannel")
	public ModelAndView createChannel(HttpServletRequest request) {

		ModelAndView createChannelmav = manageChannelsService.createChannel(
				request, kohlsRestAPIUrl);
		List<Collections> collections = manageCollectionsService
				.getCollections(kohlsRestAPIUrl);
		List<ChannelType> channelTypes = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		createChannelmav.addObject("selected_header", "channel");
		List<Channel> channels = manageChannelsService
				.getChannels(kohlsRestAPIUrl);
		createChannelmav.addObject("channels", channels);
		createChannelmav.addObject("channelTypes", channelTypes);
		createChannelmav.addObject("collections", collections);
		return createChannelmav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteChannel")
	public ModelAndView deleteChannel(HttpServletRequest request) {

		ModelAndView deleteChannelmav = manageChannelsService.deleteChannel(
				request, kohlsRestAPIUrl);
		List<Channel> channels = manageChannelsService
				.getChannels(kohlsRestAPIUrl);
		List<Collections> collections = manageCollectionsService
				.getCollections(kohlsRestAPIUrl);
		List<ChannelType> channelTypes = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		deleteChannelmav.addObject("selected_header", "channel");
		deleteChannelmav.addObject("channels", channels);
		deleteChannelmav.addObject("channelTypes", channelTypes);
		deleteChannelmav.addObject("collections", collections);
		return deleteChannelmav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateChannel")
	public ModelAndView updateChannel(HttpServletRequest request) {
		ModelAndView mav = manageChannelsService.updateChannel(request,
				kohlsRestAPIUrl);
		mav.addObject("collections",
				manageCollectionsService.getCollections(kohlsRestAPIUrl));
		List<Channel> channels = manageChannelsService
				.getChannels(kohlsRestAPIUrl);
		List<ChannelType> channelTypes = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		List<Collections> collections = manageCollectionsService
				.getCollections(kohlsRestAPIUrl);
		mav.addObject("channels", channels);
		mav.addObject("channelTypes", channelTypes);
		mav.addObject("selected_header", "channel");
		mav.addObject("collections", collections);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/showUpdateChannel")
	public ModelAndView getChannelDetailsById(HttpServletRequest request) {
		ModelAndView mav = manageChannelsService.getChannelDetailsById(request,
				kohlsRestAPIUrl);
		List<Channel> channels = manageChannelsService
				.getChannels(kohlsRestAPIUrl);

		List<ChannelType> channelTypes = manageChannelTypesService
				.getChannelTypes(kohlsRestAPIUrl);
		mav.addObject("collections",
				manageCollectionsService.getCollections(kohlsRestAPIUrl));
		mav.addObject("update", "update");
		mav.addObject("channels", channels);
		mav.addObject("channelTypes", channelTypes);
		mav.addObject("selected_header", "channel");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/channelNameList")
	@ResponseBody
	public List<String> getChannelnameList(@RequestParam("term") String query) {
		return manageChannelsService.getChannelNameList(query.trim(),
				kohlsRestAPIUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/channelTypeNameList")
	@ResponseBody
	public List<String> getChannelTypenameList(
			@RequestParam("term") String query) {
		return manageChannelTypesService.getChannelTypeNameList(query.trim(),
				kohlsRestAPIUrl);
	}

}
