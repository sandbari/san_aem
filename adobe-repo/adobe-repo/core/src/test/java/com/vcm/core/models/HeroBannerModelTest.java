package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class HeroBannerModelTest {
	
	private HeroBannerModel heroBannerModel;
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @BeforeEach
    public void setup() throws Exception {
    	heroBannerModel = new HeroBannerModel();
    }
    
    @Test
    public void testGetterSetters() throws Exception {

    	HeroBannerModel heroBannerModel = new HeroBannerModel();
    	
    	heroBannerModel.setResourceResolver(context.resourceResolver());

    	heroBannerModel.setVarientType("Varient Type");
    	heroBannerModel.setHeroImage("Hero Image");
    	heroBannerModel.setHeroImageMobile("Hero Mobile Image");
    	heroBannerModel.setHeroImageTablet("Hero Tablet Image");
    	heroBannerModel.setAltText("Alt Text");
    	heroBannerModel.setWelcomeText("Welcome Text");
    	heroBannerModel.setHeading("Heading");
    	heroBannerModel.setTextWrap("Wrap Text");
    	heroBannerModel.setDescription("Description");
    	heroBannerModel.setLinkText("Link Text");
    	heroBannerModel.setLinkUrl("Link Url");
    	heroBannerModel.setTabSelect("Tabselect");
    	heroBannerModel.setRequirementItems("Requirement Items");
    	heroBannerModel.setFragmentPath("fragment path");
    	heroBannerModel.setImagePath("Image Path");
    	heroBannerModel.setBannerType("Banner Type");
    	heroBannerModel.setHeroBackgroundImage("Hero Background Image");
    	heroBannerModel.setHeroBackgroundColor("Hero Background Color");
    	heroBannerModel.setImageFocusArea("Image Focus Area");
    	heroBannerModel.setAddGreenTriangle("Green Triangle");
    	heroBannerModel.setImagePathAltText("Image Path Alt Text");
    	heroBannerModel.setLinkDescription("Link Description");
    	heroBannerModel.setLinkAriaLabel("Link Aria Label");
    	
    	assertNotNull(heroBannerModel.getVarientType());
        assertNotNull(heroBannerModel.getHeroImage());
        assertNotNull(heroBannerModel.getHeroImageMobile());
        assertNotNull(heroBannerModel.getHeroImageTablet());
        assertNotNull(heroBannerModel.getAltText());        
        assertNotNull(heroBannerModel.getWelcomeText());
        assertNotNull(heroBannerModel.getHeading());
		assertNotNull(heroBannerModel.getTextWrap());
		assertNotNull(heroBannerModel.getDescription());
        assertNotNull(heroBannerModel.getLinkText());
        assertNotNull(heroBannerModel.getLinkUrl());
        assertNotNull(heroBannerModel.getTabSelect());
        assertNotNull(heroBannerModel.getRequirementItems());        
        assertNotNull(heroBannerModel.getFragmentPath());
        assertNotNull(heroBannerModel.getImagePath());
		assertNotNull(heroBannerModel.getBannerType());
		assertNotNull(heroBannerModel.getHeroBackgroundImage());
        assertNotNull(heroBannerModel.getHeroBackgroundColor());
        assertNotNull(heroBannerModel.getImageFocusArea());
        assertNotNull(heroBannerModel.getAddGreenTriangle());
        assertNotNull(heroBannerModel.getImagePathAltText());        
        assertNotNull(heroBannerModel.getLinkDescription());
        assertNotNull(heroBannerModel.getLinkAriaLabel());
        
        heroBannerModel.init();
    }
    
    @Test
    public void testInitWithElse() {
    	heroBannerModel.setResourceResolver(context.resourceResolver());
    	heroBannerModel.setLinkDescription(null); 
    	heroBannerModel.setLinkText("Link Text");
    	heroBannerModel.setLinkUrl("Link Url");
    	heroBannerModel.init();
    }
}