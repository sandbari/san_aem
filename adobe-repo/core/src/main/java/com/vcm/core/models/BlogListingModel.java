package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BlogListingModel {

        private static final Logger log = LoggerFactory.getLogger(BlogListingModel.class);

	@SlingObject
	ResourceResolver resourceResolver;

	@Inject
	@Via("resource")
	private String rootPath;

	@Inject
	@Via("resource")
	private String itemsOnLoadMore;

	@Inject
	@Via("resource")
	private String[] topicFilter;

	@Inject
	@Via("resource")
	private String searchLabel;

	@Inject
	@Via("resource")
	private String searchBoxText;

	@Inject
	@Via("resource")
	private Boolean hideSearchBox;

	@Inject
	@Via("resource")
	private String topicFilterLabel;

	@Inject
	@Via("resource")
	private Boolean hideTopicFilter;
	
	@Inject
	@Via("resource")
	private String noResultFound;
	
	public String[] getTopicFilter() {
		return topicFilter.clone();
	}

	public String getSearchLabel() {
		return searchLabel;
	}

	public String getSearchBoxText() {
		return searchBoxText;
	}

	public Boolean getHideSearchBox() {
		return hideSearchBox;
	}

	public void setHideSearchBox(Boolean hideSearchBox) {
		this.hideSearchBox = hideSearchBox;
	}

	public Boolean getHideTopicFilter() {
		return hideTopicFilter;
	}

	public void setHideTopicFilter(Boolean hideTopicFilter) {
		this.hideTopicFilter = hideTopicFilter;
	}

	public String getTopicFilterLabel() {
		return topicFilterLabel;
	}

	private List<String> topicList = new ArrayList<>();

	public String getRootPath() {
		return rootPath;
	}

	public String getItemsOnLoadMore() {
		return itemsOnLoadMore;
	}
	
	public String getNoResultFound() {
		return noResultFound;
	}

	public List<String> getTopicList() {
		List<String> copyTopicList = topicList;
		Collections.copy(copyTopicList, topicList);
		return copyTopicList;
	}

	@PostConstruct
	protected void init() {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		setTopicList(tagManager);
	}

	public void setTopicList(TagManager tagManager) {
		if (null != topicFilter) {
			for (String string : topicFilter) {
				Tag topic = tagManager.resolve(string);
				topicList.add(topic.getTitle());
			}
		}
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public void setItemsOnLoadMore(String itemsOnLoadMore) {
		this.itemsOnLoadMore = itemsOnLoadMore;
	}

	public void setTopicFilter(String[] topicFilter) {
		this.topicFilter = topicFilter.clone();
	}

	public void setSearchLabel(String searchLabel) {
		this.searchLabel = searchLabel;
	}

	public void setSearchBoxText(String searchBoxText) {
		this.searchBoxText = searchBoxText;
	}

	public void setTopicFilterLabel(String topicFilterLabel) {
		this.topicFilterLabel = topicFilterLabel;
	}

	public void setNoResultFound(String noResultFound) {
		this.noResultFound = noResultFound;
	}

}