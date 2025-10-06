package com.vcm.core.pojo;

import com.day.cq.wcm.api.Page;
/**
 * 
 * @author CTS Product Accordion Bean class
 *
 */
public class ProductAccordionBean {

	private Page page;
	private String productPageUrl;
	private String target;
	private String linkTitle;
	private boolean isExternalUrl;

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the linkTitle
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * @param linkTitle the linkTitle to set
	 */
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * @return the productPageUrl
	 */
	public String getProductPageUrl() {
		return productPageUrl;
	}

	/**
	 * @param productPageUrl the productPageUrl to set
	 */
	public void setProductPageUrl(String productPageUrl) {
		this.productPageUrl = productPageUrl;
	}

	/**
	 * @return the isExternalUrl
	 */
	public boolean getIsExternalUrl() {
		return isExternalUrl;
	}

	/**
	 * @param isExternalUrl the isExternalUrl to set
	 */
	public void setIsExternalUrl(boolean isExternalUrl) {
		this.isExternalUrl = isExternalUrl;
	}

}
