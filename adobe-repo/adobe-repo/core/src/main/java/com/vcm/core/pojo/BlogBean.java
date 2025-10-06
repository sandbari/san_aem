package com.vcm.core.pojo;

import java.util.Date;
import java.util.Map;

public class BlogBean {

    private String title;

    private String imagePath;

    private String topic;

    private String description;
    
    private String authorDate;
    
    private transient Date authorDateDt;

    private Map<String, String> authorName;

    private transient Boolean isFeatureBlog;

    private String blogPagePath;
    
    private String earMarkColor;
    
    private String blogId;

    private Boolean hideAuthorDate;
    
    public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public String getEarMarkColor() {
		return earMarkColor;
	}

	public void setEarMarkColor(String earMarkColor) {
		this.earMarkColor = earMarkColor;
	}

	public Date getAuthorDateDt() {
        Date copyAuthorDateDt = authorDateDt;
        return copyAuthorDateDt;
    }

    public void setAuthorDateDt(Date authorDateDt) {
    	this.authorDateDt = (Date) authorDateDt.clone();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorDate() {
    	String copyAuthorDate = authorDate;
        return copyAuthorDate;
    }

    public void setAuthorDate(String authorDate) {
        this.authorDate = authorDate;
    }

    public Map<String, String> getAuthorName() {
        return authorName;
    }

    public void setAuthorName(Map<String, String> authorName) {
        this.authorName = authorName;
    }

    public Boolean isFeatureBlog() {
        return isFeatureBlog;
    }

    public void setFeatureBlog(Boolean isFeatureBlog) {
        this.isFeatureBlog = isFeatureBlog;
    }

    public String getBlogPagePath() {
        return blogPagePath;
    }

    public void setBlogPagePath(String blogPagePath) {
        this.blogPagePath = blogPagePath;
    }

    public Boolean getHideAuthorDate() {
        return hideAuthorDate;
    }

    public void setHideAuthorDate(Boolean hideAuthorDate) {
        this.hideAuthorDate = hideAuthorDate;
    }
}
