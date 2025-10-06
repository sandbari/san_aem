package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.models.LabelsConfigModel;
import com.vcm.core.models.SiteConfigModel;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.VCMSiteConfiguationService;

@Component(service = VCMSiteConfiguationService.class, immediate = true)
public final class VCMSiteConfiguationServiceImpl implements VCMSiteConfiguationService {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(VCMSiteConfiguationServiceImpl.class);

    private static final String RESPONSIVE_GRID_PATH="/jcr:content/root/responsivegrid";

	private static final String SITE_CONFIGURATION_COMPONENT="vcm/components/content/siteconfig";
	
	private static final String THEME_CONFIGURATION_COMPONENT="vcm/components/content/themeconfig";

	@Reference
	private ConstantsConfigService constantsConfigService;
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

    /**
     * This method returns the JSON representation of the ACS Generic list. The
     * JSON object will contain title & value pairs. Title is localized value.
     * 
     * @param configKey
     *            the generic list page.
     * @param locale
     *            the locale
     * @return listJson the JSON representation of the generic list.
     */
	@Override
    public String getSiteConfigAsJson(String configKey) {
        String listJson = null;
        JSONObject jsonObj = new JSONObject();
        try {
	        if(Objects.nonNull(configKey)) {
	        	List<SiteConfigModel> siteConfigItemList = this.getSiteConfigItem(configKey);
	        	if(Objects.nonNull(siteConfigItemList) && !siteConfigItemList.isEmpty()) {
	        		 LOG.debug("Site Config size {}",siteConfigItemList.size());
	        		for(SiteConfigModel siteConfigList : siteConfigItemList) {
	        			String key=siteConfigList.getKey();
	        			List<String> values=siteConfigList.getValuesList();
	        			LOG.debug("Site Config key {}", key );
	        			if(Objects.nonNull(key) && Objects.nonNull(values) && !values.isEmpty()) {
	        				       LOG.debug("Site Config key into add list with values {}",values);
									jsonObj.put(key,values);
							} 
	        			}
					}
	        	}
        	}
        catch (JSONException e) {
        	LOG.error("JSON EXCEPTION OCCURED {}",e);
		}
        listJson = jsonObj.toString();
        LOG.debug("JSON representation of the generic list {} ", listJson);
        return listJson;
    }

    /**
     * Gets the generic list as map.
     *
     * @param configKey
     *            the list page
     * @param locale
     *            the locale
     * @return the generic list as map
     */
    @Override
    public Map<String, List<String>> getSiteConfigAsMap(String configKey) {
        Map<String, List<String>> response = new HashMap<String, List<String>>();  
		if(Objects.nonNull(configKey)) {
        	List<SiteConfigModel> siteConfigItemList = this.getSiteConfigItem(configKey);
        	if(Objects.nonNull(siteConfigItemList) && !siteConfigItemList.isEmpty()) {
        		for (SiteConfigModel siteConfigList : siteConfigItemList) {
        			String key=siteConfigList.getKey();
        			List<String> values=siteConfigList.getValuesList();
        			LOG.debug("Site Config key :: {} ", key);
        			if(Objects.nonNull(key) && Objects.nonNull(values) && !values.isEmpty()) {
        					response.put(key,values);
						} 
        			}
				}
        	}
        return response;
    }
    @Override
    public List<SiteConfigModel> getSiteConfigItem(String configKey) {
    	 List<SiteConfigModel> siteConfigItems=new ArrayList<SiteConfigModel>();
    	 Map<String, Object> map = new HashMap<>();
         map.put(ResourceResolverFactory.SUBSERVICE, "vcmservice");
         if (resourceResolverFactory != null) {
        	 try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {
	             String vcmSiteConfigRootPath=constantsConfigService.getVcmConfigPagePath();
	             if (Objects.nonNull(resourceResolver) && Objects.nonNull(configKey) && Objects.nonNull(vcmSiteConfigRootPath)) {
	            	String targetPath=vcmSiteConfigRootPath+ "/" +RESPONSIVE_GRID_PATH;
	                Resource responsiveGridNode = resourceResolver.getResource(targetPath);
	                if(Objects.nonNull(responsiveGridNode) && responsiveGridNode.hasChildren()) {
	             	   Iterator<Resource> siteconfigNodes = responsiveGridNode.listChildren();
	             	   while(siteconfigNodes.hasNext()) {
	             		   Resource siteConfigNode = siteconfigNodes.next();
	             		   if(siteConfigNode.isResourceType(SITE_CONFIGURATION_COMPONENT)) {
	             			  SiteConfigModel list=siteConfigNode.adaptTo(SiteConfigModel.class);
	             			   LOG.debug("Resource Path"+list.getKey());
	             			   if(Objects.nonNull(configKey) && 
	             				  !configKey.isEmpty() &&
	             				  configKey.equals(list.getKey())) {
	             				   	siteConfigItems.add(list);
	             			   }
	             		   }
	             	   }
	             	   
	                }
	             }
	         } catch (LoginException e) {
	        	 LOG.error("Exception Occured", e);
			}
         }
         return siteConfigItems;
    }
	public List<LabelsConfigModel> getThemeConfigItem(ResourceResolver resourceResolver) {
		List<LabelsConfigModel> themeConfigItems=new ArrayList<LabelsConfigModel>();
		String vcmSiteConfigRootPath = constantsConfigService.getVcmConfigPagePath();
		if (Objects.nonNull(resourceResolver) && Objects.nonNull(vcmSiteConfigRootPath)) {
			String targetPath = vcmSiteConfigRootPath + RESPONSIVE_GRID_PATH;
			Resource responsiveGridNode = resourceResolver.getResource(targetPath);
			if (Objects.nonNull(responsiveGridNode) && responsiveGridNode.hasChildren()) {
				Iterator<Resource> labelsConfigNodes = responsiveGridNode.listChildren();
				while (labelsConfigNodes.hasNext()) {
					Resource labelsConfigNode = labelsConfigNodes.next();
					LOG.debug("themeConfigNode {}", labelsConfigNode);
					if (labelsConfigNode.isResourceType(THEME_CONFIGURATION_COMPONENT)) {
						LabelsConfigModel configModelList = labelsConfigNode.adaptTo(LabelsConfigModel.class);
						themeConfigItems.add(configModelList);
					}
				}

			}
		}
		return themeConfigItems;
	}

	@Override
	public Map<String, Object> getLabelConfigAsMap() {
		Map<String, Object> map = new HashMap<>();
		LinkedHashMap<String, Object> productLabels = new LinkedHashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "vcmservice");
		if (resourceResolverFactory != null) {
			try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {
				List<LabelsConfigModel> labelsConfigModel = getThemeConfigItem(resourceResolver);
				if (Objects.nonNull(labelsConfigModel) && !labelsConfigModel.isEmpty()) {
					for(LabelsConfigModel labelsConfigModelItem : labelsConfigModel){
						if(Objects.nonNull(labelsConfigModelItem.getProductLabels()) && !labelsConfigModelItem.getProductLabels().isEmpty()){
							productLabels.putAll(labelsConfigModelItem.getProductLabels());
						}
					}
				}
			} catch (LoginException e) {
				LOG.error("Exception Occured", e);
			}
		}
		return productLabels;
	}

	/**
	 * @param constantsConfigService the constantsConfigService to set
	 */
	public void setConstantsConfigService(ConstantsConfigService constantsConfigService) {
		this.constantsConfigService = constantsConfigService;
	}

	/**
	 * @param resourceResolverFactory the resourceResolverFactory to set
	 */
	public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}
}