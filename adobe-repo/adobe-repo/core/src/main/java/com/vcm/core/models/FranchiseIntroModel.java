package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Objects;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FranchiseIntroModel {
	
	@Inject
	private String franchiseDescription;

	public void setStrategyHeading(String strategyHeading) {
		this.strategyHeading = strategyHeading;
	}

	public void setStrategyDescription(String strategyDescription) {
		this.strategyDescription = strategyDescription;
	}
	
	@Inject
	private String altText;	

	@Inject
	private String strategyHeading;
	
	@Inject
	private String strategyDescription;
	
	@Inject
    private String linkDescription;
		
	@Inject
	private String strategyViewDetail;
	
	@Inject
	private String strategyLinkUrl;
	
	@Inject
	private String tabSelectStrategy;
	
	@Inject
	private String strategyShowButton;
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}	
	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}
	
	public String getFranchiseDescription() {
		return franchiseDescription;
	}
	
  	public String getStrategyHeading() {
		return strategyHeading;
	}

	public String getStrategyDescription() {
		return strategyDescription;
	}

	public String getStrategyViewDetail() {
		return strategyViewDetail;
	}
	
	public String getStrategyLinkUrl() {
		return UtilityService.identifyLinkUrl(strategyLinkUrl,resourceResolver);
	}
	
	public String getStrategyShowButton() {
		return strategyShowButton;
	}
	
	public String getTabSelectStrategy() {
		return tabSelectStrategy;
	}
	
	public void setFranchiseDescription(String franchiseDescription) {
			this.franchiseDescription = franchiseDescription;
	}
	  
	public void setStrategyShowButton(String strategyShowButton) {
		this.strategyShowButton = strategyShowButton;
	}
	
	public void setStrategyViewDetail(String strategyViewDetail) {
		this.strategyViewDetail = strategyViewDetail;
	}

	public void setStrategyLinkUrl(String strategyLinkUrl) {
		this.strategyLinkUrl = strategyLinkUrl;
	}
	
	public void setTabSelectStrategy(String tabSelectStrategy) {
		this.tabSelectStrategy = tabSelectStrategy;
	}
	
	public String getLinkDescription() { return linkDescription; }

    public void setLinkDescription(String linkDescription) { this.linkDescription = linkDescription; }

    public String getLinkAriaLabel() {
        return linkAriaLabel;
    }

    public void setLinkAriaLabel(String linkAriaLabel) {
        this.linkAriaLabel = linkAriaLabel;
    }
	
	String linkAriaLabel;

    @PostConstruct
    protected void init() {
        if(Objects.nonNull(linkDescription)){
            linkAriaLabel = linkDescription;
        } else if (Objects.nonNull(strategyLinkUrl) && Objects.nonNull(strategyViewDetail)) {
            linkAriaLabel = UtilityService.getLinkDescription(strategyLinkUrl, strategyViewDetail, resourceResolver);
        }
    }

	
}
