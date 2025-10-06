package com.vcm.core.servlets;

import com.vcm.core.service.PostloginBannerService;
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
class GetTotalActiveBannerServletTest {

	@Mock
	PostloginBannerService postloginBannerService;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private GetTotalActiveBannerServlet getTotalActiveBanner;

	@BeforeEach
	public void setup() throws Exception {
		getTotalActiveBanner = new GetTotalActiveBannerServlet();
		getTotalActiveBanner.setPostloginBannerService(postloginBannerService);
		Mockito.when(postloginBannerService.getActivePostloginBanner(Mockito.any())).thenReturn("true");
	}

	@Test
	void testGetMethodSearchContext() throws Exception {
		MockSlingHttpServletRequest mockReq = context.request();
		MockSlingHttpServletResponse mockRes = context.response();
		getTotalActiveBanner.doGet(mockReq, mockRes);
		Assert.assertEquals(mockRes.getStatus(), 200);
		Assert.assertNotNull(mockRes.getContentLength());

	}


}