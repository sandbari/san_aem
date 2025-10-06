package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.QueryBuilder;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.SearchConfigService;
import com.vcm.core.service.SuggestionService;

@Component(service = SuggestionService.class, immediate = true)
public class SuggestionsServiceImpl implements SuggestionService {
	private static final Logger LOG = LoggerFactory.getLogger(SuggestionsServiceImpl.class);

	@Reference
	private QueryBuilder queryBuilder;

	@Reference
	private SearchConfigService searchConfigService;

	@Override
	public List<String> suggest(ResourceResolver resourceResolver, String nodeType, String term, int limit)
			throws RepositoryException {
		final Set<String> suggestionsKeys = new HashSet<String>();
		final List<String> suggestions = new ArrayList<>();

		if (StringUtils.isBlank(term)) {
			return suggestions;
		}

		nodeType = StringUtils.defaultIfEmpty(nodeType, JcrConstants.NT_HIERARCHYNODE);
		String path = StringUtils.defaultIfEmpty(searchConfigService.getSearchRootPath(), Constant.VCM_CONTENT_PATH);

		final String statement = String.format(
				"SELECT [rep:suggest()] FROM [%s] WHERE ISDESCENDANTNODE('%s') AND SUGGEST('%s')", escape(nodeType),
				escape(path), escape(term));
		LOG.debug("SELECT statement is={}",statement);
		final QueryManager queryManager = resourceResolver.adaptTo(Session.class).getWorkspace().getQueryManager();
		final QueryResult result = queryManager.createQuery(statement, javax.jcr.query.Query.JCR_SQL2).execute();
		final RowIterator rows = result.getRows();
		LOG.debug("Total Results is={}",rows.getSize());
		int count = 0;
		while (rows.hasNext()) {
			final Row row = rows.nextRow();

			String suggestion = row.getValue("rep:suggest()").getString();
			String key = getUniqueSuggestionKey(suggestion);

			if (suggestionsKeys.contains(key)) {
				continue;
			}

			suggestionsKeys.add(key);
			suggestions.add(StringUtils.lowerCase(suggestion));
			if (limit > 0 && ++count >= limit) {
				break;
			}
		}

		return suggestions;
	}

	private String escape(String str) {
		return StringUtils.stripToEmpty(str).replaceAll("'", "''");
	}

	/**
	 * This create a key to help normalize and de-dupe results. - turns to lowercase
	 * - turns all ' ' to - - reduces all multiple - segments into a single - (ex.
	 * --- becomes -) *
	 *
	 * @param suggestion the suggestion to create a unique key for.
	 * @return the suggestions key.
	 */
	private String getUniqueSuggestionKey(String suggestion) {
		suggestion = StringUtils.lowerCase(suggestion);
		suggestion = StringUtils.trim(suggestion);
		suggestion = suggestion.replaceAll("/[^A-Za-z0-9 ]/", "-");
		suggestion = suggestion.replaceAll("-+", "-");
		return suggestion;
	}

	/**
	 * @param searchConfigService the searchConfigService to set
	 */
	public void setSearchConfigService(SearchConfigService searchConfigService) {
		this.searchConfigService = searchConfigService;
	}
}
