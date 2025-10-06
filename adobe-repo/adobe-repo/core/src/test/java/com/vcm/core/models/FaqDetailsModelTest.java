package com.vcm.core.models;

import javax.annotation.Nullable;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vcm.core.models.mock.contentfragment.ContentFragmentMockAdapter;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class FaqDetailsModelTest {

	private ResourceResolver resourceResolver;
	private FaqDetailsModel testFaqDetailsModel;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private JSONArray expectedFaqJson;

	@BeforeEach
	public void setUp() throws Exception {
		testFaqDetailsModel = new FaqDetailsModel();
		context.load().json("/faqTag.json", "/content/cq:tags/vcm");
		resourceResolver = context.resourceResolver();
		testFaqDetailsModel.setResourceResolver(resourceResolver);
		context.registerAdapter(Resource.class, com.adobe.cq.dam.cfm.ContentFragment.class, ADAPTER);
	}

	@Test
	public void testfaqDetails() {
		context.load().json("/faqdetails.json", "/content/dam/vcm/faqdetails");
		context.load().json("/faqresource.json", "/content/vcm/en/faqdetails");
		context.load().json("/contentFragment.json", "/conf/vcm/settings/dam/cfm/models/faq");
		testFaqDetailsModel.setTagDetailsList(context.currentResource("/content/vcm/en/faqdetails"));
		testFaqDetailsModel.setParentPath("/content/dam/vcm/faqdetails");
		testFaqDetailsModel.setSeeAllFaq("All FAQs");

		testFaqDetailsModel.init();
		Assert.assertNotNull(testFaqDetailsModel.getFaqDetailsContentListJson());
		Assert.assertEquals("/content/dam/vcm/faqdetails", testFaqDetailsModel.getParentPath());
		Assert.assertEquals("/content/vcm/en/faqdetails", testFaqDetailsModel.getTagDetailsList().getPath());
		Assert.assertNotNull(testFaqDetailsModel.getSeeAllFaq());
	}

	@Test
	public void testNoElementsContFrag() {
		context.load().json("/faqdetails.json", "/content/dam/vcm/faqdetails");
		context.load().json("/faqresource.json", "/content/vcm/en/faqdetails");
		context.load().json("/contentFragment1.json", "/conf/vcm/settings/dam/cfm/models/faq");
		testFaqDetailsModel.setTagDetailsList(context.currentResource("/content/vcm/en/faqdetails"));
		testFaqDetailsModel.setParentPath("/content/dam/vcm/faqdetails");
		testFaqDetailsModel.init();
		Assert.assertNotNull(testFaqDetailsModel.getFaqDetailsContentListJson());
	}

	@Test
	public void testNullTagDetailsList() {
		context.load().json("/faqdetails.json", "/content/dam/vcm/faqdetails");
		context.load().json("/contentFragment.json", "/conf/vcm/settings/dam/cfm/models/faq");
		testFaqDetailsModel.setParentPath("/content/dam/vcm/faqdetails");
		testFaqDetailsModel.init();
		Assert.assertNull(testFaqDetailsModel.getFaqDetailsContentListJson());
	}

	@Test
	public void testNoTaggedResourceDetailsList() {
		context.load().json("/faqresource.json", "/content/vcm/en/faqdetails");
		context.load().json("/faqNoList.json", "/content/dam/vcm/faqdetails");
		context.load().json("/contentFragment.json", "/conf/vcm/settings/dam/cfm/models/faq");
		testFaqDetailsModel.setParentPath("/content/dam/vcm/faqdetails");
		testFaqDetailsModel.init();
		Assert.assertNull(testFaqDetailsModel.getFaqDetailsContentListJson());
	}

	private static final java.util.function.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment> CONTENT_FRAGMENT_ADAPTER = new ContentFragmentMockAdapter();

	public static final com.google.common.base.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment> ADAPTER = new com.google.common.base.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment>() {
		@Nullable
		@Override
		public com.adobe.cq.dam.cfm.ContentFragment apply(@Nullable Resource resource) {
			return CONTENT_FRAGMENT_ADAPTER.apply(resource);
		}
	};

}