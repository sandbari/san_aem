package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RightRailETF {

	@Inject
	private String summaryOverlayContentPath;

	@Inject
	private String summaryTextPrefix;

	@Inject
	private String summaryLinkText;

	@Inject
	private String funddetailsOverlayContentPath;

	@Inject
	private String funddetailsTextPrefix;

	@Inject
	private String funddetailsLinkText;

	@Inject
	private String fundyieldOverlayContentPath;

	@Inject
	private String fundyieldTextPrefix;

	@Inject
	private String fundyieldLinkText;

	String summaryoverlayId;
	String summaryoverlayContent;
	String funddetailsoverlayId;
	String funddetailsoverlayContent;
	String fundyieldoverlayId;
	String fundyieldoverlayContent;
	
	@SlingObject
    private ResourceResolver resourceResolver;

	@PostConstruct
	protected void init() {
		summaryoverlayContent = UtilityService.getProductDetailOverlayContent(summaryOverlayContentPath, resourceResolver, "textcontent");
		summaryoverlayId = UtilityService.getProductDetailOverlayContent(summaryOverlayContentPath, resourceResolver, "popupId");
		funddetailsoverlayContent = UtilityService.getProductDetailOverlayContent(funddetailsOverlayContentPath, resourceResolver, "textcontent");
		funddetailsoverlayId = UtilityService.getProductDetailOverlayContent(funddetailsOverlayContentPath, resourceResolver, "popupId");
		fundyieldoverlayContent = UtilityService.getProductDetailOverlayContent(fundyieldOverlayContentPath, resourceResolver, "textcontent");
		fundyieldoverlayId = UtilityService.getProductDetailOverlayContent(fundyieldOverlayContentPath, resourceResolver, "popupId");
	}

	public String getSummaryOverlayContentPath() {
		return summaryOverlayContentPath;
	}

	public void setSummaryOverlayContentPath(String summaryOverlayContentPath) {
		this.summaryOverlayContentPath = summaryOverlayContentPath;
	}

	public String getSummaryTextPrefix() {
		return summaryTextPrefix;
	}

	public void setSummaryTextPrefix(String summaryTextPrefix) {
		this.summaryTextPrefix = summaryTextPrefix;
	}

	public String getSummaryLinkText() {
		return summaryLinkText;
	}

	public void setSummaryLinkText(String summaryLinkText) {
		this.summaryLinkText = summaryLinkText;
	}

	public String getFunddetailsOverlayContentPath() {
		return funddetailsOverlayContentPath;
	}

	public void setFunddetailsOverlayContentPath(String funddetailsOverlayContentPath) {
		this.funddetailsOverlayContentPath = funddetailsOverlayContentPath;
	}

	public String getFunddetailsTextPrefix() {
		return funddetailsTextPrefix;
	}

	public void setFunddetailsTextPrefix(String funddetailsTextPrefix) {
		this.funddetailsTextPrefix = funddetailsTextPrefix;
	}

	public String getFunddetailsLinkText() {
		return funddetailsLinkText;
	}

	public void setFunddetailsLinkText(String funddetailsLinkText) {
		this.funddetailsLinkText = funddetailsLinkText;
	}

	public String getFundyieldOverlayContentPath() {
		return fundyieldOverlayContentPath;
	}

	public void setFundyieldOverlayContentPath(String fundyieldOverlayContentPath) {
		this.fundyieldOverlayContentPath = fundyieldOverlayContentPath;
	}

	public String getFundyieldTextPrefix() {
		return fundyieldTextPrefix;
	}

	public void setFundyieldTextPrefix(String fundyieldTextPrefix) {
		this.fundyieldTextPrefix = fundyieldTextPrefix;
	}

	public String getFundyieldLinkText() {
		return fundyieldLinkText;
	}

	public void setFundyieldLinkText(String fundyieldLinkText) {
		this.fundyieldLinkText = fundyieldLinkText;
	}

	public String getSummaryoverlayId() {
		return summaryoverlayId;
	}

	public void setSummaryoverlayId(String summaryoverlayId) {
		this.summaryoverlayId = summaryoverlayId;
	}

	public String getSummaryoverlayContent() {
		return summaryoverlayContent;
	}

	public void setSummaryoverlayContent(String summaryoverlayContent) {
		this.summaryoverlayContent = summaryoverlayContent;
	}

	public String getFunddetailsoverlayId() {
		return funddetailsoverlayId;
	}

	public void setFunddetailsoverlayId(String funddetailsoverlayId) {
		this.funddetailsoverlayId = funddetailsoverlayId;
	}

	public String getFunddetailsoverlayContent() {
		return funddetailsoverlayContent;
	}

	public void setFunddetailsoverlayContent(String funddetailsoverlayContent) {
		this.funddetailsoverlayContent = funddetailsoverlayContent;
	}

	public String getFundyieldoverlayId() {
		return fundyieldoverlayId;
	}

	public void setFundyieldoverlayId(String fundyieldoverlayId) {
		this.fundyieldoverlayId = fundyieldoverlayId;
	}

	public String getFundyieldoverlayContent() {
		return fundyieldoverlayContent;
	}

	public void setFundyieldoverlayContent(String fundyieldoverlayContent) {
		this.fundyieldoverlayContent = fundyieldoverlayContent;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
	
}