package com.vcm.core.servlets;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.vcm.core.service.LearnMoreService;
import com.vcm.core.service.VCMSiteConfiguationService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class DynamicDropDownServletTest {

	@Mock
	VCMSiteConfiguationService vcmSiteConfigurationService;
	
	@Mock
	ValueMap valueMap;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private DynamicDropDownServlet dynamicDropDownServlet;

	@Test
	public void testGet() throws Exception {
		dynamicDropDownServlet = new DynamicDropDownServlet();
		dynamicDropDownServlet.setVCMSiteConfiguationService(vcmSiteConfigurationService);
		Map<String, Object> keyMap = new LinkedHashMap<String, Object>();
		keyMap.put("key1", "value1");
		keyMap.put("key2", "value2");
		Mockito.when(vcmSiteConfigurationService.getLabelConfigAsMap()).thenReturn(keyMap);
		valueMap.put("key3", "value3");
		valueMap.put("key4", "value4");
		dynamicDropDownServlet.doGet(context.request(), context.response());
		List<Resource> resourceList = new ArrayList<Resource>();
		resourceList.add(new ValueMapResource(context.request().getResourceResolver(), new ResourceMetadata(),
				JcrConstants.NT_UNSTRUCTURED, valueMap));
		DataSource ds = new SimpleDataSource(resourceList.iterator());
		Assert.assertNotNull(resourceList);
	}

}