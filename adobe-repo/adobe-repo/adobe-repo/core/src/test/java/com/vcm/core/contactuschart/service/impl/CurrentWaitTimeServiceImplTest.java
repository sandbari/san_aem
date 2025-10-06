package com.vcm.core.contactuschart.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class CurrentWaitTimeServiceImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    private GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    @Mock
    private ContactDetailUitiliyService contactDetailUitiliyService;

    @Mock
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;
    
    @Mock
    CloseableHttpResponse closeableHttpResponse;
    
    private CurrentWaitTimeServiceImpl currentWaitTimeServiceImpl;
    private GenesysAuthTokenGeneratorImpl genesysAuthTokenGeneratorImpl;

    @BeforeEach
    void setup() throws Exception {
    	currentWaitTimeServiceImpl = new CurrentWaitTimeServiceImpl();
    	currentWaitTimeServiceImpl.setGenesysAuthTokenGenerator(genesysAuthTokenGenerator);
    	currentWaitTimeServiceImpl.setContactDetailWaitTimeAPIConfigs(contactDetailWaitTimeAPIConfigs);
    	currentWaitTimeServiceImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	
    	genesysAuthTokenGeneratorImpl = new GenesysAuthTokenGeneratorImpl();
    	GenesysAuthTokenGeneratorImpl.Config config = mock(GenesysAuthTokenGeneratorImpl.Config.class);
    	Mockito.when(config.authTokenEndpointUrl()).thenReturn("https://login.usw2.pure.cloud/oauth/token");
    	Mockito.when(config.clientId()).thenReturn("08551d14-9fcd-41fe-8252-78b928cb5e5a");
    	Mockito.when(config.clientSecret()).thenReturn("St5UHUySQbjJghtGr49UyxnOsJt04ketQnLjwFlmhho");  
    	genesysAuthTokenGeneratorImpl.activate(config);
    	
    }

    @Test
    public void testCurrentWaitTimeServiceImpl() throws Exception {
    	String currentWaitTimeEndpoint = "https://api.usw2.pure.cloud/api/v2/routing/queues/34024a0e-3a6b-40e3-a584-dca72807c6a6/estimatedwaittime";
    	Mockito.when(contactDetailWaitTimeAPIConfigs.currentWaitTimeEndpoint()).thenReturn(currentWaitTimeEndpoint);
    	genesysAuthTokenGeneratorImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	String access_token = genesysAuthTokenGeneratorImpl.getAuthToken();    	
    	Mockito.when(genesysAuthTokenGenerator.getAuthToken()).thenReturn(access_token);
    	assertNotNull(currentWaitTimeServiceImpl.getCurrentWaitTime());
    }
}
