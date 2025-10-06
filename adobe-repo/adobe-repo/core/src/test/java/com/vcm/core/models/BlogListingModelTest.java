package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class BlogListingModelTest {

	private BlogListingModel blogListingModel;
	ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		blogListingModel = new BlogListingModel();
		context.load().json("/blogListResource.json", "/content/vcm/us/en/blog");
		context.load().json("/blogTags.json", "/content/cq:tags/vcm");
		blogListingModel.setResourceResolver(context.resourceResolver());
	}

	@Test
	void testInit() throws Exception {
		String[] topicFilter = {"vcm:blogs/investing", "vcm:blogs/etfs", "vcm:blogs/retirement", "vcm:blogs/practice-management"};
		blogListingModel.setTopicFilter(topicFilter);
		blogListingModel.setHideSearchBox(true);
		blogListingModel.setRootPath("/content/vcm/us/en/blog");
		blogListingModel.setItemsOnLoadMore("9");
		blogListingModel.setSearchBoxText("Search");
		blogListingModel.setSearchLabel("Search");
		blogListingModel.setTopicFilterLabel("filter by topic");
		blogListingModel.setHideTopicFilter(false);
		blogListingModel.setNoResultFound("No Results Found");
		blogListingModel.init();
		assertNotNull(blogListingModel.getTopicFilter());
		assertNotNull(blogListingModel.getHideSearchBox());
		assertNotNull(blogListingModel.getRootPath());
		assertNotNull(blogListingModel.getItemsOnLoadMore());
		assertNotNull(blogListingModel.getSearchBoxText());
		assertNotNull(blogListingModel.getSearchLabel());
		assertNotNull(blogListingModel.getTopicFilterLabel());
		assertNotNull(blogListingModel.getHideTopicFilter());
		assertNotNull(blogListingModel.getNoResultFound());
		assertEquals("Investing", blogListingModel.getTopicList().get(0));
	}
	@Test
	void testInitFilterNull() throws Exception {
		blogListingModel.init();
		assertEquals(0,blogListingModel.getTopicList().size());
	}

}