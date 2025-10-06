package com.vcm.core.contactuschart.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.exceptions.ServiceException;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.service.WaitTimeChartService;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.contactuschart.utils.ErrorCodeMapping;

@Component(service = WaitTimeChartService.class, immediate = true)
public class WaitTimeChartImpl implements WaitTimeChartService {

    private static final String RESULTS_JSON = "results";

    private static final int timeout = 10;

    //private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private static final String GRANULARITY = "PT30M";

    private static Logger LOG = LoggerFactory.getLogger(WaitTimeChartImpl.class);

    @Reference
    private GenesysAuthTokenGenerator genesysAuthTokenGenerator;

    @Reference
    private ContactDetailUitiliyService contactDetailUitiliyService;

    @Reference
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    public void setGenesysAuthTokenGenerator(GenesysAuthTokenGenerator genesysAuthTokenGenerator) {
		this.genesysAuthTokenGenerator = genesysAuthTokenGenerator;
	}

	public void setContactDetailUitiliyService(ContactDetailUitiliyService contactDetailUitiliyService) {
		this.contactDetailUitiliyService = contactDetailUitiliyService;
	}

	public void setContactDetailWaitTimeAPIConfigs(ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs) {
		this.contactDetailWaitTimeAPIConfigs = contactDetailWaitTimeAPIConfigs;
	}

	private enum Days {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }

    private String[] timeArray = { "7:30" , "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
	    "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
	    "19:00", "19:30", "20:00" };

    private String access_token = null;

    private String waittime_history_endpoint = null;

    private String queue_id = null;

    private int n_days_history = 0;

