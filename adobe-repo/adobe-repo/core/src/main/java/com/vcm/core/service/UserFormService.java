package com.vcm.core.service;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;

public interface UserFormService{
	String userFormsJson(SlingHttpServletRequest request) throws RepositoryException ;

    String userFormsPrivateJson(SlingHttpServletRequest request) throws RepositoryException;
}
