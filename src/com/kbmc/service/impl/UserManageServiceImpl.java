package com.kbmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kbmc.dao.impl.RoleDAOImpl;
import com.kbmc.dao.impl.UserDAOImpl;
import com.kbmc.dao.impl.UserRoleDAOImpl;
import com.kbmc.model.GenericException;
import com.kbmc.model.Profile;
import com.kbmc.model.Role;
import com.kbmc.model.ServiceResponse;
import com.kbmc.model.User;
import com.kbmc.model.UserDetails;
import com.kbmc.model.UserRole;
import com.kbmc.service.UserManageService;
import com.kbmc.service.impl.MD5Hash;

/**
 * User Manage Service implementation class.
 * 
 * @author ZONE24X7
 */

public class UserManageServiceImpl implements UserManageService {

	private String from;
	private String subject;
	private UserDAOImpl userDAO;
	private RoleDAOImpl roleDAO;
	private UserRoleDAOImpl userRoleDAO;
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	public void setUserDAO(UserDAOImpl userDAO) {
		this.userDAO = userDAO;
	}

	public void setRoleDAO(RoleDAOImpl roleDAO) {
		this.roleDAO = roleDAO;
	}

	public void setUserRoleDAO(UserRoleDAOImpl userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * Create new KBMC user
	 * 
	 * @param request, session
	 * @return ModelAndView object
	 */

	@Override
	public ModelAndView addUser(HttpServletRequest request, HttpSession session) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userRole = "ROLE_" + request.getParameter("selectionrole");
		String status = request.getParameter("status");
		Role roleObj = (Role) roleDAO.findByName(userRole).listIterator()
				.next();
		Long roleid = roleObj.getId();

		if (!username.isEmpty() && !password.isEmpty()) {

			if (userDAO.existUser(username) != 0) {
				ModelAndView mav = new ModelAndView("redirect:redirectAdmin");
				return mav;
			}

			User loginUser = (User) userDAO
					.findByUsername(session.getAttribute("username"))
					.listIterator().next();
			User user = new User();
			user.setFirstname(request.getParameter("fname"));
			user.setLastname(request.getParameter("lname"));

			user.setUsername(username);
			user.setCreatedBy(loginUser.getId());
			user.setModifiedBy(loginUser.getId());
			user.setCreated(new Date());
			user.setModified(new Date());
			user.setPassword(new MD5Hash().MD5Encrypt(password));
			user.setEmail(request.getParameter("email"));
			if (status.equals("enable")) {
				user.setEnabled(true);
			} else {
				user.setEnabled(false);
			}

			userDAO.save(user);
			UserRole userroleObj = new UserRole();
			userroleObj.setRoleId(roleid);
			userroleObj.setUserId(user.getId());
			userRoleDAO.save(userroleObj);
		}

		ModelAndView mav = new ModelAndView("redirect:welcome");
		mav.addObject("isSuccessMessage", "true");
		return mav;

	}

	/**
	 * Delete the existing KBMC user
	 * 
	 * @param request, session
	 * @return ModelAndView object
	 */
	@Override
	public ModelAndView deleteUser(HttpServletRequest request,
			HttpSession session) {
		long userId = Long.parseLong(request.getParameter("id"));

		User loginUser = (User) userDAO
				.findByUsername(session.getAttribute("username"))
				.listIterator().next();

		User user = userDAO.findById(userId);
		user.setModifiedBy(loginUser.getId());
		user.setModified(new Date());
		user.setEnabled(false);
		userDAO.merge(user);

		List<Role> rolesList = getRoles();
		List<UserDetails> userDetails = getUserDetails();

		ModelAndView mav = new ModelAndView("admin/user");
		mav.addObject("role", rolesList);
		mav.addObject("userdetails", userDetails);

		if (loginUser.getUsername().equalsIgnoreCase(user.getUsername())) {
			mav.addObject("currentuser", user.getUsername());
		}
		mav.addObject("successMessage", "User Deleted SuccessFully");
		return mav;
	}

	/**
	 * Get the KBMC user details by Id
	 * 
	 * @param request, session
	 * @return ModelAndView object
	 */

	@Override
	public ModelAndView getUserDetailsById(HttpServletRequest request,
			HttpSession session) {
		long userId = Long.parseLong(request.getParameter("id"));
		User user = userDAO.findById(userId);
		List<Role> role = getRoles();
		UserRole userrole = (UserRole) userRoleDAO.findByUserId(user.getId())
				.listIterator().next();
		String[] userrolename = roleDAO.findById(userrole.getRoleId())
				.getName().split("_");

		List<UserDetails> userdetails = getUserDetails();
		ModelAndView mav = new ModelAndView("admin/user");
		mav.addObject("role", role);
		mav.addObject("userdetails", userdetails);
		mav.addObject("userrolename", userrolename[1]);
		mav.addObject("user", user);
		return mav;
	}

	/**
	 * Update existing KBMC user
	 * 
	 * @param request, session
	 * @return ModelAndView object
	 */

