package com.vcm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface FormsModel {

    @Inject
    String heading();

    @Inject
    String description();

    @Inject
    String loadMoreText();

    @Inject
    String formsTags();

    @Inject
    String rootPath();

    @Inject
    String formID();

    @Inject
    String formCategory();

    @Inject
    String download();

    @Inject
    String form();

}
