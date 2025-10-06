package com.vcm.core.contactuschart.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.exceptions.ServiceException;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.contactuschart.utils.ErrorCodeMapping;

@Component(service = OpenHoursScheduleGenerator.class, immediate = true)
public class OpenHoursScheduleGeneratorImpl implements OpenHoursScheduleGenerator {

    private static final String CREATED_TIME_STAMP = "createdTimeStamp";

    private static final int timeout = 10;

    private static Logger LOG = LoggerFactory.getLogger(OpenHoursScheduleGeneratorImpl.class);

    @Reference
    private ContactDetailUitiliyService contactDetailUitiliyService;

    @Reference
    private GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    @Reference
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    public void setContactDetailUitiliyService(ContactDetailUitiliyService contactDetailUitiliyService) {
		this.contactDetailUitiliyService = contactDetailUitiliyService;
	}

	public void setGenesysAuthTokenGenerator(GenesysAuthTokenGenerator genesysAuthTokenGenerator) {
		this.genesysAuthTokenGenerator = genesysAuthTokenGenerator;
	}

	public void setContactDetailWaitTimeAPIConfigs(ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs) {
		this.contactDetailWaitTimeAPIConfigs = contactDetailWaitTimeAPIConfigs;
	}

	private String access_token = null;

    private JsonObject scheduledGroupJson = null;

    private JsonObject processedJson = null;

    private String schedule_group_endpoint = null;

    private String schedule_end_point = null;

    private String schedule_group_id = null;

    private static String HOLIDAY_SCHEDULE_JSON_KEY = "holidaySchedules";

    private static String OPEN_HOURS_SCHEDULE_JSON_KEY = "openSchedules";

    protected void initializeConfigParams() {

	LOG.debug("Into OpenHoursScheduleGeneratorImpl service Start");

	access_token = genesysAuthTokenGenerator.getAuthToken();

	schedule_group_endpoint = contactDetailWaitTimeAPIConfigs.openHoursScheduleGroupEndpoint();

	schedule_end_point = contactDetailWaitTimeAPIConfigs.openHoursScheduleEndpoint();

	schedule_group_id = contactDetailWaitTimeAPIConfigs.scheduleGroupId();

	LOG.debug(
		"Into OpenHoursScheduleGeneratorImpl service Configs access_token {} schedule_group_endpoint {} schedule_end_point {} schedule_group_id{}",
		access_token, schedule_group_endpoint, schedule_end_point, schedule_group_id);

	LOG.debug("Into OpenHoursScheduleGeneratorImpl service END");

    }

    @Override
    public String getOpenHoursScheduleJson() {
	
	processedJson =  new JsonObject();

	String processedJsonString = null;
	
	this.initializeConfigParams();

	scheduledGroupJson = this.getJsonFromService(schedule_group_endpoint, schedule_group_id);

	LOG.debug("Into OpenHoursScheduleGeneratorImpl scheduledGroupJson {}", scheduledGroupJson);

	boolean isValidData = this.checkValidJson(scheduledGroupJson, OPEN_HOURS_SCHEDULE_JSON_KEY,
		HOLIDAY_SCHEDULE_JSON_KEY);

	LOG.debug("Into OpenHoursScheduleGeneratorImpl isValidData {}", isValidData);

	if (isValidData) {

	    JsonArray processedOpenHoursJson = this.processEachSchedule(schedule_end_point,
		    OPEN_HOURS_SCHEDULE_JSON_KEY);

	    JsonArray processedHolidayHoursJson = this.processEachSchedule(schedule_end_point,
		    HOLIDAY_SCHEDULE_JSON_KEY);

	    if (Objects.nonNull(processedOpenHoursJson) && processedOpenHoursJson.size() > 0
		    && Objects.nonNull(processedHolidayHoursJson) && processedHolidayHoursJson.size() > 0) {

		processedJson.add("open_hours_data", processedOpenHoursJson);

		processedJson.add("holiday_schedule_data", processedHolidayHoursJson);

		ZonedDateTime zoneDateTime = ZonedDateTime.now();

		processedJson.addProperty(CREATED_TIME_STAMP, zoneDateTime.toString());

		processedJsonString = processedJson.toString();
	    }
	}
	return processedJsonString;
    }

