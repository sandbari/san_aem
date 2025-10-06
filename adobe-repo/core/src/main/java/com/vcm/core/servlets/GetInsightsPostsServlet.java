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

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.InsightListingService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet that writes tagged page list into the response. It is mounted for all
 * resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=Get Insights Posts Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.servlet.selectors=" + "insightlisting", "sling.servlet.extensions=" + "json"})
public class GetInsightsPostsServlet extends SlingAllMethodsServlet {

    /**
     * Constant serialVersionUID
     */
    private static final long serialVersionUID = -1403127448277914274L;

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GetInsightsPostsServlet.class);

    @Reference

    private transient InsightListingService insightListingService;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            LOG.debug("GetInsightsPostsServlet doGet Called");
            out.write(insightListingService.blogListJsonString(request));
            out.flush();
        } catch (Exception e) {
            LOG.error("error while getting blog list : {} {}", e.getMessage(), e);
            response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
