package com.vcm.core.contactuschart.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.CurrentWaitTimeService;
import com.vcm.core.contactuschart.service.OpenHoursFinder;
import com.vcm.core.contactuschart.service.OpenHoursScheduleGenerator;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.utils.FileHandlerService;

@Component(service = OpenHoursFinder.class, immediate = true)
public class OpenHoursFinderImpl implements OpenHoursFinder {

    private static final String IS_OPEN_NOW = "isOpenNow";

    private static final String IS_HOLIDAY = "isHoliday";

    private static final String HOLIDAY_SCHEDULE_JSON_KEY = "holiday_schedule_data";

    private static final String OPEN_HOURS_JSON_KEY = "open_hours_data";

    private static Logger LOG = LoggerFactory.getLogger(OpenHoursFinderImpl.class);;

    private static DateTimeFormatter DATE_TIME_FORMATTER_CST = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
	    .withZone(Constant.CENTRAL_ZONE_ID);

    private ArrayList<LocalDate> holidays = null;

    private HashMap<String, LocalTime> openCloseHoursMap=null;

    private HashMap<LocalDate, LocalTime> closingEarlyHoursMap = null;

    @Reference
    private ContactDetailUitiliyService contactDetailUitiliyService;
    
    @Reference
    private ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs;

    @Reference
    private OpenHoursScheduleGenerator openHoursScheduleGenerator;

    @Reference
    private CurrentWaitTimeService currentWaitTimeService;
    
    @Reference
    private FileHandlerService fileHandlerService;

    public void setContactDetailUitiliyService(ContactDetailUitiliyService contactDetailUitiliyService) {
		this.contactDetailUitiliyService = contactDetailUitiliyService;
	}

	public void setContactDetailWaitTimeAPIConfigs(ContactDetailWaitTimeAPIConfigs contactDetailWaitTimeAPIConfigs) {
		this.contactDetailWaitTimeAPIConfigs = contactDetailWaitTimeAPIConfigs;
	}

	public void setOpenHoursScheduleGenerator(OpenHoursScheduleGenerator openHoursScheduleGenerator) {
		this.openHoursScheduleGenerator = openHoursScheduleGenerator;
	}

	public void setCurrentWaitTimeService(CurrentWaitTimeService currentWaitTimeService) {
		this.currentWaitTimeService = currentWaitTimeService;
	}

	public void setFileHandlerService(FileHandlerService fileHandlerService) {
		this.fileHandlerService = fileHandlerService;
	}

    private JsonObject openHoursResponse = null;

    @Override
    public JsonObject getOpenCloseHours() {
	
	openCloseHoursMap = new HashMap<String, LocalTime>();
	
	closingEarlyHoursMap = new HashMap<LocalDate, LocalTime>();
	holidays = new ArrayList<LocalDate>();

	JsonObject responseObj = new JsonObject();

	Map<String, Boolean> isOpenNow = null;

	this.intializeConfigs();

	if (Objects.nonNull(openCloseHoursMap) && openCloseHoursMap.size() > 0) {

	    if (Objects.nonNull(holidays) && holidays.size() > 0) {

		if (Objects.nonNull(closingEarlyHoursMap) && closingEarlyHoursMap.size() > 0) {

		    isOpenNow = this.isOpenNow();
		    
		    if(Objects.nonNull(isOpenNow) && isOpenNow.containsKey(IS_HOLIDAY) && isOpenNow.containsKey(IS_OPEN_NOW)) {
			
			responseObj.addProperty(IS_OPEN_NOW, isOpenNow.get(IS_OPEN_NOW));
			
			responseObj.addProperty(IS_HOLIDAY , isOpenNow.get(IS_HOLIDAY));

		    }
		    else {
			responseObj.addProperty(IS_OPEN_NOW, false);
			
			responseObj.addProperty(IS_HOLIDAY , false);
		    }
		    responseObj.addProperty("open_hours", this.convertTimeFormat(openCloseHoursMap));

		    responseObj.addProperty("current_waittime", currentWaitTimeService.getCurrentWaitTime());

		}
	    }
	}

	return responseObj;
    }

    private void intializeConfigs() {

	JsonParser jsonparser = new JsonParser();

	String filePath = contactDetailWaitTimeAPIConfigs.openHoursScheduleFilePath();

	StringBuilder openHoursResponseSb = fileHandlerService.readFile(filePath);

	if (Objects.nonNull(openHoursResponseSb) && openHoursResponseSb.length() > 0) {

	    String openHoursResponseString = openHoursResponseSb.toString();

	    LOG.debug("Inside OpenHoursFinder and  openHoursResponseString is : {}", openHoursResponseString);

	    if (Objects.nonNull(openHoursResponseString) && !StringUtils.isEmpty(openHoursResponseString)) {

		openHoursResponse = jsonparser.parse(openHoursResponseString).getAsJsonObject();

		if (Objects.nonNull(openHoursResponse)) {

		    JsonArray open_hours_data_array = openHoursResponse.getAsJsonArray(OPEN_HOURS_JSON_KEY);

		    JsonArray dataArray = openHoursResponse.getAsJsonArray(HOLIDAY_SCHEDULE_JSON_KEY);

		    this.processOpenHoursScheduleData(open_hours_data_array);

		    this.processHolidayScheduleData(dataArray);

		}
	    }
	}
    }

