package com.vcm.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

class SearchResultDetailTest {

	private SearchResultDetail searchResultDetail;

	private String[] tags;

	@BeforeEach
	public void setup() throws Exception {
		tags = new String[] { "tag1", "tag2" };
		searchResultDetail = new SearchResultDetail();
		searchResultDetail.setDescription("description");
		searchResultDetail.setPagePath("pagePath");
		searchResultDetail.setPageTitle("pageTitle");
		searchResultDetail.setTags(tags);
	}

	@Test
	void testSearchResultDetail() throws Exception {
		Assert.assertEquals("description", searchResultDetail.getDescription());
		Assert.assertEquals("pagePath", searchResultDetail.getPagePath());
		Assert.assertEquals("pageTitle", searchResultDetail.getPageTitle());
		Assert.assertEquals(tags[0], searchResultDetail.getTags()[0]);
	}

}