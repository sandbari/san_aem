package com.vcm.core.contactuschart.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.exceptions.ServiceException;
import com.vcm.core.contactuschart.service.ContactDetailWaitTimeAPIConfigs;
import com.vcm.core.contactuschart.service.CurrentWaitTimeService;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.contactuschart.utils.ErrorCodeMapping;

@Component(service = CurrentWaitTimeService.class, immediate = true)
public class CurrentWaitTimeServiceImpl implements CurrentWaitTimeService {

    private static final String ESTIMATED_WAIT_TIME_SECONDS = "estimatedWaitTimeSeconds";

    private static final int timeout = 10;

    private static Logger LOG = LoggerFactory.getLogger(CurrentWaitTimeServiceImpl.class);

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

    private String access_token = null;

    private String current_waittime_endpoint = null;

    private void initializeConfigParams() {

	access_token = genesysAuthTokenGenerator.getAuthToken();

	current_waittime_endpoint = contactDetailWaitTimeAPIConfigs.currentWaitTimeEndpoint();

	LOG.debug("Into CurrentWaitTimeService config current_waittime_endpoint: {}", current_waittime_endpoint);
    }

    @Override
    public String getCurrentWaitTime() {

	String currentwaittime = "0";

	long starttime = System.currentTimeMillis();

	JsonObject jsonObj = this.getJsonFromService();

	LOG.debug("Service Json {}", jsonObj);

	if (Objects.nonNull(jsonObj)) {

	    JsonElement results = jsonObj.get("results");

	    if (Objects.nonNull(results)) {

		JsonArray resultsArray = results.getAsJsonArray();

		if (resultsArray != null && resultsArray.size() > 0) {

		    JsonObject currentObj = resultsArray.get(0).getAsJsonObject();

		    JsonElement estimatedWaitTime = currentObj.get(ESTIMATED_WAIT_TIME_SECONDS);

		    if (Objects.nonNull(estimatedWaitTime)) {

			long currentwaittimeMin = TimeUnit.SECONDS.toMinutes(estimatedWaitTime.getAsLong());

			if (currentwaittimeMin < 1) {

			    currentwaittime = estimatedWaitTime.getAsLong() + " sec";
			} else {

			    currentwaittime = currentwaittimeMin + " min";
			}
		    }
		}
	    }
	}

	long endtime = System.currentTimeMillis();

	LOG.debug("CurrentWaitTimeServiceImpl time taken : {}ms", (endtime - starttime));

	return currentwaittime;
    }

    @Override
    public JsonObject getJsonFromService() {

	this.initializeConfigParams();

	JsonObject jsonObject = null;

	CloseableHttpClient client = null;

	CloseableHttpResponse response = null;

	int statusCode;

	try {

	    if (Objects.nonNull(current_waittime_endpoint) && !current_waittime_endpoint.equals(StringUtils.EMPTY)) {

		HttpGet request = new HttpGet(current_waittime_endpoint);

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
			    ErrorCodeMapping.CURRENTWAITTIME_SERVICE_EXCEPTION.getErrorMessage());
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
}
