package com.kbmc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.service.UserManageService;

/**
 * This controller class can handle the login requests and login failure
 * requests.
 * 
 * @author ZONE24X7
 * 
 */

@Controller
public class LoginController {
	@Autowired
	private UserManageService userService;

	@RequestMapping(method = RequestMethod.GET, value = { "/", "/login",
			"/sendNewPassword", "/savePassword" })
	public ModelAndView welcome(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("login/index");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loginfailed")
	public ModelAndView loginFailed(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("login/index");
		mav.addObject("error", true);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/sendNewPassword")
	public ModelAndView generateNewPassword(HttpServletRequest request) {
		return userService.generatePassword(request);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/changePassword")
	public ModelAndView changePassword(@RequestParam String resetCode) {

		ModelAndView mav = userService.resetCodeOfUser(resetCode);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/savePassword")
	public ModelAndView saveNewPassword(HttpServletRequest request) {
		ModelAndView mav = userService.saveNewPassword(request);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resetPassword")
	public ModelAndView resetPassword() {
		ModelAndView mav = new ModelAndView("login/resetpassword");
		return mav;
	}

}
