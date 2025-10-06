package com.vcm.core.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface QualityStructure {

    @Inject
    String getHeading();

    @Inject
    String getAsOfDate();

    @Inject
    String getHeadLabel();

    @Inject
    List<TableBodyContent> getTableBodyContent();

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface TableBodyContent {
        @Inject
        String getBodyHeading();

        @Inject
        String getBodyDescription();

        @Inject
        String getFundValue();
    }
}
