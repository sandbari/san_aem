package com.vcm.core.servlets;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.NxiContentPageService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
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

@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=Get Page Details Servlet",
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.servlet.selectors=" + "nxicontentpage",
        "sling.servlet.extensions=" + "html"
})
public class GetNxiContentPageServlet extends SlingSafeMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(GetNxiContentPageServlet.class);

    @Reference
    private transient NxiContentPageService nxiContentPageService;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Marketing Page Content servlet");
        try {
            response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(nxiContentPageService.getNxiPageContent(request));
            out.flush();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
