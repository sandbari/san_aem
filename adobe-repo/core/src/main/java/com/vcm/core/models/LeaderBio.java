package com.vcm.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Objects;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LeaderBio {

    @Inject
    private String bioPicture;

    @Inject
    private String altText;

    @Inject
    private String fullname;

    @Inject
    private String titleAbbreviation;

    @Inject
    private String titleDescription;

    @Inject
    private String designation;

    @Inject
    private String profileDescription;

    @Inject
    private String shortDescription;

    @Inject
    private String bioDisplayStyle;

    @Inject
    private String linkText;

    @SlingObject
    private Resource resource;

    private String pagePath;

    private String backPage;

    ResourceResolver resolver;

    Page pageDetails;

    String backLinkAriaLabel;

    String pageLinkAriaLabel;

    String managementPicture;

    @PostConstruct
    protected void init() {
        resolver = resource.getResourceResolver();
        PageManager pageManager= resolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(resource);
        pageDetails = currentPage;
        pagePath = currentPage.getPath();
        backPage = currentPage.getParent().getPath();
        if (Objects.nonNull(backPage) && Objects.nonNull(linkText)) {
            backLinkAriaLabel = UtilityService.getLinkDescription(backPage, linkText, resolver);
        }
        if (Objects.nonNull(pagePath)) {
            pageLinkAriaLabel = UtilityService.getLinkDescription(pagePath, linkText, resolver);
        }
    }

    public void setBioPicture(String bioPicture) {this.bioPicture = bioPicture; }

    public void setAltText(String altText) { this.altText = altText; }

    public void setFullname(String fullname) { this.fullname = fullname; }

    public void setTitleAbbreviation(String titleAbbreviation) { this.titleAbbreviation = titleAbbreviation; }

    public void setTitleDescription(String titleDescription) { this.titleDescription = titleDescription; }

    public void setDesignation(String designation) { this.designation = designation; }

    public void setProfileDescription(String profileDescription) { this.profileDescription = profileDescription; }

    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public void setBioDisplayStyle(String bioDisplayStyle) { this.bioDisplayStyle = bioDisplayStyle; }

    public void setLinkText(String linkText) { this.linkText = linkText; }

    public void setPagePath(String pagePath) { this.pagePath = pagePath; }

    public String getPagePath() {
        return UtilityService.identifyLinkUrl(pagePath,resolver);
    }

    public String getBackPage() {
        return UtilityService.identifyLinkUrl(backPage,resolver);
    }

    public String getBioPicture() {
        return bioPicture;
    }

    public String getAltText() {
        return altText;
    }

    public String getFullname() {
        return fullname;
    }

    public String getTitleAbbreviation() {
        return titleAbbreviation;
    }

    public String getTitleDescription() {
        return titleDescription;
    }

    public String getDesignation() {
        return designation;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getBioDisplayStyle() {
        return bioDisplayStyle;
    }

    public String getLinkText() {
        return linkText;
    }

    public Page getPageDetails() {
        return pageDetails;
    }

    public void setPageDetails(Page pageDetails) {
        this.pageDetails = pageDetails;
    }

    public String getBackLinkAriaLabel() {
        return backLinkAriaLabel;
    }

    public void setBackLinkAriaLabel(String backLinkAriaLabel) {
        this.backLinkAriaLabel = backLinkAriaLabel;
    }

    public String getPageLinkAriaLabel() {
        return pageLinkAriaLabel;
    }

    public void setPageLinkAriaLabel(String pageLinkAriaLabel) {
        this.pageLinkAriaLabel = pageLinkAriaLabel;
    }

    public String getManagementPicture() { return managementPicture; }

    public void setManagementPicture(String managementPicture) { this.managementPicture = managementPicture; }

    public void setResourceResolver(ResourceResolver resolver) { this.resolver = resolver; }

}