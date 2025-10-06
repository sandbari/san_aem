package com.vcm.core.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vcm.core.pojo.FundDetailPojo;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class ProductListingServiceImplTest {

    private ProductListingServiceImpl productListingService = new ProductListingServiceImpl();
    private ProductListingServiceImpl.Config config;
    private ConstantsConfigServiceImpl constantConfigService=new ConstantsConfigServiceImpl();
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    private ResourceResolver resourceResolver;

    @BeforeEach
    public void setUp() {
        config = mock(ProductListingServiceImpl.Config.class);
        when (config.getMembersPageRootPath()).thenReturn("/content/vcm/us/en/member/products/mutual-funds");
        when (config.getFundListEndPoint()).thenReturn("/content/vcm/us/en/member/products/mutual-funds");
        when (config.getFundApiKey()).thenReturn("HU8s0Kj6f848YhcLKz8Cl3tYbn9ezTwm9xybNwB7");
        productListingService.setConstantsConfigService(constantConfigService);
        productListingService.activate(config);

        when (config.getStrategyListEndPoint()).thenReturn("/content/vcm/us/en/institutional-investor/our-strategies");
        when (config.getProductApiKey()).thenReturn("HU8s0Kj6f848YhcLKz8Cl3tYbn9ezTwm9xybNwB7");
    }

    @Test
    public void getFundListTest() {
        resourceResolver = context.resourceResolver();
        context.load().json("/mutualFundList.json", "/content/vcm/us/en/member/products/mutual-funds");
        String fundListJson=productListingService.getFundList(resourceResolver);
        String endpoint=productListingService.getFundListEndPoint();
        String apiKey=productListingService.getFundListApiKey();
        productListingService.getEtfListEndpoint();
        productListingService.getHighIndexEndPoint();
        Assert.assertEquals("HU8s0Kj6f848YhcLKz8Cl3tYbn9ezTwm9xybNwB7",apiKey);
        Assert.assertNotNull(fundListJson);
        Assert.assertNotNull(endpoint);
    }

	@Test
	public void getStrategyListTest() {
		resourceResolver = context.resourceResolver();
		when (config.getStrategyPageRootPath()).thenReturn("/content/vcm/us/en/institutional-investor/our-strategies");
		productListingService.setConstantsConfigService(constantConfigService);
		productListingService.activate(config);
		context.load().json("/strategyList.json", "/content/vcm/us/en/institutional-investor/our-strategies");
		context.load().json("/filterTags.json", "/content/cq:tags/vcm");
		String fundListJson=productListingService.getStrategyList(resourceResolver);
		String endpoint=productListingService.getStrategyListEndPoint();
		String apiKey=productListingService.getProductApiKey();
		Assert.assertEquals("HU8s0Kj6f848YhcLKz8Cl3tYbn9ezTwm9xybNwB7",apiKey);
		Assert.assertNotNull(fundListJson);
		Assert.assertNotNull(endpoint);
	}
	
	@Test
	public void getStrategyRootPageNullTest() {
		resourceResolver = context.resourceResolver();
		context.load().json("/strategyList.json", "/content/vcm/us/en/institutional-investor/our-strategies");
		when (config.getStrategyPageRootPath()).thenReturn(null);
		productListingService.setConstantsConfigService(constantConfigService);
		productListingService.activate(config);
		String fundListJson=productListingService.getStrategyList(resourceResolver);
		String endpoint=productListingService.getStrategyListEndPoint();
		String apiKey=productListingService.getProductApiKey();
		Assert.assertEquals("HU8s0Kj6f848YhcLKz8Cl3tYbn9ezTwm9xybNwB7",apiKey);
		Assert.assertEquals("{}",fundListJson);
		Assert.assertNotNull(endpoint);
	}
	
	@Test
	public void testGetETFList() {
		resourceResolver = context.resourceResolver();
		context.load().json("/mutualFundList.json", "/content/vcm/us/en/member/products/mutual-funds");
		when (config.getETFPageRootPath()).thenReturn("/content/vcm/us/en/member/products/mutual-funds");
		productListingService.activate(config);
		
		Assert.assertNotNull(productListingService.getETFList(resourceResolver));
	}
	
	@Test
	public void testFundDetailPojoSettersGetters() {
	    FundDetailPojo fundDetailPojo = new FundDetailPojo();
		fundDetailPojo.getSolutionType();
		fundDetailPojo.getMemberFactsheetUrl();
		fundDetailPojo.getAdvisorFactsheetUrl();
		fundDetailPojo.getTicker();
		fundDetailPojo.getFundName();
		fundDetailPojo.getFundDescription();
		fundDetailPojo.getFactsheeturl();
		fundDetailPojo.getDetailpageurl();
		fundDetailPojo.isActiveFundPage();
		fundDetailPojo.getFundId();
		fundDetailPojo.getFranchise();
		fundDetailPojo.getIraEligible();
		fundDetailPojo.getAssetClass();
	}
}
