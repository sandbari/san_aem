package com.vcm.core.service.impl;

import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.contentsync.handler.util.RequestResponseFactory;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class LearnMoreServiceImplTest {

	private LearnMoreServiceImpl learnMoreService= new LearnMoreServiceImpl();
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    @Mock
    RequestResponseFactory service1;
    @Mock
	SlingRequestProcessor service2;
	@BeforeEach
	public void setUp() {
		
		Mockito.when(service1.createRequest(Mockito.any(), Mockito.any())).thenReturn(context.request());
		Mockito.when(service1.createResponse(Mockito.any())).thenReturn(context.response());
	}

	@Test
	public void getHtmlContentDetail() {	 
		context.load().json("/learnmore.json", "/content/vcm/language-masters/en/member/advice-tools/investor-learning-center-day1-1/retirement/jcr:content/root/responsivegrid");
		context.request().setServletPath("/content/vcm/language-masters/en/member/advice-tools/investor-learning-center-day1-1.learnmore.retirement.html");
		context.requestPathInfo().setSelectorString("learnmore.retirement");
		context.requestPathInfo().setExtension(".html");
		learnMoreService.setRequestResponseFactory(service1);
		learnMoreService.setRequestProcessor(service2);
		Assert.assertNotNull(learnMoreService.getRenderedHTML(context.request(), context.response()));
	}
	@Test
	public void getHtmlContentDefault() {	 
		context.load().json("/learnmore.json", "/content/vcm/language-masters/en/member/advice-tools/investor-learning-center-day1-1/retirement/jcr:content/root/responsivegrid");
		context.request().setServletPath("/content/vcm/language-masters/en/member/advice-tools/investor-learning-center-day1-1.learnmore.default.html");
		context.requestPathInfo().setSelectorString("learnmore.default");
		context.requestPathInfo().setExtension(".html");
		learnMoreService.setRequestResponseFactory(service1);
		learnMoreService.setRequestProcessor(service2);
		Assert.assertNotNull(learnMoreService.getRenderedHTML(context.request(), context.response()));
	}
	
}
