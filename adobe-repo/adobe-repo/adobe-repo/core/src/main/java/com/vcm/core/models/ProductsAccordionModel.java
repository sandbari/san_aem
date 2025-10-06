package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.vcm.core.constants.Constant;
import com.vcm.core.pojo.ProductAccordionBean;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

/**
 * @author CTS. A Model class for ProductsAccordionModel
 */

@Model(adaptables = { SlingHttpServletRequest.class,Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductsAccordionModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAccordionModel.class);

	@Inject
	@Via("resource") 
	private String heading;

	@Inject
	@Via("resource") 
	private Resource productPages;

	private List<ProductAccordionBean> productPagesList;

	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;
	
	@SlingObject
        SlingHttpServletRequest slingRequest;
	
	private boolean isUserAuthorized;
	
	private List<String> allowedAudience = new ArrayList<>();
	
	@Inject
	AudienceSelectorService audienceSelectorService;
	
	private String id;
	

	/**
	 * Post Construct method
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Into Product Accordion Model");
		productPagesList = new ArrayList<>();
		Resource productAccordionResource = slingRequest.getResource();
		id = UtilityService.getId(productAccordionResource);
		Iterator<Resource> productPagesItr = productPages.listChildren();
		while (productPagesItr.hasNext()) {
			Resource productField = productPagesItr.next();
			ProductAccordionBean productAccordionBean = new ProductAccordionBean();
			String productPageUrl = productField.getValueMap().get("linkUrl", String.class);
			Resource productResource = resourceResolver.getResource(productPageUrl);		
			if(Objects.nonNull(productResource)) {
				productAccordionBean.setPage(productResource.adaptTo(Page.class));
				productAccordionBean.setProductPageUrl(productPageUrl+Constant.HTML_EXTENSION);
				productAccordionBean.setIsExternalUrl(false);
			}
			else
			{
				productAccordionBean.setProductPageUrl(productPageUrl);
				productAccordionBean.setIsExternalUrl(true);
			}
			productAccordionBean.setTarget(productField.getValueMap().get("tabSelect", String.class));
			productAccordionBean.setLinkTitle(productField.getValueMap().get("linkTitle", String.class));
			productPagesList.add(productAccordionBean);
		}
		isUserAuthorized = audienceSelectorService.isUserAuthorized(slingRequest);
		allowedAudience= audienceSelectorService.getAllowedAudienceForComponent(slingRequest);
		
		LOGGER.debug("End Product Accordion Model");
	}

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @return the productPages
	 */
	public Resource getProductPages() {
		return productPages;
	}

	/**
	 * @return the productPagesList
	 */
	public List<ProductAccordionBean> getProductPagesList() {
		List<ProductAccordionBean> copyListBean = productPagesList;
		Collections.copy(copyListBean, productPagesList);
		return copyListBean;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @param productPages the productPages to set
	 */
	public void setProductPages(Resource productPages) {
		this.productPages = productPages;
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public List<String> getAllowedAudience() {
		List<String> copyOfAllowedAudience = allowedAudience;
		Collections.copy(copyOfAllowedAudience, allowedAudience);
		return copyOfAllowedAudience;
	}
	
	/**
	 * @param audienceSelectorService the audienceSelectorService to set
	 */
	public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
		this.audienceSelectorService = audienceSelectorService;
	}
	
	public boolean getIsUserAuthorized() {
		return isUserAuthorized;
	}

	/**
	 * @param isUserAuthorized the isUserAuthorized to set
	 */
	public void setUserAuthorized(boolean isUserAuthorized) {
		this.isUserAuthorized = isUserAuthorized;
	}

	/**
	 * @return the id
	 */
	public String getId() {
	    return id;
	}

	/**
	 * @param slingRequest the slingRequest to set
	 */
	public void setSlingRequest(SlingHttpServletRequest slingRequest) {
	    this.slingRequest = slingRequest;
	}

}
