package com.vcm.core.pojo;

public class BreadCrumbNavItem {

	private String title;

	private String link;

	private String memberUrl;

	private String guestUrl;


	public BreadCrumbNavItem(String title, String link) {
        this.title = title;
        this.link = link;
    }
	 
	/**
	 * @return productTitle
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return productLink
	 */
	public String getLink() {
		return link;
	}

	public void setMemberUrl(String memberUrl) {
		this.memberUrl = memberUrl;
	}

	public void setGuestUrl(String guestUrl) {
		this.guestUrl = guestUrl;
	}

	public String getMemberUrl() {
		return memberUrl;
	}

	public String getGuestUrl() {
		return guestUrl;
	}
	
}
