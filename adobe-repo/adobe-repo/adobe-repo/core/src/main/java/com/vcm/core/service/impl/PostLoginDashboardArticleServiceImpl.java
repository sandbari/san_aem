package com.vcm.core.service.impl;

import com.google.gson.Gson;
import com.vcm.core.pojo.*;
import com.vcm.core.service.PostLoginDashboardArticleService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component(service = PostLoginDashboardArticleService.class, immediate = true)
public class PostLoginDashboardArticleServiceImpl implements PostLoginDashboardArticleService {

    private static final Logger LOG = LoggerFactory.getLogger(PostLoginDashboardArticleServiceImpl.class);

    private String RESPONSIVEGRID = "root/responsivegrid/";
    private String COMPONENT_NAME = "vcm/components/content/postlogindashboardarticles";

    private String bannerPath;
    private String bannerRootpath;

    PostloginDashboardArticleBean postloginDashboardArticleBean= new PostloginDashboardArticleBean();
    PostloginDashboardMarketingContentBean postloginDashboardMarketingContentBean =
            postloginDashboardArticleBean.getDashboard_marketing_content();

    @Override
    public String getDashboardMarketingContent(SlingHttpServletRequest request) throws RepositoryException {
        LOG.info("DashboardMarketingContentServiceImpl : getDashboardMarketingContent() method");
        getBannerConfig();
        Resource root;
        if(null != request.getResourceResolver().getResource(bannerPath.trim())){
           root = request.getResourceResolver().getResource(bannerPath.trim());
        }else{
            root = request.getResourceResolver().getResource(bannerPath.trim().concat("s"));
        }
        if (root.hasChildren()) {
            Iterable<Resource> resources = root.getChildren();
            for (Resource resource : resources) {
                Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID.trim()).getChildren();
                AtomicReference<Resource> componentResource=new AtomicReference<>();
                componentResources.forEach(resource1 -> {
                    if(resource1.isResourceType(COMPONENT_NAME.trim())){
                        componentResource.set(resource1);
                    }
                });

                if (componentResource.get().isResourceType(COMPONENT_NAME.trim())) {
                    Node node = componentResource.get().adaptTo(Node.class);
                    LOG.info("Node details {}", node);
                    // Overview Left object details
                    getOverviewDetails(node);
                    // Overview Left object details
                    getOverviewLeftDetails(node);
                    // Overview Right object details
                    getOverviewRightDetails(node);
                    // Fund Object Details
                    getFundDetails(node);
                    // Market object details
                    getMarketDetails(node);
                    // Educational object details
                    getEductionDetails(node);
                    //Market photos bottom object details
                    getMarketPhotoBtmDetail(node);
                    //Dynamic Advisor object details
                    getDynamicAdvisorDetail(node);
                }
            }
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(postloginDashboardArticleBean);
        LOG.info("result {}",jsonString);
        return jsonString.replaceAll("\\\\r\\\\n", "");
    }

    private void getOverviewDetails(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getOverviewDetails() method");
        PostloginOverviewBean postloginOverviewBean =postloginDashboardMarketingContentBean.getOverview();
        if(node.hasProperty("welathAdvisorbgImageURl")) {
            postloginOverviewBean.setHNW_WealthAdvisor_bgImage_Desktop(node.getProperty("welathAdvisorbgImageURl").getString());
        }else{
            postloginOverviewBean.setHNW_WealthAdvisor_bgImage_Desktop("");
        }
        if(node.hasProperty("wealthAdvisorMobileBgImageURl")) {
            postloginOverviewBean.setHNW_WealthAdvisor_bgImage_Mobile(node.getProperty("wealthAdvisorMobileBgImageURl").getString());
        }else{
            postloginOverviewBean.setHNW_WealthAdvisor_bgImage_Mobile("");
        }
        if(node.hasProperty("wealthAdvisorTabBgImageURl")) {
            postloginOverviewBean.setHNW_WealthAdvisor_bgImage_Tab(node.getProperty("wealthAdvisorTabBgImageURl").getString());
        }else{
            postloginOverviewBean.setHNW_WealthAdvisor_bgImage_Tab("");
        }
    }

