package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.vcm.core.service.AudienceSelectorService;

@Component(service = AudienceSelectorService.class, immediate = true)
@Designate(ocd = AudienceSelectorServiceImpl.Config.class)
public class AudienceSelectorServiceImpl implements AudienceSelectorService {

    private static final String INSTITUTIONALINVESTORS_TYPE = "institutionalinvestorsType";

    private static final String FINANCIAL_ADVISORS_TYPE = "financialAdvisorsType";

    private static final String MEMBERS_TYPE = "membersType";

    private static final String ALL = "ALL";

    private static final String CORP_HOME = "isCorpHomePage";
    
    private static Logger LOGGER = LoggerFactory.getLogger(AudienceSelectorServiceImpl.class);

    private Map<String, String> homePageConfig = null;

    private String memberAudienceTypeKey;

    private String financialAudienceTypeKey;

    private String institutionalInvestorAudienceTypeKey;

    private String vcmcorpHome;

    private String vcmMemberHome;

    private String vcmFinancialPath;

    private String vcmInstitutionalInvestorPath;

    private String vcmMemberDomainKey;

    private String vcmFinancialDomainKey;

    private String vcmInstitutionalInvestorDomainKey;
    
    private Map<String,String> cookieKeyConfig = null;

    @Activate
    @Modified
    protected void activate(final Config config) {
	
	cookieKeyConfig= new HashMap<String,String>();

	LOGGER.debug("Into Audience Selector config service Start");

	this.memberAudienceTypeKey = String.valueOf(config.getMemberAudienceTypeKey());

	this.financialAudienceTypeKey = String.valueOf(config.getFinancialAudienceTypeKey());

	this.institutionalInvestorAudienceTypeKey = String.valueOf(config.getInvestorAudienceTypeKey());
	
	if(Objects.nonNull(this.memberAudienceTypeKey)) {
	    
	    cookieKeyConfig.put(this.memberAudienceTypeKey.toUpperCase(), this.memberAudienceTypeKey);
	
	}
	if(Objects.nonNull(this.financialAudienceTypeKey)) {
	    
	    cookieKeyConfig.put(this.financialAudienceTypeKey.toUpperCase(), this.financialAudienceTypeKey);
	
	}
	
	if(Objects.nonNull(this.institutionalInvestorAudienceTypeKey)) {
	    
	    cookieKeyConfig.put(this.institutionalInvestorAudienceTypeKey.toUpperCase(), this.institutionalInvestorAudienceTypeKey);
	
	}
	// Home Page Configs

	this.vcmcorpHome = String.valueOf(config.vcmcorpHome());

	this.vcmMemberHome = String.valueOf(config.vcmMemberHome());

	this.vcmFinancialPath = String.valueOf(config.vcmFinancialPath());

	this.vcmInstitutionalInvestorPath = String.valueOf(config.vcmInstitutionalInvestorPath());

	// Domain Key Config
	this.vcmMemberDomainKey = String.valueOf(config.vcmMemberDomainKey());

	this.vcmFinancialDomainKey = String.valueOf(config.vcmFinancialDomainKey());

	this.vcmInstitutionalInvestorDomainKey = String.valueOf(config.vcmInstitutionalInvestorDomainKey());

	LOGGER.debug("Into Audience Selector config service END");

    }

    @ObjectClassDefinition(name = "VCM Audience Selector Configuration", description = "This service contains "
	    + "all VCM Audience Selector related Configuration  OSGI Configuration")
    public @interface Config {

