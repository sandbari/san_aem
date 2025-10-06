package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.VCMSiteConfiguationService;
import com.vcm.core.utils.UtilityService;
import com.vcm.core.utils.XSSUtils;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VCMPageModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(VCMPageModel.class);
	private static final int PAGE_INDEX = 3;

	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	private ConstantsConfigService constantsConfigService;

	@Inject
	private VCMSiteConfiguationService vcmSiteConfiguationService;

	@Inject
	Resource resource;

	@Inject
	Page currentPage;

	@Inject
	private AudienceSelectorService audienceSelectorService;

	@SlingObject
	SlingHttpServletRequest slingRequest;


	private String analyticsEndPoint;

	private String popupContentPage;

	private String pageHeirarchy;

	private String pageType;

	private String bugHerdUrl;

	private String etfPagePatterns;

	private String homePageConfig;

	private String domainKeyConfig;

	private List<String> allowedAudience = new ArrayList<>();

	private String currentPagePath = "";

	private String currentPageCanonicalUrl = "";

	private String customCanonicalUrl = "";

	private String ogImagePath = "";

	private String ogTitle = "";

	private Map<String,String> cookieKeyConfig = null;

	private String cookieDomain = "";
	private String vcmcorpHome = "";

	/**
	 * The init method to fetch the page level properties
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Start of VCM page Model init");
		pageHeirarchy = getPagePathOfIndex(PAGE_INDEX);
		// getting page type
		if(Objects.nonNull(currentPage)) {
			ogTitle = currentPage.getTitle();
			currentPagePath=currentPage.getPath();
			if (null != currentPage.getProperties().get("pageCategory")) {
				String pageCategory = currentPage.getProperties().get("pageCategory").toString();
				TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
				if (null != tagManager.resolve(pageCategory))
					pageType = tagManager.resolve(pageCategory).getTitle();
			}
			if (null != currentPage.getProperties().get("canonicalUrl")) {
				customCanonicalUrl = currentPage.getProperties().get("canonicalUrl").toString();
			}
			if (null != currentPage.getProperties().get("ogImage")) {
				ogImagePath = currentPage.getProperties().get("ogImage").toString();
				Resource jcrContentResource = currentPage.getContentResource();
				if (Objects.nonNull(jcrContentResource)) {
					String domainValue = audienceSelectorService.getMappedDomainForPage(jcrContentResource);
					if (Objects.nonNull(domainValue)) {
						ogImagePath = domainValue + ogImagePath;
					}
				}
			}
		}
		analyticsEndPoint = constantsConfigService.getAnalyticsEndPoint();
		popupContentPage = constantsConfigService.getPopupContentFragmentPage();
		bugHerdUrl = constantsConfigService.getBugHerdUrl();
		etfPagePatterns = vcmSiteConfiguationService.getSiteConfigAsJson(Constant.ETF_PAGE_PATTERN_CONFIGURATION_KEY);
		homePageConfig=audienceSelectorService.getHomePageConfig();
		domainKeyConfig=audienceSelectorService.getDomainKeyVsCookieConfig();
		allowedAudience= audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
		cookieKeyConfig=audienceSelectorService.getCookieKeyConfig();
		vcmcorpHome =audienceSelectorService.getVcmcorpHome();
		cookieDomain=constantsConfigService.getCookieDomain();
		currentPageCanonicalUrl = this.getCanonicalUrl();
		LOGGER.debug("etfPatterns is {}",etfPagePatterns);
		LOGGER.debug("End of VCM page Model init {}", analyticsEndPoint);
	}

	/**
	 * @return the constantsConfigService
	 */
	public ConstantsConfigService getConstantsConfigService() {
		return constantsConfigService;
	}

	/**
	 * @param constantsConfigService the constantsConfigService to set
	 */
	public void setConstantsConfigService(ConstantsConfigService constantsConfigService) {
		this.constantsConfigService = constantsConfigService;
	}

	/**
	 * @return the analyticsEndPoint
	 */
	public String getAnalyticsEndPoint() {
		return analyticsEndPoint;
	}

	/**
	 * @return the popupContentPage
	 */
	public String getPopupContentPage() {
		return UtilityService.identifyLinkUrl(popupContentPage, resourceResolver);
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	/**
	 * @param popupContentPage the popupContentPage to set
	 */
	public void setPopupContentPage(String popupContentPage) {
		this.popupContentPage = popupContentPage;
	}

	/**
	 * @return pageHeirarchy.
	 */
	public String getPageHeirarchy() {
		return pageHeirarchy;
	}

	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the pageType
	 */
	public String getPageType() {
		return pageType;
	}

	/**
	 * Get the page path of the given index.
	 *
	 * @param the startsWith
	 * @return the pagePath
	 */
	public String getPagePathOfIndex(int startsWith) {
		int depth = currentPage.getDepth();
		String pagePath = StringUtils.EMPTY;
		StringBuilder sb = new StringBuilder(pagePath);
		for (int i = startsWith; i < depth; i++) {
			if (null != currentPage.getAbsoluteParent(i)) {
				sb.append(currentPage.getAbsoluteParent(i).getTitle());
				sb.append("|");
			}
		}
		sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}

	public String getBugHerdUrl() {
		return bugHerdUrl;
	}

	/**
	 * @return the eftPagePatterns
	 */
	public String getEtfPagePatterns() {
		return XSSUtils.encodeQueryParameter(etfPagePatterns);
	}

	/**
	 * @param vcmSiteConfiguationService the vcmSiteConfiguationService to set
	 */
	public void setVcmSiteConfiguationService(VCMSiteConfiguationService vcmSiteConfiguationService) {
		this.vcmSiteConfiguationService = vcmSiteConfiguationService;
	}

	public String getHomePageConfig() {
		return homePageConfig;
	}

	/**
	 * @param audienceSelectorService the audienceSelectorService to set
	 */
	public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
		this.audienceSelectorService = audienceSelectorService;
	}

	public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}

	/**
	 * @return the domainConfig
	 */
	public String getDomainKeyConfig() {
		return domainKeyConfig;
	}

	/**
	 * @return the currentPagePath
	 */
	public String getCurrentPagePath() {
		return UtilityService.identifyLinkUrl(currentPagePath, resourceResolver);
	}


	public String getCanonicalUrl() {

		String pagePath =StringUtils.EMPTY;

		if (Objects.nonNull(currentPage)) {

			pagePath = currentPage.getPath();

			pagePath = UtilityService.identifyLinkUrl(pagePath, resourceResolver);

			Resource jcrContentResource = currentPage.getContentResource();

			if (Objects.nonNull(jcrContentResource)) {

				String domainValue = audienceSelectorService.getMappedDomainForPage(jcrContentResource);

				if (Objects.nonNull(domainValue)) {

					pagePath = domainValue + pagePath;
				}

			}

		}

		return pagePath;

	}

	/**
	 * @return the currentPageCanonicalUrl
	 */
	public String getCurrentPageCanonicalUrl() {
		return currentPageCanonicalUrl;
	}

	/**
	 * @return the cookieKeyConfig
	 */
	public String getCookieKeyConfig() {

		String jsonString = "";

		if(Objects.nonNull(cookieKeyConfig)) {

			Gson gson = new Gson();

			jsonString = gson.toJson(cookieKeyConfig);
		}

		return jsonString;
	}

	/**
	 * @return the cookieDomain
	 */
	public String getCookieDomain() {
		return cookieDomain;
	}

	public String getCustomCanonicalUrl() {
		return customCanonicalUrl;
	}

	public void setCustomCanonicalUrl(String customCanonicalUrl) {
		this.customCanonicalUrl = customCanonicalUrl;
	}

	public String getOgImagePath() {
		return ogImagePath;
	}

	public void setOgImagePath(String ogImagePath) {
		this.ogImagePath = ogImagePath;
	}

	public String getOgTitle() {
		return ogTitle;
	}

	public void setOgTitle(String ogTitle) {
		this.ogTitle = ogTitle;
	}

	public String getVcmcorpHome() {
		return vcmcorpHome;
	}

	public void setVcmcorpHome(String vcmcorpHome) {
		this.vcmcorpHome = vcmcorpHome;
	}
}