    public JsonObject getJsonFromService(String apiEndPoint, String scheduleId) {

	JsonObject jsonObject = null;

	CloseableHttpClient client = null;

	CloseableHttpResponse response = null;

	int statusCode;

	try {
	    if (Objects.nonNull(apiEndPoint) && !apiEndPoint.equals(StringUtils.EMPTY) && Objects.nonNull(scheduleId)
		    && !scheduleId.equals(StringUtils.EMPTY)) {

		String scheduleAPIEndPoint = apiEndPoint + scheduleId;

		HttpGet request = new HttpGet(scheduleAPIEndPoint);

		request.setHeader("Content-Type", Constant.APPLICATION_JSON);

		request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);

		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
			.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		response = client.execute(request);

		statusCode = response.getStatusLine().getStatusCode();

		if (!contactDetailUitiliyService.isError(statusCode)) {

		    String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());

		    jsonObject = new JsonParser().parse(responseString).getAsJsonObject();

		} else {

		    LOG.error("Recieved response with error status: {}", statusCode);

		    contactDetailUitiliyService.generalExceptionHandling(statusCode,
			    ErrorCodeMapping.OPEN_HOURS_FINDER_SERVICE_EXCEPTION.getErrorMessage());
		}

	    }

	} catch (UnsupportedEncodingException e) {

	    LOG.error("UnsupportedEncodingException Occured in token service {} ", e);
	} catch (ClientProtocolException e) {

	    LOG.error("ClientProtocolException Occured in token service {} ", e);

	} catch (IOException e) {

	    LOG.error("IOException Occured in token service {} ", e);

	} catch (ServiceException e) {

	    LOG.error("ServiceException Occured in token service {} ", e);

	} finally {

	    try {

		if (Objects.nonNull(client)) {

		    client.close();
		}
		if (Objects.nonNull(response)) {

		    response.close();
		}

	    } catch (IOException e) {

		LOG.error("IOException Occured in token service {} ", e);
	    }
	}

	return jsonObject;
    }

    private boolean checkValidJson(JsonObject jsonObject, String key1, String key2) {
	boolean isValid = false;

	if (Objects.nonNull(jsonObject) && Objects.nonNull(key1) && Objects.nonNull(key2)) {

	    if (jsonObject.has(key1) && jsonObject.has(key2)) {

		isValid = true;

	    }
	}

	return isValid;
    }

    private JsonArray processEachSchedule(String apiEndPoint, String scheduleJsonKey) {
	JsonArray schedulesArray = new JsonArray();

	try {

	    if (Objects.nonNull(scheduledGroupJson.get(scheduleJsonKey))) {

		JsonArray dataArray = scheduledGroupJson.getAsJsonArray(scheduleJsonKey);

		for (int i = 0; i < dataArray.size(); i++) {

		    JsonObject currentObj = dataArray.get(i).getAsJsonObject();

		    if (currentObj.get("id") != null) {

			String scheduleId = currentObj.get("id").getAsString();

			JsonObject jsonObj = this.getJsonFromService(apiEndPoint, scheduleId);

			if (Objects.nonNull(jsonObj)) {

			    schedulesArray.add(jsonObj);

			} else {

			    contactDetailUitiliyService.generalExceptionHandling(500,
				    ErrorCodeMapping.OPEN_HOURS_FINDER_SERVICE_EXCEPTION.getErrorMessage());

			    break;
			}
		    }
		}
	    }
	} catch (ServiceException e) {

	    LOG.error("ServiceException Occured in token service {} ", e);

	}
	return schedulesArray;
    }

}
