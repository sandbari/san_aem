package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;

import com.vcm.core.utils.UtilityService;


@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchOverlayModel {
	
	@Inject
	private String indexUrl;
	
	@Inject
	private String searchLabel;
	
	@Inject
	private String recentsearchlabel;
	
	@Inject
	private String clearsearchlabel;
	
	@Inject
	private String closebottontext;
	
	@Inject 
	private String searchResultsPage;
	
	@Inject
	private String vcmLogoImage;
	
	@Inject
	private String suggestionsLimit;
	
	@Inject
	private String suggestionsPath;
	
	@Inject
	private String searchText;
	
	@Inject
	private boolean displaySearchOverlay;
	
	@Inject
	private boolean displaySearchBar;
	
	@Inject
	@Source("sling-object")
	private ResourceResolver resourceResolver;

	
	/**
	 * @return the indexUrl
	 */
	public String getIndexUrl() {
		return UtilityService.identifyLinkUrl(indexUrl, resourceResolver);
	}
	
	/**
	 * @return the recentsearchlabel
	 */
	public String getRecentsearchlabel() {
		return recentsearchlabel;
	}
	/**
	 * @return the clearsearchlabel
	 */
	public String getClearsearchlabel() {
		return clearsearchlabel;
	}
	/**
	 * @return the closebottontext
	 */
	public String getClosebottontext() {
		return closebottontext;
	}
	/**
	 * @return the searchResultsPage
	 */
	public String getSearchResultsPage() {
		return UtilityService.identifyLinkUrl(searchResultsPage, resourceResolver);
	}
	/**
	 * @return the vcmLogoImage
	 */
	public String getVcmLogoImage() {
		return vcmLogoImage;
	}
	/**
	 * 
	 * @return the suggestionsLimit
	 */
	public String getSuggestionsLimit() {
		return suggestionsLimit;
	}

	/**
	 * @return the suggestionsPath
	 */
	public String getSuggestionsPath() {
		return suggestionsPath;
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @return the displaySearchOverlay
	 */
	public boolean isDisplaySearchOverlay() {
		return displaySearchOverlay;
	}

	/**
	 * @return the displaySearchBar
	 */
	public boolean isDisplaySearchBar() {
		return displaySearchBar;
	}

	

	/**
	 * @param indexUrl the indexUrl to set
	 */
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}
	/**
	 * @param recentsearchlabel the recentsearchlabel to set
	 */
	public void setRecentsearchlabel(String recentsearchlabel) {
		this.recentsearchlabel = recentsearchlabel;
	}

	/**
	 * @param clearsearchlabel the clearsearchlabel to set
	 */
	public void setClearsearchlabel(String clearsearchlabel) {
		this.clearsearchlabel = clearsearchlabel;
	}

	/**
	 * @param closebottontext the closebottontext to set
	 */
	public void setClosebottontext(String closebottontext) {
		this.closebottontext = closebottontext;
	}

	/**
	 * @param searchResultsPage the searchResultsPage to set
	 */
	public void setSearchResultsPage(String searchResultsPage) {
		this.searchResultsPage = searchResultsPage;
	}

	/**
	 * @param vcmLogoImage the vcmLogoImage to set
	 */
	public void setVcmLogoImage(String vcmLogoImage) {
		this.vcmLogoImage = vcmLogoImage;
	}

	/**
	 * @param suggestionsLimit the suggestionsLimit to set
	 */
	public void setSuggestionsLimit(String suggestionsLimit) {
		this.suggestionsLimit = suggestionsLimit;
	}

	/**
	 * @param suggestionsPath the suggestionsPath to set
	 */
	public void setSuggestionsPath(String suggestionsPath) {
		this.suggestionsPath = suggestionsPath;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @param displaySearchOverlay the displaySearchOverlay to set
	 */
	public void setDisplaySearchOverlay(boolean displaySearchOverlay) {
		this.displaySearchOverlay = displaySearchOverlay;
	}

	/**
	 * @param displaySearchBar the displaySearchBar to set
	 */
	public void setDisplaySearchBar(boolean displaySearchBar) {
		this.displaySearchBar = displaySearchBar;
	}

	/**
	 * @param resourceResolver the resourceResolver to set
	 */
	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}
	/**
	 * @return the searchLabel
	 */
	public String getSearchLabel() {
		return searchLabel;
	}
	/**
	 * @param searchLabel the searchLabel to set
	 */
	public void setSearchLabel(String searchLabel) {
		this.searchLabel = searchLabel;
	}

}