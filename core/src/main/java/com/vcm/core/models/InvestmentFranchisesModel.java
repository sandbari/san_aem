package com.vcm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InvestmentFranchisesModel {

    private static final Logger log = LoggerFactory.getLogger(InvestmentFranchisesModel.class);
	private static final String CONTENT_RESOURCE_PATH = "jcr:content/root/responsivegrid";
	private static final String IMAGE_RESOURCE_PATH = "jcr:content/image";
	private static final String FILE_REFERENCE = "fileReference";

	private static final String TRIANGLE_COLOR = "triangleColor";
	private static final String CONTENT_RESOURCE_NAME = "franchiseintro";
	private static final String INTRO_DESCRIPTION = "introDescription";
	private static final String PAGEPATH = "pagePath";

	List<Map<String, Object>> franchiseList = new ArrayList<>();

	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	String heading;

	@Inject
	String description;

	@Inject
	String rootPath;

	@Inject
	String viewDetail;
	
	@Inject
	private String linkDescription;

	@Inject
	String linkUrl;

	@Inject
	String displayStyle;

	@Inject
	String tileDisplay;

	@Inject
	String contentType;

	@Inject
	String tabSelect;
	
	String linkAriaLabel;

	public String getTileDisplay() {
		return tileDisplay;
	}

	public void setTileDisplay(String tileDisplay) {
		this.tileDisplay = tileDisplay;
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	public String getHeading() {
		return heading;
	}

    public void setHeading(String heading) {
		this.heading = heading;
	}

    public String getViewDetail() {
        return viewDetail;
    }
    
	public void setViewDetail(String viewDetail) {
		this.viewDetail = viewDetail;
	}

	public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getRootPath() {
		return rootPath;
	}
    
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getLinkUrl() {
		return UtilityService.identifyLinkUrl(linkUrl,resourceResolver);
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDisplayStyle() {
		return displayStyle;
	}

    public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

	public String getContentType() {
        return contentType;
    }
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<Map<String, Object>> getFranchiseList() {
		return franchiseList;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
	
	 public String getLinkDescription() { 
		 return linkDescription; 
	 }

	 public void setLinkDescription(String linkDescription) { 
		 this.linkDescription = linkDescription; 
	 }

	 public String getLinkAriaLabel() {
	        return linkAriaLabel;
	 }

	 public void setLinkAriaLabel(String linkAriaLabel) {
	        this.linkAriaLabel = linkAriaLabel;
	 }

	@PostConstruct
	protected void init() {
		Resource root = resourceResolver.getResource(rootPath);
		if (null == root || !root.hasChildren()) {
			log.error("No valid rootPath");
		} else {
			log.debug("Inside Investment Franchise Model : resource path is {}", root.getPath());
			Iterable<Resource> resources = root.getChildren();

			for (Resource resource : resources) {
				log.debug("Resource name is {}", resource.getName());
				setFranchiseMapList(resource);
			}
		}
		
		 if(Objects.nonNull(linkDescription)){
	            linkAriaLabel = linkDescription;
	        } else if (Objects.nonNull(linkUrl) && Objects.nonNull(viewDetail)) {
	            linkAriaLabel = UtilityService.getLinkDescription(linkUrl, viewDetail, resourceResolver);
	        }
	}

	public void setFranchiseMapList(Resource resource) {
		FranchiseIntroModel franchiseIntroModel = null;
		if (!resource.getName().equalsIgnoreCase(JcrConstants.JCR_CONTENT)) {

			String triangleColor = "";
			String fileReference = "";
			String introDescription = "";

			Resource imageResource = resource.getChild(IMAGE_RESOURCE_PATH); // investment-franchise is
																				// getting
																				// child pages

			if (null != imageResource) {
				fileReference = imageResource.getValueMap().get(FILE_REFERENCE).toString(); // image of
																							// child pages.
			}

			Resource contentResource = resource.getChild(JcrConstants.JCR_CONTENT); // for jcr:description

			if (null != contentResource) {

				if (null != contentResource.getValueMap().get(JcrConstants.JCR_DESCRIPTION)) {
					introDescription = contentResource.getValueMap().get(JcrConstants.JCR_DESCRIPTION).toString(); // for
																													// jcr:description
				}
				if (null != contentResource.getValueMap().get(TRIANGLE_COLOR)) {
					triangleColor = contentResource.getValueMap().get(TRIANGLE_COLOR).toString(); // for
																									// border-top-triangle
																									// color.
				}
			}
			String franchisePath = resource.getPath(); // to get the path of the franchise page...
			Resource responsivegrid = resource.getChild(CONTENT_RESOURCE_PATH); // investment-franchise
																				// responsive
																				// grid to get strategy
																				// heading
																				// and
																				// desc.
			if (null != responsivegrid) {
				Resource introComponent = responsivegrid.getChild(CONTENT_RESOURCE_NAME);
				if (null == introComponent) {
					log.debug("No Franchise Into component available in the child page");
				} else {
					franchiseIntroModel = introComponent.adaptTo(FranchiseIntroModel.class);
					log.debug("Franchise Intro Model Description: {}", franchiseIntroModel.getFranchiseDescription());
				}
				Map<String, Object> map = new HashMap<>();
				map.put(FILE_REFERENCE, fileReference);
				map.put(INTRO_DESCRIPTION, introDescription);
				map.put(TRIANGLE_COLOR, triangleColor);
				map.put(PAGEPATH, franchisePath);
				map.put("franchiseIntroModel", franchiseIntroModel);
				franchiseList.add(map);
				log.debug("Franchise List: {}", franchiseList);
			}
		}

	}
}