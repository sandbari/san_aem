package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.impl.ConstantsConfigServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class ContactDetailsModelTest {

	private ContactDetailsModel contactDetailsModel;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    private ResourceResolver resourceResolver;
    private Resource phoneList;

    @BeforeEach
    public void setup() throws Exception {
		contactDetailsModel = new ContactDetailsModel();
    	ConstantsConfigService constantsConfigService = new ConstantsConfigServiceImpl();
    	context.registerInjectActivateService(constantsConfigService);
    	contactDetailsModel.setConstantsConfigService(constantsConfigService);
    	
		resourceResolver = context.resourceResolver();
		context.load().json("/contactDetails.json", "/content/vcm/language-masters/en/contact-us");
		phoneList = resourceResolver.getResource("/content/vcm/language-masters/en/contact-us");
		contactDetailsModel.setResourceResolver(context.resourceResolver()); 
		contactDetailsModel.setPhoneList(phoneList);
    }
    
    @Test
    public void testInit() throws Exception {
    	contactDetailsModel.init();
    }

    @Test
	public void testGetterSetters() throws Exception {
		ContactDetailsModel contactDetailsModel = new ContactDetailsModel();
		contactDetailsModel.setResourceResolver(context.resourceResolver()); 
		
		contactDetailsModel.setPhoneSectionHeading("By Phone or Email");
		contactDetailsModel.setPhoneIconImage("/content/dam/vcm/contact-details/images/phoneicon.png");
		contactDetailsModel.setAltTextPhone("Phone");
		contactDetailsModel.setPhoneNumber("1-800-235-8396");
		contactDetailsModel.setEmailIconImage("/content/dam/vcm/contact-details/images/email.png");
		contactDetailsModel.setAltTextEmail("Email");
		contactDetailsModel.setEmail("usaamutualfunds@vcm.com");
		contactDetailsModel.setOpenInfoText("Open Now");
		contactDetailsModel.setClosedInfoText("Closed");
		contactDetailsModel.setTodayOpenHoursText("Today");
		contactDetailsModel.setHoursText("Hours");
		contactDetailsModel.setMondayToFridayText("Monday - Friday");
		contactDetailsModel.setMondayToFridayOpenHours("8:30am-9:00pm (ET)");
		contactDetailsModel.setCurrentWaitTimeText("Current wait time");
		contactDetailsModel.setMailSectionHeading("By mail");
		contactDetailsModel.setMailiconImage("/content/dam/vcm/contact-details/images/mailicon.png");
		contactDetailsModel.setAltTextMail("Mail");
		contactDetailsModel.setMailingAddressHeading("Victory Funds Mailing Address");
		contactDetailsModel.setMailingAddress("4249 Easton Way, Suite 400");
		contactDetailsModel.setLinkText("Mailing a check? See how.");
		contactDetailsModel.setLinkURL("/content/vcm/us/en");
		contactDetailsModel.setTabSelect("_self");
		contactDetailsModel.setPopupOverlayContentPath("/content/dam/vcm");
		contactDetailsModel.setInternationalText("International");
		contactDetailsModel.setInternationalNumber("123456789");
		contactDetailsModel.setFaxNumber("9876543210");
		
		
		assertNotNull(contactDetailsModel.getPhoneSectionHeading());
		assertNotNull(contactDetailsModel.getPhoneIconImage());
		assertNotNull(contactDetailsModel.getAltTextPhone());
		assertNotNull(contactDetailsModel.getPhoneNumber());
		assertNotNull(contactDetailsModel.getEmailIconImage());
		assertNotNull(contactDetailsModel.getAltTextEmail());
		assertNotNull(contactDetailsModel.getEmail());
		assertNotNull(contactDetailsModel.getOpenInfoText());
		assertNotNull(contactDetailsModel.getClosedInfoText());
		assertNotNull(contactDetailsModel.getTodayOpenHoursText());
		assertNotNull(contactDetailsModel.getHoursText());
		assertNotNull(contactDetailsModel.getMondayToFridayText());
		assertNotNull(contactDetailsModel.getMondayToFridayOpenHours());
		assertNotNull(contactDetailsModel.getCurrentWaitTimeText());
		assertNotNull(contactDetailsModel.getMailSectionHeading());
		assertNotNull(contactDetailsModel.getMailiconImage());
		assertNotNull(contactDetailsModel.getAltTextMail());
		assertNotNull(contactDetailsModel.getMailingAddressHeading());
		assertNotNull(contactDetailsModel.getMailingAddress());
		assertNotNull(contactDetailsModel.getLinkText());
		assertNotNull(contactDetailsModel.getLinkURL());
		assertNotNull(contactDetailsModel.getTabSelect());
		assertNotNull(contactDetailsModel.getContactList());
		assertNotNull(contactDetailsModel.getInternationalText());
		assertNotNull(contactDetailsModel.getInternationalNumber());
		assertNotNull(contactDetailsModel.getFaxNumber());
		contactDetailsModel.getPopupId();
		contactDetailsModel.getStaticPhoneIconImage();
		contactDetailsModel.getStaticAltTextPhone();
	}
}
