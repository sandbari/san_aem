package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialSiteLinksModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialSiteLinksModel.class);

	@Inject
	private String heading;

	@Inject
	private Resource socialLinks;

	@SlingObject
	private ResourceResolver resourceResolver;

	@Self
	private List<SocialLinkIcons> updSocialShare = new ArrayList<SocialLinkIcons>();

	private SocialLinkIcons tempSocialShareIcons;

	@PostConstruct
	protected void init() {

		LOGGER.debug("inside init function of SocialShare");

		ValueMap socialLinkInfoValMap;

		LOGGER.debug("before for loop");

		for (Iterator<Resource> iter = socialLinks.listChildren(); iter.hasNext();) {

			socialLinkInfoValMap = iter.next().getValueMap();

			tempSocialShareIcons = new SocialLinkIcons();

			tempSocialShareIcons.setSocialIcons(socialLinkInfoValMap.get("socialIcons", String.class));
			tempSocialShareIcons.setAltText(socialLinkInfoValMap.get("altText", String.class));
			tempSocialShareIcons.setLinkUrl(UtilityService
					.identifyLinkUrl(socialLinkInfoValMap.get("linkUrl", String.class), resourceResolver));
			tempSocialShareIcons.setTabSelect(socialLinkInfoValMap.get("tabSelect", String.class));

			updSocialShare.add(tempSocialShareIcons);

		}

		LOGGER.debug("after for loop");

	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Resource getSocialLinks() {
		return socialLinks;
	}

	public void setSocialLinks(Resource socialLinks) {
		this.socialLinks = socialLinks;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public List<SocialLinkIcons> getUpdSocialShare() {
		List<SocialLinkIcons> copySocialShare = updSocialShare;
		Collections.copy(copySocialShare, updSocialShare);
		return copySocialShare;
	}

	public void setUpdSocialShare(List<SocialLinkIcons> updSocialShare) {
		List<SocialLinkIcons> copySocialShare = new ArrayList<SocialLinkIcons>();
		copySocialShare.addAll(updSocialShare);
		this.updSocialShare = copySocialShare;
	}

}
