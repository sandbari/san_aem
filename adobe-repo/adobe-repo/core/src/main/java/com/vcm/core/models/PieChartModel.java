package com.vcm.core.models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcm.core.pojo.PieChartBean;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PieChartModel {
	
	@Inject
	String portfolioOptionText;
	
	@Inject
	String description;
	
	@Inject
	String aggressiveText;
	
	@Inject
	String conservativeText;
	
	@Inject
	String includeSlider;

	@Inject
	String linkUrl;
	
	@Inject
	String viewAllText;
	
    @Inject
    List<PieChartBean> chartInfo;

	String json;
	
    @SlingObject
    private ResourceResolver resourceResolver;
    
	@PostConstruct
    protected void init() {
		Map<Integer,PieChartBean> map = new HashMap<Integer,PieChartBean>();
		
		if(chartInfo != null) {
		for(int i=0; i < chartInfo.size(); i++) {
			map.put(i, chartInfo.get(i));
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();	
		json = gson.toJson(map);
		}
    }

	public String getPortfolioOptionText() {
		return portfolioOptionText;
	}

	public void setPortfolioOptionText(String portfolioOptionText) {
		this.portfolioOptionText = portfolioOptionText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAggressiveText() {
		return aggressiveText;
	}

	public void setAggressiveText(String aggressiveText) {
		this.aggressiveText = aggressiveText;
	}

	public String getConservativeText() {
		return conservativeText;
	}

	public void setConservativeText(String conservativeText) {
		this.conservativeText = conservativeText;
	}
	
	public String getIncludeSlider() {
		return includeSlider;
	}

	public void setIncludeSlider(String includeSlider) {
		this.includeSlider = includeSlider;
	}

	public String getLinkUrl() {
		return UtilityService.identifyLinkUrl(linkUrl,resourceResolver);
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getViewAllText() {
		return viewAllText;
	}

	public void setViewAllText(String viewAllText) {
		this.viewAllText = viewAllText;
	}	
	
    public List<PieChartBean> getChartInfo() {
		return chartInfo;
	}

	public void setChartInfo(List<PieChartBean> chartInfo) {
		this.chartInfo = chartInfo;
	}

	public String getJson() {
		return json;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
	
}
