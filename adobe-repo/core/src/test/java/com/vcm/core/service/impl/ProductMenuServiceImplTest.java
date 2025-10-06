package com.vcm.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.drew.lang.annotations.NotNull;
import com.vcm.core.common.MockJsonUtility;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class ProductMenuServiceImplTest {

	private ProductMenuServiceImpl productMenuService = new ProductMenuServiceImpl();
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@NotNull
	private ResourceResolver resourceResolver;

	private MockSlingHttpServletRequest request;

	private MockSlingHttpServletResponse response;

	private JSONObject expectedErrorJson;

	@BeforeEach
	public void setUp() throws JSONException {
		context.load().json("/productMenuCurrentResource.json",
				"/content/vcm/language-masters/en/jcr:content/root/responsivegrid/productmenu");
		context.load().json("/default-tag.json", "/content/cq:tags/vcm");
		context.load().json("/emptyListPage.json", "/content/vcm/en/vcm-content-page-template-test/empty-list");
		resourceResolver = context.resourceResolver();
		request = context.request();
		response = context.response();
		expectedErrorJson = new JSONObject(MockJsonUtility.getJsonData("expected-emptyList.json"));
	}

	/**
	 * Test method for
	 * {@link com.vcm.core.component.helper.ProductMenuHelper#writeJsonResponse(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.resource.Resource, org.json.JSONWriter)}.
	 * 
	 * @throws LoginException
	 * @throws RepositoryException
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test
	void testWriteJsonResponseWithEmptyList() throws JSONException, RepositoryException, LoginException, IOException {
		Resource resource = context.resourceResolver()
				.getResource("/content/vcm/en/vcm-content-page-template-test/empty-list");
		context.currentResource(resource);
		JSONWriter writer = new JSONWriter(response.getWriter());
		productMenuService.writeJsonResponse(request, writer);
		JSONObject actualErrorJson = new JSONObject(response.getOutputAsString());
		assertTrue(StringUtils.equalsAnyIgnoreCase(expectedErrorJson.toString(), actualErrorJson.toString()));
	}

	@Test
	void testWriteJsonResponse_withRootPageJson()
			throws JSONException, RepositoryException, LoginException, IOException {
		context.load().json("/productMenuRootPage.json", "/content/vcm/language-masters/en/products");
		Resource resource = context.resourceResolver()
				.getResource("/content/vcm/language-masters/en/jcr:content/root/responsivegrid/productmenu");
		context.currentResource(resource);
		JSONWriter writer = new JSONWriter(response.getWriter());
		productMenuService.writeJsonResponse(request, writer);
		JSONArray actualExpectedJson = new JSONArray(response.getOutputAsString());
		assertNotNull(actualExpectedJson);
	}

	@Test
	void testWriteJsonResponse_withNoPageListJson()
			throws JSONException, RepositoryException, LoginException, IOException {
		Resource resource = context.resourceResolver()
				.getResource("/content/vcm/language-masters/en/jcr:content/root/responsivegrid/productmenu");
		context.currentResource(resource);
		JSONWriter writer = new JSONWriter(response.getWriter());
		productMenuService.writeJsonResponse(request, writer);
		JSONArray actualExpectedJson = new JSONArray(response.getOutputAsString());
		assertNotNull(actualExpectedJson);
	}

}
