package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.vcm.core.utils.UtilityService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LearningResourceTileContent {
		
	@Inject
	private String topic;
	
	@Inject
	private String tileHeading;
	
	@Inject
	private String tileLinkURL;
	
	@Inject
	private String tiletabSelect;
	
	@Inject
	private String bgimage;
	
	@Inject
	private String topicbackgroundcolor;
	
	@Inject
	private String altText;
	
	@SlingObject
	private ResourceResolver resourceResolver;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTileHeading() {
		return tileHeading;
	}

	public void setTileHeading(String tileHeading) {
		this.tileHeading = tileHeading;
	}

	public String getTileLinkURL() {
		return UtilityService.identifyLinkUrl(tileLinkURL, resourceResolver);
	}

	public void setTileLinkURL(String tileLinkURL) {
		this.tileLinkURL = tileLinkURL;
	}

	public String getTiletabSelect() {
		return tiletabSelect;
	}

	public void setTiletabSelect(String tiletabSelect) {
		this.tiletabSelect = tiletabSelect;
	}

	public String getBgimage() {
		return bgimage;
	}

	public void setBgimage(String bgimage) {
		this.bgimage = bgimage;
	}

	public String getTopicbackgroundcolor() {
		return topicbackgroundcolor;
	}

	public void setTopicbackgroundcolor(String topicbackgroundcolor) {
		this.topicbackgroundcolor = topicbackgroundcolor;
	}
	
	public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }
	
	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

}