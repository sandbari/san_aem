package com.vcm.core.service;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public interface AudienceSelectorService {

	public boolean isUserAuthorized(SlingHttpServletRequest request);
	
	public List<String> getAllowedAudienceForComponent(SlingHttpServletRequest request);
	
	public List<String> getAllowedAudienceListFromResource(Resource resource);
	
	public List<String> getAllowedAudienceListFromValueMap(ValueMap valuemap);

	public String getMemberAudienceTypeKey();

	public String getFinancialAudienceTypeKey();

	public String getInstitutionalInvestorAudienceTypeKey();

	public String getVcmcorpHome();
	   
	public String getVcmMemberHome();
	   
	public String getVcmFinancialPath();
	   
	public String getVcmInstitutionalInvestorPath();
	
	public String getHomePageConfig();
	
	public String vcmMemberDomainKey();
	
	public String vcmFinancialDomainKey();
	
	public String vcmInstitutionalInvestorDomainKey();
	
	public String getDomainKeyVsCookieConfig();

	public String getMappedDomainForUserTypeCookiesAsJsonString();

	public Map<String, String> getMappedDomainForUserTypeCookiesAsMap();
	
	public String getUserTypeFromRequestDomain(SlingHttpServletRequest request);

	public String getMappedDomainForPage(Resource resource);
	
	public Map<String,String> getCookieKeyConfig();

}
