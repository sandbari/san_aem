package com.vcm.core.models;



import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = {Resource.class} , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoModel {
	
	@Inject
	private String videoUrl;
	
	@Inject
	private String videoDuration;
	
	@Inject
	private String youtubeVideoId;
	
	@Inject 
	private String youtubeWidth;
	
	@Inject 
	private String youtubeHeight;
	
	@Inject
	private String heading;
	
	@Inject
	private String description;
	
	@Inject
	private String videoTitle;
	
	@Inject
	private String transcriptText;
	
	@Inject
	private String transcriptDescription;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}

	public String getYoutubeVideoId() {
		return youtubeVideoId;
	}

	public void setYoutubeVideoId(String youtubeVideoId) {
		this.youtubeVideoId = youtubeVideoId;
	}

	public String getYoutubeWidth() {
		return youtubeWidth;
	}

	public void setYoutubeWidth(String youtubeWidth) {
		this.youtubeWidth = youtubeWidth;
	}

	public String getYoutubeHeight() {
		return youtubeHeight;
	}

	public void setYoutubeHeight(String youtubeHeight) {
		this.youtubeHeight = youtubeHeight;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getTranscriptText() {
		return transcriptText;
	}

	public void setTranscriptText(String transcriptText) {
		this.transcriptText = transcriptText;
	}

	public String getTranscriptDescription() {
		return transcriptDescription;
	}

	public void setTranscriptDescription(String transcriptDescription) {
		this.transcriptDescription = transcriptDescription;
	}

	
}
