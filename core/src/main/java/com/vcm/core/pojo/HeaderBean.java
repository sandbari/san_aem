package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeaderBean {

    private String linkText;
    private String navText;
    private String linkUrl;
    private String imagePath;
    private String imageAlt;
    private String entityType;
    private int childCount;
    private Boolean secondLevelOnly;
    private List<HeaderBean> listBean;

    public String getNavText() {
        return navText;
    }

    public void setNavText(String navText) {
        this.navText = navText;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public List<HeaderBean> getListBean() {
        List<HeaderBean> copyListBean = listBean;
        Collections.copy(copyListBean, listBean);
        return copyListBean;
    }

    public void setListBean(List<HeaderBean> listBean) {
        List<HeaderBean> copyListBean = new ArrayList<HeaderBean>();
        copyListBean.addAll(listBean);
        this.listBean = copyListBean;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getImageAlt() { return imageAlt; }

    public void setImageAlt(String imageAlt) { this.imageAlt = imageAlt; }

    public Boolean getSecondLevelOnly() { return secondLevelOnly; }

    public void setSecondLevelOnly(Boolean secondLevelOnly) { this.secondLevelOnly = secondLevelOnly; }

}
