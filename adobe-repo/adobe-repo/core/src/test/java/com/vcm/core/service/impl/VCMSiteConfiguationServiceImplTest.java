package com.vcm.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.ConstantsConfigService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class VCMSiteConfiguationServiceImplTest {


	@Mock
	private ConstantsConfigService constantsConfigService;
	
	public final AemContext context = new AemContext();

	VCMSiteConfiguationServiceImpl vcmSiteConfiguationServiceImpl;
	
    private ResourceResolver resourceResolver;
    
    @Mock
	private ResourceResolverFactory resourceResolverFactory;
	
		
	@BeforeEach
	public void setUp() throws LoginException {
		resourceResolver=context.resourceResolver();
		 context.load().json("/configPage.json", "/content/vcm/us/vcm-site-configurations");
		vcmSiteConfiguationServiceImpl=new VCMSiteConfiguationServiceImpl();
		vcmSiteConfiguationServiceImpl.setResourceResolverFactory(resourceResolverFactory);
		vcmSiteConfiguationServiceImpl.setConstantsConfigService(constantsConfigService);
		Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
		Mockito.when(constantsConfigService.getVcmConfigPagePath()).thenReturn("/content/vcm/us/vcm-site-configurations");
		
	}
	
	@Test
	public void getSiteConfigAsJsonTest() {
			String configKey="etfLinks";
			String json = vcmSiteConfiguationServiceImpl.getSiteConfigAsJson(configKey);
			Map<String, List<String>> configMap = vcmSiteConfiguationServiceImpl.getSiteConfigAsMap(configKey);
			Assert.assertNotNull(json);
			Assert.assertNotNull(configMap);
	}
	
	@Test
	public void getLabelsConfigAsJsonTest() {
			Map<String, Object> labelsConfig = vcmSiteConfiguationServiceImpl.getLabelConfigAsMap();
			Assert.assertNotNull(labelsConfig);
	}

}
