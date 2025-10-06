package com.vcm.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;

public interface InsightListingService {
	String blogListJsonString(SlingHttpServletRequest request) throws RepositoryException ;
}
