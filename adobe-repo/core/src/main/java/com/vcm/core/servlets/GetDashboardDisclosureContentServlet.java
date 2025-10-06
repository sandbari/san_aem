package com.vcm.core.servlets;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.DashboardDisclosureService;
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
import java.io.PrintWriter;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Get Dashboard disclosure text Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.auth.requirements=/cors",
        "sling.servlet.selectors=" + "dashboarddisclosure", "sling.servlet.extensions=" + "json" })
public class GetDashboardDisclosureContentServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUID = -2014397651676211432L;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetDashboardDisclosureContentServlet.class);

    @Reference
    private transient DashboardDisclosureService dashboardDisclosureService;

    public void setDashboardDisclosureService(DashboardDisclosureService dashboardDisclosureService) {
        this.dashboardDisclosureService = dashboardDisclosureService;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(dashboardDisclosureService.getDashboardDisclosureContent(request));
            out.flush();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}