package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class BlogArticleModelTest {

    private BlogArticleModel blogArticleModel;
    ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
    	blogArticleModel = new BlogArticleModel();
    	blogArticleModel.setResourceResolver(context.resourceResolver());
    	
    }

    @Test
    void testInit() throws Exception {
    	String[] authorName = {"bernadl", "seron"};
    	blogArticleModel.setAuthorName(authorName);
    	blogArticleModel.setBlogContent("<p>some content is here</p>");
    	blogArticleModel.setBlogHeadImage("head.png");
    	blogArticleModel.setAltText("Image heading");
    	blogArticleModel.setBlogHeading("heading");
    	blogArticleModel.setIsFeaturedBlog(false);
    	blogArticleModel.setTimeTaken("2 minutes");
    	blogArticleModel.setAuthorDate(new Date());
    	blogArticleModel.init();
    	assertNotNull(blogArticleModel.getAuthorDateString());
    	assertNotNull(blogArticleModel.getBlogContent());
    	assertNotNull(blogArticleModel.getBlogHeadImage());
    	assertNotNull(blogArticleModel.getAltText());
    	assertNotNull(blogArticleModel.getBlogHeading());
    	assertNotNull(blogArticleModel.getIsFeaturedBlog());
    	assertNotNull(blogArticleModel.getTimeTaken());
    	assertNotNull(blogArticleModel.getAuthorDate());
    	assertNotNull(blogArticleModel.getAuthorList());
    }
    
    @Test
    void testInitAuthorDateNull() throws Exception {
    	blogArticleModel.init();
    	assertNotNull(blogArticleModel.getAuthorDateString());
  
    }
    
    @Test
    void testInitAuthorNameNull() throws Exception {
    	blogArticleModel.init();
    	assertNull(blogArticleModel.getAuthorList());
  
    }
    
}