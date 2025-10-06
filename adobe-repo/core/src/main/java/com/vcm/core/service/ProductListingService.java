package com.vcm.core.service;

import org.apache.sling.api.resource.ResourceResolver;

public interface ProductListingService{
	String getFundList(ResourceResolver resourceResolver);

	String getFundListEndPoint();
	
	String getFundListApiKey();

	public String getETFList(ResourceResolver resourceResolver);

	public String getEtfListEndpoint();

	String getStrategyList(ResourceResolver resourceResolver);

	String getStrategyListEndPoint();

	String getProductApiKey();
	
	String getHighIndexEndPoint();
}
