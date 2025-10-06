package com.vcm.core.models;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.VCMSiteConfiguationService;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetailsPageModel {

	private static final Logger log = LoggerFactory.getLogger(ProductDetailsPageModel.class);
	private static final String FACTSHEET_URL = "factsheeturl";
	private static final String AD_FACTSHEET_URL = "advisorFactsheetUrl";
	private static final String ALL_HOLDINGS_URL = "allHoldingsUrl";
	private static final String MANAGER_COMMENTARY_URL = "managerCommentaryUrl";
	private static final String PREMIUM_DISCOUNT_REPORT = "premiumDiscountReport";
	private static final String PROSPECTUS_URL = "prospectusUrl";
	private static final String ADVISOR_PROSPECTUS_URL = "advisorprospectusUrl";
	private static final String HIST_INFORMATION_URL = "historicalInformationUrl";
	private static final String GIPS_COMPLIANCE_URL = "gipsComplainceUrl";
	private static final String FUND_ID_TEXT = "fundid";
	private static final String ENTITY_ID_PROPERTY = "fundID";
	private static final String IS_API_DATA_TITLE = "isApiDataTitle";
	
	@Inject
	private VCMSiteConfiguationService vcmSiteConfiguationService;
	
	
	@Inject
	private ConstantsConfigService constantsConfigService;

	@Inject
	private Page currentPage;
	
	@Inject
	@Via("resource")
	private String morningStarText;
	
	@Inject
	@Via("resource")
	private String tickerImage;
	
	@Inject
	@Via("resource")
	private String victorySharesImage;
	
	@Inject
	@Via("resource")
	private String fundDescription;
	
	private String advisorFactSheetUrl;
	private String factsheetUrl;
	private String allHoldingsUrl;
	private String managerCommentaryUrl;
	private String prospectusUrl;
	private String advisorprospectusUrl;
	private String historicalInformationUrl;
	private String purchaseFundUrl;
	private String premiumDiscountReport;
	private Map<String, List<String>> themeColorMap;
	private String themeColor;
	private String gipsComplainceUrl;
	private String titleFromApiData;
	private String activeFixedIncomeTemplate;

	public String getActiveFixedIncomeTemplate() {
		return activeFixedIncomeTemplate;
	}
	
	public String getFactsheetUrl() {
		return factsheetUrl;
	}

	public String getAdvisorFactSheetUrl() {
		return advisorFactSheetUrl;
	}

	public String getGipsComplainceUrl() {
		return gipsComplainceUrl;
	}
	
	public String getMorningStarText() {
		return morningStarText;
	}

	public void setMorningStarText(String morningStarText) {
		this.morningStarText = morningStarText;
	}

	public String getFundDescription() {
		return fundDescription;
	}

	public void setFundDescription(String fundDescription) {
		this.fundDescription = fundDescription;
	}

	public String getPurchaseFundUrl() {
		return purchaseFundUrl;
	}

	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}

	public String getAllHoldingsUrl() {
		return allHoldingsUrl;
	}

	public String getManagerCommentaryUrl() {
		return managerCommentaryUrl;
	}

	public String getProspectusUrl() {
		return prospectusUrl;
	}

	public String getAdvisorprospectusUrl() { return advisorprospectusUrl; }

	public String getHistoricalInformationUrl() {
		return historicalInformationUrl;
	}

	public String getThemeColor() {
		return themeColor;
	}
	
	public String getPremiumDiscountReport() {
		return premiumDiscountReport;
	}

	public void setVcmSiteConfiguationService(VCMSiteConfiguationService vcmSiteConfiguationService) {
		this.vcmSiteConfiguationService = vcmSiteConfiguationService;
	}

	public String getVictorySharesImage() {
		return victorySharesImage;
	}

	public String getTitleFromApiData() {
		return titleFromApiData;
	}

	/**
	 * @param constantsConfigService
	 *            the constantsConfigService to set
	 */
	public void setConstantsConfigService(ConstantsConfigService constantsConfigService) {
		this.constantsConfigService = constantsConfigService;
	}

	@PostConstruct
	protected void init() {
		log.debug("inside init method : current page path{}", currentPage.getPath());
		this.activeFixedIncomeTemplate = "false";
		ValueMap pageProps = currentPage.getProperties();

		this.purchaseFundUrl = constantsConfigService.getPurchaseFundUrl();

		log.debug("<<**purchase fund url in constant config service {}", constantsConfigService.getPurchaseFundUrl());
		if(null != pageProps.get("solutionType", String.class) && pageProps.get("solutionType", String.class).contains("active-fixed-income")) {
			this.activeFixedIncomeTemplate = "true";
		}
		if (null != pageProps.get(FACTSHEET_URL, String.class)) {
			this.factsheetUrl = UtilityService.getPDFLinkUrl(pageProps.get(FACTSHEET_URL, String.class));
		}
		if (null != pageProps.get(IS_API_DATA_TITLE, String.class)) {
			this.titleFromApiData = pageProps.get(IS_API_DATA_TITLE, String.class);
		}
		if (null != pageProps.get(AD_FACTSHEET_URL, String.class)) {
			this.advisorFactSheetUrl = UtilityService.getPDFLinkUrl(pageProps.get(AD_FACTSHEET_URL, String.class));
		}
		if (null != pageProps.get(ALL_HOLDINGS_URL, String.class)) {
			this.allHoldingsUrl = UtilityService.getPDFLinkUrl(pageProps.get(ALL_HOLDINGS_URL, String.class));
		}
		if (null != pageProps.get(MANAGER_COMMENTARY_URL, String.class)) {
			this.managerCommentaryUrl = UtilityService
					.getPDFLinkUrl(pageProps.get(MANAGER_COMMENTARY_URL, String.class));
		}
		if (null != pageProps.get(PREMIUM_DISCOUNT_REPORT, String.class)) {
			this.premiumDiscountReport = UtilityService
					.getPDFLinkUrl(pageProps.get(PREMIUM_DISCOUNT_REPORT, String.class));
		}
		if (null != pageProps.get(PROSPECTUS_URL, String.class)) {
			this.prospectusUrl = pageProps.get(PROSPECTUS_URL, String.class);
		}
		if (null != pageProps.get(ADVISOR_PROSPECTUS_URL, String.class)) {
			this.advisorprospectusUrl = pageProps.get(ADVISOR_PROSPECTUS_URL, String.class);
		}
		if (null != pageProps.get(GIPS_COMPLIANCE_URL, String.class)) {
			this.gipsComplainceUrl = UtilityService.getPDFLinkUrl(pageProps.get(GIPS_COMPLIANCE_URL, String.class));
		}
		if (null != pageProps.get(HIST_INFORMATION_URL, String.class)) {
			this.historicalInformationUrl = UtilityService
					.getPDFLinkUrl(pageProps.get(HIST_INFORMATION_URL, String.class));
		}
		/*String entityId = pageProps.get(TICKER_ID, String.class);
		URIBuilder uriBuilder;
		try {
			if (null != purchaseFundUrl) {
				uriBuilder = new URIBuilder(purchaseFundUrl);
				if (null != entityId) {
				uriBuilder.addParameter(FUND_ID_TEXT, entityId);
					uriBuilder.addParameter(FLOW_TEXT, "prelogin");
				}
				purchaseFundUrl = uriBuilder.build().toString();
				log.debug("<<**purchase fund url after appending ticker {}", purchaseFundUrl);
			}
		} catch (URISyntaxException e) {
			log.error("Error whild building purchase fund url: {}", e);
		}*/

		themeColorMap = vcmSiteConfiguationService.getSiteConfigAsMap(Constant.ETF_THEME_COLOR_CONFIGURATION_KEY);
		for (Map.Entry<String, List<String>> entry : themeColorMap.entrySet()) {
			List<String> colorArray = entry.getValue();
			if (null != colorArray && !colorArray.isEmpty()) {
				themeColor = colorArray.get(0);
			}
		}
	}

	public void setThemeColor(String themeColor) {
		this.themeColor = themeColor;
	}

	public void setVictorySharesImage(String victorySharesImage) {
		this.victorySharesImage = victorySharesImage;
	}

}