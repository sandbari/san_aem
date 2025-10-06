package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.vcm.core.pojo.OverlayContentBean;
import com.vcm.core.utils.UtilityService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.constants.Constant;
import com.vcm.core.service.ProductListingService;
import com.vcm.core.service.VCMSiteConfiguationService;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FundListModel {

	private static final Logger log = LoggerFactory.getLogger(DisclaimerTextModel.class);
	
	@Inject
	private VCMSiteConfiguationService vcmSiteConfigService;
	
	@Inject
	private ProductListingService productListingService;

	@Inject
	private String riskContentPath;

	@Inject
	List<OverlayPathsModel> overlayPaths;
	
	private String fundList=StringUtils.EMPTY;
	
	private String fundListEndpoint=StringUtils.EMPTY;

	private String fundListApiKey=StringUtils.EMPTY;

	private String mfLabels;

	private List<OverlayContentBean> overlayList = new ArrayList<OverlayContentBean>();

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

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
		fundList=productListingService.getFundList(resourceResolver);
		fundListEndpoint=productListingService.getFundListEndPoint();
		fundListApiKey=productListingService.getFundListApiKey();

		Gson gson = new Gson();
		Map<String, Object> productLabels = vcmSiteConfigService.getLabelConfigAsMap();
		if(null != productLabels && !productLabels.isEmpty()) {
			mfLabels = gson.toJson(productLabels.get(Constant.MF_LABELS));
		}
	}

	public void setVcmSiteConfigService(VCMSiteConfiguationService vcmSiteConfigService) {
		this.vcmSiteConfigService = vcmSiteConfigService;
	}

	/**
	 * @return the fundList
	 */
	public String getFundList() {
		return fundList;
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
	 * @return the fundListEndpoint
	 */
	public String getFundListEndpoint() {
		return fundListEndpoint;
	}

	/**
	 * @param fundListEndpoint the fundListEndpoint to set
	 */
	public void setFundListEndpoint(String fundListEndpoint) {
		this.fundListEndpoint = fundListEndpoint;
	}

	/**
	 * @return the fundListApiKey
	 */
	public String getFundListApiKey() {
		return fundListApiKey;
	}

	/**
	 * @param fundListApiKey the fundListApiKey to set
	 */
	public void setFundListApiKey(String fundListApiKey) {
		this.fundListApiKey = fundListApiKey;
	}

	public List<OverlayContentBean> getOverlayList() {
		List<OverlayContentBean> copyoverlayList = overlayList;
		Collections.copy(copyoverlayList, overlayList);
		return copyoverlayList;
	}

	public String getRiskContent() {
		return riskContent;
	}

	public void setRiskContent(String riskContent) {
		this.riskContent = riskContent;
	}

	public String getMfLabels() {
		return mfLabels;
	}

	public void setMfLabels(String mfLabels) {
		this.mfLabels = mfLabels;
	}


}
