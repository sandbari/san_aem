package com.vcm.core.servlets;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.BlogListingService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class GetAllBlogPostServletTest {

	@Mock
	BlogListingService blogListingService;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private GetAllBlogPostsServlet getAllBlogPostsServlet;

	@BeforeEach
	public void setup() throws Exception {
		getAllBlogPostsServlet = new GetAllBlogPostsServlet();
		getAllBlogPostsServlet.setBlogListing(blogListingService);
		Mockito.when(blogListingService.blogListJsonString(Mockito.any())).thenReturn("FundList");
	}

	@Test
	void testGetMethodSearchContext() throws Exception {
		MockSlingHttpServletRequest mockReq = context.request();
		MockSlingHttpServletResponse mockRes = context.response();
		getAllBlogPostsServlet.doGet(mockReq, mockRes);
		Assert.assertEquals(mockRes.getStatus(), 200);
		Assert.assertNotNull(mockRes.getContentLength());

	}


}