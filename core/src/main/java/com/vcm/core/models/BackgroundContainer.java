package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.PageManager;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = { SlingHttpServletRequest.class,Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BackgroundContainer {

    @Inject
    @Via("resource")
    String backgroundStyle;

    @Inject
    @Via("resource")
    String headingText;

    @Inject
    @Via("resource")
    String headingAlignment;

    @Inject
    @Via("resource")
    String leftLinkText;

    @Inject
    @Via("resource")
    String leftLinkUrl;

    @Inject
    @Via("resource")
    String rightLinkText;

    @Inject
    @Via("resource")
    String rightLinkUrl;

    @Inject
    @Via("resource")
    String cornerTriangle;

    @Inject
    @Via("resource")
    String loadMoreButtonLabel;
    
    @Inject
    @Via("resource")
    String filterViewType;
    
    @Inject
    @Via("resource")
    String seeAllLink;
    
    @Inject
    @Via("resource")
    String seeAllLabel;
    
    private List<String> allowedAudience = new ArrayList<String>();
	
    @Inject
	AudienceSelectorService audienceSelectorService;
	

    @Inject
    @Via("resource")
    String ilcAlignment;

	@SlingObject
    private ResourceResolver resourceResolver;

    String rightPageTitle;

    String leftPageTitle;
    
    @SlingObject
    SlingHttpServletRequest slingRequest;
	

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if(null != rightLinkUrl && null != pageManager.getPage(rightLinkUrl)) {
             rightPageTitle = pageManager.getPage(rightLinkUrl).getTitle();
        }
        if(null != leftLinkUrl && null != pageManager.getPage(leftLinkUrl)) {
             leftPageTitle = pageManager.getPage(leftLinkUrl).getTitle();
        }
        allowedAudience= audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
    }

    public String getBackgroundStyle() {
        return backgroundStyle;
    }

    public void setBackgroundStyle(String backgroundStyle) {
        this.backgroundStyle = backgroundStyle;
    }

    public String getHeadingText() {
        return headingText;
    }

    public void setHeadingText(String headingText) {
        this.headingText = headingText;
    }

    public String getHeadingAlignment() {
        return headingAlignment;
    }

    public void setHeadingAlignment(String headingAlignment) {
        this.headingAlignment = headingAlignment;
    }

    public String getLeftLinkText() {
        return leftLinkText;
    }

    public void setLeftLinkText(String leftLinkText) {
        this.leftLinkText = leftLinkText;
    }

    public String getLeftLinkUrl() {
        return UtilityService.identifyLinkUrl(leftLinkUrl, resourceResolver);
    }

    public void setLeftLinkUrl(String leftLinkUrl) {
        this.leftLinkUrl = leftLinkUrl;
    }

    public String getRightLinkText() {
        return rightLinkText;
    }

    public void setRightLinkText(String rightLinkText) {
        this.rightLinkText = rightLinkText;
    }

    public String getRightLinkUrl() {
        return UtilityService.identifyLinkUrl(rightLinkUrl, resourceResolver);
    }

    public void setRightLinkUrl(String rightLinkUrl) {
        this.rightLinkUrl = rightLinkUrl;
    }

    public String getCornerTriangle() {
        return cornerTriangle;
    }

    public void setCornerTriangle(String cornerTriangle) {
        this.cornerTriangle = cornerTriangle;
    }

    public String getLoadMoreButtonLabel() {
        return loadMoreButtonLabel;
    }

    public void setLoadMoreButtonLabel(String loadMoreButtonLabel) {
        this.loadMoreButtonLabel = loadMoreButtonLabel;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public String getRightPageTitle() {
        return rightPageTitle;
    }

    public void setRightPageTitle(String rightPageTitle) {
        this.rightPageTitle = rightPageTitle;
    }

    public String getLeftPageTitle() {
        return leftPageTitle;
    }

    public void setLeftPageTitle(String leftPageTitle) {
        this.leftPageTitle = leftPageTitle;
    }

	public String getFilterViewType() {
		return filterViewType;
	}

	public void setFilterViewType(String filterViewType) {
		this.filterViewType = filterViewType;
	}
	

    /**
	 * @return the seeAllLink
	 */
	public String getSeeAllLink() {
		return seeAllLink;
	}

	/**
	 * @param seeAllLink the seeAllLink to set
	 */
	public void setSeeAllLink(String seeAllLink) {
		this.seeAllLink = seeAllLink;
	}
	/**
	 * @param audienceSelectorService the audienceSelectorService to set
	 */
	public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
		this.audienceSelectorService = audienceSelectorService;
	}

	/**
	 * @param slingRequest the slingRequest to set
	 */
	public void setSlingRequest(SlingHttpServletRequest slingRequest) {
		this.slingRequest = slingRequest;
	}

	/**
	 * @return the seeAllLabel
	 */
	public String getSeeAllLabel() {
		return seeAllLabel;
	}

	/**
	 * @param seeAllLabel the seeAllLabel to set
	 */
	public void setSeeAllLabel(String seeAllLabel) {
		this.seeAllLabel = seeAllLabel;
	}

	public String getIlcAlignment() {
		return ilcAlignment;
	}

	public void setIlcAlignment(String ilcAlignment) {
		this.ilcAlignment = ilcAlignment;
	}
	
	public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}


}
