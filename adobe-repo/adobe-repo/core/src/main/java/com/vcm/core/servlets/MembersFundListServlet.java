package com.vcm.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.vcm.core.service.ProductListingService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Servlet is used to search JCR
 */
@Component(service = Servlet.class, property = { 
			 Constants.SERVICE_DESCRIPTION + "=Members Fund Listing Servlet",
			"sling.servlet.methods=" + HttpConstants.METHOD_GET, 
			"sling.servlet.resourceTypes=" + "sling/servlet/default",
			"sling.servlet.selectors=" + "membersFundList", 
			"sling.servlet.extensions=" + "json" })
public class MembersFundListServlet extends SlingAllMethodsServlet {

	/**
	 * Constant serialVersionUID
	 */
	private static final long serialVersionUID = -1403607448277914274L;

	/** The Constant LOGGER. */
	private static final Logger LOG = LoggerFactory.getLogger(MembersFundListServlet.class);

	@Reference
	private transient ProductListingService membersFundDetailsService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("X-Frame-Options", "DENY");

		ResourceResolver resourceResolver=request.getResourceResolver();
		LOG.debug("Into Members fund list Servlet");
		response.getWriter().write(membersFundDetailsService.getFundList(resourceResolver));
	}

	/**
	 * @param membersFundDetailsService the membersFundDetailsService to set
	 */
	public void setMembersFundDetailsService(ProductListingService membersFundDetailsService) {
		this.membersFundDetailsService = membersFundDetailsService;
	}
}
