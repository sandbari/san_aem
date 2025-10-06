package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

@Model(adaptables =  { SlingHttpServletRequest.class, Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TextImageCTAModel {
    private static final Logger log = LoggerFactory.getLogger(TextImageCTAModel.class);
	
	@SlingObject
    SlingHttpServletRequest slingRequest;
	
	@Inject @Via("resource")
	 private String variation;
	
	@Inject @Via("resource") 
	private String backgroundImage;
	
	@Inject @Via("resource") 
	private String sectionTitle;
	
	@Inject @Via("resource") 
	private String heading;
	
	@Inject @Via("resource") 
	private String greenUnderline;
	
	@Inject @Via("resource") 
	private String description;
	
	@Inject @Via("resource") 
	private String linkText;
	
	@Inject @Via("resource") 
	private String linkUrl;
	
	@Inject @Via("resource") 
	private String linkDescription;
	
	@Inject @Via("resource") 
	private String secondLinkText;
	
	@Inject @Via("resource") 
	private String secondLinkUrl;
	
	@Inject @Via("resource") 
	private String secondLinkDescription;
	
	@Inject @Via("resource") 
	private String buttonStyle;
	@Inject @Via("resource") 
	private String sectionStyle;
	
	@Inject @Via("resource") 
	private String tabSelect;
	
	@Inject @Via("resource") 
	private String note;
	
	@Inject @Via("resource") 
	private String linkStyle;
	
	@Inject @Via("resource") 
	private String noOfLinks;
	
	@Inject @Via("resource") 
	private String popupOverlayContentPath;
	
	@Inject @Via("resource") 
	private String cardShadow;
	
	@Inject @Via("resource") 
	private String backgroundStyle;

	@Inject @Via("resource") 
	private String headingAlignment;
		
    @SlingObject
    private ResourceResolver resourceResolver;
    
	String popupId;
	

	String popupIdForScndLink;
	
	private boolean isUserAuthorized;
	
	private List<String> allowedAudience = new ArrayList<>();
	
	String experiencePopupPath;
	
	@Inject @Via("resource") 
	private String membersType;
	
    @Inject @Via("resource")
	private String financialAdvisorsType;
    
    @Inject @Via("resource")
	private String institutionalinvestorsType;
    
	@Inject @Via("resource") 
	private String linkStyleForScndLink;
	
	@Inject @Via("resource") 
	private String buttonStyleForScndLink;
	
	@Inject @Via("resource") 
	private String tabSelectForScndLink;
	
	@Inject @Via("resource") 
	private String popupOverlayContentPathForScndLink;
	
	@Inject @Via("resource") 
	private String altText;
	
	
	@Inject
	AudienceSelectorService audienceSelectorService;

	String linkAriaLabel;

	String secondLinkAriaLabel;
	
	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getGreenUnderline() {
		return greenUnderline;
	}

	public void setGreenUnderline(String greenUnderline) {
		this.greenUnderline = greenUnderline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getLinkUrl() {
		return UtilityService.identifyLinkUrl(linkUrl,resourceResolver);
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public String getSectionStyle() {
		return sectionStyle;
	}

	public void setSectionStyle(String sectionStyle) {
		this.sectionStyle = sectionStyle;
	}

	public String getVariation() {
		return variation;
	}

	public void setVariation(String variation) {
		this.variation = variation;
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLinkStyle() {
		return linkStyle;
	}

	public void setLinkStyle(String linkStyle) {
		this.linkStyle = linkStyle;
	}

	public String getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

	public String getSecondLinkText() {
		return secondLinkText;
	}

	public void setSecondLinkText(String secondLinkText) {
		this.secondLinkText = secondLinkText;
	}

	public String getSecondLinkUrl() {
		return UtilityService.identifyLinkUrl(secondLinkUrl,resourceResolver);
	}

	public void setSecondLinkUrl(String secondLinkUrl) {
		this.secondLinkUrl = secondLinkUrl;
	}

	public String getSecondLinkDescription() {
		return secondLinkDescription;
	}

	public void setSecondLinkDescription(String secondLinkDescription) {
		this.secondLinkDescription = secondLinkDescription;
	}

	public String getNoOfLinks() {
		return noOfLinks;
	}

	public void setNoOfLinks(String noOfLinks) {
		this.noOfLinks = noOfLinks;
	}

	public String getPopupOverlayContentPath() {
		return popupOverlayContentPath;
	}

	public void setPopupOverlayContentPath(String popupOverlayContentPath) {
		this.popupOverlayContentPath = popupOverlayContentPath;
	}

	public String getPopupId() {
		return popupId;
	}

	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}

	public String getCardShadow() {
		return cardShadow;
	}

	public void setCardShadow(String cardShadow) {
		this.cardShadow = cardShadow;
	}

	public String getBackgroundStyle() {
		return backgroundStyle;
	}

	public void setBackgroundStyle(String backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}

	public String getHeadingAlignment() {
		return headingAlignment;
	}

	public void setHeadingAlignment(String headingAlignment) {
		this.headingAlignment = headingAlignment;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getPopupIdForScndLink() {
		return popupIdForScndLink;
	}

	public void setPopupIdForScndLink(String popupIdForScndLink) {
		this.popupIdForScndLink = popupIdForScndLink;
	}

	public boolean getIsUserAuthorized() {
		return isUserAuthorized;
	}

	public void setIsUserAuthorized(boolean flag) {
		this.isUserAuthorized = flag;
	}

	public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}

	public void setAllowedAudience(List<String> allowedAudience) {
		List<String> copyOfAllowedAudience = new ArrayList<String>();
		copyOfAllowedAudience.addAll(allowedAudience);
		this.allowedAudience = copyOfAllowedAudience;
	}

	public String getMembersType() {
		return membersType;
	}

	public void setMembersType(String membersType) {
		this.membersType = membersType;
	}

	public String getFinancialAdvisorsType() {
		return financialAdvisorsType;
	}

	public void setFinancialAdvisorsType(String financialAdvisorsType) {
		this.financialAdvisorsType = financialAdvisorsType;
	}

	public String getInstitutionalinvestorsType() {
		return institutionalinvestorsType;
	}

	public void setInstitutionalinvestorsType(String institutionalinvestorsType) {
		this.institutionalinvestorsType = institutionalinvestorsType;
	}

	public String getLinkStyleForScndLink() {
		return linkStyleForScndLink;
	}

	public void setLinkStyleForScndLink(String linkStyleForScndLink) {
		this.linkStyleForScndLink = linkStyleForScndLink;
	}

	public String getButtonStyleForScndLink() {
		return buttonStyleForScndLink;
	}

	public void setButtonStyleForScndLink(String buttonStyleForScndLink) {
		this.buttonStyleForScndLink = buttonStyleForScndLink;
	}

	public String getTabSelectForScndLink() {
		return tabSelectForScndLink;
	}

	public void setTabSelectForScndLink(String tabSelectForScndLink) {
		this.tabSelectForScndLink = tabSelectForScndLink;
	}

	public String getPopupOverlayContentPathForScndLink() {
		return popupOverlayContentPathForScndLink;
	}

	public void setPopupOverlayContentPathForScndLink(String popupOverlayContentPathForScndLink) {
		this.popupOverlayContentPathForScndLink = popupOverlayContentPathForScndLink;
	}

	/**
	 * @param audienceSelectorService the audienceSelectorService to set
	 */
	public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
		this.audienceSelectorService = audienceSelectorService;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getLinkAriaLabel() {
		return linkAriaLabel;
	}

	public void setLinkAriaLabel(String linkAriaLabel) {
		this.linkAriaLabel = linkAriaLabel;
	}

	public String getSecondLinkAriaLabel() {
		return secondLinkAriaLabel;
	}

	public void setSecondLinkAriaLabel(String secondLinkAriaLabel) {
		this.secondLinkAriaLabel = secondLinkAriaLabel;
	}
	
	public String getExperiencePopupPath() {
		return UtilityService.identifyLinkUrl(popupOverlayContentPath, resourceResolver);
	}

	public void setExperiencePopupPath(String experiencePopupPath) {
		this.experiencePopupPath = experiencePopupPath;
	}

	@PostConstruct
	protected void init() {
		log.debug("Inside Textimagecta init method");
		popupId = UtilityService.getPopupId(popupOverlayContentPath, resourceResolver);
		popupIdForScndLink = UtilityService.getPopupId(popupOverlayContentPathForScndLink, resourceResolver);
		isUserAuthorized = audienceSelectorService.isUserAuthorized(slingRequest);
		allowedAudience= audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
		log.debug("isUserAuthorized : {}", isUserAuthorized);
        if(Objects.nonNull(linkDescription)){
            linkAriaLabel = linkDescription;
        } else if (Objects.nonNull(linkUrl) && Objects.nonNull(linkText)) {
            linkAriaLabel = UtilityService.getLinkDescription(linkUrl, linkText, resourceResolver);
        }
        if(Objects.nonNull(secondLinkDescription)){
        	secondLinkAriaLabel = secondLinkDescription;
        } else if (Objects.nonNull(secondLinkUrl) && Objects.nonNull(secondLinkText)) {
        	secondLinkAriaLabel = UtilityService.getLinkDescription(secondLinkUrl, secondLinkText, resourceResolver);
        }
		log.debug("End of Textimagecta init method");
	}
			 
	
}
