package com.vcm.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.service.LearnMoreService;

/**
 * This Servlet is used to search JCR
 */

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Members Fund Listing Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.resourceTypes=" + "sling/servlet/default",
		"sling.servlet.selectors=" + "learnmore", "sling.servlet.extensions=" + "html" })
public class LearnMoreServlet extends SlingAllMethodsServlet {

	/**
	 * Constant serialVersionUID
	 */
	private static final long serialVersionUID = -1403127448277914274L;

	/** The Constant LOGGER. */
	private static final Logger LOG = LoggerFactory.getLogger(LearnMoreServlet.class);

	@Reference
	private transient LearnMoreService learnMoreServiceImpl;

	/**
	 * @param learnMoreServiceImpl the learnMoreServiceImpl to set
	 */
	public void setLearnMoreServiceImpl(LearnMoreService learnMoreServiceImpl) {
		this.learnMoreServiceImpl = learnMoreServiceImpl;
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOG.debug("Inside Learn more servlet");
		response.setContentType("text/html");
		response.getWriter().write(learnMoreServiceImpl.getRenderedHTML(request, response));
		//LOG.debug("Learn more servlet Content {}", learnMoreServiceImpl.getRenderedHTML(request, response));
		response.flushBuffer();
	}

}
