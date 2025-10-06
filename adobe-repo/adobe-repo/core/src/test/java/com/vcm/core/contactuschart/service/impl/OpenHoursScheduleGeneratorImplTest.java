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
class OpenHoursScheduleGeneratorImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);


    @Mock
    private ContactDetailUitiliyService contactDetailUitiliyService;
    
    @Mock
    private GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    @Mock
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;
    
    private OpenHoursScheduleGeneratorImpl openHoursScheduleGeneratorImpl;
    private GenesysAuthTokenGeneratorImpl genesysAuthTokenGeneratorImpl;

    @BeforeEach
    void setup() throws Exception {
    	openHoursScheduleGeneratorImpl = new OpenHoursScheduleGeneratorImpl();
    	openHoursScheduleGeneratorImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	openHoursScheduleGeneratorImpl.setGenesysAuthTokenGenerator(genesysAuthTokenGenerator);
    	openHoursScheduleGeneratorImpl.setContactDetailWaitTimeAPIConfigs(contactDetailWaitTimeAPIConfigs);
    	
    	genesysAuthTokenGeneratorImpl = new GenesysAuthTokenGeneratorImpl();
    	GenesysAuthTokenGeneratorImpl.Config config = mock(GenesysAuthTokenGeneratorImpl.Config.class);
    	Mockito.when(config.authTokenEndpointUrl()).thenReturn("https://login.usw2.pure.cloud/oauth/token");
    	Mockito.when(config.clientId()).thenReturn("08551d14-9fcd-41fe-8252-78b928cb5e5a");
    	Mockito.when(config.clientSecret()).thenReturn("St5UHUySQbjJghtGr49UyxnOsJt04ketQnLjwFlmhho");  
    	genesysAuthTokenGeneratorImpl.activate(config);
    	
    	String openHoursScheduleGroupEndpoint = "https://api.usw2.pure.cloud/api/v2/architect/schedulegroups/";
    	String openHoursScheduleEndpoint = "https://api.usw2.pure.cloud/api/v2/architect/schedules/";
    	String scheduleGroupId = "ab909f0d-50fe-42d2-a819-7a90194947fb";
    	Mockito.when(contactDetailWaitTimeAPIConfigs.openHoursScheduleGroupEndpoint()).thenReturn(openHoursScheduleGroupEndpoint);
    	Mockito.when(contactDetailWaitTimeAPIConfigs.openHoursScheduleEndpoint()).thenReturn(openHoursScheduleEndpoint);
    	Mockito.when(contactDetailWaitTimeAPIConfigs.scheduleGroupId()).thenReturn(scheduleGroupId);
    	
    }

    @Test
    public void testGetJsonFromService() throws Exception {
    	genesysAuthTokenGeneratorImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	String access_token = genesysAuthTokenGeneratorImpl.getAuthToken();    	
    	Mockito.when(genesysAuthTokenGenerator.getAuthToken()).thenReturn(access_token);
    	
    	openHoursScheduleGeneratorImpl.getOpenHoursScheduleJson();
    }
}