	@AttributeDefinition(name = "Member Type Key", type = AttributeType.STRING)
	String getMemberAudienceTypeKey()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "Financial Type Key", type = AttributeType.STRING) String getFinancialAudienceTypeKey()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "Institutional Investor Type Key", type = AttributeType.STRING) String getInvestorAudienceTypeKey()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Corporate home page", description = "Please enter VCM corporate home page path") String vcmcorpHome()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Member Home page", description = "Please enter VCM Member Home page") String vcmMemberHome()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Financial Advisor Home page", description = "Please enter Financial Advisor Home page") String vcmFinancialPath()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Institutional Investor page", description = "Please enter VCM Institutional Investor page") String vcmInstitutionalInvestorPath()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Member Domain Identifier", description = "Please enter VCM Member domain identifier") String vcmMemberDomainKey()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Financial Domain Identifier", description = "Please enter Financial  domain identifier") String vcmFinancialDomainKey()

	default StringUtils.EMPTY;

	@AttributeDefinition(name = "VCM Institutional Domain Identifier", description = "Please enter VCM Institutional  domain identifier") String vcmInstitutionalInvestorDomainKey() default StringUtils.EMPTY;
    }

    @Override
    public String getMemberAudienceTypeKey() {

	return this.memberAudienceTypeKey;

    }

    @Override
    public String getFinancialAudienceTypeKey() {

	return this.financialAudienceTypeKey;
    }

    @Override
    public String getInstitutionalInvestorAudienceTypeKey() {

	return this.institutionalInvestorAudienceTypeKey;
    }

    @Override
    public List<String> getAllowedAudienceForComponent(SlingHttpServletRequest request) {

	Resource resource = request.getResource();

	List<String> allowedAudience = this.getAllowedAudienceListFromResource(resource);

	return allowedAudience;
    }

    public List<String> getAllowedAudienceListFromValueMap(ValueMap valuemap) {

	List<String> allowedAudience = new ArrayList<String>();

	if (Objects.nonNull(valuemap)) {

	    if (valuemap.containsKey(MEMBERS_TYPE) && valuemap.get(MEMBERS_TYPE, Boolean.class)) {

		allowedAudience.add(this.memberAudienceTypeKey);
	    }

	    if (valuemap.containsKey(FINANCIAL_ADVISORS_TYPE) && valuemap.get(FINANCIAL_ADVISORS_TYPE, Boolean.class)) {

		allowedAudience.add(this.financialAudienceTypeKey);
	    }

	    if (valuemap.containsKey(INSTITUTIONALINVESTORS_TYPE)
		    && valuemap.get(INSTITUTIONALINVESTORS_TYPE, Boolean.class)) {

		allowedAudience.add(this.institutionalInvestorAudienceTypeKey);

	    }
	    // if none is selected, by default it is allowed for all users
	    if (allowedAudience.size() == 0 || allowedAudience.size() == 3) {

		LOGGER.debug(
			"Audience Selector is empty or all options are selected !!! So returning true by default..This component is allowed for all audineces....");

		allowedAudience.clear();

		allowedAudience.add(ALL);
	    }
	}

	return allowedAudience;
    }

    @Override
    public List<String> getAllowedAudienceListFromResource(Resource resource) {

	List<String> allowedAudience = new ArrayList<String>();

	if (Objects.nonNull(resource)) {

	    LOGGER.debug("getAllowedAudienceForComponent >>> Current Resource : {}", resource.getPath());

	    allowedAudience = this.getAllowedAudienceListFromValueMap(resource.getValueMap());

	}

	LOGGER.debug("allowedAudience={}", allowedAudience);

	return allowedAudience;
    }

    @Override
    public boolean isUserAuthorized(SlingHttpServletRequest request) {

	List<String> allowedAudienceList = this.getAllowedAudienceForComponent(request);

	String audieneceTypeFromFilter = StringUtils.EMPTY;

	if (Objects.nonNull(request.getAttribute("VCM_AUDIENCE_TYPE"))) {

	    audieneceTypeFromFilter = request.getAttribute("VCM_AUDIENCE_TYPE").toString();
	}

	/**
	 * Audience Selector is empty!!! So returning true by default..This component is
	 * allowed for all audiences OR WCMMode is edit display all types
	 */
	if (allowedAudienceList == null || allowedAudienceList.contains("ALL")
		|| WCMMode.fromRequest(request) == WCMMode.EDIT) {

	    LOGGER.debug("WCMMODE : {}", WCMMode.fromRequest(request));

	    return true;

	} else {

	    LOGGER.debug("audieneceTypeFromFilter : {}", audieneceTypeFromFilter);

	    if (Objects.nonNull(this.memberAudienceTypeKey)
		    && this.memberAudienceTypeKey.equalsIgnoreCase(audieneceTypeFromFilter)
		    && allowedAudienceList.contains(this.memberAudienceTypeKey)) {

		LOGGER.debug("member type matched!!!");

		return true;

	    } else if (Objects.nonNull(this.memberAudienceTypeKey)
		    && this.financialAudienceTypeKey.equalsIgnoreCase(audieneceTypeFromFilter)
		    && allowedAudienceList.contains(this.financialAudienceTypeKey)) {

		LOGGER.debug("financialprofessional type matched!!!");

		return true;

	    } else if (Objects.nonNull(this.institutionalInvestorAudienceTypeKey)
		    && this.institutionalInvestorAudienceTypeKey.equalsIgnoreCase(audieneceTypeFromFilter)
		    && allowedAudienceList.contains(this.institutionalInvestorAudienceTypeKey)) {

		LOGGER.debug("institutionalinvestor type matched!!!");

		return true;
	    }
	}
	return false;
    }

    @Override
    public String getHomePageConfig() {

	homePageConfig = new HashMap<>();

	homePageConfig.put(ALL, this.vcmcorpHome);

	homePageConfig.put(this.memberAudienceTypeKey, this.vcmMemberHome);

	homePageConfig.put(this.financialAudienceTypeKey, this.vcmFinancialPath);

	homePageConfig.put(this.institutionalInvestorAudienceTypeKey, this.vcmInstitutionalInvestorPath);

	Gson gson = new Gson();

	String json = gson.toJson(homePageConfig);

	return json;
    }

    @Override
    public String getDomainKeyVsCookieConfig() {

	Map<String, String> domainConfig = new HashMap<>();

	domainConfig.put(this.vcmMemberDomainKey, this.memberAudienceTypeKey);

	domainConfig.put(this.vcmFinancialDomainKey, this.financialAudienceTypeKey);

	domainConfig.put(this.vcmInstitutionalInvestorDomainKey, this.institutionalInvestorAudienceTypeKey);

	Gson gson = new Gson();

	String json = gson.toJson(domainConfig);

	return json;
    }

    @Override
    public String getMappedDomainForUserTypeCookiesAsJsonString() {

	Map<String, String> cookieVsdomainConfig = this.getMappedDomainForUserTypeCookiesAsMap();

	Gson gson = new Gson();

	String json = gson.toJson(cookieVsdomainConfig);

	return json;
    }

    @Override
    public Map<String, String> getMappedDomainForUserTypeCookiesAsMap() {

	Map<String, String> cookieVsdomainConfig = new HashMap<>();

	cookieVsdomainConfig.put(ALL, this.trimDomainURL(this.vcmcorpHome));

	cookieVsdomainConfig.put(this.memberAudienceTypeKey, this.trimDomainURL(this.vcmMemberHome));

	cookieVsdomainConfig.put(this.financialAudienceTypeKey, this.trimDomainURL(this.vcmFinancialPath));

	cookieVsdomainConfig.put(this.institutionalInvestorAudienceTypeKey,
		this.trimDomainURL(this.vcmInstitutionalInvestorPath));

	return cookieVsdomainConfig;
    }

    public String trimDomainURL(String url) {

	if (Objects.nonNull(url)) {

	    String comstring = ".com";

	    int startIndex = url.lastIndexOf(comstring);

	    if (startIndex > -1) {

		int endIndex = startIndex + comstring.length();

		url = url.substring(0, endIndex);

	    }
	}
	return url;
    }

    @Override
    public String getUserTypeFromRequestDomain(SlingHttpServletRequest request) {

	String domainName = request.getServerName();

	LOGGER.debug("domainName : {}", domainName);

	String userType = "";

	if (domainName.contains(this.vcmMemberDomainKey)) {

	    userType = this.memberAudienceTypeKey;

	} else if (domainName.contains(this.vcmFinancialDomainKey)) {

	    userType = this.financialAudienceTypeKey;

	} else if (domainName.contains(this.vcmInstitutionalInvestorDomainKey)) {

	    userType = this.institutionalInvestorAudienceTypeKey;

	} else {

	    userType = this.memberAudienceTypeKey;
	}

	LOGGER.debug("userType : {}", userType);

	return userType;
    }

    @Override
    public String getMappedDomainForPage(Resource resource) {
	
   	 String domainValue = "";
	
   	 ValueMap resourceValueMap = resource.getValueMap();
   	 
	 List<String> allowedAudience = this.getAllowedAudienceListFromResource(resource);

	   if (Objects.nonNull(allowedAudience) && !allowedAudience.isEmpty()) {

           	Map<String, String> domainMap = this.getMappedDomainForUserTypeCookiesAsMap();
        
           	if (Objects.nonNull(domainMap)) {
        
           	    if (allowedAudience.contains(this.memberAudienceTypeKey)) {
        
           		domainValue = domainMap.get(this.memberAudienceTypeKey);
           	    
           	    } else if (allowedAudience.contains(this.financialAudienceTypeKey)) {
        
           		domainValue = domainMap.get(this.financialAudienceTypeKey);
           	    
           	    } else if (allowedAudience.contains(this.institutionalInvestorAudienceTypeKey)) {
        
           		domainValue = domainMap.get(this.institutionalInvestorAudienceTypeKey);
           	    
           	    } 
           	    else if (resourceValueMap.containsKey(CORP_HOME) && Objects.nonNull(resourceValueMap.get(CORP_HOME)) && resourceValueMap.get(CORP_HOME,String.class).equals("true")) {  
           	    
           		domainValue = domainMap.get(ALL);
           	    }
           	   else {
        
           		domainValue = domainMap.get(this.memberAudienceTypeKey);
           	    }
           	}
	   }

	LOGGER.debug("domainValue : {}", domainValue);
	  
   	return domainValue;
    }

    @Override
    public String getVcmcorpHome() {

	return this.vcmcorpHome;
    }

    @Override
    public String getVcmMemberHome() {

	return this.vcmMemberHome;
    }

    @Override
    public String getVcmFinancialPath() {

	return this.vcmFinancialPath;
    }

    @Override
    public String getVcmInstitutionalInvestorPath() {

	return this.vcmInstitutionalInvestorPath;
    }

    @Override
    public String vcmMemberDomainKey() {

	return this.vcmMemberDomainKey;
    }

    @Override
    public String vcmFinancialDomainKey() {

	return this.vcmFinancialDomainKey;
    }

    @Override
    public String vcmInstitutionalInvestorDomainKey() {

	return this.vcmInstitutionalInvestorDomainKey;
    }

    /**
     * @return the cookieKeyConfig
     */
    @Override
    public Map<String, String> getCookieKeyConfig() {
        return cookieKeyConfig;
    }

}
