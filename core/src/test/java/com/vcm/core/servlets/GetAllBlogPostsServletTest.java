package com.vcm.core.servlets;

import javax.jcr.RepositoryException;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
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
class GetAllBlogPostsServletTest {

	@Mock
	private transient BlogListingService blogListingService;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private GetAllBlogPostsServlet getAllBlogPostsServlet;
	
	@BeforeEach
	public void setup() throws Exception {
		getAllBlogPostsServlet = new GetAllBlogPostsServlet();
		getAllBlogPostsServlet.setBlogListing(blogListingService);
	}

	@Test
	public void testGet() throws Exception {		
		Mockito.when(blogListingService.blogListJsonString(context.request())).thenReturn("HTML");
		getAllBlogPostsServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}
	
	@Test
    public void testGetWithException() throws Exception {
        Mockito.when(blogListingService.blogListJsonString(context.request())).thenThrow(RepositoryException.class);
        getAllBlogPostsServlet.doGet(context.request(), context.response());
        Assert.assertEquals(400, context.response().getStatus());

 

    }

}