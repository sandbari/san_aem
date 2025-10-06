package com.vcm.core.models;

import java.util.ArrayList;
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
import com.vcm.core.utils.UtilityService;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MoreTopicsModel {

    private static final Logger log = LoggerFactory.getLogger(MoreTopicsModel.class);

	@SlingObject
	ResourceResolver resourceResolver;

	@Inject
	@Via("resource")
	private String heading;
	
	@Inject
	@Via("resource")
	private String blogListPath;

	@Inject
	@Via("resource")
	private String[] topics;

	List<String> topicList = new ArrayList<>();

	@PostConstruct
	protected void init() {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		setTopicList(tagManager);
	}

	public void setTopicList(TagManager tagManager) {

		if (null != topics) {
			for (String string : topics) {
				Tag topic = tagManager.resolve(string);
				topicList.add(topic.getTitle());
			}
		}
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getBlogListPath() {
		return UtilityService.identifyLinkUrl(blogListPath, resourceResolver);
	}

	public void setBlogListPath(String blogListPath) {
		this.blogListPath = blogListPath;
	}

	public String[] getTopics() {
		return topics.clone();
	}

	public void setTopics(String[] topics) {
		this.topics = topics.clone();
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<String> getTopicList() {
		return topicList;
	}

	
}