package com.eltacodeldiablo.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.eltacodeldiablo.business.domain.Product;

public class HomeController {
	public void process(final HttpServletRequest request, final HttpServletResponse response,
			final ServletContext servletContext, final TemplateEngine templateEngine) throws Exception {
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		List<Product> products = new ArrayList<Product>();

		ctx.setVariable("today", Calendar.getInstance());
		ctx.setVariable("products", products);
		templateEngine.process("home", ctx, response.getWriter());
	}
}
