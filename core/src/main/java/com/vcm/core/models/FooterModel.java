package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vcm.core.utils.UtilityService;

import org.apache.sling.api.resource.ResourceResolver;
import javax.annotation.PostConstruct;
import java.util.Iterator;
import org.apache.sling.api.resource.ValueMap;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(Header.class);

	@Inject
	@Via("resource")
	private String copyRightText;

	@Inject
	@Via("resource")
	private Boolean incSocShare;

	@Inject
	@Via("resource")
	private String logoImagePath;

	@Inject
	@Via("resource")
	private String logoMobImagePath;

	@Inject
	@Via("resource")
	private String insLogoImagePath;

	@Inject
	@Via("resource")
	private String insLogoMobImagePath;

	@Inject
	@Via("resource")
	private String logoAltText;

	@Inject
	@Via("resource")
	private String logoLinkUrl;

	@Inject
	@Via("resource")
	private Resource multiFooterLinks;

	@Inject
	@Via("request")
	Page currentPage;

	@SlingObject
	@Via("resource")
	private ResourceResolver resourceResolver;

	@Inject
	@Via("resource")
	private Resource postLoginMultiFooterLinks;

	@Self
	private List<MultiFooterLink> updFooterLinks = new ArrayList<MultiFooterLink>();

	@Self
	private List<MultiFooterLink> updPstLoginFooterLinks = new ArrayList<MultiFooterLink>();

	private MultiFooterLink tempFooterLinkModel;

	private MultiFooterLink tempPstFooterLinkModel;

	String mappedCurrentPage;

	@PostConstruct
	protected void init() {

		LOGGER.debug("inside init function of FooterModel");

		mappedCurrentPage = UtilityService.identifyLinkUrl(currentPage.getPath(),resourceResolver);

		ValueMap footerLinkInfoValMap;

		ValueMap pstLoginfooterLinkInfoValMap;

		for (Iterator<Resource> iter = multiFooterLinks.listChildren(); iter.hasNext();) {

			footerLinkInfoValMap = iter.next().getValueMap();
			tempFooterLinkModel = new MultiFooterLink();
			tempFooterLinkModel.setFooterLinkText(footerLinkInfoValMap.get("footerLinkText", String.class));
			tempFooterLinkModel.setFooterLinkUrl(UtilityService
					.identifyLinkUrl(footerLinkInfoValMap.get("footerLinkUrl", String.class), resourceResolver));
			tempFooterLinkModel.setTabSelect(footerLinkInfoValMap.get("tabSelect", String.class));
			updFooterLinks.add(tempFooterLinkModel);

		}

		for (Iterator<Resource> iter = postLoginMultiFooterLinks.listChildren(); iter.hasNext();) {

			pstLoginfooterLinkInfoValMap = iter.next().getValueMap();
			tempPstFooterLinkModel = new MultiFooterLink();
			tempPstFooterLinkModel.setFooterLinkText(pstLoginfooterLinkInfoValMap.get("footerLinkText", String.class));
			tempPstFooterLinkModel.setFooterLinkUrl(UtilityService
					.identifyLinkUrl(pstLoginfooterLinkInfoValMap.get("footerLinkUrl", String.class), resourceResolver));
			tempPstFooterLinkModel.setTabSelect(pstLoginfooterLinkInfoValMap.get("tabSelect", String.class));
			updPstLoginFooterLinks.add(tempPstFooterLinkModel);

		}

	}

	public String getCopyRightText() {
		return copyRightText;
	}

	public void setCopyRightText(String copyRightText) {
		this.copyRightText = copyRightText;
	}

	public Boolean getIncSocShare() {
		return incSocShare;
	}

	public void setIncSocShare(Boolean incSocShare) {
		this.incSocShare = incSocShare;
	}

	public String getLogoImagePath() {
		return logoImagePath;
	}

	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}

	public String getLogoMobImagePath() {
		return logoMobImagePath;
	}

	public void setLogoMobImagePath(String logoMobImagePath) {
		this.logoMobImagePath = logoMobImagePath;
	}

	public String getLogoAltText() {
		return logoAltText;
	}

	public void setLogoAltText(String logoAltText) {
		this.logoAltText = logoAltText;
	}

	public String getLogoLinkUrl() {
		return UtilityService.identifyLinkUrl(logoLinkUrl, resourceResolver);
	}

	public void setLogoLinkUrl(String logoLinkUrl) {
		this.logoLinkUrl = logoLinkUrl;
	}

	public Resource getMultiFooterLinks() {
		return multiFooterLinks;
	}

	public void setMultiFooterLinks(Resource multiFooterLinks) {
		this.multiFooterLinks = multiFooterLinks;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public List<MultiFooterLink> getUpdFooterLinks() {
		List<MultiFooterLink> copyFooterLinks = updFooterLinks;
		Collections.copy(copyFooterLinks, updFooterLinks);
		return copyFooterLinks;
	}

	public void setUpdFooterLinks(List<MultiFooterLink> updFooterLinks) {
		List<MultiFooterLink> copyFooterLinks = new ArrayList<MultiFooterLink>();
		copyFooterLinks.addAll(updFooterLinks);
		this.updFooterLinks = copyFooterLinks;
	}

	public String getMappedCurrentPage() {
		return mappedCurrentPage;
	}

	public void setMappedCurrentPage(String mappedCurrentPage) {
		this.mappedCurrentPage = mappedCurrentPage;
	}

	public Page getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the postLoginMultiFooterLinks
	 */
	public Resource getPostLoginMultiFooterLinks() {
		return postLoginMultiFooterLinks;
	}

	/**
	 * @param postLoginMultiFooterLinks the postLoginMultiFooterLinks to set
	 */
	public void setPostLoginMultiFooterLinks(Resource postLoginMultiFooterLinks) {
		this.postLoginMultiFooterLinks = postLoginMultiFooterLinks;
	}

	/**
	 * @return the updPstLoginFooterLinks
	 */
	public List<MultiFooterLink> getUpdPstLoginFooterLinks() {
		List<MultiFooterLink> copyPstFooterLinks = updPstLoginFooterLinks;
		Collections.copy(copyPstFooterLinks, updPstLoginFooterLinks);
		return copyPstFooterLinks;
	}

	/**
	 * @param updPstLoginFooterLinks the updPstLoginFooterLinks to set
	 */
	public void setUpdPstLoginFooterLinks(List<MultiFooterLink> updPstLoginFooterLinks) {
		List<MultiFooterLink> copyPstFooterLinks = new ArrayList<MultiFooterLink>();
		copyPstFooterLinks.addAll(updPstLoginFooterLinks);
		this.updPstLoginFooterLinks = copyPstFooterLinks;
	}

	public String getInsLogoImagePath() {
		return insLogoImagePath;
	}

	public void setInsLogoImagePath(String insLogoImagePath) {
		this.insLogoImagePath = insLogoImagePath;
	}

	public String getInsLogoMobImagePath() {
		return insLogoMobImagePath;
	}

	public void setInsLogoMobImagePath(String insLogoMobImagePath) {
		this.insLogoMobImagePath = insLogoMobImagePath;
	}
}
