package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IRAComparisonModel {

    @Inject
    String sectionHeading;

    @Inject
    String sectionDescription;

    @Inject
    String iraTitleOne;

    @Inject
    String iraTitleTwo;

    @Inject
    String iraComparisonText;

    @Inject
    String buttonDescription;

    @Inject
    String linkText;

    @Inject
    String linkUrl;

    @Inject
    private String linkDescription;

    @Inject
    String linkTarget;

    @Inject
    List<IraCompareText> iraTable;

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


    public String getSectionHeading() {
        return sectionHeading;
    }

    public void setSectionHeading(String sectionHeading) {
        this.sectionHeading = sectionHeading;
    }

    public String getSectionDescription() {
        return sectionDescription;
    }

    public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public String getIraTitleOne() {
        return iraTitleOne;
    }

    public void setIraTitleOne(String iraTitleOne) {
        this.iraTitleOne = iraTitleOne;
    }

    public String getIraTitleTwo() {
        return iraTitleTwo;
    }

    public void setIraTitleTwo(String iraTitleTwo) {
        this.iraTitleTwo = iraTitleTwo;
    }

    public String getIraComparisonText() {
        return iraComparisonText;
    }

    public void setIraComparisonText(String iraComparisonText) {
        this.iraComparisonText = iraComparisonText;
    }

    public String getButtonDescription() {
        return buttonDescription;
    }

    public void setButtonDescription(String buttonDescription) {
        this.buttonDescription = buttonDescription;
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

    public List<IraCompareText> getIraTable() {
        List<IraCompareText> copyIraTable = iraTable;
        Collections.copy(copyIraTable, iraTable);
        return copyIraTable;
    }

    public void setIraTable(List<IraCompareText> iraTable) {
        List<IraCompareText> copyIraTable = new ArrayList<IraCompareText>();
        copyIraTable.addAll(iraTable);
        this.iraTable = copyIraTable;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public String getLinkAriaLabel() {
        return linkAriaLabel;
    }

    public void setLinkAriaLabel(String linkAriaLabel) {
        this.linkAriaLabel = linkAriaLabel;
    }

	public String getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

}
