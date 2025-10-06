package com.vcm.core.contactuschart.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.exceptions.ServiceException;
import com.vcm.core.contactuschart.service.GenesysAuthTokenGenerator;
import com.vcm.core.contactuschart.utils.ContactDetailUitiliyService;
import com.vcm.core.contactuschart.utils.ErrorCodeMapping;

@Component(service = GenesysAuthTokenGenerator.class, immediate = true)
@Designate(ocd = GenesysAuthTokenGeneratorImpl.Config.class)
public class GenesysAuthTokenGeneratorImpl implements GenesysAuthTokenGenerator {

    private static Logger LOG = LoggerFactory.getLogger(GenesysAuthTokenGeneratorImpl.class);

    private static int timeout = 100;

    private String authTokenAPIEndPoint = "";

    private String clientId = "";

    private String clientSecret = "";

    @Reference
    private ContactDetailUitiliyService contactDetailUitiliyService;

    public void setContactDetailUitiliyService(ContactDetailUitiliyService contactDetailUitiliyService) {
		this.contactDetailUitiliyService = contactDetailUitiliyService;
	}

    @Activate
    @Modified
    protected void activate(final Config config) {

	LOG.debug("Into GenesysAuthTokenGeneratorImpl service Start");

	this.authTokenAPIEndPoint = String.valueOf(config.authTokenEndpointUrl());

	this.clientId = String.valueOf(config.clientId());

	this.clientSecret = String.valueOf(config.clientSecret());

	LOG.debug("GenesysAuthTokenGeneratorImpl Configs authTokenAPIEndPoint: {} clientId:{} clientSecret: {}",authTokenAPIEndPoint,clientId,clientSecret);
	
	LOG.debug("Into GenesysAuthTokenGeneratorImpl service END");

    }

    @Override
    public String getAuthToken() {

	String auth_token = null;

	CloseableHttpClient client = null;

	CloseableHttpResponse response = null;

	int statusCode;

	try {

	    if (Objects.nonNull(authTokenAPIEndPoint) && !authTokenAPIEndPoint.equals(StringUtils.EMPTY)
		    && Objects.nonNull(clientId) && Objects.nonNull(clientSecret)) {

		HttpPost request = new HttpPost(authTokenAPIEndPoint);

		request.setHeader("Content-Type", Constant.CONTENTTYPE_FORMURLENCODED);

		String clientAuth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

		LOG.debug("clientAuth {}", clientAuth);

		request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + clientAuth);

		ArrayList<NameValuePair> postParameters;

		postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));

		request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
			.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		response = client.execute(request);

		statusCode = response.getStatusLine().getStatusCode();
		
		LOG.debug("statusCode {}", statusCode);

		if (!contactDetailUitiliyService.isError(statusCode)) {

		    String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());

		    JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();

		    auth_token = jsonObject.get("access_token").getAsString();

		    LOG.debug("GenesysAuthTokenGeneratorImpl access_token {}", auth_token);

		} else {

		    LOG.error("Recieved response with error status: {}", statusCode);

		    contactDetailUitiliyService.generalExceptionHandling(statusCode,ErrorCodeMapping.GENESYS_AUTHSERVICE_EXCEPTION.getErrorMessage());
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

	return auth_token;
    }

    @ObjectClassDefinition(name = "VCM Genesys Tocken Generator Configuration", description = "This service contains "
	    + "all VCM Genesys Tocken Generator Configuration")
    public @interface Config {
	
	@AttributeDefinition(name = "Genesys Token Generator URL", type = AttributeType.STRING)
	String authTokenEndpointUrl() default "https://login.usw2.pure.cloud/oauth/token"; // StringUtils.EMPTY;

	@AttributeDefinition(name = "Client ID", type = AttributeType.STRING)
	String clientId() default "08551d14-9fcd-41fe-8252-78b928cb5e5a";// StringUtils.EMPTY;

	@AttributeDefinition(name = "Client Secret Key", type = AttributeType.STRING)
	String clientSecret() default "St5UHUySQbjJghtGr49UyxnOsJt04ketQnLjwFlmhho"; // StringUtils.EMPTY;
    }

}
