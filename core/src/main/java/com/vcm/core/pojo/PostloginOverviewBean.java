package com.vcm.core.pojo;

public class PostloginOverviewBean {
    private String HNW_WealthAdvisor_bgImage_Desktop;
    private String HNW_WealthAdvisor_bgImage_Mobile;
    private String HNW_WealthAdvisor_bgImage_Tab;
    private PostloginOverviewLeftBean left;
    private PostloginOverviewRightBean right;

    public PostloginOverviewBean(){
        this.right=new PostloginOverviewRightBean();
        this.left=new PostloginOverviewLeftBean();
    }

    public String getHNW_WealthAdvisor_bgImage_Desktop() {
        return HNW_WealthAdvisor_bgImage_Desktop;
    }

    public void setHNW_WealthAdvisor_bgImage_Desktop(String HNW_WealthAdvisor_bgImage_Desktop) {
        this.HNW_WealthAdvisor_bgImage_Desktop = HNW_WealthAdvisor_bgImage_Desktop;
    }

    public String getHNW_WealthAdvisor_bgImage_Mobile() {
        return HNW_WealthAdvisor_bgImage_Mobile;
    }

    public void setHNW_WealthAdvisor_bgImage_Mobile(String HNW_WealthAdvisor_bgImage_Mobile) {
        this.HNW_WealthAdvisor_bgImage_Mobile = HNW_WealthAdvisor_bgImage_Mobile;
    }

    public PostloginOverviewLeftBean getLeft() {
        return left;
    }

    public void setLeft(PostloginOverviewLeftBean left) {
        this.left = left;
    }

    public PostloginOverviewRightBean getRight() {
        return right;
    }

    public void setRight(PostloginOverviewRightBean right) {
        this.right = right;
    }

    public String getHNW_WealthAdvisor_bgImage_Tab() {
        return HNW_WealthAdvisor_bgImage_Tab;
    }

    public void setHNW_WealthAdvisor_bgImage_Tab(String HNW_WealthAdvisor_bgImage_Tab) {
        this.HNW_WealthAdvisor_bgImage_Tab = HNW_WealthAdvisor_bgImage_Tab;
    }
}