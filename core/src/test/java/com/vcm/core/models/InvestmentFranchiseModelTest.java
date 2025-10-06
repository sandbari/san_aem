package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class InvestmentFranchiseModelTest {
	
	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
	@BeforeEach
	public void setUp() throws JSONException {
		context.load().json("/franchiseRootPage.json", "/content/vcm/en/investment-franchise");
      	resourceResolver = context.resourceResolver();      	
	}
	
	@Test
	public void testInvestmentFranchise() {
		InvestmentFranchisesModel testInvestmentFranchise = new InvestmentFranchisesModel();	
		testInvestmentFranchise.setResourceResolver(context.resourceResolver());
		testInvestmentFranchise.setRootPath("/content/vcm/en/investment-franchise");
		testInvestmentFranchise.setLinkDescription("Link Description");
		testInvestmentFranchise.setLinkAriaLabel("Aria Label");
		testInvestmentFranchise.init();
		assertEquals("/content/vcm/en/investment-franchise", testInvestmentFranchise.getRootPath());
	}
	
	@Test
	public void testGetterSetters() {
		InvestmentFranchisesModel testInvestmentFranchise = new InvestmentFranchisesModel();
		
		testInvestmentFranchise.setResourceResolver(context.resourceResolver());
		testInvestmentFranchise.setHeading("Heading");
		testInvestmentFranchise.setDescription("Description");
		testInvestmentFranchise.setViewDetail("View Details");
		
		testInvestmentFranchise.setLinkUrl("/content/us");		
		testInvestmentFranchise.setDisplayStyle("Display Style");
		testInvestmentFranchise.setContentType("json");
		testInvestmentFranchise.setTabSelect("Same tab or new tab");   
		testInvestmentFranchise.setLinkDescription("Link Description");
		testInvestmentFranchise.setLinkAriaLabel("Aria Label");
		
		
		assertEquals("Heading", testInvestmentFranchise.getHeading());
		assertEquals("Description", testInvestmentFranchise.getDescription());
		assertEquals("View Details", testInvestmentFranchise.getViewDetail());
		assertEquals("/content/us", testInvestmentFranchise.getLinkUrl());
		assertEquals("Display Style", testInvestmentFranchise.getDisplayStyle());
		assertEquals("json", testInvestmentFranchise.getContentType());
	    assertEquals("Same tab or new tab",testInvestmentFranchise.getTabSelect());
	    assertEquals("Link Description",testInvestmentFranchise.getLinkDescription());
	    assertEquals("Aria Label",testInvestmentFranchise.getLinkAriaLabel());
	    assertNotNull(testInvestmentFranchise.getFranchiseList());
		

	}
	
	@Test
	public void testFranchiseIntro() {
		FranchiseIntroModel testFranchiseIntro = new FranchiseIntroModel();
		testFranchiseIntro.setResourceResolver(context.resourceResolver());
		testFranchiseIntro.setAltText("Incore Image");
		testFranchiseIntro.setFranchiseDescription("Franchise Description");
		testFranchiseIntro.setStrategyHeading("Strategy Heading");
	    testFranchiseIntro.setStrategyDescription("Description Strategy");
		testFranchiseIntro.setStrategyViewDetail("Strategy View Detail");
		testFranchiseIntro.setStrategyLinkUrl("/content/vcm/en/franchiseintro/strategy");
		testFranchiseIntro.setTabSelectStrategy("Same tab or new tab");
		testFranchiseIntro.setStrategyShowButton("Strategy Show Button");
		testFranchiseIntro.setLinkAriaLabel("Aria Label");
		testFranchiseIntro.setLinkDescription("Link Description");
		
		assertEquals("Incore Image", testFranchiseIntro.getAltText());
		assertEquals("Franchise Description", testFranchiseIntro.getFranchiseDescription());
		assertEquals("Strategy Heading", testFranchiseIntro.getStrategyHeading());
		assertEquals("Description Strategy", testFranchiseIntro.getStrategyDescription());
		assertEquals("Strategy View Detail", testFranchiseIntro.getStrategyViewDetail());
		assertEquals("/content/vcm/en/franchiseintro/strategy", testFranchiseIntro.getStrategyLinkUrl());
		assertEquals("Same tab or new tab", testFranchiseIntro.getTabSelectStrategy());
		assertEquals("Strategy Show Button", testFranchiseIntro.getStrategyShowButton());
		assertEquals("Aria Label", testFranchiseIntro.getLinkAriaLabel());
		assertEquals("Link Description", testFranchiseIntro.getLinkDescription());	
		testFranchiseIntro.init();
	
	}
	
	@Test
	public void testInitWithNull() {
		InvestmentFranchisesModel testInvestmentFranchise = new InvestmentFranchisesModel();	
		testInvestmentFranchise.setResourceResolver(context.resourceResolver());
		testInvestmentFranchise.setRootPath(null);
		testInvestmentFranchise.setLinkDescription(null);
		testInvestmentFranchise.setLinkUrl("/content/us");	
		testInvestmentFranchise.setViewDetail("View Details");
		testInvestmentFranchise.init();
		
	}
}
