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
public class ProductDetailConfigureOverlayModel {

	@Inject
	private String isEnablingOverlay;
	
	@Inject
	private String overlayType;
	
	@Inject
	private String overlayTextPrefix;
	
	@Inject
	private String overlayLinkText;
	
	@Inject
	private String overlayContentPath;
	
	String overlayId;
	String overlayContent;
	
	@SlingObject
    private ResourceResolver resourceResolver;
	
	public String getIsEnablingOverlay() {
		return isEnablingOverlay;
	}
	public void setIsEnablingOverlay(String isEnablingOverlay) {
		this.isEnablingOverlay = isEnablingOverlay;
	}
	public String getOverlayType() {
		return overlayType;
	}
	public void setOverlayType(String overlayType) {
		this.overlayType = overlayType;
	}
	public String getOverlayTextPrefix() {
		return overlayTextPrefix;
	}
	public void setOverlayTextPrefix(String overlayTextPrefix) {
		this.overlayTextPrefix = overlayTextPrefix;
	}
	public String getOverlayLinkText() {
		return overlayLinkText;
	}
	public void setOverlayLinkText(String overlayLinkText) {
		this.overlayLinkText = overlayLinkText;
	}
	public String getOverlayContentPath() {
		return overlayContentPath;
	}
	public void setOverlayContentPath(String overlayContentPath) {
		this.overlayContentPath = overlayContentPath;
	}	
	
	public String getOverlayId() {
		return overlayId;
	}
	public void setOverlayId(String overlayId) {
		this.overlayId = overlayId;
	}
	public String getOverlayContent() {
		return overlayContent;
	}
	public void setOverlayContent(String overlayContent) {
		this.overlayContent = overlayContent;
	}
	@PostConstruct
	protected void init() {
		overlayContent = UtilityService.getProductDetailOverlayContent(overlayContentPath, resourceResolver, "textcontent");
		overlayId = UtilityService.getProductDetailOverlayContent(overlayContentPath, resourceResolver, "popupId");
	}	
	
}