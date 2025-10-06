package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class ProductMenuModelTest {
	@Mock
	Resource resource;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	Iterator<Resource> resourceIterator;
	private ProductMenuModel productMenuModel;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
    	productMenuModel = new ProductMenuModel();
    	resourceResolver = context.resourceResolver();
    	productMenuModel.setResourceResolver(resourceResolver);
    	
    	context.load().json("/productMenu.json","/content/vcm/language-masters/en/financial-professional");
    	Resource resource = context.currentResource("/content/vcm/language-masters/en/financial-professional");
    	productMenuModel.setResource(resource);
    	
    }
    
    @Test
    public void testGetterSetters() throws Exception {
    	productMenuModel.setErrorMessage("Test Error Message");
    	productMenuModel.setPlaceHolderText("Test Place Holder");
    	productMenuModel.setTabVariant("Test Variant");
    	
    	assertNotNull(productMenuModel.getErrorMessage());
    	assertNotNull(productMenuModel.getPlaceHolderText());
    	assertNotNull(productMenuModel.getTabVariant());
    	assertNotNull(productMenuModel.getTabLinkItems());
    	
    	productMenuModel.init();
    	assertNotNull(productMenuModel.getTabLinkItems());
    	
    	
    }

}
