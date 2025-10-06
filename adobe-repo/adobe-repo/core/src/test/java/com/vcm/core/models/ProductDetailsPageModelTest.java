package com.vcm.core.models;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.VCMSiteConfiguationService;
import com.vcm.core.service.impl.ConstantsConfigServiceImpl;
import com.vcm.core.service.impl.VCMSiteConfiguationServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
class ProductDetailsPageModelTest {

    private ProductDetailsPageModel productDetailsPageModel;
    private Page currentPage;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        productDetailsPageModel = new ProductDetailsPageModel();
		VCMSiteConfiguationService vcmSiteConfiguationService=new VCMSiteConfiguationServiceImpl();
    	ConstantsConfigService constantsConfigService = new ConstantsConfigServiceImpl();
    	context.registerInjectActivateService(constantsConfigService);
    	productDetailsPageModel.setConstantsConfigService(constantsConfigService);
		productDetailsPageModel.setVcmSiteConfiguationService(vcmSiteConfiguationService);
    }

    @Test
    void testInit() throws Exception {
    	context.load().json("/mutualDetailPage.json", "/content/vcm/us/en/member/products/mutual-funds");
    	currentPage = context.currentPage("/content/vcm/us/en/member/products/mutual-funds");
    	productDetailsPageModel.setCurrentPage(currentPage);
    	productDetailsPageModel.setFundDescription("fundDescription");
    	productDetailsPageModel.setMorningStarText("morningStarText");
    	productDetailsPageModel.setThemeColor("themeColor");
    	Assert.assertNotNull(productDetailsPageModel.getThemeColor());
    	productDetailsPageModel.setVictorySharesImage("victorySharesImage.png");
    	productDetailsPageModel.init();
    	Assert.assertEquals("/content/dam/vcm/vcm-content-fragments/test002", productDetailsPageModel.getHistoricalInformationUrl());
    	Assert.assertEquals("/content/dam/vcm/vcm-content-fragments/test002", productDetailsPageModel.getAllHoldingsUrl());
    	Assert.assertEquals("https://www.google.com", productDetailsPageModel.getProspectusUrl());
		Assert.assertEquals("https://www.google.com", productDetailsPageModel.getAdvisorprospectusUrl());
    	Assert.assertEquals("/content/dam/vcm/vcm-content-fragments/test002", productDetailsPageModel.getManagerCommentaryUrl());
    	//Assert.assertEquals("https://vcm-dev-aem65.adobecqms.net/member/solutions.html?fundID=USMIX", productDetailsPageModel.getPurchaseFundUrl());
    	Assert.assertEquals("/content/dam/vcm/vcm-content-fragments/pop-up/etf-site-message", productDetailsPageModel.getFactsheetUrl());
    	Assert.assertEquals("/content/dam/vcm/vcm-content-fragments/test002", productDetailsPageModel.getGipsComplainceUrl());
    	Assert.assertEquals("/content/dam/vcm/vcm-content-fragments/test002", productDetailsPageModel.getPremiumDiscountReport());
    	Assert.assertNotNull(productDetailsPageModel.getMorningStarText());
    	Assert.assertNotNull(productDetailsPageModel.getFundDescription());
    	Assert.assertNotNull(productDetailsPageModel.getVictorySharesImage());
    	Assert.assertNotNull(productDetailsPageModel.getTitleFromApiData());
    }
    
    @Test
    void testInitNoUrlNoTicker() throws Exception {
    	context.load().json("/funddetailWithNoUrl.json", "/content/vcm/us/en/member/products/mutual-funds-no-url");
    	currentPage = context.currentPage("/content/vcm/us/en/member/products/mutual-funds-no-url");
    	productDetailsPageModel.setCurrentPage(currentPage);
    	productDetailsPageModel.init();
    	Assert.assertEquals(null, productDetailsPageModel.getHistoricalInformationUrl());
    	//Assert.assertEquals("https://vcm-dev-aem65.adobecqms.net/member/solutions.html?fundID=USMIX", productDetailsPageModel.getPurchaseFundUrl());
    }

}