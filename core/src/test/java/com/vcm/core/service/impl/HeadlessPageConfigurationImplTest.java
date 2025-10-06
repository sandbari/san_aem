package com.vcm.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class HeadlessPageConfigurationImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    SlingHttpServletRequest request;
    
    @Mock
    ValueMap valuemap;
    
    @Mock
    Resource resource;
    
    private HeadlessPageConfigurationImpl headlessPageConfigurationImpl;

    @BeforeEach
    void setup() throws Exception {
    	headlessPageConfigurationImpl = new HeadlessPageConfigurationImpl();
    	
    	HeadlessPageConfigurationImpl.Config config = mock(HeadlessPageConfigurationImpl.Config.class);
    	String[] cssLinks= {"https://use.typekit.net/vpy3cvs.css", "https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"};
    	String[] clientLibsCategories = {"vcm.headless.main"};
	    String[] staticPageClientLibCategories = {"vcm.headless.rapid"};
	    String[] filterClientlibsCategories = {"search/vcmSearchClientLib", "/libs/cq/experience-fragments/components/xfpage", "/etc.clientlibs/vcm/clientlibs/clientlib-base"};
	    String[] filterComponents = {"breadcrumb", "header", "footer"};
	    String[] getHostPatternRequiredElements = {"popupContentPage", "suggestionsUrl", "searchResultsPagePath", "currentPagePath"};
	    
    	Mockito.when(config.cssLinks()).thenReturn(cssLinks);
    	Mockito.when(config.clientLibsCategories()).thenReturn(clientLibsCategories);
    	Mockito.when(config.productPageClientLibCategories()).thenReturn(staticPageClientLibCategories);
    	Mockito.when(config.filterClientlibsCategories()).thenReturn(filterClientlibsCategories);
    	Mockito.when(config.filterComponents()).thenReturn(filterComponents);
    	Mockito.when(config.getHostPatternRequiredElements()).thenReturn(getHostPatternRequiredElements);
    	headlessPageConfigurationImpl.activate(config);
    }

    @Test
    public void testHeadlessPageConfigurationImpl() {
    	
    	assertNotNull(headlessPageConfigurationImpl.getCSSLinks());
    	assertNotNull(headlessPageConfigurationImpl.getClientLibsCategories());
    	assertNotNull(headlessPageConfigurationImpl.getProductPageClientLibsCategories());
    	assertNotNull(headlessPageConfigurationImpl.getFilterClientlibsCategories());
    	assertNotNull(headlessPageConfigurationImpl.getFilterComponents());
    	String[] filterComponents = {"breadcrumb", "header", "footer"};
    	headlessPageConfigurationImpl.setFilterComponents(filterComponents);
    	assertNotNull(headlessPageConfigurationImpl.getHostPatternRequiredElements());
    	
    	
    }
}
