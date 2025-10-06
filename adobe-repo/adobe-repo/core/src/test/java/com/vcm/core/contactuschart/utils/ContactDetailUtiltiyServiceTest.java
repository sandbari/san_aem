package com.vcm.core.contactuschart.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZonedDateTime;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.replication.Replicator;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class ContactDetailUtiltiyServiceTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    Replicator replicator;
    
    @Mock
    Session session;
    
    private ContactDetailUitiliyService contactDetailUitiliyService;

    @BeforeEach
    public void setup() throws Exception {
    	contactDetailUitiliyService = new ContactDetailUitiliyService();
    	
    	
    }
    
    @Test
    public void testIsError() {
    	boolean status = contactDetailUitiliyService.isError(200);
    	assertEquals(false, status);
    	assertNotNull(status);
    }
    
    @Test
    public void testIsErrorOthers() {
    	boolean status = contactDetailUitiliyService.isError(501);
    	assertEquals(true, status);
    	assertNotNull(status);
    }

    @Test
    public void testGetCentralTime() throws Exception {
    	ZonedDateTime centralTime =  contactDetailUitiliyService.getCentralTime("2020-05-22T20:01:28.136+05:30");
    	assertNotNull(centralTime);
    }
    
    @Test
    public void testGetTimeIntervalString() {
    	String timeInterval = contactDetailUitiliyService.getTimeIntervalString("2020-05-22T20:01:28.136+05:30");
    	assertNotNull(timeInterval);
    }
    
    @Test
    public void testGetTimeIntervalStringZeroMinutes() {
    	String timeInterval = contactDetailUitiliyService.getTimeIntervalString("2020-05-22T20:30:00.136+05:30");
    	assertNotNull(timeInterval);
    }
    
    @Test
    public void testGetDayOfWeekFromCSTString() {
    	String dow = contactDetailUitiliyService.getDayOfWeekFromCSTString("2020-05-22T20:01:28.136+05:30");
    	assertEquals("FRIDAY", dow);
    }
    
    @Test
    public void testReplicateAsset() throws Exception {
    	contactDetailUitiliyService.setResourceResolverFactory(resourceResolverFactory);
    	Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
    	contactDetailUitiliyService.setReplicator(replicator);
    	Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    	Mockito.when(session.isLive()).thenReturn(true);
    	contactDetailUitiliyService.replicateAsset("/content/dam/vcm");
    }
}
