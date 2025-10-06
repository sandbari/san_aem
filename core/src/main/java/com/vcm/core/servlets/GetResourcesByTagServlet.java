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

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONWriter;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.ProductMenuService;

/**
 * Servlet that writes tagged page list into the response. It is mounted for all
 * resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Get Resources By Tag Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "vcm/components/content/productmenu", "sling.servlet.selectors=" + "funds",
		"sling.servlet.extensions=" + "json" })
public class GetResourcesByTagServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	protected final transient Logger log = LoggerFactory.getLogger(GetResourcesByTagServlet.class);

	@Reference
	private transient ProductMenuService productMenuService;

	/**
	 * @param productMenuServiceImpl the productMenuServiceImpl to set
	 */
	public void setProductMenuService(ProductMenuService productMenuService) {
		this.productMenuService = productMenuService;
	}

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
			response.setCharacterEncoding("UTF-8");
			log.debug(">>>>>inside doGet method: GetResourcesByTagServlet ={}", request.getResource());
			JSONWriter writer = new JSONWriter(response.getWriter());
			productMenuService.writeJsonResponse(request, writer);
			response.flushBuffer();

		} catch (JSONException | RepositoryException e) {

			log.error("Error while adding list of tags and tagged pages {}", e.getMessage());

			response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST);
		}

	}

}
