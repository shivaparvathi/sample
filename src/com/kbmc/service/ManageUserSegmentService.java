package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Segment;
import com.kbmc.model.UserSegment;

/**
 * Manage User Segment Service interface.
 * 
 * @author ZONE24X7
 */

public interface ManageUserSegmentService {

	public List<UserSegment> getUsers(String url);

	public ModelAndView updateUserSegment(HttpServletRequest request, String url);

	public ModelAndView getUsersSegments(String url);

	public List<String> getClientUserNames(String query, String kohlsRestAPIUrl);
}
