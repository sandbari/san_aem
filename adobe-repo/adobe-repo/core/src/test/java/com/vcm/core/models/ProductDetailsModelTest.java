package com.vcm.core.models;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import com.vcm.core.service.ProductDetailService;
import com.vcm.core.service.VCMSiteConfiguationService;
import com.vcm.core.service.impl.ProductDetailServiceImpl;
import com.vcm.core.service.impl.VCMSiteConfiguationServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(AemContextExtension.class)
public class ProductDetailsModelTest {
	
	private ProductDetailsModel productDetailsModel;
	private ProductDetailService productDetailService;
	VCMSiteConfiguationService vcmSiteConfiguationService;
	private Page currentPage;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	
	@BeforeEach
	public void setup() throws Exception {
	
		productDetailsModel = new ProductDetailsModel();
		productDetailService = new ProductDetailServiceImpl();
		vcmSiteConfiguationService=new VCMSiteConfiguationServiceImpl();
	    context.registerInjectActivateService(productDetailService);
	    productDetailsModel.setProductDetailService(productDetailService);
	    productDetailsModel.setVcmSiteConfigService(vcmSiteConfiguationService);
	}

	
	 @Test
	    void testInit() throws Exception {
	    	context.load().json("/mutualDetailPage.json", "/content/vcm/us/en/member/products/mutual-funds");
	    	currentPage = context.currentPage("/content/vcm/us/en/member/products/mutual-funds");
	    	productDetailsModel.setCurrentPage(currentPage);
	    	productDetailsModel.init();
		 	List<String> colorArray = new ArrayList<String>();
		 	colorArray.add("#004B98");
		 	colorArray.add("#9D6915");
	    	String[] productsServiceUrl = {"annualreturns:AnnualReturns", "assetallocation:AssetAllocation"};
			productDetailsModel.setProductDetailEndPoint("https://dev.api.vcm.com/search/product/");
			productDetailsModel.setProductDetailAPIKey("aWNSUczYsa1acwedSJw4eGjjLWVRGtj6CppidZL8");
			productDetailsModel.setColorArray(colorArray);
			productDetailsModel.setProductsServiceUrl(productsServiceUrl);
			productDetailsModel.setLabelsJson("labelsJson");
			productDetailsModel.setConfigJson("configJson");
			Assert.assertNotNull(productDetailsModel.getProductDetailEndPoint());
			Assert.assertNotNull(productDetailsModel.getProductDetailAPIKey());
			Assert.assertNotNull(productDetailsModel.getProductsServiceUrl());
			Assert.assertNotNull(productDetailsModel.getCurrentPage());
			Assert.assertNotNull(productDetailsModel.getProductDetailService());
			Assert.assertNotNull(productDetailsModel.getConfigJson());
			Assert.assertNotNull(productDetailsModel.getLabelsJson());
			Assert.assertNotNull(productDetailsModel.getConfigJson());
		 	Assert.assertNotNull(productDetailsModel.getColorArray());
			productDetailsModel.getStrategyDetailEndPoint();
			productDetailsModel.getIndexDetailEndPoint();
			productDetailsModel.getVcmSiteConfigService();
	    }
	
}
