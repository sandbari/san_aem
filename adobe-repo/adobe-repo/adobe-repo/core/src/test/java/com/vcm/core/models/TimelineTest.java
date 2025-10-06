package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class TimelineTest {

	private Timeline timeline;
	private TimelineYearData timelineYearData;
	
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	@BeforeEach
    public void setup() throws Exception {
		timelineYearData = new TimelineYearData();
		timeline = new Timeline();
		
 		List<TimelineYearData> yearData = new ArrayList<TimelineYearData>();  
 		
 		timeline.setResourceResolver(context.resourceResolver());
 		
 		timeline.setHeading("Heading");
 		timeline.setDescription("Description");
 		timeline.setLinkText("Link Text");
 		timeline.setLinkUrl("Link Url");
 		timeline.setTabSelect("_self");
 		timeline.setLinkDescription("Link Description"); 
 		timeline.setLinkAriaLabel("Link Arialabel");
 		
 		timelineYearData.setYear("Year");
 		timelineYearData.setEventImage("Event Image");
 		timelineYearData.setEventTitle("Event Title");
 		timelineYearData.setEventDescription("Event Description");
 		timelineYearData.setAltText("Alt Text for event image");
 		yearData.add(timelineYearData);
 		timeline.setYearData(yearData);	
	}
	@Test
 	void testTimeline() {
	
	timeline.init();
	assertNotNull(timeline.getHeading());
	assertNotNull(timeline.getDescription());
	assertNotNull(timeline.getLinkText());	
	assertNotNull(timeline.getTabSelect());
	assertNotNull(timeline.getLinkUrl());
	assertNotNull(timeline.getYearData());
	assertNotNull(timeline.getLinkDescription());
	assertNotNull(timeline.getLinkAriaLabel());
	}	
	
	@Test
	void testInitWithElse() {
		timeline.setLinkDescription(null); 
 		timeline.setLinkText("Link Text");
 		timeline.setLinkUrl("Link Url");
		timeline.init();
	}
}
