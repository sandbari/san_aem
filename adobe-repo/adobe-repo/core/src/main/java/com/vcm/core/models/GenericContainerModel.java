package com.vcm.core.models;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

/**
 * @author CTS. A Model class for ProductsAccordionModel
 */

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GenericContainerModel {

    private static final Logger log = LoggerFactory.getLogger(GenericContainerModel.class);

    @Inject
    private String containerType;

    @Inject
    private Resource resource;

    private String id;

    private List<String> allowedAudience = null;

    @Inject
    AudienceSelectorService audienceSelectorService;

    /**
     * @return the containerType
     */
    public String getContainerType() {
	return containerType;
    }

    /**
     * @param containerType the containerType to set
     */
    public void setContainerType(String containerType) {
	this.containerType = containerType;
    }

    /**
     * @return the container id
     */
    public String getId() {
	return id;
    }

    @PostConstruct
    protected void init() {

	log.debug("*** inside Init method genericContainer  ***" + resource);

	id = UtilityService.getId(resource);

	allowedAudience = audienceSelectorService.getAllowedAudienceListFromResource(resource);

    }

    public List<String> getAllowedAudience() {
	
	List<String> copyOfAllowedAudience = allowedAudience;
	
	Collections.copy(copyOfAllowedAudience, allowedAudience);
	
	return copyOfAllowedAudience;
    }

    /**
     * @param audienceSelectorService the audienceSelectorService to set
     */
    public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
	this.audienceSelectorService = audienceSelectorService;
    }

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
