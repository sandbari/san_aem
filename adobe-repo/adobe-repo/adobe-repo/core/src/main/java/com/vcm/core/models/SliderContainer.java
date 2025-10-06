package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.VCMSiteConfiguationService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

import java.util.Map;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SliderContainer {

	@Inject
	private VCMSiteConfiguationService vcmSiteConfigService;
	
	@Inject
	String heading;
	
	@Inject
	String sliderMin;
	
	@Inject
	String sliderMax;
	
	@Inject
	String description;
	
	@Inject
	String viewAll;
	
	@Inject
	String viewSlider;
	
	@Inject
	String performanceUrl;
	
	@Inject
	String viewPlanPerformanceText;
	
	@Inject
	Resource parsys;
	
    @SlingObject
    private ResourceResolver resourceResolver;

	private String collegeDetailLabels;

	@PostConstruct
	protected void init() {
		Gson gson = new Gson();
		Map<String, Object> productLabels = vcmSiteConfigService.getLabelConfigAsMap();
		if(null != productLabels && !productLabels.isEmpty()) {
			collegeDetailLabels = gson.toJson(productLabels.get(Constant.COLLEGE_DETAIL_LABELS));
		}
	}

	public String getCollegeDetailLabels() {
		return collegeDetailLabels;
	}

	public void setCollegeDetailLabels(String collegeDetailLabels) {
		this.collegeDetailLabels = collegeDetailLabels;
	}

	public String getViewPlanPerformanceText() {
		return viewPlanPerformanceText;
	}

	public void setViewPlanPerformanceText(String viewPlanPerformanceText) {
		this.viewPlanPerformanceText = viewPlanPerformanceText;
	}

	public String getViewSlider() {
		return viewSlider;
	}

	public void setViewSlider(String viewSlider) {
		this.viewSlider = viewSlider;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getSliderMin() {
		return sliderMin;
	}

	public void setSliderMin(String sliderMin) {
		this.sliderMin = sliderMin;
	}

	public String getSliderMax() {
		return sliderMax;
	}

	public void setSliderMax(String sliderMax) {
		this.sliderMax = sliderMax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getViewAll() {
		return viewAll;
	}

	public void setViewAll(String viewAll) {
		this.viewAll = viewAll;
	}

	public String getPerformanceUrl() {
		return UtilityService.identifyLinkUrl(performanceUrl, resourceResolver);
	}

	public void setPerformanceUrl(String performanceUrl) {
		this.performanceUrl = performanceUrl;
	}
}
