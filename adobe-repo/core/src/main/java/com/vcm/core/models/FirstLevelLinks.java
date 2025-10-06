package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


import java.util.List;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface FirstLevelLinks {

	@Inject
	String guestuser();

	@Inject
	String firstLinkText();

	@Inject
	String firstLinkUrl();

	@Inject
	String firstlevelTabSelect();

	@Inject
	List<SecondLevelLinks> secondlevelLinks();

	@Inject
	String secondLevelOnly();

}
