package com.vcm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AudienceSelectionModel {

	@Inject
	String headingText;

	@Inject
	String desc;

	@Inject
	String individualInvestordesc;

	@Inject
	String memPageLinkText;

	@Inject
	String memPageLinkUrl;

	@Inject
	String victoryFundLinkText;

	@Inject
	String finProfPageLinkText;

	@Inject
	String finProfPageLinkUrl;

	@Inject
	String instInvPageLinkText;

	@Inject
	String instInvPageLinkUrl;
	
	@Inject
	String tabselectFinprof;
	
	@Inject
	String tabSelect;
	
	@Inject
	String tabselectInstinv;

	@Inject
	String individualInvestorLinkText;

	@Inject
	String usaafundsInvestorLinkText;

	@Inject
	String usaafundsInvestorLinkUrl;

	@Inject
	String victoryfundsInvestorLinkText;

	@Inject
	String finadvLinkText;

	@Inject
	String victoryFinadvLinkUrl;

	@Inject
	String overlayText;

	@Inject
	String overlayContentPath;

	String overlayId;
	
	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

	@PostConstruct
	protected void init() {
		overlayId = UtilityService.getProductDetailOverlayContent(overlayContentPath, resourceResolver, "popupId");
	}

	/**
	 * @return the headingText
	 */
	public String getHeadingText() {
		return headingText;
	}

	/**
	 * @param headingText the headingText to set
	 */
	public void setHeadingText(String headingText) {
		this.headingText = headingText;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the memPageLinkText
	 */
	public String getMemPageLinkText() {
		return memPageLinkText;
	}

	/**
	 * @param memPageLinkText the memPageLinkText to set
	 */
	public void setMemPageLinkText(String memPageLinkText) {
		this.memPageLinkText = memPageLinkText;
	}

	/**
	 * @return the memPageLinkUrl
	 */
	public String getMemPageLinkUrl() {
		return UtilityService.identifyLinkUrl(memPageLinkUrl, resourceResolver);
	}

	/**
	 * @param memPageLinkUrl the memPageLinkUrl to set
	 */
	public void setMemPageLinkUrl(String memPageLinkUrl) {
		this.memPageLinkUrl = memPageLinkUrl;
	}

	/**
	 * @return the finProfPageLinkText
	 */
	public String getFinProfPageLinkText() {
		return finProfPageLinkText;
	}

	/**
	 * @param finProfPageLinkText the finProfPageLinkText to set
	 */
	public void setFinProfPageLinkText(String finProfPageLinkText) {
		this.finProfPageLinkText = finProfPageLinkText;
	}

	/**
	 * @return the finProfPageLinkUrl
	 */
	public String getFinProfPageLinkUrl() {
		return UtilityService.identifyLinkUrl(finProfPageLinkUrl, resourceResolver);
	}

	/**
	 * @param finProfPageLinkUrl the finProfPageLinkUrl to set
	 */
	public void setFinProfPageLinkUrl(String finProfPageLinkUrl) {
		this.finProfPageLinkUrl = finProfPageLinkUrl;
	}

	/**
	 * @return the instInvPageLinkText
	 */
	public String getInstInvPageLinkText() {
		return instInvPageLinkText;
	}

	/**
	 * @param instInvPageLinkText the instInvPageLinkText to set
	 */
	public void setInstInvPageLinkText(String instInvPageLinkText) {
		this.instInvPageLinkText = instInvPageLinkText;
	}

	/**
	 * @return the instInvPageLinkUrl
	 */
	public String getInstInvPageLinkUrl() {
		return UtilityService.identifyLinkUrl(instInvPageLinkUrl, resourceResolver);
	}

	/**
	 * @param instInvPageLinkUrl the instInvPageLinkUrl to set
	 */
	public void setInstInvPageLinkUrl(String instInvPageLinkUrl) {
		this.instInvPageLinkUrl = instInvPageLinkUrl;
	}

	/**
	 * @return the tabselectFinprof
	 */
	public String getTabselectFinprof() {
		return tabselectFinprof;
	}

	/**
	 * @param tabselectFinprof the tabselectFinprof to set
	 */
	public void setTabselectFinprof(String tabselectFinprof) {
		this.tabselectFinprof = tabselectFinprof;
	}

	/**
	 * @return the tabSelect
	 */
	public String getTabSelect() {
		return tabSelect;
	}

	/**
	 * @param tabSelect the tabSelect to set
	 */
	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	/**
	 * @return the tabselectInstinv
	 */
	public String getTabselectInstinv() {
		return tabselectInstinv;
	}

	/**
	 * @param tabselectInstinv the tabselectInstinv to set
	 */
	public void setTabselectInstinv(String tabselectInstinv) {
		this.tabselectInstinv = tabselectInstinv;
	}

	/**
	 * @return the resourceResolver
	 */
	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getVictoryFundLinkText() {
		return victoryFundLinkText;
	}

	public void setVictoryFundLinkText(String victoryFundLinkText) {
		this.victoryFundLinkText = victoryFundLinkText;
	}

	public String getIndividualInvestorLinkText() {
		return individualInvestorLinkText;
	}

	public void setIndividualInvestorLinkText(String individualInvestorLinkText) {
		this.individualInvestorLinkText = individualInvestorLinkText;
	}

	public String getUsaafundsInvestorLinkText() {
		return usaafundsInvestorLinkText;
	}

	public void setUsaafundsInvestorLinkText(String usaafundsInvestorLinkText) {
		this.usaafundsInvestorLinkText = usaafundsInvestorLinkText;
	}

	public String getUsaafundsInvestorLinkUrl() {
		return UtilityService.identifyLinkUrl(usaafundsInvestorLinkUrl, resourceResolver);
	}

	public void setUsaafundsInvestorLinkUrl(String usaafundsInvestorLinkUrl) {
		this.usaafundsInvestorLinkUrl = usaafundsInvestorLinkUrl;
	}

	public String getVictoryfundsInvestorLinkText() {
		return victoryfundsInvestorLinkText;
	}

	public void setVictoryfundsInvestorLinkText(String victoryfundsInvestorLinkText) {
		this.victoryfundsInvestorLinkText = victoryfundsInvestorLinkText;
	}

	public String getFinadvLinkText() {
		return finadvLinkText;
	}

	public void setFinadvLinkText(String finadvLinkText) {
		this.finadvLinkText = finadvLinkText;
	}

	public String getVictoryFinadvLinkUrl() {
		return UtilityService.identifyLinkUrl(victoryFinadvLinkUrl, resourceResolver);
	}

	public void setVictoryFinadvLinkUrl(String victoryFinadvLinkUrl) {
		this.victoryFinadvLinkUrl = victoryFinadvLinkUrl;
	}

	public String getOverlayText() {
		return overlayText;
	}

	public void setOverlayText(String overlayText) {
		this.overlayText = overlayText;
	}

	public String getOverlayContentPath() {
		return overlayContentPath;
	}

	public void setOverlayContentPath(String overlayContentPath) {
		this.overlayContentPath = overlayContentPath;
	}

	public String getOverlayId() {
		return overlayId;
	}

	public void setOverlayId(String overlayId) {
		this.overlayId = overlayId;
	}

	public String getIndividualInvestordesc() {
		return individualInvestordesc;
	}

	public void setIndividualInvestordesc(String individualInvestordesc) {
		this.individualInvestordesc = individualInvestordesc;
	}
}
