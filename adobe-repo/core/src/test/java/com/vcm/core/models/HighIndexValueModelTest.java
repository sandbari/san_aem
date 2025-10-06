package com.vcm.core.models;

import java.util.HashMap;
import java.util.Map;

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
class HighIndexValueModelTest {

	private HighIndexValueModel highIndexValueModel;
	private ProductListingService productListingService;
	
	@Mock
	private VCMSiteConfiguationService vcmSiteConfiguationService;
	
	//private StrategyListService strategyListService;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		//strategyListService = new StrategyListServiceImpl();
		productListingService = new ProductListingServiceImpl();
		highIndexValueModel = new HighIndexValueModel();
	}

	@Test
	void testFundModelList() throws Exception {
		highIndexValueModel.setVcmSiteConfigService(vcmSiteConfiguationService);
		highIndexValueModel.setProductListingService(productListingService);
		
    	Map<String, Object> errorMessageMap = new HashMap<String, Object>();
    	errorMessageMap.put("apiCallFailureMessage", "API Call  Failed");
    	Mockito.when(vcmSiteConfiguationService.getLabelConfigAsMap()).thenReturn(errorMessageMap);
    	
		highIndexValueModel.init();
		highIndexValueModel.setHighIndexEndpoint("strategyListEndpoint");
		highIndexValueModel.setHighIndexApiKey("strategyListApiKey");
		highIndexValueModel.getApiCallFailureMessage();
		Assert.assertNotNull(highIndexValueModel.getHighIndexEndpoint());
		Assert.assertEquals("strategyListApiKey",highIndexValueModel.getHighIndexApiKey());
	}

}