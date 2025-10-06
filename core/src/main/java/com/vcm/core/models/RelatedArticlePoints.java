package com.vcm.core.models;


import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RelatedArticlePoints {
	
    	@Inject
		private String thumbnailImage;	    	
    	
    	@Inject
		private String altText;
    	
    	@Inject
		private String articleText;
    	
    	@Inject
    	private String linkUrl;
    	
		@Inject
    	private String tabSelect;
    	
    	@SlingObject
    	private ResourceResolver resourceResolver;

		public String getThumbnailImage() {
			return thumbnailImage;
		}

		public void setThumbnailImage(String thumbnailImage) {
			this.thumbnailImage = thumbnailImage;
		}

		public String getAltText() {
			return altText;
		}

		public void setAltText(String altText) {
			this.altText = altText;
		}

		public String getArticleText() {
			return articleText;
		}

		public void setArticleText(String articleText) {
			this.articleText = articleText;
		}

		public String getLinkUrl() {
			return UtilityService.identifyLinkUrl(linkUrl, resourceResolver);
		}

		public void setLinkUrl(String linkUrl) {
			this.linkUrl = linkUrl;
		}
		
		public void setResourceResolver(ResourceResolver resourceResolver) {
	        this.resourceResolver = resourceResolver;
	    }

		public String getTabSelect() {
			return tabSelect;
		}

		public void setTabSelect(String tabSelect) {
			this.tabSelect = tabSelect;
		}
}
