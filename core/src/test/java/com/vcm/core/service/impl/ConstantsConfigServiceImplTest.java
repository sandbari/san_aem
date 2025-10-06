package com.vcm.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

public class ConstantsConfigServiceImplTest {

	private ConstantsConfigServiceImpl constantsConfigServiceImpl= new ConstantsConfigServiceImpl();
	 private TestLogger logger = TestLoggerFactory.getTestLogger(constantsConfigServiceImpl.getClass());
	 private ConstantsConfigServiceImpl.Config config;
	@BeforeEach
	public void setUp() {
		config = mock(ConstantsConfigServiceImpl.Config.class);
		when (config.applicationName()).thenReturn("Victory Capital");
		when (config.websiteName()).thenReturn("VCM Site");
		when (config.websiteLocale()).thenReturn("en");
		when (config.memberSigninServiceURL()).thenReturn("no-url-available");
		when (config.ussa529PerformanceFundEndPoint()).thenReturn("https://sit.memberapi.vcm.com/search/products/USAA529");
		when (config.ussa529PerformanceFundKey()).thenReturn("gq2BA7h7VA7tuqkEtiWP57ILRY0COdUr7qgoARW0");
		when (config.analyticsEndPoint()).thenReturn("//assets.adobedtm.com/9038a1739344/e69bdabe1962/launch-15c65aff4b9c-development.min.js");
		when (config.getPopupContentFragmentPage()).thenReturn("/content/vcm/us/en/popup-content-page");
		when (config.purchaseFundUrl()).thenReturn("http://vcm-web.s3-website-us-east-1.amazonaws.com/sign-in");
		constantsConfigServiceImpl.setVcmConfigPagePath("/content/vcm/us/en");
		constantsConfigServiceImpl.activate(config);
	}

	@Test
	public void getApplicationNameTest() {
		String applicationName = "Victory Capital";
		assertEquals(config.applicationName(), applicationName);
	}
	@Test
	public void getWebsiteNameTest() {
		String websiteName = "VCM Site";
		assertEquals(config.websiteName(), websiteName);
	}
	@Test
	public void getWebsiteLocaleTest() {
		String websiteLocale = "en";
		assertEquals(config.websiteLocale(), websiteLocale);
	}
	@Test
	public void getMemberSigninServiceURLTest() {
			String memberSigninServiceUrl = "no-url-available";
			assertEquals(config.memberSigninServiceURL(), memberSigninServiceUrl);
	}
	@Test
	public void getAllVariablesTest() {
		assertEquals("Victory Capital", constantsConfigServiceImpl.getApplicationName());
		assertEquals("VCM Site", constantsConfigServiceImpl.getWebsiteName());
		assertEquals("en", constantsConfigServiceImpl.getWebsiteLocale());	
		assertEquals("no-url-available", constantsConfigServiceImpl.getMemberSigninServiceURL());
		assertEquals("https://sit.memberapi.vcm.com/search/products/USAA529", constantsConfigServiceImpl.getUSSA529PerformanceFundEndPoint());
		assertEquals("gq2BA7h7VA7tuqkEtiWP57ILRY0COdUr7qgoARW0", constantsConfigServiceImpl.getUSSA529PerformanceFundKey());
		assertEquals("//assets.adobedtm.com/9038a1739344/e69bdabe1962/launch-15c65aff4b9c-development.min.js", constantsConfigServiceImpl.getAnalyticsEndPoint());
		assertEquals("/content/vcm/us/en/popup-content-page", constantsConfigServiceImpl.getPopupContentFragmentPage());
		assertEquals("http://vcm-web.s3-website-us-east-1.amazonaws.com/sign-in", constantsConfigServiceImpl.getPurchaseFundUrl());	
		constantsConfigServiceImpl.getBugHerdUrl();
		constantsConfigServiceImpl.getVcmConfigPagePath();
	}
}
