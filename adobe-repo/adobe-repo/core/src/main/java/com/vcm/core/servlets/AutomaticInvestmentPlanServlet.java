package com.vcm.core.servlets;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.AutomaticInvestmentPlanService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=Get AIP Content",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.servlet.selectors=" + "automaticinvestmentplan", "sling.servlet.extensions=" + "json"})
public class AutomaticInvestmentPlanServlet extends SlingAllMethodsServlet {

    /**
     * Constant serialVersionUID
     */
    private static final long serialVersionUID = -1403184637675345275L;

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AutomaticInvestmentPlanServlet.class);

    @Reference
    private transient AutomaticInvestmentPlanService automaticInvestmentPlanService;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.print(automaticInvestmentPlanService.getAIPContent(request));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        out.flush();
    }
}
