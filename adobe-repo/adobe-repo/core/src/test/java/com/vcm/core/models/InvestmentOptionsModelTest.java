package com.vcm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AemContextExtension.class)
public class InvestmentOptionsModelTest {

    private InvestmentOptionsModel investmentOptionsModel;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        investmentOptionsModel = new InvestmentOptionsModel();
        resourceResolver=context.resourceResolver();
        investmentOptionsModel.setResourceResolver(resourceResolver);
        investmentOptionsModel.setDescription("USAA Mutual Funds are managed by Victory Capital. Get started today with a low initial investment.");
        investmentOptionsModel.setDescriptionOption1("With automatic investments of at least $50 per month");
        investmentOptionsModel.setDescriptionOption2("Automatic investments not required");
        investmentOptionsModel.setHeading("Initial Investment Options");
        investmentOptionsModel.setHeadingOption1("50 initial investment");
        investmentOptionsModel.setHeadingOption2("500 initial investment");
        investmentOptionsModel.setImageOption1("/images/icon__50.png");
        investmentOptionsModel.setImageOption2("/images/icon__500.png");
        investmentOptionsModel.setLinkTarget("same tab");
        investmentOptionsModel.setLinkText("View all mutual funds");
        investmentOptionsModel.setLinkUrl("/content/vcm/en");
        investmentOptionsModel.setImgAltText1("Alt text 1");
        investmentOptionsModel.setImgAltText2("Alt text 2");
        investmentOptionsModel.setOptionsSeparator("or");
        investmentOptionsModel.setLinkDescription("Link Description");
        investmentOptionsModel.setLinkAriaLabel("Link Arialabel");
    }

    @Test
    void testInvestments() throws Exception {
    	
    	investmentOptionsModel.init();
        Assert.assertEquals("USAA Mutual Funds are managed by Victory Capital. Get started today with a low initial investment.",investmentOptionsModel.getDescription());
        Assert.assertEquals("With automatic investments of at least $50 per month",investmentOptionsModel.getDescriptionOption1());
        Assert.assertEquals("Automatic investments not required",investmentOptionsModel.getDescriptionOption2());
        Assert.assertEquals("Initial Investment Options",investmentOptionsModel.getHeading());
        Assert.assertEquals("50 initial investment",investmentOptionsModel.getHeadingOption1());
        Assert.assertEquals("500 initial investment",investmentOptionsModel.getHeadingOption2());
        Assert.assertEquals("/images/icon__50.png",investmentOptionsModel.getImageOption1());
        Assert.assertEquals("/images/icon__500.png",investmentOptionsModel.getImageOption2());
        Assert.assertEquals("same tab",investmentOptionsModel.getLinkTarget());
        Assert.assertEquals("View all mutual funds",investmentOptionsModel.getLinkText());
        Assert.assertEquals("/content/vcm/en",investmentOptionsModel.getLinkUrl());        
        Assert.assertEquals("Alt text 1",investmentOptionsModel.getImgAltText1());
        Assert.assertEquals("Alt text 2",investmentOptionsModel.getImgAltText2());        
        Assert.assertEquals("or",investmentOptionsModel.getOptionsSeparator());
        Assert.assertNotNull(investmentOptionsModel.getLinkDescription());
        Assert.assertNotNull(investmentOptionsModel.getLinkAriaLabel());
        Assert.assertNotNull(investmentOptionsModel.getResourceResolver());
    }
    
    @Test
    public void testInitWithElse() {
    	investmentOptionsModel.setResourceResolver(context.resourceResolver());
    	investmentOptionsModel.setLinkDescription(null); 
    	investmentOptionsModel.setLinkText("Link Text");
    	investmentOptionsModel.setLinkUrl("Link Url");
    	investmentOptionsModel.init();
    }
}
