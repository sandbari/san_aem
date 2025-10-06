package com.vcm.core.models;


import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.ProductDetailService;
import com.vcm.core.service.VCMSiteConfiguationService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetailsModel {

	private static final Logger log = LoggerFactory.getLogger(ProductDetailsModel.class);

	@Inject
	private ProductDetailService productDetailService;

	@Inject
	private VCMSiteConfiguationService vcmSiteConfigService;


	@Inject
	private Page currentPage;

	private String productDetailEndPoint = StringUtils.EMPTY;

	private String productDetailAPIKey = StringUtils.EMPTY;

	private String[] productsServiceUrl;

	private String configJson;

	private String labelsJson;

	private Map<String, List<String>> themeColorMap;

	private List<String> colorArray;

	private String fundListEndpoint = StringUtils.EMPTY;

	private String fundListApiKey = StringUtils.EMPTY;

	@PostConstruct
	protected void init() {
		log.debug("Inside init method of ProductDetailsModel");
		productDetailEndPoint = productDetailService.getProductDetailEndPoint();
		productDetailAPIKey = productDetailService.getProductDetailAPIKey();
		fundListEndpoint = productDetailService.getFundListEndPoint();
		fundListApiKey = productDetailService.getFundListAPIKey();
		configJson = productDetailService.getConfigAsJson(currentPage);
		Gson gson = new Gson();
		labelsJson = gson.toJson(vcmSiteConfigService.getLabelConfigAsMap());
		themeColorMap = vcmSiteConfigService.getSiteConfigAsMap(Constant.CHART_COLOR_CONFIGURATION_KEY);
		for (Map.Entry<String, List<String>> entry : themeColorMap.entrySet()) {
			colorArray = entry.getValue();
		}
	}

	public ProductDetailService getProductDetailService() {
		return productDetailService;
	}

	public void setProductDetailService(ProductDetailService productDetailService) {
		this.productDetailService = productDetailService;
	}

	public Page getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}

	public String getProductDetailEndPoint() {
		return productDetailEndPoint;
	}

	public void setProductDetailEndPoint(String productDetailEndPoint) {
		this.productDetailEndPoint = productDetailEndPoint;
	}

	public String getProductDetailAPIKey() {
		return productDetailAPIKey;
	}

	public void setProductDetailAPIKey(String productDetailAPIKey) {
		this.productDetailAPIKey = productDetailAPIKey;
	}

	public String[] getProductsServiceUrl() {
		return productsServiceUrl.clone();
	}

	public void setProductsServiceUrl(String[] productsServiceUrl) {
		String[] copyProductsServiceUrl = productsServiceUrl.clone();
		this.productsServiceUrl = copyProductsServiceUrl;
	}

	public String getConfigJson() {
		return configJson;
	}

	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}

	public String getStrategyDetailEndPoint() {
		String strategyEndPoint = productDetailService.getStrategyDetailEndPoint() + currentPage.getProperties().get("fundID", String.class);
		return strategyEndPoint;
	}

	public String getIndexDetailEndPoint() {
		String indexEndPoint = productDetailService.getIndexDetailEndPoint() + currentPage.getProperties().get("fundID", String.class);
		return indexEndPoint;
	}

	public String getLabelsJson() {
		return labelsJson;
	}

	public void setLabelsJson(String labelsJson) {
		this.labelsJson = labelsJson;
	}

	public VCMSiteConfiguationService getVcmSiteConfigService() {
		return vcmSiteConfigService;
	}

	public void setVcmSiteConfigService(VCMSiteConfiguationService vcmSiteConfigService) {
		this.vcmSiteConfigService = vcmSiteConfigService;
	}

	public List<String> getColorArray() {
		List<String> copyColorArray = colorArray;
		Collections.copy(copyColorArray, colorArray);
		return copyColorArray;
	}

	public void setColorArray(List<String> colorArray) {
		List<String> copyColorArray = new ArrayList<String>();
		copyColorArray.addAll(colorArray);
		this.colorArray = copyColorArray;
	}

	public String getFundListEndpoint() {
		return fundListEndpoint;
	}

	public void setFundListEndpoint(String fundListEndpoint) {
		this.fundListEndpoint = fundListEndpoint;
	}

	public String getFundListApiKey() {
		return fundListApiKey;
	}

	public void setFundListApiKey(String fundListApiKey) {
		this.fundListApiKey = fundListApiKey;
	}
}