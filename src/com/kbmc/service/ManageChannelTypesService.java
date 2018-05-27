package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.ChannelType;

/**
 * Manage ChannelType interface.
 * 
 * @author ZONE24X7
 * 
 */

public interface ManageChannelTypesService {
	
	public List<ChannelType> getChannelTypes(String kohlsRestAPIUrl);
	
	public ModelAndView getChannelTypeDetailsById(HttpServletRequest request,String kohlsRestAPIUrl);
	
	public ModelAndView updateChannelType(HttpServletRequest request,String kohlsRestAPIUrl);
	
	public List<String> getChannelTypeNameList(String query,String kohlsRestAPIUrl);

}