    private void processOpenHoursScheduleData(JsonArray open_hours_data_array) {

	if (Objects.nonNull(open_hours_data_array) && open_hours_data_array.size() > 0) {

	    JsonObject openHoursJsonObj = open_hours_data_array.get(0).getAsJsonObject();

	    if (openHoursJsonObj.get("start") != null) {

		String start = openHoursJsonObj.get("start").getAsString();

		String end = openHoursJsonObj.get("end").getAsString();

		ZonedDateTime zdtStart = ZonedDateTime.parse(start, DATE_TIME_FORMATTER_CST);

		ZonedDateTime zdtEnd = ZonedDateTime.parse(end, DATE_TIME_FORMATTER_CST);

		openCloseHoursMap.put("openfrom", zdtStart.toLocalTime());

		openCloseHoursMap.put("closedfrom", zdtEnd.toLocalTime());
	    }
	}
    }

    private void processHolidayScheduleData(JsonArray dataArray) {

	if (Objects.nonNull(dataArray) && dataArray.size() > 0) {

	    for (int i = 0; i < dataArray.size(); i++) {

		JsonObject currentObj = dataArray.get(i).getAsJsonObject();

		if (currentObj.get("start") != null) {

		    String start = currentObj.get("start").getAsString();

		    ZonedDateTime zdtInstanceAtOffset = ZonedDateTime.parse(start, DATE_TIME_FORMATTER_CST);

		    if (zdtInstanceAtOffset.getHour() == 0 && zdtInstanceAtOffset.getMinute() == 0
			    && zdtInstanceAtOffset.getSecond() == 0) {

			LocalDate holidayDate = zdtInstanceAtOffset.toLocalDate();

			holidays.add(holidayDate);

		    } else {

			LocalDate closingEarlyDate = zdtInstanceAtOffset.toLocalDate();

			LocalTime closingEarlyHours = zdtInstanceAtOffset.toLocalTime();

			closingEarlyHoursMap.put(closingEarlyDate, closingEarlyHours);
		    }
		}
	    }
	}
    }

    public Map<String, Boolean> isOpenNow() {

	Instant instant = Instant.now();
	
	Map<String, Boolean> openStatus = new HashMap<String, Boolean>();

	ZonedDateTime currentCSTTime = instant.atZone(Constant.CENTRAL_ZONE_ID);

	// check if the current time is falling under any of the holiday schedules

	LocalDate currentDate = currentCSTTime.toLocalDate();

	LocalTime currentTime = currentCSTTime.toLocalTime();

	if (holidays.contains(currentDate)) {

	    openStatus.put(IS_HOLIDAY, true);
	    openStatus.put(IS_OPEN_NOW, false);

	} else if (closingEarlyHoursMap.containsKey(currentDate)) {

	    if (currentTime.isBefore(openCloseHoursMap.get("openfrom"))
		    || currentTime.minusMinutes(1).isAfter(closingEarlyHoursMap.get(currentDate))) {

		 openStatus.put(IS_HOLIDAY, false);
		 openStatus.put(IS_OPEN_NOW, false);
	    }

	} else if (currentDate.getDayOfWeek().toString().equalsIgnoreCase("Saturday")
		|| currentDate.getDayOfWeek().toString().equalsIgnoreCase("Sunday")) {

	    openStatus.put(IS_HOLIDAY, true);
	    openStatus.put(IS_OPEN_NOW, false);

	} else if (currentTime.isBefore(openCloseHoursMap.get("openfrom"))
		|| currentTime.minusMinutes(1).isAfter(openCloseHoursMap.get("closedfrom"))) {

	    openStatus.put(IS_HOLIDAY, false);
	    openStatus.put(IS_OPEN_NOW, false);

	} else {

	    openStatus.put(IS_HOLIDAY, false);
	    openStatus.put(IS_OPEN_NOW, true);
	}

	return openStatus;
    }

    private String convertTimeFormat(HashMap<String, LocalTime> openTimeMap) {

	String result = null;

	LocalTime openHours = openTimeMap.get("openfrom");

	LocalTime closeHours = openTimeMap.get("closedfrom");

	if (Objects.nonNull(openHours) && Objects.nonNull(closeHours)) {

	    String openHoursString = LocalTime.parse(openHours.toString(), DateTimeFormatter.ofPattern("HH:mm"))
		    .format(DateTimeFormatter.ofPattern("hh:mma"));

	    String closeHoursString = LocalTime.parse(closeHours.toString(), DateTimeFormatter.ofPattern("HH:mm"))
		    .format(DateTimeFormatter.ofPattern("hh:mma"));

	    result = openHoursString.toLowerCase() + "-" + closeHoursString.toLowerCase() + " (CST)";

	}

	return result;
    }
}