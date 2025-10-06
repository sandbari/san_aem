package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostloginDashboardMarketingContentBean {

    private List<PostloginMarketingPhotoBean> marketing_photos_bottom;
    private PostloginOverviewBean overview;
    private PostloginFundBean funds;
    private PostloginMarketBean markets;
    private PostloginEducationBean education;
    private PostLoginDynamicAdvisorBean dynamicAdvisor;


    public PostloginDashboardMarketingContentBean() {
        this.marketing_photos_bottom = new ArrayList<>();
        this.overview = new PostloginOverviewBean();
        this.funds = new PostloginFundBean();
        this.markets = new PostloginMarketBean();
        this.education = new PostloginEducationBean();
        this.dynamicAdvisor = new PostLoginDynamicAdvisorBean();
    }

    public List<PostloginMarketingPhotoBean> getMarketing_photos_bottom() {
        List<PostloginMarketingPhotoBean> copyMarketing_photos_bottom = marketing_photos_bottom;
        Collections.copy(copyMarketing_photos_bottom, marketing_photos_bottom);
        return copyMarketing_photos_bottom;
    }

    public void setMarketing_photos_bottom(List<PostloginMarketingPhotoBean> marketing_photos_bottom) {
        List<PostloginMarketingPhotoBean> copyMarketing_photos_bottom = new ArrayList<>();
        copyMarketing_photos_bottom.addAll(marketing_photos_bottom);
        this.marketing_photos_bottom = copyMarketing_photos_bottom;
    }

    public PostloginOverviewBean getOverview() {
        return overview;
    }

    public void setOverview(PostloginOverviewBean overview) {
        this.overview = overview;
    }

    public PostloginFundBean getFunds() {
        return funds;
    }

    public void setFunds(PostloginFundBean funds) {
        this.funds = funds;
    }

    public PostloginMarketBean getMarkets() {
        return markets;
    }

    public void setMarkets(PostloginMarketBean markets) {
        this.markets = markets;
    }

    public PostloginEducationBean getEducation() {
        return education;
    }

    public void setEducation(PostloginEducationBean education) {
        this.education = education;
    }

    public PostLoginDynamicAdvisorBean getDynamicAdvisor() {
        return dynamicAdvisor;
    }

    public void setDynamicAdvisor(PostLoginDynamicAdvisorBean dynamicAdvisor) {
        this.dynamicAdvisor = dynamicAdvisor;
    }
}