package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedFundsModel {

	@Inject
	private String heading;

	@Inject
	private String description;

	@Inject
	private String linkText;

	@Inject
	private String linkUrl;

	@Inject
	private String linkTarget;

	@Inject
	List<MultiFundContent> multiContent;

	@SlingObject
	private ResourceResolver resourceResolver;

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

	public String getLinkUrl() { return UtilityService.identifyLinkUrl(linkUrl, resourceResolver); }

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkTarget() {
		return linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public List<MultiFundContent> getMultiContent() {
		List<MultiFundContent> copyMultiContent = multiContent;
		Collections.copy(copyMultiContent, multiContent);
		return copyMultiContent;
	}

	public void setMultiContent(List<MultiFundContent> multiContent) {
		List<MultiFundContent> copyMultiContent = new ArrayList<MultiFundContent>();
		copyMultiContent.addAll(multiContent);
		this.multiContent = copyMultiContent;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
}
