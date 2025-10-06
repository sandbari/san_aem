package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductManagementTeam {
    private static final Logger log = LoggerFactory.getLogger(ProductManagementTeam.class);

    @Inject
    @Via("resource")
    String description;
    @Inject
    @Via("resource")
    String heading;
    @Inject
    @Via("resource")
    String linkText;
    @Inject
    @Via("resource")
    List<ManagementTeam> teamsLeaderBio;
    @Inject
    @Via("resource")
    String teamLinkText;
    @Inject
    @Via("resource")
    String teamLinkUrl;

    @SlingObject
    private ResourceResolver resourceResolver;

    private ArrayList<LeaderBio> list = new ArrayList<LeaderBio>();

    private String COMPONENT_NAME = "vcm/components/content/leaderbio";

    private String RESPONSIVEGRID = "jcr:content/root/responsivegrid/";

    String triangleColor;

    @PostConstruct
    protected void init() {
        if (null != teamLinkUrl) {
            Resource teamPageResource = resourceResolver.getResource(teamLinkUrl);
            if (null != teamPageResource) {
                Page teamPage = teamPageResource.adaptTo(Page.class);
                final InheritanceValueMap pageProperties = new HierarchyNodeInheritanceValueMap(
                        teamPage.getContentResource());
                triangleColor = pageProperties.getInherited("triangleColor", String.class);
                if (null != teamsLeaderBio) {
                    for (ManagementTeam pageList : teamsLeaderBio) {
                        Resource leaderPage = resourceResolver.getResource(pageList.getLeaderUrl());
                        if (null != leaderPage && leaderPage.getChild(RESPONSIVEGRID).hasChildren()) {
                            Iterable<Resource> componentResources = leaderPage.getChild(RESPONSIVEGRID).getChildren();
                            for (Resource componentResource : componentResources) {
                                if (componentResource.isResourceType(COMPONENT_NAME)) {
                                    LeaderBio leaderBio = componentResource.adaptTo(LeaderBio.class);
                                    log.debug("This component info got added here : {} ", leaderBio.getFullname());
                                    leaderBio.setManagementPicture(pageList.getPictureview());
                                    list.add(leaderBio);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public void setList(ArrayList<LeaderBio> list) {
        ArrayList<LeaderBio> copyList = new ArrayList<LeaderBio>();
        copyList.addAll(list);
        this.list = copyList;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public String getHeading() {
        return heading;
    }

    public String getLinkText() {
        return linkText;
    }

    public ArrayList<LeaderBio> getList() {
        ArrayList<LeaderBio> copyList = list;
        Collections.copy(copyList, list);
        return copyList;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public List<ManagementTeam> getTeamsLeaderBio() {
        return teamsLeaderBio;
    }

    public void setTeamsLeaderBio(List<ManagementTeam> teamsLeaderBio) {
        this.teamsLeaderBio = teamsLeaderBio;
    }

    public String getTeamLinkText() {
        return teamLinkText;
    }

    public void setTeamLinkText(String teamLinkText) {
        this.teamLinkText = teamLinkText;
    }

    public String getTeamLinkUrl() {
        return teamLinkUrl;
    }

    public void setTeamLinkUrl(String teamLinkUrl) {
        this.teamLinkUrl = teamLinkUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTriangleColor() {
        return triangleColor;
    }

    public void setTriangleColor(String triangleColor) {
        this.triangleColor = triangleColor;
    }

}
