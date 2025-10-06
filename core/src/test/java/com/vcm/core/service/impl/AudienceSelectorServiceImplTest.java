package com.vcm.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class AudienceSelectorServiceImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    SlingHttpServletRequest request;
    
    @Mock
    ValueMap valuemap;
    
    @Mock
    Resource resource;
    
    private AudienceSelectorServiceImpl audienceSelectorServiceImpl;

    @BeforeEach
    void setup() throws Exception {
    	audienceSelectorServiceImpl = new AudienceSelectorServiceImpl();
    	
    	AudienceSelectorServiceImpl.Config config = mock(AudienceSelectorServiceImpl.Config.class);
    	Mockito.when(config.vcmMemberDomainKey()).thenReturn("investor");
    	Mockito.when(config.getMemberAudienceTypeKey()).thenReturn("usaa_member");
    	Mockito.when(config.getFinancialAudienceTypeKey()).thenReturn("financial_professional");
    	Mockito.when(config.vcmInstitutionalInvestorDomainKey()).thenReturn("institutional");
    	Mockito.when(config.getInvestorAudienceTypeKey()).thenReturn("institutional_investor");
    	Mockito.when(config.vcmFinancialDomainKey()).thenReturn("advisor");
    	audienceSelectorServiceImpl.activate(config);
    	Mockito.when(valuemap.containsKey(Mockito.any())).thenReturn(true);
    	Mockito.when(valuemap.get(Mockito.any(), Mockito.any())).thenReturn(true);
    	Mockito.when(request.getResource()).thenReturn(resource);
    	Mockito.when(request.getAttribute("VCM_AUDIENCE_TYPE")).thenReturn("usaa_member");
    }

    @Test
    public void testAudienceSelectorServiceImpl() {
    	
    	context.load().json("/contactVictory.json","/content/vcm/language-masters/en/financial-professional");
    	resource = context.currentResource("/content/vcm/language-masters/en/financial-professional");
    	
    	assertNotNull(audienceSelectorServiceImpl.getMemberAudienceTypeKey());
    	assertNotNull(audienceSelectorServiceImpl.getFinancialAudienceTypeKey());
    	assertNotNull(audienceSelectorServiceImpl.getInstitutionalInvestorAudienceTypeKey());
    	assertNotNull(audienceSelectorServiceImpl.getAllowedAudienceForComponent(request));
    	assertNotNull(audienceSelectorServiceImpl.getAllowedAudienceListFromValueMap(valuemap));
    	assertNotNull(audienceSelectorServiceImpl.getAllowedAudienceListFromResource(resource));
    	assertNotNull(audienceSelectorServiceImpl.isUserAuthorized(request));
    	assertNotNull(audienceSelectorServiceImpl.getHomePageConfig());
    	assertNotNull(audienceSelectorServiceImpl.getDomainKeyVsCookieConfig());
    	assertNotNull(audienceSelectorServiceImpl.getMappedDomainForUserTypeCookiesAsJsonString());
    	assertNotNull(audienceSelectorServiceImpl.getMappedDomainForUserTypeCookiesAsMap());
    	assertNotNull(audienceSelectorServiceImpl.trimDomainURL("https://dev.vcm.com/"));
    	assertNotNull(audienceSelectorServiceImpl.getMappedDomainForPage(resource));
    	assertNotNull(audienceSelectorServiceImpl.getVcmcorpHome());
    	assertNotNull(audienceSelectorServiceImpl.getVcmMemberHome());
    	assertNotNull(audienceSelectorServiceImpl.getVcmFinancialPath());
    	assertNotNull(audienceSelectorServiceImpl.getVcmInstitutionalInvestorPath());
    	assertNotNull(audienceSelectorServiceImpl.vcmMemberDomainKey());
    	assertNotNull(audienceSelectorServiceImpl.vcmFinancialDomainKey());
    	assertNotNull(audienceSelectorServiceImpl.vcmInstitutionalInvestorDomainKey());
    	
    	Mockito.when(request.getServerName()).thenReturn("investor");
    	assertNotNull(audienceSelectorServiceImpl.getUserTypeFromRequestDomain(request)); 
    	
    	Mockito.when(request.getServerName()).thenReturn("advisor");
    	assertNotNull(audienceSelectorServiceImpl.getUserTypeFromRequestDomain(request));  
    	
    	Mockito.when(request.getServerName()).thenReturn("institutional");
    	assertNotNull(audienceSelectorServiceImpl.getUserTypeFromRequestDomain(request));  
    	
    	Mockito.when(request.getServerName()).thenReturn("usaa_member");
    	assertNotNull(audienceSelectorServiceImpl.getUserTypeFromRequestDomain(request)); 
    	
    }
}
