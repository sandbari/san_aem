package com.vcm.core.models;

import javax.inject.Inject;

import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import javax.annotation.PostConstruct;
import java.util.Objects;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InvestmentOptionsModel {

	@Inject
	private String heading;

	@Inject
	private String description;

	@Inject
	private String linkText;
	
	@Inject
	private String linkDescription;

	@Inject
	private String linkUrl;

	@Inject
	private String linkTarget;
	
	@Inject
	private String imageOption1;
	
	@Inject
	private String imgAltText1;
	
	@Inject
	private String headingOption1;
	
	@Inject
	private String descriptionOption1;

	@Inject
	private String imageOption2;
	
	@Inject
	private String imgAltText2;

	@Inject
	private String headingOption2;

	@Inject
	private String descriptionOption2;

	@Inject
	private String optionsSeparator;

	@SlingObject
	private ResourceResolver resourceResolver;

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
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

	public String getLinkTarget() {
		return linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public String getImageOption1() {
		return imageOption1;
	}

	public void setImageOption1(String imageOption1) {
		this.imageOption1 = imageOption1;
	}

	public String getHeadingOption1() {
		return headingOption1;
	}

	public void setHeadingOption1(String headingOption1) {
		this.headingOption1 = headingOption1;
	}

	public String getDescriptionOption1() {
		return descriptionOption1;
	}

	public void setDescriptionOption1(String descriptionOption1) {
		this.descriptionOption1 = descriptionOption1;
	}

	public String getImageOption2() {
		return imageOption2;
	}

	public void setImageOption2(String imageOption2) {
		this.imageOption2 = imageOption2;
	}

	public String getHeadingOption2() {
		return headingOption2;
	}

	public void setHeadingOption2(String headingOption2) {
		this.headingOption2 = headingOption2;
	}

	public String getDescriptionOption2() {
		return descriptionOption2;
	}

	public void setDescriptionOption2(String descriptionOption2) {
		this.descriptionOption2 = descriptionOption2;
	}
	
	public String getImgAltText1() {
		return imgAltText1;
	}

	public void setImgAltText1(String imgAltText1) {
		this.imgAltText1 = imgAltText1;
	}

	public String getImgAltText2() {
		return imgAltText2;
	}

	public void setImgAltText2(String imgAltText2) {
		this.imgAltText2 = imgAltText2;
	}

	public String getOptionsSeparator() {
		return optionsSeparator;
	}

	public void setOptionsSeparator(String optionsSeparator) {
		this.optionsSeparator = optionsSeparator;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
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
	
	String linkAriaLabel;
	
	@PostConstruct
    protected void init() {
        if(Objects.nonNull(linkDescription)){
            linkAriaLabel = linkDescription;
        } else if (Objects.nonNull(linkUrl) && Objects.nonNull(linkText)) {
            linkAriaLabel = UtilityService.getLinkDescription(linkUrl, linkText, resourceResolver);
        }
    }
}
