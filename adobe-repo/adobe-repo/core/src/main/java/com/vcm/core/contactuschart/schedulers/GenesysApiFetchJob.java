package com.vcm.core.contactuschart.schedulers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.service.WaitTimeChartService;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.service.EmailService;
import com.vcm.core.utils.FileHandlerService;

@Designate(ocd = GenesysApiFetchJob.Config.class)
@Component(service = Runnable.class, immediate = true)
public class GenesysApiFetchJob implements Runnable {

    private static final String ENVIRONMENT = "environment";

    private static final String ENDED_AT = "EndedAt";

    private static final String STARTED_AT = "startedAt";

    private static final String FILE_PATH = "filePath";

    private static final String JOB_STATUS = "jobStatus";

    private static final String FROM_MAIL_ID = "fromMailId";

    private static final String SUCCESSFUL = "Successful";

    private static final String FAILED = "Failed";

    private static final Logger LOG = LoggerFactory.getLogger(GenesysApiFetchJob.class);

    @Reference
    private WaitTimeChartService WaitTimeChartService;

    @Reference
    private OpenHoursScheduleGenerator openHoursScheduleGenerator;

    @Reference
    private FileHandlerService fileHandlerService;

    @Reference
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    @Reference
    private ContactDetailUitiliyService contactDetailUitiliyService;

    @Reference
    private EmailService emailService;

    @Reference
    private SlingSettingsService slingSettingsService;

    public void setWaitTimeChartService(WaitTimeChartService waitTimeChartService) {
		WaitTimeChartService = waitTimeChartService;
	}

	public void setOpenHoursScheduleGenerator(OpenHoursScheduleGenerator openHoursScheduleGenerator) {
		this.openHoursScheduleGenerator = openHoursScheduleGenerator;
	}

	public void setFileHandlerService(FileHandlerService fileHandlerService) {
		this.fileHandlerService = fileHandlerService;
	}

	public void setContactDetailWaitTimeAPIConfigs(ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs) {
		this.contactDetailWaitTimeAPIConfigs = contactDetailWaitTimeAPIConfigs;
	}

	public void setContactDetailUitiliyService(ContactDetailUitiliyService contactDetailUitiliyService) {
		this.contactDetailUitiliyService = contactDetailUitiliyService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setSlingSettingsService(SlingSettingsService slingSettingsService) {
		this.slingSettingsService = slingSettingsService;
	}

	private Set<String> runmodes = null;

    @ObjectClassDefinition(name = "VCM Genesys Scheduler", description = "A Scheduler to fetch the genesys api daily")
    public @interface Config {
	@AttributeDefinition(name = "Cron-job expression", description = "Expression stands for sec min hour monthDay month weekday year")
	String scheduler_expression() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
	boolean scheduler_concurrent() default false;
    }

    @Override
    public void run() {

	runmodes = slingSettingsService.getRunModes();

	if (runmodes.contains(Constant.AUTHOR_INSTANCE)) {

	    long starttime = System.currentTimeMillis();

	    LOG.debug("GenesysApiFetchJob Started at {}", starttime);

	    String waitTimeChartJson = WaitTimeChartService.generateWaittimeChartJson();

	    String waitTimeChartFilePath = contactDetailWaitTimeAPIConfigs.waitTimeChartFilePath();

	    String openHoursScheduleJson = openHoursScheduleGenerator.getOpenHoursScheduleJson();

	    String openHoursScheduleFilePath = contactDetailWaitTimeAPIConfigs.openHoursScheduleFilePath();

	    Asset waitTimeChartFile = this.createFile(waitTimeChartJson, waitTimeChartFilePath);

	    Asset openHoursScheduleFile = this.createFile(openHoursScheduleJson, openHoursScheduleFilePath);

	    long endtime = System.currentTimeMillis();

	    String waitTimeTemplatePath = contactDetailWaitTimeAPIConfigs.waitTimeEmailTemplatePath();

	    String openHoursTemplatePath = contactDetailWaitTimeAPIConfigs.openHoursEmailTemplatePath();
	    
	    String[] recepients = contactDetailWaitTimeAPIConfigs.genesysToEmailAddressList();


	    if (Objects.nonNull(waitTimeChartFile)) {

		this.sendWaitTimeEmail(starttime, endtime, SUCCESSFUL, waitTimeTemplatePath,
			waitTimeChartFile.getPath(),recepients);

	    } else {

		this.sendWaitTimeEmail(starttime, endtime, FAILED, waitTimeTemplatePath, null,recepients);

	    }

	    if (Objects.nonNull(openHoursScheduleFile)) {

		this.sendWaitTimeEmail(starttime, endtime, SUCCESSFUL, openHoursTemplatePath,
			openHoursScheduleFile.getPath(),recepients);

	    } else {

		this.sendWaitTimeEmail(starttime, endtime, FAILED, openHoursTemplatePath, null,recepients);

	    }

	    LOG.debug("GenesysApiFetchJob Ended at {}", endtime);
	}

    }

    private Asset createFile(String json, String filePath) {

	Asset asset = null;

	if (Objects.nonNull(json) && !StringUtils.isEmpty(json)) {

	    if (Objects.nonNull(filePath) && !StringUtils.isEmpty(filePath)) {

		asset = fileHandlerService.createFile(filePath, json, Constant.APPLICATION_JSON);

		if (Objects.nonNull(asset) && runmodes.contains(Constant.AUTHOR_INSTANCE)) {

		    contactDetailUitiliyService.replicateAsset(asset.getPath());
		}
	    }
	}

	return asset;

    }

    private void sendWaitTimeEmail(long starttime, long endtime, String jobStatus, String templatePath,
	    String filePath, String[] recepients) {

	Date startedAt = new Date(starttime);

	Date EndedAt = new Date(endtime);
	
	String environment = this.getRunmode();

	Map<String, String> emailProperties = new HashMap<String, String>();

	emailProperties.put(STARTED_AT, startedAt.toString());

	emailProperties.put(ENDED_AT, EndedAt.toString());
	
	emailProperties.put(ENVIRONMENT, environment);

	if (jobStatus.equalsIgnoreCase(SUCCESSFUL)) {

	    String fromEmail = contactDetailWaitTimeAPIConfigs.genesysSuccessFromEmailAddress();

	    emailProperties.put(FROM_MAIL_ID, fromEmail);

	    emailProperties.put(JOB_STATUS, SUCCESSFUL);

	    emailProperties.put(FILE_PATH, filePath);

	} else {

	    String fromEmail = contactDetailWaitTimeAPIConfigs.genesysFailureFromEmailAddress();

	    emailProperties.put(FROM_MAIL_ID, fromEmail);

	    emailProperties.put(JOB_STATUS, FAILED);

	    emailProperties.put(FILE_PATH, "NA");
	}

	HtmlEmail htmlEmail = emailService.creatEmail(templatePath, emailProperties, recepients);

	if (Objects.nonNull(htmlEmail)) {

	    emailService.sendMail(htmlEmail);

	}
    }
    
    public String getRunmode() {
	
	String environment = "";
	
	if(runmodes.contains("qa")) {
	    environment="QA";
	}
	else if(runmodes.contains("dev")) {
	    environment="DEV";
	}
	else if(runmodes.contains("stage")) {
	    environment="STAGE";
	}
	else if(runmodes.contains("prod")) {
	    environment="PROD";
	}
	else if(runmodes.contains("local")) {
	    environment="LOCAL";
	}
	
	return environment;
	
    }

}
