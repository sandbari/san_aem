package com.vcm.core.servlets;

import com.vcm.core.service.ProductListingService;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class MembersFundListServletTest {

	@Mock
	ProductListingService membersFundDetailsService;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private MembersFundListServlet membersFundListServlet;

	@BeforeEach
	public void setup() throws Exception {
		membersFundListServlet = new MembersFundListServlet();
		membersFundListServlet.setMembersFundDetailsService(membersFundDetailsService);
		Mockito.when(membersFundDetailsService.getFundList(Mockito.any())).thenReturn("FundList");
	}

	@Test
	void testGetMethodSearchContext() throws Exception {
		MockSlingHttpServletRequest mockReq = context.request();
		MockSlingHttpServletResponse mockRes = context.response();
		membersFundListServlet.doGet(mockReq, mockRes);
		Assert.assertEquals(mockRes.getStatus(), 200);
		Assert.assertNotNull(mockRes.getContentLength());

	}

}