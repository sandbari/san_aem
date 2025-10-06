package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlogListBean {

    private String itemsOnLoadMore;

    private String authorLink;
    
    private BlogBean featureBlog;
    
    public BlogBean getFeatureBlog() {
        return featureBlog;
    }

    public void setFeatureBlog(BlogBean featureBlog) {
        this.featureBlog = featureBlog;
    }

    private List<BlogBean> blogList;
    
    public String getItemsOnLoadMore() {
        return itemsOnLoadMore;
    }

    public void setItemsOnLoadMore(String itemsOnLoadMore) {
        this.itemsOnLoadMore = itemsOnLoadMore;
    }

    public String getAuthorLink() {
        return authorLink;
    }

    public void setAuthorLink(String authorLink) {
        this.authorLink = authorLink;
    }

    public List<BlogBean> getBlogList() {
        List<BlogBean> copyBlogList = blogList;
        Collections.copy(copyBlogList, blogList);
        return copyBlogList;
    }

    public void setBlogList(List<BlogBean> blogList) {
    	List<BlogBean> copyBlogList = new ArrayList<>();
    	copyBlogList.addAll(blogList);
		this.blogList = copyBlogList;
    }
    

}
