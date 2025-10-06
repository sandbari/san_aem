package com.vcm.core.service.impl;

import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.day.cq.wcm.api.NameConstants;

import io.wcm.testing.mock.aem.junit5.AemContext;
import junit.framework.Assert;

public class SuggestionsServiceImplTest {

	private SuggestionsServiceImpl suggestionsService = new SuggestionsServiceImpl();
	private ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private SearchConfigServiceImpl searchConfigService = null;

	@BeforeEach
	public void setUp() throws RepositoryException {
		searchConfigService = new SearchConfigServiceImpl();
		searchConfigService.setSearchRootPath("/content/vcm");
	}

	@Test
	public void testSuggest() throws RepositoryException {
		resourceResolver = context.resourceResolver();
		context.load().json("/rootPage.json", "/content/vcm");
		String term = "test";
		int limit = 4;
		suggestionsService.setSearchConfigService(searchConfigService);
		List<String> suggestedValues = suggestionsService.suggest(resourceResolver, NameConstants.NT_PAGE, term, limit);
		Assert.assertNotNull(suggestedValues);
	}
}
