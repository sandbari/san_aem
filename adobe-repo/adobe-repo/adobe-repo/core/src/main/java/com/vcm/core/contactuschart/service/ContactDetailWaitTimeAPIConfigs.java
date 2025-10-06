package com.vcm.core.contactuschart.service;


public interface ContactDetailWaitTimeAPIConfigs {
	public String currentWaitTimeEndpoint();
	public String WaitTimeHistoryEndpoint();
	public String WaitTimeHistoryQueueId();
	public int WaitTimeHistoryInterval();
	public String openHoursScheduleGroupEndpoint();
	public String scheduleGroupId();
	public String openHoursScheduleEndpoint();
	public String waitTimeChartFilePath();
	public String openHoursScheduleFilePath();
	public String genesysSuccessFromEmailAddress();
	public String genesysFailureFromEmailAddress();
	public String[] genesysToEmailAddressList();
	public String waitTimeEmailTemplatePath();
	public String openHoursEmailTemplatePath();
}
