package com.vcm.core.servlets;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.PostLoginDashboardArticleService;
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

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Get Dashboard Marketing Content Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        /*"sling.auth.requirements=/cors",
        "sling.servlet.paths=/bin/api/dashboardmarketingcontent" }*/
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.servlet.selectors=" + "dashboardmarketingcontent", "sling.servlet.extensions=" + "json"})
public class GetDashboardMarketingContentServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = -2014397651676211432L;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetDashboardMarketingContentServlet.class);

    @Reference
    private transient PostLoginDashboardArticleService postLoginDashboardArticleService;

    public void setDashboardMarketingContentService(PostLoginDashboardArticleService postLoginDashboardArticleService) {
        this.postLoginDashboardArticleService = postLoginDashboardArticleService;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        LOGGER.info("GetDashboardMarketingContentServlet : doGet() method");
        response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.write(postLoginDashboardArticleService.getDashboardMarketingContent(request));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        out.flush();
    }

}
