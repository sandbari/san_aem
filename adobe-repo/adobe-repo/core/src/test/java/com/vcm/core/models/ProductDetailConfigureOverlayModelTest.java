package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;

public class ProductDetailConfigureOverlayModelTest {
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Test
    public void testGetterSetters() throws Exception {

    	ProductDetailConfigureOverlayModel configureOverlayModel = new ProductDetailConfigureOverlayModel();
    	
    	configureOverlayModel.setIsEnablingOverlay("true");
    	configureOverlayModel.setOverlayType("tooltip");
    	configureOverlayModel.setOverlayTextPrefix("What are");
    	configureOverlayModel.setOverlayLinkText("risk measures");
    	configureOverlayModel.setOverlayContentPath("/content/dam/vcm/vcm-content-fragments/tool-tip/product-detail-tool-tip");
    	configureOverlayModel.setOverlayContent("Test Content");
    	configureOverlayModel.setOverlayContent("Test ID");
    	configureOverlayModel.setOverlayId("overlayId");

        assertNotNull(configureOverlayModel.getIsEnablingOverlay());
        assertNotNull(configureOverlayModel.getOverlayType());
        assertNotNull(configureOverlayModel.getOverlayTextPrefix());
        assertNotNull(configureOverlayModel.getOverlayLinkText());        
        assertNotNull(configureOverlayModel.getOverlayContentPath());
        assertNotNull(configureOverlayModel.getOverlayContent());
		assertNotNull(configureOverlayModel.getOverlayContent());
		assertNotNull(configureOverlayModel.getOverlayId());
		
		configureOverlayModel.init();
    }

}
