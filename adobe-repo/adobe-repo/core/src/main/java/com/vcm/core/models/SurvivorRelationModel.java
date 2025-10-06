package com.vcm.core.models;

import com.vcm.core.service.SurvivorRelationConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;



@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SurvivorRelationModel {

    private static final Logger LOG = LoggerFactory.getLogger(SurvivorRelationModel.class);

    private String srSaveDetailsEndPoint = StringUtils.EMPTY;

    private String apiKey = StringUtils.EMPTY;

    private String googleReCaptchaSiteKey = StringUtils.EMPTY;

    private String srConfirmationEndpoint = StringUtils.EMPTY;


    @Inject
    public SurvivorRelationConfigurationService survivorRelationConfigurationService;

    @PostConstruct
    protected void init() {
        srSaveDetailsEndPoint = survivorRelationConfigurationService.getSurvivorRelationPostRequestEndPoint();
        apiKey = survivorRelationConfigurationService.getSurvivorRelationXApiKey();
        googleReCaptchaSiteKey = survivorRelationConfigurationService.getGReCaptchaSiteKey();
        srConfirmationEndpoint = survivorRelationConfigurationService.getSurvivorRelationConfirmationEndpoint();

    }

    public String getSrSaveDetailsEndPoint() {
        return srSaveDetailsEndPoint;
    }

    public void setSrSaveDetailsEndPoint(String srSaveDetailsEndPoint) {
        this.srSaveDetailsEndPoint = srSaveDetailsEndPoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getGoogleReCaptchaSiteKey() {
        return googleReCaptchaSiteKey;
    }

    public void setGoogleReCaptchaSiteKey(String googleReCaptchaSiteKey) {
        this.googleReCaptchaSiteKey = googleReCaptchaSiteKey;
    }

    public String getSrConfirmationEndpoint() {
        return srConfirmationEndpoint;
    }

    public void setSrConfirmationEndpoint(String srConfirmationEndpoint) {
        this.srConfirmationEndpoint = srConfirmationEndpoint;
    }
}