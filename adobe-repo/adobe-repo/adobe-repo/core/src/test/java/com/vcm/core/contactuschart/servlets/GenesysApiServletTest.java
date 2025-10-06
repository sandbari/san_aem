package com.vcm.core.contactuschart.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonObject;
import com.vcm.core.contactuschart.service.CurrentWaitTimeService;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.service.OpenHoursFinder;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.service.WaitTimeChartService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class GenesysApiServletTest {
	private GenesysApiServlet genesysApiServlet;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @Mock
    private transient GenesysAuthTokenGenerator genesysAuthTokenGenerator;
    
    @Mock
    private transient CurrentWaitTimeService currentWaitTimeServiceImpl;

    @Mock
    private transient WaitTimeChartService waitTimeChartService;

    @Mock
    private transient OpenHoursScheduleGenerator openHoursScheduleGenerator;

    @Mock
    private transient OpenHoursFinder openHoursFinder;
    
    @Mock
    SlingHttpServletRequest request;
    
	
	@Test
	public void testCurrentWaitTime() throws Exception {
		genesysApiServlet = new GenesysApiServlet();
		
		genesysApiServlet.setCurrentWaitTimeService(currentWaitTimeServiceImpl);	
		context.requestPathInfo().setSelectorString("test.currentWaitTimeService");
		Mockito.when(currentWaitTimeServiceImpl.getCurrentWaitTime()).thenReturn("Waiting Time");
		genesysApiServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}
	
	@Test
	public void testOpenHoursFinder() throws Exception {
		genesysApiServlet = new GenesysApiServlet();
		
		genesysApiServlet.setOpenHoursFinder(openHoursFinder);
		context.requestPathInfo().setSelectorString("test.findOpenCloseHours");
		
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("isOpenNow", "Open Now");
		responseObj.addProperty("isHoliday", "Holiday");
		
		Mockito.when(openHoursFinder.getOpenCloseHours()).thenReturn(responseObj);
		genesysApiServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}
	
	@Test
	public void testOpenHoursSchedule() throws Exception {
		genesysApiServlet = new GenesysApiServlet();
		
		genesysApiServlet.setOpenHoursScheduleGenerator(openHoursScheduleGenerator);
		context.requestPathInfo().setSelectorString("test.openHoursSchedule");
		Mockito.when(openHoursScheduleGenerator.getOpenHoursScheduleJson()).thenReturn("Schedule Hours");
		genesysApiServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}
	
	@Test
	public void testWaitTimeChartService() throws Exception {
		genesysApiServlet = new GenesysApiServlet();
		
		genesysApiServlet.setWaitTimeChartService(waitTimeChartService);
		context.requestPathInfo().setSelectorString("test.waitTimeChartService");
		Mockito.when(waitTimeChartService.generateWaittimeChartJson()).thenReturn("Wait Time");
		genesysApiServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}

}
