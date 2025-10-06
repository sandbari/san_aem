package com.vcm.core.contactuschart.schedulers;

import java.util.HashSet;
import java.util.Set;

import org.apache.sling.settings.SlingSettingsService;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.dam.api.Asset;
import com.vcm.core.common.MockJsonUtility;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.service.WaitTimeChartService;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.service.EmailService;
import com.vcm.core.utils.FileHandlerService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class GenesysApiFetchJobTest {

    @Mock
    private SlingSettingsService slingSettingsService;

    @Mock
    private WaitTimeChartService waitTimeChartService;

    @Mock
    private OpenHoursScheduleGenerator openHoursScheduleGenerator;

    @Mock
    private FileHandlerService fileHandlerService;

    @Mock
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    @Mock
    private ContactDetailUitiliyService contactDetailUitiliyService;

    @Mock
    private EmailService emailService;
    
    @Mock
    Asset asset;
    
	private GenesysApiFetchJob genesysApiFetchJob;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    private Set<String> runmodes = null;

    @BeforeEach
    void setup() throws Exception {
    	genesysApiFetchJob = new GenesysApiFetchJob();
    	genesysApiFetchJob.setSlingSettingsService(slingSettingsService);
    	genesysApiFetchJob.setContactDetailUitiliyService(contactDetailUitiliyService);
    	genesysApiFetchJob.setContactDetailWaitTimeAPIConfigs(contactDetailWaitTimeAPIConfigs);
    	genesysApiFetchJob.setEmailService(emailService);
    	genesysApiFetchJob.setFileHandlerService(fileHandlerService);
    	genesysApiFetchJob.setOpenHoursScheduleGenerator(openHoursScheduleGenerator);
    	genesysApiFetchJob.setWaitTimeChartService(waitTimeChartService);
    }

	@Test
	public void testRun() throws Exception {
    	runmodes = new HashSet<String>();
    	runmodes.add("author");
    	runmodes.add("publish");
    	genesysApiFetchJob.setSlingSettingsService(slingSettingsService);
    	Mockito.when(slingSettingsService.getRunModes()).thenReturn(runmodes);
    	Mockito.when(waitTimeChartService.generateWaittimeChartJson()).thenReturn(MockJsonUtility.getJsonData("wait-time.json"));
    	Mockito.when(contactDetailWaitTimeAPIConfigs.waitTimeChartFilePath()).thenReturn("/content/dam/vcm/configs/chart-data.json");
    	Mockito.when(openHoursScheduleGenerator.getOpenHoursScheduleJson()).thenReturn(MockJsonUtility.getJsonData("openHoursScheduleData.json"));
    	Mockito.when(contactDetailWaitTimeAPIConfigs.openHoursScheduleFilePath()).thenReturn("/content/dam/vcm/configs/openHours-data.json");
    	Mockito.when(fileHandlerService.createFile(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(asset);
    	
    	genesysApiFetchJob.run();
	}

}
