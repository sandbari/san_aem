package com.vcm.core.service;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.json.JSONException;
import org.json.JSONWriter;

public interface ProductMenuService{
	void writeJsonResponse(SlingHttpServletRequest request, JSONWriter writer) throws JSONException, RepositoryException;
}