    private void getOverviewLeftDetails(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getOverviewLeftDetails() method");
        PostloginOverviewLeftBean postloginOverviewLeftBean =postloginDashboardMarketingContentBean.getOverview().getLeft();
        if(node.hasProperty("overviewLeftTitle")) {
            postloginOverviewLeftBean.setTitle(node.getProperty("overviewLeftTitle").getString());
        }else{
            postloginOverviewLeftBean.setTitle("");
        }
        if(node.hasProperty("overviewLeftDescription")) {
            postloginOverviewLeftBean.setDescription(node.getProperty("overviewLeftDescription").getString());
        }else{
            postloginOverviewLeftBean.setDescription("");
        }
    }

    private void getOverviewRightDetails(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getOverviewRightDetails() method");
        PostloginOverviewRightBean postloginOverviewRightBean =
                postloginDashboardMarketingContentBean.getOverview().getRight();
        if(node.hasProperty("overviewRightContentTitle")) {
            postloginOverviewRightBean.setTitle(node.getProperty("overviewRightContentTitle").getString());
        }else{
            postloginOverviewRightBean.setTitle("");
        }
        if(node.hasProperty("overviewRightContentDescription")) {
            postloginOverviewRightBean.setDescription(node.getProperty("overviewRightContentDescription").getString());
        }else{
            postloginOverviewRightBean.setDescription("");
        }
        if(node.hasProperty("btnText")) {
            postloginOverviewRightBean.setButtonText(node.getProperty("btnText").getString());
        }else{
            postloginOverviewRightBean.setButtonText("");
        }
        if(node.hasProperty("btnActionLink")) {
            postloginOverviewRightBean.setButtonLink(node.getProperty("btnActionLink").getString());
        }else{
            postloginOverviewRightBean.setButtonLink("");
        }
        if(node.hasProperty("overviewRightContentBgImageURl")) {
            postloginOverviewRightBean.setBgURL_Desktop(node.getProperty("overviewRightContentBgImageURl").getString());
        }else{
            postloginOverviewRightBean.setBgURL_Desktop("");
        }
        if(node.hasProperty("overviewRightContentMobileBgImageURl")) {
            postloginOverviewRightBean.setBgURL_Mobile(node.getProperty("overviewRightContentMobileBgImageURl").getString());
        }else {
            postloginOverviewRightBean.setBgURL_Mobile("");
        }
        if(node.hasProperty("overviewRightContentTabBgImageURl")) {
            postloginOverviewRightBean.setBgURL_Tab(node.getProperty("overviewRightContentTabBgImageURl").getString());
        }else {
            postloginOverviewRightBean.setBgURL_Tab("");
        }
    }

