package com.vcm.core.models;

import java.util.ArrayList;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import com.day.cq.tagging.Tag;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
class SearchResultsModelTest {
	
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	private SearchResultsModel searchResultsModel;

	private String[] tags;

	@Mock
	private ResourceResolver resourceResolver;

	@Mock
	Tag tag;

	private ArrayList<Tag> tagList;

	@BeforeEach
	public void setup() throws Exception {
		tags = new String[] { "tag1", "tag2" };
		tagList = new ArrayList<Tag>();
		tagList.add(tag);
		searchResultsModel = new SearchResultsModel();
		searchResultsModel.setClearSearchLabel("clearSearchLabel");
		searchResultsModel.setSearchLimit("10");
		searchResultsModel.setHelptext("helptext");
		searchResultsModel.setResourceResolver(resourceResolver);
		searchResultsModel.setResultsForText("resultsForText");
		searchResultsModel.setSearchIgnoreTag("/searchPath");
		searchResultsModel.setSeeAllText("seeAllText");
		searchResultsModel.setTabList(tagList);
		searchResultsModel.setTabTags(tags);
		searchResultsModel.setSeeAllLinkDescription("See All desc");

		resourceResolver = context.resourceResolver();
		searchResultsModel.setResourceResolver(resourceResolver);
	}

	@Test
	void testSearchResultDetail() throws Exception {

		Assert.assertEquals("clearSearchLabel", searchResultsModel.getClearSearchLabel());
		Assert.assertEquals("10", searchResultsModel.getSearchLimit());
		Assert.assertEquals("helptext", searchResultsModel.getHelptext());
		Assert.assertEquals("resultsForText", searchResultsModel.getResultsForText());
		Assert.assertEquals("/searchPath", searchResultsModel.getSearchIgnoreTag());
		Assert.assertEquals("seeAllText", searchResultsModel.getSeeAllText());
		Assert.assertNotNull(searchResultsModel.getTabList());
		Assert.assertNotNull(searchResultsModel.getTabTags());
		Assert.assertNotNull(searchResultsModel.getSeeAllLinkDescription());
		
		searchResultsModel.init();
	}

}