package com.vcm.core.models;

import com.day.cq.wcm.api.Page;
import com.vcm.core.utils.ComponentProperties;
import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InvestmentTeams {
    private static final Logger log = LoggerFactory.getLogger(InvestmentTeams.class);

    @Inject
    @Via("resource")
    String heading;
    @Inject
    @Via("resource")
    String teamDisplayStyle;
    @Inject
    @Via("resource")
    String rootPath;
    @Inject
    @Via("resource")
    String linkText;

    @Inject
    @Via("request")
    Page currentPage;

    @SlingObject
    private ResourceResolver resourceResolver;

    private ArrayList<LeaderBio> list = new ArrayList<LeaderBio>();

    private String COMPONENT_NAME = "vcm/components/content/leaderbio";

    String mappedCurrentPage;

    @PostConstruct
    protected void init() {
        mappedCurrentPage = UtilityService.identifyLinkUrl(currentPage.getPath(),resourceResolver);
        log.debug("Root Path : {} " , rootPath);
        if(null != rootPath) {
            Resource root = resourceResolver.getResource(rootPath);
            if (null != root) {
                ComponentProperties componentProperties = new ComponentProperties();
                ArrayList<Resource> componentResource = componentProperties.getProperties(root, COMPONENT_NAME);
                for (Resource component : componentResource) {
                    log.debug("This component info got added here : {} ", component.getResourceType());
                    LeaderBio leaderBio = component.adaptTo(LeaderBio.class);
                    list.add(leaderBio);
                }
            }
        }
    }

    public void setHeading(String heading) { this.heading = heading; }

    public void setTeamDisplayStyle(String teamDisplayStyle) { this.teamDisplayStyle = teamDisplayStyle; }

    public void setRootPath(String rootPath) { this.rootPath = rootPath; }

    public void setLinkText(String linkText) { this.linkText = linkText; }

    public void setList(ArrayList<LeaderBio> list) {
        ArrayList<LeaderBio> copyList = new ArrayList<LeaderBio>();
        copyList.addAll(list);
        this.list = copyList;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) { this.resourceResolver = resourceResolver; }

    public String getHeading() {
        return heading;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getTeamDisplayStyle() {
        return teamDisplayStyle;
    }

    public String getLinkText() {
        return linkText;
    }

    public ArrayList<LeaderBio> getList() {
        ArrayList<LeaderBio> copyList = list;
        Collections.copy(copyList, list);
        return copyList;
    }

    public ResourceResolver getResourceResolver() { return resourceResolver; }

    public String getMappedCurrentPage() {
        return mappedCurrentPage;
    }

    public void setMappedCurrentPage(String mappedCurrentPage) {
        this.mappedCurrentPage = mappedCurrentPage;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

}
