package com.vcm.core.contactuschart.service.impl;

import static org.mockito.Mockito.mock;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
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
class GenesysAuthTokenGeneratorImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);


    @Mock
    private ContactDetailUitiliyService contactDetailUitiliyService;
    
    @Mock
    private GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    @Mock
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;
    
    @Mock
    CloseableHttpClient client;
    
    @Mock
    HttpPost request;
    
    private GenesysAuthTokenGeneratorImpl genesysAuthTokenGeneratorImpl;

    @BeforeEach
    void setup() throws Exception {
    	genesysAuthTokenGeneratorImpl = new GenesysAuthTokenGeneratorImpl();
    	genesysAuthTokenGeneratorImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	
    	GenesysAuthTokenGeneratorImpl.Config config = mock(GenesysAuthTokenGeneratorImpl.Config.class);
    	Mockito.when(config.authTokenEndpointUrl()).thenReturn("https://login.usw2.pure.cloud/oauth/token");
    	Mockito.when(config.clientId()).thenReturn("08551d14-9fcd-41fe-8252-78b928cb5e5a");
    	Mockito.when(config.clientSecret()).thenReturn("St5UHUySQbjJghtGr49UyxnOsJt04ketQnLjwFlmhho");  
    	genesysAuthTokenGeneratorImpl.activate(config);
    	
    	
    }

    @Test
    public void testGetAuthToken() throws Exception {    	
    	genesysAuthTokenGeneratorImpl.getAuthToken();
    }
    
    @Test
    public void testElse() throws Exception{
    	Mockito.when(contactDetailUitiliyService.isError(Mockito.anyInt())).thenReturn(true);
    	genesysAuthTokenGeneratorImpl.getAuthToken();
    }
}
