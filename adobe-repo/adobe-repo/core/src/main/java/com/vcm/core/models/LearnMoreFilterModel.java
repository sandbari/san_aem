package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.service.LearnMoreService;



/**
 * @author CTS. A Model class for LearnMoreFilterModel
 */

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LearnMoreFilterModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(LearnMoreFilterModel.class);

	@Inject
	private LearnMoreService learnMoreService;

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;
	
	@SlingObject
    private Resource resource;

	
	private String defaultContent;
	
	/**
	 * Post Construct method
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Into Learn More Filter Model");
        PageManager pageManager= resourceResolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(resource);
		defaultContent = learnMoreService.getDefaultContent(currentPage.getPath(),resourceResolver);
		LOGGER.debug("End Learn More Filter Model");
	}

	public String getDefaultContent() {
        return defaultContent;
    }

}
