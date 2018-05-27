package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import com.kbmc.model.Offer;

/**
 * Manage Offer Service Interface.
 * 
 * @author ZONE24X7
 * 
 */

public interface ManageOffersService {

	public List<Offer> getOffers(String kohlsRestAPIUrl);

	public ModelAndView createOffer(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView updateOffer(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView deleteOffer(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public ModelAndView getOfferDetailsById(HttpServletRequest request,
			String kohlsRestAPIUrl);

	public List<String> getOfferNames(String query, String kohlsRestAPIUrl);
}
