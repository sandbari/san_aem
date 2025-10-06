package com.vcm.core.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.constants.Constant;
import com.vcm.core.service.ConstantsConfigService;

/**
 * 
 * @author CTS 
 * Util Class to hold utillity methods
 *
 */
@Component(service = UtilityService.class, immediate = true)
@Designate(ocd = UtilityService.Config.class)
public class UtilityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UtilityService.class);
	
	private static String[] extensionlessDomainValues = null;
	
	private static String[] linkRewriterValues = null;
	
	@Reference
	ConstantsConfigService constantsConfigService;
	
        @Activate
        @Modified
        protected void activate(final Config config) {
    
            LOGGER.debug("Into UtilityService service Start");
           
            linkRewriterValues = config.linkRewriterValues();
            
	    extensionlessDomainValues=config.extensionlessDomainValues();
	
            LOGGER.debug("Into UtilityService service END extensionlessDomainValues {} linkRewriterValues {}",extensionlessDomainValues,linkRewriterValues);
    
        }
	/*
	 * This function checks for internal or external and appends .html if it is
	 * internal link
	 */
	public static String identifyLinkUrl(String linkUrl, ResourceResolver resourceResolver) {
		LOGGER.debug("*** inside identifyLinkUrl method ***");
		if (linkUrl != null) {
			Resource pathResource = resourceResolver.getResource(linkUrl);
			// check if resource exists
			if (pathResource != null) {
				LOGGER.debug("Link resourceType {} ", pathResource.getResourceType());
				if (pathResource.getResourceType().equals(NameConstants.NT_PAGE)) {
					linkUrl = resourceResolver.map(linkUrl) + Constant.HTML_EXTENSION;
					linkUrl = UtilityService.getExtensionlessLinkUrl(linkUrl);
					LOGGER.debug("Link URL is "+linkUrl);
				}

			}
		}
		return linkUrl;
	}

	public static String getPDFLinkUrl(String href) {
		String shortenPath = href;
		if (Objects.nonNull(href) 
				&& Objects.nonNull(linkRewriterValues)) {
			for (String rewriterValue : linkRewriterValues) {
				if(!rewriterValue.equals("")) {
					String[] valueArr = rewriterValue.split(":");
					shortenPath = href.replace(valueArr[0], valueArr[1]);
					LOGGER.debug("DAM Link Path after change :: " + shortenPath);
				}
			}
		}
		return shortenPath;
	}
	
    public static String getExtensionlessLinkUrl(String href) {
		
	String shortenPath = href;
		
	if (Objects.nonNull(href) && Objects.nonNull(extensionlessDomainValues) && extensionlessDomainValues.length > 0) {
			
	    if((href.startsWith("/") && href.endsWith(Constant.HTML_EXTENSION))){
		
		shortenPath= href.replace(".html","");
		
	    }
		
	    else{
		    
		for (String domainValue : extensionlessDomainValues) {
			
		    LOGGER.debug("Domain Value {} href {} ",domainValue,href);
			
		    if(!domainValue.equals("") && href.contains(domainValue)) {
			
			if( href.endsWith(Constant.HTML_EXTENSION)) {
			    
			    shortenPath=href.replace(".html","");
			
			}
			
			int startIndex = shortenPath.indexOf(domainValue);
			
			int endIndex = startIndex + domainValue.length();
			
			if(startIndex > 0) {
			  
			    shortenPath = shortenPath.substring(endIndex);
				
			}
			
			if(StringUtils.isEmpty(shortenPath)) {
			   
			    shortenPath="/";			
			}
			
			LOGGER.debug("Shorten Path {} ",shortenPath);
			break;
				
		    }
		    
		}
		
	    }
			
	  }
	
	return shortenPath;
	
    }
	
	public static String getPopupId(String popupPath, ResourceResolver resourceResolver) {
		LOGGER.debug("*** inside getPopupId method ***");
		String popupId = null;
		if (popupPath != null) {
			Resource cfResource = resourceResolver.getResource(popupPath);
			// check if resource exists
			if (cfResource != null) {
				ContentFragment contentFragment = cfResource.adaptTo(ContentFragment.class);
				if (contentFragment !=null) {
					popupId = contentFragment.getElement("popupId").getContent();
				}

			}
		}
		return popupId;
	}
	
	/* This method returns Tooltip/Popup Content Fragment property value */
	public static String getProductDetailOverlayContent(String path, ResourceResolver resourceResolver, String cfPropertyName) {
		LOGGER.debug("*** inside getProductDetailOverlayContent method ***");
		String cfPropertyValue = null;
		if (path != null && resourceResolver != null) {
			Resource cfResource = resourceResolver.getResource(path);
			// check if resource exists
			if (cfResource != null) {
				ContentFragment contentFragment = cfResource.adaptTo(ContentFragment.class);
				if (contentFragment !=null && cfPropertyName != null) {
					if(contentFragment.hasElement(cfPropertyName)){
						cfPropertyValue = contentFragment.getElement(cfPropertyName).getContent().replaceAll("\\<.*?\\>", "");
					}
					LOGGER.debug("*** Product Detail Overlay Property Value: "+cfPropertyValue);
				}
			}
		}
		LOGGER.debug("*** getProductDetailOverlayContent method completed ***");
		return cfPropertyValue;
	}

	public static String getLinkDescription(String linkUrl, String linkText, ResourceResolver resourceResolver) {
		String linkDescription = null;
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if(null != linkUrl && null != pageManager.getPage(linkUrl)) {
			linkDescription = Constant.VIEW + pageManager.getPage(linkUrl).getTitle();
		} else if(null != linkText){
			linkDescription = linkText;
		}
		return linkDescription;
	}
	
	/**
	 * @return the linkRewriterValues
	 */
	public String[] getLinkRewriterValues() {
		String[] linkRewriterValuesLocal= {""};
		if(Objects.nonNull(linkRewriterValues)) {
			linkRewriterValuesLocal=linkRewriterValues.clone();
		}
		return linkRewriterValuesLocal;
		
	}

	 /**
	  * Method to generate dynamic Id.
	  * @return componentNameTrimed
	  *
	  */	
	public static String getId(Resource resoure) {
	    	String componentNameTrimed = "";
	    	
	    	if(Objects.nonNull(resoure)) {
		
	    	    LOGGER.debug("*** inside getId method ***"+resoure.getPath());
	        
	    	    String id = String.valueOf(Math.abs(resoure.getPath().hashCode() - 1)); 
	        
	    	    Resource compResource= resoure;
	        
	    	    String componentName=compResource.getName();
	        
	    	    componentNameTrimed=componentName + id;  
	    	}
	    	
	        return componentNameTrimed;        
	 }

	@ObjectClassDefinition(name = "VCM Utility Configurations", description = "This service contains "
        		+ "all VCM related Utility OSGI Configuration")
        public @interface Config {
         
           @AttributeDefinition(name = "Link Rewriter Values", description = "Please enter colon separated values")
           String[] linkRewriterValues() default {""};
           
           @AttributeDefinition(name = "Extension Less Domain Patterns", description = "Please enter extensionless domain values")
           String[] extensionlessDomainValues() default {""};
         }
}