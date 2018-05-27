package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Channel;

/**
 * Manage Channels Service Interface class.
 * 
 * @author ZONE24X7
 */
 

public interface ManageChannelsService {
	
	public List<Channel> getChannels(String kohlsRestAPIUrl);
	
	public ModelAndView createChannel(HttpServletRequest request,String kohlsRestAPIUrl);
	
	public ModelAndView deleteChannel(HttpServletRequest request,String kohlsRestAPIUrl);
	
	public ModelAndView updateChannel(HttpServletRequest request,String kohlsRestAPIUrl);
	
	public ModelAndView getChannelDetailsById(HttpServletRequest request,String kohlsRestAPIUrl);
	
	public List<String> getChannelNameList(String query,String kohlsRestAPIUrl);

}
