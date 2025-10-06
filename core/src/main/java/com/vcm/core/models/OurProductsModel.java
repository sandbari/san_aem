package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.NameConstants;
import com.vcm.core.pojo.ProductsBean;
import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OurProductsModel {

    private static final Logger log = LoggerFactory.getLogger(OurProductsModel.class);

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;

	@Inject
	private String title;

	@Inject
	private String desktopImage;

	private List<ProductsBean> productList = new ArrayList<>();

	@Inject
	private String contentType;

	@Inject
	private String linkVariant;
	
	@Inject
	private String columnVariant;
	
	@Inject
	private String sectionDescription;
	
	@Inject 
	private String titleVariant;
	
	@Inject
	private String altText;
	
	@Inject
	private String listingPageLink;
	
	@Inject
	private String listingLinkDesc;
			
	public String getListingLinkDesc() {
		return listingLinkDesc;
	}

	public void setListingLinkDesc(String listingLinkDesc) {
		this.listingLinkDesc = listingLinkDesc;
	}

	public String getListingPageLink() {
		return listingPageLink;
	}

	public void setListingPageLink(String listingPageLink) {
		this.listingPageLink = listingPageLink;
	}

	public String getAltText(){
		return altText;
	}
	
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}

	public String getSectionDescription() {
		return sectionDescription;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesktopImage() {
		return desktopImage;
	}

	public String getColumnVariant() {
		return columnVariant;
	}

	public String getTitleVariant() {
		return titleVariant;
	}

	public void setTitleVariant(String titleVariant) {
		this.titleVariant = titleVariant;
	}

	public void setColumnVariant(String columnVariant) {
		this.columnVariant = columnVariant;
	}

	public void setDesktopImage(String desktopImage) {
		this.desktopImage = desktopImage;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<ProductsBean> getProductList() {
		List<ProductsBean> copyProductList = productList;
		Collections.copy(copyProductList, productList);
		return copyProductList;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setLinkVariant(String variant) {
		this.linkVariant = variant;
	}

	public String getLinkVariant() {
		return linkVariant;
	}

	public String getContentType() {
		return contentType;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	@PostConstruct
	protected void init() {
		
		log.debug("*** inside Our Product Model init() ***");
		listingPageLink = UtilityService.identifyLinkUrl(listingPageLink, resourceResolver);
		Iterator<Resource> resourceIterator = resource.listChildren();
		// get the grandchild node of the currentNode - which represents where the
		// MultiField values are stored
		while (resourceIterator.hasNext()) {
			Resource childResource = resourceIterator.next();

			Iterator<Resource> childResourceIterator = childResource.listChildren();

			// THIS WILL READ THE VALUE OF THE Multifield and set them in a collection
			while (childResourceIterator.hasNext()) {
				Resource contentResource = childResourceIterator.next();
				setProductBeanList(contentResource);
			}
		}

	}

	/**
	 * Method to set product list items
	 * 
	 * @return submenuItems
	 */
	private void setProductBeanList(Resource resource) {
		log.debug("*** inside set product Bean list ***{}", resource.getPath());
		String prodTitle = "";
		String prodLink = "";
		String productButtonText = "";
		String rightColumnButtonText = "";
		String rightColumnLink = "";
		ProductsBean productItems = new ProductsBean();

		if (null != resource.getValueMap().get("productTitle")) {
			prodTitle = resource.getValueMap().get("productTitle").toString();
		}
		if (null != resource.getValueMap().get("ismultiTargetLink")) {
			productItems.setIsmultiTargetLink(resource.getValueMap().get("ismultiTargetLink",Boolean.class));
		}
		if (null != resource.getValueMap().get("memberProductLink")) {
			String memberProductLink = UtilityService.identifyLinkUrl(resource.getValueMap().get("memberProductLink").toString(),
					resourceResolver);
			productItems.setMemberProductLink(memberProductLink);
		}
		if (null != resource.getValueMap().get("financialProductlink")) {
			String financialProductlink = UtilityService.identifyLinkUrl(resource.getValueMap().get("financialProductlink").toString(),
					resourceResolver);
			productItems.setFinancialProductlink(financialProductlink);
		}
		if (null != resource.getValueMap().get("iiProductlink")) {
			String iiProductlink = UtilityService.identifyLinkUrl(resource.getValueMap().get("iiProductlink").toString(),
					resourceResolver);
			productItems.setIiProductlink(iiProductlink);
		}
		if (null != resource.getValueMap().get("productLink")) {
			prodLink = UtilityService.identifyLinkUrl(resource.getValueMap().get("productLink").toString(),
					resourceResolver);
		}
		if(Objects.nonNull(resource.getValueMap().get("linkDescription"))){
	        	productItems.setLinkDescription(resource.getValueMap().get("linkDescription").toString());
	    } 
		else if (Objects.nonNull(prodLink) && Objects.nonNull(prodTitle)) {
	        	productItems.setLinkDescription(UtilityService.getLinkDescription(prodLink, prodTitle, resourceResolver));
	    }
		productItems.setProductTitle(prodTitle);
		productItems.setProductLink(prodLink);

		if (resource.getValueMap().containsKey("audienceType") && null != resource.getValueMap().get("audienceType")) {
			 String[] audienceTypeArray = resource.getValueMap().get("audienceType", new String[]{});
			 //String audienceTypeArrayString = Arrays.toString(audienceTypeArray);
			 productItems.setAudienceType(audienceTypeArray);
		}

		if (null != resource.getValueMap().get("tabSelect")) {
			productItems.setTabSelect(resource.getValueMap().get("tabSelect").toString());
		}
		
		if(null != resource.getValueMap().get("fundType", String.class)) {
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			Tag topicTag = tagManager.resolve(resource.getValueMap().get("fundType", String.class));
			Resource tagResource = resourceResolver.getResource(topicTag.getPath());
			if(null != tagResource) {
				productItems.setFundType(tagResource.getValueMap().get(NameConstants.PN_TITLE).toString());
			}
		}
		
		if (null != resource.getValueMap().get("descTabSelect")) {
			productItems.setDescTabSelect(resource.getValueMap().get("descTabSelect").toString());
		}

		if (null != resource.getValueMap().get("productHeading")) {
			productItems.setProductHeading(resource.getValueMap().get("productHeading").toString());
		}

		if (null != resource.getValueMap().get("productDescription")) {
			productItems.setProductDescription(resource.getValueMap().get("productDescription").toString());
		}

		if (null != resource.getValueMap().get("desktopButtonText")) {
			productButtonText = resource.getValueMap().get("desktopButtonText").toString();
			productItems.setDesktopButtonText(productButtonText);
		}

		if (null != resource.getValueMap().get("tabletButtonText")) {
			productItems.setTabletButtonText(resource.getValueMap().get("tabletButtonText").toString());
		}

		if (null != resource.getValueMap().get("productButtonLink")) {
			String productButtonLink = resource.getValueMap().get("productButtonLink").toString();
			productItems.setProductButtonLink(UtilityService
					.identifyLinkUrl(productButtonLink, resourceResolver));
	        if(Objects.nonNull(resource.getValueMap().get("buttonLinkDesc"))){
	        	productItems.setButtonLinkDesc(resource.getValueMap().get("buttonLinkDesc").toString());
	        } else if (Objects.nonNull(productButtonLink) && Objects.nonNull(productButtonText)) {
	        	productItems.setButtonLinkDesc(UtilityService.getLinkDescription(productButtonLink, productButtonText, resourceResolver));
	        }

		}

		if (null != resource.getValueMap().get("productIconTitle")) {
			productItems.setProductIconTitle(resource.getValueMap().get("productIconTitle").toString());
		}

		if (null != resource.getValueMap().get("productTextArea")) {
			productItems.setProductTextArea(resource.getValueMap().get("productTextArea").toString());
		}

		if (null != resource.getValueMap().get("productIcon")) {
			productItems.setProductIcon(resource.getValueMap().get("productIcon").toString());
		}

		if (null != resource.getValueMap().get("productGreyText")) {
			productItems.setProductGreyText(resource.getValueMap().get("productGreyText").toString());
		}
		if (null != resource.getValueMap().get("productBlueText")) {
			String blueText = resource.getValueMap().get("productBlueText").toString();
			productItems.setProductBlueText(blueText);
			productItems.setOverlayAttribute(blueText.replaceAll(" ", "_"));
		}
		if (null != resource.getValueMap().get("leftColumnTitle")) {
			productItems.setLeftColumnTitle(resource.getValueMap().get("leftColumnTitle").toString());
		}
		if (null != resource.getValueMap().get("leftColumnDescription")) {
			productItems.setLeftColumnDescription(resource.getValueMap().get("leftColumnDescription").toString());
		}
		if (null != resource.getValueMap().get("rightColumnTitle")) {
			productItems.setRightColumnTitle(resource.getValueMap().get("rightColumnTitle").toString());
		}
		if (null != resource.getValueMap().get("rightColumnDescription")) {
			productItems.setRightColumnDescription(resource.getValueMap().get("rightColumnDescription").toString());
		}
		if (null != resource.getValueMap().get("rightColumnButtonText")) {
			rightColumnButtonText = resource.getValueMap().get("rightColumnButtonText").toString();
			productItems.setRightColumnButtonText(rightColumnButtonText);
		}
		
		if (null != resource.getValueMap().get("rightColumnLink")) {
			rightColumnLink = resource.getValueMap().get("rightColumnLink").toString();
			productItems.setRightColumnLink(UtilityService
					.identifyLinkUrl(rightColumnLink, resourceResolver));
			if(Objects.nonNull(resource.getValueMap().get("overlayLinkDesc"))){
	        	productItems.setOverlayLinkDesc(resource.getValueMap().get("overlayLinkDesc").toString());
	        } else if (Objects.nonNull(rightColumnLink) && Objects.nonNull(rightColumnButtonText)) {
	        	productItems.setOverlayLinkDesc(UtilityService.getLinkDescription(rightColumnLink, rightColumnButtonText, resourceResolver));
	        }
		}
		
		if (null != resource.getValueMap().get("rightColumnLink")) {
			rightColumnLink = resource.getValueMap().get("rightColumnLink").toString();
			productItems.setRightColumnLink(UtilityService
					.identifyLinkUrl(rightColumnLink, resourceResolver));

			if(Objects.nonNull(resource.getValueMap().get("linkDescriptionType"))){
	        	productItems.setLinkDescriptionType(resource.getValueMap().get("linkDescriptionType").toString());
	        } else if (Objects.nonNull(rightColumnLink) && Objects.nonNull(rightColumnButtonText)) {
	        	productItems.setLinkDescriptionType(UtilityService.getLinkDescription(rightColumnLink, rightColumnButtonText, resourceResolver));
	        }
		}	
		
		productList.add(productItems);
	}

}
