package com.vcm.core.contactuschart.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.replication.Replicator;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class ContactDetailWaitTimeAPIConfigsImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    Replicator replicator;
    
    @Mock
    Session session;
    
    private ContactDetailWaitTimeAPIConfigsImpl contactDetailWaitTimeAPIConfigsImpl;

    @BeforeEach
    void setup() throws Exception {
    	contactDetailWaitTimeAPIConfigsImpl = new ContactDetailWaitTimeAPIConfigsImpl();
        resourceResolver=context.resourceResolver();
    }

    @Test
    public void testContactWaitTimeAPIConfigs() {
    	ContactDetailWaitTimeAPIConfigsImpl.Config config = mock(ContactDetailWaitTimeAPIConfigsImpl.Config.class);
    	contactDetailWaitTimeAPIConfigsImpl.activate(config);
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.currentWaitTimeEndpoint());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.WaitTimeHistoryEndpoint());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.WaitTimeHistoryQueueId());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.WaitTimeHistoryInterval());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.openHoursScheduleGroupEndpoint());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.scheduleGroupId());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.openHoursScheduleEndpoint());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.waitTimeChartFilePath());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.openHoursScheduleFilePath());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.genesysSuccessFromEmailAddress());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.genesysFailureFromEmailAddress());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.waitTimeEmailTemplatePath());
    	assertNotNull(contactDetailWaitTimeAPIConfigsImpl.openHoursEmailTemplatePath());
    	contactDetailWaitTimeAPIConfigsImpl.genesysToEmailAddressList();
    }
}
