package com.vcm.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vcm.core.pojo.BlogBean;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class BlogListingServiceImplTest {

	private BlogListingServiceImpl blogListingService= new BlogListingServiceImpl();
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
    	context.load().json("/blogListResource.json", "/content/vcm/en/blogslisting");
    	context.load().json("/blogTags.json", "/content/cq:tags/vcm");
    	context.load().json("/blogPages.json", "/content/vcm/us/en/blog");
    }
    
    @Test
    public void blogListingJsonTest() throws PathNotFoundException, RepositoryException, LoginException, JSONException {
    	MockSlingHttpServletRequest mockReq=context.request();
    	Resource resource = context.currentResource("/content/vcm/en/blogslisting");
    	mockReq.setResource(resource);
    	String jsonString = blogListingService.blogListJsonString(context.request());
    	assertNotNull(jsonString);
    	assertNotNull(blogListingService.getBlogListBean());
    	assertNotNull(blogListingService.getBlogListBean().getAuthorLink());
    	assertNotNull(blogListingService.getBlogListBean().getBlogList());
    	assertNotNull(blogListingService.getBlogListBean().getFeatureBlog());
    	assertNotNull(blogListingService.getBlogListBean().getItemsOnLoadMore());
    	BlogBean blogBean = blogListingService.getBlogListBean().getBlogList().get(0);
    	assertNotNull(blogBean.getAuthorDate());
    	assertNotNull(blogBean.getAuthorDateDt());
    	assertNotNull(blogBean.getAuthorName());
    	assertNotNull(blogBean.getBlogId());
    	assertNotNull(blogBean.getBlogPagePath());
    	assertNotNull(blogBean.getDescription());
    	assertNotNull(blogBean.getEarMarkColor());
    	assertNotNull(blogBean.getImagePath());
    	assertNotNull(blogBean.getTitle());
    	assertNotNull(blogBean.getTopic());
    }
    @Test
    public void blogListingJsonReqParamTest() throws PathNotFoundException, RepositoryException, LoginException, JSONException {
    	MockSlingHttpServletRequest mockReq=context.request();
    	Resource mockResource = context.currentResource("/content/vcm/us/en/blog");
    	mockReq.setResource(mockResource);
    	Map<String,Object> parameterMap = new HashMap<>(1);
    	parameterMap.put("blogRootPath", "/content/vcm/us/en/blog");
    	mockReq.setParameterMap(parameterMap);
    	String jsonString = blogListingService.blogListJsonString(context.request());
    	assertNotNull(jsonString);
    	
    }
}