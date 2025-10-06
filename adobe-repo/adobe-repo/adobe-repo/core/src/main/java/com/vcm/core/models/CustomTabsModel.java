package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CustomTabsModel{
	
    private static final Logger log = LoggerFactory.getLogger(CustomTabsModel.class);
    @SlingObject
	private Resource resource;

	@Inject
	String tabType;

	@Inject
	String stickyRightRail;
    
    public void setResource(Resource resource) {
		this.resource = resource;
	}

	private List<Map<String, String>> tabResourceList = new ArrayList<>();
    
    public List<Map<String, String>> getTabResourceList() {
		List<Map<String, String>> copyTabResourceList = tabResourceList;
		Collections.copy(copyTabResourceList, tabResourceList);
		return copyTabResourceList;
	}
    
    @PostConstruct
	protected void init() {
		if(resource.hasChildren()) {
			Iterator<Resource> resourceIterator = resource.listChildren();
			log.debug("resource path {}", resource.getPath());
			while (resourceIterator.hasNext()) {
				String tabTitle = "";
				String tabReference = "";
				Resource childResource = resourceIterator.next();
				if(null != childResource.getValueMap().get("cq:panelTitle", String.class)) {
					tabTitle = childResource.getValueMap().get("cq:panelTitle", String.class);
					String initialString = tabTitle.replaceAll("[()?:!.,;{}&]+", "").trim();
					tabReference = initialString.replaceAll("\\s+", "-");
				}
				Iterator<Resource> childresourceIterator = childResource.listChildren();
				while (childresourceIterator.hasNext()) {
					Resource grandChild = childresourceIterator.next();
					Map<String, String> map = new HashMap<>();
					map.put("tabTitle", tabTitle);
					map.put("tabReference", tabReference);
					map.put("resourcePath", grandChild.getPath());
					map.put("resourceType",grandChild.getResourceType());
					log.debug("resource type {}", grandChild.getPath());
					tabResourceList.add(map);	
				}
			}
		}
		
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public String getStickyRightRail() {
		return stickyRightRail;
	}

	public void setStickyRightRail(String stickyRightRail) {
		this.stickyRightRail = stickyRightRail;
	}
}