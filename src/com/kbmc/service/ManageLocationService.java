package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Location;

/**
 * Manage Location Service Interface.
 * 
 * @author ZONE24X7
 * 
 */
public interface ManageLocationService {

	public List<Location> getLocations(String kohlsRestAPIUrl);

	public ModelAndView createLocation(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView updateLocation(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView deleteLocation(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView getLocationDetailsById(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public List<String> getLocationNames(String query, String kohlsRestAPIUrl);

}