	@Override
	public ModelAndView updateUser(HttpServletRequest request,
			HttpSession session) {
		Long userId = Long.parseLong(request.getParameter("id"));

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String status = request.getParameter("status");
		if (!username.isEmpty() && !password.isEmpty()) {

			User user = userDAO.findById(userId);
			User loginUser = (User) userDAO
					.findByUsername(session.getAttribute("username"))
					.listIterator().next();
			user.setId(userId);
			user.setModifiedBy(loginUser.getId());
			user.setModified(new Date());
			user.setFirstname(request.getParameter("fname"));
			user.setLastname(request.getParameter("lname"));

			user.setUsername(request.getParameter("username"));
			user.setPassword(new MD5Hash().MD5Encrypt(request
					.getParameter("password")));
			user.setEmail(email);
			if (status.equals("enable")) {
				user.setEnabled(true);
			} else {
				user.setEnabled(false);
			}

			String rolename = request.getParameter("selectionrole");
			rolename = "ROLE_" + rolename;
			Role role = (Role) roleDAO.findByName(rolename).listIterator()
					.next();
			UserRole userRole = (UserRole) userRoleDAO.findByUserId(userId)
					.listIterator().next();
			userRole.setRoleId(role.getId());
			userRoleDAO.merge(userRole);
			userDAO.merge(user);
		}
		List<Role> role = getRoles();
		List<UserDetails> userDetails = getUserDetails();
		ModelAndView mav = new ModelAndView("admin/user");
		mav.addObject("role", role);
		mav.addObject("userdetails", userDetails);
		mav.addObject("successMessage", "User Updated SuccesFully");
		return mav;
	}

	/**
	 * Get the existing KBMC user list
	 * 
	 * @return List<UserDetails> object
	 */
	public List<UserDetails> getUserDetails() {
		ArrayList<UserDetails> userdetails = new ArrayList<UserDetails>();

		List<User> usersList = userDAO.getUserEnabled();

		for (User user : usersList) {
			UserDetails userInfo = new UserDetails();
			userInfo.setUser(user);

			UserRole userRole = (UserRole) userRoleDAO
					.findByUserId(user.getId()).listIterator().next();
			String[] roleName = roleDAO.findById(userRole.getRoleId())
					.getName().split("_");
			userInfo.setUserrole(roleName[1]);
			userdetails.add(userInfo);
		}
		return userdetails;

	}

	/**
	 * Get the existing KBMC role list
	 * 
	 * @return List<Role> object
	 */
	@Override
	public List<Role> getRoles() {

		List<Role> rolesList = roleDAO.findAll();
		List<Role> appRoles = new ArrayList<Role>();
		for (Role role : rolesList) {
			Role userRole = new Role();
			String[] user_role = role.getName().split("_");
			userRole.setName(user_role[1]);
			appRoles.add(userRole);
		}
		return appRoles;
	}

	/**
	 * Send the password recovery instructions to existing KBMC user
	 * 
	 * @param request
	 * @return ModelAndView object
	 */
	@Override
	public ModelAndView generatePassword(HttpServletRequest request) {
		String message = null;
		ModelAndView mav = null;
		String username = request.getParameter("username");
		int userlist = userDAO.existUser(username);
		if (userlist == 0) {
			message = "Please Enter Valid Username";
			mav = new ModelAndView("login/resetpassword");
			mav.addObject("message", message);
		} else {
			User user = userDAO.findByUsername(username).listIterator().next();
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			user.setResetcode(randomUUIDString);
			userDAO.merge(user);
			String to = user.getEmail();
			String msg = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ "/changePassword?resetCode=" + user.getResetcode();
			sendMail(from, to, subject, msg, request);
			message = "An email with instructions to choose a new password has been sent to you.";
			mav = new ModelAndView("login/index");
			mav.addObject("message", message);
		}

		return mav;
	}

	/**
	 * Send email to the existing KBMC user
	 * 
	 * @param from, to, subject, message, request
	 */
	public void sendMail(String from, String to, String subject, String msg,
			HttpServletRequest request) {

		final String fromAddress = from;
		final String toAddress = to;
		final String subjectAddress = subject;
		final String msgAddress = msg;
		final String appURL = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "unchecked", "deprecation" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(toAddress);
				message.setFrom(fromAddress);
				message.setSubject(subjectAddress);
				Map model = new HashMap();
				model.put("msg", msgAddress);
				model.put("appURL", appURL);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "content.vm", model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
	}

	/**
	 * Validate the reset code of the user
	 * 
	 * @param resetCode
	 * @return ModelAndView object
	 */
	@Override
	public ModelAndView resetCodeOfUser(String resetCode) {

		ModelAndView mav;
		if (userDAO.existResetCode(resetCode) == 1) {
			User user = userDAO.findByResetcode(resetCode).listIterator()
					.next();
			mav = new ModelAndView("login/changepassword");
			mav.addObject("username", user.getUsername());
		} else {
			mav = new ModelAndView("login/index");

		}
		return mav;

	}

