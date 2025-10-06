package com.vcm.core.service.impl;

import com.vcm.core.service.SurvivorRelationConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = SurvivorRelationConfigurationService.class, immediate = true)
@Designate(ocd = SurvivorRelationConfigurationServiceImpl.Config.class)
public class SurvivorRelationConfigurationServiceImpl implements SurvivorRelationConfigurationService {

    private static final Logger LOG = LoggerFactory.getLogger(SurvivorRelationConfigurationServiceImpl.class);

    private String survivorRelationRequestEndPoint =StringUtils.EMPTY;
    private String xApiKey =StringUtils.EMPTY;
    private String gReCaptchaSitekey =StringUtils.EMPTY;
    private String survivorRelationConfirmationEndpoint =StringUtils.EMPTY;

    @Activate
    @Modified
    protected void activate(final Config config) {
        survivorRelationRequestEndPoint = String.valueOf(config.survivorRelationRequestEndPoint());

        xApiKey = String.valueOf(config.xApiKey());

        gReCaptchaSitekey = String.valueOf(config.gReCaptchaSitekey());

        survivorRelationConfirmationEndpoint = String.valueOf(config.survivorRelationConfirmationEndpoint());

    }
    @Override
    public String getSurvivorRelationPostRequestEndPoint() {
        return survivorRelationRequestEndPoint;
    }

    @Override
    public String getSurvivorRelationXApiKey() {
        return xApiKey;
    }

    @Override
    public String getGReCaptchaSiteKey() {
        return gReCaptchaSitekey;
    }

    @Override
    public String getSurvivorRelationConfirmationEndpoint() {
        return survivorRelationConfirmationEndpoint;
    }

    @ObjectClassDefinition(name = "Survivor Relation Configuration", description = "This service contains "
            + "all survivor relation related OSGI Configuration")
    public @interface Config {
        @AttributeDefinition(name = "Survivor Relation Endpoint",description = "Please enter members sr relation api endpoint")
        String survivorRelationRequestEndPoint() default StringUtils.EMPTY;

        @AttributeDefinition(name = "Survivor Relation x-Api Key",description = "Please enter members sr relation x ApiKey")
        String xApiKey() default "zm4LSWbDrt94MufZwTfAry5kHkdfSnp9TWIyOfke";

        @AttributeDefinition(name = "gReCaptcha site key",description = "Please enter members Google ReCaptcha site Key")
        String gReCaptchaSitekey() default "6Ld7Q00fAAAAAB3C3EZgmcwPdIW_3e8LrQvxaT08";

        @AttributeDefinition(name = "Survivor Relation Confirmation Endpoint",description = "Please enter members Survivor Relation Confirmation Endpoint")
        String survivorRelationConfirmationEndpoint() default StringUtils.EMPTY;;
    }
}