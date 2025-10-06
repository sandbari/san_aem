package com.vcm.core.models;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.ProductListingService;
import com.vcm.core.service.VCMSiteConfiguationService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class StrategyListModel {

	private static final Logger log = LoggerFactory.getLogger(DisclaimerTextModel.class);
	
	@Inject
	private VCMSiteConfiguationService vcmSiteConfigService;
	
	@Inject
	private ProductListingService productListingService;

	private String strategyList = StringUtils.EMPTY;

	private String strategyListEndpoint = StringUtils.EMPTY;

	private String strategyListApiKey = StringUtils.EMPTY;
	
	private String apiCallFailureMessage;
	
	private String strategyListLabels;

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

	/**
	 * Init Methods
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside init method of FundListModel");
		strategyList = productListingService.getStrategyList(resourceResolver);
		strategyListEndpoint = productListingService.getStrategyListEndPoint();
		strategyListApiKey = productListingService.getProductApiKey();
		Map<String, Object> labels = vcmSiteConfigService.getLabelConfigAsMap();
		Gson gson = new Gson();
		if(null != labels && !labels.isEmpty()) {
			 strategyListLabels = gson.toJson(labels.get(Constant.ST_LABELS));
			 apiCallFailureMessage = labels.get(Constant.API_CALL_FAILURE_MESSAGE_KEY).toString();
		}
	}


	/**
	 * @return the apiCallFailureMessage
	 */
	public String getApiCallFailureMessage() {
		return apiCallFailureMessage;
	}

	/**
	 * @return the strategyListLabels
	 */
	public String getStrategyListLabels() {
		return strategyListLabels;
	}


	/**
	 * @param strategyListLabels the strategyListLabels to set
	 */
	public void setStrategyListLabels(String strategyListLabels) {
		this.strategyListLabels = strategyListLabels;
	}



	public void setProductListingService(ProductListingService productListingService) {
		this.productListingService = productListingService;
	}

	/**
	 * @return strategyList
	 */
	public String getStrategyList() {
		return strategyList;
	}

	/**
	 * @return strategyListEndpoint
	 */
	public String getStrategyListEndpoint() {
		return strategyListEndpoint;
	}

	/**
	 * @param strategyListEndpoint
	 */
	public void setStrategyListEndpoint(String strategyListEndpoint) {
		this.strategyListEndpoint = strategyListEndpoint;
	}

	/**
	 * @return strategyListApiKey
	 */
	public String getStrategyListApiKey() {
		return strategyListApiKey;
	}

	/**
	 * @param strategyListApiKey
	 */
	public void setStrategyListApiKey(String strategyListApiKey) {
		this.strategyListApiKey = strategyListApiKey;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;

	}

	public void setVcmSiteConfigService(VCMSiteConfiguationService vcmSiteConfigService) {
		this.vcmSiteConfigService = vcmSiteConfigService;
	}

}
