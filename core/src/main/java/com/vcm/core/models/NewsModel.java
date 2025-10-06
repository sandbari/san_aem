package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface NewsModel {
	
	@Inject
	String mediaIcon();
	
	@Inject
	String mediaSource();
	
	@Inject
	String mediaTitle();
	
	@Inject
	String mediaDescription();
	
	@Inject
	String readMoreText();
	
	@Inject
	String readMoreLink();
	
	@Inject
	String tabSelect();
	
	@Inject
	String mediaDate();
	
	@Inject
	String loadMoreButtonText();
	
	@Inject
	String altText();
}
