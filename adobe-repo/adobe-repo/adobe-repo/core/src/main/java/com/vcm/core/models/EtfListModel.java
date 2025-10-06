package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import com.vcm.core.pojo.OverlayContentBean;
import com.vcm.core.service.ProductListingService;
import com.vcm.core.service.VCMSiteConfiguationService;
import com.vcm.core.utils.UtilityService;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EtfListModel {

	private static final Logger log = LoggerFactory.getLogger(DisclaimerTextModel.class);
	
	@Inject
	private VCMSiteConfiguationService vcmSiteConfigService;
	
	@Inject
	private ProductListingService productListingService;
	
	private String etfList=StringUtils.EMPTY;
	
	private String etfListEndpoint=StringUtils.EMPTY;

	private String etfListApiKey=StringUtils.EMPTY;
	
	private String etfLabels;
	
	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;
	
	@Inject
	private String riskContentPath;

	@Inject
	List<OverlayPathsModel> overlayPaths;
	
	private List<OverlayContentBean> overlayList = new ArrayList<OverlayContentBean>();
	
	OverlayContentBean overlayContentBean;

	String riskContent;

	/**
	 * Init Methods
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside init method of FundListModel");
		if (null != overlayPaths) {
			for (OverlayPathsModel overlays : overlayPaths) {
				overlayContentBean = new OverlayContentBean();
				overlayContentBean.setOverlayContent(UtilityService.getProductDetailOverlayContent(overlays.overlayContentPath(), resourceResolver, "popuptext"));
				overlayContentBean.setOverlayId(UtilityService.getProductDetailOverlayContent(overlays.overlayContentPath(), resourceResolver, "popupId"));
				overlayContentBean.setOverlayLinkText(overlays.overlayLinkText());
				overlayContentBean.setOverlayTextPrefix(overlays.overlayTextPrefix());
				overlayList.add(overlayContentBean);
			}
		}
		riskContent = UtilityService.getProductDetailOverlayContent(riskContentPath, resourceResolver, "popuptext");
		etfList=productListingService.getETFList(resourceResolver);
		etfListEndpoint=productListingService.getEtfListEndpoint();
		etfListApiKey=productListingService.getFundListApiKey();
		Gson gson = new Gson();
		Map<String, Object> productLabels = vcmSiteConfigService.getLabelConfigAsMap();
		if(null != productLabels && !productLabels.isEmpty()) {
		etfLabels = gson.toJson(productLabels.get(Constant.ETF_LABELS));
		}
	}



	/**
	 * @return the etfLabels
	 */
	public String getEtfLabels() {
		return etfLabels;
	}



	/**
	 * @param etfLabels the etfLabels to set
	 */
	public void setEtfLabels(String etfLabels) {
		this.etfLabels = etfLabels;
	}



	public void setVcmSiteConfigService(VCMSiteConfiguationService vcmSiteConfigService) {
		this.vcmSiteConfigService = vcmSiteConfigService;
	}

	/**
	 * @return the etfList
	 */
	public String getEtfList() {
		return etfList;
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}


	public void setProductListingService(ProductListingService productListingService) {
		this.productListingService = productListingService;
	}

	/**
	 * @return the etfListEndpoint
	 */
	public String getEtfListEndpoint() {
		return etfListEndpoint;
	}

	/**
	 * @param etfListEndpoint the etfListEndpoint to set
	 */
	public void setEtfListEndpoint(String etfListEndpoint) {
		this.etfListEndpoint = etfListEndpoint;
	}

	/**
	 * @return the etfListApiKey
	 */
	public String getEtfListApiKey() {
		return etfListApiKey;
	}

	/**
	 * @param etfListApiKey the etfListApiKey to set
	 */
	public void setEtfListApiKey(String etfListApiKey) {
		this.etfListApiKey = etfListApiKey;
	}

	/**
	 * @return the riskContent
	 */
	public String getRiskContent() {
		return riskContent;
	}

	/**
	 * @param riskContent the riskContent to set
	 */
	public void setRiskContent(String riskContent) {
		this.riskContent = riskContent;
	}

	/**
	 * @return the overlayList
	 */
	public List<OverlayContentBean> getOverlayList() {
		List<OverlayContentBean> copyoverlayList = overlayList;
		Collections.copy(copyoverlayList, overlayList);
		return copyoverlayList;
	}

}
