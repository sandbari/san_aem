package com.vcm.core.servlets;

import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.service.UserFormService;
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

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Get User PDF Forms Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "sling/servlet/default",
        "sling.servlet.selectors=" + "userforms_private", "sling.servlet.extensions=" + "json" })
public class GetUserFormsWebServlet extends SlingAllMethodsServlet {
    /**
     * Constant serialVersionUID
     */
    private static final long serialVersionUID = -1403127886677914274L;

    /** The Constant LOGGER. */
    private static final Logger LOG = LoggerFactory.getLogger(GetUserFormsServlet.class);

    @Reference
    private transient UserFormService userFormService;


    public void setUserFormService(UserFormService userFormService) {
        this.userFormService = userFormService;
    }


    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType(ComponentConstants.JSON_RESPONSE_CONTENT_TYPE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(userFormService.userFormsPrivateJson(request));
            out.flush();

        } catch (RepositoryException e) {
            LOG.error("error while getting blog list : {} {}", e.getMessage(), e);
            response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
