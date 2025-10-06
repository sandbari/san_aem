package com.vcm.core.contactuschart.service.impl;

import static org.mockito.Mockito.mock;

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
class WaitTimeChartImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @Mock
    private ContactDetailUitiliyService contactDetailUitiliyService;
    
    @Mock
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    @Mock
    private GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    private WaitTimeChartImpl waitTimeChartImpl;
    private GenesysAuthTokenGeneratorImpl genesysAuthTokenGeneratorImpl;

    @BeforeEach
    void setup() throws Exception {
    	waitTimeChartImpl = new WaitTimeChartImpl();
    	waitTimeChartImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	waitTimeChartImpl.setContactDetailWaitTimeAPIConfigs(contactDetailWaitTimeAPIConfigs);
    	waitTimeChartImpl.setGenesysAuthTokenGenerator(genesysAuthTokenGenerator);
    	
    	genesysAuthTokenGeneratorImpl = new GenesysAuthTokenGeneratorImpl();
    	GenesysAuthTokenGeneratorImpl.Config config = mock(GenesysAuthTokenGeneratorImpl.Config.class);
    	Mockito.when(config.authTokenEndpointUrl()).thenReturn("https://login.usw2.pure.cloud/oauth/token");
    	Mockito.when(config.clientId()).thenReturn("08551d14-9fcd-41fe-8252-78b928cb5e5a");
    	Mockito.when(config.clientSecret()).thenReturn("St5UHUySQbjJghtGr49UyxnOsJt04ketQnLjwFlmhho");  
    	genesysAuthTokenGeneratorImpl.activate(config);
    	
    	String waitTimeHistoryEndpoint = "https://api.usw2.pure.cloud/api/v2/analytics/conversations/aggregates/query";
    	String waitTimeHistoryQueueId = "34024a0e-3a6b-40e3-a584-dca72807c6a6";
    	Integer waitTimeHistoryInterval = 30;
    	Mockito.when(contactDetailWaitTimeAPIConfigs.WaitTimeHistoryEndpoint()).thenReturn(waitTimeHistoryEndpoint);
    	Mockito.when(contactDetailWaitTimeAPIConfigs.WaitTimeHistoryQueueId()).thenReturn(waitTimeHistoryQueueId);
    	Mockito.when(contactDetailWaitTimeAPIConfigs.WaitTimeHistoryInterval()).thenReturn(waitTimeHistoryInterval);
    	
    }

    @Test
    public void testGetJsonFromService() throws Exception {
    	genesysAuthTokenGeneratorImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	String access_token = genesysAuthTokenGeneratorImpl.getAuthToken();    	
    	Mockito.when(genesysAuthTokenGenerator.getAuthToken()).thenReturn(access_token);
    	
    	waitTimeChartImpl.generateWaittimeChartJson();
    }
    
    @Test
    public void testElse() throws Exception{
    	Mockito.when(contactDetailUitiliyService.isError(Mockito.anyInt())).thenReturn(true);
    	waitTimeChartImpl.generateWaittimeChartJson();
    }
}