    private void getMarketPhotoBtmDetail(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getMarketPhotoBtmDetail() method");
        List<PostloginMarketingPhotoBean> marketingPhotoBeanList = new ArrayList<>();
        PostloginMarketingPhotoBean orderBean=null;
        if (node.hasProperty("marketPhotoOrderOneTitle") || node.hasProperty("marketPhotoOrderOneRedirectLink") ||
                node.hasProperty("marketPhotoOrderOneBgImageURl") || node.hasProperty("marketPhotoOrderOneMobileBgImageURl")) {
            orderBean = new PostloginMarketingPhotoBean();
            orderBean.setOrder(1);
            if(node.hasProperty("marketPhotoOrderOneTitle")) {
                orderBean.setTitle(node.getProperty("marketPhotoOrderOneTitle").getString());
            }else{
                orderBean.setTitle("");
            }
            if(node.hasProperty("marketPhotoOrderOneRedirectLink")) {
                orderBean.setRedirectLink(node.getProperty("marketPhotoOrderOneRedirectLink").getString());
            }else {
                orderBean.setRedirectLink("");
            }
            if(node.hasProperty("marketPhotoOrderOneBgImageURl")) {
                orderBean.setBgURL_Desktop(node.getProperty("marketPhotoOrderOneBgImageURl").getString());
            }else{
                orderBean.setBgURL_Desktop("");
            }
            if(node.hasProperty("marketPhotoOrderOneMobileBgImageURl")) {
                orderBean.setBgURL_Mobile(node.getProperty("marketPhotoOrderOneMobileBgImageURl").getString());
            }else {
                orderBean.setBgURL_Mobile("");
            }
            if(node.hasProperty("marketPhotoOrderOneTabBgImageURl")) {
                orderBean.setBgURL_Tab(node.getProperty("marketPhotoOrderOneTabBgImageURl").getString());
            }else {
                orderBean.setBgURL_Tab("");
            }
            if(node.hasProperty("marketPhotoOrderOneDescription")) {
                orderBean.setDescription(node.getProperty("marketPhotoOrderOneDescription").getString());
            }else {
                orderBean.setDescription("");
            }
            if(node.hasProperty("marketPhotoOrderOneBtnText")) {
                orderBean.setBtnText(node.getProperty("marketPhotoOrderOneBtnText").getString());
            }else {
                orderBean.setBtnText("");
            }
            marketingPhotoBeanList.add(orderBean);
        }

        if (node.hasProperty("marketPhotoOrderTwoTitle") || node.hasProperty("marketPhotoOrderTwoRedirectLink")||
                node.hasProperty("marketPhotoOrderTwoBgImageURl") || node.hasProperty("marketPhotoOrderTwoMobileBgImageURl")) {
            orderBean = new PostloginMarketingPhotoBean();
            orderBean.setOrder(2);
            if (node.hasProperty("marketPhotoOrderTwoTitle")) {
                orderBean.setTitle(node.getProperty("marketPhotoOrderTwoTitle").getString());
            }else{
                orderBean.setTitle("");
            }
            if (node.hasProperty("marketPhotoOrderTwoRedirectLink")){
                orderBean.setRedirectLink(node.getProperty("marketPhotoOrderTwoRedirectLink").getString());
            }else{
                orderBean.setRedirectLink("");
            }
            if(node.hasProperty("marketPhotoOrderTwoBgImageURl")){
                orderBean.setBgURL_Desktop(node.getProperty("marketPhotoOrderTwoBgImageURl").getString());
            }else{
                orderBean.setBgURL_Desktop("");
            }
            if(node.hasProperty("marketPhotoOrderTwoMobileBgImageURl")) {
                orderBean.setBgURL_Mobile(node.getProperty("marketPhotoOrderTwoMobileBgImageURl").getString());
            }else {
                orderBean.setBgURL_Mobile("");
            }
            if(node.hasProperty("marketPhotoOrderTwoTabBgImageURl")) {
                orderBean.setBgURL_Tab(node.getProperty("marketPhotoOrderTwoTabBgImageURl").getString());
            }else {
                orderBean.setBgURL_Tab("");
            }
            if(node.hasProperty("marketPhotoOrderTwoDescription")) {
                orderBean.setDescription(node.getProperty("marketPhotoOrderTwoDescription").getString());
            }else {
                orderBean.setDescription("");
            }
            if(node.hasProperty("marketPhotoOrderTwoBtnText")) {
                orderBean.setBtnText(node.getProperty("marketPhotoOrderTwoBtnText").getString());
            }else {
                orderBean.setBtnText("");
            }
            marketingPhotoBeanList.add(orderBean);
        }

        if (node.hasProperty("marketPhotoOrderThreeTitle") || node.hasProperty("marketPhotoOrderThreeRedirectLink") ||
                node.hasProperty("marketPhotoOrderThreeBgImageURl") || node.hasProperty("marketPhotoOrderThreeMobileBgImageURl")) {
            orderBean = new PostloginMarketingPhotoBean();
            orderBean.setOrder(3);
            if(node.hasProperty("marketPhotoOrderThreeTitle")) {
                orderBean.setTitle(node.getProperty("marketPhotoOrderThreeTitle").getString());
            }else {
                orderBean.setTitle("");
            }
            if(node.hasProperty("marketPhotoOrderThreeRedirectLink")) {
                orderBean.setRedirectLink(node.getProperty("marketPhotoOrderThreeRedirectLink").getString());
            }else {
                orderBean.setRedirectLink("");
            }
            if(node.hasProperty("marketPhotoOrderThreeBgImageURl")) {
                orderBean.setBgURL_Desktop(node.getProperty("marketPhotoOrderThreeBgImageURl").getString());
            }else {
                orderBean.setBgURL_Desktop("");
            }
            if(node.hasProperty("marketPhotoOrderThreeMobileBgImageURl")) {
                orderBean.setBgURL_Mobile(node.getProperty("marketPhotoOrderThreeMobileBgImageURl").getString());
            }else {
                orderBean.setBgURL_Mobile("");
            }
            if(node.hasProperty("marketPhotoOrderThreeTabBgImageURl")) {
                orderBean.setBgURL_Tab(node.getProperty("marketPhotoOrderThreeTabBgImageURl").getString());
            }else {
                orderBean.setBgURL_Tab("");
            }
            if(node.hasProperty("marketPhotoOrderThreeDescription")) {
                orderBean.setDescription(node.getProperty("marketPhotoOrderThreeDescription").getString());
            }else {
                orderBean.setDescription("");
            }
            if(node.hasProperty("marketPhotoOrderThreeBtnText")) {
                orderBean.setBtnText(node.getProperty("marketPhotoOrderThreeBtnText").getString());
            }else {
                orderBean.setBtnText("");
            }
            marketingPhotoBeanList.add(orderBean);
        }
        postloginDashboardMarketingContentBean.setMarketing_photos_bottom(marketingPhotoBeanList);
    }

