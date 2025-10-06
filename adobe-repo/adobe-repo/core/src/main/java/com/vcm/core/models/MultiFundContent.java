package com.vcm.core.models;

import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.inject.Inject;
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultiFundContent {

    @Inject
    String ticker;

    @Inject
    String fundName;

    @Inject
    String fundUrl;

    @SlingObject
    private ResourceResolver resourceResolver;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundUrl() {
        return UtilityService.identifyLinkUrl(fundUrl, resourceResolver);
    }

    public void setFundUrl(String fundUrl) {
        this.fundUrl = fundUrl;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

}
