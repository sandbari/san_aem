package com.vcm.core.servlets;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.json.JSONWriter;
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
class GetResourcesByTagServletTest {

	@Mock
	private transient ProductMenuService productMenuService;
	@Mock
	JSONWriter writer;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private GetResourcesByTagServlet getResourcesByTagServlet;

	@Test
	public void testGet() throws Exception {
		getResourcesByTagServlet = new GetResourcesByTagServlet();
		
		getResourcesByTagServlet.setProductMenuService(productMenuService);
		writer = new JSONWriter(context.response().getWriter());
		getResourcesByTagServlet.doGet(context.request(), context.response());
		Assert.assertNotNull(context.response().getOutput());
	}

}