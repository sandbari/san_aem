package com.vcm.core.models;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.pojo.HeaderBean;
import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PostloginHeader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostloginHeader.class);

    @Inject
    List<DropdownLinks> dropdownLinks;

    @Inject
    String basicinfoUrl;

    @Inject
    String basicinfoUrlGuest;

    @Inject
    String messageCenterUrl;

    @Inject
    String homeLinkText;

    @Inject
    String homeMemberUrl;

    @Inject
    String homeGuestUrl;

    @Inject
    String homeTabSelect;

    @Inject
    private String navRootPath;

    @Inject
    List<FirstLevelLinks> firstlevelLinks;

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<HeaderBean> beanList = new ArrayList<HeaderBean>();

    private String FALSE = "false";

    private String TRUE = "true";

    String showInPostlogin = FALSE;

    String hideInNav = FALSE;

    @PostConstruct
    protected void init() {
        if (navRootPath != null) {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page rootPage = pageManager.getPage(navRootPath);
            LOGGER.debug("Root Page Path : {} ", rootPage.getPath());
            vcmLevelNavigation(rootPage, 3, beanList);
        }
    }


    public void vcmLevelNavigation(Page rootPage, int level, List<HeaderBean> beanList) {

        try {
            if (level > 1) {
                rootPage.listChildren().forEachRemaining(currentPage -> {
                    LOGGER.debug("currentPage level > 1 ---> {}",currentPage.getPath());
                    checkHideInNav(currentPage);
                    checkShowInPostlogin(currentPage);
                    boolean navImage=false;
                    if (hideInNav.equalsIgnoreCase(FALSE) && (showInPostlogin.equalsIgnoreCase(TRUE) || level < 3)) {
                        if(level == 2 && null != currentPage.getParent().getProperties().get("useImageInHeaderNav",String.class)  && currentPage.getParent().getProperties().get("useImageInHeaderNav",String.class).equalsIgnoreCase("true")){
                            LOGGER.debug("inside second level image is set to true");
                            navImage = true;
                        }
                        HeaderBean tempsite = generateBeanAddPages(currentPage, beanList, Boolean.FALSE, navImage, level);
                        vcmLevelNavigation(currentPage, level - 1, tempsite.getListBean());
                    }
                });
            } else {
                rootPage.listChildren().forEachRemaining(currentPage -> {
                    boolean navImage=false;
                    LOGGER.debug("currentPage level < 1 ---> {}",currentPage.getPath());
                    checkHideInNav(currentPage);
                    checkShowInPostlogin(currentPage);
                    if (hideInNav.equalsIgnoreCase(FALSE) && (showInPostlogin.equalsIgnoreCase(TRUE))) {
                        if(null != currentPage.getParent().getProperties().get("useImageInHeaderNav",String.class)  && currentPage.getParent().getProperties().get("useImageInHeaderNav",String.class).equalsIgnoreCase("true")){
                            navImage = true;
                        }
                        generateBeanAddPages(currentPage, beanList, Boolean.TRUE, navImage, level);
                    }
                });
            }
        } catch (UnsupportedOperationException | ClassCastException | IllegalArgumentException ex) {
            LOGGER.error("Exception Occured while fetching the child pages :: {}", ex);
        }
    }

    public int getChildNumbers(Page rootPage){
        int count = 0;
        Iterator<Page> secondLevelPagesItr = rootPage.listChildren();
        while (secondLevelPagesItr.hasNext()) {
            Page secondLevelPage = secondLevelPagesItr.next();
            checkHideInNav(secondLevelPage);
            if (hideInNav.equalsIgnoreCase(FALSE)) {
                count = count + 1;
                Iterator<Page> thirdLevelPagesItr = secondLevelPage.listChildren();
                while (thirdLevelPagesItr.hasNext()) {
                    checkHideInNav(thirdLevelPagesItr.next());
                    if (hideInNav.equalsIgnoreCase(FALSE)) {
                        count = count + 1;
                    }
                }
            }
        }
        LOGGER.debug("count of the child ppages : {}" , count);
        if(count > 2) {
            count = (count + 1) / 2;
        }
        return count;
    }

    public HeaderBean generateBeanAddPages(Page levelPage, List<HeaderBean> beanList, Boolean leaf, Boolean navImage, int level)
            throws UnsupportedOperationException, ClassCastException, IllegalArgumentException {
        HeaderBean tempsite = new HeaderBean();
        beanList.add(tempsite);
        List<String> dataValue= new ArrayList<String>();
        if (dataValue.isEmpty()){
            dataValue.add("ALL");
        }
        LOGGER.debug("Nav Image Flag ---> {}",navImage);
        String entityValue = String.join(",",dataValue);
        Resource imgRes = levelPage.getContentResource("image");
        if(navImage == true && null != imgRes) {
            LOGGER.debug("imgRes entry logic --> {}",imgRes);
            ValueMap properties = imgRes.adaptTo(ValueMap.class);
            String navImagePath = properties.get("fileReference",String.class);
            LOGGER.debug("navImagePath ---> {}",navImagePath);
            if(Objects.nonNull(navImagePath)) {
                String[] words=navImagePath.split("/");
                String imageName = words[words.length-1];
                String[] image = imageName.split("\\.");
                LOGGER.debug("imageName ---> {}",imageName);
                String imageAlt = image[0];
                tempsite.setImageAlt(imageAlt);
                tempsite.setImagePath(navImagePath);
                tempsite.setLinkText(levelPage.getTitle());
            }

        } else if(null != levelPage.getProperties().get("navTitle",String.class)) {
            tempsite.setLinkText(levelPage.getProperties().get("navTitle",String.class));
        } else{
            tempsite.setLinkText(levelPage.getTitle());
        }
        if(null != levelPage.getProperties().get("subtitle",String.class)) {
            tempsite.setNavText(levelPage.getProperties().get("subtitle",String.class));
        } else {
            tempsite.setNavText(levelPage.getTitle());
        }
        if(level == 3){
            int childCount = getChildNumbers(levelPage);
            tempsite.setChildCount(childCount);
        }
        if(null != levelPage.getProperties().get("secondLevelNavOnly",String.class) && levelPage.getProperties().get("secondLevelNavOnly",String.class).equalsIgnoreCase("true")) {
            tempsite.setSecondLevelOnly(true);
        }
        tempsite.setLinkUrl(UtilityService.identifyLinkUrl(levelPage.getPath(),resourceResolver));
        tempsite.setEntityType(entityValue);
        LOGGER.info("leaf" + leaf);
        if (leaf == Boolean.FALSE) {
            List<HeaderBean> temp = new ArrayList<>();
            Iterator iterator = temp.iterator();
            while(iterator.hasNext()) {
                LOGGER.info("tmplist" + iterator.next());
            }
            tempsite.setListBean(temp);
        }
        return tempsite;
    }

    public void checkHideInNav(Page levelPage){
        hideInNav = FALSE;
        if(null != levelPage.getProperties().get("hideInNav",String.class) && levelPage.getProperties().get("hideInNav",String.class).equalsIgnoreCase("true")){
            hideInNav = TRUE;
        }
    }

    public void checkShowInPostlogin(Page levelPage){
        showInPostlogin = FALSE;
        if(null != levelPage.getProperties().get("showInPostlogin",String.class) && levelPage.getProperties().get("showInPostlogin",String.class).equalsIgnoreCase("true")){
            showInPostlogin = TRUE;
        }
    }

    public List<DropdownLinks> getDropdownLinks() {
        return dropdownLinks;
    }

    public void setDropdownLinks(List<DropdownLinks> dropdownLinks) {
        this.dropdownLinks = dropdownLinks;
    }

    public String getBasicinfoUrl() {
        return basicinfoUrl;
    }

    public void setBasicinfoUrl(String basicinfoUrl) {
        this.basicinfoUrl = basicinfoUrl;
    }

    public String getBasicinfoUrlGuest() {
        return basicinfoUrlGuest;
    }

    public void setBasicinfoUrlGuest(String basicinfoUrlGuest) {
        this.basicinfoUrlGuest = basicinfoUrlGuest;
    }

    public String getMessageCenterUrl() {
        return messageCenterUrl;
    }

    public void setMessageCenterUrl(String messageCenterUrl) {
        this.messageCenterUrl = messageCenterUrl;
    }

    public String getHomeLinkText() {
        return homeLinkText;
    }

    public void setHomeLinkText(String homeLinkText) {
        this.homeLinkText = homeLinkText;
    }

    public String getHomeMemberUrl() {
        return homeMemberUrl;
    }

    public void setHomeMemberUrl(String homeMemberUrl) {
        this.homeMemberUrl = homeMemberUrl;
    }

    public String getHomeGuestUrl() {
        return homeGuestUrl;
    }

    public void setHomeGuestUrl(String homeGuestUrl) {
        this.homeGuestUrl = homeGuestUrl;
    }

    public String getHomeTabSelect() {
        return homeTabSelect;
    }

    public void setHomeTabSelect(String homeTabSelect) {
        this.homeTabSelect = homeTabSelect;
    }

    public List<FirstLevelLinks> getFirstlevelLinks() {
        return firstlevelLinks;
    }

    public void setFirstlevelLinks(List<FirstLevelLinks> firstlevelLinks) {
        this.firstlevelLinks = firstlevelLinks;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public List<HeaderBean> getBeanList() {
        List<HeaderBean> copyBeanList = beanList;
        Collections.copy(copyBeanList, beanList);
        return copyBeanList;
    }

    public void setBeanList(List<HeaderBean> beanList) {
        List<HeaderBean> copyBeanList = new ArrayList<HeaderBean>();
        copyBeanList.addAll(beanList);
        this.beanList = copyBeanList;
    }

}
