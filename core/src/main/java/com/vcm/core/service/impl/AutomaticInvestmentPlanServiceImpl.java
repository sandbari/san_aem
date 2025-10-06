package com.vcm.core.service.impl;

import com.google.gson.Gson;
import com.vcm.core.common.MockJsonUtility;
import com.vcm.core.pojo.AutomaticInvestmentPlanBean;
import com.vcm.core.service.AutomaticInvestmentPlanService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.concurrent.atomic.AtomicReference;

@Component(service = AutomaticInvestmentPlanService.class, immediate = true)
public class AutomaticInvestmentPlanServiceImpl implements AutomaticInvestmentPlanService {

    AutomaticInvestmentPlanBean automaticInvestmentPlanBean = null;

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticInvestmentPlanServiceImpl.class);

    private String RESPONSIVEGRID = "root/responsivegrid/";

    private String COMPONENT_NAME = "vcm/components/content/automaticinvestmentplan";

    private String bannerPath;

    private String bannerRootpath;

    @Override
    public String getAIPContent(SlingHttpServletRequest request) throws RepositoryException {
        LOG.info("AutomaticInvestmentPlanServiceImpl : getDashboardMarketingContent() method");
        automaticInvestmentPlanBean = new AutomaticInvestmentPlanBean();

        getBannerConfig();
        Resource root = request.getResourceResolver().getResource(bannerPath.trim());
        if (root.hasChildren()) {
            Iterable<Resource> resources = root.getChildren();
            for (Resource resource : resources) {
                Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID.trim()).getChildren();
                AtomicReference<Resource> componentResource = new AtomicReference<>();
                componentResources.forEach(resource1 -> {
                    if (resource1.isResourceType(COMPONENT_NAME.trim())) {
                        componentResource.set(resource1);
                    }
                });
                if (componentResource.get().isResourceType(COMPONENT_NAME.trim())) {
                    Node node = componentResource.get().adaptTo(Node.class);
                    LOG.info("Node details {}", node);
                    if (node.hasProperty("webContent")) {
                        String contentStr= node.getProperty("webContent").getString();
                        LOG.info("contentStr: web "+ contentStr);
                        automaticInvestmentPlanBean.setContentAIP(MockJsonUtility.getRemoveScriptTagStr(node.getProperty("webContent").getString()));
                        LOG.info("contentStr: web "+ automaticInvestmentPlanBean.getContentAIP());
                    } else {
                        automaticInvestmentPlanBean.setContentAIP("");
                    }

                    if (node.hasProperty("mobContent")) {
                        String contentStr= node.getProperty("mobContent").getString();
                        LOG.info("contentStr: Mobile "+ contentStr);
                        automaticInvestmentPlanBean.setMobContentAIP(MockJsonUtility.getRemoveScriptTagStr(node.getProperty("mobContent").getString()));
                        LOG.info("contentStr: Mobile "+ automaticInvestmentPlanBean.getMobContentAIP());
                    } else {
                        automaticInvestmentPlanBean.setMobContentAIP("");
                    }

                    if (node.hasProperty("mobLink")) {
                        automaticInvestmentPlanBean.setMobLink(node.getProperty("mobLink").getString());
                    } else {
                        automaticInvestmentPlanBean.setMobLink("");
                    }
                }

            }
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(automaticInvestmentPlanBean);
        return jsonString;

    }

    public void getBannerConfig() {
        LOG.info("AutomaticInvestm entPlanServiceImpl:getBannerConfig() method");
        bannerRootpath = "/content/vcm/language-masters/en/automatic-investment-plan";
        LOG.info("ENTRY getPostloginBannerDetails bannerRootpath {}", bannerRootpath);
        bannerPath = "/content/vcm/us/en/automatic-investment-plan";
        LOG.info("ENTRY getPostloginBannerDetails bannerPath {}", bannerPath);
    }
}
