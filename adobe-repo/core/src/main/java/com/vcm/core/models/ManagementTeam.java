package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ManagementTeam {

    @Inject
    String leaderUrl;

    @Inject
    String pictureview;

    public String getLeaderUrl() {
        return leaderUrl;
    }

    public void setLeaderUrl(String leaderUrl) {
        this.leaderUrl = leaderUrl;
    }

    public String getPictureview() {
        return pictureview;
    }

    public void setPictureview(String pictureview) {
        this.pictureview = pictureview;
    }


}
