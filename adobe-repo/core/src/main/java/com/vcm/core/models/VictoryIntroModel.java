package com.vcm.core.models;

import javax.inject.Inject;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.models.IntroPointsBean;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VictoryIntroModel {
	
	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	String heading;
	
	@Inject
	String linkText;
	
	@Inject
	String linkUrl;
	
	@Inject
	String tabSelect;

	@Inject
    List<IntroPointsBean> introPoints;
	
	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
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

	public List<IntroPointsBean> getIntroPoints() {
		return introPoints;
	}

	public void setIntroPoints(List<IntroPointsBean> introPoints) {
		this.introPoints = introPoints;
	}
	


	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
}