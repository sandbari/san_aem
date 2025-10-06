package com.vcm.core.servlets;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.PostloginBannerGetDetailsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.IOException;
import java.text.ParseException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Get PostLogin banner detail Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.auth.requirements=/cors",
        "sling.servlet.selectors=" + "postloginbannerdetails", "sling.servlet.extensions=" + "json" })
public class PostLoginBannerGetDetailsServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUID = -2014397651676211439L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostLoginBannerGetDetailsServlet.class);

    @Reference
    private transient PostloginBannerGetDetailsService postloginBannerGetDetailsService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = postloginBannerGetDetailsService.getPostloginBannerDetails(request);
            LOGGER.debug("EXIT getUserLoginResponse jsonResponse1 {}", jsonResponse);
            response.getWriter().println(jsonResponse);
        } catch (RepositoryException | ParseException e) {
            LOGGER.error("EXIT error while getting postlogin banner details : {} {}", e.getMessage(), e);
            response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST);
        }
    }
}