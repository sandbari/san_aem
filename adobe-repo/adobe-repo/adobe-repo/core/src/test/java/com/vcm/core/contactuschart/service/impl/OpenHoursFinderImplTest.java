package com.vcm.core.contactuschart.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.common.MockJsonUtility;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.CurrentWaitTimeService;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.utils.FileHandlerService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class OpenHoursFinderImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @Mock
    private ContactDetailUitiliyService contactDetailUitiliyService;
    
    @Mock
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    @Mock
    private OpenHoursScheduleGenerator openHoursScheduleGenerator;

    @Mock
    private CurrentWaitTimeService currentWaitTimeService;
    
    @Mock
    private FileHandlerService fileHandlerService;
    
    private OpenHoursFinderImpl openHoursFinderImpl;

    @BeforeEach
    void setup() throws Exception {
    	openHoursFinderImpl = new OpenHoursFinderImpl();
    	openHoursFinderImpl.setContactDetailUitiliyService(contactDetailUitiliyService);
    	openHoursFinderImpl.setContactDetailWaitTimeAPIConfigs(contactDetailWaitTimeAPIConfigs);
    	openHoursFinderImpl.setCurrentWaitTimeService(currentWaitTimeService);
    	openHoursFinderImpl.setFileHandlerService(fileHandlerService);
    	openHoursFinderImpl.setOpenHoursScheduleGenerator(openHoursScheduleGenerator);
    	
    }

    @Test
    public void testGetJsonFromService() throws Exception {
    	
    	StringBuilder sb = new StringBuilder(MockJsonUtility.getJsonData("openHours-data.json"));
    	
    	Mockito.when(contactDetailWaitTimeAPIConfigs.openHoursScheduleFilePath()).thenReturn("/openHours-data.json");
    	Mockito.when(fileHandlerService.readFile("/openHours-data.json")).thenReturn(sb);
    	
    	assertNotNull(openHoursFinderImpl.getOpenCloseHours());
    }
}
