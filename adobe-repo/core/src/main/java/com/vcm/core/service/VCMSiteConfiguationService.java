package com.vcm.core.service;

import java.util.List;
import java.util.Map;

import com.vcm.core.models.SiteConfigModel;

public interface VCMSiteConfiguationService {
	
	public String getSiteConfigAsJson(String configName);

	public Map<String, List<String>> getSiteConfigAsMap(String configName);

	public List<SiteConfigModel> getSiteConfigItem(String configName);

	public Map<String, Object> getLabelConfigAsMap();
}
