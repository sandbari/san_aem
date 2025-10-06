package com.vcm.core.models;

import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Objects;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeamsLeaderBio {
    @Inject
    private String teamName;

    @Inject
    private String bioPicture;

    @Inject
    private String altText;

    @Inject
    private String fullname;

    @Inject
    private String designation;

    @Inject
    private String leaderUrl;

    @Inject
    private String profileDescription;

    @Inject
    private String linkText;

    @Inject
    private String linkUrl;

    @Inject
    private String linkDescription;

    @Inject
    private String linkTarget;

    @SlingObject
    private ResourceResolver resourceResolver;

    String linkAriaLabel;

    @PostConstruct
    protected void init() {
        if(Objects.nonNull(linkDescription)){
            linkAriaLabel = linkDescription;
        } else if (Objects.nonNull(linkUrl) && Objects.nonNull(linkText)) {
            linkAriaLabel = UtilityService.getLinkDescription(linkUrl, linkText, resourceResolver);
        }
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setBioPicture(String bioPicture) {
        this.bioPicture = bioPicture;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    public void setLeaderUrl(String leaderUrl) { this.leaderUrl = leaderUrl; }

    public String getTeamName() {
        return teamName;
    }

    public String getBioPicture() {
        return bioPicture;
    }

    public String getAltText() {
        return altText;
    }

    public String getFullname() {
        return fullname;
    }

    public String getDesignation() {
        return designation;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getLinkUrl() {
        return UtilityService.identifyLinkUrl(linkUrl,resourceResolver);
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public String getLeaderUrl() { return leaderUrl; }

    public String getLinkAriaLabel() {
        return linkAriaLabel;
    }

    public void setLinkAriaLabel(String linkAriaLabel) {
        this.linkAriaLabel = linkAriaLabel;
    }

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

}
