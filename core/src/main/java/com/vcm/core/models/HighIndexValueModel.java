package com.vcm.core.models;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.ProductListingService;
import com.vcm.core.service.VCMSiteConfiguationService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HighIndexValueModel {

	private static final Logger log = LoggerFactory.getLogger(DisclaimerTextModel.class);

	@Inject
	private VCMSiteConfiguationService vcmSiteConfigService;
	
	@Inject
	private ProductListingService productListingService;

	private String highIndexEndpoint = StringUtils.EMPTY;

	private String highIndexApiKey = StringUtils.EMPTY;
	
	private String apiCallFailureMessage;
	
	private String highIndexLabels;

	/**
	 * Init Methods
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside init method of FundListModel");
		highIndexEndpoint = productListingService.getHighIndexEndPoint();
		highIndexApiKey = productListingService.getProductApiKey();
		Map<String, Object> labels = vcmSiteConfigService.getLabelConfigAsMap();
		Gson gson = new Gson();
		if(null != labels && !labels.isEmpty()) {
			apiCallFailureMessage = labels.get(Constant.API_CALL_FAILURE_MESSAGE_KEY).toString();
			highIndexLabels = gson.toJson(labels.get(Constant.HIGH_INDEX_LABELS));
		}
	}
	
	public String getApiCallFailureMessage() {
		return apiCallFailureMessage;
	}

	
	/**
	 * @return the highIndexLabels
	 */
	public String getHighIndexLabels() {
		return highIndexLabels;
	}

	/**
	 * @param highIndexLabels the highIndexLabels to set
	 */
	public void setHighIndexLabels(String highIndexLabels) {
		this.highIndexLabels = highIndexLabels;
	}

	/**
	 * @param productListingService
	 */
	public void setProductListingService(ProductListingService productListingService) {
		this.productListingService = productListingService;
	}

	/**
	 * @return highIndexEndpoint
	 */
	public String getHighIndexEndpoint() {
		return highIndexEndpoint;
	}

	/**
	 * @param highIndexEndpoint
	 */
	public void setHighIndexEndpoint(String highIndexEndpoint) {
		this.highIndexEndpoint = highIndexEndpoint;
	}

	/**
	 * @return highIndexApiKey
	 */
	public String getHighIndexApiKey() {
		return highIndexApiKey;
	}

	/**
	 * @param highIndexApiKey
	 */
	public void setHighIndexApiKey(String highIndexApiKey) {
		this.highIndexApiKey = highIndexApiKey;
	}

	public void setVcmSiteConfigService(VCMSiteConfiguationService vcmSiteConfigService) {
		this.vcmSiteConfigService = vcmSiteConfigService;
	}

}
