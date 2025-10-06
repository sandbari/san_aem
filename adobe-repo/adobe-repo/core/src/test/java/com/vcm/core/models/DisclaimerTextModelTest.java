package com.vcm.core.models;

import com.vcm.core.service.AudienceSelectorService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class DisclaimerTextModelTest {

	private DisclaimerTextModel disclaimerTextModel;
	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@Mock
	AudienceSelectorService audienceSelectorService;

	@BeforeEach
	public void setup() throws Exception {
		List<String> allowedaudience=new ArrayList<String>();
		allowedaudience.add("ALL");
		Mockito.when(audienceSelectorService.getAllowedAudienceForComponent(Mockito.any())).thenReturn(allowedaudience);
		resourceResolver = context.resourceResolver();
		disclaimerTextModel = new DisclaimerTextModel();
		context.load().json("/disclaimer.json", "/content/dam/vcm/disclaimer-text/test-disclaimer");
		Resource disclaimer = resourceResolver.getResource("/content/dam/vcm/disclaimer-text/test-disclaimer");
		disclaimerTextModel.setDisclaimerHeadText("heading");
		disclaimerTextModel.setDisclaimerTextList(disclaimer);
		disclaimerTextModel.setContainerwidth("true");
		disclaimerTextModel.setAudienceSelectorService(audienceSelectorService);
		disclaimerTextModel.setSlingRequest(context.request());
		disclaimerTextModel.setResourceResolver(resourceResolver);
	}

	@Test
	void testLevelNavigation() throws Exception {
		disclaimerTextModel.init();
		Assert.assertEquals("heading", disclaimerTextModel.getDisclaimerHeadText());
		Assert.assertEquals("true", disclaimerTextModel.getContainerwidth());
		Assert.assertNotNull(disclaimerTextModel.getDisclaimerContentFragmentList());
		junitx.framework.Assert.assertNotNull(disclaimerTextModel.getAllowedAudience());
	}
}