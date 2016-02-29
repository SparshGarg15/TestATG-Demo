/**
 * 
 */
package com.app.droplets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;

/**
 * @author prashant.joshi (prashant.joshi@cygrp.com)
 * @version (11-Dec-2013)
 */
public class MyFirstDroplet extends DynamoServlet {

	@Override
	public void service(DynamoHttpServletRequest req,
			DynamoHttpServletResponse res) throws ServletException, IOException {
		Integer loop = Integer
				.parseInt((String) req.getObjectParameter("loop"));
		Cookie cookies[] = req.getCookies();
		Cookie cookie = new Cookie("myCookie", req.getSession().getId());
		cookie.setPath(req.getContextPath());
		res.addCookie(cookie);
		res.sendRedirect("someNewUrl");
		for (int i = 0; i < loop; i++) {
			req.setParameter("value", i);
			req.serviceParameter("output", req, res);
		}
	}
}
