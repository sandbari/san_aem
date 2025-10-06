package com.vcm.core.models;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.VCMSiteConfiguationService;
import com.vcm.core.service.impl.ConstantsConfigServiceImpl;
import com.vcm.core.service.impl.VCMSiteConfiguationServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class VCMPageModelTest {

	private VCMPageModel vcmPageModel;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	private ResourceResolver resourceResolver;

	@Mock
	ResourceResolverFactory resourceResolverFactory;

	@Mock
	AudienceSelectorService audienceSelectorService;

	@BeforeEach
	public void setup() throws Exception {
		resourceResolver=context.resourceResolver();
		vcmPageModel = new VCMPageModel();
		context.load().json("/mutualDetailPage.json", "/content/vcm/language-masters/en/member/mutual-detail");
		ConstantsConfigService constantsConfigService = new ConstantsConfigServiceImpl();
		VCMSiteConfiguationService vcmSiteConfiguationService=new VCMSiteConfiguationServiceImpl();
		context.registerInjectActivateService(constantsConfigService);
		vcmPageModel.setConstantsConfigService(constantsConfigService);
		vcmPageModel.setVcmSiteConfiguationService(vcmSiteConfiguationService);
		vcmPageModel.setAudienceSelectorService(audienceSelectorService);
		vcmPageModel.setCustomCanonicalUrl("https://www.vcm.com");
		vcmPageModel.setOgImagePath("/content/dam/vcm/basic/logo.png");
		vcmPageModel.setOgTitle("Member");
		String homePageConfig= "{'ALL','en.html'}";
		Mockito.when(audienceSelectorService.getHomePageConfig()).thenReturn(homePageConfig);

	}

	@Test
	void testInit() throws Exception {
		context.load().json("/popupContent.json", "/content/vcm/language-masters/en/member/popup-content-page");
		vcmPageModel.setCurrentPage(context.currentPage("/content/vcm/language-masters/en/member/mutual-detail"));
		vcmPageModel.setPopupContentPage("/content/vcm/language-masters/en/member/popup-content-page");
		vcmPageModel.setResourceResolver(resourceResolver);
		Mockito.when(audienceSelectorService.getMappedDomainForPage(Mockito.any())).thenReturn("qa.vcm.com");
		vcmPageModel.init();
		Assert.assertEquals("//assets.adobedtm.com/9038a1739344/e69bdabe1962/launch-15c65aff4b9c-development.min.js",
				vcmPageModel.getAnalyticsEndPoint());
		Assert.assertNotNull(vcmPageModel.getPopupContentPage());
		Assert.assertNotNull(vcmPageModel.getPageHeirarchy());
		Assert.assertNotNull(vcmPageModel.getConstantsConfigService());
		Assert.assertNotNull(vcmPageModel.getEtfPagePatterns());
		Assert.assertNotNull(vcmPageModel.getHomePageConfig());
		vcmPageModel.getPageType();
		vcmPageModel.getAllowedAudience();
		vcmPageModel.getDomainKeyConfig();
		vcmPageModel.getBugHerdUrl();
		Assert.assertNotNull(vcmPageModel.getCurrentPagePath());
		Assert.assertNotNull(vcmPageModel.getCurrentPageCanonicalUrl());
		Assert.assertNotNull(vcmPageModel.getCustomCanonicalUrl());
		Assert.assertNotNull(vcmPageModel.getOgImagePath());
		Assert.assertNotNull(vcmPageModel.getOgTitle());
	}

}
