package com.kbmc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Role;
import com.kbmc.model.ServiceResponse;
import com.kbmc.model.UserDetails;
import com.kbmc.service.UserManageService;

/**
 * This controller class can handle the user operations(create, update, delete)
 * 
 * @author ZONE24X7
 */
@Controller
public class AdminController {

	@Autowired
	private UserManageService userService;

	@RequestMapping(method = RequestMethod.GET, value = { "/admin/welcome" })
	public ModelAndView welcomeAdmin() {

		ModelAndView mav = new ModelAndView("admin/user");
		List<Role> role = userService.getRoles();
		List<UserDetails> user = userService.getUserDetails();
		mav.addObject("userdetails", user);
		mav.addObject("role", role);
		mav.addObject("selected_header", "user");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/admin/redirectAdmin" })
	public ModelAndView redirectAdmin() {

		ModelAndView mav = new ModelAndView("admin/user");
		List<Role> role = userService.getRoles();
		List<UserDetails> user = userService.getUserDetails();
		mav.addObject("userdetails", user);
		mav.addObject("create", "create");
		mav.addObject("selected_header", "user");
		mav.addObject("errormsg", true);
		mav.addObject("role", role);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/admin/getuser",
			"/admin/addUser", "/admin/deleteUser", "/admin/updateUser",
			"/admin/showUpdateUser", "/admin/searchUser" })
	public ModelAndView getUser(HttpServletRequest request, HttpSession session) {
		List<UserDetails> userdetails = userService.getUserDetails();
		// list all roles
		List<Role> role = userService.getRoles();
		ModelAndView mav = new ModelAndView("admin/user");
		mav.addObject("role", role);
		mav.addObject("userdetails", userdetails);
		mav.addObject("selected_header", "user");
		mav.addObject("create", "create");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/addUser")
	public ModelAndView addUser(HttpServletRequest request, HttpSession session) {
		ModelAndView showUserView = userService.addUser(request, session);
		return showUserView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/updateUser")
	public ModelAndView updateUser(HttpServletRequest request,
			HttpSession session) {
		ModelAndView updateUserView = userService.updateUser(request, session);
		updateUserView.addObject("create", "create");
		updateUserView.addObject("selected_header", "user");
		return updateUserView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/deleteUser")
	public ModelAndView deleteUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		ModelAndView deleteUserView = userService.deleteUser(request, session);
		Map<String, Object> map = deleteUserView.getModel();
		if (map.containsKey("currentuser")) {
			response.sendRedirect("../j_spring_security_logout");
		}
		deleteUserView.addObject("create", "create");
		deleteUserView.addObject("selected_header", "user");
		return deleteUserView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/showUpdateUser")
	public ModelAndView getUserDetailsById(HttpServletRequest request,
			HttpSession session) {
		ModelAndView showUpdateUserView = userService.getUserDetailsById(
				request, session);
		showUpdateUserView.addObject("update", "update");
		showUpdateUserView.addObject("selected_header", "user");
		return showUpdateUserView;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin/searchUser")
	public ModelAndView searchUser(HttpServletRequest request,
			HttpSession session) {
		List<UserDetails> user_search = new ArrayList<UserDetails>();
		String username = request.getParameter("searchtext").trim();
		user_search = userService.search(username);
		List<Role> role = userService.getRoles();

		ModelAndView mav = new ModelAndView("admin/user");

		if (username.length() == 0) {
			mav.addObject("userdetails", user_search);
		}
		if (user_search.size() == 0) {
			List<UserDetails> list = userService.getUserDetails();
			mav.addObject("message", "No such user exists");

		} else {

			mav.addObject("userdetails", user_search);
		}
		mav.addObject("role", role);
		mav.addObject("selected_header", "user");
		mav.addObject("search", username);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admin/usernameList")
	@ResponseBody
	public List<String> getUsernameList(@RequestParam("term") String query) {
		return userService.getusernames(query.trim());
	}

	/**
	 * Logged-in User Profile Implementation
	 */

	@RequestMapping(method = RequestMethod.GET, value = { "/{role}/profile",
			"/{role}/changepassword" })
	public ModelAndView myprofile(HttpSession session, @PathVariable String role) {
		return userService.profile(session, role);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{role}/profilechange")
	@ResponseBody
	public ServiceResponse editProfile(@RequestParam("json") String userData,
			HttpServletRequest request, HttpSession session) {
		ServiceResponse response = userService.profileupdate(userData, request,
				session);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{role}/changepassword")
	public ModelAndView changePassword(HttpServletRequest request,
			HttpSession session, @PathVariable String role) {
		ModelAndView mav = userService.passwordChange(request, session, role);
		return mav;
	}

}
