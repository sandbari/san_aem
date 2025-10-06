package com.vcm.core.servlets;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.LearnMoreService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class LearnMoreServletTest {

	@Mock
	LearnMoreService learnMoreServiceImpl;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private LearnMoreServlet learnMoreServlet;

	@Test
	public void testGet() throws Exception {
		learnMoreServlet = new LearnMoreServlet();
		learnMoreServlet.setLearnMoreServiceImpl(learnMoreServiceImpl);
		Mockito.when(learnMoreServiceImpl.getRenderedHTML(Mockito.any(),Mockito.any())).thenReturn("<div>Test</div>");
		learnMoreServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}

}