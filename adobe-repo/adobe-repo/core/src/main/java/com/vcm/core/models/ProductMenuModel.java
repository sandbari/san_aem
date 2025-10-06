package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.pojo.TabBean;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductMenuModel {

    private static final Logger log = LoggerFactory.getLogger(ProductMenuModel.class);

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;

	@Inject
	private String errorMessage;

	@Inject
	private String placeHolderText;

	@Inject
	private String tabVariant;

	private List<TabBean> tabLinkItems = new ArrayList<>();

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	@PostConstruct
	protected void init() {
		log.debug("*** inside Product Menu init() ***");
		if(resource != null) {
			Iterator<Resource> resourceIterator = resource.listChildren();
			// get the grandchild node of the currentNode - which represents where the
			// MultiField values are stored
			while (resourceIterator.hasNext()) {
				Resource childResource = resourceIterator.next();
	
				if (("tabLinkItems").equalsIgnoreCase(childResource.getName())) {
	
					Iterator<Resource> childResourceIterator = childResource.listChildren();
	
					// THIS WILL READ THE VALUE OF THE Multifield and set them in a collection
					while (childResourceIterator.hasNext()) {
						TabBean tabBean = new TabBean();
						Resource contentResource = childResourceIterator.next();
						if (null != contentResource.getValueMap().get("tabTitle")) {
							tabBean.setTabTitle(contentResource.getValueMap().get("tabTitle").toString());
						}
	
						if (null != contentResource.getValueMap().get("tabLink")) {
							tabBean.setTabLink(UtilityService.identifyLinkUrl(
									contentResource.getValueMap().get("tabLink").toString(), resourceResolver));
						}
						tabLinkItems.add(tabBean);
					}
				}
			}
		}

	}

	/**
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return placeHolderText
	 */
	public String getPlaceHolderText() {
		return placeHolderText;
	}

	/**
	 * @param placeHolderText
	 */
	public void setPlaceHolderText(String placeHolderText) {
		this.placeHolderText = placeHolderText;
	}

	/**
	 * @return tabVariant
	 */
	public String getTabVariant() {
		return tabVariant;
	}

	/**
	 * @param tabVariant
	 */
	public void setTabVariant(String tabVariant) {
		this.tabVariant = tabVariant;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * @return tabLinkItems
	 */
	public List<TabBean> getTabLinkItems() {
		List<TabBean> copyTabLinkItems = tabLinkItems;
		Collections.copy(copyTabLinkItems, tabLinkItems);
		return copyTabLinkItems;
	}

}
