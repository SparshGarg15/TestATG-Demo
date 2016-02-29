package com.app.droplets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;

public class TestDroplet extends DynamoServlet {

	String name;

	public TestDroplet() {
		System.out.println("In test droplet constructor");
	}

	@Override
	public void service(DynamoHttpServletRequest req,
			DynamoHttpServletResponse res) throws ServletException, IOException {

		ServletOutputStream out = res.getOutputStream();
		out.println("Hello World! My name is" + name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("Setting name attribute to " + name);
		this.name = name;
	}

}
