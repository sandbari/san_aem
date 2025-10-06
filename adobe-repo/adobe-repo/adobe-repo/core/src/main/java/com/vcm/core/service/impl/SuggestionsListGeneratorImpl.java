package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationStatus;
import com.day.cq.tagging.TagConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.service.SearchConfigService;
import com.vcm.core.service.SuggestionServiceListGenerator;

@Component(service = SuggestionServiceListGenerator.class, immediate = true)
public class SuggestionsListGeneratorImpl implements SuggestionServiceListGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(SuggestionsListGeneratorImpl.class);

    @Reference
    private SearchConfigService searchConfigService;

    @Reference
    private AudienceSelectorService audienceSelectorService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private String searchIgnoreTag = null;

    private static final String ALL = "ALL";

    private List<JsonObject> membersSuggestList = null;

    private List<JsonObject> financialSuggestList = null;

    private List<JsonObject> institutionalInvestorSuggestList = null;

    private Map<String, List<JsonObject>> suggestJson = null;

    public Map<String, List<JsonObject>> createSuggestionList() {

	searchIgnoreTag = "vcm:search-tabs/ignore-search";

	membersSuggestList = new ArrayList<JsonObject>();

	financialSuggestList = new ArrayList<JsonObject>();

	institutionalInvestorSuggestList = new ArrayList<JsonObject>();

	suggestJson = new HashMap<>();

	this.executeQuery(searchConfigService.getSearchRootPath());

	if(membersSuggestList.size() > 0 && financialSuggestList.size() > 0 && institutionalInvestorSuggestList.size() > 0){
	
	    suggestJson.put(audienceSelectorService.getMemberAudienceTypeKey(), membersSuggestList);

	
	    suggestJson.put(audienceSelectorService.getFinancialAudienceTypeKey(), financialSuggestList);

	
	    suggestJson.put(audienceSelectorService.getInstitutionalInvestorAudienceTypeKey(),
		institutionalInvestorSuggestList);
	}

	return suggestJson;

    }

    public String createQuery(String nodeType, String path) {

	String statement = null;

	if (Objects.nonNull(path) && Objects.nonNull(nodeType)) {

	    statement = String.format("SELECT * FROM [%s] AS page WHERE ISDESCENDANTNODE('%s')", escape(nodeType),
		    escape(path));

	    LOG.debug("SELECT statement is={}", statement);
	}

	return statement;

    }

    public QueryResult executeQuery(String path) {

	String statement = null;

	Map<String, Object> map = new HashMap<>();

	map.put(ResourceResolverFactory.SUBSERVICE, Constant.VCMSERVICEUSER);

	Session session = null;

	QueryResult result = null;

	if (Objects.nonNull(path) && StringUtils.isNotEmpty(path)) {

	    statement = this.createQuery(NameConstants.NT_PAGE, path);

	    if (Objects.nonNull(statement)) {

		if (resourceResolverFactory != null) {

		    try (final ResourceResolver resourceResolver = resourceResolverFactory
			    .getServiceResourceResolver(map)) {

			if (Objects.nonNull(resourceResolver)) {

			    session = resourceResolver.adaptTo(Session.class);

			    if (Objects.nonNull(session)) {

				QueryManager queryManager = session.getWorkspace().getQueryManager();

				if (Objects.nonNull(queryManager)) {

				    Query query = queryManager.createQuery(statement, Query.JCR_SQL2);

				    QueryResult results = query.execute();

				    this.processQueryResult(results, resourceResolver);

				}
			    }
			}

		    }

		    catch (LoginException | RepositoryException e) {

			LOG.error("Exception Occured while executing query {}", e);
		    }

		    finally {

			if (Objects.nonNull(session) && session.isLive()) {

			    session.logout();
			}
		    }
		}
	    }
	}

	return result;
    }

    public void processQueryResult(QueryResult result, ResourceResolver resourceResolver) {

	if (Objects.nonNull(result)) {

	    try {

		NodeIterator nodeIter = result.getNodes();

		while (nodeIter.hasNext()) {

		    Node node = nodeIter.nextNode();

		    if (Objects.nonNull(node)) {

			Resource pageResource = resourceResolver.getResource(node.getPath());

			LOG.debug("into suggestion result node path {}", pageResource.getPath());

			LOG.debug("into suggestion result node {}", pageResource);

			if (Objects.nonNull(pageResource)) {

			    Resource childResource = pageResource.getChild(JcrConstants.JCR_CONTENT);
			    
			    Boolean status = this.isPublished(pageResource);
			    
			    if (status && Objects.nonNull(childResource)) {

				checkIgnoredPages(childResource);
			    }

			}
		    }
		}

	    } catch (RepositoryException e) {

		LOG.error("Exception Occured while executing query {}", e);
	    }
	}
    }

    private String escape(String str) {
	return StringUtils.stripToEmpty(str).replaceAll("'", "''");
    }

    private void checkIgnoredPages(Resource pageResource) {

	if (Objects.nonNull(pageResource)) {

	    ValueMap valueMap = pageResource.getValueMap();

	    String[] tagsArray = valueMap.get(TagConstants.PN_TAGS, new String[0]);

	    LOG.debug("Ignore Search Tag is {}", searchIgnoreTag);

	    if (tagsArray != null && tagsArray.length > 0) {

		List<String> tagsArrayList = Arrays.asList(tagsArray);

		if (!tagsArrayList.contains(searchIgnoreTag)) {

		    this.addPagesToResult(pageResource);
		}

	    } else {

		this.addPagesToResult(pageResource);
	    }

	}

    }

    private void addPagesToResult(Resource pageResource) {

	List<String> audienceList = audienceSelectorService.getAllowedAudienceListFromResource(pageResource);

	if (Objects.nonNull(pageResource)) {

	    ValueMap pageValueMap = pageResource.getValueMap();

	    if (Objects.nonNull(pageValueMap)) {
		
		String title = pageValueMap.get(JcrConstants.JCR_TITLE, String.class);
		
		JsonObject resultItem = new JsonObject();
		
		resultItem.addProperty("value", title);
		
		for (String userType : audienceList) {

		    if (userType.equals(audienceSelectorService.getMemberAudienceTypeKey())) {

			membersSuggestList.add(resultItem);

		    }

		    if (userType.equals(audienceSelectorService.getFinancialAudienceTypeKey())) {

			financialSuggestList.add(resultItem);

		    }

		    if (userType.equals(audienceSelectorService.getInstitutionalInvestorAudienceTypeKey())) {

			institutionalInvestorSuggestList.add(resultItem);

		    }

		    if (userType.equals(ALL)) {

			membersSuggestList.add(resultItem);

			financialSuggestList.add(resultItem);

			institutionalInvestorSuggestList.add(resultItem);
		    }

		}
	    }
	}
    }

    public Boolean isPublished(Resource resource) {
	
	Boolean activated;
	
	ReplicationStatus status = null;
	
	activated = false;
	
	if (resource != null) {
	
	  Page page = resource.adaptTo(Page.class);
		
	  status = page.adaptTo(ReplicationStatus.class);
	  
	  LOG.debug("Inside activate check Page is {}",resource.getPath());
	    
	    if (status != null) {
		
		activated = status.isActivated();
		
		LOG.debug(" Inside activate check Page is {} and status is {}",resource.getPath(),activated);
	    }
	}
	
	return activated;
    
    }

}
