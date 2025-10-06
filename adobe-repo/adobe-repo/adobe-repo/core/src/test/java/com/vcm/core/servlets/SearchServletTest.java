package com.vcm.core.servlets;

import com.vcm.core.service.QueryBuilderService;
import com.vcm.core.service.SuggestionService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.apache.sling.xss.ProtectionContext;
import org.apache.sling.xss.XSSFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class SearchServletTest {

   
	@Mock
	QueryBuilderService queryBuilderService;
	
	@Mock
	SuggestionService suggestionService;
	
	@Mock
	XSSFilter xssFilter;
	
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    private SearchServlet searchServlet;

    @BeforeEach
    public void setup() throws Exception {
    	searchServlet= new SearchServlet();
    	searchServlet.setQueryBuilderService(queryBuilderService);
    	searchServlet.setSuggestionService(suggestionService);
    	searchServlet.setXssFilter(xssFilter);
    	
    }

    @Test
    void testGetMethodSearchContext() throws Exception {
    	MockSlingHttpServletRequest mockReq=context.request();
    	MockRequestPathInfo mockPathInfo =(MockRequestPathInfo) mockReq.getRequestPathInfo();
    	mockPathInfo.setResourcePath("/content/vcm");
    	mockPathInfo.setSelectorString("sitesearch");
    	mockPathInfo.setExtension(".json");
    	Map<String, Object> parameterMap=new HashMap<>();
    	parameterMap.put("searchtext", "test");
		mockReq.setParameterMap(parameterMap);
    	MockSlingHttpServletResponse mockRes=context.response();
    	PrintWriter mockWriter  = mock(PrintWriter.class);//mockRes.getWriter();
    	resourceResolver=context.resourceResolver();

		when(xssFilter.filter(ProtectionContext.PLAIN_HTML_CONTENT, null)).thenReturn("{}");
    	searchServlet.doGet(mockReq, mockRes);
    	Assert.assertEquals(mockRes.getContentType(), "application/json;charset=UTF-8");
    	Assert.assertNotNull(mockRes.getContentLength());
    }
    
    @Test
    void testGetMethodSuggestionContext() throws Exception {
    	MockSlingHttpServletRequest mockReq=context.request();
    	MockRequestPathInfo mockPathInfo =(MockRequestPathInfo) mockReq.getRequestPathInfo();
    	mockPathInfo.setResourcePath("/content/vcm");
    	mockPathInfo.setSelectorString("suggestions");
    	mockPathInfo.setExtension(".json");
    	Map<String, Object> parameterMap=new HashMap<>();
    	parameterMap.put("searchtext", "test");
		mockReq.setParameterMap(parameterMap);
    	MockSlingHttpServletResponse mockRes=context.response();
    	resourceResolver=context.resourceResolver();
		when(xssFilter.filter(ProtectionContext.PLAIN_HTML_CONTENT, "{\"suggestion\":[]}")).thenReturn("{\"suggestion\":[]}");

    	searchServlet.doGet(mockReq, mockRes);
    	Assert.assertEquals(mockRes.getContentType(), "application/json;charset=UTF-8");
    	Assert.assertNotNull(mockRes.getContentLength());
    }


}