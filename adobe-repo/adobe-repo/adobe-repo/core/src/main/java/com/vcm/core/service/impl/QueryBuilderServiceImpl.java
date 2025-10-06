package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.TagConstants;
import com.day.cq.wcm.api.NameConstants;
import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.models.SearchResultDetail;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.QueryBuilderService;
import com.vcm.core.service.SearchConfigService;
import com.vcm.core.utils.UtilityService;

@Component(service = QueryBuilderService.class, immediate = true)
public class QueryBuilderServiceImpl implements QueryBuilderService {

    private static final Logger LOG = LoggerFactory.getLogger(QueryBuilderServiceImpl.class);

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ConstantsConfigService contstantsConfigService;

    @Reference
    private SearchConfigService searchConfigService;

    @Reference
    private AudienceSelectorService audienceSelectorService;

    private static final String ALL = "ALL";

    @Override
    public String getResults(SlingHttpServletRequest request, String searchText, String tabTypes,
	    String searchIgnoreTag, String searchLimit) {

	String resultJson = StringUtils.EMPTY;

	String searchRootPathLocal = Constant.VCM_CONTENT_PATH;

	String searchLimitLocal = "-1";

	ResourceResolver resourceResolver = request.getResourceResolver();

	if (Objects.nonNull(searchText) && !searchText.equals(StringUtils.EMPTY)) {

	    String searchRootPathTemp = searchConfigService.getSearchRootPath();

	    if (Objects.nonNull(searchRootPathTemp) && !searchRootPathTemp.equals("")) {

		searchRootPathLocal = searchRootPathTemp;
	    }
	   
	    if (Objects.nonNull(searchLimit) && !searchLimit.equals("")) {

		searchLimitLocal = searchLimit;
	    }

	    LOG.debug("inside getResults method:fulltext={}", searchText);

	    final Map<String, String> map = new HashMap<String, String>();

	    map.put("path", searchRootPathLocal);

	    map.put("type", NameConstants.NT_PAGE);

	    map.put("fulltext", searchText);

	    map.put("p.limit", searchLimitLocal);

	    // Order By

	    map.put("orderby.case", "ignore");

	    LOG.debug("SearchResult Predicate is ={}", map.toString());

	    SearchResult result = null;

	    Query query = queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));

	    result = query.getResult();

	    LOG.debug("SearchResult total={}", result.getTotalMatches());

	    resultJson = getCategorizedResultJson(result, tabTypes, searchIgnoreTag, request);

	}

	return resultJson;
    }

    private String getCategorizedResultJson(SearchResult result, String tabTypes, String searchIgnoreTag,
	    SlingHttpServletRequest request) {

	String jsonString = StringUtils.EMPTY;

	String[] tabTypesArr;

	String lastTabItem = "";

	Map<String, List<SearchResultDetail>> categorizedResults = new HashMap<>();

	if (tabTypes != null && !tabTypes.equals(StringUtils.EMPTY)) {

	    tabTypesArr = tabTypes.split(",");

	    for (String tabTypeItem : tabTypesArr) {

		categorizedResults.put(tabTypeItem, new ArrayList<>());

		lastTabItem = tabTypeItem;
	    }

	}

	try {

	    if (result != null) {

		for (final Hit hit : result.getHits()) {

		    this.loadSearchResultsMap(hit, categorizedResults, searchIgnoreTag, lastTabItem, request);
		}
	    }

	    Gson gson = new Gson();

	    jsonString = gson.toJson(categorizedResults);

	} catch (RepositoryException e) {

	    LOG.error("Error in query {}", e.getMessage());
	}

	return jsonString;
    }

    private void loadSearchResultsMap(Hit hit, Map<String, List<SearchResultDetail>> categorizedResults,
	    String searchIgnoreTag, String lastTabItem, SlingHttpServletRequest request) throws RepositoryException {

	ResourceResolver resourceResolver = request.getResourceResolver();

	ValueMap pagePropertiesMap = hit.getProperties();

	SearchResultDetail searchResultsDetail = new SearchResultDetail();

	searchResultsDetail.setPageTitle(hit.getTitle());

	searchResultsDetail.setDescription(pagePropertiesMap.get(Constant.META_DESCRIPTION, String.class));

	searchResultsDetail.setPagePath(UtilityService.identifyLinkUrl(hit.getPath(), resourceResolver));

	String[] tagsArray = pagePropertiesMap.get(TagConstants.PN_TAGS, new String[0]);

	LOG.debug("Ignore Search Tag is {}", searchIgnoreTag);

	if (this.isValidUserSpecificPage(pagePropertiesMap, request)) {

	    if (tagsArray != null && tagsArray.length > 0) {

		List<String> tagsArrayList = Arrays.asList(tagsArray);

		if (!tagsArrayList.contains(searchIgnoreTag)) {

		    searchResultsDetail.setTags(tagsArray);

		    for (String tag : tagsArray) {

			if (categorizedResults.containsKey(tag)) {

			    categorizedResults.get(tag).add(searchResultsDetail);

			} else {

			    categorizedResults.get(lastTabItem).add(searchResultsDetail);
			}
		    }
		}

	    } else {

		categorizedResults.get(lastTabItem).add(searchResultsDetail);
	    }

	}

    }

    /**
     * @param queryBuilder the queryBuilder to set
     */
    public void setQueryBuilder(QueryBuilder queryBuilder) {

	this.queryBuilder = queryBuilder;
    }

    /**
     * @param searchConfigService the searchConfigService to set
     */
    public void setSearchConfigService(SearchConfigService searchConfigService) {

	this.searchConfigService = searchConfigService;
    }

    private boolean isValidUserSpecificPage(ValueMap pagePropertiesMap, SlingHttpServletRequest request) {

	boolean validPageForUser = true;

	if (Objects.nonNull(pagePropertiesMap)) {

	    List<String> audienceList = audienceSelectorService.getAllowedAudienceListFromValueMap(pagePropertiesMap);

	    LOG.debug("Search audienceList is {}", audienceList);

	    String currentUserType = audienceSelectorService.getUserTypeFromRequestDomain(request);

	    LOG.debug("Search currentUserType is {}", currentUserType);

	    if (audienceList.contains(ALL)) {

		validPageForUser = true;
		
	    } else if (audienceList.contains(currentUserType)) {

		validPageForUser = true;
	    } else {

		validPageForUser = false;
	    }
	}

	return validPageForUser;
    }

    /**
     * @param audienceSelectorService the audienceSelectorService to set
     */
    public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
        this.audienceSelectorService = audienceSelectorService;
    }

}
