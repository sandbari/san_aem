package com.vcm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(AemContextExtension.class)
public class PhoneDetailsModelTest{
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private PhoneDetailsModel phoneDetailsModel;
	private ResourceResolver resourceResolver;
	private Resource phoneResource;

	@BeforeEach
	public void setup() throws Exception {
		phoneDetailsModel = new PhoneDetailsModel();
		resourceResolver = context.resourceResolver();
		context.load().json("/phoneDetails.json", "/content/vcm/language-masters/en/contact-us");
		phoneResource = resourceResolver.getResource("/content/vcm/language-masters/en/contact-us");
		phoneDetailsModel.setPhoneList(phoneResource.getChild("phoneList"));
		phoneDetailsModel.setSegmentList(phoneResource.getChild("segmentList"));

		phoneDetailsModel.setPhoneConfig(phoneResource.getValueMap().get("phoneConfig", ""));
		phoneDetailsModel.setPhoneLink(phoneResource.getValueMap().get("phoneLink", ""));
		phoneDetailsModel.setDisplayMode(phoneResource.getValueMap().get("displayMode", ""));
		phoneDetailsModel.setBackToContactLink(phoneResource.getValueMap().get("backToContactLink", ""));
	}

	@Test
	public void testInit() throws Exception {
		phoneDetailsModel.init();
		atLeastOnce();
	}

	@Test
	public void testGetterSetters() throws Exception {
		phoneDetailsModel.setPhonesJson("[{\"staticOpenText\":\"USA\", \"staticPhoneNumber\": \"18001221121\"}]");
		phoneDetailsModel.setPhones(Collections.emptyList());
		phoneDetailsModel.setSegments(Collections.emptyList());

		assertNotNull(phoneDetailsModel.getPhoneConfig());
		assertNotNull(phoneDetailsModel.getPhoneLink());
		assertNotNull(phoneDetailsModel.getPhonesJson());
		assertNotNull(phoneDetailsModel.getPhoneList());
		assertNotNull(phoneDetailsModel.getSegmentList());
		assertNotNull(phoneDetailsModel.getSegments());
		assertNotNull(phoneDetailsModel.getPhones());
		assertNotNull(phoneDetailsModel.getDisplayMode());
		assertNotNull(phoneDetailsModel.getBackToContactLink());
	}
}