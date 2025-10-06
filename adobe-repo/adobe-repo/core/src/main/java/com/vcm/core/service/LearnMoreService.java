package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;

public interface LearnMoreService{
	String getRenderedHTML(SlingHttpServletRequest request, SlingHttpServletResponse response);
	String getDefaultContent(String parentPage,ResourceResolver resourceResolver);
}
