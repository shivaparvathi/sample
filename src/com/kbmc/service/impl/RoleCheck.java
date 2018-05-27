package com.kbmc.service.impl;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * This class will redirect the user to the authorized urls based on user role
 * 
 * @author ZONE24X7
 */

public class RoleCheck implements AuthenticationSuccessHandler {

	public static String username;

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		RoleCheck.username = username;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute("username", authentication.getName());
		setUsername(authentication.getName());
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication
				.getAuthorities());
		session.setAttribute("role", roles);
		if (roles.contains("ROLE_Administrator")) {
			response.sendRedirect("admin/welcome");
			return;
		} else if (roles.contains("ROLE_Wallet Manager")) {
			response.sendRedirect("walletManager/segments");
			return;
		} else if (roles.contains("ROLE_Channel Manager")) {
			response.sendRedirect("channelManager/channels");
			return;
		} 

	}

}