    private void getEductionDetails(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getEductionDetails() method");
        PostloginEducationBean postloginEducationBean = postloginDashboardMarketingContentBean.getEducation();
        if(node.hasProperty("educationTitle")) {
            postloginEducationBean.setTitle(node.getProperty("educationTitle").getString());
        }else {
            postloginEducationBean.setTitle("");
        }
        if(node.hasProperty("educationDescription")){
            postloginEducationBean.setDescription(node.getProperty("educationDescription").getString());
        }else {
            postloginEducationBean.setDescription("");
        }
        if(node.hasProperty("edBgImageURl")) {
            postloginEducationBean.setBgURL_Desktop(node.getProperty("edBgImageURl").getString());
        }else {
            postloginEducationBean.setBgURL_Desktop("");
        }
        if(node.hasProperty("edMobileBgImageURl")) {
            postloginEducationBean.setBgURL_Mobile(node.getProperty("edMobileBgImageURl").getString());
        }else {
            postloginEducationBean.setBgURL_Mobile("");
        }
        if(node.hasProperty("edTabBgImageURl")) {
            postloginEducationBean.setBgURL_Tab(node.getProperty("edTabBgImageURl").getString());
        }else {
            postloginEducationBean.setBgURL_Tab("");
        }
    }

    private void getMarketDetails(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getMarketDetails() method");
        PostloginMarketBean postloginMarketBean = postloginDashboardMarketingContentBean.getMarkets();
        if(node.hasProperty("marketTitle")) {
            postloginMarketBean.setTitle(node.getProperty("marketTitle").getString());
        }else {
            postloginMarketBean.setTitle("");
        }
        if(node.hasProperty("marketDescription")) {
            postloginMarketBean.setDescription(node.getProperty("marketDescription").getString());
        }else {
            postloginMarketBean.setDescription("");
        }
        if(node.hasProperty("marketBgImageURl")) {
            postloginMarketBean.setBgURL_Desktop(node.getProperty("marketBgImageURl").getString());
        }else {
            postloginMarketBean.setBgURL_Desktop("");
        }
        if(node.hasProperty("marketMobileBgImageURl")){
            postloginMarketBean.setBgURL_Mobile(node.getProperty("marketMobileBgImageURl").getString());
        }else {
            postloginMarketBean.setBgURL_Mobile("");
        }
        if(node.hasProperty("marketTabBgImageURl")){
            postloginMarketBean.setBgURL_Tab(node.getProperty("marketTabBgImageURl").getString());
        }else {
            postloginMarketBean.setBgURL_Tab("");
        }
    }

