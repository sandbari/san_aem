package com.vcm.core.service;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;

public interface BlogListingService{
	String blogListJsonString(SlingHttpServletRequest request) throws RepositoryException ;
}
