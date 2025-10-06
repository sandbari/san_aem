package com.vcm.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.xss.ProtectionContext;
import org.apache.sling.xss.XSSFilter;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.NameConstants;
import com.vcm.core.service.QueryBuilderService;
import com.vcm.core.service.SuggestionService;
import com.vcm.core.utils.XSSRequestWrapper;

/**
 * This Servlet is used to search JCR
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Search Servlet",
	"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.resourceTypes=" + "sling/servlet/default",
	"sling.servlet.selectors=" + "sitesearch", "sling.servlet.selectors=" + "suggestions",
	"sling.servlet.extensions=" + "json" })
public class SearchServlet extends SlingAllMethodsServlet {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5770335628643743361L;
    /** The Constant LOGGER. */
    private static final Logger LOG = LoggerFactory.getLogger(SearchServlet.class);

    @Reference
    private transient QueryBuilderService queryBuilderService;
  
    @Reference
    private transient SuggestionService suggestionService;
    
    @Reference
    private transient XSSFilter xssFilter;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
	    throws ServletException, IOException {

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.addHeader("X-Frame-Options", "DENY");


	String[] selectors = request.getRequestPathInfo().getSelectors();
	
	ResourceResolver resourceResolver = request.getResourceResolver();
	
	XSSRequestWrapper filteredRequest = new XSSRequestWrapper((HttpServletRequest) request);
	
	String searchText = this.checkReqPattern(filteredRequest.getParameter("searchtext"));
	
	LOG.debug(">>>>>inside doGet method: searchText={}", searchText);
	
	JSONObject suggestedResults = new JSONObject();
	
	String searchResultJson = "";
	
	if (Objects.nonNull(selectors) && selectors.length > 0 && Objects.nonNull(searchText)
		&& !searchText.equals(StringUtils.EMPTY)) {
	
	    if (Objects.equals(selectors[0], "sitesearch")) {
		
		String tabTypes = filteredRequest.getParameter("tabTypes");
		
		String searchIgnoreTag = filteredRequest.getParameter("searchIgnoreTag");
		
		String searchLimit = filteredRequest.getParameter("searchLimit");
		
		LOG.debug(">>>>>inside doGet method: tabTypes={}", tabTypes);
		
		LOG.debug(">>>>>inside doGet method: searchIgnoreTag={}", searchIgnoreTag);
		
		LOG.debug(">>>>>inside doGet method: searchLimit={}", searchLimit);
		
		searchResultJson = queryBuilderService.getResults(request, searchText, tabTypes,
			searchIgnoreTag, searchLimit);

	    }
	    if (Objects.equals(selectors[0], "suggestions")) {
		
		List<String> suggestionsList = new ArrayList<String>();
		
		try {
		
		    String limitString = StringUtils.defaultIfEmpty(filteredRequest.getParameter("limit"), "5");
		    
		    int limit = Integer.parseInt(limitString);
		    
		    suggestionsList = suggestionService.suggest(resourceResolver, NameConstants.NT_PAGE, searchText,
			    limit);
		    
		    suggestedResults.put("suggestion", suggestionsList);
		    
		    searchResultJson = suggestedResults.toString();
		
		} catch (RepositoryException | JSONException e) {
		
		    LOG.error("Could not collect suggestions for search term [ {} ]", searchText);
		}
	    }
	}
	
	LOG.debug(">>>>>inside doGet method: searchResults={}", searchResultJson);
	
	response.getWriter().write(xssFilter.filter(ProtectionContext.PLAIN_HTML_CONTENT, searchResultJson));
	
	response.flushBuffer();
    }

    /**
     * @param queryBuilderService the queryBuilderService to set
     */
    public void setQueryBuilderService(QueryBuilderService queryBuilderService) {
	
	this.queryBuilderService = queryBuilderService;
    }

    /**
     * @param suggestionService the suggestionService to set
     */
    public void setSuggestionService(SuggestionService suggestionService) {
	
	this.suggestionService = suggestionService;
    }

    /**
     * @param xssFilter the xssFilter to set
     */
    public void setXssFilter(XSSFilter xssFilter) {
	
	this.xssFilter = xssFilter;
    }

    private String checkReqPattern(String value) {
	
	if (Objects.nonNull(value) && !value.equals(StringUtils.EMPTY)) {
	    // AlphaNumeric
	
	    Pattern alphaNumbericPattern = Pattern.compile("^[a-zA-Z0-9&-_ ]+$");
	    
	    Matcher matcher = alphaNumbericPattern.matcher(value);
	    
	    if (matcher.matches()) {
		
		return value;
	    
	    } else {
		
		return StringUtils.EMPTY;
	    }

	} else {
	
	    return StringUtils.EMPTY;
	}
    }
}
