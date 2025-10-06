/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.vcm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.vcm.core.service.PostloginBannerService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.constants.ComponentConstants;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Get Number of active postlogin banner",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "sling/servlet/default",
		"sling.servlet.selectors=" + "enabledbanner", "sling.servlet.extensions=" + "json" })
public class GetTotalActiveBannerServlet extends SlingAllMethodsServlet {

	/**
	 * Constant serialVersionUID
	 */
	private static final long serialVersionUID = -1403127886675345274L;

	/** The Constant LOGGER. */
	private static final Logger LOG = LoggerFactory.getLogger(GetTotalActiveBannerServlet.class);

	@Reference
	private transient PostloginBannerService postloginBannerService;


	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(postloginBannerService.getActivePostloginBanner(request));
			out.flush();

		} catch (RepositoryException e) {
			LOG.error("error while getting number of active banner : {} {}", e.getMessage(), e);
			response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST);
		}

	}

	public void setPostloginBannerService(PostloginBannerService postloginBannerService) {
		this.postloginBannerService = postloginBannerService;
	}

}
