package com.vcm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InsightArticleModel {

	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	private String insightHeading;

	@Inject
	private String timeTaken;

	@Inject
	private String[] authorName;

	@Inject
	private Date authorDate;

	@Inject
	private String insightImage;
	
	@Inject
	private String altText;

	@Inject
	private String insightContent;

	@Inject
	private Boolean isFeaturedInsight;

	@Inject
	private Boolean hideAuthorDate;

	private String authorDateString;

	private Set<String> authorList;

	/**
	 * init method for adding logic
	 */
	@PostConstruct
	protected void init() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		if (null == authorDate) {
			Date date = new Date();
			authorDateString = simpleDateFormat.format(date);
		} else {
			authorDateString = simpleDateFormat.format(authorDate);
		}
		if (null != authorName) {
			authorList = new HashSet<String>();
			Collections.addAll(authorList, authorName);
		}
	}


	/**
	 * @return authorList
	 */
	public Set<String> getAuthorList() {
		Set<String> copyAuthorList = authorList;
		if(null != authorList) {
			copyAuthorList.addAll(authorList);
		}
		return copyAuthorList;
	}

	public void setAuthorName(String[] authorName) {
		this.authorName = authorName.clone();
	}

	/**
	 * @return blogHeading
	 */
	public String getInsightHeading() {
		return insightHeading;
	}
	
	/**
	 * @return altText
	 */	
	public String getAltText() {
		return altText;
	}

	/**
	 * @return timeTaken
	 */
	public String getTimeTaken() {
		return timeTaken;
	}

	/**
	 * @return authorDate
	 */
	public Date getAuthorDate() {
		Date copyAuthorDate = authorDate;
		return copyAuthorDate;
	}

	/**
	 * @return blogHeadImage
	 */
	public String getInsightImage() {
		return insightImage;
	}

	/**
	 * @return blogContent
	 */
	public String getInsightContent() {
		return insightContent;
	}

	/**
	 * @return isFeaturedFund
	 */
	public Boolean getIsFeaturedInsight() {
		return isFeaturedInsight;
	}

	/**
	 * @return authorDateString
	 */
	public String getAuthorDateString() {
		return authorDateString;
	}

	/**
	 *
	 * @return hideAuthorDate
	 */
	public Boolean getHideAuthorDate() {
		return hideAuthorDate;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public void setAuthorDate(Date authorDate) {
		this.authorDate = (Date) authorDate.clone();
	}

	public void setInsightHeading(String insightHeading) {
		this.insightHeading = insightHeading;
	}

	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

	public void setInsightImage(String insightImage) {
		this.insightImage = insightImage;
	}

	public void setInsightContent(String insightContent) {
		this.insightContent = insightContent;
	}

	public void setIsFeaturedInsight(Boolean isFeaturedInsight) {
		this.isFeaturedInsight = isFeaturedInsight;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public void setHideAuthorDate(Boolean hideAuthorDate) {
		this.hideAuthorDate = hideAuthorDate;
	}
	
}
