package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

public interface QueryBuilderService {

	public String getResults(SlingHttpServletRequest request, String searchText,String tabTypes, String searchRootPath, String searchLimit);
}
