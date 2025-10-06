package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class CustomTabsModelTest {

	private CustomTabsModel customTabModel;
	ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		customTabModel = new CustomTabsModel();
		customTabModel.setStickyRightRail("true");
		customTabModel.setTabType("stickytab");
		context.load().json("/customTab.json", "/content/vcm/language-masters/en/news");
		
	}

	@Test
	void testInit() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/en/news");
		customTabModel.setResource(resource);
		customTabModel.init();
		assertEquals("Social Press", customTabModel.getTabResourceList().get(0).get("tabTitle"));
		assertEquals("Social-Press", customTabModel.getTabResourceList().get(0).get("tabReference"));
		assertEquals("true",customTabModel.getStickyRightRail());
		assertEquals("stickytab",customTabModel.getTabType());
	}
	
	@Test
	void testInitNoTabTitle() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/en/news");
		customTabModel.setResource(resource);
		customTabModel.init();
		assertEquals("", customTabModel.getTabResourceList().get(1).get("tabTitle"));
	}
}

