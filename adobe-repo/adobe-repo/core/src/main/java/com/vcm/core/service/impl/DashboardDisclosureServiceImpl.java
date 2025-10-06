package com.vcm.core.service.impl;

import com.google.gson.Gson;
import com.vcm.core.pojo.DashboardDisclosureTextBean;
import com.vcm.core.service.DashboardDisclosureService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

@Component(service = DashboardDisclosureService.class, immediate = true)
public class DashboardDisclosureServiceImpl implements DashboardDisclosureService {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardDisclosureServiceImpl.class);

    private static final String DASHBOARD_WED_DISCLOSURE_PATH = "/content/dam/vcm/vcm-content-fragments/web-disclosure-library/dashboard-disclosure-web/jcr:content/data/master";
    private static final String DASHBOARD_MOB_DISCLOSURE_PATH = "/content/dam/vcm/vcm-content-fragments/web-disclosure-library/dashboard-disclosure-mobile-app/jcr:content/data/master";
    @Override
    public String getDashboardDisclosureContent(SlingHttpServletRequest request) throws RepositoryException {
        LOG.info("DashboardDisclosureServiceImpl: getDashboardDisclosureContent() method");
        ResourceResolver resourceResolver = request.getResourceResolver();

        String webDisclosureText = getDisclosureText(DASHBOARD_WED_DISCLOSURE_PATH, resourceResolver);
        String mobDisclosureText = getDisclosureText(DASHBOARD_MOB_DISCLOSURE_PATH, resourceResolver);
        String jsonString= "";
        DashboardDisclosureTextBean dashboardDisclosureTextBean=new DashboardDisclosureTextBean();
        if(!webDisclosureText.trim().isEmpty()){
            dashboardDisclosureTextBean.setDashboardWebDisclosureText(webDisclosureText.trim());
        }else{
            dashboardDisclosureTextBean.setDashboardWebDisclosureText("");
        }

        if(!mobDisclosureText.trim().isEmpty()){
            dashboardDisclosureTextBean.setDashboardMobDisclosureText(mobDisclosureText.trim());
        }else{
            dashboardDisclosureTextBean.setDashboardMobDisclosureText("");
        }

        Gson gson = new Gson();
        jsonString = gson.toJson(dashboardDisclosureTextBean);
        return jsonString.replaceAll("\\\\r\\\\n", "");
    }
    private String getDisclosureText(String DASHBOARD_DISCLOSURE_PATH, ResourceResolver resourceResolver)
            throws RepositoryException {
        Resource resource = resourceResolver.getResource(DASHBOARD_DISCLOSURE_PATH);
        Node parentNode = resource.adaptTo(Node.class);
        return parentNode.getProperty("disclaimertext").getString();
    }

}