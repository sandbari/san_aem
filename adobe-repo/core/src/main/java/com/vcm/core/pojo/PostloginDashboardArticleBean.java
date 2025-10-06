package com.vcm.core.pojo;

public class PostloginDashboardArticleBean {
    private PostloginDashboardMarketingContentBean dashboard_marketing_content;

    public PostloginDashboardArticleBean(){
        this.dashboard_marketing_content= new PostloginDashboardMarketingContentBean();
    }
    public PostloginDashboardMarketingContentBean getDashboard_marketing_content() {
        return dashboard_marketing_content;
    }

    public void setDashboard_marketing_content(PostloginDashboardMarketingContentBean dashboard_marketing_content) {
        this.dashboard_marketing_content = dashboard_marketing_content;
    }
}