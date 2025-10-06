package com.vcm.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import javax.annotation.PostConstruct;
import java.util.Objects;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Timeline {
	@Inject
	private String heading;
	@Inject
	private String description;
	@Inject
	private String linkUrl;
	@Inject
    private String linkDescription;
	@Inject
	private String linkText;
	@Inject
	private String tabSelect;
	@SlingObject
	private ResourceResolver resourceResolver;
	@Inject
    List<TimelineYearData> yearData;
	
	String linkAriaLabel;

	@PostConstruct
    protected void init() {
        if(Objects.nonNull(linkDescription)){
            linkAriaLabel = linkDescription;
        } else if (Objects.nonNull(linkUrl) && Objects.nonNull(linkText)) {
            linkAriaLabel = UtilityService.getLinkDescription(linkUrl, linkText, resourceResolver);
        }
    }

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
	public String getLinkUrl() {
		return UtilityService.identifyLinkUrl(linkUrl, resourceResolver);
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getLinkDescription() {
		return linkDescription;
	}
	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}
    public String getLinkAriaLabel() {
		return linkAriaLabel;
	}
	public void setLinkAriaLabel(String linkAriaLabel) {
		this.linkAriaLabel = linkAriaLabel;
	}
	public String getLinkText() {
		return linkText;
	}
	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}
	public String getTabSelect() {
		return tabSelect;
	}
	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}
	public List<TimelineYearData> getYearData() {
		return yearData;
	}
	public void setYearData(List<TimelineYearData> yearData) {
		this.yearData = yearData;
	}
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
}
