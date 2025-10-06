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
class FundListModelTest {

	private FundListModel fundListModel;
	private ProductListingService productListingService;
	
	@Mock
	private VCMSiteConfiguationService vcmSiteConfiguationService;
	
	//private MembersFundDetailListService membersFundDetailListService;
	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		//membersFundDetailListService = new MembersFundDetailListingImpl();
		productListingService = new ProductListingServiceImpl();
		resourceResolver = context.resourceResolver();
		context.load().json("/mutualFundList.json", "/content/vcm/us/en/member/products/mutual-funds");
		fundListModel = new FundListModel();
	}

	@Test
	void testFundModelList() throws Exception {
		fundListModel.setVcmSiteConfigService(vcmSiteConfiguationService);
		fundListModel.setResourceResolver(resourceResolver);
		fundListModel.setProductListingService(productListingService);
		//fundListModel.setMembersFundDetailList(membersFundDetailListService);
		
    	Map<String, Object> errorMessageMap = new HashMap<String, Object>();
    	errorMessageMap.put("apiCallFailureMessage", "API Call  Failed");
    	Mockito.when(vcmSiteConfiguationService.getLabelConfigAsMap()).thenReturn(errorMessageMap);
    	
		fundListModel.init();
		fundListModel.setFundListEndpoint("fundListEndpoint");
		fundListModel.setFundListApiKey("fundListApiKey");
		fundListModel.setRiskContent("Risk Content");
		Assert.assertNotNull(fundListModel.getFundList());
		Assert.assertNotNull(fundListModel.getFundListEndpoint());
		Assert.assertNotNull(fundListModel.getRiskContent());
		Assert.assertEquals("fundListApiKey",fundListModel.getFundListApiKey());
	}

}