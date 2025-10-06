package com.vcm.core.contactuschart.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;

@Component(service = ContactDetailWaitTimeAPIConfigs.class, immediate = true)
@Designate(ocd = ContactDetailWaitTimeAPIConfigsImpl.Config.class)
public class ContactDetailWaitTimeAPIConfigsImpl implements ContactDetailWaitTimeAPIConfigs {

    private static Logger LOG = LoggerFactory.getLogger(ContactDetailWaitTimeAPIConfigsImpl.class);

    private String currentWaitTimeEndpoint = null;

    private String WaitTimeHistoryEndpoint = null;

    private String WaitTimeHistoryQueueId = null;

    private int WaitTimeHistoryInterval = 0;

    private String openHoursScheduleGroupEndpoint;

    private String scheduleGroupId;

    private String openHoursScheduleEndpoint;

    private String waitTimeChartFilePath;

    private String openHoursScheduleFilePath;

    private String genesysSuccessFromEmailAddress;

    private String genesysFailureFromEmailAddress;

    private String[] genesysToEmailAddressList;

    private String waitTimeEmailTemplatePath;
    
    private String openHoursEmailTemplatePath;

    @Activate
    @Modified
    protected void activate(final Config config) {
	LOG.debug("Into ContactDetailWaitTimeAPIConfigsImpl Start");
	this.currentWaitTimeEndpoint = String.valueOf(config.currentWaitTimeEndpoint());
	this.WaitTimeHistoryEndpoint = String.valueOf(config.WaitTimeHistoryEndpoint());
	this.WaitTimeHistoryQueueId = String.valueOf(config.WaitTimeHistoryQueueId());
	this.WaitTimeHistoryInterval = config.WaitTimeHistoryInterval();
	this.openHoursScheduleGroupEndpoint = String.valueOf(config.openHoursScheduleGroupEndpoint());
	this.scheduleGroupId = String.valueOf(config.scheduleGroupId());
	this.openHoursScheduleEndpoint = String.valueOf(config.openHoursScheduleEndpoint());
	this.genesysSuccessFromEmailAddress = String.valueOf(config.genesysSuccessFromEmailAddress());
	this.genesysFailureFromEmailAddress = String.valueOf(config.genesysFailureFromEmailAddress());
	this.genesysToEmailAddressList=config.genesysToEmailAddressList();
	this.waitTimeChartFilePath = String.valueOf(config.waitTimeChartFilePath());
	this.openHoursScheduleFilePath = String.valueOf(config.openHoursScheduleFilePath());
	this.waitTimeEmailTemplatePath= String.valueOf(config.waitTimeEmailTemplatePath());
	this.openHoursEmailTemplatePath= String.valueOf(config.openHoursEmailTemplatePath());
	LOG.debug("Into ContactDetailWaitTimeAPIConfigsImpl END");

    }

    @ObjectClassDefinition(name = "VCM ContactDetail Wait Time API configurations", description = "This service contains "
	    + "all VCM ContactDetail Wait Time API configurations")
    public @interface Config {
	@AttributeDefinition(name = "Current Wait time endpoint", type = AttributeType.STRING)
	String currentWaitTimeEndpoint() default "https://api.usw2.pure.cloud/api/v2/routing/queues/34024a0e-3a6b-40e3-a584-dca72807c6a6/estimatedwaittime";// StringUtils.EMPTY;

	@AttributeDefinition(name = "Wait time History endpoint", type = AttributeType.STRING)
	String WaitTimeHistoryEndpoint() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Wait time History Queue ID", type = AttributeType.STRING)
	String WaitTimeHistoryQueueId() default  StringUtils.EMPTY;

	@AttributeDefinition(name = "Wait time History interval(In Days)", type = AttributeType.STRING)
	int WaitTimeHistoryInterval() default 30;

	@AttributeDefinition(name = "Open Hours Group Schedule API Endpoint", type = AttributeType.STRING)
	String openHoursScheduleGroupEndpoint() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Open Hours Group Schedule API ID", type = AttributeType.STRING)
	String scheduleGroupId() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Open Hours Schedule API Endpoint", type = AttributeType.STRING)
	String openHoursScheduleEndpoint() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Wait Time Chart File Full Path", type = AttributeType.STRING)
	String waitTimeChartFilePath() default  StringUtils.EMPTY;

	@AttributeDefinition(name = "Open Hours Schedule File Full Path", type = AttributeType.STRING)
	String openHoursScheduleFilePath() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Genesys Api Success From Email Address", type = AttributeType.STRING)
	String genesysSuccessFromEmailAddress() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Genesys Api Failure From Email Address", type = AttributeType.STRING)
	String genesysFailureFromEmailAddress() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Genesys Api To Email Address List", type = AttributeType.STRING)
	String[] genesysToEmailAddressList() default { "" };

	@AttributeDefinition(name = "Wait time Job Email Template Path", type = AttributeType.STRING)
	String waitTimeEmailTemplatePath() default StringUtils.EMPTY;
	
	@AttributeDefinition(name = "Open Hours Job Email Template Path", type = AttributeType.STRING)
	String openHoursEmailTemplatePath() default StringUtils.EMPTY;

    }

    @Override
    public String currentWaitTimeEndpoint() {
	return this.currentWaitTimeEndpoint;
    }

    @Override
    public String WaitTimeHistoryEndpoint() {
	return this.WaitTimeHistoryEndpoint;
    }

    @Override
    public String WaitTimeHistoryQueueId() {
	return this.WaitTimeHistoryQueueId;
    }

    @Override
    public int WaitTimeHistoryInterval() {
	return this.WaitTimeHistoryInterval;
    }

    @Override
    public String openHoursScheduleGroupEndpoint() {
	return this.openHoursScheduleGroupEndpoint;
    }

    @Override
    public String scheduleGroupId() {
	return this.scheduleGroupId;
    }

    @Override
    public String openHoursScheduleEndpoint() {
	return this.openHoursScheduleEndpoint;
    }

    @Override
    public String waitTimeChartFilePath() {
	return this.waitTimeChartFilePath;
    }

    @Override
    public String openHoursScheduleFilePath() {
	return this.openHoursScheduleFilePath;
    }

    @Override
    public String genesysSuccessFromEmailAddress() {
	return this.genesysSuccessFromEmailAddress;
    }

    @Override
    public String genesysFailureFromEmailAddress() {
	return this.genesysFailureFromEmailAddress;
    }

    @Override
    public String[] genesysToEmailAddressList() {
	if (Objects.nonNull(this.genesysToEmailAddressList)) {
	   
	    return this.genesysToEmailAddressList.clone();
	
	} else {
	
	    return null;
	}
    }

    @Override
    public String waitTimeEmailTemplatePath() {
	return this.waitTimeEmailTemplatePath;
    }

    @Override
    public String openHoursEmailTemplatePath() {
	return this.openHoursEmailTemplatePath;
    }
}
