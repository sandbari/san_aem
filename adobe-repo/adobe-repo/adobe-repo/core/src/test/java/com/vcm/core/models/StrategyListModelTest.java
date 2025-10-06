package com.vcm.core.models;

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
import com.vcm.core.service.impl.ProductListingServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class StrategyListModelTest {

	private StrategyListModel strategyListModel;
	private ProductListingService productListingService;
	
	@Mock
	private VCMSiteConfiguationService vcmSiteConfiguationService;
	
	//private StrategyListService strategyListService;
	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		//strategyListService = new StrategyListServiceImpl();
		productListingService = new ProductListingServiceImpl();
		resourceResolver = context.resourceResolver();
		context.load().json("/strategyList.json", "/content/vcm/us/en/institutional-investor/our-strategies");
		strategyListModel = new StrategyListModel();
	}

	@Test
	void testFundModelList() throws Exception {
		strategyListModel.setVcmSiteConfigService(vcmSiteConfiguationService);
		strategyListModel.setResourceResolver(resourceResolver);
		strategyListModel.setProductListingService(productListingService);
		//strategyListModel.setStrategyListService(strategyListService);
		
    	Map<String, Object> errorMessageMap = new HashMap<String, Object>();
    	errorMessageMap.put("apiCallFailureMessage", "API Call  Failed");
    	Mockito.when(vcmSiteConfiguationService.getLabelConfigAsMap()).thenReturn(errorMessageMap);
    	
		strategyListModel.init();
		strategyListModel.setStrategyListEndpoint("strategyListEndpoint");
		strategyListModel.setStrategyListApiKey("strategyListApiKey");
		Assert.assertNotNull(strategyListModel.getStrategyList());
		Assert.assertNotNull(strategyListModel.getStrategyListEndpoint());
		Assert.assertEquals("strategyListApiKey",strategyListModel.getStrategyListApiKey());
		Assert.assertNotNull(strategyListModel.getApiCallFailureMessage());
	}

}