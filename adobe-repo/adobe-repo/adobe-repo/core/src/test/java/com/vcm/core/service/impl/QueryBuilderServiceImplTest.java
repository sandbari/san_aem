package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.service.SearchConfigService;

import junit.framework.Assert;

@ExtendWith(MockitoExtension.class)
class QueryBuilderServiceImplTest {

	@Mock
	private QueryBuilder queryBuilder;
	@Mock
	private Query query;
	@Mock
	private SearchResult result;
	@Mock
	SearchConfigService searchConfigService;
	@Mock
	private Hit hit;
	@Mock
	private ResourceResolver resourceResolver;
	
	@Mock
	SlingHttpServletRequest request;
	
	@InjectMocks
	private QueryBuilderServiceImpl queryBuilderServiceImpl;
	
	@Mock
	AudienceSelectorService audienceSelectorService;

	private Map<String, Object> hitProperties;
	private List<Hit> hits;

	@BeforeEach
	public void setUp() throws RepositoryException {
		hits = new ArrayList<>();
		List<String> userTypes = new ArrayList<>();
		userTypes.add("ussa_member");
		hits.add(hit);
		hitProperties = new HashMap<>();
		hitProperties.put("jcr:description", "Desc");
		hitProperties.put("cq:tags", "tag1");
		ValueMap valueMap = new ValueMapDecorator(hitProperties);
		queryBuilderServiceImpl.setQueryBuilder(queryBuilder);
		queryBuilderServiceImpl.setSearchConfigService(searchConfigService);
		queryBuilderServiceImpl.setAudienceSelectorService(audienceSelectorService);
		Mockito.when(searchConfigService.getSearchRootPath()).thenReturn("/content/vcm");
		Mockito.when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(query.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(hits);
		Mockito.when(hit.getProperties()).thenReturn(valueMap);
		Mockito.when(hit.getTitle()).thenReturn("Title");
		Mockito.when(hit.getPath()).thenReturn("/content/vcm");
		Mockito.when(audienceSelectorService.getUserTypeFromRequestDomain(request)).thenReturn("ussa_member");
		Mockito.when(audienceSelectorService.getAllowedAudienceListFromValueMap(Mockito.any())).thenReturn(userTypes);
	}

	@Test
	public void testSuggest() throws RepositoryException {
		String tabs = "vcm:products,vcm:advicetools,vcm:advice-tools,vcm:general";
		String resultSet = queryBuilderServiceImpl.getResults(request, "vcm", tabs, "ignoreSearch", "-1");
		Assert.assertNotNull(resultSet);
	}
}
