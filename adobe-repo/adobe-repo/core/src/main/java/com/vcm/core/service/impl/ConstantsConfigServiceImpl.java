package com.vcm.core.service.impl;

import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.service.ConstantsConfigService;

@Component(service = ConstantsConfigService.class, immediate = true)
@Designate(ocd = ConstantsConfigServiceImpl.Config.class)
public class ConstantsConfigServiceImpl implements ConstantsConfigService {

	private static Logger LOGGER = LoggerFactory.getLogger(ConstantsConfigServiceImpl.class);

	private String applicationName;
	private String websiteName;
	private String websiteLocale;
	private String memberSigninServiceURL;
	private String analyticsEndPoint;
	private String popupContentFragmentPage;
	private String purchaseFundUrl;
	private String ussa529PerformanceFundEndPoint;
	private String ussa529PerformanceFundKey;
	private String bugHerdUrl;
	private String vcmConfigPagePath;
	private String cookieDomain;
	@Activate
	@Modified
	protected void activate(final Config config) {
		this.applicationName = String.valueOf(config.applicationName());
		this.websiteName = String.valueOf(config.websiteName());
		this.websiteLocale = String.valueOf(config.websiteLocale());
		this.cookieDomain = String.valueOf(config.cookieDomain());
		this.memberSigninServiceURL = String.valueOf(config.memberSigninServiceURL());
		this.analyticsEndPoint = String.valueOf(config.analyticsEndPoint());
		this.popupContentFragmentPage = String.valueOf(config.getPopupContentFragmentPage());
		this.purchaseFundUrl= String.valueOf(config.purchaseFundUrl());
		LOGGER.debug("configure: memberSigninServiceURL ='{}'", this.memberSigninServiceURL);
		LOGGER.debug("configure: analyticsEndPoint ='{}'", this.analyticsEndPoint);
		this.ussa529PerformanceFundEndPoint=String.valueOf(config.ussa529PerformanceFundEndPoint());
		this.ussa529PerformanceFundKey=String.valueOf(config.ussa529PerformanceFundKey());
		this.bugHerdUrl = String.valueOf(config.bugHerdUrl());
		this.vcmConfigPagePath=String.valueOf(config.vcmConfigPagePath());
	}

	@Override
	public String getApplicationName() {
		return this.applicationName;
	}

	@Override
	public String getWebsiteName() {
		return this.websiteName;
	}

	@Override
	public String getWebsiteLocale() {
		return this.websiteLocale;
	}

	@Override
	public String getMemberSigninServiceURL() {
		return this.memberSigninServiceURL;
	}

	/**
	 * @return the analyticsEndPoint
	 */
	public String getAnalyticsEndPoint() {
		return analyticsEndPoint;
	}

	@Override
	public String getPopupContentFragmentPage() {
		return popupContentFragmentPage;
	}
	
	@Override
	public String getPurchaseFundUrl() {
		return purchaseFundUrl;
	}

	@Override
	public String getUSSA529PerformanceFundEndPoint() {
		return this.ussa529PerformanceFundEndPoint;
	}
	
	@Override
	public String getUSSA529PerformanceFundKey() {
		return this.ussa529PerformanceFundKey;
	}

	@Override
	public String getBugHerdUrl() { return bugHerdUrl; }


	/**
	 * @return the vcmConfigPagePath
	 */
	public String getVcmConfigPagePath() {
		return vcmConfigPagePath;
	}

	/**
	 * @param vcmConfigPagePath the vcmConfigPagePath to set
	 */
	public void setVcmConfigPagePath(String vcmConfigPagePath) {
		this.vcmConfigPagePath = vcmConfigPagePath;
	}
	

	/**
	 * @return the cookieDomain
	 */
	public String getCookieDomain() {
	    return cookieDomain;
	}


	@ObjectClassDefinition(name = "VCM Global Configuration", description = "This service contains "
			+ "all VCM related GLobal OSGI Configuration")
	public @interface Config {
	   @AttributeDefinition(name = "Application Name", type = AttributeType.STRING)
	   String applicationName() default  "VCM";
	   
	   @AttributeDefinition(name = "Website Name", type = AttributeType.STRING)
	   String websiteName() default  "vcm.com";
	   
	   @AttributeDefinition(name = "Website Locale", type = AttributeType.STRING)
	   String websiteLocale() default  "en";
	   
	   @AttributeDefinition(name = "Cookie Domain", type = AttributeType.STRING)
	   String cookieDomain() default StringUtils.EMPTY;;
	  
	   @AttributeDefinition(name = "Member Signin Service URL", type = AttributeType.STRING)
	   String memberSigninServiceURL() default  "no-url-available";
	   
	   @AttributeDefinition(name = "Analytics End Point", description = "Please enter Analytics End point")
	   String analyticsEndPoint() default "//assets.adobedtm.com/9038a1739344/e69bdabe1962/launch-15c65aff4b9c-development.min.js";
	  
	   @AttributeDefinition(name = "Popup content Page", description = "Please enter popup content fragments list page")
	   String getPopupContentFragmentPage() default StringUtils.EMPTY;
	      
	   @AttributeDefinition(name = "Purchase Fund URL", description = "Please enter purchase fund url")
	   String purchaseFundUrl() default "https://vcm-dev-aem65.adobecqms.net/member/solutions.html";

	   
	   @AttributeDefinition(name = "529 plan performance end point url", description = "Please enter 529 plan performance end point url")
	   String ussa529PerformanceFundEndPoint() default StringUtils.EMPTY;
	  
	   @AttributeDefinition(name = "529 Plan performance api key", description = "Please enter API Key")
	   String ussa529PerformanceFundKey() default "HU8s0Kj6f848YhcLKz8Cl3tYbn9ezTwm9xybNwB7";
	   
	   @AttributeDefinition(name = "BugHerd Url", description = "Please enter BugHerd Url")
	   String bugHerdUrl() default StringUtils.EMPTY;
	   
	   @AttributeDefinition(name = "VCM Site Configuration page path", description = "Please enter VCM Site Configuration page path")
	   String vcmConfigPagePath() default StringUtils.EMPTY;
	}
}
