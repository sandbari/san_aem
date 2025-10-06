package com.vcm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.service.AudienceSelectorService;

@ExtendWith(MockitoExtension.class)
public class SiteMapServletTest {


    private SiteMapServlet siteMapServlet = new SiteMapServlet();
    private SiteMapServlet.SiteMapConfig config;
    @Mock
    private SlingHttpServletResponse response;
    @Mock
    private SlingHttpServletRequest request;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    PageManager pageManager;

    @Mock
    Resource resource;

    @Mock
    Page page;

    @Mock
    PrintWriter writer;

    @Mock
    ValueMap valueMap;

    @Mock
    Externalizer externalizer;

    @Mock
    PageFilter pageFilter;

    @Mock
    Iterator<Page> childrenItr;

    @Mock
    Page child;

    @Mock
    AudienceSelectorService audienceSelectorService;
	
    
    @BeforeEach
    public void setUp() throws Exception {
	
	List<String> allowedAudience = new ArrayList<String>();
	allowedAudience.add("ALL");
        String[] strArr = new String[]{};
        List<String> lst = new ArrayList<>();
        config = mock(SiteMapServlet.SiteMapConfig.class);
        siteMapServlet.setAudienceSelectorService(audienceSelectorService);
        lenient().when(audienceSelectorService.getAllowedAudienceListFromResource(Mockito.any())).thenReturn(allowedAudience);
        lenient().when(config.exclude_tag()).thenReturn("excludesearch");
        lenient().when(config.changefreq_properties()).thenReturn(strArr);
        lenient().when(config.character_encoding()).thenReturn("null");
        lenient().when(config.exclude_property()).thenReturn("navtag");
        lenient().when(config.include_inherit()).thenReturn(true);
        lenient().when(config.include_lastmod()).thenReturn(true);
        lenient().when(config.priority_properties()).thenReturn(strArr);
        lenient().when(request.getResponseContentType()).thenReturn("application/xml");
        lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
        lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        lenient().when(request.getResource()).thenReturn(resource);
        lenient().when(pageManager.getContainingPage(request.getResource())).thenReturn(page);
        lenient().when(response.getWriter()).thenReturn(writer);
        lenient().when(page.getProperties()).thenReturn(valueMap);
        lenient().when(page.getPath()).thenReturn("/content/vcm/us/en");
        lenient().when(resourceResolver.map(Mockito.anyString())).thenReturn("/content/vcm/us/en");
        lenient().when(page.listChildren(Mockito.any(PageFilter.class),Mockito.anyBoolean())).thenReturn(childrenItr);
        lenient().when(childrenItr.hasNext()).thenReturn(false);
        lenient().when(request.getRequestURI()).thenReturn("/content/vcm/us/en.sitemap.xml");
    }


    @Test
    public void testDoGetSlingHttpServletRequestSlingHttpServletResponse()
            throws ServletException, IOException {
        siteMapServlet.activate(config);
        siteMapServlet.doGet(request, response);
    }
    @Test
    public void testExtensionlessUrls()
            throws ServletException, IOException {
        siteMapServlet.activate(config);
        siteMapServlet.doGet(request, response);
    }
    @Test
    public void testNonInheritedValues()
            throws ServletException, IOException {
        lenient().when(config.include_inherit()).thenReturn(false);
        siteMapServlet.activate(config);
        siteMapServlet.doGet(request, response);
    }

}
