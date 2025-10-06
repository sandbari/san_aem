package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface SubscriptionModel {
	
	@Inject
	String subscribeHeading();
	
	@Inject
	String nameLabel();
	
	@Inject 
	String emailLabel();
	
	@Inject
	String submitLabel();
	
}
