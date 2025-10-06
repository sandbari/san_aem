package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class TimelineYearDataTest {

	private TimelineYearData timelineYearData;
	
	@BeforeEach
    public void setup() throws Exception {
		timelineYearData = new TimelineYearData();
		
		timelineYearData.setYear("Year");
		timelineYearData.setEventImage("Event Image");
		timelineYearData.setAltText("Alt Text for event image");
		timelineYearData.setEventTitle("Event Title");
		timelineYearData.setEventDescription("Event Description");
		
	}
	@Test
	 public void testTimelineYearData() {
		
		assertNotNull(timelineYearData.getYear());
		assertNotNull(timelineYearData.getEventImage());
		assertNotNull(timelineYearData.getEventTitle());
		assertNotNull(timelineYearData.getEventDescription());  
		assertNotNull(timelineYearData.getAltText());
	 }
		

}
