package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AnnualizedReturns {

	@Inject
	private String heading;
	
	@Inject
	private String tooltipDescription;
	
	@Inject
	private String monthlyLabel;
	
	@Inject
	private String quarterlyLabel;
	
	@Inject
	private String serviceType;

	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getTooltipDescription() {
		return tooltipDescription;
	}

	public void setTooltipDescription(String tooltipDescription) {
		this.tooltipDescription = tooltipDescription;
	}

	public String getMonthlyLabel() {
		return monthlyLabel;
	}

	public void setMonthlyLabel(String monthlyLabel) {
		this.monthlyLabel = monthlyLabel;
	}

	public String getQuarterlyLabel() {
		return quarterlyLabel;
	}

	public void setQuarterlyLabel(String quarterlyLabel) {
		this.quarterlyLabel = quarterlyLabel;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}
