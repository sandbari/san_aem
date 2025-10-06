package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultiFooterLink {

	@Inject
	String footerLinkText;

	@Inject
	String footerLinkUrl;

	@Inject
	String tabSelect;


	public String getFooterLinkText() {
		return footerLinkText;
	}

	public void setFooterLinkText(String footerLinkText) {
		this.footerLinkText = footerLinkText;
	}

	public String getFooterLinkUrl() {
		return footerLinkUrl;
	}

	public void setFooterLinkUrl(String footerLinkUrl) {
		this.footerLinkUrl = footerLinkUrl;
	}

	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}
}
