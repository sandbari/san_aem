package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchResultsModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchOverlayModel.class);

	@Inject
	private String resultsForText;

	@Inject
	private String[] tabTags;

	@Inject
	private String searchIgnoreTag;
	
	@Inject
	private String searchLimit;
	
	@Inject
	private String helptext;

	@Inject
	private String SeeAllText;
	
	@Inject
	private String seeAllLinkDescription;

	@Inject
	private String clearSearchLabel;

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

	private List<Tag> tabList;

	/**
	 * The init method
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Init Model SearchOverlayModel");
		tabList = new ArrayList<>();
		final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		LOGGER.debug("Init Model SearchOverlayModel tagManager" + tagManager);
		if(tabTags != null && tabTags.length > 0) {
			for (String tag : tabTags) {
				tabList.add(tagManager.resolve(tag));
			}
		}
		LOGGER.debug("END Model SearchOverlayModel");
	}

	/**
	 * @return the resultsForText
	 */
	public String getResultsForText() {
		return resultsForText;
	}

	/**
	 * @return the tabTags
	 */
	public String[] getTabTags() {
		return tabTags.clone();
	}

	/**
	 * @return the helptext
	 */
	public String getHelptext() {
		return helptext;
	}

	/**
	 * @return the seeAllText
	 */
	public String getSeeAllText() {
		return SeeAllText;
	}

	/**
	 * @return the clearSearchLabel
	 */
	public String getClearSearchLabel() {
		return clearSearchLabel;
	}

	/**
	 * @return the tabList
	 */
	public List<Tag> getTabList() {
		List<Tag> copyListBean = tabList;
		Collections.copy(copyListBean, tabList);
		return copyListBean;
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	/**
	 * @param resultsForText the resultsForText to set
	 */
	public void setResultsForText(String resultsForText) {
		this.resultsForText = resultsForText;
	}

	/**
	 * @param tabTags the tabTags to set
	 */
	public void setTabTags(String[] tabTags) {
		String[] copyStringArr = tabTags.clone();
		this.tabTags = copyStringArr;
	}

	/**
	 * @param helptext the helptext to set
	 */
	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}

	/**
	 * @param seeAllText the seeAllText to set
	 */
	public void setSeeAllText(String seeAllText) {
		SeeAllText = seeAllText;
	}

	/**
	 * @param clearSearchLabel the clearSearchLabel to set
	 */
	public void setClearSearchLabel(String clearSearchLabel) {
		this.clearSearchLabel = clearSearchLabel;
	}

	/**
	 * @param tabList the tabList to set
	 */
	public void setTabList(List<Tag> tabList) {
		List<Tag> copyListBean = new ArrayList<Tag>();
		copyListBean.addAll(tabList);
		this.tabList = copyListBean;
	}
	

	/**
	 * @return the searchLimit
	 */
	public String getSearchLimit() {
		return searchLimit;
	}
	

	/**
	 * @param searchLimit the searchLimit to set
	 */
	public void setSearchLimit(String searchLimit) {
		this.searchLimit = searchLimit;
	}

	/**
	 * @return the searchIgnoreTag
	 */
	public String getSearchIgnoreTag() {
		return searchIgnoreTag;
	}

	/**
	 * @param searchIgnoreTag the searchIgnoreTag to set
	 */
	public void setSearchIgnoreTag(String searchIgnoreTag) {
		this.searchIgnoreTag = searchIgnoreTag;
	}

	public String getSeeAllLinkDescription() {
		return seeAllLinkDescription;
	}

	public void setSeeAllLinkDescription(String seeAllLinkDescription) {
		this.seeAllLinkDescription = seeAllLinkDescription;
	}

}