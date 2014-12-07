package com.eltacodeldiablo.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.eltacodeldiablo.web.controller.HomeController;

public class FilterThymeLeaf implements Filter {

	private ServletContext	servletContext;

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {
		if (!process((HttpServletRequest) request, (HttpServletResponse) response)) {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {

		this.servletContext = filterConfig.getServletContext();
	}

	private TemplateEngine initializeTemplateEngine() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		// XHTML is the default mode, but we will set it anyway for better understanding of code
		templateResolver.setTemplateMode("XHTML");
		// This will convert "home" to "/WEB-INF/templates/home.html"
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		// Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		// Cache is set to true by default. Set to false if you want templates to
		// be automatically updated when modified.
		templateResolver.setCacheable(true);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	private boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			/*
			 * Query controller/URL mapping and obtain the controller
			 * that will process the request. If no controller is available,
			 * return false and let other filters/servlets process the request.
			 */
			HomeController controller = new HomeController();
			/*
			 * Obtain the TemplateEngine instance.
			 */
			TemplateEngine templateEngine = initializeTemplateEngine();
			/*
			 * Write the response headers
			 */
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			/*
			 * Execute the controller and process view template,
			 * writing the results to the response writer.
			 */
			controller.process(request, response, servletContext, templateEngine);
			return true;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
