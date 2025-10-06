package com.vcm.core.models;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroBannerModel {
	
	@Inject
	private String varientType;
	
	@Inject
	private String heroImage;
	
	@Inject 
	private String heroImageMobile;
	
	@Inject
	private String heroImageTablet;
	
	
	@Inject
	private String altText;
	
	@Inject
	private String welcomeText;
	
	@Inject
	private String heading;
	
	@Inject
	private String textWrap;
	
	
	@Inject
	private String description;
	
	
	@Inject
	private String linkText;
	
	@Inject
	private String linkUrl;
	
	@Inject
	private String tabSelect;
	
	
	@Inject
	private String requirementItems;
	
	
	@Inject
	private String fragmentPath;
	
	@Inject
	private String imagePath;
	
	@Inject
	private String bannerType;
	
	@Inject
	private String heroBackgroundImage;
	
	@Inject
	private String heroBackgroundColor;
	
	@Inject
	private String imageFocusArea;
	
	@Inject
	private String addGreenTriangle;	
	
	@Inject
	private String imagePathAltText;
	
	@Inject
    private String linkDescription;
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	String linkAriaLabel;

	@PostConstruct
    protected void init() {
        if(Objects.nonNull(linkDescription)){
            linkAriaLabel = linkDescription;
        } else if (Objects.nonNull(linkUrl) && Objects.nonNull(linkText)) {
            linkAriaLabel = UtilityService.getLinkDescription(linkUrl, linkText, resourceResolver);
        }
    }

	public String getVarientType() {
		return varientType;
	}

	public void setVarientType(String varientType) {
		this.varientType = varientType;
	}

	public String getHeroImage() {
		return heroImage;
	}

	public void setHeroImage(String heroImage) {
		this.heroImage = heroImage;
	}

	public String getHeroImageMobile() {
		return heroImageMobile;
	}

	public void setHeroImageMobile(String heroImageMobile) {
		this.heroImageMobile = heroImageMobile;
	}

	public String getHeroImageTablet() {
		return heroImageTablet;
	}

	public void setHeroImageTablet(String heroImageTablet) {
		this.heroImageTablet = heroImageTablet;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getWelcomeText() {
		return welcomeText;
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getTextWrap() {
		return textWrap;
	}

	public void setTextWrap(String textWrap) {
		this.textWrap = textWrap;
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
		return UtilityService.identifyLinkUrl(linkUrl, resourceResolver);
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	public String getRequirementItems() {
		return requirementItems;
	}

	public void setRequirementItems(String requirementItems) {
		this.requirementItems = requirementItems;
	}

	public String getFragmentPath() {
		return fragmentPath;
	}

	public void setFragmentPath(String fragmentPath) {
		this.fragmentPath = fragmentPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getBannerType() {
		return bannerType;
	}

	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}

	public String getHeroBackgroundImage() {
		return heroBackgroundImage;
	}

	public void setHeroBackgroundImage(String heroBackgroundImage) {
		this.heroBackgroundImage = heroBackgroundImage;
	}

	public String getHeroBackgroundColor() {
		return heroBackgroundColor;
	}

	public void setHeroBackgroundColor(String heroBackgroundColor) {
		this.heroBackgroundColor = heroBackgroundColor;
	}

	public String getImageFocusArea() {
		return imageFocusArea;
	}

	public void setImageFocusArea(String imageFocusArea) {
		this.imageFocusArea = imageFocusArea;
	}

	public String getAddGreenTriangle() {
		return addGreenTriangle;
	}

	public void setAddGreenTriangle(String addGreenTriangle) {
		this.addGreenTriangle = addGreenTriangle;
	}

	public String getImagePathAltText() {
		return imagePathAltText;
	}

	public void setImagePathAltText(String imagePathAltText) {
		this.imagePathAltText = imagePathAltText;
	}
	
	public String getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}
	
	public String getLinkAriaLabel() {
		return linkAriaLabel;
	}

	public void setLinkAriaLabel(String linkAriaLabel) {
		this.linkAriaLabel = linkAriaLabel;
	}
	
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
	
}