    /**
     * Initialize config params on call
     */
    public void initializeconfigParams() {

	access_token = genesysAuthTokenGenerator.getAuthToken();

	waittime_history_endpoint = contactDetailWaitTimeAPIConfigs.WaitTimeHistoryEndpoint();

	queue_id = contactDetailWaitTimeAPIConfigs.WaitTimeHistoryQueueId();

	n_days_history = contactDetailWaitTimeAPIConfigs.WaitTimeHistoryInterval();
	
	LOG.debug(
		"Into WaitTimeChartImpl service access_token {} queue_id {} waittime_history_endpoint {} n_days_history{}",
		access_token, waittime_history_endpoint, queue_id, n_days_history);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vcm.core.contactuschart.service.WaitTimeChartService#
     * generateWaittimeChartJson() Generates wait time json
     */
    @Override
    public String generateWaittimeChartJson() {

	this.initializeconfigParams();

	HashMap<String, LinkedHashMap<String, Object>> waitTimeChartMap = this.createEmptyWaitTimeJSONStructure();
	
	LOG.debug("Into WaitTimeChartImpl service empty waitTimeChartMap_Global {}", waitTimeChartMap);

	JsonArray combinedJsonData = new JsonArray();

	boolean errorFlag = false;

	try {

	    for (String interval : this.getTimeInterval()) {

		JsonObject serviceJsonObj = this.getJsonFromService(waittime_history_endpoint, interval, queue_id);

		if (Objects.nonNull(serviceJsonObj)) {

		    JsonArray resultDataArr = this.getResultDataSet(serviceJsonObj);

		    if (Objects.nonNull(combinedJsonData) && Objects.nonNull(resultDataArr)) {

			combinedJsonData.addAll(resultDataArr);

		    } else {

			errorFlag = true;

			break;
		    }

		} else {

		    errorFlag = true;

		    break;
		}
	    }

	    if (errorFlag || combinedJsonData.size() <= 0) {

		contactDetailUitiliyService.generalExceptionHandling(500, "Invalid data");

	    } else {

		waitTimeChartMap = this.processWaittimeScheduleData(combinedJsonData,waitTimeChartMap);
	    }

	} catch (ServiceException e) {

	    LOG.error("ServiceException Occured in token service {} ", e);
	}

	return this.convertToJSONArray(waitTimeChartMap);
    }

    /**
     * Creates a empty json structure for Days and time interval array
     * 
     * @return empty json structure for wait time chart
     */
    public HashMap<String, LinkedHashMap<String, Object>> createEmptyWaitTimeJSONStructure() {
	
	HashMap<String, LinkedHashMap<String, Object>> waitTimeChartMap = new HashMap<String, LinkedHashMap<String, Object>>();

	for (Days day : Days.values()) {

	    String dayValue = day.toString();

	    LinkedHashMap<String, Object> dayMap = new LinkedHashMap<String, Object>();

	    dayMap.put("TimeBucket", dayValue);

	    double zeroMinutes = 0;

	    for (String time : timeArray) {

		dayMap.put(time, zeroMinutes);
	    }

	    waitTimeChartMap.put(dayValue, dayMap);

	}
	return waitTimeChartMap;
    }

    /**
     * 
     * @param waittime_history_endpoint
     * @param interval
     * @param queue_id
     * @return JSON from genesys api service response
     * 
     */
    @Override
    public JsonObject getJsonFromService(String waittime_history_endpoint, String interval, String queue_id) {

	JsonObject jsonObject = null;

	CloseableHttpClient client = null;

	CloseableHttpResponse response = null;

	int statusCode;

	try {

	    if (Objects.nonNull(waittime_history_endpoint) && !waittime_history_endpoint.equals(StringUtils.EMPTY)) {

		HttpPost request = new HttpPost(waittime_history_endpoint);

		request.setHeader("Content-Type", Constant.APPLICATION_JSON);

		request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);

		String jsonBodyString = "{\"interval\": \"" + interval + "\",\"granularity\": \"" + GRANULARITY
			+ "\",\"groupBy\": [\"queueId\"],\"filter\": {\"type\":\"and\",\"predicates\": [{\"dimension\": \"queueId\",\"value\": \""
			+ queue_id
			+ "\"},{\"dimension\": \"mediaType\",\"value\": \"voice\"}]},\"metrics\": [\"tWait\"]}";

		StringEntity entity = new StringEntity(jsonBodyString);

		request.setEntity(entity);

		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
			.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		response = client.execute(request);

		statusCode = response.getStatusLine().getStatusCode();

		LOG.debug("Into WaitTimeChartImpl service statusCode {}", statusCode);
		
		if (!contactDetailUitiliyService.isError(statusCode)) {

		    String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());

		    LOG.debug("Into WaitTimeChartImpl service responseString {}", responseString);
		    
		    jsonObject = new JsonParser().parse(responseString).getAsJsonObject();

		} else {

		    LOG.error("Recieved response with error status: {}", statusCode);

		    contactDetailUitiliyService.generalExceptionHandling(statusCode,
			    ErrorCodeMapping.WAITTIME_CHART_SERVICE_EXCEPTION.getErrorMessage());
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

    private ArrayList<String> getTimeInterval() {

	int num_months = n_days_history / 30;

	int remainder_days = n_days_history % 30;

	ArrayList<String> intervalList = new ArrayList<String>();

	LocalDate StartDate = LocalDate.now(Constant.CENTRAL_ZONE_ID).minus(Period.ofDays(1));

	for (int i = 0; i < num_months; i++) {

	    LocalDate date_30_days_back = StartDate.minus(Period.ofDays(30));

	    String current_interval = new StringBuffer().append(date_30_days_back).append("T00:00:00/")
		    .append(StartDate).append("T00:00:00").toString();

	    intervalList.add(current_interval);

	    // reset startdate
	    StartDate = date_30_days_back;
	}

	// for remaining days
	if (remainder_days > 0) {

	    LocalDate date_remainder_days_back = StartDate.minus(Period.ofDays(remainder_days));

	    String remaining_interval = new StringBuffer().append(date_remainder_days_back).append("T00:00:00/")
		    .append(StartDate).append("T00:00:00").toString();

	    intervalList.add(remaining_interval);
	}

	LOG.debug("Into WaitTimeChartImpl service intervalList {}", intervalList);

	
	return intervalList;
    }

    private HashMap<String, LinkedHashMap<String, Object>> processWaittimeScheduleData(JsonArray dataArray,HashMap<String, LinkedHashMap<String, Object>> waitTimeChartMap) {

	boolean errorFlag = false;

	try {

	    if (Objects.nonNull(dataArray) && dataArray.size() > 0) {

		for (int i = 0; i < dataArray.size(); i++) {

		    JsonObject currentObj = dataArray.get(i).getAsJsonObject();

		    if (Objects.nonNull(currentObj) && Objects.nonNull(currentObj.get("interval"))
			    && Objects.nonNull(currentObj.getAsJsonArray("metrics"))
			    && currentObj.getAsJsonArray("metrics").size() > 0) {

			String intervalData = currentObj.get("interval").getAsString();

			JsonArray metricsArray = currentObj.getAsJsonArray("metrics");

			if (metricsArray.get(0).getAsJsonObject().get("stats") != null) {

			    JsonObject statsObj = metricsArray.get(0).getAsJsonObject().get("stats").getAsJsonObject();

			    if (Objects.nonNull(statsObj.get("count")) && Objects.nonNull(statsObj.get("sum"))) {

				String dateInString = intervalData.substring(intervalData.indexOf("/") + 1);

				String timeInterval = contactDetailUitiliyService.getTimeIntervalString(dateInString);

				String dow = contactDetailUitiliyService.getDayOfWeekFromCSTString(dateInString);

				double waittime_in_minutes = this.calculateAverateWaitTimeInMinutes(statsObj);

				LOG.debug("Into WaitTimeChartImpl service dateInString {}", dateInString);
				
				LOG.debug("Into WaitTimeChartImpl service timeInterval {}", timeInterval);
				
				LOG.debug("Into WaitTimeChartImpl service dow {}", dow);
				
				this.updateWaitTime(waitTimeChartMap,dow, timeInterval, waittime_in_minutes);
			    }

			} else {

			    errorFlag = true;

			    break;
			}

		    } else {

			errorFlag = true;

			break;
		    }
		}
	    }

	    if (errorFlag) {

		waitTimeChartMap.clear();

		contactDetailUitiliyService.generalExceptionHandling(
			ErrorCodeMapping.WAITTIME_CHART_SERVICE_EXCEPTION.getErrorCode(),
			ErrorCodeMapping.WAITTIME_CHART_SERVICE_EXCEPTION.getErrorMessage());

	    }

	} catch (ServiceException e) {

	    LOG.error("ServiceException Occured in token service {} ", e);
	}
	
	return waitTimeChartMap;
    }

    private void updateWaitTime(HashMap<String, LinkedHashMap<String, Object>> waitTimeChartMap,String dayOfWeek, String timeInterval, double waittime) {

	if (waitTimeChartMap.containsKey(dayOfWeek)) {

	    LinkedHashMap<String, Object> dayOfWeekMap = waitTimeChartMap.get(dayOfWeek);

	    if (dayOfWeekMap.containsKey(timeInterval)) {

		double currentData = (double) dayOfWeekMap.get(timeInterval);
		
		LOG.debug("Into WaitTimeChartImpl currentData {}", currentData);
		double newData = waittime;
		if(currentData > 0) {
		    newData = (currentData + waittime) / 2;
		}
		LOG.debug("Into WaitTimeChartImpl newData {}", newData);

		//String value = decimalFormat.format(newData);

		//newData = Double.parseDouble(value);

		BigDecimal bd=new BigDecimal(newData).setScale(2, RoundingMode.HALF_DOWN);

		newData = bd.doubleValue();

		//newData = Double.parseDouble(decimalFormat.format(newData));

		dayOfWeekMap.put(timeInterval, newData);
	    }
	}
	LOG.debug("Into WaitTimeChartImpl after update interval {}", waitTimeChartMap);
    }

    private double calculateAverateWaitTimeInMinutes(JsonObject statsObj) {

	int count = statsObj.get("count").getAsInt();

	long sum = statsObj.get("sum").getAsLong();

	double sumInDouble = Double.valueOf(sum);

	if(count > 0) {
	    double waittime_in_minutes = (sumInDouble / count) / (60 * 1000);
	    return waittime_in_minutes;
	}
	else {
	    double waittime_in_minutes = sumInDouble/(60 * 1000);
	    return waittime_in_minutes;
	}

    }

    private JsonArray getResultDataSet(JsonObject serviceJson) {

	JsonArray serviceDataArray = null;

	JsonArray arr = serviceJson.getAsJsonArray(RESULTS_JSON);

	if (Objects.nonNull(arr) && arr.size() > 0 && Objects.nonNull(arr.get(0))) {

	    JsonArray dataArray = arr.get(0).getAsJsonObject().getAsJsonArray("data");

	    if (Objects.nonNull(dataArray) && dataArray.size() > 0) {

		serviceDataArray = dataArray;
	    }

	}

	return serviceDataArray;
    }

    private String convertToJSONArray(HashMap<String, LinkedHashMap<String, Object>> waitTimeChartMap_Global) {

	List<HashMap<String, Object>> dayWiseList = new ArrayList<HashMap<String, Object>>();

	String output = null;

	if (Objects.nonNull(waitTimeChartMap_Global) && waitTimeChartMap_Global.size() > 0 && !waitTimeChartMap_Global.isEmpty()) {

	    for (Days day : Days.values()) {

		String dayValue = day.toString();

		dayWiseList.add(waitTimeChartMap_Global.get(dayValue));
	    }

	    Gson gson = new GsonBuilder().setPrettyPrinting().create();

	    output = gson.toJson(dayWiseList);

	}

	return output;
    }
}
