package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;

public class AnnualizedReturnsTest {
	private AnnualizedReturns annualizedReturns;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
    	annualizedReturns = new AnnualizedReturns();
    }
    
    @Test
    public void testGetterSetters() throws Exception {


    	annualizedReturns.setHeading("Heading");
    	annualizedReturns.setMonthlyLabel("Monthly");
    	annualizedReturns.setQuarterlyLabel("Quarterly");
    	annualizedReturns.setServiceType("Service Type");
    	annualizedReturns.setTooltipDescription("Tooltip Description");
    	
        assertNotNull(annualizedReturns.getHeading());
        assertNotNull(annualizedReturns.getMonthlyLabel());
        assertNotNull(annualizedReturns.getQuarterlyLabel());
        assertNotNull(annualizedReturns.getServiceType());
        assertNotNull(annualizedReturns.getTooltipDescription());  
    }

}
