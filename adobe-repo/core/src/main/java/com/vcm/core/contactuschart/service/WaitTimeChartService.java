package com.vcm.core.contactuschart.service;

import com.google.gson.JsonObject;

public interface WaitTimeChartService {
	public String generateWaittimeChartJson();
	public JsonObject getJsonFromService(String waittime_history_endpoint, String interval, String queue_id);
}