    private void getFundDetails(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getFundDetails() method");
        PostloginFundBean postloginFundBean = postloginDashboardMarketingContentBean.getFunds();
        if (node.hasProperty("fundTitle")) {
            postloginFundBean.setTitle(node.getProperty("fundTitle").getString());
        }else {
            postloginFundBean.setTitle("");
        }
        if (node.hasProperty("fundDescription")) {
            postloginFundBean.setDescription(node.getProperty("fundDescription").getString());
        }else {
            postloginFundBean.setDescription("");
        }
        if (node.hasProperty("fundBgImageURl")) {
            postloginFundBean.setBgURL_Desktop(node.getProperty("fundBgImageURl").getString());
        }else {
            postloginFundBean.setBgURL_Desktop("");
        }
        if (node.hasProperty("fundMobileBgImageURl")){
            postloginFundBean.setBgURL_Mobile(node.getProperty("fundMobileBgImageURl").getString());
        }else {
            postloginFundBean.setBgURL_Mobile("");
        }

        if (node.hasProperty("fundTabBgImageURl")){
            postloginFundBean.setBgURL_Tab(node.getProperty("fundTabBgImageURl").getString());
        }else {
            postloginFundBean.setBgURL_Tab("");
        }
    }

    private void getDynamicAdvisorDetail(Node node) throws RepositoryException {
        LOG.info("PostloginDashboardArticleServiceImple:getDynamicAdvisorDetail() method");
        PostLoginDynamicAdvisorBean postLoginDynamicAdvisorBean = postloginDashboardMarketingContentBean.getDynamicAdvisor();
        if(node.hasProperty("dynamicAdvisorTitle")) {
            postLoginDynamicAdvisorBean.setTitle(node.getProperty("dynamicAdvisorTitle").getString());
        }else {
            postLoginDynamicAdvisorBean.setTitle("");
        }
        if(node.hasProperty("dynamicAdvisorDescription")){
            postLoginDynamicAdvisorBean.setDescription(node.getProperty("dynamicAdvisorDescription").getString());
        }else {
            postLoginDynamicAdvisorBean.setDescription("");
        }
        if(node.hasProperty("dynamicAdvisorBgImageURl")) {
            postLoginDynamicAdvisorBean.setBgURL_Desktop(node.getProperty("dynamicAdvisorBgImageURl").getString());
        }else {
            postLoginDynamicAdvisorBean.setBgURL_Desktop("");
        }
        if(node.hasProperty("dynamicAdvisorMobileBgImageURl")) {
            postLoginDynamicAdvisorBean.setBgURL_Mobile(node.getProperty("dynamicAdvisorMobileBgImageURl").getString());
        }else {
            postLoginDynamicAdvisorBean.setBgURL_Mobile("");
        }
        if(node.hasProperty("dynamicAdvisorTabBgImageURl")) {
            postLoginDynamicAdvisorBean.setBgURL_Tab(node.getProperty("dynamicAdvisorTabBgImageURl").getString());
        }else {
            postLoginDynamicAdvisorBean.setBgURL_Tab("");
        }
    }
    public void getBannerConfig() {
        LOG.info("PostloginDashboardArticleServiceImple:getBannerConfig() method");
        bannerRootpath = "/content/vcm/language-masters/en/postlogin-dashboard-article";
        LOG.info("ENTRY getPostloginBannerDetails bannerRootpath {}", bannerRootpath);
        bannerPath = "/content/vcm/us/en/postlogin-dashboard-article";
        LOG.info("ENTRY getPostloginBannerDetails bannerPath {}", bannerPath);
    }
}