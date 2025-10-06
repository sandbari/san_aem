package com.vcm.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LearningResourceModel {

	@Inject
	private String heading;

	@Inject
	private String description;

	@Inject
	private String linkText;

	@Inject
	private String linkUrl;
	
	@Inject
	private String tabSelect;
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@Inject
    List<LearningResourceTileContent> tileContent;

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getLinkUrl() {
		return UtilityService.identifyLinkUrl(linkUrl, resourceResolver);
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	public List<LearningResourceTileContent> getTileContent() {
		return tileContent;
	}

	public void setTileContent(List<LearningResourceTileContent> tileContent) {
		this.tileContent = tileContent;
	}
	
	public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }
}