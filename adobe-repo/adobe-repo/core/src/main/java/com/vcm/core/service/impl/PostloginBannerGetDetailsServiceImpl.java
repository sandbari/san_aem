package com.vcm.core.service.impl;

import com.google.gson.Gson;
import com.vcm.core.pojo.PostloginBannerGetDetailsBean;
import com.vcm.core.service.PostloginBannerGetDetailsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component(service = PostloginBannerGetDetailsService.class, immediate = true)
public class PostloginBannerGetDetailsServiceImpl implements PostloginBannerGetDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostloginBannerGetDetailsServiceImpl.class);

    private String RESPONSIVEGRID = "root/responsivegrid/";

    private String COMPONENT_NAME = "vcm/components/content/postloginbanner";

    private String bannerRootpath;

    private String bannerPath;

    private int bannerCount;

//	@Reference
//	private VCMSiteConfiguationService vcmSiteConfigService;

    @Override
    public String getPostloginBannerDetails(SlingHttpServletRequest request) throws RepositoryException, ParseException {
        getBannerConfig();
        String jsonString;
        List<PostloginBannerGetDetailsBean> bannerBeanList = new ArrayList<>();
        int count = 1;
        LOGGER.debug("ENTRY getPostloginBannerDetails request {}", request);
        Resource root = request.getResourceResolver().getResource(bannerPath);
        LOGGER.debug("ENTRY getPostloginBannerDetails root {}", root);
        if (root.hasChildren()) {
            Iterable<Resource> resources = root.getChildren();
            LOGGER.debug("ENTRY getPostloginBannerDetails resources {}", resources);
            for (Resource resource : resources) {
                Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID).getChildren();
                LOGGER.debug("ENTRY getPostloginBannerDetails root {}", root);
                for (Resource componentResource : componentResources) {
                    if (componentResource.isResourceType(COMPONENT_NAME)) {
                        PostloginBannerGetDetailsBean PostloginBannerGetDetailsBean = new PostloginBannerGetDetailsBean();
                        Node node = componentResource.adaptTo(Node.class);
                        if(node.hasProperty("banners") && node.getProperty("banners").getString().equals("Dashboard Banner")){
                            if (node.hasProperty("enabledBanner") && node.getProperty("enabledBanner").getString().equals("true")) {
                                if (node.hasProperty("startDate") && node.hasProperty("endDate")) {
                                    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                                    Date startDate = removeTime(dateFormat.parse(node.getProperty("startDate").getString()));
                                    LOGGER.debug("endDate :{}", startDate);
                                    Date endDate =  removeTime(dateFormat.parse(node.getProperty("endDate").getString()));
                                    LOGGER.debug("endDate :{}", endDate);
                                    Date todayDate= removeTime(new Date());
                                    LOGGER.debug("todayDate :{}", todayDate);
                                    if(todayDate.after(startDate) && todayDate.before(endDate)) {
                                        LOGGER.debug("between :{}", todayDate);
                                        String id = "Banner" + count++;
                                        PostloginBannerGetDetailsBean.setStartDate(node.getProperty("startDate").getString());
                                        PostloginBannerGetDetailsBean.setEndDate(node.getProperty("endDate").getString());

                                        PostloginBannerGetDetailsBean.setBannerId(id);
                                        if (node.hasProperty("caption")) {
                                            PostloginBannerGetDetailsBean.setBannerCaption(node.getProperty("caption").getString());
                                        }
                            /*if (node.hasProperty("message")) {
                                PostloginBannerGetDetailsBean.setBannerMessage(node.getProperty("message").getString());
                            }*/
                                        if (node.hasProperty("startDate")) {
                                            PostloginBannerGetDetailsBean.setStartDate(node.getProperty("startDate").getString());
                                        }
                                        if (node.hasProperty("endDate")) {
                                            PostloginBannerGetDetailsBean.setEndDate(node.getProperty("endDate").getString());
                                        }
                                        if (node.hasProperty("type")) {
                                            PostloginBannerGetDetailsBean.setType(node.getProperty("type").getString());
                                            if (node.getProperty("type").getString().equalsIgnoreCase("alert")) {
                                                if (node.hasProperty("iconUrl")) {
                                                    PostloginBannerGetDetailsBean.setIconUrl(node.getProperty("iconUrl").getString());
                                                }else {
                                                    PostloginBannerGetDetailsBean.setIconUrl("/content/dam/vcm/dashboard-banner/icon-alert@2x.png");
                                                }
                                            }
                                            if (node.getProperty("type").getString().equalsIgnoreCase("information")) {
                                                if (node.hasProperty("iconUrl")) {
                                                    PostloginBannerGetDetailsBean.setIconUrl(node.getProperty("iconUrl").getString());
                                                }else {
                                                    PostloginBannerGetDetailsBean.setIconUrl("/content/dam/vcm/dashboard-banner/icon-info@2x.png");
                                                }
                                            }
                                            if (node.getProperty("type").getString().equalsIgnoreCase("marketing")) {
                                                if(node.hasProperty("bgImageURl")){
                                                    PostloginBannerGetDetailsBean.setBgImageURl(node.getProperty("bgImageURl").getString());
                                                }else{
                                                    PostloginBannerGetDetailsBean.setBgImageURl("/content/dam/vcm/herobanner/redesign/Crypto_header.png");

                                                }
                                                if(node.hasProperty("bgTextureImageURL")){
                                                    PostloginBannerGetDetailsBean.setBgTextureImageURL(node.getProperty("bgTextureImageURL").getString());
                                                }else{
                                                    PostloginBannerGetDetailsBean.setBgTextureImageURL("/content/dam/vcm/herobanner/redesign/corporate_big.png");
                                                }
                                                if(node.hasProperty("mobileBgTextureImageURL")){
                                                    PostloginBannerGetDetailsBean.setMobileBgTextureImageURL(node.getProperty("mobileBgTextureImageURL").getString());
                                                }
                                                if(node.hasProperty("mobileBgImageURl")){
                                                    PostloginBannerGetDetailsBean.setMobileBgImageURl(node.getProperty("mobileBgImageURl").getString());
                                                }
                                                if(node.hasProperty("btnText")){
                                                    PostloginBannerGetDetailsBean.setBtnText(node.getProperty("btnText").getString());
                                                }
                                                if(node.hasProperty("btnActionLink")){
                                                    PostloginBannerGetDetailsBean.setBtnActionLink(node.getProperty("btnActionLink").getString());
                                                }else{
                                                    PostloginBannerGetDetailsBean.setBtnActionLink("/content/dam/vcm/dashboard-banner/icon-info@2x.png");
                                                }
                                            }
                                        }

                                        if (node.hasProperty("priority")) {
                                            PostloginBannerGetDetailsBean.setPriority(node.getProperty("priority").getString());
                                        }
                                        if (node.hasProperty("usertype")) {
                                            PostloginBannerGetDetailsBean.setUserType(node.getProperty("usertype").getString());
                                        }
                                        LOGGER.debug("ENTRY  desktopHeading  {}", node.hasProperty("desktopHeading"));
                                        if (node.hasProperty("desktopHeading")) {
                                            PostloginBannerGetDetailsBean.setBannerDesktopHeading(node.getProperty("desktopHeading").getString());
                                        }
                                        LOGGER.debug("ENTRY desktopMessage {}", node.hasProperty("desktopMessage"));
                                        if (node.hasProperty("desktopMessage")) {
                                            PostloginBannerGetDetailsBean.setBannerDesktopMessage(node.getProperty("desktopMessage").getString());
                                        }
                                        if (node.hasProperty("mobileMessage")) {
                                            PostloginBannerGetDetailsBean.setBannerMobileMessage(node.getProperty("mobileMessage").getString());
                                        }
                                        LOGGER.debug("ENTRY  root {}", node.hasProperty("mobileHeading"));
                                        if (node.hasProperty("mobileHeading")) {
                                            PostloginBannerGetDetailsBean.setBannerMobileHeading(node.getProperty("mobileHeading").getString());
                                        }
                                        LOGGER.debug("ENTRY  timeDuration {}", node.hasProperty("timeDuration"));
                                        if (node.hasProperty("timeDuration")) {
                                            PostloginBannerGetDetailsBean.setTimeDuration(node.getProperty("timeDuration").getString());
                                        }

                                        if (node.hasProperty("mobileHeadingApp")) {
                                            PostloginBannerGetDetailsBean.setBannerMobileHeading_app(node.getProperty("mobileHeadingApp").getString());
                                        }

                                        if (node.hasProperty("mobileAppMessage")) {
                                            PostloginBannerGetDetailsBean.setBannerMobileMessage_app(node.getProperty("mobileAppMessage").getString());
                                        }

                                        if (node.hasProperty("linkType")) {
                                            PostloginBannerGetDetailsBean.setLinkType(node.getProperty("linkType").getString());
                                        }

                                        if (node.hasProperty("mobileDashboardScreen")) {
                                            PostloginBannerGetDetailsBean.setMobileDashboardScreen(node.getProperty("mobileDashboardScreen").getString());
                                        }

                                        bannerBeanList.add(PostloginBannerGetDetailsBean);
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        Gson gson = new Gson();
        jsonString = gson.toJson(bannerBeanList);
        //jsonString.replace("<br/>","");
        //LOGGER.debug("Json response "+ jsonString);
        return jsonString.replaceAll("\\\\r\\\\n", "");
    }

    @Override
    public String getActivePostloginBanner(SlingHttpServletRequest request) throws RepositoryException {
        getBannerConfig();
        int count = 0;
        Resource root = request.getResourceResolver().getResource(bannerRootpath);
        if (root.hasChildren()) {
            Iterable<Resource> resources = root.getChildren();
            for (Resource resource : resources) {
                Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID).getChildren();
                for (Resource componentResource : componentResources) {
                    if (componentResource.isResourceType(COMPONENT_NAME)) {
                        Node node = componentResource.adaptTo(Node.class);
                        if (node.hasProperty("enabledBanner") && node.getProperty("enabledBanner").getString().equals("true")) {
                            count++;
                        }

                    }
                }
            }
        }
        /*if (count >= bannerCount) {
            return "true";
        } else {
            return "false";
        }*/
        return "false";
    }

    public void getBannerConfig() {
        //Map<String, Object> bannerConfig = vcmSiteConfigService.getLabelConfigAsMap();
        //if(null != bannerConfig && !bannerConfig.isEmpty()) {
        bannerRootpath = "/content/vcm/language-masters" + "/en/postlogin-banner-config";
        LOGGER.debug("ENTRY getPostloginBannerDetails bannerRootpath {}", bannerRootpath);
        bannerPath = "/content/vcm/us" + "/en/postlogin-banner-config";
        LOGGER.debug("ENTRY getPostloginBannerDetails bannerPath {}", bannerPath);
        bannerCount = 4;
        LOGGER.debug("ENTRY getPostloginBannerDetails bannerCount {}", bannerCount);
        //}
    }

    private static Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}