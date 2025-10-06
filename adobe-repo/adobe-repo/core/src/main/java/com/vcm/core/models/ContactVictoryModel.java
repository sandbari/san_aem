package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = { SlingHttpServletRequest.class,Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactVictoryModel {
	
	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	@Via("resource")
	String bgImageDesktopShow;
	
	@Inject
	@Via("resource")
	String bgImageMobileShow;
	
	@Inject
	@Via("resource")
	String altText;
	
	@Inject
	@Via("resource")
	String heading;
	
	@Inject
	@Via("resource")
	String description;
		
	@Inject
	@Via("resource")
	String linkText;
	
	@Inject
	@Via("resource")
	String linkUrl;

	@Inject
	@Via("resource")
	String contactLinkUrlFinancial;

	@Inject
	@Via("resource")
	String contactLinkUrlII;
	
	@Inject
	@Via("resource")
    String tabSelect;
	
	@SlingObject
	SlingHttpServletRequest slingRequest;
		
	private List<String> allowedAudience = new ArrayList<String>();
	
    @Inject
	AudienceSelectorService audienceSelectorService;
	
	public String getBgImageDesktopShow() {
		return bgImageDesktopShow;
	}

	public void setBgImageDesktopShow(String bgImageDesktopShow) {
		this.bgImageDesktopShow = bgImageDesktopShow;
	}

	public String getBgImageMobileShow() {
		return bgImageMobileShow;
	}

	public void setBgImageMobileShow(String bgImageMobileShow) {
		this.bgImageMobileShow = bgImageMobileShow;
	}
	
	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
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

	public String getContactLinkUrlFinancial() { return UtilityService.identifyLinkUrl(contactLinkUrlFinancial, resourceResolver); }

	public void setContactLinkUrlFinancial(String contactLinkUrlFinancial) { this.contactLinkUrlFinancial = contactLinkUrlFinancial; }

	public String getContactLinkUrlII() { return UtilityService.identifyLinkUrl(contactLinkUrlII, resourceResolver); }

	public void setContactLinkUrlII(String contactLinkUrlII) { this.contactLinkUrlII = contactLinkUrlII; }

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}
	
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
    
    public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}

    @PostConstruct
    protected void init() {
        allowedAudience= audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
    }

}
