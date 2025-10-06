package com.vcm.core.servlets;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.ProductMenuService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class GetResourceByTagServletTest {

	@Mock
	ProductMenuService productMenuService;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private GetResourcesByTagServlet getResourcesByTagServlet;

	@BeforeEach
	public void setup() throws Exception {
		getResourcesByTagServlet = new GetResourcesByTagServlet();
		getResourcesByTagServlet.setProductMenuService(productMenuService);
	}

	@Test
	void testGetMethodSearchContext() throws Exception {
		MockSlingHttpServletRequest mockReq = context.request();
		MockSlingHttpServletResponse mockRes = context.response();
		getResourcesByTagServlet.doGet(mockReq, mockRes);
		Assert.assertEquals(mockRes.getStatus(), 200);
		Assert.assertNotNull(mockRes.getContentLength());

	}


}