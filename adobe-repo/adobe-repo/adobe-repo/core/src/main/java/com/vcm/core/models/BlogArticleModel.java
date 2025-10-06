package com.vcm.core.models;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BlogArticleModel {

	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	private String blogHeading;

	@Inject
	private String timeTaken;

	@Inject
	private String[] authorName;

	@Inject
	private Date authorDate;

	@Inject
	private String blogHeadImage;
	
	@Inject
	private String altText;

	@Inject
	private String blogContent;

	@Inject
	private Boolean isFeaturedBlog;

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
	public String getBlogHeading() {
		return blogHeading;
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
	public String getBlogHeadImage() {
		return blogHeadImage;
	}

	/**
	 * @return blogContent
	 */
	public String getBlogContent() {
		return blogContent;
	}

	/**
	 * @return isFeaturedFund
	 */
	public Boolean getIsFeaturedBlog() {
		return isFeaturedBlog;
	}

	/**
	 * @return authorDateString
	 */
	public String getAuthorDateString() {
		return authorDateString;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public void setAuthorDate(Date authorDate) {
		this.authorDate = (Date) authorDate.clone();
	}

	public void setBlogHeading(String blogHeading) {
		this.blogHeading = blogHeading;
	}

	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

	public void setBlogHeadImage(String blogHeadImage) {
		this.blogHeadImage = blogHeadImage;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public void setIsFeaturedBlog(Boolean isFeaturedBlog) {
		this.isFeaturedBlog = isFeaturedBlog;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	
}
