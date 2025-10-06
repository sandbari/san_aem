package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.ProductListingService;
import com.vcm.core.service.VCMSiteConfiguationService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
public class EtfListModelTest {
	private EtfListModel etfListModel;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    ProductListingService productListingService;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    VCMSiteConfiguationService  vcmSiteConfigService;

    @BeforeEach
    public void setup() throws Exception {
    	etfListModel = new EtfListModel();
    }
    
    @Test
    public void testGetterSetters() throws Exception {
    	resourceResolver = context.resourceResolver();
    	
    	etfListModel.setEtfListApiKey("API Key");
    	etfListModel.setEtfListEndpoint("End Point");
    	etfListModel.setProductListingService(productListingService);
    	etfListModel.setResourceResolver(resourceResolver);
    	etfListModel.setVcmSiteConfigService(vcmSiteConfigService);
    	
    	assertNotNull(etfListModel.getEtfListApiKey());
    	assertNotNull(etfListModel.getEtfListEndpoint());
    	etfListModel.getEtfLabels();
    	assertNotNull(etfListModel.getEtfList());
    	
    	Mockito.when(productListingService.getETFList(resourceResolver)).thenReturn("list");
    	Mockito.when(productListingService.getEtfListEndpoint()).thenReturn("End Point");
    	Mockito.when(productListingService.getFundListApiKey()).thenReturn("API Key");
    	Map<String, Object> etflabels = new HashMap<String, Object>();
    	etflabels.put("apiCallFailureMessage", "API Call  Failed");
    	
    	Mockito.when(vcmSiteConfigService.getLabelConfigAsMap()).thenReturn(etflabels);
    	etfListModel.init();

    }

}
