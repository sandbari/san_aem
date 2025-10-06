package com.vcm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IntroPointsBean {
	
	@Inject
	String includeDollar;	    	

	@Inject
	String numberValue;

	@Inject
	String description;

	@Inject
	String asofdate;
	public String getIncludeDollar() {
		return includeDollar;
	}

	public void setIncludeDollar(String includeDollar) {
		this.includeDollar = includeDollar;
	}

	public String getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(String numberValue) {
		this.numberValue = numberValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAsofdate() {
		return asofdate;
	}

	public void setAsofdate(String asofdate) {
		this.asofdate = asofdate;
	}   	
}
