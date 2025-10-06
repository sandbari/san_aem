package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.vcm.core.models.IntroPointsBean;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class VictoryIntroModelTest {
	private VictoryIntroModel victoryIntroTest;
	private IntroPointsBean introPointsbean;    	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
    
    @BeforeEach
    public void setup() throws Exception {
    	victoryIntroTest = new VictoryIntroModel();
    }
    	 @Test
    	    public void testGetterSetters() throws Exception {
    		 VictoryIntroModel victoryIntroTest = new VictoryIntroModel();
    		 List<IntroPointsBean> introPointsbean1  = new ArrayList<IntroPointsBean>();
    		 
    		 introPointsbean1.add(introPointsbean);
    		 victoryIntroTest.setResourceResolver(context.resourceResolver());
    		 victoryIntroTest.setHeading("Heading");
    		 victoryIntroTest.setLinkText("View details");
    		 victoryIntroTest.setLinkUrl("/conent/us");
    		 victoryIntroTest.setTabSelect("Select same or new tab");
    		 introPointsbean1.add(introPointsbean);
    		 victoryIntroTest.setIntroPoints(introPointsbean1);   		
    		     		 
    			
    		assertEquals("Heading", victoryIntroTest.getHeading());
    		assertEquals("View details", victoryIntroTest.getLinkText());
    		assertEquals("/conent/us", victoryIntroTest.getLinkUrl());
    		assertEquals("Select same or new tab", victoryIntroTest.getTabSelect());
    		assertNotNull(victoryIntroTest.getIntroPoints());
    	 }

    	 @Test
 	    public void introPointsTest() throws Exception {
    		 IntroPointsBean introPointsbean = new IntroPointsBean();
    		
    		   		 
    		 introPointsbean.setDescription("Description");
    		 introPointsbean.setIncludeDollar("Include dollar");
    		 introPointsbean.setNumberValue("Number value");
    		 introPointsbean.setAsofdate("Date");
    		 
     		assertEquals("Description", introPointsbean.getDescription());
     		assertEquals("Include dollar", introPointsbean.getIncludeDollar());
     		assertEquals("Number value", introPointsbean.getNumberValue());
     		assertEquals("Date", introPointsbean.getAsofdate());
     	
     		
    	 }
}

