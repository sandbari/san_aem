package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.day.cq.wcm.api.PageManager;
import com.vcm.core.service.AudienceSelectorService;

import io.wcm.testing.mock.aem.junit5.AemContext;

public class ContactVictoryModelTest {
	@InjectMocks
	private ContactVictoryModel contactVictoryTest;
	@Mock
	private ResourceResolver mockResourceResolver;
	@Mock
	SlingHttpServletRequest mockSlingRequest;
	@Mock
	AudienceSelectorService audienceSelectorService;
	@Mock
	PageManager pageManger;
	
	private List<String> allowedAudience = new ArrayList<String>();
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    @BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
    @BeforeEach
    public void setup() throws Exception {
        when(audienceSelectorService.getAllowedAudienceForComponent(mockSlingRequest)).thenReturn(allowedAudience);
    	contactVictoryTest.setResourceResolver(mockResourceResolver);
    	contactVictoryTest.setBgImageDesktopShow("Leader Image");
    	contactVictoryTest.setBgImageMobileShow("Leader Image");
    	contactVictoryTest.setHeading("Leader Image");
    	contactVictoryTest.setDescription("Leader Image");
    	contactVictoryTest.setLinkText("Leader Image");
    	contactVictoryTest.setLinkUrl("Leader Image");
		contactVictoryTest.setContactLinkUrlFinancial("Leader Image");
		contactVictoryTest.setContactLinkUrlII("Leader Image");
    	contactVictoryTest.setTabSelect("Leader Image");
		contactVictoryTest.setAltText("alt text");	
    }
    
    @Test
    public void testGetterSetters() throws Exception {	
    	contactVictoryTest.init();
        assertNotNull(contactVictoryTest.getBgImageDesktopShow());
        assertNotNull(contactVictoryTest.getBgImageMobileShow());
        assertNotNull(contactVictoryTest.getHeading());
        assertNotNull(contactVictoryTest.getDescription());
        assertNotNull(contactVictoryTest.getLinkText());        
        assertNotNull(contactVictoryTest.getLinkUrl());
		assertNotNull(contactVictoryTest.getContactLinkUrlFinancial());
		assertNotNull(contactVictoryTest.getContactLinkUrlII());
        assertNotNull(contactVictoryTest.getTabSelect());
		assertNotNull(contactVictoryTest.getAltText());
		assertNotNull(contactVictoryTest.getAllowedAudience());
    }	

	@Test
	void testInitWithElse() {
		contactVictoryTest.setResourceResolver(mockResourceResolver);
		Mockito.when(mockResourceResolver.adaptTo(PageManager.class)).thenReturn(pageManger);
		contactVictoryTest.setLinkText("Link Text");
		contactVictoryTest.setLinkUrl("Link Url");
		contactVictoryTest.init();
	}
}