package com.vcm.core.models;

public class SearchResultDetail {
	private String pageTitle;
	private String description;
	private String[] tags;
	private String pagePath;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String[] getTags() {
		return tags.clone();
	}
	public void setTags(String[] tags) {
		String[] copyStringArr =  tags.clone();
		this.tags = copyStringArr;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
}
