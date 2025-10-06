package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SiteConfigModelTest {

	private SiteConfigModel siteConfigModel;
	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		resourceResolver = context.resourceResolver();
		siteConfigModel = new SiteConfigModel();
		context.load().json("/siteConfig.json", "/content/vcm/siteconfig");
		Resource values = resourceResolver.getResource("/content/vcm/siteconfig");
		siteConfigModel.setValues(values);
		siteConfigModel.setKey("Key");
		siteConfigModel.setTitle("Title");
		
	}

	@Test
	void testLevelNavigation() throws Exception {
		siteConfigModel.init();
		
		assertNotNull(siteConfigModel.getKey());
		assertNotNull(siteConfigModel.getTitle());
		assertNotNull(siteConfigModel.getValuesList());
		siteConfigModel.setValuesList(siteConfigModel.getValuesList());
	}
}