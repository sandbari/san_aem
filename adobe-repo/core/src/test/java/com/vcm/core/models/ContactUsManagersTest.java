package com.vcm.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.AudienceSelectorService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class ContactUsManagersTest {

	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
	//@Mock
	//AudienceSelectorService audienceSelectorService;
	
	private Resource mangerDetailsResource;
	@Mock
	private AudienceSelectorService audienceSelectorService;
	
	@BeforeEach
	public void setup() throws Exception {
		
		List<String> allowedaudience=new ArrayList<String>();
		allowedaudience.add("ALL");
		Mockito.when(audienceSelectorService.getAllowedAudienceForComponent(Mockito.any())).thenReturn(allowedaudience);
		Mockito.when(audienceSelectorService.isUserAuthorized(Mockito.any())).thenReturn(true);
		resourceResolver = context.resourceResolver();
		
		context.load().json("/contactUsManagers.json", "/content/vcm/language-masters/en/member/contact-us");
		resourceResolver = context.resourceResolver();
		mangerDetailsResource = resourceResolver.getResource("/content/vcm/language-masters/en/member/contact-us");
		context.request().setResource(mangerDetailsResource);
		context.request().setAttribute("audienceSelectorService", audienceSelectorService);


	}

	@Test
	void testProductAccorionModel() throws Exception {
		 ContactUsManagersModel modelObj = context.request().adaptTo(ContactUsManagersModel.class);
		 Assert.assertEquals("RelationShip Manager",modelObj.getHeading());
		 List<ContactManagerPrimaryDetailBean> primaryContacts = modelObj.getPrimaryContactDetailsList();
		 Assert.assertEquals("Head of Institutional",primaryContacts.get(0).getPrimaryContactDesignation());
		 Assert.assertEquals("216-898-2451",primaryContacts.get(0).getPrimaryContactPhoneNo());
		 Assert.assertEquals("Paul G. Pasicznyk",primaryContacts.get(0).getPrimaryContactName());
		 Assert.assertEquals("pgp@vcm.com",primaryContacts.get(0).getPrimaryContactMailId());
		 Assert.assertEquals("/content/dam/vcm/relationship-manager/Paul G. Pasicznyk.png",primaryContacts.get(0).getPrimaryContactProfilePhoto());
		 Assert.assertEquals("Test Alt Text",primaryContacts.get(0).getPrimaryContactProfileAltText());
		 List<ContactManagerDetailBean> secContacts = modelObj.getSecondaryContactDetailsList();
		 Assert.assertEquals("Test 1", secContacts.get(0).getSecondaryContactName());
		 Assert.assertEquals("12334556", secContacts.get(0).getSecondaryContactPhoneNo());
		 Assert.assertEquals("test1@vcm.com", secContacts.get(0).getSecondaryContactUsMailId());
		 modelObj.getAllowedAudience();
		 modelObj.getIsUserAuthorized();
	}

}