package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Segment;
import com.kbmc.model.ServiceResponse;

/**
 * Manage Segment Service interface.
 * 
 * @author ZONE24X7
 */

public interface SegmentService {

	public List<Segment> getAllSegments(String url);

	public ModelAndView createSegment(String url, HttpServletRequest request,
			HttpSession session);

	public ModelAndView getSegmentDetailsById(String url,
			HttpServletRequest request, HttpSession session);

	public ModelAndView updateSegment(String url, HttpServletRequest request,
			HttpSession session);

	public ModelAndView deleteSegment(String kohlsRestAPIUrl,
			HttpServletRequest request, HttpSession session);

	public List<String> getSegmentNames(String query, String kohlsRestAPIUrl);
	
	public ServiceResponse updateItemsInSegment(String kohlsRestAPIUrl,String segmentInfo);


}
