package com.vcm.core.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class ProductDetailServiceImplTest {

	private ProductDetailServiceImpl ProductDetailService= new ProductDetailServiceImpl();
    private ProductDetailServiceImpl.Config config;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    private Page currentPage;
	@BeforeEach
	public void setUp() {
		String[] productsServiceUrl = {"annualreturns:AnnualReturns", "assetallocation:AssetAllocation"};
		config = mock(ProductDetailServiceImpl.Config.class);
		when (config.applicationName()).thenReturn("VCM");
		when (config.productDetailEndPoint()).thenReturn("https://sit.api.vcm.com/search/product/");
		when (config.productDetailAPIKey()).thenReturn("aWNSUczYsa1acwedSJw4eGjjLWVRGtj6CppidZL8");
		when (config.productsServiceUrl()).thenReturn(productsServiceUrl);
		ProductDetailService.activate(config);
	}

	@Test
	public void getProductDetailsTest() {
		context.load().json("/mutualDetailPage.json", "/content/vcm/us/en/member/products/mutual-funds");
    	currentPage = context.currentPage("/content/vcm/us/en/member/products/mutual-funds");
		String applicationName=ProductDetailService.getApplicationName();
		String configJson=ProductDetailService.getConfigAsJson(currentPage);
		String endpoint=ProductDetailService.getProductDetailEndPoint();
		String apiKey=ProductDetailService.getProductDetailAPIKey();
		String[] productsServiceUrl=ProductDetailService.getProductsServiceUrl();
		Assert.assertEquals("aWNSUczYsa1acwedSJw4eGjjLWVRGtj6CppidZL8",apiKey);
		Assert.assertNotNull(applicationName);
		Assert.assertNotNull(configJson);
		Assert.assertNotNull(endpoint);
		Assert.assertNotNull(productsServiceUrl);
	}
	
}
