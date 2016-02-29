/**
 * 
 */
package com.app.handlers;

import java.io.IOException;

import javax.servlet.ServletException;

import atg.repository.RepositoryException;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.userprofiling.ProfileFormHandler;

/**
 * Handle operations related to Login
 * 
 * @author prashant.joshi (prashant.joshi@cygrp.com)
 * @version (13-Dec-2013)
 */
public class LoginHandler extends ProfileFormHandler {

	private String loginSuccessUrl;
	private String loginErrorUrl;

	public boolean handleSubmit(DynamoHttpServletRequest req,
			DynamoHttpServletResponse res) throws ServletException,
			IOException, RepositoryException {
		this.handleLogin(req, res);
		return checkFormRedirect(loginSuccessUrl, loginErrorUrl, req, res);
	}

	/**
	 * @return the loginSuccessUrl
	 */
	public String getLoginSuccessUrl() {
		return loginSuccessUrl;
	}

	/**
	 * @param loginSuccessUrl
	 *            the loginSuccessUrl to set
	 */
	public void setLoginSuccessUrl(String loginSuccessUrl) {
		this.loginSuccessUrl = loginSuccessUrl;
	}

	/**
	 * @return the loginErrorUrl
	 */
	public String getLoginErrorUrl() {
		return loginErrorUrl;
	}

	/**
	 * @param loginErrorUrl
	 *            the loginErrorUrl to set
	 */
	public void setLoginErrorUrl(String loginErrorUrl) {
		this.loginErrorUrl = loginErrorUrl;
	}

}