	/**
	 * Save the new password for the user in the forgot password mechanism
	 * 
	 * @param request
	 * @return ModelAndView object
	 */
	@Override
	public ModelAndView saveNewPassword(HttpServletRequest request) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userDAO.findByUsername(username).listIterator().next();
		user.setResetDate(new Date());
		user.setResetcode(null);
		user.setPassword(new MD5Hash().MD5Encrypt(password));
		userDAO.merge(user);
		ModelAndView mav = new ModelAndView("login/index");
		String msg = "Your Password is Changed Successfully";
		mav.addObject("msg", msg);
		return mav;
	}

	/**
	 * Search functionality for KBMC users
	 * 
	 * @param search
	 * @return List<UserDetails> object
	 */
	@Override
	public List<UserDetails> search(String search) {
		ArrayList<UserDetails> userdetails = new ArrayList<UserDetails>();
		if (search.matches("[^a-zA-Z0-9]")) {
			search = "\\" + search;
		}
		List<User> list = userDAO.search(search);
		for (User user : list) {
			UserDetails details = new UserDetails();
			details.setUser(user);
			UserRole userrole = (UserRole) userRoleDAO
					.findByUserId(user.getId()).listIterator().next();
			String[] rName = roleDAO.findById(userrole.getRoleId()).getName()
					.split("_");
			details.setUserrole(rName[1]);
			userdetails.add(details);
		}

		return userdetails;

	}

	@Override
	public List<String> getusernames(String query) {

		String username = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<User> users = userDAO.findAll();
		for (User user : users) {
			username = user.getUsername().toLowerCase();
			if (username.contains(query)) {
				matched.add(user.getUsername());
			}
		}
		return matched;

	}

	/**
	 * Profile Page Implementation for the logged-in user (wallet manager and
	 * channel manager)
	 * 
	 * @param session, role
	 * @return ModelAndView object
	 */
	@Override
	public ModelAndView profile(HttpSession session, String role) {
		String roleName = null;
		if (role.equals("walletManager")) {
			roleName = "Wallet Manager";
		} else if (role.equals("channelManager")) {
			roleName = "Channel Manager";
		} else {
			ModelAndView mav = new ModelAndView("admin/error");
			return mav;
		}
		String username = (String) session.getAttribute("username");
		User user = (User) userDAO.findByUsername(username).listIterator()
				.next();

		Profile profile = new Profile();
		profile.setFirstname(user.getFirstname());
		profile.setLastname(user.getLastname());
		profile.setUsername(user.getUsername());
		profile.setRole(roleName);
		profile.setEmail(user.getEmail());

		ModelAndView mav = new ModelAndView("admin/profile");
		mav.addObject("profile", profile);
		mav.addObject("selected_header", "profile");
		return mav;
	}

	/**
	 * Update user details (firstname, lastname and email)
	 * 
	 * @param userData, request, session
	 * @return ServiceResponse object
	 */
	@Override
	public ServiceResponse profileupdate(String userData,
			HttpServletRequest request, HttpSession session) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			jsonObject = (JSONObject) parser.parse(userData);
			String name = (String) session.getAttribute("username");
			User loginUser = (User) userDAO.findByUsername(name).listIterator()
					.next();
			loginUser.setFirstname((String) jsonObject.get("firstName"));
			loginUser.setLastname((String) jsonObject.get("lastName"));
			loginUser.setEmail((String) jsonObject.get("email"));
			userDAO.merge(loginUser);
			serviceResponse.setMessage("success");

		} catch (ParseException exception ) {
			exception.printStackTrace();
			throw new GenericException(exception.getMessage());
		}
		return serviceResponse;
	}

	/**
	 * Update user password
	 * 
	 * @param request, session, role
	 * @return ModelAndView object
	 */
	@Override
	public ModelAndView passwordChange(HttpServletRequest request,
			HttpSession session, String role) {
		String roleName = null;
		String name = (String) session.getAttribute("username");
		User user = (User) userDAO.findByUsername(name).listIterator().next();
		if (role.equals("walletManager")) {
			roleName = "Wallet Manager";
		} else if (role.equals("channelManager")) {
			roleName = "Channel Manager";
		} else {
			ModelAndView mav = new ModelAndView("admin/error");
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("admin/profile");
		String actualPassword = user.getPassword();
		String EncryptedPassword = new MD5Hash().MD5Encrypt(request
				.getParameter("currentPassword"));
		if (actualPassword.equals(EncryptedPassword)) {
			user.setPassword(new MD5Hash().MD5Encrypt(request
					.getParameter("newPassword")));
			userDAO.merge(user);
			mav.addObject("message", "Password Changed Succesfully");
		} else {
			mav.addObject("errormsg", true);
		}
		mav.addObject("selected_header", "profile");

		Profile profile = new Profile();
		profile.setFirstname(user.getFirstname());
		profile.setLastname(user.getLastname());
		profile.setUsername(user.getUsername());
		profile.setRole(roleName);
		profile.setEmail(user.getEmail());
		mav.addObject("profile", profile);
		return mav;
	}

}
