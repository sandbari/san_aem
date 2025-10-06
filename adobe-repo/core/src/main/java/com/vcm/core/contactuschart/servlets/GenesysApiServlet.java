package com.vcm.core.contactuschart.servlets;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.CurrentWaitTimeService;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.service.OpenHoursFinder;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.service.WaitTimeChartService;
import com.vcm.core.utils.FileHandlerService;

/**
 * This Servlet is used to search JCR
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Genesys api Servlet",
	"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.resourceTypes=" + "sling/servlet/default",
	"sling.servlet.selectors=" + "genesys", "sling.servlet.extensions=" + "json" })
public class GenesysApiServlet extends SlingAllMethodsServlet {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5770335628643743361L;
    /** The Constant LOGGER. */
    private static final Logger LOG = LoggerFactory.getLogger(GenesysApiServlet.class);

    @Reference
    private transient GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    @Reference
    private transient CurrentWaitTimeService currentWaitTimeServiceImpl;

    @Reference
    private transient WaitTimeChartService waitTimeChartService;

    @Reference
    private transient OpenHoursScheduleGenerator openHoursScheduleGenerator;

    @Reference
    private transient OpenHoursFinder openHoursFinder;
   
    @Reference
    private transient ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    @Reference
    private transient FileHandlerService fileHandlerService;
    
    public void setGenesysAuthTokenGenerator(GenesysAuthTokenGenerator genesysAuthTokenGenerator) {
		this.genesysAuthTokenGenerator = genesysAuthTokenGenerator;
	}
    
    public void setCurrentWaitTimeService(CurrentWaitTimeService currentWaitTimeService) {
		this.currentWaitTimeServiceImpl = currentWaitTimeService;
	}
    
    public void setOpenHoursScheduleGenerator(OpenHoursScheduleGenerator openHoursScheduleGenerator) {
		this.openHoursScheduleGenerator = openHoursScheduleGenerator;
	}
    
    public void setWaitTimeChartService(WaitTimeChartService waitTimeChartService) {
		this.waitTimeChartService = waitTimeChartService;
	}
    
    public void setOpenHoursFinder(OpenHoursFinder openHoursFinder) {
		this.openHoursFinder = openHoursFinder;
	}    

    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
	    throws ServletException, IOException {

	LOG.debug(">>>>>inside doGet method:");

	String[] selectors = request.getRequestPathInfo().getSelectors();

	response.setContentType(Constant.APPLICATION_JSON);
	response.setCharacterEncoding("UTF-8");
	response.addHeader("X-Frame-Options", "DENY");
	

	if (Objects.nonNull(selectors) && selectors.length == 2) {


	    if (Objects.equals(selectors[1], "currentWaitTimeService")) {

		response.getWriter().write(currentWaitTimeServiceImpl.getCurrentWaitTime());

	    }
	    if (Objects.equals(selectors[1], "waitTimeChartService")) {

		response.getWriter().write(waitTimeChartService.generateWaittimeChartJson().toString());

	    }
	    if (Objects.equals(selectors[1], "openHoursSchedule")) {

		response.getWriter().write(openHoursScheduleGenerator.getOpenHoursScheduleJson());

	    }
	    if (Objects.equals(selectors[1], "findOpenCloseHours")) {

		response.getWriter().write(openHoursFinder.getOpenCloseHours().toString());

	    }
	}

	response.flushBuffer();

    }
}
