package com.vcm.core.servlets;

import com.vcm.core.service.UserFormService;
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
class GetUserFormsServletTest {

	@Mock
	UserFormService userFormService;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private GetUserFormsServlet getUserFormsServlet;

	@BeforeEach
	public void setup() throws Exception {
		getUserFormsServlet = new GetUserFormsServlet();
		getUserFormsServlet.setUserFormService(userFormService);
		Mockito.when(userFormService.userFormsJson(Mockito.any())).thenReturn("FundList");
	}

	@Test
	void testGetMethodSearchContext() throws Exception {
		MockSlingHttpServletRequest mockReq = context.request();
		MockSlingHttpServletResponse mockRes = context.response();
		getUserFormsServlet.doGet(mockReq, mockRes);
		Assert.assertEquals(mockRes.getStatus(), 200);
		Assert.assertNotNull(mockRes.getContentLength());

	}


}