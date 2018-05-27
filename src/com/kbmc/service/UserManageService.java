package com.kbmc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.kbmc.model.Role;
import com.kbmc.model.UserDetails;
import com.kbmc.model.ServiceResponse;

/**
 * User Manage Service interface.
 * 
 * @author ZONE24X7
 */
public interface UserManageService {

	public ModelAndView addUser(HttpServletRequest request, HttpSession session);

	public ModelAndView deleteUser(HttpServletRequest request,
			HttpSession session);

	public ModelAndView getUserDetailsById(HttpServletRequest request,
			HttpSession session);

	public ModelAndView updateUser(HttpServletRequest request,
			HttpSession session);

	public List<UserDetails> getUserDetails();

	public List<Role> getRoles();

	public ModelAndView generatePassword(HttpServletRequest request);

	public ModelAndView resetCodeOfUser(String resetCode);

	public ModelAndView saveNewPassword(HttpServletRequest request);

	public List<UserDetails> search(String search);

	public List<String> getusernames(String query);

	public ModelAndView profile(HttpSession session, String role);

	public ServiceResponse profileupdate(String userData,
			HttpServletRequest request, HttpSession session);

	public ModelAndView passwordChange(HttpServletRequest request,
			HttpSession session, String role);
}
