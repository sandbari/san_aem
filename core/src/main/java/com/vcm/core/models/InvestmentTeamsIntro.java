package com.vcm.core.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface InvestmentTeamsIntro {

    @Inject
    String getHeading();

    @Inject
    List<TeamsLeaderBio> getTeamsLeaderBio();
}
