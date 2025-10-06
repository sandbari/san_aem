package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.service.ConstantsConfigService;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class USSA529PerformanceFundModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(USSA529PerformanceFundModel.class);
	
	@Inject
	private ConstantsConfigService constantsConfigService;

	@Inject
	Resource resource;

	private String ussa529PerformanceFundEndPoint;
	
	private String ussa529PerformanceFundKey;
	

	/**
	 * The init method to fetch the page level properties
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Into USSA 529 Fund");
		ussa529PerformanceFundEndPoint = constantsConfigService.getUSSA529PerformanceFundEndPoint();
		ussa529PerformanceFundKey=constantsConfigService.getUSSA529PerformanceFundKey();
	}
	/**
	 * @param constantsConfigService the constantsConfigService to set
	 */
	public void setConstantsConfigService(ConstantsConfigService constantsConfigService) {
		this.constantsConfigService = constantsConfigService;
	}
	/**
	 * @return the ussa529PerformanceFundEndPoint
	 */
	public String getUssa529PerformanceFundEndPoint() {
		return ussa529PerformanceFundEndPoint;
	}

	/**
	 * @return the ussa529PerformanceFundKey
	 */
	public String getUssa529PerformanceFundKey() {
		return ussa529PerformanceFundKey;
	}


}
